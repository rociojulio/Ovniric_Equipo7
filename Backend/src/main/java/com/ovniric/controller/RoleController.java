package com.ovniric.controller;

import com.ovniric.model.user.Role;
import com.ovniric.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> crearRole(@RequestBody Role role) {
        Role nuevoRole = roleService.crearRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRole);
    }

    @GetMapping
    public ResponseEntity<List<Role>> obtenerRoles() {
        List<Role> roles = roleService.obtenerRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> obtenerRolePorId(@PathVariable Long id) {
        Role role = roleService.obtenerRolePorId(id);
        if (role == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(role);
        }
    }
}