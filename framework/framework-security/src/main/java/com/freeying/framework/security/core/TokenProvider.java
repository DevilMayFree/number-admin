package com.freeying.framework.security.core;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.freeying.common.core.utils.StringPool;
import com.freeying.framework.security.config.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.ArrayList;

import static com.freeying.common.core.constant.WebSocketConstants.WEBSOCKET_API;

/**
 * TokenProvider
 * <p>Token相关操作类</p>
 *
 * @author fx
 */
public class TokenProvider implements InitializingBean {

    public static final String AUTHORITIES_KEY = "user";
    private final SecurityProperties properties;
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public TokenProvider(SecurityProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建Token 设置永不过期，
     * Token 的时间有效性转到Redis 维护
     *
     * @param authentication /
     * @return /
     */
    public String createToken(Authentication authentication) {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();

        return jwtBuilder
                .setId(IdUtil.simpleUUID())
                .claim(AUTHORITIES_KEY, userDetails.getUsername())
                .setSubject(authentication.getName())
                .compact();
    }

    /**
     * 初步检测Token
     *
     * @param request /
     * @return /
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(), "");
        }
        String uri = request.getRequestURI();
        if (uri.contains(WEBSOCKET_API)) {
            // websocket相关请求
            String queryString = request.getQueryString();
            if (StringUtils.hasText(queryString) && queryString.contains(HttpHeaders.AUTHORIZATION)) {
                return queryString.substring(HttpHeaders.AUTHORIZATION.length() + 1);
            }
        }
        return StringPool.EMPTY;
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        User principal = new User(claims.getSubject(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public Claims getClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return StringPool.EMPTY;
    }

    /**
     * 获取登录用户RedisKey
     *
     * @param token /
     * @return key
     */
    public String loginKey(String token) {
        Claims claims = getClaims(token);
        String md5Token = DigestUtil.md5Hex(token);
        return properties.getOnlineKey() + claims.getSubject() + "-" + md5Token;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        jwtBuilder = Jwts.builder().signWith(key, SignatureAlgorithm.HS512);
    }
}
