package com.pack.demo.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pack.demo.Model.TimeQuestion;

@Repository
public interface DailyRepo extends JpaRepository<TimeQuestion,Integer> {
    Optional<TimeQuestion> findByDate(LocalDate currentDate);

    
} 