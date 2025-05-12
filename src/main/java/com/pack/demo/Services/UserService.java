package com.pack.demo.Services;

import com.pack.demo.Model.UserModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserService {
    String processSignup(UserModel userModel, HttpSession session,Model model);
    String resendOtp(HttpSession session, RedirectAttributes redirectAttributes);
    String verifyOtp(int code, HttpSession session, Model model);
    String handleContact(String name, String email, String message, Model model);
    String saveUser(UserModel userModel);
    String handleUserEntry(HttpSession session);
}
