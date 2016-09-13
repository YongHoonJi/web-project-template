package com.systrangroup.web.template.example.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.systrangroup.web.template.example.domain.ActiveType;
import com.systrangroup.web.template.example.domain.Dept;
import com.systrangroup.web.template.example.domain.DeptNameType;
import com.systrangroup.web.template.example.domain.User;
@Service
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<User> findInactiveUsers() {
		try {
			this.doLogic();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			em.close();
		}
		return this.doLogic();
	}
	
	// commit persistence context before closing transaction
	@Transactional
	private List<User> doLogic(){
		// create a user
		Dept dept = new Dept();
		dept.setDeptNameType(DeptNameType.ARCH);
		
		User newUser = new User();
		newUser.setName("Turing");
		newUser.setActiveType(ActiveType.Y);
		newUser.setAge(99);
		newUser.setDept(dept);
		
		em.persist(newUser);

		System.out.println("queryFromPersistence");
		String queryFromPersistence = "from User u where u.name = 'Turing'";
		em.createQuery(queryFromPersistence).getResultList();
		
		System.out.println("queryFromDB");
		String queryFromDB = "from User u where u.inactive = 'Y'";
		List<User> users = em.createQuery(queryFromDB).getResultList();

		return users;		
	}
}
