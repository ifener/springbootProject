package com.wey.springboot.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wey.springboot.pojo.auth.Member;
import com.wey.springboot.pojo.auth.Permission;
import com.wey.springboot.pojo.auth.Role;
import com.wey.springboot.service.MemberService;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    @Autowired
    MemberService memberService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("#################################" + username);
        Member member = memberService.getMemberByName(username);
        if (member == null) {
            throw new UsernameNotFoundException(username);
        }
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (Role role : member.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }
        
        return new User(member.getUsername(), member.getPassword(), authorities);
    }
    
}
