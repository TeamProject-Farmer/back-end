package com.farmer.backend.service.user;


import com.farmer.backend.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    private int size;

    //인증키 생성
    private String getKey(int size) {
        this.size = size;
        return getAuthCode();
    }

    //인증코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }

        return buffer.toString();
    }

    //인증메일 보내기
    public String sendAuthMail(String email) {

        //6자리 난수 인증번호 생성
        String authKey = getKey(6);
        //인증메일 보내기

        try {
            MailUtils sendMail = new MailUtils(mailSender);

            sendMail.setSubject("회원가입 이메일 인증");
            sendMail.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
                    .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
                    .append("<a href='http://localhost:8080/api/member/join/mail/check?email=")
                    .append(email)
                    .append("&authKey=")
                    .append(authKey)
                    .append("' target='_blenk'>이메일 인증 확인</a>")
                    .toString());
            sendMail.setFrom("kce2360@naver.com", "Farmer 관리자");
            sendMail.setTo(email);
            sendMail.send();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }

        return authKey;
    }
}