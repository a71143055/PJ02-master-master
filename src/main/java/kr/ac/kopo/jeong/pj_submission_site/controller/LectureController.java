package kr.ac.kopo.jeong.pj_submission_site.controller;

import kr.ac.kopo.jeong.pj_submission_site.model.Lecture;
import kr.ac.kopo.jeong.pj_submission_site.model.User;
import kr.ac.kopo.jeong.pj_submission_site.repository.LectureRepository;
import kr.ac.kopo.jeong.pj_submission_site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/lectures")
public class LectureController {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/create")
    @PreAuthorize("hasRole('PROFESSOR')")
    public String showCreateForm(Model model) {
        model.addAttribute("lecture", new Lecture());
        return "createLecture"; // createLecture.html
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('PROFESSOR')")
    public String createLecture(@RequestParam String title,
                                @RequestParam String description,
                                Principal principal) {
        User professor = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("교수 정보를 찾을 수 없습니다."));

        Lecture lecture = new Lecture();
        lecture.setTitle(title);
        lecture.setDescription(description);
        lecture.setProfessorId(professor.getId());

        lectureRepository.save(lecture);
        return "redirect:/home";
    }

}
