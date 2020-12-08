package com.define.controller;

import com.define.dto.UserDTO;
import com.define.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUsersByIdList")
    public List<UserDTO> getUsersByIdList() {
        List<String> idList = new ArrayList<>();
        idList.add("1");
        idList.add("2");
        idList.add("3");
        if(CollectionUtils.isEmpty(idList)) {
            return null;
        }
        return userService.getUsersByIdList(idList);
    }
}
