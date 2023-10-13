package com.sep490.g49.shibadekiru.controller;

import com.sep490.g49.shibadekiru.service.IWritingQuestionService;
import com.sep490.g49.shibadekiru.service.IWritingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("api/admin/lesson")
public class AdminManageWritingController {
    @Autowired
    private IWritingService iWritingService;

    @Autowired
    private IWritingQuestionService iWritingQuestionService;

    
}
