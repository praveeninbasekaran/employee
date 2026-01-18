package com.chakravyuha.service.impl;

import com.chakravyuha.dto.request.EmployeeRequestDto;
import com.chakravyuha.dto.response.EmployeeResponseDto;
import com.chakravyuha.entity.EmployeeDepartment;
import com.chakravyuha.entity.EmployeePersonal;
import com.chakravyuha.exception.ResourceAlreadyExistsException;
import com.chakravyuha.exception.ResourceNotFoundException;
import com.chakravyuha.mapper.EmployeeMapper;
import com.chakravyuha.repository.EmployeeDepartmentRepository;
import com.chakravyuha.repository.EmployeePersonalRepository;
import com.chakravyuha.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for employee management operations.
 * Handles business logic for CRUD operations with proper transaction management.
 * 
 * @author Chakravyuha Team
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	private static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee not found with ID: ";
	private static final String EMAIL_ALREADY_EXISTS_MESSAGE = "Employee with email already exists: ";

	private final EmployeePersonalRepository employeePersonalRepository;
	private final EmployeeDepartmentRepository employeeDepartmentRepository;
	private final EmployeeMapper employeeMapper;

	/**
	 * Creates a new employee with personal and department information in a single transaction.
	 * Validates email uniqueness before creating the employee.
	 * 
	 * @param requestDto Employee request DTO containing personal and department details
	 * @return EmployeeResponseDto containing created employee information
	 * @throws ResourceAlreadyExistsException if employee with same email already exists
	 */
	@Override
	public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
		log.info("Creating new employee with email: {}", requestDto.getPersonalInfo().getPersonalEmail());

		// Validate email uniqueness
		if (requestDto.getPersonalInfo().getPersonalEmail() != null
				&& employeePersonalRepository.existsByPersonalEmail(
						requestDto.getPersonalInfo().getPersonalEmail())) {
			throw new ResourceAlreadyExistsException(
					EMAIL_ALREADY_EXISTS_MESSAGE + requestDto.getPersonalInfo().getPersonalEmail());
		}

		// Map DTO to entities
		EmployeePersonal employeePersonal = employeeMapper.toEmployeePersonal(requestDto.getPersonalInfo());
		EmployeeDepartment employeeDepartment = employeeMapper.toEmployeeDepartment(requestDto.getDepartmentInfo());

		// Save personal information first
		EmployeePersonal savedPersonal = employeePersonalRepository.save(employeePersonal);
		log.debug("Saved employee personal information with ID: {}", savedPersonal.getEmpId());

		// Link department to personal and save
		employeeDepartment.setEmployeePersonal(savedPersonal);
		EmployeeDepartment savedDepartment = employeeDepartmentRepository.save(employeeDepartment);
		log.debug("Saved employee department information with record ID: {}", savedDepartment.getRecordId());

		log.info("Successfully created employee with ID: {}", savedPersonal.getEmpId());
		return employeeMapper.toEmployeeResponseDto(savedPersonal, savedDepartment);
	}

	/**
	 * Retrieves an employee by their ID along with their latest department information.
	 * 
	 * @param empId Employee ID
	 * @return EmployeeResponseDto containing employee information
	 * @throws ResourceNotFoundException if employee not found
	 */
	@Override
	@Transactional(readOnly = true)
	public EmployeeResponseDto getEmployeeById(Integer empId) {
		log.info("Retrieving employee with ID: {}", empId);

		EmployeePersonal employeePersonal = employeePersonalRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE + empId));

		// Get the latest department record
		EmployeeDepartment employeeDepartment = employeeDepartmentRepository
				.findFirstByEmployeePersonal_EmpIdOrderByRecordIdDesc(empId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Department information not found for employee ID: " + empId));

		log.debug("Successfully retrieved employee with ID: {}", empId);
		return employeeMapper.toEmployeeResponseDto(employeePersonal, employeeDepartment);
	}

	/**
	 * Retrieves all employees with their latest department information.
	 * 
	 * @return List of EmployeeResponseDto containing all employees
	 */
	@Override
	@Transactional(readOnly = true)
	public List<EmployeeResponseDto> getAllEmployees() {
		log.info("Retrieving all employees");

		List<EmployeePersonal> allEmployees = employeePersonalRepository.findAll();

		List<EmployeeResponseDto> responseList = allEmployees.stream().map(employeePersonal -> {
			EmployeeDepartment department = employeeDepartmentRepository
					.findFirstByEmployeePersonal_EmpIdOrderByRecordIdDesc(employeePersonal.getEmpId())
					.orElse(null);
			return employeeMapper.toEmployeeResponseDto(employeePersonal, department);
		}).collect(Collectors.toList());

		log.debug("Retrieved {} employees", responseList.size());
		return responseList;
	}

	/**
	 * Updates an existing employee's personal and department information.
	 * Updates both personal and department records in a single transaction.
	 * 
	 * @param empId Employee ID to update
	 * @param requestDto Employee request DTO containing updated information
	 * @return EmployeeResponseDto containing updated employee information
	 * @throws ResourceNotFoundException if employee not found
	 */
	@Override
	public EmployeeResponseDto updateEmployee(Integer empId, EmployeeRequestDto requestDto) {
		log.info("Updating employee with ID: {}", empId);

		// Find existing employee
		EmployeePersonal employeePersonal = employeePersonalRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE + empId));

		// Validate email uniqueness if email is being changed
		if (requestDto.getPersonalInfo().getPersonalEmail() != null
				&& !requestDto.getPersonalInfo().getPersonalEmail()
						.equals(employeePersonal.getPersonalEmail())
				&& employeePersonalRepository.existsByPersonalEmail(
						requestDto.getPersonalInfo().getPersonalEmail())) {
			throw new ResourceAlreadyExistsException(
					EMAIL_ALREADY_EXISTS_MESSAGE + requestDto.getPersonalInfo().getPersonalEmail());
		}

		// Update personal information
		employeeMapper.updateEmployeePersonal(employeePersonal, requestDto.getPersonalInfo());
		EmployeePersonal updatedPersonal = employeePersonalRepository.save(employeePersonal);
		log.debug("Updated employee personal information for ID: {}", empId);

		// Get or create department record
		EmployeeDepartment employeeDepartment = employeeDepartmentRepository
				.findFirstByEmployeePersonal_EmpIdOrderByRecordIdDesc(empId)
				.orElseGet(() -> {
					EmployeeDepartment newDept = employeeMapper
							.toEmployeeDepartment(requestDto.getDepartmentInfo());
					newDept.setEmployeePersonal(updatedPersonal);
					return newDept;
				});

		// Update department information
		employeeMapper.updateEmployeeDepartment(employeeDepartment, requestDto.getDepartmentInfo());
		employeeDepartment.setEmployeePersonal(updatedPersonal);
		EmployeeDepartment updatedDepartment = employeeDepartmentRepository.save(employeeDepartment);
		log.debug("Updated employee department information for ID: {}", empId);

		log.info("Successfully updated employee with ID: {}", empId);
		return employeeMapper.toEmployeeResponseDto(updatedPersonal, updatedDepartment);
	}

	/**
	 * Deletes an employee and all associated department records.
	 * Cascades deletion to department records.
	 * 
	 * @param empId Employee ID to delete
	 * @throws ResourceNotFoundException if employee not found
	 */
	@Override
	public void deleteEmployee(Integer empId) {
		log.info("Deleting employee with ID: {}", empId);

		EmployeePersonal employeePersonal = employeePersonalRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND_MESSAGE + empId));

		// Delete all department records first (to maintain referential integrity)
		List<EmployeeDepartment> departments = employeeDepartmentRepository
				.findByEmployeePersonal_EmpId(empId);
		employeeDepartmentRepository.deleteAll(departments);
		log.debug("Deleted {} department records for employee ID: {}", departments.size(), empId);

		// Delete personal record
		employeePersonalRepository.delete(employeePersonal);
		log.info("Successfully deleted employee with ID: {}", empId);
	}
}
