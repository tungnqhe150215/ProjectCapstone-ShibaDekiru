package com.sep490.g49.shibadekiru.service;

import com.sep490.g49.shibadekiru.entity.Lesson;
import com.sep490.g49.shibadekiru.entity.Writing;
import com.sep490.g49.shibadekiru.entity.WritingQuestion;

import java.util.List;

public interface IWritingQuestionService {
    public List<WritingQuestion> getWritingQuestionByWriting(Writing writing);

    public WritingQuestion getWritingQuestionById(Long id);

    public WritingQuestion createWritingQuestion(WritingQuestion writingQuestion);

    public WritingQuestion updateWritingQuestion(Long id,WritingQuestion writingQuestion);

    void deleteWritingQuestion(Long id);
}
