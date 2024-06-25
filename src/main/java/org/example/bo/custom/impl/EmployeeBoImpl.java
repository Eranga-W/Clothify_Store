package org.example.bo.custom.impl;

import org.example.bo.custom.EmployeeBo;
import org.example.dao.DaoFactory;
import org.example.dao.custom.EmployeeDao;
import org.example.dto.Employee;
import org.example.entity.EmployeeEntity;
import org.example.entity.ItemEntity;
import org.example.util.DaoType;
import org.modelmapper.ModelMapper;

import java.util.List;

public class EmployeeBoImpl implements EmployeeBo {

    private EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
    @Override
    public boolean saveEmployee(Employee dto) {
        return employeeDao.save(new ModelMapper().map(dto, EmployeeEntity.class));
    }

    @Override
    public boolean deleteById(String id) {
        return employeeDao.delete(id);
    }

    public List<EmployeeEntity> getAllEmployees(){
        return employeeDao.getAllEmployees();
    }

    @Override
    public EmployeeEntity findEmployeeById(String Id) {
        return employeeDao.findEmployeeById(Id);
    }
    public EmployeeEntity findEmployeeByEmail(String email) {
        return employeeDao.findEmployeeByEmail(email);
    }

    @Override
    public boolean updateEmployee(EmployeeEntity employee) {
        return employeeDao.updateEmployee(employee);
    }
}
