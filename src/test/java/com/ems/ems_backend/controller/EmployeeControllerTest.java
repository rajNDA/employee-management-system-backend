package com.ems.ems_backend.controller;


import com.ems.ems_backend.entity.Employee;
import com.ems.ems_backend.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllEmployees() throws Exception {

        Employee emp = new Employee(1L, "Rajesh", "r@gmail.com", "IT", 50000);

        Mockito.when(service.getAllEmployees()).thenReturn(List.of(emp));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Rajesh"))
                .andExpect(jsonPath("$[0].email").value("r@gmail.com"));
    }

    @Test
    void testCreateEmployee() throws Exception {

        Employee emp = new Employee(1L, "Rajesh", "r@gmail.com", "IT", 50000);

        Mockito.when(service.saveEmployee(Mockito.any())).thenReturn(emp);

        mockMvc.perform(post("/api/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rajesh"));
    }

    @Test
    void testCreateEmployee_InvalidInput() throws Exception {

        Employee emp = new Employee(null, "", "wrong-email", "", 500);

        mockMvc.perform(post("/api/employees")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(emp)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.email").exists());
    }

    @Test
    void testGetEmployee_NotFound() throws Exception {

        Mockito.when(service.getEmployeeById(1L))
                .thenThrow(new RuntimeException("Employee not found"));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isInternalServerError());
    }
}
