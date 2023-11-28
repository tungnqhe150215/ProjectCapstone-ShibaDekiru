package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.BookServiceImpl;
import com.sep490.g49.shibadekiru.repository.BookRepository;
import com.sep490.g49.shibadekiru.service.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllBooks() {
        // Create mock data
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setBookId(1L);
        book1.setName("Book 1");
        book1.setDescription("Description 1");
        book1.setImage("Image 1");

        Book book2 = new Book();
        book2.setBookId(2L);
        book2.setName("Book 2");
        book2.setDescription("Description 2");
        book2.setImage("Image 2");

        books.add(book1);
        books.add(book2);

        // Set up mock behavior
        when(bookRepository.findAll()).thenReturn(books);

        // Call the service
        List<Book> result = bookService.getAllBooks();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getBookId());
        assertEquals("Book 1", result.get(0).getName());
        assertEquals("Description 1", result.get(0).getDescription());
        assertEquals("Image 1", result.get(0).getImage());
        assertEquals(2L, result.get(1).getBookId());
        assertEquals("Book 2", result.get(1).getName());
        assertEquals("Description 2", result.get(1).getDescription());
        assertEquals("Image 2", result.get(1).getImage());

        // Verify that the findAll method is called once
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void createBook() {
        // Create a new book
        Book book = new Book();
        book.setBookId(1L);
        book.setName("Book 1");
        book.setDescription("Description 1");
        book.setImage("Image 1");

        // Set up mock behavior
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Call the service
        Book result = bookService.createBook(book);

        // Verify the result
        assertNotNull(result);
        assertEquals(1L, result.getBookId());
        assertEquals("Book 1", result.getName());
        assertEquals("Description 1", result.getDescription());
        assertEquals("Image 1", result.getImage());

        // Verify that the save method is called once with the book parameter
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook_existingBook() {
        // Create mock data
        Book existingBook = new Book();
        existingBook.setBookId(1L);
        existingBook.setName("Book 1");
        existingBook.setDescription("Description 1");
        existingBook.setImage("Image 1");

        // Create the update book
        Book updatedBook = new Book();
        updatedBook.setName("Updated Book");
        updatedBook.setDescription("Updated Description");
        updatedBook.setImage("Updated Image");

        // Set up mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Call the service
        Book result = bookService.updateBook(1L, updatedBook);

        // Verify the result
        assertNotNull(result);
        assertEquals("Updated Book", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("Updated Image", result.getImage());

        // Verify that the findById and save methods are called once with the correct parameters
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void updateBook_nonExistingBook() {
        // Create the update book
        Book updatedBook = new Book();
        updatedBook.setName("Updated Book");
        updatedBook.setDescription("Updated Description");
        updatedBook.setImage("Updated Image");

        // Set up mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service and verify that it throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> bookService.updateBook(1L, updatedBook));

        // Verify that the findById method is called once with the correct parameter
        verify(bookRepository, times(1)).findById(1L);

        // Verify that the save method is never called
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void deleteBook_existingBook() {
        // Create mock data
        Book existingBook = new Book();
        existingBook.setBookId(1L);
        existingBook.setName("Book 1");
        existingBook.setDescription("Description 1");
        existingBook.setImage("Image 1");

        // Set up mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        // Call the service
        bookService.deleteBook(1L);

        // Verify that the findById and delete methods are called once with the correct parameter
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(existingBook);
    }

    @Test
    void deleteBook_nonExistingBook() {
        // Set up mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service and verify that it throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(1L));

        // Verify that the findById method is called once with the correct parameter
        verify(bookRepository, times(1)).findById(1L);

        // Verify that the delete method is never called
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void getBookById_existingBook() {
        // Create mock data
        Book existingBook = new Book();
        existingBook.setBookId(1L);
        existingBook.setName("Book 1");
        existingBook.setDescription("Description 1");
        existingBook.setImage("Image 1");

        // Set up mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        // Call the service
        Book result = bookService.getBookById(1L);

        // Verify the result
        assertNotNull(result);
        assertEquals(1L, result.getBookId());
        assertEquals("Book 1", result.getName());
        assertEquals("Description 1", result.getDescription());
        assertEquals("Image 1", result.getImage());

        // Verify that the findById method is called once with the correct parameter
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void getBookById_nonExistingBook() {
        // Set up mock behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service and verify that it throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(1L));

        // Verify that the findById method is called once with the correct parameter
        verify(bookRepository, times(1)).findById(1L);
    }
}