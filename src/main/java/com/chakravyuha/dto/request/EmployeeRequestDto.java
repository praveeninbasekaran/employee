package com.chakravyuha.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Request DTO for creating or updating an employee.
 * Contains both personal and department information in a single request.
 * 
 * @author Chakravyuha Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

	/**
	 * Personal information section.
	 */
	@Valid
	@NotNull(message = "Personal information is required")
	private PersonalInfo personalInfo;

	/**
	 * Department information section.
	 */
	@Valid
	@NotNull(message = "Department information is required")
	private DepartmentInfo departmentInfo;

	/**
	 * Inner class representing employee personal information.
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PersonalInfo {

		@NotBlank(message = "First name is required")
		@Size(max = 50, message = "First name must not exceed 50 characters")
		private String firstName;

		@NotBlank(message = "Last name is required")
		@Size(max = 50, message = "Last name must not exceed 50 characters")
		private String lastName;

		@Email(message = "Invalid email format")
		@Size(max = 100, message = "Email must not exceed 100 characters")
		private String personalEmail;

		@Pattern(regexp = "^[0-9]{10,15}$", message = "Mobile number must be 10-15 digits")
		private String mobileNumber;

		private LocalDate dateOfBirth;

		@Pattern(regexp = "^[A-Z]{5}[0-9]{4}[A-Z]{1}$", message = "Invalid PAN card format")
		@Size(max = 10, message = "PAN card number must be 10 characters")
		private String panCardNumber;

		private String currentAddress;

		@Size(max = 100, message = "Emergency contact name must not exceed 100 characters")
		private String emergencyContactName;

		@Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Invalid blood group format")
		@Size(max = 5, message = "Blood group must not exceed 5 characters")
		private String bloodGroup;
	}

	/**
	 * Inner class representing employee department information.
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DepartmentInfo {

		@NotBlank(message = "Department name is required")
		@Size(max = 50, message = "Department name must not exceed 50 characters")
		private String deptName;

		@Size(max = 50, message = "Designation must not exceed 50 characters")
		private String designation;

		private LocalDate joiningDate;

		@NotNull(message = "Annual CTC is required")
		private BigDecimal annualCtcInr;

		private Integer reportingManagerId;

		@Size(max = 100, message = "Work location must not exceed 100 characters")
		private String workLocation;

		@Size(max = 20, message = "Shift timing must not exceed 20 characters")
		private String shiftTiming;

		private Boolean isOnProbation = true;
	}
}
