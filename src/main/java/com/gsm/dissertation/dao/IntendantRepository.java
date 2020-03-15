package com.gsm.dissertation.dao;

import com.gsm.dissertation.model.Intendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IntendantRepository extends JpaRepository<Intendant,Integer> {
    @Query("select count(t) from Intendant t where t.account=?1")
    int checkIntendant(String account);

    @Query
    Optional<Intendant> findByAccount(String account);
}
