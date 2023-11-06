package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.SectionType;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import com.sep490.g49.shibadekiru.service.ITestSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestSectionServiceImpl implements ITestSectionService {

    @Autowired
    private TestSectionRepository testSectionRepository;

    @Override
    public List<TestSection> getTestSectionByTypeAndTest(SectionType sectionType,Test test) {
        return testSectionRepository.findTestSectionsByTest(test);
    }

    @Override
    public List<TestSection> getTestSectionByTest(Test test) {
        return testSectionRepository.findTestSectionsByTest(test);
    }

    @Override
    public TestSection getTestSectionById(Long id) {
        TestSection testSection = testSectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return testSection;
    }

    @Override
    public TestSection createTestSection(TestSection testSectionRequest) {
        return testSectionRepository.save(testSectionRequest);
    }

    @Override
    public TestSection updateTestSection(Long id, TestSection testSectionRequest) {
        TestSection testSection = testSectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return testSectionRepository.save(testSection);
    }

    @Override
    public void deleteTestSection(Long id) {
        TestSection testSection = testSectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        testSectionRepository.delete(testSection);
    }

}
