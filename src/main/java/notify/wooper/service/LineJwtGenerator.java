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

import java.time.Instant;
import java.util.Date;

@Service
public class LineJwtGenerator {
    @Value("${line.kid}")
    private String kid;

    @Value("${line.private-key}")
    private String privateKey;

    @Value("${line.channel-id}")
    private String channelId;

    public String generate() throws Exception {
        RSAKey rsaKey = RSAKey.parse(privateKey);

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
}