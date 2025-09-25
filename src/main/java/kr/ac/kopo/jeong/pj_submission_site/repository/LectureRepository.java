package kr.ac.kopo.jeong.pj_submission_site.repository;

import kr.ac.kopo.jeong.pj_submission_site.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByProfessorId(String professorId);
}
