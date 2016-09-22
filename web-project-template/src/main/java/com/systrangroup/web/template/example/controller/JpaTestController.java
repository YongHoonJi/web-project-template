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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
 
/**
 *  JPA 테스트용. 인증이 필요없음
 */
@Controller
@RequestMapping("/jpa/test")
public class JpaTestController extends AbstractRestHandler{
	
	@Autowired 
	private JpaTestService businessService;
	
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
