package com.example.tool;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    //生成token
    public static String createToken(String subject, Date issueDate, Date expireDate) {
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issueDate)  //签发时间
                .setExpiration(expireDate) //过期时间
                .signWith(SignatureAlgorithm.HS256, ConstantKey.SIGNING_KEY)
                .compact();
        return token;
    }

    //解析Token
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(ConstantKey.SIGNING_KEY)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }
}
