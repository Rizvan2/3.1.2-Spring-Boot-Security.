package ru.kata.spring.boot_security.demo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.CustomUser;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final RegistrationService registrationService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService, RegistrationService registrationService) {
        this.userService = userService;
        this.roleService = roleService;
        this.registrationService = registrationService;
    }

    @GetMapping()
    public String listAllUsers(Model model) {
        model.addAttribute("admin", userService.listAllUsers());
        return "admin/index";
    }

    @GetMapping("/show")
    public String getUserById(@RequestParam("id") int id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/show";
    }

    @GetMapping("/saveUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("availableRoles", roleService.findAllRoles());
        return "admin/saveUser";
    }

    @PostMapping("/saveUser")
    public String addUser(
            @ModelAttribute("user") @Valid User user,
            @RequestParam(value = "selectedRoleIds", required = false) List<Long> selectedRoleIds,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("availableRoles", roleService.findAllRoles());
            return "admin/saveUser";
        }
        user.setRoles(roleService.getRolesFromIds(selectedRoleIds));
        registrationService.register(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUserById(Model model, @RequestParam("id") int id) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/edit";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam(value = "selectedRoleIds", required = false) List<Long> selectedRoleIds,
                             @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        user.setRoles(roleService.getRolesFromIds(selectedRoleIds));
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUserById(@RequestParam("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        model.addAttribute("user", customUser.getUser());
        return "admin/user";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model, Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        model.addAttribute("user", customUser.getUser());
        return "redirect:/admin";
    }

}