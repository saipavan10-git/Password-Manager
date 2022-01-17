package com.example.PasswordManagementSys.UI.controller;

import com.example.PasswordManagementSys.exceptions.DuplicateAccountException;
import com.example.PasswordManagementSys.exceptions.DuplicateGroupException;
import com.example.PasswordManagementSys.Bean.Group;
import com.example.PasswordManagementSys.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/showGroup")
    public String listOfAccounts(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "groupList";
    }

    @GetMapping("/groups/{id}")
    public String accountListByGroup(@PathVariable Long id, Model model){
        model.addAttribute("accounts",groupService.getAllAccountsByIdAndUser(id));
        return "accountList";
    }


    @GetMapping("/addGroup")
    public String addGroup(Model model){
        Group group=new Group();
        model.addAttribute("group",group);
        return "addGroup";
    }

    @PostMapping("/addGroup")
    public String addGroup(@Valid @ModelAttribute("group") Group group, BindingResult binding,Model model)throws DuplicateAccountException {
       if(binding.hasErrors()){
           return "addGroup";
       }
       try{
           groupService.saveGroup(group);
       }catch (DuplicateGroupException duplicateGroupException){
           model.addAttribute("duplicateGroup",duplicateGroupException.getMessage());
           return "addGroup";
       }
        return "redirect:/showGroup";
    }
    @GetMapping("/update/{id}")
    public String getGroupById(@PathVariable Long id, Model model ){
        model.addAttribute("group", groupService.getGroupById(id));
        return "updateGroup";
    }
    @PostMapping("/update/{id}")
    public String updateGroupById(@PathVariable Long id,@ModelAttribute("group") Group group){
        Group group1=groupService.getGroupById(id);
        group1.setGroupName(group.getGroupName());
        groupService.updateGroupName(group1);
        return "redirect:/showGroup";
    }
    @GetMapping("/deleteGroup/{id}")
    public String deleteGroup(@PathVariable Long id){
        groupService.deleteGroupById(id);
        return "redirect:/showGroup";
    }



}
