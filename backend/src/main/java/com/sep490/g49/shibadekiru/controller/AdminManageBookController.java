package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.dto.BookDto;
import com.sep490.g49.shibadekiru.service.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
