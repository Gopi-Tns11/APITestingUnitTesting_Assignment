package com.example.apitesting.repository;

import java.sql.Savepoint;
import java.util.Optional;

import org.hibernate.sql.Delete;

import com.example.apitesting.entity.Employee;

//public interface EmployeeRepository {
//
//	Optional<Employee>findById(Long id);
//	Employee save(Employee employee);
//	void deleteById(Long id);
//}



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apitesting.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);
}
