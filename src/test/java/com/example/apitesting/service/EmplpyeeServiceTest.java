package com.example.apitesting.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.apitesting.entity.Employee;
import com.example.apitesting.repository.EmployeeRepository;

public class EmplpyeeServiceTest {

	@Mock
	private EmployeeRepository repository;
	private EmployeeService service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new EmployeeService(repository);
	}

	@Test
	void shouldReturnEmployeeWhenEmployeeExist() {
		// Arrange
		Employee employee = new Employee(1L, "Gopi", "QA Ebginner", 50000.00);
		when(repository.findById(1L)).thenReturn(Optional.of(employee));

		// Act
		Employee result = service.getEmployeeById(1L);

		// Assert
		assertEquals(1l, result.getId());
		assertEquals("Gopi", result.getName());

	}

	@Test
	void shouldThrowExceptionWhenEmployeeDoesNotExist() {

		// Arrange
		when(repository.findById(100L)).thenReturn(Optional.empty());

		// Act
		try {
			service.getEmployeeById(100L);

		} catch (RuntimeException exception) {

			// Assert
			assertEquals("Employee not found", exception.getMessage());
		}

	}

	@Test
	void shouldCreateEmployee() {

		Employee employee = new Employee(2L, "Raju", "DataBase", 45000.00);
		when(repository.save(employee)).thenReturn(employee);
		

		Employee result = service.createEmployee(employee);
		assertNotNull(result);
		assertEquals(2L, result.getId());
		assertEquals("Raju", result.getName());
		assertEquals("DataBase", result.getDepartment());
		assertEquals(45000.00, result.getSalary());
		verify(repository).save(employee);

	}

	@Test
	void shouldRejectEmployeeWhenSalaryIsZero() {

		Employee employee = new Employee(3L, "Ravi", "IT", 0);

		RuntimeException exception = null;
		try {
			service.createEmployee(employee);

		} catch (RuntimeException e) {
			exception = e;
		}
		assertEquals("Salary must be greater than 0", exception.getMessage());
	}

	
	@Test
	void shouldThrowExceptionWhenDeletingNonExistingEmployee() {

		Long employeeId = 100L;

		when(repository.findById(employeeId)).thenReturn(Optional.empty());

		RuntimeException exception = null;

		try {
			service.deleteEmployee(employeeId);

		} catch (RuntimeException e) {
			exception = e;
		}

		assertEquals("Employee not found", exception.getMessage());
		verify(repository, never()).deleteById(employeeId);
	}

}
