package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.SectionType;
import com.sep490.g49.shibadekiru.entity.Test;
import com.sep490.g49.shibadekiru.entity.TestSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestSectionRepository extends JpaRepository<TestSection,Long> {

    TestSection findBySectionId(Long sectionId);

    List<TestSection> findTestSectionsByTest(Test test);

    List<TestSection> findTestSectionsBySectionTypeAndAndTest(SectionType sectionType, Test test);
}
