package com.wey.springboot.dao;

import org.springframework.data.repository.CrudRepository;

import com.wey.springboot.pojo.auth.Member;

public interface MemberRepository extends CrudRepository<Member, Long> {
    
    Member findByUsername(String username);
}
