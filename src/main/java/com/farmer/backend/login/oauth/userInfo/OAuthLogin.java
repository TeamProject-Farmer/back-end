package com.farmer.backend.login.oauth.userInfo;
import com.farmer.backend.api.controller.user.login.RequestOAuthUserInfoDto;
import com.farmer.backend.api.controller.user.login.ResponseOAuthUserInfoDto;
import com.farmer.backend.domain.member.Member;


public interface OAuthLogin {

    String getAccessToken(String code);

    ResponseOAuthUserInfoDto getUserInfo(String code) ;

    Member userSave(RequestOAuthUserInfoDto userInfo);


}
