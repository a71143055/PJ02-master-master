package kr.ac.kopo.jeong.pj_submission_site.service;

import kr.ac.kopo.jeong.pj_submission_site.model.Lecture;
import kr.ac.kopo.jeong.pj_submission_site.model.User;
import kr.ac.kopo.jeong.pj_submission_site.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    public void createLecture(String title, String description, User professor) {
        Lecture lecture = new Lecture();
        lecture.setTitle(title);
        lecture.setDescription(description);
        lecture.setProfessor(professor);
        lectureRepository.save(lecture);
    }
}

