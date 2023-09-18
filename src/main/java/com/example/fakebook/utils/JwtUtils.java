package com.example.fakebook.utils;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.service.UserDetailsIpmpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

@Slf4j
@Component
public class JwtUtils {
    @Value("${bezkoder.app.jwtSecret}")
    public String jwtSecret;
    @Value("${bezkoder.app.jwtExpirationMs}")
    public int jwtExpirationsMs;
    // lớp này dùng để ký (là hoạt động mã hóa tạo ra chữ ký) và xác nhận (verify) token
    public static Algorithm algorithm;
    // giữ phương thức xác minh đúng định dạng  JWT và đúng chữ ký
    private static JWTVerifier verifier;
    public static String JWT_SECRET_KEY = "secret-changed";
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = ONE_SECOND * 60;
    public static final int ONE_HOUR = ONE_MINUTE * 60;
    public static final int ONE_DAY = ONE_HOUR * 24;
    public static final String ROLE_CLAIM_KEY = "role";
    private static final String DEFAULT_ISSUER = "T2009M1";

    public static Algorithm getAlgorithm() {
        if (algorithm == null) {
            algorithm = Algorithm.HMAC256(JWT_SECRET_KEY.getBytes());
        }
        return algorithm;
    }

    static int time = ONE_DAY * 3;

    public static JWTVerifier getVerifier() {
        if (verifier == null) {
            verifier = JWT.require(getAlgorithm()).build();
        }
        return verifier;
    }

    public static DecodedJWT getDecodedJwt(String token) {
        DecodedJWT decodedJWT = getVerifier().verify(token);
        return decodedJWT;
    }

    public static String generateToken(String subject, String role, String issuer, int expireAfter) {
        if (role == null || role.length() == 0) {
            return JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter))
                    .withIssuer(issuer)
                    .sign(getAlgorithm());
        }
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuer(issuer)
                .withClaim(JwtUtils.ROLE_CLAIM_KEY, role) //get first role in Authorities
                .sign(getAlgorithm());
    }

    public static String generateTokenByAccount(Accounts accounts, int expireAfter) {
        return JWT.create()
                .withSubject(String.valueOf(accounts.getId()))
                .withSubject(String.valueOf(accounts.getUsername()))
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuer(DEFAULT_ISSUER)
                .withClaim("username", accounts.getUsername())
                .sign(getAlgorithm());
    }

    public String generateJwtToken(Authentication authentication){
        UserDetailsIpmpl userPrincipal = (UserDetailsIpmpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationsMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("JWT token is expire: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("JWT claim string is empty: {}", e.getMessage());
        }
        return false;
    }

    public static <T extends Enum<?>> T searchEnum(Class<T> enumeration, String search) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }
        return null;
    }
}
