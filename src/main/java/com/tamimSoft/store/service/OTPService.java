package com.tamimSoft.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OTPService {

    private final EmailService emailService;

    private static final int OTP_MIN = 100000;
    private static final int OTP_MAX = 999999;

    // Temporary storage (consider using Redis or a database)
    private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();

    // Generate a 6-digit OTP
    public String generateOTP(String email) {
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(OTP_MIN, OTP_MAX + 1));
        otpStorage.put(email, otp);
        return otp;
    }

    // Send OTP via email
    public void sendOTP(String email, String otp) {
        String emailContent = buildOTPEmailTemplate(otp);
        emailService.sendMail(
                email,
                "Your OTP for Signup - FakeStore",
                emailContent
        );
        // Store OTP
        otpStorage.put(email, otp);
    }

    // Validate OTP
    public boolean validateOTP(String email, String userOtp) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(userOtp)) {
            otpStorage.remove(email); // OTP used, so remove it
            return true;
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