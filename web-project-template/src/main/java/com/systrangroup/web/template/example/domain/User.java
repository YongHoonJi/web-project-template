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

import com.google.common.base.MoreObjects;
 
@Entity
@NamedQueries({
    @NamedQuery(
    		name="User.findByName",
    		query="from User u where u.name = ?"
                ),
    @NamedQuery(name="User.findByNameAndAge",
                query="from User u where u.name = ? and age = ?"),
}) 
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
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate=new Date();
	
	
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Integer getAge() {
        return age;
    }
 
    public void setAge(Integer age) {
        this.age = age;
    }


	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public ActiveType getActiveType() {
		return activeType;
	}

	public void setActiveType(ActiveType activeType) {
		this.activeType = activeType;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String toString(){
		return MoreObjects.toStringHelper(this)
				.add("user name", this.name)
				.add("user age", this.age)
				.add("dept name", this.dept.toString())
				.add("created date", this.createdDate)
				.toString();
	}
}