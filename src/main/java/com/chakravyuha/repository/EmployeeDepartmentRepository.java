package com.chakravyuha.repository;

import com.chakravyuha.entity.EmployeeDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for EmployeeDepartment entity.
 * Provides CRUD operations and custom query methods.
 * 
 * @author Chakravyuha Team
 */
@Repository
public interface EmployeeDepartmentRepository extends JpaRepository<EmployeeDepartment, Integer> {

	/**
	 * Finds all department records for a given employee ID.
	 * 
	 * @param empId Employee ID
	 * @return List of EmployeeDepartment records
	 */
	List<EmployeeDepartment> findByEmployeePersonal_EmpId(Integer empId);

	/**
	 * Finds the latest department record for a given employee ID.
	 * 
	 * @param empId Employee ID
	 * @return Optional containing the latest EmployeeDepartment record
	 */
	Optional<EmployeeDepartment> findFirstByEmployeePersonal_EmpIdOrderByRecordIdDesc(Integer empId);
}
