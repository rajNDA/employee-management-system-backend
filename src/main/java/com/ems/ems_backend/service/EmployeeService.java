package com.ems.ems_backend.service;

import com.ems.ems_backend.entity.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee saveEmployee(Employee employee);

    void deleteEmployee(Long id);

    Employee getEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee employee);
}
