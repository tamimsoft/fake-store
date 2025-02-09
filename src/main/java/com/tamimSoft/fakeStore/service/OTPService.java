package com.tamimSoft.fakeStore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final EmailService emailService;

    private final Map<String, OTPSession> otpStorage = new HashMap<>();

    public static class OTPSession {
        String otp;
        long creationTime;

        public OTPSession(String otp, long creationTime) {
            this.otp = otp;
            this.creationTime = creationTime;
        }
    }

    // Generate a 6-digit OTP
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        return String.valueOf(otp);
    }

    // Send OTP via email
    public void sendOTP(String email, String otp) {
        String emailContent = "<h3>Hello,</h3>"
                + "<p>Your OTP for signup is: <b>" + otp + "</b></p>"
                + "<p>This OTP is valid for 5 minutes.</p>"
                + "<p>Regards,<br>FakeStore Team</p>";
        emailService.sendMail(
                email,
                "Your OTP for Signup - FakeStore",
                emailContent
        );
        // Store OTP with creation time
        otpStorage.put(email, new OTPSession(otp, System.currentTimeMillis()));
    }

    // Validate OTP
    public boolean validateOTP(String email, String otp) {
        OTPSession session = otpStorage.get(email);
        if (session != null && session.otp.equals(otp)) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - session.creationTime <= 5 * 60 * 1000) { // 5 minutes expiry
                otpStorage.remove(email); // Clear OTP after validation
                return true;
            }
        }
        return false;
    }
}