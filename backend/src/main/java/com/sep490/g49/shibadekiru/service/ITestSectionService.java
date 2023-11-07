package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.SectionType;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;

import java.util.List;

public interface ITestSectionService {
    List<TestSection> getTestSectionByTypeAndTest(SectionType sectionType, Test test);

    List<TestSection> getTestSectionByTest(Test test);

    TestSection getTestSectionById(Long id);

    TestSection createTestSection(TestSection testSectionRequest);

    TestSection updateTestSection(Long id, TestSection testSectionRequest);

    void deleteTestSection(Long id);
}
