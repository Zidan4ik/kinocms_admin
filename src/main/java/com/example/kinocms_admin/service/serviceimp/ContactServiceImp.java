package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Contact;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.repository.ContactRepository;
import com.example.kinocms_admin.service.ContactService;
import com.example.kinocms_admin.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImp implements ContactService {
    private final ContactRepository contactRepository;
    private final ImageServiceImp imageServiceImp;

    @Override
    public void save(Contact contact) {
        LogUtil.logSaveNotification("contact", "id", contact.getId());
        contactRepository.save(contact);
        LogUtil.logSaveInfo("Contact", "id", contact.getId());
    }

    @Transactional
    @Override
    public void saveImages(Contact contact, MultipartFile fileLogo) {
        String nameLogo = null;
        if (contact.getId() != null) {
            Optional<Contact> pageById = getById(contact.getId());
            updatePageAttributes(contact, pageById);
        }
        if (fileLogo != null && !Objects.equals(fileLogo.getOriginalFilename(), "exist")) { //не генеруємо нову назву, якщо 'exist'
            nameLogo = imageServiceImp.generateFileName(fileLogo);
            contact.setNameLogo(nameLogo);
        }
        save(contact);
        try {
            if (fileLogo != null && !Objects.requireNonNull(fileLogo.getOriginalFilename()).isEmpty() && !Objects.equals(fileLogo.getOriginalFilename(), "exist")) {
                imageServiceImp.saveFile(fileLogo, nameLogo, GalleriesType.contacts, ImageType.logo, contact.getId());
            }
        } catch (IOException e) {
            LogUtil.logErrorSavingFiles(e);
        }
    }

    private void updatePageAttributes(Contact contact, Optional<Contact> pageById) {
        LogUtil.logUpdateNotifications("contact", "id", contact.getId());
        pageById.ifPresent(p -> contact.setNameLogo(p.getNameLogo()));
        LogUtil.logUpdateInfo("Contact", "id", contact.getId());
    }

    @Override
    public Optional<Contact> getById(Long id) {
        LogUtil.logGetNotification("contact", "id", id);
        Optional<Contact> contactById = contactRepository.findById(id);
        LogUtil.logGetInfo("Contact", "id", id, contactById.isPresent());
        return contactById;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        LogUtil.logDeleteNotification("contact", "id", id);
        contactRepository.deleteById(id);
        LogUtil.logDeleteInfo("Contact", "id", id);
    }

    @Override
    public List<Contact> getAll() {
        LogUtil.logGetAllNotification("contacts");
        List<Contact> contacts = contactRepository.findAll();
        LogUtil.logSizeInfo("contacts", contacts.size());
        return contacts;
    }

    @Override
    public List<Contact> getAllByPage(Page page) {
        LogUtil.logGetAllNotification("pages", "id", page.getId());
        List<Contact> pages = contactRepository.getAllByPage(page);
        LogUtil.logSizeInfo("contacts", pages.size());
        return pages;
    }

    @Override
    public void deleteAllByPage(Page page) {
        LogUtil.logDeleteAllNotification("pages");
        contactRepository.deleteAllByPage(page);
        LogUtil.logDeleteAllInfo("contacts", "page");
    }
}
