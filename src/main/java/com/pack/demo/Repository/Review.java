package com.pack.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pack.demo.Model.Reviwer;

@Repository
interface Review extends JpaRepository<Reviwer, Integer> {
    // Custom query methods can be defined here if needed
    
}
