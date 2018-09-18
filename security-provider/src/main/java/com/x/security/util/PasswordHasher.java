package com.x.security.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * 密码hash
 *
 * @author xiaojuan.zhan
 */
public class PasswordHasher {
    public static final Integer DEFAULT_ITERATIONS = 10000;
    public static final String algorithm = "pbkdf2_sha256";
    public static final String format = "%s$%d$%s$%s";

    /**
     * 获取加密后的串
     *
     * @param password
     * @param salt
     * @param iterations
     * @return
     */
    private static String getEncodedHash(String password, String salt, int iterations) {
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        generator.init(PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(password.toCharArray()), salt.getBytes(), iterations);
        byte[] dk = ((KeyParameter) generator.generateDerivedParameters(256)).getKey();
        byte[] hashBase64 = Base64.encodeBase64(dk);
        return new String(hashBase64);
    }

    private static String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format(format, algorithm, iterations, salt, hash);
    }

    public static String encode(String password, String salt) {
        return encode(password, salt, DEFAULT_ITERATIONS);
    }

    /**
     * 核对密码是否准确
     *
     * @param password
     * @param hashedPassword
     * @return
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and
        // HASH; parts are joined with dollar character ("$")
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        //12位的随机数
        String salt = parts[2];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }


}
