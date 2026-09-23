package notify.wooper.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import notify.wooper.entity.UserLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final int validSeconds;

    public JwtService(@Value("${jwt.secret-key}") String secretKeyStr, @Value("${jwt.valid-seconds}") int validSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyStr.getBytes(StandardCharsets.UTF_8));
        this.validSeconds = validSeconds;
    }

    public String createLoginAccessToken(UserLogin userLogin) {
        String userId = userLogin.getUserId();
        String userName = userLogin.getUserName();
        // 計算過期時間
        long expirationMillis = Instant.now()
                .plusSeconds(validSeconds)
                .getEpochSecond()
                * 1000;

        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(expirationMillis))
                .claim("userName", userName)  // 改用 claim() 方法
                .claim("userId", userId)
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}