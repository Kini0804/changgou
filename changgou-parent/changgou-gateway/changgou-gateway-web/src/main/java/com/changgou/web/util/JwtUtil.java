package com.changgou.web.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    public static final String AUTHORIZE_TOKEN = "Authorization";

    public static String creatJwt(String subject, Long time){
        JwtBuilder builder = Jwts.builder();
        String id = UUID.randomUUID().toString();
        builder.setId(id);
        builder.setSubject(subject);
        builder.setIssuer("liushaodong");
        long nowMill = System.currentTimeMillis();
        builder.setIssuedAt(new Date(nowMill));
        if(time != null){
            builder.setExpiration(new Date(nowMill + time));
        }else {
            builder.setExpiration(new Date(nowMill + 3600000));
        }
        builder.signWith(SignatureAlgorithm.HS256, generalKey());
        return builder.compact();
    }

    public static Claims parseJwt(String jwt){
        return Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(jwt).getBody();
    }

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getEncoder().encode("lsd".getBytes());
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

}
