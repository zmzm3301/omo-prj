package com.omo.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omo.dto.KPerson;

import jakarta.transaction.Transactional;

@Repository
public interface KPersonRepository extends JpaRepository<KPerson, String> {
	@Transactional
    @Modifying(clearAutomatically = true) 
    @Query("UPDATE kperson k SET k.authority = :authority WHERE id = :id")
    public void save(int id, String authority);
	
	Optional<KPerson> findByEmail(String email);

	void deleteByEmail(String email);

}
