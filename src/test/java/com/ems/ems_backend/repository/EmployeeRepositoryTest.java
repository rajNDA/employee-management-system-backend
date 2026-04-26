package com.ems.ems_backend.repository;


import com.ems.ems_backend.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    void testSaveEmployee() {

        Employee emp = new Employee(null, "Rajesh", "r@gmail.com", "IT", 50000);

        Employee saved = repository.save(emp);

        assertNotNull(saved.getId());
        assertEquals("Rajesh", saved.getName());
    }

    @Test
    void testFindById() {

        Employee emp = new Employee(null, "Suresh", "s@gmail.com", "HR", 40000);
        Employee saved = repository.save(emp);

        Optional<Employee> result = repository.findById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Suresh", result.get().getName());
    }

    @Test
    void testDeleteEmployee() {

        Employee emp = new Employee(null, "Amit", "a@gmail.com", "IT", 45000);
        Employee saved = repository.save(emp);

        repository.deleteById(saved.getId());

        Optional<Employee> result = repository.findById(saved.getId());

        assertFalse(result.isPresent());
    }
}