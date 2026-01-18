package com.chakravyuha.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Response DTO for employee information.
 * Contains both personal and department details for API responses.
 * 
 * @author Chakravyuha Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {

	/**
	 * Employee ID (primary key).
	 */
	private Integer empId;

	/**
	 * Personal information section.
	 */
	private PersonalInfoResponse personalInfo;

	/**
	 * Department information section.
	 */
	private DepartmentInfoResponse departmentInfo;

	/**
	 * Inner class representing personal information in response.
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PersonalInfoResponse {
		private String firstName;
		private String lastName;
		private String personalEmail;
		private String mobileNumber;
		private LocalDate dateOfBirth;
		private String panCardNumber;
		private String currentAddress;
		private String emergencyContactName;
		private String bloodGroup;
	}

	/**
	 * Inner class representing department information in response.
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class DepartmentInfoResponse {
		private Integer recordId;
		private String deptName;
		private String designation;
		private LocalDate joiningDate;
		private BigDecimal annualCtcInr;
		private Integer reportingManagerId;
		private String workLocation;
		private String shiftTiming;
		private Boolean isOnProbation;
	}
}
