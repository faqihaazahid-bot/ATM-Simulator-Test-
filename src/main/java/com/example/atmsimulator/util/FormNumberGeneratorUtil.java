package com.example.atmsimulator.util;


import org.springframework.stereotype.Component;

import java.util.Random;
public class FormNumberGeneratorUtil {
    public static String generate5DigitFormNumber() {
        Random random = new Random();
        int number = 10000 + random.nextInt(90000); // 5 digit
        return String.valueOf(number);
    }
}
