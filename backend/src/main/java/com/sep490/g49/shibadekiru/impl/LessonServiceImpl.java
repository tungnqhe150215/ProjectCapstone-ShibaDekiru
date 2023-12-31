package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.BookRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.ILessonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements ILessonService {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
//        return lessonRepository.findAll().stream().peek(data ->
//                data.setImage(googleDriveService.getFileUrl(data.getImage()))
//        ).collect(Collectors.toList());
    }

    @Override
    public List<Lesson> getLessonPartByBook(Book book) {
        return lessonRepository.findByBook(book);
    }

    @Override
    public Lesson createLesson(Lesson lesson) {

        LocalDateTime currentDateTime = LocalDateTime.now();


        String name = lesson.getName();
        String content = lesson.getContent();
        boolean status = lesson.getStatus();
        String image = lesson.getImage();
        Long bookId = lesson.getBook().getBookId();


        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {

            Book book = bookOptional.get();


            Lesson lesson1 = new Lesson();
            lesson1.setName(name);
            lesson1.setContent(content);
            lesson1.setCreatedAt(currentDateTime);
            lesson1.setStatus(status);
            lesson1.setImage(image);
            lesson1.setBook(book);


            return lessonRepository.save(lesson1);

        } else {
            throw new ResourceNotFoundException("Lesson can't be added.");
        }
    }

    @Override
    @Transactional
    public Lesson updateLesson(Long lessonId, Lesson updatedLesson) {
        Optional<Lesson> existingLesson = lessonRepository.findById(lessonId);

        if (existingLesson.isPresent()) {
            Lesson lesson = existingLesson.get();

            Optional<Long> bookIdOptional = lessonRepository.findBookIdByLessonId(lessonId);
            Long bookId = bookIdOptional.orElse(null);
            System.out.println("Book id trong service: " + bookId);


            lesson.setName(updatedLesson.getName());
            lesson.setContent(updatedLesson.getContent());
            lesson.setStatus(updatedLesson.getStatus());

            if (updatedLesson.getImage().length() > 0) {
                googleDriveService.deleteFile(lesson.getImage());
                lesson.setImage(updatedLesson.getImage());
            } else {
                lesson.setImage(lesson.getImage());
            }


            if (bookId != null) {
                lesson.getBook().setBookId(bookId);
            }

            return lessonRepository.save(lesson);
        } else {
            throw new ResourceNotFoundException("Lesson not found with: " + lessonId);
        }
    }


    @Override
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResourceNotFoundException("Lesson not exist with id:" + lessonId));
        if (lesson.getImage() != null) {
            System.out.println("Đã xóa trong đây.");
            googleDriveService.deleteFile(lesson.getImage());

        }

        lessonRepository.delete(lesson);
    }

    @Override
    public Lesson getLessonById(Long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId).orElse(null);

        if (lesson == null) {
            throw new ResourceNotFoundException("Lesson not found with id: " + lessonId);
        }
        return lesson;
    }

}
