package com.paslas.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.util.Pair;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.link}")
    private String link;

    @Value("${spring.mail.username}")
    private String username;

    private void storeVerificationToken(String token, UUID userId, String email) {
        String key = "verify-email:" + token;
        String value = userId + ":" + email;
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(15));
    }

    private void sendVerificationMail(String to, String verificationLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject("Email Verification");
        message.setText("Email adresinizi onaylamak için tıklayın: \n\n" + verificationLink);

        mailSender.send(message);
    }

    public void deleteToken(String token) {
        redisTemplate.delete("verify-email:"+token);
    }

    public Optional<Pair<UUID, String>> extractToken(String token) {
        String value = redisTemplate.opsForValue().get("verify-email"+token);
        if(value == null) {
            return Optional.empty();
        }
        String[] values = value.split(":");
        return Optional.of(Pair.of(UUID.fromString(values[0]), values[1]));
    }

    public void createAndStore(UUID userId, String email){
        String token = UUID.randomUUID().toString();
        storeVerificationToken(token, userId, email);
        sendVerificationMail(email, link+token);
    }
}

