package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Lectures;
import com.sep490.g49.shibadekiru.entity.UserAccount;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.LecturersRepository;
import com.sep490.g49.shibadekiru.repository.UserAccountRepository;
import com.sep490.g49.shibadekiru.service.ILecturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LecturesServiceImpl implements ILecturesService {

    @Autowired
    private LecturersRepository lecturersRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;



    public void createLecturerFromUserAccount(UserAccount userAccount) {

         String memberId = userAccount.getMemberId();

        Optional<UserAccount> userAccountOptional = userAccountRepository.findByMemberId(memberId);
        if (userAccountOptional.isPresent()) {

            UserAccount userAccount1 = userAccountOptional.get();

            Lectures lectures = new Lectures();
            lectures.setFirstName(userAccount1.getNickName());
            lectures.setLastName(userAccount1.getNickName());
            lectures.setEmail(userAccount1.getEmail());
            lectures.setPhone("");
            lectures.setAvatar("");
            lectures.setUserAccount(userAccount1);

            lecturersRepository.save(lectures);
        }

    }

    @Override
    public Lectures getLectureById(Long lectureId) {
        Lectures lectures = lecturersRepository.findById(lectureId).orElse(null);
        if (lectures == null) {
            throw new ResourceNotFoundException("Lecture not found with id: " +  lectureId);
        }
        return lectures;
    }


}
