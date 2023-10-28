package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.BookRepository;
import com.sep490.g49.shibadekiru.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long bookId, Book updatedBook) {

        Optional<Book> existingBook =  bookRepository.findById(bookId);
        if (existingBook.isPresent()) {

            Book book = existingBook.get();

            book.setName(updatedBook.getName());
            book.setDescription(updatedBook.getDescription());
            book.setImage(updatedBook.getImage());

            return bookRepository.save(book);
        } else {
            throw new ResourceNotFoundException("Book not found with id: " + bookId);
        }

    }

    @Override
    public void deleteBook(Long bookId) {
         Book book = bookRepository.findById(bookId).orElseThrow(()->
                 new ResourceNotFoundException("Book can not delete with id: " + bookId));

         bookRepository.delete(book);
    }

    @Override
    public Book getBookById(Long bookId) {

        Book book = bookRepository.findById(bookId).orElse(null);

        if (book == null) {
            throw new ResourceNotFoundException("Book not found with id: " + bookId);
        }

        return book;
    }
}
