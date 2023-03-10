package com.bitacademy.cocktail.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;
	private final RedisTemplate redisTemplate;
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 헤더에서 JWT 를 받아옵니다.
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		// 유효한 토큰인지 확인합니다.
		if (token != null && jwtTokenProvider.validateToken(token)) {

			String isBlackList = (String) redisTemplate.opsForValue().get(token);

			// 블랙리스트에 해당 토큰이 존재하지 않을 경우
			if (ObjectUtils.isEmpty(isBlackList)) {
				// 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				// SecurityContext 에 Authentication 객체를 저장합니다.
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		chain.doFilter(request, response);
    }
}
