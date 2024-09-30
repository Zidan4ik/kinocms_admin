package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Contact;
import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.repository.ContactRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceImpTest {
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ImageServiceImp imageServiceImp;
    @InjectMocks
    private ContactServiceImp contactServiceImp;
    private List<Contact> loadedContacts;
    private MultipartFile file;
    private final static Long ID = 1L;

    @BeforeEach
    void setUp() {
        loadedContacts = TestDataUtil.loadContact();
    }

    @Test
    void shouldSaveContact() {
        contactServiceImp.save(loadedContacts.get(0));
        verify(contactRepository, times(1)).save(loadedContacts.get(0));
    }

    @Test
    void shouldSaveContact_WhenDataAreNotNull() {
        file = new MockMultipartFile("fileImage1", "contact1.html", "text/html", "content".getBytes());
        contactServiceImp.saveImages(loadedContacts.get(0), file);
    }
    @Test
    void shouldSaveContact_WhenFileIsNullAndContactId() {
        Contact contact = loadedContacts.get(0);
        contact.setId(null);
        contactServiceImp.saveImages(contact, null);
    }
    @Test
    void shouldSaveContact_WhenFileOriginalNameIsEmpty() {
        file = new MockMultipartFile("fileImage1", "","text/html", "content".getBytes());
        contactServiceImp.saveImages(loadedContacts.get(0), file);
    }
    @Test
    void shouldSaveContact_WhenFileOriginalNameIsSpecial() {
        file = new MockMultipartFile("fileImage1", "exist","text/html", "content".getBytes());
        contactServiceImp.saveImages(loadedContacts.get(0), file);
    }

    @Test
    void shouldSaveContact_WhenThrowException() throws IOException {
        file = new MockMultipartFile("fileImage1", "contact1.html", "text/html", "content".getBytes());
        contactServiceImp.saveImages(loadedContacts.get(0), file);
        doThrow(new IOException("Error saving file")).when(imageServiceImp).saveFile(
                any(MultipartFile.class), eq(null), eq(GalleriesType.contacts), eq(ImageType.logo), eq(loadedContacts.get(0).getId()));
        contactServiceImp.saveImages(loadedContacts.get(0), file);
    }

    @Test
    void shouldGetContact_ById() {
        when(contactRepository.findById(ID)).thenReturn(Optional.of(loadedContacts.get(0)));
        Optional<Contact> contactById = contactServiceImp.getById(ID);
        assertTrue(contactById.isPresent(), "Contact was not found");
        assertEquals(ID, contactById.get().getId(), "Ids should be match");
        verify(contactRepository, times(1)).findById(ID);
    }

    @Test
    void shouldDeleteByIdContact_ById() {
        contactRepository.deleteById(ID);
        verify(contactRepository, times(1)).deleteById(ID);
    }

    @Test
    void shouldGetAllContacts() {
        when(contactRepository.findAll()).thenReturn(loadedContacts);
        List<Contact> contacts = contactServiceImp.getAll();
        assertEquals(loadedContacts.size(),contacts.size(),"Sizes should be match");
        verify(contactRepository,times(1)).findAll();
    }

    @Test
    void getAllByPage() {
        Page page = new Page();
        when(contactRepository.getAllByPage(page)).thenReturn(loadedContacts);
        List<Contact> contactsByPage = contactServiceImp.getAllByPage(page);
        assertEquals(loadedContacts.size(),contactsByPage.size(),"Sizes should be match");
    }

    @Test
    void deleteByIdAllByPage() {
        Page page = new Page();
        contactServiceImp.deleteAllByPage(page);
        verify(contactRepository,times(1)).deleteAllByPage(page);
    }
    @Test
    void shouldDeleteByIdById(){
        contactServiceImp.deleteById(ID);
        verify(contactRepository,times(1)).deleteById(ID);
    }
}