package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.model.PageResponse;
import com.example.kinocms_admin.model.UserDTOTable;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.service.serviceimp.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


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
    public ModelAndView editUser(@PathVariable Long id){
        ModelAndView model = new ModelAndView("users/user-edit");
        model.addObject("id",id);
        return model;
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
}
