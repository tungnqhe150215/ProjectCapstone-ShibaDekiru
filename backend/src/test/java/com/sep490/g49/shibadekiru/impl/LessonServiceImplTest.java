package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.BookRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class LessonServiceImplTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void getAllLessons() {
        // Mocking the behavior of the lessonRepository.findAll() method
        List<Lesson> mockLessons = new ArrayList<>();
        Mockito.when(lessonRepository.findAll()).thenReturn(mockLessons);

        List<Lesson> result = lessonService.getAllLessons();

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockLessons list is empty
    }

    @Test
    void getLessonPartByBook() {
        // Mocking the behavior of the lessonRepository.findByBook() method
        Book book = new Book();
        List<Lesson> mockLessons = new ArrayList<>();
        Mockito.when(lessonRepository.findByBook(book)).thenReturn(mockLessons);

        List<Lesson> result = lessonService.getLessonPartByBook(book);

        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the mockLessons list is empty
    }

    @Test
    void createLesson() {
        // Mocking the behavior of the bookRepository.findById() method
        Long bookId = 1L;
        Book book = new Book();
        book.setBookId(bookId);
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Mocking the behavior of the lessonRepository.save() method
        Lesson savedLesson = new Lesson();
        Mockito.when(lessonRepository.save(any(Lesson.class))).thenReturn(savedLesson);

        // Creating a test lesson
        Lesson testLesson = new Lesson();
        testLesson.setBook(book);

        Lesson result = lessonService.createLesson(testLesson);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void updateLesson() {
        Long lessonId = 1L;

        // Mocking the behavior of the lessonRepository.findById() method
        Lesson existingLesson = new Lesson();
        existingLesson.setLessonId(lessonId);
        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(existingLesson));

        // Mocking the behavior of the lessonRepository.findBookIdByLessonId() method
        Mockito.when(lessonRepository.findBookIdByLessonId(lessonId)).thenReturn(Optional.of(1L));

        // Mocking the behavior of the lessonRepository.save() method
        Lesson updatedLesson = new Lesson();
        updatedLesson.setLessonId(lessonId);
        Mockito.when(lessonRepository.save(any(Lesson.class))).thenReturn(updatedLesson);

        Lesson result = lessonService.updateLesson(lessonId, updatedLesson);

        assertNotNull(result);
        assertEquals(lessonId, result.getLessonId());
        // Add more assertions as needed
    }

    @Test
    void deleteLesson() {
        Long lessonId = 1L;

        // Mocking the behavior of the lessonRepository.findById() method
        Lesson existingLesson = new Lesson();
        existingLesson.setLessonId(lessonId);
        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(existingLesson));

        assertDoesNotThrow(() -> lessonService.deleteLesson(lessonId));
    }

    @Test
    void getLessonById() {
        Long lessonId = 1L;

        // Mocking the behavior of the lessonRepository.findById() method
        Lesson existingLesson = new Lesson();
        existingLesson.setLessonId(lessonId);
        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(existingLesson));

        Lesson result = lessonService.getLessonById(lessonId);

        assertNotNull(result);
        assertEquals(lessonId, result.getLessonId());
    }
}
