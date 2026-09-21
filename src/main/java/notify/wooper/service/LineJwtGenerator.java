package notify.wooper.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;

@Service
public class LineJwtGenerator {
    @Value("${line.kid}")
    private String kid;

    @Value("${line.private-key-path}")
    private String privateKeyPath;

    @Value("${line.channel-id}")
    private String channelId;

    private String privateJwkJson;

    public String generate() throws Exception {
        if (privateJwkJson == null || privateJwkJson.isBlank()) {
            privateJwkJson = loadPrivateJwkJson();
        }

        RSAKey rsaKey = RSAKey.parse(privateJwkJson);

        JWSSigner signer = new RSASSASigner(rsaKey);

        Instant now = Instant.now();

        // Header
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(kid)
                .type(com.nimbusds.jose.JOSEObjectType.JWT)
                .build();

        // Payload
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer(channelId)
                .subject(channelId)
                .audience("https://api.line.me/")
                .expirationTime(
                        Date.from(now.plusSeconds(60 * 30))
                )
                .claim(
                        "token_exp",
                        60 * 60 * 24 * 30
                )
                .build();

        SignedJWT signedJWT =
                new SignedJWT(header, claims);

        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    private String loadPrivateJwkJson() throws IOException {
        if (privateKeyPath == null || privateKeyPath.isBlank()) {
            throw new IllegalStateException("line.private-key-path is required");
        }

        return Files.readString(Path.of(privateKeyPath), StandardCharsets.UTF_8);
    }
}