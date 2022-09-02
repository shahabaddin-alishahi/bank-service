package energizeglobalservices.bankservice.config.security;

import energizeglobalservices.bankservice.config.exception.NotAcceptableException;
import energizeglobalservices.bankservice.config.exception.TokenExpiredException;
import energizeglobalservices.bankservice.domain.AuthenticationResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtTokenProvider implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${energize-global-services.jwt.secret}")
    private String secret;

    @Value("${energize-global-services.jwt.access-token-validity-in-minute}")
    private String accessTokenValidityInMinute;

    @Value("${energize-global-services.jwt.refresh-token-validity-in-minute}")
    private String refreshTokenValidityInMinute;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public AuthenticationResult generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private AuthenticationResult doGenerateToken(Map<String, Object> claims, String subject) {
        Pair<String, Long> accessToken = generateAccessToken(claims, subject);
        Pair<String, Long> refreshToken = generateRefreshToken(claims, subject);
        return AuthenticationResult.builder()
                .accessToken(accessToken.getFirst())
                .accessTokenExpirationTimeInMilliSeconds(accessToken.getSecond())
                .refreshToken(refreshToken.getFirst())
                .refreshTokenExpirationTimeInMilliSeconds(refreshToken.getSecond())
                .build();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public AuthenticationResult refreshToken(String refreshToken) {
        Claims claims = getAllClaimsFromToken(refreshToken);
        if (isTokenExpired(refreshToken))
            throw new TokenExpiredException();
        if (!StringUtils.equalsIgnoreCase(claims.get("TOKEN_TYPE", String.class), "REFRESH_TOKEN"))
            throw new NotAcceptableException();

        Pair<String, Long> accessToken = generateAccessToken(claims, claims.getSubject());
        Pair<String, Long> newRefreshToken = generateRefreshToken(claims, claims.getSubject());

        return AuthenticationResult.builder()
                .accessToken(accessToken.getFirst())
                .accessTokenExpirationTimeInMilliSeconds(accessToken.getSecond())
                .refreshToken(newRefreshToken.getFirst())
                .refreshTokenExpirationTimeInMilliSeconds(newRefreshToken.getSecond())
                .build();
    }

    private Pair<String, Long> generateAccessToken(Map<String, Object> claims, String subject) {
        claims.put("TOKEN_TYPE", "ACCESS_TOKEN");

        String jwts = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong(accessTokenValidityInMinute)))))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return Pair.of(jwts, TimeUnit.MINUTES.toMillis(Long.parseLong(accessTokenValidityInMinute)));
    }

    private Pair<String, Long> generateRefreshToken(Map<String, Object> claims, String subject) {
        claims.put("TOKEN_TYPE", "REFRESH_TOKEN");

        String jwts = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(Long.parseLong(refreshTokenValidityInMinute)))))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        return Pair.of(jwts, TimeUnit.MINUTES.toMillis(Long.parseLong(refreshTokenValidityInMinute)));
    }

}