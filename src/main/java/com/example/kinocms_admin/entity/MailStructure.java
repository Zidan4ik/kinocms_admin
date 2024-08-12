package com.example.kinocms_admin.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailStructure {
    private String subject;
    private String message;

    public MailStructure(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }
}
