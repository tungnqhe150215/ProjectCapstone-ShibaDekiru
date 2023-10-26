package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Kaiwa;
import com.sep490.g49.shibadekiru.entity.Lesson;

import java.util.List;

public interface IKaiwaService {
    List<Kaiwa> getKaiwaPartByLesson(Lesson lesson);

    Kaiwa getKaiwaById(Long id);

    Kaiwa createKaiwa(Kaiwa kaiwa);

    Kaiwa updateKaiwa(Long id, Kaiwa kaiwaRequest);

    void deleteKaiwa(Long id);
}
