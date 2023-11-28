package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.LecturesDto;
import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class LecturesServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LecturersRepository lecturersRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private LecturesServiceImpl lecturesService;

    @Test
    void createLecturerFromUserAccount() {
        LecturesDto lecturesDto = new LecturesDto();
        lecturesDto.setMemberId("exampleMemberId");

        Lectures lectures = new Lectures();
        lectures.setLectureId(1L);

        UserAccount userAccount = new UserAccount();
        userAccount.setMemberId("exampleMemberId");

        Mockito.when(modelMapper.map(lecturesDto, Lectures.class)).thenReturn(lectures);
        Mockito.when(userAccountRepository.findByMemberId("exampleMemberId")).thenReturn(userAccount);
        Mockito.when(lecturersRepository.save(any(Lectures.class))).thenReturn(lectures);

        LecturesDto result = lecturesService.createLecturerFromUserAccount(lecturesDto);

        assertNotNull(result);
        assertEquals("exampleMemberId", result.getMemberId());
        // Add more assertions as needed
    }

    @Test
    void getLectureById() {
        Long lectureId = 1L;
        Lectures expectedLecture = new Lectures();
        expectedLecture.setLectureId(lectureId);

        Mockito.when(lecturersRepository.findById(lectureId)).thenReturn(Optional.of(expectedLecture));

        Lectures result = lecturesService.getLectureById(lectureId);

        assertNotNull(result);
        assertEquals(lectureId, result.getLectureId());
        // Add more assertions as needed
    }

    @Test
    void getByUserAccount() {
        UserAccount userAccount = new UserAccount();
        Lectures expectedLecture = new Lectures();

        Mockito.when(lecturersRepository.findByUserAccount(userAccount)).thenReturn(expectedLecture);

        Lectures result = lecturesService.getByUserAccount(userAccount);

        assertNotNull(result);
        // Add more assertions as needed
    }
}
