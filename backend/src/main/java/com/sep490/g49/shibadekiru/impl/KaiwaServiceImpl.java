package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Kaiwa;
import com.sep490.g49.shibadekiru.repository.KaiwaRepository;
import com.sep490.g49.shibadekiru.service.GoogleDriveService;
import com.sep490.g49.shibadekiru.service.IKaiwaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KaiwaServiceImpl implements IKaiwaService {

    @Autowired
    private KaiwaRepository kaiwaRepository;

    @Autowired
    private GoogleDriveService googleDriveService;

    @Override
    public List<Kaiwa> getKaiwaPartByLesson(Lesson lesson) {
        return kaiwaRepository.findKaiwasByLesson(lesson).stream().peek(data ->
                {
                    if (data.getLink().length() > 0 && !data.getLink().equals("")) {
                        data.setLink(googleDriveService.getFileUrl(data.getLink()));
                    }
                }

        ).collect(Collectors.toList());
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
        kaiwa.setScript(kaiwaRequest.getScript());
        kaiwa.setTitle(kaiwaRequest.getTitle());

        if (kaiwaRequest.getLink().length() > 0) {
            googleDriveService.deleteFile(kaiwa.getLink());
            System.out.println("File đã xóa : " + kaiwa.getLink());
            kaiwa.setLink(kaiwaRequest.getLink());
        }
        else {
            kaiwa.setLink(kaiwa.getLink());
        }

        return kaiwaRepository.save(kaiwa);
    }

    @Override
    public void deleteKaiwa(Long id) {
        Kaiwa kaiwa = kaiwaRepository.findKaiwaByKaiwaId(id);

        if (kaiwa.getLink() != null) {
            googleDriveService.deleteFile(kaiwa.getLink());
            System.out.println("File đã xóa : " + kaiwa.getLink());
        }

        kaiwaRepository.delete(kaiwa);
    }
}
