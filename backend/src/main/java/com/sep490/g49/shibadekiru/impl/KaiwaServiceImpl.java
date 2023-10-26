package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Kaiwa;
import com.sep490.g49.shibadekiru.repository.KaiwaRepository;
import com.sep490.g49.shibadekiru.service.IKaiwaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KaiwaServiceImpl implements IKaiwaService {

    @Autowired
    private KaiwaRepository kaiwaRepository;

    @Override
    public List<Kaiwa> getKaiwaPartByLesson(Lesson lesson) {
        return kaiwaRepository.findKaiwasByLesson(lesson);
    }

    @Override
    public Kaiwa getKaiwaById(Long id) {
        return kaiwaRepository.findKaiwaByKaiwaId(id);
    }

    @Override
    public Kaiwa createKaiwa(Kaiwa kaiwa) {
        return kaiwaRepository.save(kaiwa);
    }

    @Override
    public Kaiwa updateKaiwa(Long id, Kaiwa kaiwaRequest) {
        Kaiwa kaiwa = kaiwaRepository.findKaiwaByKaiwaId(id);
        kaiwa.setLink(kaiwaRequest.getLink());
        kaiwa.setScript(kaiwaRequest.getScript());
        kaiwa.setTitle(kaiwaRequest.getTitle());
        return kaiwaRepository.save(kaiwa);
    }

    @Override
    public void deleteKaiwa(Long id) {
        Kaiwa kaiwa = kaiwaRepository.findKaiwaByKaiwaId(id);
        kaiwaRepository.delete(kaiwa);
    }
}
