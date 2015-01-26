package com.systemsinmotion.orgchart.data;

import java.util.List;

import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;

public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
	
	Employee findById(Integer id);
	
	Employee findByFirstName(String firstName);
	
	Employee findByLastName(String lastName);
	
	List<Employee> findByFirstNameOrLastName(String firstName, String lastName);
	
	Employee findByMiddleInitial(Character middleInitial);
	
	Employee findByEmail(String email);
	
	Employee findBySkypeName(String skypeName);
	
	List<Employee> findByIsManager(Boolean isManager);
	
	List<Employee> findByJobTitleName(String jobTitle);
	
	List<Employee> findByJobTitleId(Integer jobId);
	
	List<Employee> findByDepartmentName(String department);
	
	List<Employee> findByManagerFirstName(String managerFirstName);

	List<Employee> findByDepartment(Department department);
	
	List<Employee> findByDepartmentId(Integer deptId);

	List<Employee> findByManager(Employee manager);
	
	List<Employee> findByDepartmentIdAndJobTitleId(Integer deptId, Integer jobId);
	
	List<Employee> findByFirstNameOrLastNameAndDepartmentId(String firstName, String lastName, Integer deptId);
	
	List<Employee> findByFirstNameOrLastNameAndJobTitleId(String firstName, String lastName, Integer jobId);

	List<Employee> findByFirstNameAndLastNameAndDepartmentIdAndJobTitleId(
			String firstName, String lastName, Integer id, Integer id2);
	
	List<Employee> findByFirstNameOrLastNameAndDepartmentIdAndJobTitleId (
			String firstName, String lastName, Integer deptId, Integer jobId);
}
