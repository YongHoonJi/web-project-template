package com.systrangroup.web.template.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.exception.DataFormatException;
import com.systrangroup.web.template.example.service.BusinessService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
 
@RestController
@RequestMapping("/sample/users")

public class MainController extends AbstractRestHandler{
	
	@Autowired 
	private BusinessService businessService;
	
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all users", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")    
    public
    @ResponseBody
    Page<User> getAllUsers(@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
    	if(request.getSession() != null){
    		System.out.println(request.getSession().getCreationTime());
    		System.out.println(request.getSession().getId());
    	}
    	return this.businessService.getAllUsers(page, size);
    }
    
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a user.", notes = "You have to provide a valid user ID.")
    public
    @ResponseBody
    User getUser(@ApiParam(value = "The ID of a user.", required = true)
                             @PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return this.businessService.getUser(id);
    }
    
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a user.", notes = "Returns the URL of the new resource in the Location header.")
    public void createUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        User savedUser = this.businessService.createUser(user);
        response.setHeader("Location", request.getRequestURL().append("/").append(savedUser.getId()).toString());
    }
    
    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a user.", notes = "You have to provide a valid user ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateUser(@ApiParam(value = "The ID of the existing hotel resource.", required = true)
                                 @PathVariable("id") Long id, @RequestBody User user,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.businessService.getUser(id));
        if (id != user.getId()) throw new DataFormatException("ID doesn't match!");
        this.businessService.updateUser(user);
    }
    
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deactivate a user.", notes = "You have to provide a valid user ID in the URL.")
    public void deleteUser(@ApiParam(value = "The ID of the existing user.", required = true)
                                 @PathVariable("id") Long id, 
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.businessService.getUser(id));
        this.businessService.deActivate(id);
    }
    
    /************** 아래는 JPA 테스트용 ****************/
    /**
     * find a user by advanced query(JPQL)
     * @return
     */
    @RequestMapping("/findOneUserByAdvancedQuery")
    public @ResponseBody User findOneUserByAdvancedQuery(User user) {
        return this.businessService.findOneUserByAdvancedQuery(user.getName());
    }        
    
    /**
     * search by defined query
     * @return
     */
    @RequestMapping("/findByNameFromUser")
    public @ResponseBody List<User> findByNameFromUser(User user) {
        return this.businessService.findByNameFromUser(user.getName());
    }
    
    /**
     * search by customized query
     * @return
     */
    @RequestMapping("/findOneUserByNativeQuery")
    public @ResponseBody List<User> findOneUserByNativeQuery() {
        return this.businessService.findOneUserByNativeQuery();
    }
    
    /**
     * search by queryDsl(Joined JPQL)
     * @return
     */
    @RequestMapping("/findOneUserByQueryDsl")
    public @ResponseBody User findOneUserByQueryDsl(User user) throws Exception{
    	return this.businessService.findOneUserByQueryDsl(user.getId());
    }
    
    /**
     * get list with pagination 
     * @return
     */
    @RequestMapping("/findListWithPagination")
    public @ResponseBody List<User> findListWithPagination(User user,@RequestParam("page") int pageNumber, @RequestParam("size")int pageSize) throws Exception{
    	Pageable pageRequest = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "id");
    	return this.businessService.findListWithPagination(user, pageRequest);
    }
}
