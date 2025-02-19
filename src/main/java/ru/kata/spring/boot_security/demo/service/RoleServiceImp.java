package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRoleById(long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + id));
    }
}
