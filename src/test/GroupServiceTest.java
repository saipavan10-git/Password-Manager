package com.example.PasswordManagementSys.Service;

import com.example.PasswordManagementSys.Bean.Account;
import com.example.PasswordManagementSys.Bean.Group;
import com.example.PasswordManagementSys.Bean.User;
import com.example.PasswordManagementSys.Dao.GroupRepository;
import com.example.PasswordManagementSys.exceptions.DuplicateGroupException;
import com.example.PasswordManagementSys.service.UserService;
import com.example.PasswordManagementSys.service.impl.GroupServiceImpl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    @InjectMocks
    GroupServiceImpl groupServiceImpl;
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;
    @Mock
    UserService userService;
    @Mock
    GroupRepository groupRepository;

    User user;
    Group group;
    Account account;
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
        account = new Account();
        when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("name");
        when(userService.getUserByUserName(any())).thenReturn(user);
        group = new Group("");
    }

    @Test
    @DisplayName("Group service should save unique and encrypted group")
    public void groupServiceShouldSaveUniqueAndEncryptedGroup() {
        Assertions.assertDoesNotThrow(() -> groupServiceImpl.saveGroup(group));
    }

    @Test
    @DisplayName("Group service should give group by id")
    public void groupServiceShouldGiveGroupById() {
        groupServiceImpl.getGroupById(1l);
        verify(groupRepository,times(1)).getById(1l);
    }




    @Test
    @DisplayName("Group service save the group or not ")
    public void groupSavedOrNot() throws DuplicateGroupException {
        groupServiceImpl.saveGroup(group);
        verify(groupRepository, times(1)).save(ArgumentMatchers.any(Group.class));
    }


    @Test
    @DisplayName("Group service should update Group name")
    public void groupServiceUpdateGroupName(){
        groupServiceImpl.updateGroupName(group);
        verify(groupRepository, times(1)).save(ArgumentMatchers.any(Group.class));

    }

    @Test
    @DisplayName("Group Service Should Delete group by Id")
    public void groupShouldBeDeleteById(){
        groupServiceImpl.deleteGroupById(1l);
        verify(groupRepository,times(1)).deleteById(1l);
    }
}
