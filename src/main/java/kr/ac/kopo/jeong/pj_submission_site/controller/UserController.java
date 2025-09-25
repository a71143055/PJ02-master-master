package kr.ac.kopo.jeong.pj_submission_site.controller;

import kr.ac.kopo.jeong.pj_submission_site.model.Role;
import kr.ac.kopo.jeong.pj_submission_site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // 회원가입 폼 렌더링
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam Role role) {
        userService.registerUser(username, password, role);
        return "redirect:/login";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam String username) {
        userService.deleteUserByUsername(username);
        return "redirect:/home";
    }
}