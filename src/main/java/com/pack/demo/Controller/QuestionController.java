package com.pack.demo.Controller;

import com.pack.demo.Model.QuestionDTO;
import com.pack.demo.Model.QuestionModel;
import com.pack.demo.Model.TimeQuestion;
import com.pack.demo.Services.QuestionService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/ques")
public class QuestionController {

    String[] arr = new String[10];
    String arr1[] = new String[10];
    String[] a = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    public QuestionController() {
        arr[1] = "1";
        arr[0] = "0";
    }

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TimeQuestion timeQuestion;

    @RequestMapping("/home")
    public String home() {
        return "new";
    }

    @RequestMapping("/before")
    public String index() {
        return "before";
    }

    @RequestMapping("/add")
    public String addQuestion() {
        return "QuestionAdding";
    }
    
    @RequestMapping("/save")
    public String add(@ModelAttribute QuestionModel questionModel) {
        questionService.saveQuestion(questionModel);
        return "Saved";
    }

    @RequestMapping("/getall")
    public String getAll(Model model) {
        List<QuestionModel> list = questionService.getAllQuestions();
        Collections.shuffle(list);
        model.addAttribute("result", list);
        return "QuestionShow";
    }

    @PostMapping(value = "/test")
    public String showRandomQuestions(HttpSession session, Model model) {
        List<QuestionModel> randomQuestions = questionService.getRandomQuestions(10);
        session.setAttribute("randomQuestions", randomQuestions);
        model.addAttribute("result", randomQuestions);
        return "quizz";
    }

    @RequestMapping(value = "/questionOne", method = {RequestMethod.GET, RequestMethod.POST})
    public String getByCategory(@RequestParam(value = "questionType", required = false) String type, Model model) {
        List<QuestionModel> resultList = questionService.getQuestionsByCategory(type);
        model.addAttribute("result", resultList);
        return "QuestionShow";
    }

    @RequestMapping(value = "/dailyQuestion", method = RequestMethod.POST)
    public String dailyquiz(Model model, HttpSession session) {
        timeQuestion = questionService.getDailtOne();
        model.addAttribute("question",timeQuestion);
        return "DailyQuiz";
    }

    @RequestMapping(value = "/submitQuiz", method = RequestMethod.POST)
    public String submitQuiz(@RequestParam Map<String, String> answers, Model model, HttpSession session) {
        List<QuestionModel> randomQuestions = (List<QuestionModel>) session.getAttribute("randomQuestions");

        if (randomQuestions == null || randomQuestions.isEmpty()) {
            return "redirect:/ques/test";
        }

        List<QuestionDTO> results = new ArrayList<>();
        int score = questionService.evaluateQuiz(randomQuestions, answers, results);

        int total = randomQuestions.size();
        int percentage = (score * 100) / total;

        model.addAttribute("result", score);
        model.addAttribute("total", total);
        model.addAttribute("percentage", percentage);
        model.addAttribute("results", results);

        return "Result";
    }
}
