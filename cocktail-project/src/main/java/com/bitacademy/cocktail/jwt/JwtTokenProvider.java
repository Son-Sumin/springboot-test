package com.bitacademy.cocktail.jwt;

import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.bitacademy.cocktail.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

private String secretKey = "cocktailproject123";
	
	// 토큰 유효시간 60분
	private long tokenValidTime = 60 * 60 * 1000L;
	
	private final UserDetailsService userDetailsService;
	
	// 객체 초기화, secretKey를 Base64로 인코딩
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	// JWT 토큰 생성
	public String createToken(String userPk, Role roles, String nickname) {
		Claims claims = Jwts.claims().setSubject(userPk);   // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
		claims.put("roles", roles);  // 정보 저장 (key-value)
		Date now = new Date();
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setClaims(claims)  // 정보 저장
				.setIssuedAt(now)   // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
				.signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
				// signature 에 들어갈 secret값 세팅
				.compact();
		
	}
	
	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	// 토큰에서 회원 정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	// Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	// 토큰의 유효성 + 만료일자 확인
	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public Long getExpiration(String accessToken) {
		// accessToken 남은 유효시간
		Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getExpiration();
		// 현재 시간
		Long now = new Date().getTime();
		return (expiration.getTime() - now);
	}
}
