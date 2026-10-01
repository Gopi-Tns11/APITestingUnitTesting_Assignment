package com.example.apitesting.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.apitesting.entity.Employee;
import com.example.apitesting.repository.EmployeeRepository;

@Service
public class EmployeeService {

	public final EmployeeRepository repository;
	
	public EmployeeService(EmployeeRepository repository) {
		this.repository=repository;
	}
	
	public Employee getEmployeeById(Long id) {
		 Optional<Employee> employee = repository.findById(id);
		 if (employee.isPresent()) {
	            return employee.get();
	        }

	        throw new RuntimeException("Employee not found");
	}
	

//    public Employee createEmployee(Employee employee) {
//        return repository.save(employee);
//    }

	
	public Employee createEmployee(Employee employee) {

	    if (employee.getSalary() <= 0) {
	        throw new RuntimeException("Salary must be greater than 0");
	    }

	    return repository.save(employee);
	}
	
//    public void deleteEmployee(Long id) {
//        repository.deleteById(id);
//    }
	
	public void deleteEmployee(Long id) {

	    Optional<Employee> employee = repository.findById(id);

	    if (employee.isPresent()) {
	        repository.deleteById(id);
	    } else {
	        throw new RuntimeException("Employee not found");
	    }
	}
}
