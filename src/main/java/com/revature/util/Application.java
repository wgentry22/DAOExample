package com.revature.util;

import java.util.List;

import com.revature.model.Employee;
import com.revature.service.EmployeeService;

public class Application {

	public static void main(String[] args) {
//		Employee vince = new Employee(3, "Vince", "Mark", "vince@test.com");
//		System.out.println(EmployeeService.getInstance().insertEmployee(vince));
		Employee employee = new Employee(4, "Michael", "B", "bdm@test.com");
		EmployeeService.getInstance().insertEmployee(employee);
		List<Employee> employees = EmployeeService.getInstance().getAllEmployees();
		for (Employee e : employees) {
			System.out.println("Id: " + e.getId());
			System.out.println("First Name: " + e.getFirstName());
			System.out.println("Last Name: " + e.getLastName());
			System.out.println("Email: " + e.getEmail());
			System.out.println();
		}
	}
}
