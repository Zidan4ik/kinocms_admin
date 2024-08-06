package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Contact;
import com.example.kinocms_admin.entity.Gallery;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.repository.ContactRepository;
import com.example.kinocms_admin.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactServiceImp implements ContactService {
    private final ContactRepository contactRepository;
    private final ImageServiceImp imageServiceImp;

    @Override
    public void save(Contact contact) {
        contactRepository.save(contact);
    }

    @Transactional
    @Override
    public void saveImages(Contact contact, MultipartFile fileLogo) {
        String nameLogo = null;
        if (contact.getId() != null) {
            Optional<Contact> pageById = getById(contact.getId());
            updatePageAttributes(contact, pageById);
        }

        if (fileLogo != null && !Objects.equals(fileLogo.getOriginalFilename(), "exist")) {
            nameLogo = imageServiceImp.generateFileName(fileLogo);
            contact.setNameLogo(nameLogo);
        }
        save(contact);
        try {
            if (fileLogo != null && !Objects.requireNonNull(fileLogo.getOriginalFilename()).isEmpty()
                    && !Objects.equals(fileLogo.getOriginalFilename(), "exist")) {
                imageServiceImp.saveFile(fileLogo, nameLogo, GalleriesType.contacts, ImageType.logo, contact.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePageAttributes(Contact contact, Optional<Contact> pageById) {
        pageById.ifPresent(p -> contact.setNameLogo(p.getNameLogo()));
    }

    @Override
    public Optional<Contact> getById(Long id) {
        return contactRepository.findById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public List<Contact> getAllByPage(Page page) {
        return contactRepository.getAllByPage(page);
    }

    @Override
    public void deleteAll(Page page) {
        contactRepository.deleteAllByPage(page);
    }
}
