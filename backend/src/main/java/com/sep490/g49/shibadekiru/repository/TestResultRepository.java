package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findTestResultsByTestSection(TestSection test);

    TestResult findTestResultsByStudentAndTestSection(Student student,TestSection testSection);

    List<TestResult> findTestResultsByClassTestAssign(ClassTestAssign classTestAssign);

    List<TestResult> findTestResultsByStudentAndClassTestAssign(Student student,ClassTestAssign classTestAssign);

    TestResult findTestResultsByStudentAndClassTestAssignAndTestSection(Student student, ClassTestAssign classTestAssign, TestSection testSection);
}
