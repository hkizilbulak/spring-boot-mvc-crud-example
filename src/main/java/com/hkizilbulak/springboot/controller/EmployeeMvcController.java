package com.hkizilbulak.springboot.controller;

import com.hkizilbulak.springboot.entity.Employee;
import com.hkizilbulak.springboot.exception.RecordNotFoundException;
import com.hkizilbulak.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class EmployeeMvcController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "index";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String showEmployeeForm(Model model, @PathVariable(value = "id", required = false) Optional<Long> id) throws RecordNotFoundException {
        if (id.isPresent()) {
            Employee employee = employeeService.getEmployeeById(id.get());
            model.addAttribute("employee", employee);
        } else {
            model.addAttribute("employee", new Employee());
        }
        return "add-edit-employee";
    }

    @PostMapping("/createEmployee")
    public String saveOrUpdateEmployee(@Valid Employee employee, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-edit-employee";
        }
        employeeService.createOrUpdateEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) throws RecordNotFoundException {
        employeeService.deleteEmployee(id);
        return "redirect:/";
    }
}
