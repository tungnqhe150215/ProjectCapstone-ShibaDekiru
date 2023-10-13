package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
}
