package com.omo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omo.dto.MailCode;

	@Repository
	public interface CodeRepository extends JpaRepository<MailCode, Long> {
	    MailCode findByEmailAndCode(String email, String code);

		MailCode findByEmail(String email);
	}

