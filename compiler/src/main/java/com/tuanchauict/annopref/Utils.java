package com.tuanchauict.annopref;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by tuanchauict on 11/16/16.
 */

public class Utils {
    public static String getPackageName(Elements elementUtils, TypeElement type) throws NoPackageNameException {
        PackageElement pkg = elementUtils.getPackageOf(type);
        if (pkg.isUnnamed()) {
            throw new NoPackageNameException(type);
        }
        return pkg.getQualifiedName().toString();
    }

    static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public static String md5(String text) {
        byte[] bytes = md.digest(text.getBytes());
        BigInteger bi = new BigInteger(bytes);
        return bi.toString(16);
    }


    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }
}
