package com.gopal.devjunction.passwordgenerator;

import java.util.concurrent.ThreadLocalRandom;

public class PasswordGenerator {

    //we will use ASCII character from code 32 to 126
    private static final int MIN_CODE = 48;
    private static final int MAX_CODE = 56;

    public static String Process(int lenght){
            StringBuilder stringBuilder = new StringBuilder(  );

            for (int i = 0 ; i < lenght ; i++){
                stringBuilder.append(( char )ThreadLocalRandom.current().nextInt(MIN_CODE , MAX_CODE + 1 ));
            }
            return  stringBuilder.toString();
    }
}
