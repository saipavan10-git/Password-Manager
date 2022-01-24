package com.example.PasswordManagementSys.Service;

import com.example.PasswordManagementSys.Bean.Account;
import com.example.PasswordManagementSys.Bean.Group;
import com.example.PasswordManagementSys.Bean.User;
import com.example.PasswordManagementSys.Dao.AccountRepository;
import com.example.PasswordManagementSys.Dao.AccountRepositoryWrapper;
import com.example.PasswordManagementSys.exceptions.AccountDoesNotExistException;
import com.example.PasswordManagementSys.exceptions.DuplicateAccountException;
import com.example.PasswordManagementSys.service.UserService;
import com.example.PasswordManagementSys.service.impl.AccountServiceImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @InjectMocks
    AccountServiceImpl accountServiceImpl;
    @Mock
    Authentication authentication;
    @Mock
    AccountRepository accountRepository;
    @Mock
    SecurityContext securityContext;
    @Mock
    UserService userService;
    @Mock
    AccountRepositoryWrapper accountRepositoryWrapper;
    Account account;
    User user;
    Group group;

    private static MockedStatic<SecurityContextHolder> mockedSettings;

    @BeforeClass
    public static void init() {
        mockedSettings = mockStatic(SecurityContextHolder.class);
    }

    @AfterClass
    public static void close() {
        mockedSettings.close();
    }

    @Before
    public void setUp() {
        user = new User();
        group = new Group();
        Assertions.assertNotNull(accountServiceImpl);
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("name");
        when(userService.getUserByUserName(anyString())).thenReturn(user);
        account = new Account();
        account.setGroup(group);
    }

    @Test
    @DisplayName("Account Service should return all accounts List")
    public void accountServiceShouldReturnAllAccountsList() {
        when(accountRepositoryWrapper.getAllAccounts(any())).thenReturn(List.of(account));
        Assertions.assertEquals(List.of(account), accountServiceImpl.getAllAccounts());
    }

    @Test
    @DisplayName("Account Service should return all accounts")
    public void accountServiceShouldReturnAllAccounts1() {
        when(accountRepositoryWrapper.getAllAccounts(any())).thenReturn(List.of());
        Assertions.assertEquals(List.of(), accountServiceImpl.getAllAccounts());
    }


    @Test
    @DisplayName("Account Service should save unique account otherwise throw exception")
    public void accountServiceShouldSaveValidAndUniqueAccount1() throws DuplicateAccountException {
        doThrow(DuplicateAccountException.class).when(accountRepositoryWrapper).saveAccount(account, user);
        Assertions.assertThrows(DuplicateAccountException.class, () -> accountServiceImpl.saveAccount(account));
    }

    @Test
    @DisplayName("Account Service should save validated and unique account")
    public void accountServiceShouldSaveValidAndUniqueAccount2() {
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.saveAccount(account));
    }




    @Test
    @DisplayName("Account Service should update valid account by id")
    public void accountShouldUpdateValidateAccountById2() {
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.updateAccount(account));
    }


    @Test
    @DisplayName("Account Service should return account by id")
    public void accountServiceShouldReturnAccountById() {
        account.setAccountId(0);
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.getAccountById(account.getAccountId()));
    }

    @Test
    @DisplayName("Account Service should return account by id")
    public void accountServiceShouldReturnAccountById1() throws AccountDoesNotExistException {
        account.setAccountId(0);
        doThrow(AccountDoesNotExistException.class).when(accountRepositoryWrapper).getAccountById(account.getAccountId(), user);
        Assertions.assertThrows(AccountDoesNotExistException.class, () -> accountServiceImpl.getAccountById(account.getAccountId()));
    }


    @Test
    @DisplayName("Account Service should remove account by id")
    public void accountServiceShouldRemoveAccountById() {
        account.setAccountId(0);
        Assertions.assertDoesNotThrow(() -> accountServiceImpl.deleteAccountById(account.getAccountId()));
    }

    @Test
    @DisplayName("Account Service should remove account by id")
    public void accountServiceShouldRemoveAccountById1() throws AccountDoesNotExistException {
        account.setAccountId(0);
        doThrow(AccountDoesNotExistException.class).when(accountRepositoryWrapper).removeAccountById(account.getAccountId(), user);
        Assertions.assertThrows(AccountDoesNotExistException.class, () -> accountServiceImpl.deleteAccountById(account.getAccountId()));
    }

}