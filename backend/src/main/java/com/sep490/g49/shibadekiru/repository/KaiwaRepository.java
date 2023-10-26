package com.sep490.g49.shibadekiru.repository;

import com.sep490.g49.shibadekiru.entity.Kaiwa;
import com.sep490.g49.shibadekiru.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KaiwaRepository extends JpaRepository<Kaiwa,Long> {
    List<Kaiwa> findKaiwasByLesson(Lesson lesson);

    Kaiwa findKaiwaByKaiwaId(Long id);
}
