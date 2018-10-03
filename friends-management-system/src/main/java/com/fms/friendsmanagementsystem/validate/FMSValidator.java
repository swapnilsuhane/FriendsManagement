package com.fms.friendsmanagementsystem.validate;

public class FMSValidator {

    //validate the email address
    public static boolean isInValidEmail(String email){
        if(email.split(" |;|,").length>1){
            return true;
        } else if (!email.contains("@") || !email.contains(".")){
            return true;
        }
        return false;
    }

}
