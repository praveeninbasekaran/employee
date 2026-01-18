package com.chakravyuha.mapper;

import com.chakravyuha.dto.request.EmployeeRequestDto;
import com.chakravyuha.dto.response.EmployeeResponseDto;
import com.chakravyuha.entity.EmployeeDepartment;
import com.chakravyuha.entity.EmployeePersonal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * MapStruct mapper interface for converting between Entities and DTOs.
 * Handles bidirectional mapping between EmployeePersonal, EmployeeDepartment and DTOs.
 * 
 * @author Chakravyuha Team
 */
@Mapper(componentModel = "spring", 
		nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

	/**
	 * Maps EmployeeRequestDto to EmployeePersonal entity.
	 * 
	 * @param requestDto Request DTO containing personal information
	 * @return EmployeePersonal entity
	 */
	@Mapping(target = "empId", ignore = true)
	@Mapping(target = "employeeDepartments", ignore = true)
	EmployeePersonal toEmployeePersonal(EmployeeRequestDto.PersonalInfo requestDto);

	/**
	 * Maps EmployeeRequestDto to EmployeeDepartment entity.
	 * 
	 * @param requestDto Request DTO containing department information
	 * @return EmployeeDepartment entity
	 */
	@Mapping(target = "recordId", ignore = true)
	@Mapping(target = "employeePersonal", ignore = true)
	EmployeeDepartment toEmployeeDepartment(EmployeeRequestDto.DepartmentInfo requestDto);

	/**
	 * Updates existing EmployeePersonal entity with values from DTO.
	 * 
	 * @param requestDto Request DTO containing updated personal information
	 * @param entity Existing EmployeePersonal entity to update
	 */
	@Mapping(target = "empId", ignore = true)
	@Mapping(target = "employeeDepartments", ignore = true)
	void updateEmployeePersonal(@MappingTarget EmployeePersonal entity, 
								EmployeeRequestDto.PersonalInfo requestDto);

	/**
	 * Updates existing EmployeeDepartment entity with values from DTO.
	 * 
	 * @param requestDto Request DTO containing updated department information
	 * @param entity Existing EmployeeDepartment entity to update
	 */
	@Mapping(target = "recordId", ignore = true)
	@Mapping(target = "employeePersonal", ignore = true)
	void updateEmployeeDepartment(@MappingTarget EmployeeDepartment entity, 
								  EmployeeRequestDto.DepartmentInfo requestDto);

	/**
	 * Maps EmployeePersonal and EmployeeDepartment entities to EmployeeResponseDto.
	 * 
	 * @param personal EmployeePersonal entity
	 * @param department EmployeeDepartment entity
	 * @return EmployeeResponseDto containing both personal and department information
	 */
	default EmployeeResponseDto toEmployeeResponseDto(EmployeePersonal personal, EmployeeDepartment department) {
		if (personal == null) {
			return null;
		}
		EmployeeResponseDto dto = new EmployeeResponseDto();
		dto.setEmpId(personal.getEmpId());
		dto.setPersonalInfo(toPersonalInfoResponse(personal));
		if (department != null) {
			dto.setDepartmentInfo(toDepartmentInfoResponse(department));
		}
		return dto;
	}

	/**
	 * Maps EmployeePersonal entity to PersonalInfoResponse.
	 * 
	 * @param personal EmployeePersonal entity
	 * @return PersonalInfoResponse DTO
	 */
	EmployeeResponseDto.PersonalInfoResponse toPersonalInfoResponse(EmployeePersonal personal);

	/**
	 * Maps EmployeeDepartment entity to DepartmentInfoResponse.
	 * 
	 * @param department EmployeeDepartment entity
	 * @return DepartmentInfoResponse DTO
	 */
	@Mapping(target = "recordId", source = "recordId")
	EmployeeResponseDto.DepartmentInfoResponse toDepartmentInfoResponse(EmployeeDepartment department);
}
