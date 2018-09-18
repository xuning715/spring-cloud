package com.x.security.util;

public class PasswordBuilder {

    public static String encodePassword(String rawPass) {
        String salt = ValidateUtils.getPasswordRandom(12);
        return PasswordHasher.encode(rawPass, salt);
    }

    public static boolean isPasswordValid(String rawPass, String encPass) {
        return PasswordHasher.checkPassword(rawPass, encPass);
    }

    public static void main(String[] args ){
        String pass = "xuning";
        String pass1 = encodePassword(pass);
        String xxx = "xuning1";
        String xxx1 = encodePassword(xxx);
        System.out.println(pass1);
        System.out.println(isPasswordValid(pass, pass1));
        System.out.println(xxx1);
        System.out.println(isPasswordValid(xxx, xxx1));
    }

}
