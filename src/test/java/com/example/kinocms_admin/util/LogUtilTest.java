package com.example.kinocms_admin.util;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogUtilTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private Page<User> page;
    private final static String item = "item";
    private final static String name = "name";
    private final static int size = 5;
    private final static int id = 1;

    @Test
    void shouldLogPageInfo() {
        Pageable pageable = PageRequest.of(0, 2);
        Pageable modifyPageable = PageRequest.of(pageable.getPageNumber(), 2);
        when(userRepository.findAll(modifyPageable)).thenReturn(page);
        Page<User> page = userRepository.findAll(modifyPageable);
        LogUtil.logPageInfo(page, "some text");
    }

    @Test
    void shouldLogSizeInfo() {
        LogUtil.logSizeInfo(item, size);
    }

    @Test
    void shouldLogSaveInfo() {
        LogUtil.logSaveInfo(item, "id", id);
    }

    @Test
    void shouldLogSaveInfo2() {
        LogUtil.logSaveInfo(item, (long) id);
    }

    @Test
    void shouldLogDeleteInfo_WhenAttributeIsNull() {
        LogUtil.logDeleteInfo(item, "id", null);
    }

    @Test
    void shouldLogDeleteInfo_WhenAttributeIsNotNull() {
        LogUtil.logDeleteInfo(item, "id", id);
    }

    @Test
    void shouldLogDeleteAllInfo() {
        LogUtil.logDeleteAllInfo(item, "id");
    }

    @Test
    void shouldLogDeleteGetNotification() {
        LogUtil.logGetNotification(item, "id", id);
    }

    @Test
    void shouldLogDeleteGetNotification2() {
        LogUtil.logGetNotification(item, "id", "name", id, name);
    }

    @Test
    void shouldLogDeleteGetAllNotification() {
        LogUtil.logGetAllNotification("items");
    }

    @Test
    void shouldLogDeleteGetAllNotification_WithAttribute() {
        LogUtil.logGetAllNotification("items", "id", id);
    }

    @Test
    void shouldLogGetAllNotification() {
        Pageable pageable = PageRequest.of(0, 10);
        LogUtil.logGetAllNotification(item, pageable);
    }

    @Test
    void shouldLogSaveNotification_WhenAttribute_WithOneAttribute() {
        LogUtil.logSaveNotification(item, "id", id);
    }

    @Test
    void shouldLogSaveNotification_WhenAttributeIsNull_WithOneAttribute() {
        LogUtil.logSaveNotification(item, "id", null);
    }

    @Test
    void shouldLogSaveNotification_WhenAttribute_WithTwoAttribute() {
        LogUtil.logSaveNotification(item, "id", "name", id, name, (long) id);
    }

    @Test
    void shouldLogSaveNotification_WhenIdIsNull_WithTwoAttribute() {
        LogUtil.logSaveNotification(item, "id", "name", id, name, null);
    }

    @Test
    void shouldLogSaveNotification_WhenAttributeIsNull_WithTwoAttribute() {
        LogUtil.logSaveNotification(item, "id", null);
    }

    @Test
    void shouldLogDeleteNotification() {
        LogUtil.logDeleteNotification(item, "id", id);
    }

    @Test
    void shouldLogDeleteAllNotification_WithOneParameters() {
        LogUtil.logDeleteAllNotification(item);
    }

    @Test
    void shouldLogDeleteAllNotification_WithThreeParameters() {
        LogUtil.logDeleteAllNotification(item, "id", id);
    }

    @Test
    void shouldLogGetInfo_WhenFound() {
        LogUtil.logGetInfo(item, "id", id, true);
    }

    @Test
    void shouldLogGetInfo_WhenNotFound() {
        LogUtil.logGetInfo(item, "id", id, false);
    }

    @Test
    void shouldLogGetInfo_WhenFound_WithTwoParameters() {
        LogUtil.logGetInfo(item, "id", "name", id, name, true);
    }

    @Test
    void shouldLogGetInfo_WhenNotFound_WithTwoParameters() {
        LogUtil.logGetInfo(item, "id", "name", id, name, false);
    }

    @Test
    void shouldLogErrorSavingFiles() {
        IOException exception = new IOException("File error");
        LogUtil.logErrorSavingFiles(exception);
    }

    @Test
    void shouldLogSaveAllNotifications() {
        LogUtil.logSaveAllNotifications(item);
    }

    @Test
    void shouldLogSaveAllInfo() {
        LogUtil.logSaveAllInfo(item);
    }

    @Test
    void shouldLogItemNull() {
        LogUtil.logItemNull(item);
    }

    @Test
    void shouldLogUpdateNotifications() {
        LogUtil.logUpdateNotifications(item, "id", id);
    }

    @Test
    void shouldLogUpdateInfo() {
        LogUtil.logUpdateInfo(item, "id", id);
    }

    @Test
    void shouldClearMarksNotification() {
        LogUtil.logClearMarksNotification(item);
    }

    @Test
    void shouldClearMarksInfo() {
        LogUtil.logClearMarksInfo(item);
    }
}