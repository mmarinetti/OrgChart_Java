package com.systemsinmotion.orgchart.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.systemsinmotion.orgchart.Entities;
import com.systemsinmotion.orgchart.config.JPAConfig;
import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;
import com.systemsinmotion.orgchart.entity.JobTitle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JPAConfig.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class EmployeeRepositoryTest {

	private static final String NOT_PRESENT_VALUE = "XXX";
	private static final Integer NOT_PRESENT_ID = -666;
	private Department department = new Department();
	private JobTitle jobTitle = new JobTitle();
	private Employee employee;
	private Employee manager;

	@Autowired
	EmployeeRepository empRepo;

	@Autowired
	DepartmentRepository deptRepo;
	
	@Autowired
	JobTitleRepository jobTitleRepo;

	@After
	public void after() {
		this.empRepo.delete(this.employee);
		this.deptRepo.delete(this.department);

		if (null != this.manager) {
			this.empRepo.delete(this.manager);
		}
	}

	@Before
	public void before() throws Exception {
		this.department = Entities.department();
		this.deptRepo.saveAndFlush(this.department);

		this.employee = Entities.employee();
		this.employee.setDepartment(this.department);
		this.employee.setId(this.empRepo.save(this.employee).getId());
		
		this.jobTitle = Entities.jobTitle();
		this.employee.setJobTitle(this.jobTitle);
		this.jobTitleRepo.saveAndFlush(jobTitle);
	}

	@Test
	public void testInstantiation() {
		assertNotNull(deptRepo);
	}
	
	private void createManager() {
		this.manager = Entities.manager();
		this.empRepo.save(this.manager);
	}

	@Test
	public void findAll() throws Exception {
		List<Employee> emps = this.empRepo.findAll();
		assertNotNull(emps);
		assertTrue(0 < emps.size());
	}

	@Test
	public void findByDepartment() throws Exception {
		List<Employee> emps = this.empRepo.findByDepartment(this.employee.getDepartment());
		assertNotNull("Expecting a non-null list of Employees but was null", emps);
		Employee emp = emps.get(0);
		assertEquals(this.employee.getFirstName(), emp.getFirstName());
		assertEquals(this.employee.getLastName(), emp.getLastName());
		assertEquals(this.employee.getEmail(), emp.getEmail());
	}

	@Test
	public void findByDepartment_null() throws Exception {
		List<Employee> emps = this.empRepo.findByDepartment(null);
		//assertNull("Expecting a null list of Employees but was non-null", emps);
		assertNotNull(emps);
		assertTrue(emps.isEmpty());
	}

	@Test
	public void findByEmail() throws Exception {
		Employee emp = this.empRepo.findByEmail(this.employee.getEmail());
		assertNotNull("Expecting a non-null Employee but was null", emp);
		assertEquals(this.employee.getFirstName(), emp.getFirstName());
		assertEquals(this.employee.getLastName(), emp.getLastName());
		assertEquals(this.employee.getEmail(), emp.getEmail());
	}

	@Test
	public void findByEmail_null() throws Exception {
		Employee emp = this.empRepo.findByEmail(null);
		assertNull("Expecting a null Employee but was non-null", emp);
	}

	@Test
	public void findByEmailTest_XXX() throws Exception {
		Employee emp = this.empRepo.findByEmail(NOT_PRESENT_VALUE);
		assertNull("Expecting a null Employee but was non-null", emp);
	}

	@Test
	public void findById() throws Exception {
		Employee emp = this.empRepo.findById(this.employee.getId());
		assertNotNull("Expecting a non-null Employee but was null", emp);
		assertEquals(this.employee.getFirstName(), emp.getFirstName());
		assertEquals(this.employee.getLastName(), emp.getLastName());
		assertEquals(this.employee.getEmail(), emp.getEmail());
	}

	@Test
	public void findById_null() throws Exception {
		Employee emp = this.empRepo.findById(null);
		assertNull("Expecting a null Employee but was non-null", emp);
	}

	@Test
	public void findById_XXX() throws Exception {
		Employee emp = this.empRepo.findById(NOT_PRESENT_ID);
		assertNull("Expecting a null Employee but was non-null", emp);
	}

	@Test
	public void findByManagerId() throws Exception {
		createManager();

		this.employee.setManager(this.manager);
		this.empRepo.save(this.employee);

		List<Employee> emps = this.empRepo.findByManager(this.employee.getManager());
		assertNotNull("Expecting a non-null Employee but was null", emps);
		assertTrue("Expecting at least one employee found for manager but none was found", emps.size() > 0);
		Employee emp = emps.get(0);
		assertEquals(this.employee.getFirstName(), emp.getFirstName());
		assertEquals(this.employee.getLastName(), emp.getLastName());
		assertEquals(this.employee.getEmail(), emp.getEmail());
	}

	@Test
	public void findByManagerId_empty() throws Exception {
		Employee emp = Entities.employee();
		emp.setDepartment(this.department);
		this.empRepo.saveAndFlush(emp);
		List<Employee> emps = this.empRepo.findByManager(emp);
		//assertNull(emps);
		assertNotNull(emps);
		assertTrue(emps.isEmpty());
	}

//	@Test
//	public void findByManagerId_null() throws Exception {
//		List<Employee> emps = this.empRepo.findByManager(null);
//		assertNull(emps);
//		//assertTrue(emps.isEmpty());
//	}

	@Test
	public void findByFirstNameAndLastNameAndDepartmentAndJobTitle() throws Exception {
		String firstName = employee.getFirstName();
		String lastName = employee.getLastName();
		Department dept = employee.getDepartment();
		Integer deptId = dept.getId();
		JobTitle jobTitle = employee.getJobTitle();
		Integer jobTitleId = jobTitle.getId();
		
		List<Employee> emp = this.empRepo.findByFirstNameAndLastNameAndDepartmentIdAndJobTitleId(firstName, lastName, deptId, jobTitleId);
		assertNotNull("Expecting a non-null Employee but was null", emp);
		assertTrue(emp.size()==1);
		assertEquals(this.employee.getFirstName(), emp.get(0).getFirstName());
		assertEquals(this.employee.getLastName(), emp.get(0).getLastName());
		assertEquals(this.employee.getDepartment(), emp.get(0).getDepartment());
		assertEquals(this.employee.getJobTitle(), emp.get(0).getJobTitle());
	}
}
