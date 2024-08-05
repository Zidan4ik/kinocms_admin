package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.Contact;
import com.example.kinocms_admin.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> getAllByPage(Page page);
    void deleteAllByPage(Page page);
}
