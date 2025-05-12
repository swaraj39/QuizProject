package com.pack.demo.Implementation;

import com.pack.demo.Model.QuestionDTO;
import com.pack.demo.Model.QuestionModel;
import com.pack.demo.Model.TimeQuestion;
import com.pack.demo.Repository.DailyRepo;
import com.pack.demo.Repository.QuestionRepo;
import com.pack.demo.Services.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepo questionRepository;

    @Autowired
    private DailyRepo dailyRepo;

    @Override
    public void saveQuestion(QuestionModel questionModel) {
        questionRepository.save(questionModel);
    }

    @Override
    public List<QuestionModel> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<QuestionModel> getRandomQuestions(int count) {
        List<QuestionModel> allQuestions = questionRepository.findAll();
        Collections.shuffle(allQuestions);

        return allQuestions.subList(0, Math.min(count, allQuestions.size()));
    }

    @Override
    public List<QuestionModel> getQuestionsByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return questionRepository.findAll();
        }
        return questionRepository.findByCateogry(category);
    }

    @Override
    public int evaluateQuiz(List<QuestionModel> questions, Map<String, String> answers, List<QuestionDTO> resultList) {
        int score = 0;

        for (QuestionModel question : questions) {
            String questionId = String.valueOf(question.getId());
            String userAnswer = answers.get("q" + questionId);

            if (userAnswer != null) {
                userAnswer = userAnswer.trim(); // Clean user input
            }

            String correctAnswer = question.getCorrectans();
            if (correctAnswer != null) {
                correctAnswer = correctAnswer.trim(); // Clean correct answer
            }

            boolean isCorrect = correctAnswer != null && correctAnswer.equalsIgnoreCase(userAnswer);
            if (isCorrect) {
                score++;
            }

            // Build DTO
            QuestionDTO dto = new QuestionDTO();
            dto.setId(questionId);
            dto.setQuestion(question.getQuestion());
            dto.setOption1(question.getOption1());
            dto.setOption2(question.getOption2());
            dto.setOption3(question.getOption3());
            dto.setOption4(question.getOption4());
            dto.setCorrectAnswer(correctAnswer);
            dto.setUserAnswer(userAnswer);
            dto.setCorrect(isCorrect);
            resultList.add(dto);
        }

        return score;
    }

    @Override
    public TimeQuestion getDailtOne() {
        LocalDate currentDate = LocalDate.now();
        Optional<TimeQuestion> timequestion = dailyRepo.findByDate(currentDate);
        if (timequestion.isPresent()) {
            return dailyRepo.findById(timequestion.get().getId()).orElse(null);
        } else {
            List<QuestionModel> allQuestions = questionRepository.findAll();
            Collections.shuffle(allQuestions);
            QuestionModel questionModel = allQuestions.get(0);
            TimeQuestion timeQuestion = new TimeQuestion();
            timeQuestion.setId(questionModel.getId());
            timeQuestion.setQuestion(questionModel.getQuestion());
            timeQuestion.setOption1(questionModel.getOption1());
            timeQuestion.setOption2(questionModel.getOption2());
            timeQuestion.setOption3(questionModel.getOption3());
            timeQuestion.setOption4(questionModel.getOption4());
            timeQuestion.setDate(currentDate);
            timeQuestion.setAnswer(questionModel.getCorrectans());
            timeQuestion.setReason("See it and solve it yourself"); // Set a default reason or modify as needed
            dailyRepo.save(timeQuestion); // Save the new question for today
            return timeQuestion;
        }

        // Return a random question
    }

}
