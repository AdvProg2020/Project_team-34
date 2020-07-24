package server.security;

import exceptionalMassage.ExceptionalMassage;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static final int STRING_MAX_LENGTH = 15;
    public static final int EMAIL_MAX_LENGTH = 40;

    public static void normalStringValidation(String input) throws ExceptionalMassage {
        if(input.length() > STRING_MAX_LENGTH){
            throw new ExceptionalMassage("You have Exceeded maxLength");
        }
        if(!getMatcher(input, "^[a-zA-Z]+$").matches()){
            throw new ExceptionalMassage("Invalid String ");
        }
    }

    public static void userPassStringValidation(String input) throws ExceptionalMassage {
        if(input.length() > STRING_MAX_LENGTH){
            throw new ExceptionalMassage("You have Exceeded maxLength");
        }
        if(!getMatcher(input, "^\\w+$").matches()){
            throw new ExceptionalMassage("Invalid String");
        }
    }

    public static void normalIntValidation(String input) throws ExceptionalMassage{
        int number;
        try{
            number = Integer.parseInt(input);
        }catch (NumberFormatException exception){
            throw new ExceptionalMassage("Not an Integer");
        }
        if(number <= 0){
            throw new ExceptionalMassage("Negative values are not allowed");
        }
    }

    public static void emailValidation(String input) throws ExceptionalMassage{
        if(input.length()> EMAIL_MAX_LENGTH){
            throw new ExceptionalMassage("You have ExceededMaxLength");
        }
        if(!getMatcher(input, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matches()){
            throw new ExceptionalMassage("Invalid Email Address");
        }
    }

    public static void filePathValidation(String filePath) throws ExceptionalMassage{
        File file = new File(filePath);
        if(! file.exists()){
            throw new ExceptionalMassage("Invalid file path");
        }
    }

    public static void phoneNumberValidation(String input)throws ExceptionalMassage{
        if(!getMatcher(input,"\\d{11}" ).matches()){
            throw new ExceptionalMassage("Invalid phone number");
        }
    }

    public static void booleanValidation(String input)throws ExceptionalMassage{
        try {
            Boolean.parseBoolean(input);
        }catch (Exception e){
            throw new ExceptionalMassage("Not a boolean");
        }
    }

    public static Matcher getMatcher(String input , String regexString){
        Pattern pattern = Pattern.compile(regexString);
        return pattern.matcher(input);
    }
}
