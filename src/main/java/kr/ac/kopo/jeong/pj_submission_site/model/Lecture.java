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
    @JoinColumn(name = "professor_id", nullable = false)
    private User professor; // ✅ 이 필드와 연결됨
}