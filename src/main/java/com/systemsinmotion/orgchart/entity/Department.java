package com.systemsinmotion.orgchart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "DEPARTMENT")
public class Department extends BaseEntity {

	@Column(name = "NAME", nullable = false, length = 50)
	@NotNull
	@NotEmpty
	@Size(min = 1, max = 45)
	private String name;

	@ManyToOne
	@JoinColumn(name = "PARENT_DEPARTMENT_ID", referencedColumnName = "ID")
	private Department parentDepartment;
	
	@Column(name = "MANAGER_ID")
	private Integer managerId;

	public String getName() {
		return this.name;
	}

	public Department getParentDepartment() {
		return this.parentDepartment;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentDepartment(Department department) {
		this.parentDepartment = department;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

}