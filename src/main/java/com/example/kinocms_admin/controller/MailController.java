package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.Template;
import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.mapper.TemplateMapper;
import com.example.kinocms_admin.model.PageResponse;
import com.example.kinocms_admin.model.TemplateDTO;
import com.example.kinocms_admin.model.UserDTOTable;
import com.example.kinocms_admin.model.UserDTOView;
import com.example.kinocms_admin.service.serviceimp.TemplateServiceImp;
import com.example.kinocms_admin.service.serviceimp.UserServiceImp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class MailController {
    private final UserServiceImp userServiceImp;
    private final TemplateServiceImp templateServiceImp;
    @GetMapping("/mailing")
    public ModelAndView toMailing(@RequestParam(name = "isExistUsers",required = false) Boolean isExistUsers){
        ModelAndView model = new ModelAndView("mail/mailing");

        model.addObject("isExistUsers",isExistUsers);
        return model;
    }
    @GetMapping("/templates")
    @ResponseBody
    public List<TemplateDTO> getTemplates(){
            List<Template> fiveTemplates = templateServiceImp.getFiveTemplates();
        return TemplateMapper.toListDTO(fiveTemplates);
    }
    @GetMapping("/mailing/users")
    public ModelAndView mailingUsersList(@RequestParam(required = false) String searchValue,
                                         Pageable pageable){

        ModelAndView model = new ModelAndView("mail/users");

        Page<UserDTOView> page = userServiceImp.searchByValueAndPagination(searchValue,pageable);
        PageResponse<UserDTOView> pageResponse = PageResponse.of(page);
        model.addObject("users", pageResponse);
        model.addObject("size", pageResponse.getMetadata().getSize());
        model.addObject("currentPage", pageResponse.getMetadata().getPage());
        model.addObject("totalPages", pageResponse.getMetadata().getTotalPages());
        model.addObject("searchValue",searchValue);
        return model;
    }

    @PostMapping("/mailing/user-selected/save")
    @ResponseBody
    public ResponseEntity<Object> saveSelectedUser(@RequestParam Long id, @RequestParam Boolean isSelected){
        Optional<User> userId = userServiceImp.getById(id);
        if(userId.isPresent()){
            User user = userId.get();
            user.setSelected(isSelected);
            userServiceImp.save(user);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/mailing/users-view")
    public String viewUsers(){
        return "mail/users-test";
    }

    @GetMapping("/mailing/users-table")
    @ResponseBody
    public UserDTOTable getPageResponse(@RequestParam(required = false) String searchValue,
                                        Pageable pageable){
//        Page<UserDTOView> page = userServiceImp.getAll(pageable);
        Page<UserDTOView> page = userServiceImp.searchByValueAndPagination(searchValue,pageable);
        PageResponse<UserDTOView> pageResponse = PageResponse.of(page);
        return new UserDTOTable(pageResponse,searchValue);
    }
}
