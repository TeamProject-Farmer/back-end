package com.farmer.backend.login.oauth.userInfo;
import com.farmer.backend.dto.user.login.OAuthUserInfoDto;
import com.farmer.backend.entity.Member;


public interface OAuthLogin {

    String getAccessToken(String code);

    OAuthUserInfoDto getUserInfo(String code) ;

    Member userSave(OAuthUserInfoDto userInfo);

}
