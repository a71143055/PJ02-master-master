package kr.ac.kopo.jeong.pj_submission_site.controller;

import kr.ac.kopo.jeong.pj_submission_site.model.User;
import kr.ac.kopo.jeong.pj_submission_site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String showHomePage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "home";
    }

}
