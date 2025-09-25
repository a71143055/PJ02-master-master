package kr.ac.kopo.jeong.pj_submission_site.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private User professor; // 강의를 생성한 교수

    private String professorUsername; // 선택적으로 유지 가능
}

