package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassWorkRepository extends JpaRepository<ClassWork, Long> {
}
