package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.SectionType;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.TestSectionRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.ITestSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestSectionServiceImpl implements ITestSectionService {

    @Autowired
    private TestSectionRepository testSectionRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public List<TestSection> getTestSectionByTypeAndTest(SectionType sectionType,Test test) {
        List<TestSection> testSections = testSectionRepository.findTestSectionsBySectionTypeAndAndTest(sectionType, test);

        return testSections.stream()
                .peek(data -> {
                    if (data.getSectionType().equals(SectionType.LISTENING) && data.getSectionAttach() != null && data.getSectionAttach().length() > 0) {
                        data.setSectionAttach(googleDriveService.getFileUrl(data.getSectionAttach()));
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<TestSection> getTestSectionByTest(Test test) {
        List<TestSection> testSections = testSectionRepository.findTestSectionsByTest(test);
        return testSections.stream()
                .peek(data -> {
                    if (data.getSectionType().equals(SectionType.LISTENING) && data.getSectionAttach() != null && data.getSectionAttach().length() > 0) {
                        data.setSectionAttach(googleDriveService.getFileUrl(data.getSectionAttach()));
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public TestSection getTestSectionById(Long id) {
        TestSection testSection = testSectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        if (testSection.getSectionType().equals(SectionType.LISTENING) && testSection.getSectionAttach() != null && testSection.getSectionAttach().length() > 0) {
            testSection.setSectionAttach(googleDriveService.getFileUrl(testSection.getSectionAttach()));
        }
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
        System.out.println(testSectionRequest.getSectionAttach());
        testSection.setSectionName(testSectionRequest.getSectionName());
        if (testSectionRequest.getSectionAttach().length() > 0 || !testSectionRequest.getSectionType().equals(SectionType.LISTENING)){
            if (testSectionRequest.getSectionType().equals(SectionType.LISTENING)){
                try {
                    googleDriveService.deleteFile(testSection.getSectionAttach());
                } catch (Exception e){
                    System.out.println("File không tồn tại");
                }
            }
            testSection.setSectionAttach(testSectionRequest.getSectionAttach());
        }
        return testSectionRepository.save(testSection);
    }

    @Override
    public void deleteTestSection(Long id) {
        TestSection testSection = testSectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        if (testSection.getSectionType().equals(SectionType.LISTENING)){
            googleDriveService.deleteFile(testSection.getSectionAttach());
        }
        testSectionRepository.delete(testSection);
    }

}
