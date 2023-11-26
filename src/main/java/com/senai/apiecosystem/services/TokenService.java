package com.senai.apiecosystem.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.senai.apiecosystem.models.UsuarioModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(UsuarioModel usuario) {
        try {
            Algorithm algoritimo = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    //Emissor do TOKEN
                    .withIssuer("api-vsconnect")
                    //Subject => Emai/ID
                    .withSubject(usuario.getEmail())
                    //Expiração do TOKEN
                    .withExpiresAt(gerarValidadeToken())
                    //.withClaim("nomeUsuario", usuario.getNome())
                    .sign(algoritimo);

            return token;

        }catch(JWTCreationException exception){
            throw new RuntimeException("erro na criação do token:", exception);
        }
    }

    public Instant gerarValidadeToken(){
        //Retorna a dataeHora atual + 2 Hora convertido para Instant com FusoHorario -3(Brasília)
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String validarToken(String token){
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer("api-vsconnect")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTCreationException exception){
            return "";
        }
    }
}
