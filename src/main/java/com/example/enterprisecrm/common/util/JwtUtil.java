package com.example.enterprisecrm.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final String secret = "crm";

    /**
     * 生成用户token,设置token超时时间
     */

    public static String createToken(String id) {
        //过期时间 毫秒
        Date expireDate = new Date(System.currentTimeMillis() +24*60*60*1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                //可以将基本信息放到claims中
                //userName
                .withClaim("nana", id)
                //超时设置,设置过期的日期
                .withExpiresAt(expireDate)
                //签发时间
                .withIssuedAt(new Date())
                //SECRET加密
                .sign(Algorithm.HMAC256(secret));
        System.out.println(token);
        return token;
    }

    /**
     * 校验token
     */
    public static boolean verifyToken(String token) {
        DecodedJWT jwt;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
            System.out.println(jwt.getPayload());
        } catch (Exception e) {
            //解码异常则抛出异常
            throw new RuntimeException("token过期");
        }
        return true;
    }

    /**
     * 
     * 解析token
     */
    public static String getToken(String token) {
        DecodedJWT jwt;
        String userId;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
            String nana = jwt.getClaim("nana").asString();
            userId=nana;
        } catch (Exception e) {
            //解码异常则抛出异常
            throw new RuntimeException("token过期");
        }
        return userId;
    }
}


