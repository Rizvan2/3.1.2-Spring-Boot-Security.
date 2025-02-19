package ru.kata.spring.boot_security.demo.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addUser(ru.kata.spring.boot_security.demo.model.@Valid User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, ru.kata.spring.boot_security.demo.model.@Valid User user) {
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

}
