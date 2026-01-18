package com.chakravyuha.controller;

import com.chakravyuha.dto.request.EmployeeRequestDto;
import com.chakravyuha.dto.response.ApiResponse;
import com.chakravyuha.dto.response.EmployeeResponseDto;
import com.chakravyuha.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for employee management operations.
 * Provides CRUD endpoints for employee personal and department information.
 * 
 * @author Chakravyuha Team
 */
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

	private static final String EMPLOYEE_CREATED_MESSAGE = "Employee created successfully";
	private static final String EMPLOYEE_RETRIEVED_MESSAGE = "Employee retrieved successfully";
	private static final String ALL_EMPLOYEES_RETRIEVED_MESSAGE = "All employees retrieved successfully";
	private static final String EMPLOYEE_UPDATED_MESSAGE = "Employee updated successfully";
	private static final String EMPLOYEE_DELETED_MESSAGE = "Employee deleted successfully";

	private final EmployeeService employeeService;

	/**
	 * Creates a new employee with personal and department information.
	 * 
	 * @param requestDto Employee request DTO containing personal and department details
	 * @return ResponseEntity containing created employee information
	 */
	@PostMapping
	public ResponseEntity<ApiResponse<EmployeeResponseDto>> createEmployee(
			@Valid @RequestBody EmployeeRequestDto requestDto) {
		log.info("POST /api/v1/employees - Creating new employee");
		EmployeeResponseDto responseDto = employeeService.createEmployee(requestDto);
		ApiResponse<EmployeeResponseDto> response = ApiResponse.success(EMPLOYEE_CREATED_MESSAGE, responseDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param empId Employee ID
	 * @return ResponseEntity containing employee information
	 */
	@GetMapping("/{empId}")
	public ResponseEntity<ApiResponse<EmployeeResponseDto>> getEmployeeById(@PathVariable Integer empId) {
		log.info("GET /api/v1/employees/{} - Retrieving employee", empId);
		EmployeeResponseDto responseDto = employeeService.getEmployeeById(empId);
		ApiResponse<EmployeeResponseDto> response = ApiResponse.success(EMPLOYEE_RETRIEVED_MESSAGE, responseDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves all employees.
	 * 
	 * @return ResponseEntity containing list of all employees
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> getAllEmployees() {
		log.info("GET /api/v1/employees - Retrieving all employees");
		List<EmployeeResponseDto> responseDtoList = employeeService.getAllEmployees();
		ApiResponse<List<EmployeeResponseDto>> response = ApiResponse.success(
				ALL_EMPLOYEES_RETRIEVED_MESSAGE, responseDtoList);
		return ResponseEntity.ok(response);
	}

	/**
	 * Updates an existing employee's information.
	 * 
	 * @param empId Employee ID to update
	 * @param requestDto Employee request DTO containing updated information
	 * @return ResponseEntity containing updated employee information
	 */
	@PutMapping("/{empId}")
	public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployee(
			@PathVariable Integer empId,
			@Valid @RequestBody EmployeeRequestDto requestDto) {
		log.info("PUT /api/v1/employees/{} - Updating employee", empId);
		EmployeeResponseDto responseDto = employeeService.updateEmployee(empId, requestDto);
		ApiResponse<EmployeeResponseDto> response = ApiResponse.success(EMPLOYEE_UPDATED_MESSAGE, responseDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Deletes an employee and all associated department records.
	 * 
	 * @param empId Employee ID to delete
	 * @return ResponseEntity with success message
	 */
	@DeleteMapping("/{empId}")
	public ResponseEntity<ApiResponse<Object>> deleteEmployee(@PathVariable Integer empId) {
		log.info("DELETE /api/v1/employees/{} - Deleting employee", empId);
		employeeService.deleteEmployee(empId);
		ApiResponse<Object> response = ApiResponse.success(EMPLOYEE_DELETED_MESSAGE, null);
		return ResponseEntity.ok(response);
	}
}
