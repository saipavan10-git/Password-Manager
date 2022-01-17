package com.example.PasswordManagementSys.service;

import com.example.PasswordManagementSys.exceptions.DuplicateAccountException;
import com.example.PasswordManagementSys.exceptions.DuplicateGroupException;
import com.example.PasswordManagementSys.Bean.Account;
import com.example.PasswordManagementSys.Bean.Group;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface GroupService {
    void saveGroup(Group group) throws DuplicateGroupException, DuplicateAccountException;
    List<Group> getAllGroups();
    List<Account> getAllAccountsByIdAndUser(Long groupId);
    Group getGroupById(Long id);

    void updateGroupName(Group group1);

    void deleteGroupById(Long id);
}
