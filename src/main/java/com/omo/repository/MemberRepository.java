package com.omo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import com.omo.dto.Member;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    
	Optional<Member> findByUsername(String username);

	Object findOneWithAuthoritiesByUsername(String username);

	void save(User user);

	Member findMemberByUsername(String username);

	Member findMemberByName(String name);

	Optional<Member> findByName(String name);

	Member findMemberByBirth(String birth);

	Optional<Member> findById(Long id);
    Optional<Member> findByNo(Long integer);

	Member findByNameAndBirth(String name, LocalDate birth);

	Member findByUsernameAndNameAndBirth(String username, String name, LocalDate birthDate);
	
	Member findByUsernameAndName(String username, String name);

	Member findByNickname(String nickname);

	Member findMemberByNo(Member author);

	
	
}