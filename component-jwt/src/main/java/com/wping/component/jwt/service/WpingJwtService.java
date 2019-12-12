package com.wping.component.jwt.service;

import com.wping.component.base.constatns.CommonConstants;
import com.wping.component.base.dto.WpingToken;
import com.wping.component.jwt.properties.WpingJwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

/**
 * http client 业务逻辑处理类
 */
public class WpingJwtService {

    private static final Logger logger = LoggerFactory.getLogger(WpingJwtService.class);

    private WpingJwtProperties wpingJwtProperties;
    private Key key;

    public WpingJwtService(WpingJwtProperties wpingJwtProperties, Key key) {
        this.wpingJwtProperties = wpingJwtProperties;
        this.key = key;
    }

    /**
     * 密钥加密token
     *
     * @param wpingToken
     * @return
     * @throws Exception
     */
    public String generateToken(WpingToken wpingToken) {
        String compactJws = Jwts.builder()
                .setSubject(wpingToken.getUserId())
                .claim(CommonConstants.JWT_KEY_USER_NAME, wpingToken.getUserName())
                .setExpiration(new Date(System.currentTimeMillis() + wpingJwtProperties.getExpiryTime() * 1000))
                .setIssuedAt(new Date())
                .setIssuer(CommonConstants.WPING_ISSUER)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return compactJws;
    }

    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public Jws<Claims> parserToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return claimsJws;
    }

    /**
     * 获取token中的用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    public WpingToken getWpingTokenFromToken(String token) {

        String methodName = "获取token中的用户信息, token:{}" + token;
        try {
            Jws<Claims> claimsJws = parserToken(token);
            Claims body = claimsJws.getBody();
            return new WpingToken(body.getSubject(), getObjectValue(body.get(CommonConstants.JWT_KEY_USER_NAME)));
        } catch (SignatureException | MalformedJwtException e) {
            logger.error(methodName + "-Invalid JWT signature.");
            throw new JwtException("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            logger.error(methodName + "Expired JWT token.");
            throw new JwtException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            logger.error(methodName + "Unsupported JWT token.");
            throw new JwtException("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            logger.error(methodName + "JWT token compact of handler are invalid.");
            throw new JwtException("JWT token compact of handler are invalid");
        }
    }

    public static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    // 是否已过期
    public boolean isExpiration(String token) {
        try {
            return parserToken(token).getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token) {

        String methodName = "token验证, token:{}" + token;
        try {
            parserToken(token);
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            logger.error(methodName + "-Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            logger.error(methodName + "Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            logger.error(methodName + "Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            logger.error(methodName + "JWT token compact of handler are invalid.");
        }

        return false;
    }



}