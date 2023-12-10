package com.sep490.g49.shibadekiru.controller.admin;

import com.sep490.g49.shibadekiru.dto.BookDto;
import com.sep490.g49.shibadekiru.dto.UserAccountDto;
import com.sep490.g49.shibadekiru.entity.Book;
import com.sep490.g49.shibadekiru.entity.RoleType;
import com.sep490.g49.shibadekiru.impl.AuthenticationServiceImpl;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IBookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminManageBookController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IBookService iBookService;

    @Autowired
    private GoogleDriveService googleDriveService;

    @GetMapping("/book")
    public List<BookDto> getAllBook() {
        return iBookService.getAllBooks().stream().map(book -> modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable (name = "id") Long bookId) {
        Book book = iBookService.getBookById(bookId);

        book.setImage(googleDriveService.getFileUrl(book.getImage()));

        BookDto bookResponse = modelMapper.map(book, BookDto.class);


        return ResponseEntity.ok().body(bookResponse);
    }

    @PostMapping("/book")
    public ResponseEntity<BookDto> createBook (@RequestBody BookDto bookDto) {
        Book bookRequest = modelMapper.map(bookDto, Book.class);

        Book book = iBookService.createBook(bookRequest);

        BookDto bookResponse = modelMapper.map(book, BookDto.class);

        return new ResponseEntity<BookDto>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable(name = "id") Long bookId, @RequestBody BookDto bookDto) {
        Book bookRequest = modelMapper.map(bookDto, Book.class);

        Book book = iBookService.updateBook(bookId, bookRequest);

        BookDto bookResponse = modelMapper.map(book, BookDto.class);

        return ResponseEntity.ok().body(bookResponse);

    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBook(@PathVariable (name = "id") Long bookId) {
        iBookService.deleteBook(bookId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
