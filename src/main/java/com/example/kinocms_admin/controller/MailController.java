package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.model.PageResponse;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.service.serviceimp.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MailController {
    private final UserServiceImp userServiceImp;
    @GetMapping("/mailing")
    public ModelAndView toMailing(@RequestParam(name = "selectedUsers",required = false)List<User> selectedUsers){
        ModelAndView model = new ModelAndView("mail/mailing");
        model.addObject("selectedUsers",selectedUsers);
        return model;
    }

    @GetMapping("/mailing/users")
    public ModelAndView mailingUsersList(Pageable pageable){
        ModelAndView model = new ModelAndView("mail/users");

        Page<UserDTOView> page = userServiceImp.getAll(pageable);
        model.addObject("users", PageResponse.of(page));
        model.addObject("size",PageResponse.of(page).getMetadata().getSize());
        model.addObject("page",PageResponse.of(page).getMetadata().getPage());
        return model;
    }
}
