package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.repository.WritingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class WritingServiceImplTest {
    @Mock
    private WritingRepository writingRepository;

    @InjectMocks
    private WritingServiceImpl writingService;

    @Test
    void getWritingPartByLesson() {
        // Giả lập dữ liệu đầu vào
        Lesson lesson = new Lesson();
        lesson.setLessonId(1L);

        // Giả lập hành vi của repository
        Writing writing1 = new Writing();
        writing1.setWritingId(1L);
        writing1.setLesson(lesson);
        Writing writing2 = new Writing();
        writing2.setWritingId(2L);
        writing2.setLesson(lesson);

        when(writingRepository.findByLesson(lesson)).thenReturn(Arrays.asList(writing1, writing2));

        // Gọi phương thức cần kiểm thử
        List<Writing> writings = writingService.getWritingPartByLesson(lesson);

        // Kiểm tra kết quả
        assertNotNull(writings);
        assertEquals(2, writings.size());
    }

    @Test
    void getWritingById() {
        // Giả lập dữ liệu đầu vào
        Long writingId = 1L;

        // Giả lập hành vi của repository
        Writing writing = new Writing();
        writing.setWritingId(writingId);

        when(writingRepository.findById(writingId)).thenReturn(Optional.of(writing));

        // Gọi phương thức cần kiểm thử
        Writing retrievedWriting = writingService.getWritingById(writingId);

        // Kiểm tra kết quả
        assertNotNull(retrievedWriting);
        assertEquals(writingId, retrievedWriting.getWritingId());
    }

    @Test
    void createWriting() {
        // Giả lập dữ liệu đầu vào
        Writing writingToCreate = new Writing();
        writingToCreate.setTopic("Sample Topic");

        // Giả lập hành vi của repository
        when(writingRepository.save(any(Writing.class))).thenAnswer(invocation -> {
            Writing createdWriting = invocation.getArgument(0);
            createdWriting.setWritingId(1L); // Giả sử ID được tạo ra sau khi lưu vào cơ sở dữ liệu
            return createdWriting;
        });

        // Gọi phương thức cần kiểm thử
        Writing createdWriting = writingService.createWriting(writingToCreate);

        // Kiểm tra kết quả
        assertNotNull(createdWriting);
        assertEquals("Sample Topic", createdWriting.getTopic());
        assertNotNull(createdWriting.getWritingId());
    }

    @Test
    void updateWriting() {
        // Giả lập dữ liệu đầu vào
        Long writingId = 1L;
        Writing writingToUpdate = new Writing();
        writingToUpdate.setTopic("Updated Topic");

        // Giả lập hành vi của repository
        Writing existingWriting = new Writing();
        existingWriting.setWritingId(writingId);
        existingWriting.setTopic("Original Topic");

        when(writingRepository.findById(writingId)).thenReturn(Optional.of(existingWriting));
        when(writingRepository.save(any(Writing.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Gọi phương thức cần kiểm thử
        Writing updatedWriting = writingService.updateWriting(writingId, writingToUpdate);

        // Kiểm tra kết quả
        assertNotNull(updatedWriting);
        assertEquals("Updated Topic", updatedWriting.getTopic());
        assertEquals(writingId, updatedWriting.getWritingId());
    }

    @Test
    void deleteWriting() {
        // Giả lập dữ liệu đầu vào
        Long writingId = 1L;

        // Giả lập hành vi của repository
        Writing existingWriting = new Writing();
        existingWriting.setWritingId(writingId);

        when(writingRepository.findById(writingId)).thenReturn(Optional.of(existingWriting));

        // Gọi phương thức cần kiểm thử
        writingService.deleteWriting(writingId);

        // Kiểm tra xem phương thức delete đã được gọi trên repository chưa
        Mockito.verify(writingRepository, Mockito.times(1)).delete(existingWriting);
    }
}