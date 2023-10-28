package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.BookDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.service.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
public class AdminManageBookController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IBookService iBookService;

    @GetMapping("/book")
    public List<BookDto> getAllBook() {
        return iBookService.getAllBooks().stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }


    @PostMapping("/book")
    public ResponseEntity<BookDto> createBook (@RequestBody BookDto bookDto) {
        Book bookRequest = modelMapper.map(bookDto, Book.class);

        Book book = iBookService.createBook(bookRequest);

        BookDto bookResponse = modelMapper.map(book, BookDto.class);

        return new ResponseEntity<BookDto>(bookResponse, HttpStatus.CREATED);
    }
}
