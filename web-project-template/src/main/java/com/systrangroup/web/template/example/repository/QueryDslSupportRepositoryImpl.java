package com.systrangroup.web.template.example.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.types.Predicate;
import com.systrangroup.web.template.example.domain.QDept;
import com.systrangroup.web.template.example.domain.QUser;
import com.systrangroup.web.template.example.domain.User;

@Repository
public class QueryDslSupportRepositoryImpl extends QueryDslRepositorySupport implements SearchRepository{

	public QueryDslSupportRepositoryImpl() {
		super(User.class);
	}

	@Override
	public User joinResult(Long id) {
		QUser user = QUser.user;
		QDept dept = QDept.dept;
		Predicate p = QUser.user.dept.deptId.eq(id);
		JPQLQuery query = from(user);
		query.innerJoin(user.dept, dept)
			.where(p);
		return query.singleResult(user);
	}

}
