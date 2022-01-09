package com.projprogiii.lib.utilities;

import java.util.regex.Pattern;

public class Util {

    public static boolean validateEmail(String email){
        return Pattern.matches("^(.+)@unito.it", email);
    }
}
