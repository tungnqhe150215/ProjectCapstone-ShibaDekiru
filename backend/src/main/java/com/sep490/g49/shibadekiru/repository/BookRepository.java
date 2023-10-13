package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
