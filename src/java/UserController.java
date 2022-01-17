package com.example.PasswordManagementSys.UI.controller;

import com.example.PasswordManagementSys.exceptions.DuplicateUserException;
import com.example.PasswordManagementSys.service.UserService;
import com.example.PasswordManagementSys.UI.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationFormHandler(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try{
            userService.save(userDto);
        }catch (DuplicateUserException duplicateUserException){
            model.addAttribute("duplicateError",duplicateUserException.getMessage());
            return "registration";
        }

        return "redirect:/login?success";
    }

    @ExceptionHandler(value = DuplicateUserException.class)
    public String duplicateUserExceptionHandler(DuplicateUserException exception , Model model){
        return "redirect:/registration?errorMessage";
    }

}
