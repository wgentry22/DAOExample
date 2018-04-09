package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Employee;
import com.revature.util.ConnectionUtil;
import com.revature.util.ConnectionWithPropertiesUtil;

public class EmployeeDaoImpl implements EmployeeDao {

	private static EmployeeDaoImpl instance;
	
	private EmployeeDaoImpl() {
		System.out.println("Instantiating EmployeeDaoImpl");
	}
	
	public static EmployeeDaoImpl getInstance() {
		if (instance == null) {
			instance = new EmployeeDaoImpl();
		}
		return instance;
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		try (Connection conn = ConnectionWithPropertiesUtil.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees");
			ResultSet rs = stmt.executeQuery();
			System.out.println("Employees are being populated to Result Set from EmployeeDaoImpl#getAllEmployees");
			while (rs.next()) {
				employees.add(new Employee(rs.getInt("ID"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getString("EMAIL")));
			}
			System.out.println(employees.size() + " employees in ResultSet");
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
		return employees;
	}

	@Override
	public boolean insertEmployee(Employee employee) {
		try (Connection conn = ConnectionUtil.getConnection()) {
			int index = 0;
			CallableStatement stmt = conn.prepareCall("{CALL INSERT_EMPLOYEE(?, ?, ?, ?)}");
			stmt.setInt(++index, employee.getId());
			stmt.setString(++index, employee.getFirstName());
			stmt.setString(++index, employee.getLastName());
			stmt.setString(++index, employee.getEmail());
			if (stmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
		
		return false;
	}

	@Override
	public Employee getEmployee(String email) {
		Employee e = null;
		int index = 0;
		try (Connection conn = ConnectionWithPropertiesUtil.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM employees WHERE email = ?");
			stmt.setString(++index, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setFirstName(rs.getString("FIRST_NAME"));
				e.setLastName(rs.getString("LAST_NAME"));
				e.setEmail("EMAIL");
			}
		} catch (SQLException sqle) {
			sqle.getMessage();
			System.err.println("SQL State: " + sqle.getSQLState());
			System.err.println("Error Code: " + sqle.getErrorCode());
		}
		return e;
	}

	@Override
	public boolean deleteEmployee(String email) {
		int index = 0;
		try (Connection conn = ConnectionWithPropertiesUtil.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM employees WHERE email = ?");
			stmt.setString(++index, email);
			return stmt.executeUpdate() > 0;
		} catch (SQLException sqle) {
			sqle.getMessage();
			System.err.println("SQL State: " + sqle.getSQLState());
			System.err.println("Error Code: " + sqle.getErrorCode());
		}
		return false;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		int index = 0;
		try (Connection conn = ConnectionWithPropertiesUtil.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement("UPDATE employees SET FIRST_NAME = ? , LAST_NAME = ? WHERE EMAIL + ?");
			stmt.setString(++index, employee.getFirstName());
			stmt.setString(++index, employee.getLastName());
			stmt.setString(++index, employee.getEmail());
			return stmt.executeUpdate() > 0;
		} catch (SQLException sqle) {
			sqle.getMessage();
			System.err.println("SQL State: " + sqle.getSQLState());
			System.err.println("Error Code: " + sqle.getErrorCode());
		}
		return false;
	}

}
