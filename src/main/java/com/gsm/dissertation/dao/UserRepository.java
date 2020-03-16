package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("update Users u set u.password = ?1 where u.uid = ?2")
    void modifyPassword(String pwd,Integer uid);

    Optional<Users> findStuByAccount(String account);

    void deleteByAccount(String account);
}
