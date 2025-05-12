package com.pack.demo.Services;

import com.pack.demo.Model.QuestionDTO;
import com.pack.demo.Model.QuestionModel;
import com.pack.demo.Model.TimeQuestion;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    void saveQuestion(QuestionModel questionModel);

    List<QuestionModel> getAllQuestions();

    List<QuestionModel> getRandomQuestions(int count);

    List<QuestionModel> getQuestionsByCategory(String category);

    int evaluateQuiz(List<QuestionModel> questions, Map<String, String> answers, List<QuestionDTO> resultList);

    TimeQuestion getDailtOne();
}
