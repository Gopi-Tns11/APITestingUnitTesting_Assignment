package com.example.apitesting.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.apitesting.entity.Employee;
import com.example.apitesting.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EmployeeService service;

	@Test
	void shouldGetEmployee() throws Exception {

		// Arrange
		Employee employee = new Employee(1L, "Gopi", "IT", 50000);

		when(service.getEmployeeById(1L)).thenReturn(employee);

		// Act + Assert
		mockMvc.perform(get("/api/employees/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Gopi")).andExpect(jsonPath("$.department").value("IT"));
	}

	@Test
	void shouldCreateEmployee() throws Exception {

		// Arrange
		Employee employee = new Employee(2L, "Rahul", "HR", 40000);

		when(service.createEmployee(org.mockito.ArgumentMatchers.any(Employee.class))).thenReturn(employee);

		// Act + Assert
		mockMvc.perform(post("/api/employees").contentType("application/json").content("""
				{
				    "id": 2,
				    "name": "Rahul",
				    "department": "HR",
				    "salary": 40000
				}
				""")).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Rahul")).andExpect(jsonPath("$.department").value("HR"));
	}

//	@Test
//	void shouldDeleteEmployee() throws Exception {
//
//		// Arrange
//		Long employeeId = 2L;
//
//		// Act + Assert
//		mockMvc.perform(delete("/api/employees/" + employeeId)).andExpect(status().isNoContent());
//	}
	
	@Test
	void shouldDeleteEmployee() throws Exception {

	    // Arrange
	    Long employeeId = 2L;

	    // Act + Assert
	    mockMvc.perform(delete("/api/employees/" + employeeId))
	            .andExpect(status().isNoContent());

	    verify(service).deleteEmployee(employeeId);
	}
}