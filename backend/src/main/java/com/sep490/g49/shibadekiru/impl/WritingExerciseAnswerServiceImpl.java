package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.*;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.*;
import com.sep490.g49.shibadekiru.service.IWritingExerciseAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WritingExerciseAnswerServiceImpl implements IWritingExerciseAnswerService {

    @Autowired
    private WritingExerciseAnswerRepository writingExerciseAnswerRepository;

    @Autowired
    private WritingExerciseRepository writingExerciseRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassWorkRepository classWorkRepository;

//    @Override
//    public List<WritingExerciseAnswer> getWritingExerciseAnswerByExercise(Exercise exercise) {
//        List<WritingExerciseAnswer> testResults = new ArrayList<>();
//        List<WritingExercise> writingExercises = writingExerciseRepository.findWritingExerciseByExercise(exercise);
//        for (WritingExercise writingExercise: writingExercises){
//            List<WritingExerciseAnswer> writingExerciseAnswers = writingExerciseAnswerRepository.f(section);
//            for (WritingExerciseAnswer result : results){
//                if (!testResults.contains(result)){
//                    testResults.add(result);
//                }
//            }
//        }
//        return testResults;
//    }

    @Override
    public WritingExerciseAnswer createWritingExerciseAnswer(WritingExerciseAnswer writingExerciseAnswer) {
        return writingExerciseAnswerRepository.save(writingExerciseAnswer);
    }

    @Override
    public WritingExerciseAnswer updateWritingExerciseAnswer(WritingExerciseAnswer answerRequest) {
        WritingExerciseAnswer writingExerciseAnswer = writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(answerRequest.getStudent(), answerRequest.getWritingExercise());
        writingExerciseAnswer.setAnswer(answerRequest.getAnswer());
        writingExerciseAnswer.setMark(answerRequest.getMark());
        return writingExerciseAnswerRepository.save(writingExerciseAnswer);
    }

    @Override
    public List<WritingExerciseAnswer> getWritingExerciseAnswerByExerciseAndStudent(Long exerciseId, Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        Exercise exercise = exerciseRepository.findByExerciseId(exerciseId);
        List<WritingExercise> writingExercises = writingExerciseRepository.findWritingExerciseByExercise(exercise);
        List<WritingExerciseAnswer> writingExerciseAnswers = new ArrayList<>();
        writingExercises.forEach(writingExercise -> {
            if (writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(student, writingExercise) != null) {
                WritingExerciseAnswer writingExerciseAnswer = writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(student, writingExercise);
                writingExerciseAnswers.add(writingExerciseAnswer);
            }
        });
        return writingExerciseAnswers;
    }

    @Override
    public List<WritingExerciseAnswer> getWritingExerciseAnswerByClassworkAndStudent(Long classworkId, Long studentId) {
        List<WritingExerciseAnswer> writingExerciseAnswers = new ArrayList<>();
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        ClassWork classWork = classWorkRepository.findById(classworkId).orElseThrow(() -> new ResourceNotFoundException("Not found data"));
        List<Exercise> exerciseList = exerciseRepository.findExercisesByClassWork(classWork);
        exerciseList.forEach(exercise -> {
            if (!writingExerciseRepository.findWritingExerciseByExercise(exercise).isEmpty()) {
                List<WritingExercise> writingExercises = writingExerciseRepository.findWritingExerciseByExercise(exercise);
                writingExercises.forEach(writingExercise -> {
                    if (writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(student, writingExercise) != null) {
                        WritingExerciseAnswer writingExerciseAnswer = writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(student, writingExercise);
                        writingExerciseAnswers.add(writingExerciseAnswer);
                    }
                });
            }
        });
        return writingExerciseAnswers;
    }

    @Override
    public boolean checkWritingExerciseAnswerExist(Student student, WritingExercise writingExercise) {
        if (writingExerciseAnswerRepository.findByStudentAndAndWritingExercise(student, writingExercise) != null)
            return true;
        else return false;
    }
}
