package com.shop.security;
import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.util.Date;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
@Service
public class JwtService {
 private final SecretKey key; private final long expiry;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiry){
  byte[] bytes;
  try { bytes=java.util.Base64.getDecoder().decode(secret); } catch(Exception e){bytes=secret.getBytes(StandardCharsets.UTF_8);}
  if(bytes.length<32) throw new IllegalArgumentException("JWT secret must contain at least 32 bytes");
  this.key=Keys.hmacShaKeyFor(bytes);this.expiry=expiry;
 }
 public String generate(String email,String role){Date now=new Date();return Jwts.builder().subject(email).claim("role",role).issuedAt(now).expiration(new Date(now.getTime()+expiry)).signWith(key).compact();}
 public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
