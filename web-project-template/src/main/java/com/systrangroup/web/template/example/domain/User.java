package com.systrangroup.web.template.example.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.ToString;
 
@Entity
@Data
@NamedQueries({
    @NamedQuery(
    		name="User.findByName",
    		query="from User u where u.name = ?"
                ),
    @NamedQuery(name="User.findByNameAndAge",
                query="from User u where u.name = ? and age = ?"),
}) 
@ToString(callSuper=false, includeFieldNames=true)
public class User{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
    @Column(name = "name", nullable = false)
    private String name;
 
    @Column(name = "age", nullable = false)
    private Integer age;
    
    @Enumerated(EnumType.STRING)
    private ActiveType activeType;
    
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="DEPT_ID")
	private Dept dept;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate=new Date();
}