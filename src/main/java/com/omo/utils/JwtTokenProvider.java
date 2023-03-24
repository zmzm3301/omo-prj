package com.omo.utils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.omo.dto.JwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
 
    private final Key key;
    private static final long refreshTokenExpiresIn = (1000 * 60 * 60 * 24 * 7);
    
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
 
    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public JwtToken generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
 
        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + (1000 * 60 * 30));
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        
        
        
        // Refresh Token 생성
        String refreshToken = Jwts.builder()
        		.setExpiration(new Date(System.currentTimeMillis()+ refreshTokenExpiresIn))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
 
        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    
 
    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
 
        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
 
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
 
        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }
 
    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return false; // 토큰이 만료된 경우 false 반환
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw e; // 추가
        }
    }

 
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public JwtToken regenerateToken(String accessToken, HttpServletRequest request, HttpServletResponse response) {
        String token = accessToken;
        try {
            if (token != null && !token.isEmpty() && validateToken(token)) {
                // Access Token이 유효하다면 Refresh Token으로 새로운 Access Token 발급받기
                String refreshToken = getRefreshTokenFromCookie(request);
                Authentication username = getAuthentication(token);
                if (validateToken(refreshToken)) {
                    JwtToken newToken = generateToken(username);
                    
                    return newToken;
                } else {
                    throw new InvalidRefreshTokenException("Refresh token is invalid.");
                }
            }else if(!validateToken(token)) {
            	String refreshToken = getRefreshTokenFromCookie(request);
                if (validateToken(refreshToken)) {
                    Authentication username = getAuthentication(token);
                    JwtToken newToken = generateToken(username);
                    
                    return newToken;
                } else {
                    throw new InvalidRefreshTokenException("Refresh token is invalid.");
                }
            }
        } catch (ExpiredJwtException e) {
            // Access Token이 만료되었을 경우, Refresh Token으로 새로운 Access Token 발급받기
            String refreshToken = getRefreshTokenFromCookie(request);
            if (validateToken(refreshToken)) {
                Authentication username = getAuthentication(token);
                JwtToken newToken = generateToken(username);
                
                return newToken;
            } else {
                throw new InvalidRefreshTokenException("Refresh token is invalid.");
            }
        } catch (InvalidRefreshTokenException | NullPointerException e) {
            throw e;
        } catch (Exception e) {
            log.info("Error occurred while refreshing token", e);
            throw new InvalidRefreshTokenException("Error occurred while refreshing token.");
        }
        
        
        return null;
    }
    
    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
    
    

}