package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Template;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.ImageType;
import com.example.kinocms_admin.model.TemplateDTO;
import com.example.kinocms_admin.repository.TemplateRepository;
import com.example.kinocms_admin.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TemplateServiceImpTest {
    @Mock
    private TemplateRepository templateRepository;
    @Mock
    private ImageServiceImp imageServiceImp;
    @InjectMocks
    private TemplateServiceImp templateService;

    private final static Long ID = 1L;
    private List<Template> loadedTemplates;

    @BeforeEach
    void setUp() {
        loadedTemplates = TestDataUtil.loadTemplates();
    }

    @Test
    void shouldCallSave_WhenFileNotNull() throws IOException {
        Template template = new Template();
        template.setId(1L);
        MockMultipartFile file = new MockMultipartFile("file", "template.html", "text/html", "content".getBytes());
        doThrow(new IOException("Error saving file")).when(imageServiceImp).saveFile(any(), any(), any(), any(), any());
        templateService.save(template, file);
        verify(templateRepository, times(1)).save(template);
        verify(imageServiceImp, times(1)).saveFile(file, "template.html", GalleriesType.templates, ImageType.file, 1L);
    }

    @Test
    void shouldCallSave_WhenFileIsNull() {
        Template template = new Template();
        template.setId(1L);
        templateService.save(template, null);
        verify(templateRepository, times(1)).save(template);
    }

    @Test
    void shouldCallSave_WhenFileWithEmptyInOriginalName() {
        Template template = new Template();
        template.setId(1L);
        MockMultipartFile file = new MockMultipartFile("file", "", "text/html", "content".getBytes());
        templateService.save(template, file);
        verify(templateRepository, times(1)).save(template);
    }


    @Test
    void deleteById() {
        templateService.deleteById(ID);
        verify(templateRepository, times(1)).deleteById(ID);
    }

    @Test
    void getAll() {
        when(templateRepository.findAll()).thenReturn(loadedTemplates);
        List<Template> templates = templateService.getAll();
        assertEquals(loadedTemplates.size(), templates.size(), "Size should be match");
        verify(templateRepository, times(1)).findAll();
    }

    @Test
    void getFiveTemplates() {
        when(templateRepository.getTop5Templates()).thenReturn(loadedTemplates);
        List<Template> fiveTemplates = templateService.getFiveTemplates();
        assertEquals(loadedTemplates.size(), fiveTemplates.size(), "Size should be match");
        verify(templateRepository, times(1)).getTop5Templates();
    }

    @Test
    void getById() {
        when(templateRepository.findById(ID)).thenReturn(Optional.of(loadedTemplates.get(0)));
        Optional<Template> templateById = templateService.getById(ID);
        assertTrue(templateById.isPresent(), "Template should be present");
        assertEquals(ID, templateById.get().getId(), "Template's id should be match");
    }

    @Test
    void shouldGetPathToFile_WhenIdTemplateIsNotZero() {
        Template template = loadedTemplates.get(0);
        TemplateDTO templateDTO = new TemplateDTO(template.getId(),
                template.getNameFile(),
                new MockMultipartFile("file", "template.html",
                        "text/html", "content".getBytes()));
        when(templateService.getById(templateDTO.getId())).thenReturn(Optional.of(template));
        String result = templateService.getPathToFile(templateDTO);
        assertEquals("/home/slj/projects/KinoCMS-R.Pravnyk/uploads/templates/file/1/nameFile1", result);
        verify(templateRepository, times(1)).findById(1L);
    }

    @Test
    void shouldGetPathToFile_WhenIdTemplateIsZero() {
        Template template = loadedTemplates.get(0);
        TemplateDTO templateDTO = new TemplateDTO(0L,
                template.getNameFile(),
                new MockMultipartFile("file", "template.html",
                        "text/html", "content".getBytes()));
        when(templateService.getById(null)).thenReturn(Optional.of(template));
        String path = templateService.getPathToFile(templateDTO);
        assertEquals("/home/slj/projects/KinoCMS-R.Pravnyk/uploads/templates/file/1/nameFile1", path);
        verify(templateRepository, times(1)).save(any(Template.class));
        verify(templateRepository, times(1)).findById(null);
    }

    @Test
    void shouldGetPathToFile_WhenIdTemplateIdIsZeroAndReturnNull() {
        TemplateDTO templateDTO = new TemplateDTO(1L,
                null,
                null);
        when(templateService.getById(templateDTO.getId())).thenReturn(Optional.empty());
        String result = templateService.getPathToFile(templateDTO);
        assertNull(result,"Path to file should be null");
        verify(templateRepository, times(1)).findById(1L);
    }
    @Test
    void shouldGetPathToFile_WhenIdTemplateIdIsNotZeroAndReturnNull() {
        TemplateDTO templateDTO = new TemplateDTO(0L,
                null,
                null);
        when(templateService.getById(null)).thenReturn(Optional.empty());
        String result = templateService.getPathToFile(templateDTO);
        assertNull(result,"Path to file should be null");
        verify(templateRepository, times(1)).findById(null);
    }
}