package com.systrangroup.web.template.example.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.exception.DataFormatException;
import com.systrangroup.web.template.example.service.JpaTestService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
 
@Controller
@RequestMapping("/mat/v1.0/users")
public class UserController extends AbstractRestHandler{
	
	@Autowired 
	private JpaTestService businessService;
	
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all users", notes = "The list is paginated. You can provide a offset number (default 0) and a limit (default 100)")    
    public
    @ResponseBody
    Page<User> getAllUsers(@ApiParam(value = "offset number is zero-based)", required = true)
                                      @RequestParam(value = "offset", required = true, defaultValue = DEFAULT_OFFSET) Integer offset,
                                      @ApiParam(value = "limit", required = true)
                                      @RequestParam(value = "limit", required = true, defaultValue = DEFAULT_LIMIT_SIZE) Integer limit,
                                      HttpServletRequest request, HttpServletResponse response) {
    	return this.businessService.getAllUsers(offset, limit);
    }
    
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a user.", notes = "You have to provide a valid user ID.")
    public
    @ResponseBody
    User getUser(@ApiParam(value = "The ID of a user.", required = true)
    	@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    public void updateUser(@ApiParam(value = "The ID of the existing user.", required = true)
                                 @PathVariable("id") Long id, @RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
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
    
}
