package com.pack.demo.Implementation;

import com.pack.demo.Model.UserModel;
import com.pack.demo.Repository.UserRepo;
import com.pack.demo.Services.UserService;
import com.pack.demo.EmailService.EmailService;
import com.pack.demo.config.SecurityConfig;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DaoAuthenticationProvider authenticationProvider;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Random random = new Random();

    @Override
    public String processSignup(UserModel userModel, HttpSession session,Model model) {
        session.setAttribute("allowResend", true);
        session.setAttribute("user", userModel);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        if (userModel.getEmail() == null || userModel.getEmail().isEmpty()) {
            return "signup";
        }

        int code = random.nextInt(10000, 99999);
        session.setAttribute("codes", code);
        session.setAttribute("otpTime", System.currentTimeMillis());

        String result = emailService.sendMail(code, userModel);
        model.addAttribute("email", userModel.getEmail());
        return result.equals("successful") ? "Email" : "signup";
    }

    @Override
    public String resendOtp(HttpSession session, RedirectAttributes redirectAttributes) {
        
        UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) return "signup";

        int code = random.nextInt(10000, 99999);
        session.setAttribute("codes", code);
        session.setAttribute("otpTime", System.currentTimeMillis());

        if (emailService.sendMail(code, user).equals("successful")) {
            redirectAttributes.addFlashAttribute("otpMsg", "OTP has been resent successfully.");
            return "Email";
        }

        return "signup";
    }

    @Override
public String verifyOtp(int code, HttpSession session, Model model) {
    Integer sessionOtp = (Integer) session.getAttribute("codes");
        Long otpTime = (Long) session.getAttribute("otpTime");

        // Check if OTP and timestamp exist
        if (sessionOtp == null || otpTime == null) {
            model.addAttribute("error", "Session expired or OTP not found.");
            System.out.println("No otp");
            return "signup";
        }

        // Check if OTP is expired (30 seconds)
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - otpTime;

        if (timeElapsed > 30_000) {
            model.addAttribute("error", "OTP expired. Please request a new one.");
            System.out.println("Expired otp");
            return "signup";
        }

        // OTP match
        if (sessionOtp == code) {
            UserModel user = (UserModel) session.getAttribute("user");
            if (user != null) {
                System.out.println("User session found: " + user.getName());
                userRepo.save(user); // Save to DB
                System.out.println("Saved");

                String fname = user.getName().split(" ")[0]; // Get first name
                model.addAttribute("name", fname);

                return "new";
            } else {
                model.addAttribute("error", "User session missing. Please sign up again.");
                System.out.println("User session missing");
                return "signup";
            }
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            System.out.println("Invalid otp");
            return "signup";
        }
}


    @Override
    public String handleContact(String name, String email, String message, Model model) {
        if (emailService.sendMail(name, email, message).equals("successful")) {
            return "contact";
        }
        return "new";
    }

    @Override
    public String saveUser(UserModel userModel) {
        userRepo.save(userModel);
        return "Saved";
    }

    @Override
    public String handleUserEntry(HttpSession session) {
        String loggedInUser = (String) session.getAttribute("User");
        return "Swaraj Ravindra Gujar".equals(loggedInUser) ? "UserEntry" : "Saved";
    }
}
