package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.MailStructure;
import com.example.kinocms_admin.entity.Template;
import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.model.TemplateDTO;
import com.example.kinocms_admin.service.TemplateService;
import com.example.kinocms_admin.service.serviceimp.MailService;
import com.example.kinocms_admin.service.serviceimp.TemplateServiceImp;
import com.example.kinocms_admin.service.serviceimp.UserServiceImp;
import com.example.kinocms_admin.util.ImageUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/mail")
@RequiredArgsConstructor
public class MailRestController {

    private final MailService mailService;
    private final TemplateServiceImp templateServiceImp;
    private final UserServiceImp userServiceImp;
    @PostMapping("/send")
    public String sendMail(@RequestParam(name = "isAll") String isAll,
                           @RequestParam(name = "message") String message) {
        List<User> users;
        if (isAll.equals("true")) {
            users = userServiceImp.getAll();
        } else {
            users = userServiceImp.getAllSelected();
        }
        for (User user : users) {
            user.setSelected(false);
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                mailService.sendMail(user.getEmail(), new MailStructure("mailing", message));
            }
        }
        userServiceImp.saveAll(users);
        return "Successfully sent the mail!!";
    }

    @PostMapping("/sendHTML")
    public String sendHTML(@RequestParam(name = "isAll") String isAll,
                           @ModelAttribute TemplateDTO dto) throws MessagingException {
        List<User> users;
        if (isAll.equals("true")) {
            users = userServiceImp.getAll();
        } else {
            users = userServiceImp.getAllSelected();
        }
//        List<String> emails = users.stream().map(User::getEmail).toList();
//        String readHTMLFile = ImageUtil.toReadHTMLFile(file);
//        mailService.sendMailWidthHTML(emails.toArray(new String[0]), new MailStructure("subject-header", readHTMLFile));
//
//        templateServiceImp.save(new Template(),file);
//        userServiceImp.saveAll(users);
        return "Successfully sent the mail!!";
    }

}
