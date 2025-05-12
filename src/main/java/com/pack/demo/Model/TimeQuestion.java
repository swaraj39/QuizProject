package com.pack.demo.Model;


import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "daily")
@Component
public class TimeQuestion {
    @Id
    private int id;
    private String question;
    private String option1;
    private String option2; 
    private String option3;
    private String option4;
    private LocalDate date;
    private String answer;
    private String reason;
}
