package com.example.vankien.dating.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vanki on 4/12/2018.
 */

public class Validate {
    private Pattern pattern;
    private Matcher matcher;
    private static Validate shared = new Validate();

    public static Validate getShared() {
        return shared;
    }

    private static final String IMAGE_PATTERN =
            "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public Validate(){
        pattern = Pattern.compile(IMAGE_PATTERN);
    }

    /**
     * Validate image with regular expression
     * @param image image for validation
     * @return true valid image, false invalid image
     */
    public boolean validate(final String image){
        matcher = pattern.matcher(image);
        return matcher.matches();
    }
}
