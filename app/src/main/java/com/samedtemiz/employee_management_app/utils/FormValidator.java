package com.samedtemiz.employee_management_app.utils;

import android.widget.EditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidator {

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    public static boolean hasMaxLength(EditText editText, int maxLength) {
        return editText.getText().toString().trim().length() <= maxLength;
    }

    public static void setError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
    }

}
