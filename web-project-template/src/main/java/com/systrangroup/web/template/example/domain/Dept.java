package com.systrangroup.web.template.example.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
public class Dept{
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deptId;
 
	@Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private DeptNameType deptNameType;
}
