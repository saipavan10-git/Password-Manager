package com.example.PasswordManagementSys.Controller;

import com.example.PasswordManagementSys.Bean.Group;
import com.example.PasswordManagementSys.Dao.AccountRepository;
import com.example.PasswordManagementSys.Dao.UserRepositoryWrapper;
import com.example.PasswordManagementSys.UI.controller.GroupController;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GroupController.class)
@WithMockUser
public class GroupControllerTest {
    @MockBean
    GroupService groupService;
    @MockBean
    UserService userService;
    @MockBean
    AccountRepository accountRepository;
    @MockBean
    UserRepositoryWrapper userRepositoryWrapper;
    @Autowired
    MockMvc mockMvc;
    Group group;

    @Before
    public void setUp(){
        group = new Group();
    }

    @Test
    @DisplayName("getGroupById Should Give addGroup page If Group Exist")
    public void getGroupByIdShouldGiveAddGroupPageIfAccountExist() throws Exception {
        when(groupService.getGroupById(anyLong())).thenReturn(group);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/update/2"))
                .andExpect(view().name("updateGroup"));

    }




}
