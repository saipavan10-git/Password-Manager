package com.example.PasswordManagementSys.service.impl;

import com.example.PasswordManagementSys.exceptions.AccountDoesNotExistException;
import com.example.PasswordManagementSys.exceptions.DuplicateAccountException;
import com.example.PasswordManagementSys.Bean.Account;
import com.example.PasswordManagementSys.Bean.User;
import com.example.PasswordManagementSys.Dao.AccountRepository;
import com.example.PasswordManagementSys.Dao.AccountRepositoryWrapper;
import com.example.PasswordManagementSys.service.AccountService;
import com.example.PasswordManagementSys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    @Autowired
    AccountRepositoryWrapper accountRepositoryWrapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public List<Account> getAllAccounts() {
        logger.info("Getting all accounts");
        return accountRepositoryWrapper.getAllAccounts(getUser());
    }

    @Override
    public void saveAccount(Account account) throws DuplicateAccountException {
        logger.info("saving the account");
        account.setUser(getUser());
        accountRepositoryWrapper.saveAccount(account, getUser());
        logger.info("account saved successfully");
    }

    @Override
    public Account updateAccount(Account account) {
        logger.info("Updating the Account");
        accountRepositoryWrapper.updateAccount(account,getUser());
        return account;
    }

    @Override
    public Account getAccountById(int id) throws AccountDoesNotExistException {
        logger.info("getting Accounts By id");
        return accountRepositoryWrapper.getAccountById(id, getUser());
    }

    @Override
    public void deleteAccountById(int id) throws AccountDoesNotExistException {
        logger.info("removing account by Id");
        accountRepositoryWrapper.removeAccountById(id, getUser());
        logger.info("account removed by id successfully");
    }

    public User getUser() {
        logger.info("Getting user bean");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByUserName(authentication.getName());
    }
}
