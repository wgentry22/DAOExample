package com.revature.service;

import java.util.List;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.model.Employee;

public class EmployeeService {

	private static EmployeeDao dao = EmployeeDaoImpl.getInstance();
	
	private static EmployeeService instance;
	
	private EmployeeService() {
		System.out.println("Instantiating EmployeeService");
	}
	
	public static EmployeeService getInstance() {
		if (instance == null) {
			instance = new EmployeeService();
		}
		return instance;
	}
	
	public List<Employee> getAllEmployees() {
		System.out.println("Calling getAllEmployees in EmployeeService");
		return dao.getAllEmployees();
	}
	
	public boolean insertEmployee(Employee employee) {
		if (dao.insertEmployee(employee)) {
			System.out.println("Employee successfully inserted");
			return true;
		} 
		return false;
	}
	
	public Employee getEmployee(String email) {
		return dao.getEmployee(email);
	}
	
	public boolean deleteEmployee(String email) {
		return dao.deleteEmployee(email);
	}
	
	public boolean updateEmployee(Employee employee) {
		return dao.updateEmployee(employee);
	}
}
