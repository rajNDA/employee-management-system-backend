package com.ems.ems_backend.service;

import com.ems.ems_backend.entity.Employee;
import com.ems.ems_backend.exception.ResourceNotFoundException;
import com.ems.ems_backend.repository.EmployeeRepository;
import com.ems.ems_backend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

//    @Override
//    public void deleteEmployee(Long id) {
//        repository.deleteById(id);
//    }
@Override
public void deleteEmployee(Long id) {
    if (!repository.existsById(id)) {
        throw new ResourceNotFoundException("Employee not found with id: " + id);
    }
    repository.deleteById(id);
}

//    @Override
//    public Employee getEmployeeById(Long id) {
//        return repository.findById(id).orElse(null);
//    }
@Override
public Employee getEmployeeById(Long id) {
    return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
}

//      @Override
//    public Employee updateEmployee(Long id, Employee employee) {
//
//        Employee existing = repository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        existing.setName(employee.getName());
//        existing.setEmail(employee.getEmail());
//        existing.setDepartment(employee.getDepartment());
//        existing.setSalary(employee.getSalary());
//
//        return repository.save(existing);
//    }


    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        existing.setDepartment(employee.getDepartment());
        existing.setSalary(employee.getSalary());

        return repository.save(existing);
    }

}