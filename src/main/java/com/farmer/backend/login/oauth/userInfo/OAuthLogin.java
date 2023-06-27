package com.farmer.backend.login.oauth.userInfo;
import com.farmer.backend.api.controller.user.login.OAuthUserInfoDto;
import com.farmer.backend.domain.member.Member;


public interface OAuthLogin {

    String getAccessToken(String code);

    OAuthUserInfoDto getUserInfo(String code) ;

    Member userSave(OAuthUserInfoDto userInfo);

}
