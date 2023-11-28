package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.dto.ClassWorkDto;
import com.sep490.g49.shibadekiru.entity.Class;
import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.impl.ClassWorkServiceImpl;
import com.sep490.g49.shibadekiru.repository.ClassRepository;
import com.sep490.g49.shibadekiru.repository.ClassWorkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassWorkServiceImplTest {

    @InjectMocks
    private ClassWorkServiceImpl classWorkService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private ClassWorkRepository classWorkRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClassWorkByClass() {
        // Mock data
        Class myC = new Class();
        List<ClassWork> classWorkList = new ArrayList<>();

        // Mock behavior
        when(classWorkRepository.findByMyC(myC)).thenReturn(classWorkList);

        // Call the method
        List<ClassWork> result = classWorkService.getClassWorkByClass(myC);

        // Assertions
        assertEquals(classWorkList, result);

        // Verify interactions
        verify(classWorkRepository, times(1)).findByMyC(myC);
    }

    @Test
    void createClassWork() {
        // Mock data
        ClassWorkDto classWorkDto = new ClassWorkDto();
        classWorkDto.setMyCId(1L);

        ClassWork classWork = new ClassWork();
        Class aclass = new Class();

        // Mock behavior
        when(modelMapper.map(classWorkDto, ClassWork.class)).thenReturn(classWork);
        when(classRepository.findById(classWorkDto.getMyCId())).thenReturn(Optional.of(aclass));
        when(classWorkRepository.save(classWork)).thenReturn(classWork);

        // Call the method
        ClassWorkDto result = classWorkService.createClassWork(classWorkDto);

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(modelMapper, times(1)).map(classWorkDto, ClassWork.class);
        verify(classRepository, times(1)).findById(classWorkDto.getMyCId());
        verify(classWorkRepository, times(1)).save(classWork);
    }

    @Test
    void updateClassWork() {
        // Mock data
        Long classWorkId = 1L;
        ClassWork classWork = new ClassWork();

        // Mock behavior
        when(classWorkRepository.findById(classWorkId)).thenReturn(Optional.of(classWork));
        when(classWorkRepository.save(classWork)).thenReturn(classWork);

        // Call the method
        ClassWork result = classWorkService.updateClassWork(classWorkId, classWork);

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(classWorkRepository, times(1)).findById(classWorkId);
        verify(classWorkRepository, times(1)).save(classWork);
    }

    @Test
    void deleteClassWork() {
        // Mock data
        Long classWorkId = 1L;
        ClassWork classWork = new ClassWork();

        // Mock behavior
        when(classWorkRepository.findById(classWorkId)).thenReturn(Optional.of(classWork));

        // Call the method
        classWorkService.deleteClassWork(classWorkId);

        // Verify interactions
        verify(classWorkRepository, times(1)).findById(classWorkId);
        verify(classWorkRepository, times(1)).delete(classWork);
    }

    @Test
    void updateIsLocked() {
        // Mock data
        Long classWorkId = 1L;
        ClassWork classWork = new ClassWork();
        classWork.setIsLocked(false);

        // Mock behavior
        when(classWorkRepository.findById(classWorkId)).thenReturn(Optional.of(classWork));
        when(classWorkRepository.save(classWork)).thenReturn(classWork);

        // Call the method
        classWorkService.updateIsLocked(classWorkId);

        // Verify interactions
        verify(classWorkRepository, times(1)).findById(classWorkId);
        verify(classWorkRepository, times(1)).save(classWork);

        // Assertions
        assertTrue(classWork.getIsLocked());
    }

    @Test
    void getClassWorkById() {
        // Mock data
        Long classWorkId = 1L;
        ClassWork classWork = new ClassWork();

        // Mock behavior
        when(classWorkRepository.findById(classWorkId)).thenReturn(Optional.of(classWork));

        // Call the method
        ClassWork result = classWorkService.getClassWorkById(classWorkId);

        // Assertions
        assertNotNull(result);

        // Verify interactions
        verify(classWorkRepository, times(1)).findById(classWorkId);
    }
}
