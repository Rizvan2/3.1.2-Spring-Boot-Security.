package ru.kata.spring.boot_security.demo.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RegistrationService;


import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Init {
    private final RoleRepository roleRepository;
    private final RegistrationService registrationService;


    @Autowired
    public Init(RoleRepository roleRepository, RegistrationService registrationService) {
        this.roleRepository = roleRepository;
        this.registrationService = registrationService;
    }

    @PostConstruct
    public void init() {

        Role adminRole = new Role("ROLE_ADMIN");
        Role userRole = new Role("ROLE_USER");

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        registrationService.register(new User("Admin", "adminEmail@gmail.com",
                "123", new HashSet<>(adminRoles)));

        registrationService.register(new User("USER", "userEmail@gmail.com",
                "123", new HashSet<>(userRoles)));
    }
}
