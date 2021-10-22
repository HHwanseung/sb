package org.zerock.sb.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sb.security.util.JWTUtil;

import java.rmi.server.ExportException;

@SpringBootTest
@Log4j2
public class JWTTests {

    @Autowired
    JWTUtil jwtUtil;

    @Test
    public void testGenerate() {
        String jwtStr = jwtUtil.generateToken("user11");

        log.info(jwtStr);
    }

    @Test
    public void testValidate() {

        String str = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMTEiLCJpYXQiOjE2MzQ4NzA0NjEsImV4cCI6MTYzNDg3MTA2MX0.MX-JrI-WSsB0vHUqOtkHUZXlXiboqAKv-e1q0Wvx9Po";
        //이 토큰이 유효한 토큰

        try {
            jwtUtil.validateToken(str);
        }catch (ExpiredJwtException ex){
            log.info("expired.....................");

            log.error(ex.getMessage());


        }catch (JwtException ex){

            log.info("jwtException.....................");
            log.error(ex.getMessage());
        }


    }

}
