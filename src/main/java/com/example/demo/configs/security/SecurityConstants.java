package com.example.demo.configs.security;

class SecurityConstants {

    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/v1/users/sign-up";
    static final String SWAGGER_URL = "/swagger-ui.html";

}
