package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.Teacher;
import com.gsm.dissertation.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {

    /**
     * 分页查找用户
     * @param kw 关键字可为账号或者姓名
     * @param pageable 分页
     * @return 查询到的信息
     */
    @Query("select u from Users u where u.account like ?1 or u.name like ?1")
    Page<Users> findByKeyword(String kw,Pageable pageable);

    @Query("update Users u set u.password = ?1 where u.uid = ?2")
    void modifyPassword(String pwd,Integer uid);

    Optional<Users> findStuByAccount(String account);


}
