package com.omo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ConfigUtils {
	
    @Value("https://oauth2.googleapis.com")
    private String googleAuthUrl;

    @Value("https://accounts.google.com")
    private String googleLoginUrl;

    @Value("http://localhost:3000/glogin")
    private String googleRedirectUrl;

    @Value("714437714250-b4pph8bc10tlcacadjpr3dreueeebkjj.apps.googleusercontent.com")
    private String googleClientId;

    @Value("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")
    private String googleSecret;

    @Value("profile, email")
    private String scopes;

	// Google 로그인 URL 생성 로직
    public String googleInitUrl() {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", getGoogleClientId());
        params.put("redirect_uri", getGoogleRedirectUri());
        params.put("response_type", "code");
        params.put("scope", getScopeUrl());

        String paramStr = params.entrySet().stream()
                .map(param -> param.getKey() + "=" + param.getValue())
                .collect(Collectors.joining("&"));

        return getGoogleLoginUrl()
                + "/o/oauth2/v2/auth"
                + "?"
                + paramStr;
    }

    public String getGoogleAuthUrl() {
        return googleAuthUrl;
    }

    public String getGoogleLoginUrl() {
        return googleLoginUrl;
    }

    public String getGoogleClientId() {
        return googleClientId;
    }

    public String getGoogleRedirectUri() {
        return googleRedirectUrl;
    }

    public String getGoogleSecret() {
        return googleSecret;
    }

	// scope의 값을 보내기 위해 띄어쓰기 값을 UTF-8로 변환하는 로직 포함 
    public String getScopeUrl() {
//        return scopes.stream().collect(Collectors.joining(","))
//                .replaceAll(",", "%20");
        return scopes.replaceAll(",", "%20");
    }
}