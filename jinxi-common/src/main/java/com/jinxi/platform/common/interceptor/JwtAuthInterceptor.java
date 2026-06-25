package com.jinxi.platform.common.interceptor;

import com.jinxi.platform.common.context.LoginUser;
import com.jinxi.platform.common.context.UserContext;
import com.jinxi.platform.common.exception.ServiceException;
import com.jinxi.platform.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtAuthInterceptor implements HandlerInterceptor {
    private static final String AUTHORIZTION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ){
        String authorization = request.getHeader(AUTHORIZTION_HEADER);

        if (!StringUtils.hasText(authorization)){
            throw new ServiceException("请先登录");
        }
        if (!authorization.startsWith(BEARER_PREFIX)){
            throw new ServiceException("Token格式错误");
        }
        String token = authorization.substring(BEARER_PREFIX.length());

        LoginUser loginUser = jwtUtil.getLoginUser(token);

        UserContext.set(loginUser);
        return true;
    }
    @Override 
    public void afterCompletion(
            HttpServletRequest request, 
            HttpServletResponse response, 
            Object handler, 
            Exception ex) {
        UserContext.clear();
    }
}
