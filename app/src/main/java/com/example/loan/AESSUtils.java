package com.example.loan;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESSUtils {
    public static String baseCode() {
        return "dEQIlr/zRffDVkgJeIWM3Fofgh6ixYJuiwRbq2rNzTQ=";
    }

    public static String encrypt(String data) {
        try {
            IvParameterSpec initVector = new IvParameterSpec("3d0b49673c7a2399".getBytes("ISO-8859-1"));
            SecretKeySpec skeySpec = new SecretKeySpec("5a347d7b0123e0c1".getBytes("ISO-8859-1"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(1, skeySpec, initVector);
            return new String(Base64.encodeToString(cipher.doFinal(data.getBytes()), 0));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String data) {
        try {
            IvParameterSpec ivspec = new IvParameterSpec("3d0b49673c7a2399".getBytes("ISO-8859-1"));
            SecretKeySpec skeySpec = new SecretKeySpec("5a347d7b0123e0c1".getBytes("ISO-8859-1"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, skeySpec, ivspec);
            return new String(cipher.doFinal(Base64.decode(data, 0)));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
