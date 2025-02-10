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
        String emailContent = buildOTPEmailTemplate(otp);
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

    private String buildOTPEmailTemplate(String otp) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>OTP Email</title>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                "        .container { max-width: 600px; margin: 20px auto; padding: 20px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                "        h1 { color: #333333; }" +
                "        p { color: #555555; font-size: 16px; }" +
                "        .otp { font-size: 24px; font-weight: bold; color: #007BFF; }" +
                "        .footer { margin-top: 20px; font-size: 14px; color: #888888; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <h1>Your OTP for Signup</h1>" +
                "        <p>Dear User,</p>" +
                "        <p>Your One-Time Password (OTP) for signup is:</p>" +
                "        <p class=\"otp\">" + otp + "</p>" +
                "        <p>This OTP is valid for <strong>5 minutes</strong>. Please do not share it with anyone.</p>" +
                "        <div class=\"footer\">" +
                "            <p>If you did not request this OTP, please ignore this email.</p>" +
                "            <p>Best regards,<br>Fake Store Team</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}