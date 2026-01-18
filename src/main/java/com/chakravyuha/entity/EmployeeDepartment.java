package com.chakravyuha.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity class representing employee department and employment details.
 * Contains professional and financial information including annual CTC in INR.
 * 
 * @author Chakravyuha Team
 */
@Entity
@Table(name = "employee_departments", schema = "human_resources")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDepartment {

	/**
	 * Primary key - Auto-generated record ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "record_id")
	private Integer recordId;

	/**
	 * Foreign key reference to employee_personal table.
	 */
	@ManyToOne
	@JoinColumn(name = "emp_id", referencedColumnName = "emp_id", 
				foreignKey = @ForeignKey(name = "fk_emp_dept_emp_id"))
	private EmployeePersonal employeePersonal;

	/**
	 * Department name.
	 */
	@Column(name = "dept_name", nullable = false, length = 50)
	private String deptName;

	/**
	 * Employee designation/title.
	 */
	@Column(name = "designation", length = 50)
	private String designation;

	/**
	 * Date of joining the organization.
	 */
	@Column(name = "joining_date")
	private LocalDate joiningDate;

	/**
	 * Annual Cost to Company in Indian Rupees.
	 * This is the primary target for financial lineage tracing.
	 */
	@Column(name = "annual_ctc_inr", precision = 15, scale = 2)
	private BigDecimal annualCtcInr;

	/**
	 * ID of the reporting manager (self-referencing).
	 */
	@Column(name = "reporting_manager_id")
	private Integer reportingManagerId;

	/**
	 * Work location/office address.
	 */
	@Column(name = "work_location", length = 100)
	private String workLocation;

	/**
	 * Shift timing information.
	 */
	@Column(name = "shift_timing", length = 20)
	private String shiftTiming;

	/**
	 * Probation status flag.
	 * Default value is true (on probation).
	 */
	@Column(name = "is_on_probation", nullable = false)
	private Boolean isOnProbation = true;
}
