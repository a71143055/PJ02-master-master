package kr.ac.kopo.jeong.pj_submission_site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PjSubmissionSiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(PjSubmissionSiteApplication.class, args);
    }
}
