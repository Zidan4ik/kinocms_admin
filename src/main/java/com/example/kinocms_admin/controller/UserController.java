package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.mapper.UserMapper;
import com.example.kinocms_admin.model.PageResponse;
import com.example.kinocms_admin.model.UserDTOTable;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.service.serviceimp.UserServiceImp;
import com.example.kinocms_admin.util.JsonUtil;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {
    public final UserServiceImp userServiceImp;

    @GetMapping("/users")
    public String users() {
        return "users/users-view";
    }

    @GetMapping("/user/{id}/edit")
    public ModelAndView editUser(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("users/user-edit");
        model.addObject("id", id);
        return model;
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public UserDTOView getUserData(@PathVariable Long id) {
        Optional<User> userById = userServiceImp.getById(id);
        return userById.map(UserMapper::toDTOView).orElse(null);
    }

    @GetMapping("/user/{id}/delete")
    @ResponseBody
    public UserDTOTable deleteUser(@PathVariable Long id,
                                   @RequestParam(required = false) String searchValue,
                                   Pageable pageable) {
        userServiceImp.deleteById(id);
        Page<UserDTOView> page = userServiceImp.filtrationAndSortingAndPagination(pageable, searchValue);
        PageResponse<UserDTOView> pageResponse = PageResponse.of(page);
        return new UserDTOTable(pageResponse, searchValue);
    }
    @PostMapping("/user/edit")
    @ResponseBody
    public ResponseEntity<Object> editSaveUser(@ModelAttribute (name = "user") UserDTOView dto){
        userServiceImp.save(UserMapper.toEntity(dto));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
