package com.example.kinocms_admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthorityController {
    @GetMapping("/admin/login")
    public ModelAndView showLogin() {
        return new ModelAndView("auth/login");
    }
}
