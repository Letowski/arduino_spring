package hello.classes;


import java.security.MessageDigest;

/**
 * Created by yana on 27.01.16.
 */
public class Utils {

    public static String md5(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        }catch (Exception e){
            return "nope";
        }
    }

    public static String md5Salted(String original){
        return md5(original+"very randomly string");
    }

    public static String md5(byte[] digest) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(digest);
            digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        }catch (Exception e){
            return "nope";
        }
    }
}