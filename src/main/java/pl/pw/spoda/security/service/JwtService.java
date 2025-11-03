package pl.pw.spoda.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.pw.spoda.security.context.SpodaApplicationContext;
import pl.pw.spoda.security.context.Role;

import java.util.Base64;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private JWTVerifier verifier;

    private static final String ISSUER = "KS ŚPODA APP";

    @PostConstruct
    public void init() {
        byte[] secretBytes = Base64.getDecoder().decode( jwtSecret.trim() );
        this.verifier = JWT.require( Algorithm.HMAC256( secretBytes ) ).withIssuer( ISSUER ).build();
        log.info( "JwtService initialized" );
    }

    public boolean validateToken(String token) {
        try {
            verifier.verify( unwrapToken( token ) );
            return true;
        } catch (TokenExpiredException e) {
            log.warn( "Token expired: {}", e.getMessage() );
        } catch (JWTVerificationException | IllegalArgumentException e) {
            log.warn( "Invalid token: {}", e.getMessage() );
        }
        return false;
    }

    public SpodaApplicationContext extractContext(String token) {
        DecodedJWT jwt = verifier.verify( unwrapToken( token ) );
        return SpodaApplicationContext.builder()
                .username( jwt.getClaim( "username" ).asString() )
                .discordId( jwt.getClaim( "discord_id" ).asString() )
                .roleList( jwt.getClaim( "roles" ).asList( String.class ).stream().map( Role::getByTitle ).toList() )
                .build();
    }

    public String unwrapToken(String token) {
        if (token == null || token.isBlank()) throw new IllegalArgumentException( "Token is null or blank" );
        return token.trim().replaceFirst( "(?i)^Bearer\\s+", "" );
    }
}
