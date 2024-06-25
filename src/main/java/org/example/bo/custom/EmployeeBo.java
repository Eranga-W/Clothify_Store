package org.example.bo.custom;

import org.example.bo.SuperBo;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.entity.ItemEntity;

import java.util.List;

public interface EmployeeBo extends SuperBo{
    boolean saveEmployee(Employee dto);
    boolean deleteById(String id);

    public List<EmployeeEntity> getAllEmployees();

    EmployeeEntity findEmployeeById(String text);
    public EmployeeEntity findEmployeeByEmail(String email);

    boolean updateEmployee(EmployeeEntity customer);
}
