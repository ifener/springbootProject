package com.wey.springboot.service;

import com.wey.springboot.pojo.auth.Member;

public interface MemberService {
    
    Member getMemberByName(String username);
}
