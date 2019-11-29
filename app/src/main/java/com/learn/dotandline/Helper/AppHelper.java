package com.learn.dotandline.Helper;

import android.app.Application;

import com.learn.dotandline.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppHelper {

    public static String BASE_URL = "http://47.0.0.105:64000";

    public static String DoTransaction = BASE_URL + "/http://47.0.0.105:64000";
    public static String RegisterUser = BASE_URL + "/DoTransaction";
    public static String Login = BASE_URL + "/Login";
    public static String TransactionHistory = BASE_URL + "/TransactionHistory";
    public static String UpdateWallet = BASE_URL + "/UpdateWallet";
    public static String VerifyAccount = BASE_URL + "/VerifyAccount";


    public static String getSignature(String... parameter) {
        String parameters = "";
        for (String text : parameter
        ) {
            parameters += text;

        }


        return md5(parameters + "bca2c785b14f79b3d5ec0d5db2f95d4dc385ffbb1a023e089625v987bd41a555");

    }


    private static String md5(final String s) {

//        Log.e("String_code ",""+s);
        final String MD5 = "MD5";
        try {

            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
