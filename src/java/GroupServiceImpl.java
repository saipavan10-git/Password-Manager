package com.example.PasswordManagementSys.service.impl;

import com.example.PasswordManagementSys.exceptions.DuplicateGroupException;
import com.example.PasswordManagementSys.Bean.Account;
import com.example.PasswordManagementSys.Bean.Group;
import com.example.PasswordManagementSys.Bean.User;
import com.example.PasswordManagementSys.Dao.AccountRepository;
import com.example.PasswordManagementSys.Dao.GroupRepository;
import com.example.PasswordManagementSys.service.GroupService;
import com.example.PasswordManagementSys.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    UserService userService;
    GroupRepository groupRepository;
    AccountServiceImpl accountService;
    AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Override
    public void saveGroup(Group group) throws DuplicateGroupException {
        logger.info("saving the group bean");
        group.setUser(getUser());
        checkForDuplicate(group.getGroupName(),getUser());
        groupRepository.save(group);
        logger.info("group bean saved successfully");
    }

    private void checkForDuplicate(String groupName, User user) throws DuplicateGroupException {
        if(groupRepository.countByGroupNameAndUser(groupName, user)>0){
            throw new DuplicateGroupException("Group Already Exist");
        }
    }

    @Override
    public List<Group> getAllGroups() {
        User user = accountService.getUser();
        return user.getGroup();
    }

    @Override
    public List<Account> getAllAccountsByIdAndUser(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        List<Account> accounts = new ArrayList<Account>();
        if (group.isPresent()) {
            accounts = accountRepository.findByGroupAndUser(group.get(), getUser());
        }
        return accounts;
    }
    @Override
    public Group getGroupById(Long id) {
        Group group = groupRepository.getById(id);
        return group;
    }

    @Override
    public void updateGroupName(Group group1) {
        groupRepository.save(group1);
    }

    @Override
    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }


    private User getUser() {
        logger.info("getting user bean");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getUserByUserName(authentication.getName());
    }
}
