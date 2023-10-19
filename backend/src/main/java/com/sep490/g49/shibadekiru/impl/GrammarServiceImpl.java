package com.sep490.g49.shibadekiru.impl;

import com.sep490.g49.shibadekiru.entity.Grammar;
import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.exception.ResourceNotFoundException;
import com.sep490.g49.shibadekiru.repository.GrammarRepository;
import com.sep490.g49.shibadekiru.repository.LessonRepository;
import com.sep490.g49.shibadekiru.service.IGrammarService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GrammarServiceImpl implements IGrammarService {

    GrammarRepository grammarRepository;

    LessonRepository lessonRepository;

    @Override
    public List<Grammar> getAllGrammar() {
        return grammarRepository.findAll();
    }

    @Override
    public Grammar getGrammarById(long grammarId) {
        Grammar grammar = grammarRepository.findById(grammarId).orElse(null);

        if(grammar == null) {
            throw new ResourceNotFoundException("Grammar not found with id: " + grammarId);
        }
        return grammar;
    }

    @Override
    public Grammar createGrammar(Grammar grammar) {

        String grammarName = grammar.getGrammarName();
        String grammarStructure = grammar.getGrammarStructure();
        String description = grammar.getDescription();
        String example = grammar.getExample();
        Long lessonId = grammar.getLesson().getLessonId();

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);

        if(lessonOptional.isPresent()) {
            Lesson lesson = lessonOptional.get();

            Grammar grammar1 = new Grammar();
            grammar1.setGrammarName(grammarName);
            grammar1.setGrammarStructure(grammarStructure);
            grammar1.setDescription(description);
            grammar1.setExample(example);
            grammar1.setLesson(lesson);

            return grammarRepository.save(grammar1);
        } else {
            throw new ResourceNotFoundException("Grammar can't be added.");
        }
    }

    @Override
    public Grammar updateGrammar(Long grammarId, Grammar grammarUpdate) {

        Optional<Grammar> grammarOptional = grammarRepository.findById(grammarId);

        if(grammarOptional.isPresent()) {
            Grammar grammar = grammarOptional.get();

            grammar.setGrammarName(grammarUpdate.getGrammarName());
            grammar.setGrammarStructure(grammarUpdate.getGrammarStructure());
            grammar.setDescription(grammarUpdate.getDescription());
            grammar.setExample(grammarUpdate.getExample());
            grammar.setLesson(grammarUpdate.getLesson());

            return grammarRepository.save(grammar);
        } else {
            throw new ResourceNotFoundException("Grammar not found");
        }
    }

    @Override
    public void deleteGrammar(Long grammarId) {
        Grammar grammar = grammarRepository.findById(grammarId).orElseThrow(() -> new ResourceNotFoundException("Grammar not exist with id:" + grammarId));
        grammarRepository.delete(grammar);
    }
}
