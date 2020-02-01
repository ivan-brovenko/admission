package ua.nure.brovenko.SummaryTask4.encriptor;


import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password encriptor using md5 algorithm
 * @author Ivan Brovenko
 */
public class PasswordEncriptor {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getRootLogger();

    /**
     * Method that ciphers password
     * @param password password string
     * @return ciphered string
     */
    public static String hashMD5(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes("UTF-8"),0,password.length());

            //update-updates data in message digest
            String hash = new BigInteger(1,messageDigest.digest()).toString(16);

            //BigInteger 1-for positive numbers 0 for 0 -1 for negative
            //toString(16) means to hexadecimal

            return hash;

        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(),e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(),e);
        }

        return null;
    }
}
