package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Book;

import java.util.List;

public interface IBookService {

    List<Book> getAllBooks();

    Book createBook(Book book);

    Book updateBook(Long bookId, Book updatedBook);

    void deleteBook(Long bookId);

    Book getBookById(Long bookId);

}
