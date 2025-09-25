package kr.ac.kopo.jeong.pj_submission_site.repository;

import kr.ac.kopo.jeong.pj_submission_site.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    // 필요 시 사용자별/강의별 조회 메서드 추가 가능
}
