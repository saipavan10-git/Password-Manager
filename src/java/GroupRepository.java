package com.example.PasswordManagementSys.Dao;

import com.example.PasswordManagementSys.Bean.Group;
import com.example.PasswordManagementSys.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface GroupRepository extends JpaRepository<Group,Long> {
   int countByGroupNameAndUser(String group, User user);
}
