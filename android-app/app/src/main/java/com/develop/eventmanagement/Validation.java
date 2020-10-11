package com.develop.eventmanagement;

import android.util.Patterns;
import android.widget.TextView;

import java.util.regex.Pattern;


public class Validation {

    private static final Pattern specialChars = Pattern.compile("[$=\\\\|/<>^*%]");
    private static final Pattern namespecialChars = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");


    public static boolean checkIsEmpty(TextView... editTexts) {
        boolean isEmpty = false;

        for (TextView e : editTexts) {
            if (e.getText().toString().trim().isEmpty()) {
                isEmpty = true;
//                e.setError(e.getResources().getString(R.string.err_empty));
            } else {
//                e.setError(null);
            }
        }
        return isEmpty;
    }
    public static boolean checkIsAnEmail(TextView editText) {
//        if(!checkIsEmpty(editText)) {
            if (Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
//                editText.setError(null);
                return true;
            }else
//                editText.setError(editText.getResources().getString(R.string.err_email_valid));
//        }

        return false;
    }
public static boolean isAValidMobile(TextView editText) {
        if(Patterns.PHONE.matcher(editText.getText().toString()).matches()) {
            editText.setError(null);
            return true;
        }
        editText.setError(editText.getResources().getString(R.string.err_mobile_valid));
        return false;
    }
    public static boolean isAValidPassword(TextView... editText) {
//        if (!checkIsHavingSpecialChars(editText)) {
        if(!checkIsEmpty(editText)) {
            for (TextView ed : editText) {
                String text = ed.getText().toString();
               /* if (text.contains(" ") || text.contains("\t")) {
                    ed.setError(ed.getResources().getString(R.string.err_pass_space));
                    return false;
                }*/
                if (text.length() < 6) {
                    ed.setError(ed.getResources().getString(R.string.err_pass_length));
                    return false;
                } else {
                    return true;
                }
            }
        }
//            return true;
//        }
        return false;
    }
    public static boolean checkIsHavingSpecialChars(TextView... editTexts) {
        boolean contains = false;
        for (TextView e : editTexts) {
            if(specialChars.matcher(e.getText().toString()).find()) {
                contains=true;
                e.setError(e.getResources().getString(R.string.err_special_characters));
            }
        }
        return contains;
    }
    public static boolean isAvalidName(TextView editText) {
//        boolean isValid=editText.getText().toString().matches("[a-zA-Z0-9 ]*");
//        if(!checkIsEmpty(editText)) {
            boolean isValid = namespecialChars.matcher(editText.getText().toString()).find();
            if (!isValid) {
//            Utilities.showDismisAlert(editText.getContext(),"Message",editText.getResources().getString(R.string.err_special_characters),"Ok",null);
                editText.setError(editText.getResources().getString(R.string.err_special_characters));
            } else {
                editText.setError(null);
            }

            return isValid;
//        }
//        return true;

    }


    public static boolean isPasswordMatch(TextView password, TextView retype) {
        boolean check = true;
        if(checkIsEmpty(password,retype)) {
            return false;
        }
        if(!password.getText().toString().equals(retype.getText().toString())) {
            retype.setError(retype.getResources().getString(R.string.err_pass_match));
            check = false;
        } else {
            retype.setError(null);
        }
        return check;
    }
}
