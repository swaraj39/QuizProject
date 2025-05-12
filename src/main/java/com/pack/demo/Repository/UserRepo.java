package com.pack.demo.Repository;

import com.pack.demo.Model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel,String> {


}
