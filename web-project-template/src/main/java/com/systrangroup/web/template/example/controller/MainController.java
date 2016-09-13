package com.systrangroup.web.template.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.systrangroup.web.template.example.domain.User;
import com.systrangroup.web.template.example.service.BusinessService;
 
@Controller
@RequestMapping("/")
public class MainController {
	@Autowired private BusinessService businessService;
	
    @RequestMapping
    public @ResponseBody ModelAndView index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        String str = "Hello World!";
        mav.addObject("message", str);
        return mav;    	
    }
    
    /**
     * Find all users via JPA repository - check persistence area
     * @return
     */
    @RequestMapping("/findAllUser")
    public @ResponseBody List<User> findAllUser() {
        return this.businessService.findAllUser();
    }
    
    /** 
     * save a user via CRUD(create, read, update, delete) repository
     * @return
     */
    @RequestMapping("/saveUser")
    public @ResponseBody void saveUser() {
        this.businessService.saveUser();
    } 
    
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
