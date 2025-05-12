package com.pack.demo.Controller;

import com.pack.demo.Model.UserModel;
import com.pack.demo.Services.UserService;
import jakarta.servlet.http.HttpSession;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class Controllers {

    @Autowired
    private UserService userService;

    @RequestMapping({"/", "/welcome"})
    public String home() {
        return "Welcome";
    }

    @RequestMapping("/new")
    public String loginPage() {
        return "new";
    }

    @RequestMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @RequestMapping("/Email")
    public String emailPage() {
        return "Email";
    }
    @RequestMapping("/home")
    public String home(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("name", principal.getName());
        }
        return "new"; // or your home page
    }
    @RequestMapping("/review")
    public String requestMethodName() {
        return "Review";
    }
    @GetMapping("/saving")
    public String reviews(){
        return "";
    }

    @PostMapping("/signups")
    public String signup(@ModelAttribute UserModel userModel, HttpSession session, Model model) {
        return userService.processSignup(userModel, session,model);
    }

    @RequestMapping(value = "/resend", method = { RequestMethod.GET, RequestMethod.POST })
    public String resendOtp(HttpSession session, RedirectAttributes redirectAttributes) {
        return userService.resendOtp(session, redirectAttributes);
    }

    @PostMapping("/codewala")
    public String verifyOtp(@RequestParam("codes") int code, HttpSession session, Model model) {
        return userService.verifyOtp(code, session, model);
    }

    @GetMapping("/contact")
    public String contact(@RequestParam("name") String name,
                          @RequestParam("email") String email,
                          @RequestParam("message") String message,
                          Model model) {
        return userService.handleContact(name, email, message, model);
    }

    @GetMapping("/usersave")
    public String userSaved(@ModelAttribute UserModel userModel) {
        return userService.saveUser(userModel);
    }

    @GetMapping("/UserEnter")
    public String enter(HttpSession session) {
        return userService.handleUserEntry(session);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "logout";
    }
}
