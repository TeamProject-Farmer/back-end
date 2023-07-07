package com.farmer.backend.login.general;

import com.farmer.backend.domain.member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
<<<<<<< HEAD
=======
import org.springframework.security.core.userdetails.User;
>>>>>>> f2a0623 (feat: UserAdapter 클래스 생성, 리턴 값 변경)

import java.util.List;

@Getter
<<<<<<< HEAD
public class MemberAdapter extends org.springframework.security.core.userdetails.User {
=======
public class MemberAdapter extends User {
>>>>>>> f2a0623 (feat: UserAdapter 클래스 생성, 리턴 값 변경)

    private Member member;

    public MemberAdapter(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole().name())));

        this.member = member;
    }

}
