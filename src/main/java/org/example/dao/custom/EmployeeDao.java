package org.example.dao.custom;

import org.example.dao.CrudDao;
import org.example.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeDao extends CrudDao<EmployeeEntity> {

    List<EmployeeEntity> getAllEmployees();

    EmployeeEntity findEmployeeById(String custId);

    EmployeeEntity findEmployeeByEmail(String email);

    boolean updateEmployee(EmployeeEntity customer);
}
