package com.example.PasswordManagementSys.Service;

import com.example.PasswordManagementSys.Dao.UserRepository;
import com.example.PasswordManagementSys.Dao.UserRepositoryWrapper;
import com.example.PasswordManagementSys.service.impl.UserServicesImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserServicesImpl userService;

    @Mock
    UserRepositoryWrapper userRepositoryWrapper;
    @Mock
    UserRepository userRepository;



    @Test
    @DisplayName("User Service should return user by user name")
    public void userServiceShouldReturnUserByUserName()    {
        doThrow(UsernameNotFoundException.class).when(userRepositoryWrapper).getUserByUserName(any());
        Assertions.assertThrows(UsernameNotFoundException.class , () -> userService.getUserByUserName("user"));
    }
    @Test
    @DisplayName("User Service should return user by user name")
    public void userServiceShouldReturnUserByUserName1()    {
        Assertions.assertDoesNotThrow(() -> userService.getUserByUserName("user"));
    }

    @Test
    @DisplayName("User Service should return UserDetails by user name")
    public void userServiceShouldReturnUserDetails1()    {
        doThrow(UsernameNotFoundException.class).when(userRepositoryWrapper).getUserByUserName(any());
        Assertions.assertThrows(UsernameNotFoundException.class , () -> userService.loadUserByUsername("user"));
    }

}