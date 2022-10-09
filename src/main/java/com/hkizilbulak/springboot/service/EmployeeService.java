package com.hkizilbulak.springboot.service;

import com.hkizilbulak.springboot.entity.Employee;
import com.hkizilbulak.springboot.exception.RecordNotFoundException;
import com.hkizilbulak.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new RecordNotFoundException("No employee record exist for given id");
    }

    public void createOrUpdateEmployee(Employee employee) {
        if (employee.getId() == null) {
            employeeRepository.save(employee);
        } else {

            Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
            if(existingEmployee.isPresent()) {
                Employee newEmployee = employee;
                newEmployee.setEmail(employee.getEmail());
                newEmployee.setFirstName(employee.getFirstName());
                newEmployee.setLastName(employee.getLastName());
                employeeRepository.save(newEmployee);
            } else {
                employeeRepository.save(employee);
            }
        }
    }

    public void deleteEmployee(Long id) throws RecordNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);

        if(employee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No employee record exist for given id");
        }
    }
}
