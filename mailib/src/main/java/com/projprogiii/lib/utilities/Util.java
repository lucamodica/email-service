package com.projprogiii.lib.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Util {

    public static boolean validateEmail(String email){
        return Pattern.matches("^(.+)@unito.it", email);
    }

    public static String formatDate(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormatter.format(date);
    }


}
