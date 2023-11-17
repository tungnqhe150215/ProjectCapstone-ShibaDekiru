package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.ClassWork;
import com.sep490.g49.shibadekiru.entity.Exercise;
import com.sep490.g49.shibadekiru.entity.WritingExercise;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.ExerciseRepository;
import com.sep490.g49.shibadekiru.repository.WritingExerciseRepository;
import com.sep490.g49.shibadekiru.service.IWritingExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WritingExerciseServiceImpl implements IWritingExerciseService {
    
    @Autowired
    private WritingExerciseRepository writingExerciseRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public List<WritingExercise> getWritingExerciseByExercise(Exercise exercise) {
        return writingExerciseRepository.findWritingExerciseByExercise(exercise);
    }

    @Override
    public WritingExercise getWritingExerciseById(Long id) {
        WritingExercise writingExercise = writingExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        return writingExercise;
    }

    @Override
    public WritingExercise createWritingExercise(WritingExercise writingExerciseRequest) {
        return writingExerciseRepository.save(writingExerciseRequest);
    }

    @Override
    public WritingExercise updateWritingExercise(Long id, WritingExercise writingExerciseRequest) {
        WritingExercise writingExercise = writingExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        writingExercise.setQuestion(writingExerciseRequest.getQuestion());
        return writingExerciseRepository.save(writingExercise);
    }

    @Override
    public void deleteWritingExercise(Long id) {
        WritingExercise writingExercise = writingExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        writingExerciseRepository.delete(writingExercise);
    }

    @Override
    public List<WritingExercise> getWritingExerciseByClasswork(ClassWork classWork) {
        List<Exercise> exerciseList = exerciseRepository.findExercisesByClassWork(classWork);
        List<WritingExercise> writingExerciseList = new ArrayList<>();
        exerciseList.forEach(exercise -> {
            List<WritingExercise> draftWritingExerciseList = writingExerciseRepository.findWritingExerciseByExercise(exercise);
            writingExerciseList.addAll(draftWritingExerciseList);
        });
        return writingExerciseList;
    }
}
