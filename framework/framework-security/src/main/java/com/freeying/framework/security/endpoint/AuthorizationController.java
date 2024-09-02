package com.freeying.framework.security.endpoint;

import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.security.config.SecurityProperties;
import com.freeying.framework.security.core.JwtUserDetails;
import com.freeying.framework.security.core.TokenProvider;
import com.freeying.framework.security.domain.LoginRequest;
import com.freeying.framework.security.domain.TokenResponse;
import com.freeying.framework.security.service.OnlineUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * AuthorizationController
 * <p>认证授权Controller</p>
 *
 * @author fx
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "系统授权")
public class AuthorizationController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final SecurityProperties securityProperties;
    private final OnlineUserService onlineUserService;

    public AuthorizationController(AuthenticationManager authenticationManager,
                                   TokenProvider tokenProvider,
                                   SecurityProperties securityProperties,
                                   OnlineUserService onlineUserService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.securityProperties = securityProperties;
        this.onlineUserService = onlineUserService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @Operation(summary = "登录")
    @PostMapping("/login")
    @SuppressWarnings({"squid:S125", "squid:S1130"})
    public Result<TokenResponse> login(HttpServletRequest request, @Validated @RequestBody LoginRequest loginRequest)
            throws GeneralSecurityException, IOException {

        // RSA解密加密的密码 fix
        // String password = RsaUtil.decryptByPrivateKey(securityProperties.getRsaPrivateKey(), loginRequest.getPassword());
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), password);

        Authentication authentication;
        try {
            // 调用UserDetailsService#loadUserByUsername(String username)
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new BadRequestException(e.getMessage());
        }
        assert authentication != null;

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        TokenResponse tokenResponse = new TokenResponse(token, securityProperties.getTokenStartWith());

        final JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        // 保存在线用户信息
        onlineUserService.save(userDetails, token, request);

        return Result.success(tokenResponse);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @Operation(summary = "退出登录")
    @PostMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request) {
        String token = tokenProvider.getToken(request);
        onlineUserService.logout(token);
        return Result.success();
    }

}
