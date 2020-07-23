package server.security;

import exceptionalMassage.ExceptionalMassage;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static void normalStringValidation(String input, int maxLength) throws ExceptionalMassage {
        if(input.length() > maxLength){
            throw new ExceptionalMassage("You have Exceeded maxLength");
        }
        if(!getMatcher(input, "^[a-zA-Z]+$").matches()){
            throw new ExceptionalMassage("Invalid String ");
        }
    }



    public static void normalIntValidation(String input , int maxNumber) throws ExceptionalMassage{
        int number;
        try{
            number = Integer.parseInt(input);
        }catch (NumberFormatException exception){
            throw new ExceptionalMassage("Not an Integer");
        }
        if(number <= 0){
            throw new ExceptionalMassage("Negative values are not allowed");
        }
        if(number > maxNumber)
            throw new ExceptionalMassage("You have Exceeded maxNumber");
    }

    public static void emailValidation(String input , int maxLength) throws ExceptionalMassage{
        if(input.length()> maxLength){
            throw new ExceptionalMassage("You have ExceededMaxNumber");
        }
        if(!getMatcher(input, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$").matches()){
            throw new ExceptionalMassage("Invalid Email Address");
        }
    }

    public static void filePathValidation(String filePath) throws ExceptionalMassage{
        new File(filePath);
    }

    public static Matcher getMatcher(String input , String regexString){
        Pattern pattern = Pattern.compile(regexString);
        return pattern.matcher(input);
    }
}
