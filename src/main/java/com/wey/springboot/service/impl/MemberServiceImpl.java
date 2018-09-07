package com.wey.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wey.springboot.dao.MemberRepository;
import com.wey.springboot.pojo.auth.Member;
import com.wey.springboot.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
    
    @Autowired
    MemberRepository memberRepository;
    
    @Override
    public Member getMemberByName(String username) {
        
        return memberRepository.findByUsername(username);
    }
    
}
