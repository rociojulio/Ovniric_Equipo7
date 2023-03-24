package com.ovniric.service;

import com.ovniric.model.user.Role;
import com.ovniric.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role crearRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> obtenerRoles() {
        return roleRepository.findAll();
    }

    public Role obtenerRolePorId(Long id) {
        return roleRepository.findById(id).orElse(null);
    }
}
