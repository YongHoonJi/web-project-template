package com.systrangroup.web.template.example.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Dept{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deptId;
 
	@Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private DeptNameType deptNameType;

	public Long getId() {
		return deptId;
	}

	public void setId(Long id) {
		this.deptId = id;
	}

	public DeptNameType getDeptNameType() {
		return deptNameType;
	}

	public void setDeptNameType(DeptNameType deptNameType) {
		this.deptNameType = deptNameType;
	}

}
