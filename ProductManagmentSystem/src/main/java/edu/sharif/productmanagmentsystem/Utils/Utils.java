package edu.sharif.productmanagmentsystem.Utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static String createCorrelationID (){
        return String.valueOf(System.currentTimeMillis() / 1000) + ThreadLocalRandom.current().nextLong();
    }
}
