package com.chakravyuha.repository;

import com.chakravyuha.entity.EmployeePersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for EmployeePersonal entity.
 * Provides CRUD operations and custom query methods.
 * 
 * @author Chakravyuha Team
 */
@Repository
public interface EmployeePersonalRepository extends JpaRepository<EmployeePersonal, Integer> {

	/**
	 * Finds an employee by personal email.
	 * 
	 * @param personalEmail Email address to search for
	 * @return Optional containing EmployeePersonal if found
	 */
	Optional<EmployeePersonal> findByPersonalEmail(String personalEmail);

	/**
	 * Checks if an employee exists with the given email.
	 * 
	 * @param personalEmail Email address to check
	 * @return true if employee exists, false otherwise
	 */
	boolean existsByPersonalEmail(String personalEmail);
}
