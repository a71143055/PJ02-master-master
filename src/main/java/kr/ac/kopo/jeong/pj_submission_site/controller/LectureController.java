package kr.ac.kopo.jeong.pj_submission_site.controller;

import kr.ac.kopo.jeong.pj_submission_site.model.Enrollment;
import kr.ac.kopo.jeong.pj_submission_site.model.Lecture;
import kr.ac.kopo.jeong.pj_submission_site.model.Role;
import kr.ac.kopo.jeong.pj_submission_site.model.User;
import kr.ac.kopo.jeong.pj_submission_site.repository.EnrollmentRepository;
import kr.ac.kopo.jeong.pj_submission_site.repository.LectureRepository;
import kr.ac.kopo.jeong.pj_submission_site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/lectures")
public class LectureController {


    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    public LectureController(LectureRepository lectureRepository, UserRepository userRepository) {
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
    }

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
        lecture.setProfessor(professor); // ✅ 꼭 있어야 함

        lectureRepository.save(lecture);
        return "redirect:/home";
    }



    @GetMapping("/my-lectures")
    @PreAuthorize("hasRole('PROFESSOR')")
    public String showMyLectures(Model model, Principal principal) {
        User professor = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        List<Lecture> lectures = lectureRepository.findByProfessor(professor); // ✅ 수정된 부분
        model.addAttribute("lectures", lectures);

        return "myLectures";
    }


    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @PostMapping("/enroll")
    @PreAuthorize("hasRole('STUDENT')")
    public String enrollLecture(@RequestParam Long lectureId, Principal principal) {
        User student = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("학생 정보를 찾을 수 없습니다."));
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(String.valueOf(student.getId()));
        enrollment.setLectureId(lectureId);
        enrollmentRepository.save(enrollment);
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public String showLectureDetail(@PathVariable Long id, Model model) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));
        model.addAttribute("lecture", lecture);
        return "lectureDetail";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public String deleteLecture(@PathVariable Long id, Principal principal) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));
        User professor = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("교수 정보를 찾을 수 없습니다."));

        // ✅ 교수 본인이 생성한 강의인지 확인
        if (!lecture.getProfessor().getId().equals(professor.getId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        lectureRepository.delete(lecture);
        return "redirect:/lectures/my-lectures";
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        model.addAttribute("username", user.getUsername());

        if (user.getRole() == Role.PROFESSOR) {
            // 교수 전용 로직
        }
        {
            List<Lecture> myLectures = lectureRepository.findByProfessor(user);
            model.addAttribute("myLectures", myLectures); // ✅ 있어야 함
        }

        return "home";
    }

    @GetMapping("/lectures/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public String showLectureDetail(@PathVariable Long id, Model model, Principal principal) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("강의를 찾을 수 없습니다."));

        User professor = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        if (!lecture.getProfessor().getId().equals(professor.getId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        model.addAttribute("lecture", lecture);
        return "lectureDetail";
    }

}
