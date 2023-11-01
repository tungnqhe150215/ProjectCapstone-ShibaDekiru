package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestByTestId(Long id);

    List<Test> findTestsByLecture(Lectures lectures);
}
