package com.example.PasswordManagementSys.Controller;

import com.example.PasswordManagementSys.Bean.Account;
import com.example.PasswordManagementSys.Bean.User;
import com.example.PasswordManagementSys.Dao.AccountRepository;
import com.example.PasswordManagementSys.Dao.UserRepositoryWrapper;
import com.example.PasswordManagementSys.UI.controller.AccountController;
import com.example.PasswordManagementSys.service.AccountService;
import com.example.PasswordManagementSys.service.GroupService;
import com.example.PasswordManagementSys.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class)
@WithMockUser
public class AccountControllerTest {
    @MockBean
    AccountService accountService;
    @MockBean
    UserService userService;
    @MockBean
    GroupService groupService;
    @MockBean
    AccountRepository accountRepository;
    @MockBean
    UserRepositoryWrapper userRepositoryWrapper;
    @Autowired
    MockMvc mockMvc;
    Account account;
    User user;

    @Before
    public void setUp() {
        account = new Account();
        user=new User();
    }
    @Test
    @DisplayName("getAccountById Should Give alterAccount page If Account Exist")
    public void getAccountByIdShouldGiveAlterAccountPageIfAccountExist() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(List.of(account));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts"))
                .andExpect(view().name("accountList"));
    }

    @Test
    @DisplayName("getAccountById Should Give update Account page If not Exist then return 200 response")
    public void getAccountByIdShouldGiveUpdateAccountPageIfAccountExist() throws Exception {
        when(accountService.getAccountById(anyInt())).thenReturn(account);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/accounts/accountIdForUpdate/2")).andExpect(MockMvcResultMatchers
                .status().is(200));
    }

    @Test
    @DisplayName("addAccount Should Give updateAccount page")
    public void addAccountShouldReturnAddNewAccountPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/accounts/addNewAccount"))
                .andExpect(view().name("addNewAccount"));
    }

}
