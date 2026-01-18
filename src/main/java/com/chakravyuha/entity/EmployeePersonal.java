package com.chakravyuha.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Entity class representing employee personal information.
 * This is the core identity data table in the human_resources schema.
 * 
 * @author Chakravyuha Team
 */
@Entity
@Table(name = "employee_personal", schema = "human_resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePersonal {

	/**
	 * Primary key - Auto-generated employee ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private Integer empId;

	/**
	 * Employee's first name.
	 */
	@Column(name = "first_name", nullable = false, length = 50)
	private String firstName;

	/**
	 * Employee's last name.
	 */
	@Column(name = "last_name", nullable = false, length = 50)
	private String lastName;

	/**
	 * Unique personal email address.
	 */
	@Column(name = "personal_email", unique = true, length = 100)
	private String personalEmail;

	/**
	 * Mobile contact number.
	 */
	@Column(name = "mobile_number", length = 15)
	private String mobileNumber;

	/**
	 * Date of birth.
	 */
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	/**
	 * PAN card number (10 characters).
	 */
	@Column(name = "pan_card_number", length = 10)
	private String panCardNumber;

	/**
	 * Current residential address.
	 */
	@Column(name = "current_address", columnDefinition = "TEXT")
	private String currentAddress;

	/**
	 * Emergency contact person's name.
	 */
	@Column(name = "emergency_contact_name", length = 100)
	private String emergencyContactName;

	/**
	 * Blood group information.
	 */
	@Column(name = "blood_group", length = 5)
	private String bloodGroup;

	/**
	 * One-to-many relationship with employee departments.
	 * An employee can have multiple department records.
	 */
	@OneToMany(mappedBy = "employeePersonal")
	private List<EmployeeDepartment> employeeDepartments;
}
