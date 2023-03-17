package com.omo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omo.dto.KPerson;

import jakarta.transaction.Transactional;

@Repository
public interface KPersonRepository extends JpaRepository<KPerson, String> {
	
	String UPDATE_KPERSON = "UPDATE kperson k " +
	"SET k.authority = :authority " +
	"WHERE k.email = :email";
	
	@Transactional
    @Modifying(clearAutomatically = true) 
    @Query(value = UPDATE_KPERSON, nativeQuery = true)
    public int updateKPerson(String email, String authority);
}
