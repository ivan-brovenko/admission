package ua.nure.brovenko.SummaryTask4.resources;

import java.util.Random;

/**
 * Class for generating new password
 */
public class RandomStringGenerator {

    /**
     * Method for generating a new password
     * @return new password
     */
    public String getSaltString() {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
