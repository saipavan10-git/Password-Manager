package com.example.PasswordManagementSys.Dao;

import com.example.PasswordManagementSys.exceptions.DuplicateUserException;
import com.example.PasswordManagementSys.Bean.User;

public interface UserRepositoryWrapper {
    User saveUser(User user) throws DuplicateUserException;
    User getUserByUserName(String userName) ;
}
