package com.sohan.piggybank;

import java.security.SecureRandom;
import java.util.Random;

public class RandomID {

    public static Long randomId(){
        SecureRandom secureRandom = new SecureRandom();
        Long secureId = secureRandom.nextLong(1000);
        return secureId;
    }
    public static Long randomTransactionId(){
        Random random = new Random();
        long randomNumber = 100_000_0000L + random.nextInt(900_000_000);
        return randomNumber;
    }
}
