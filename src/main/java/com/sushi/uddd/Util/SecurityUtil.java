package com.sushi.uddd.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;
import com.sushi.uddd.Domain.Response.ResLoginDto;
import java.time.temporal.ChronoUnit;

@Service
public class SecurityUtil {
    private final JwtEncoder jwtEncoder;

    @Value("${nam.jwt.key.base64-secret}")
    private String jwtKey;

    @Value("${nam.jwt.expiration.base64-secret}")
    private long accessTokenExpiration;

    @Value("${nam.jwt.expiration.otptoken.base64-secret}")
    private long accessTokenForget;
    @Value("${nam.refresh-jwt.expiration.base64-secret}")
    private long refreshTokenExpiration;

    public SecurityUtil(JwtEncoder jwtEncoder){
        this.jwtEncoder = jwtEncoder;
    }

    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS256;

    public String createAccessToken(String email, ResLoginDto dto) {
        ResLoginDto.UserInsideToken userToken = new ResLoginDto.UserInsideToken();
        userToken.setId(dto.getUser().getId());
        userToken.setEmail(dto.getUser().getEmail());
        userToken.setName(dto.getUser().getName());

        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(email)
            .claim("user", userToken)
            .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
    public String createRefreshToken(String email, ResLoginDto dto) {
        ResLoginDto.UserInsideToken userToken = new ResLoginDto.UserInsideToken();
        userToken.setId(dto.getUser().getId());
        userToken.setEmail(dto.getUser().getEmail());
        userToken.setName(dto.getUser().getName());
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", userToken)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
    public String generateOtp (){
        int randomOtp = (int)(Math.random()*90000)+10000;
        return String.valueOf(randomOtp);
    }
    public String createForgetToken(String email){
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenForget, ChronoUnit.SECONDS);
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuedAt(now)
            .expiresAt(validity)
            .subject(email)
            .claim("email", email)
            .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}

