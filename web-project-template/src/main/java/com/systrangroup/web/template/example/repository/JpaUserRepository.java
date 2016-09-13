package com.systrangroup.web.template.example.repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.systrangroup.web.template.example.domain.User;

	public class JpaUserRepository implements JpaCRUDRepository {

	 @PersistenceContext
	 private EntityManager em;
	 
	 public Iterable<User> findAll() {
	  return em.createQuery(
	    "from user", User.class
	   ).getResultList();
	 }

	 public User findByUsername(String name) {
	  return em.createQuery(
	    "from user where name = :name", User.class
	   ).setParameter("name", name).getSingleResult();
	 }

	 @SuppressWarnings("unchecked")
	public User save(User user) {
	  if(user.getId() == null) {
	   em.persist(user);
	  } else {
	   user = em.merge(user);
	  }
	  return user;
	 }

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(User arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends User> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findOne(Long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	}