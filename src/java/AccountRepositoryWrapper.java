package com.example.PasswordManagementSys.Dao;

import com.example.PasswordManagementSys.exceptions.AccountDoesNotExistException;
import com.example.PasswordManagementSys.exceptions.DuplicateAccountException;
import com.example.PasswordManagementSys.Bean.Account;
import com.example.PasswordManagementSys.Bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AccountRepositoryWrapper {
    void saveAccount(Account account, User user) throws DuplicateAccountException;
    Account getAccountById(int accountId, User user) throws AccountDoesNotExistException;
    List<Account> getAllAccounts(User user);

    void removeAccountById(int accountId ,User userBean) throws AccountDoesNotExistException;

    Account updateAccount(Account account, User user);
}
