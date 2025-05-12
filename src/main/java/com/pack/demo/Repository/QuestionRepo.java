package com.pack.demo.Repository;

import com.pack.demo.Model.QuestionModel;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<QuestionModel,Integer> {

    List<QuestionModel> findByCateogry(String cateogry);
}
