package com.task2.task2.Controllers;

import com.task2.task2.Entities.Role;
import com.task2.task2.Services.roleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class roleController {

    @Autowired
    roleService roleservice;

    @PostMapping
    public ResponseEntity<String> addRole(@RequestBody Role role) {
        return roleservice.saveRole(role);
    }

    @GetMapping
    public List<Role> getAllRoles() {
        return roleservice.getallroles();
    }



}
