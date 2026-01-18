package com.chakravyuha.service;

import com.chakravyuha.dto.request.EmployeeRequestDto;
import com.chakravyuha.dto.response.EmployeeResponseDto;

import java.util.List;

/**
 * Service interface for employee management operations.
 * Defines business logic contracts for CRUD operations.
 * 
 * @author Chakravyuha Team
 */
public interface EmployeeService {

	/**
	 * Creates a new employee with personal and department information in a single transaction.
	 * 
	 * @param requestDto Employee request DTO containing personal and department details
	 * @return EmployeeResponseDto containing created employee information
	 * @throws com.chakravyuha.exception.ResourceAlreadyExistsException if employee with same email already exists
	 */
	EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param empId Employee ID
	 * @return EmployeeResponseDto containing employee information
	 * @throws com.chakravyuha.exception.ResourceNotFoundException if employee not found
	 */
	EmployeeResponseDto getEmployeeById(Integer empId);

	/**
	 * Retrieves all employees.
	 * 
	 * @return List of EmployeeResponseDto containing all employees
	 */
	List<EmployeeResponseDto> getAllEmployees();

	/**
	 * Updates an existing employee's personal and department information.
	 * 
	 * @param empId Employee ID to update
	 * @param requestDto Employee request DTO containing updated information
	 * @return EmployeeResponseDto containing updated employee information
	 * @throws com.chakravyuha.exception.ResourceNotFoundException if employee not found
	 */
	EmployeeResponseDto updateEmployee(Integer empId, EmployeeRequestDto requestDto);

	/**
	 * Deletes an employee and all associated department records.
	 * 
	 * @param empId Employee ID to delete
	 * @throws com.chakravyuha.exception.ResourceNotFoundException if employee not found
	 */
	void deleteEmployee(Integer empId);
}
