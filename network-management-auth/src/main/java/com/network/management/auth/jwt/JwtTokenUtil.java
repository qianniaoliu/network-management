package com.network.management.auth.jwt;

import com.network.management.auth.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * @author yusheng
 * @date 2020-09-07
 */
@Component
public class JwtTokenUtil {

    public String generatorToken(SysUser sysUser, int expire){
        Claims claims = Jwts.claims().setSubject(sysUser.getUsername());
        claims.put(JwtContants.JWT_KET_USER_ID,sysUser.getUsername());
        return Jwts.builder().setClaims(claims)//目前只传一个参数进去
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.HS256,getKeyInstance()).compact();

    }


    public String refreshGeneratorToken(Claims claims, int expire){
        return Jwts.builder().setClaims(claims)//目前只传一个参数进去
                .setExpiration(DateTime.now().plusSeconds(expire).toDate())
                .signWith(SignatureAlgorithm.HS256,getKeyInstance()).compact();
    }

    public Key getKeyInstance(){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] bytes = DatatypeConverter.parseBase64Binary("user-token");
        return new SecretKeySpec(bytes,signatureAlgorithm.getJcaName());
    }

    public SysUser getToken(String token){
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
            Claims claims = jws.getBody();
            return new SysUser(claims.get(JwtContants.JWT_KET_USER_ID).toString());
        }catch (Exception e){
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails){

        SysUser sysUser = getToken(token);
        if(sysUser.getUsername().equals(userDetails.getUsername())){
            return true;
        }
        return false;
    }

    public String refreshToken(String token, int expire){
        Jws<Claims> jws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        Claims claims = jws.getBody();
        return refreshGeneratorToken(claims,expire);
    }

}
