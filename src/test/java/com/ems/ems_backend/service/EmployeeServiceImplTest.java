package com.ems.ems_backend.service;

import com.ems.ems_backend.entity.Employee;
import com.ems.ems_backend.repository.EmployeeRepository;
//import com.ems.ems_backend.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeServiceImpl service;

    @Test
    void testGetAllEmployees() {
        when(repository.findAll()).thenReturn(List.of(new Employee()));

        List<Employee> result = service.getAllEmployees();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById() {
        Employee emp = new Employee(1L, "Rajesh", "r@gmail.com", "IT", 50000);

        when(repository.findById(1L)).thenReturn(Optional.of(emp));

        Employee result = service.getEmployeeById(1L);

        assertEquals("Rajesh", result.getName());
    }

    @Test
    void testSaveEmployee() {
        Employee emp = new Employee();

        when(repository.save(emp)).thenReturn(emp);

        Employee result = service.saveEmployee(emp);

        assertNotNull(result);
    }

    @Test
    void testDeleteEmployee() {

        // ✅ Mock existence check
        when(repository.existsById(1L)).thenReturn(true);

        // ✅ Mock delete
        doNothing().when(repository).deleteById(1L);

        // Call service
        service.deleteEmployee(1L);

        // Verify
        verify(repository, times(1)).deleteById(1L);
    }
}