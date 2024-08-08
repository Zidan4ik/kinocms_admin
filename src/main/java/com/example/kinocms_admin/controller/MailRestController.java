package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.MailStructure;
import com.example.kinocms_admin.service.serviceimp.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailRestController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {
        mailService.sendMail(mail, mailStructure);
        return "Successfully sent the mail!!";
    }
}
