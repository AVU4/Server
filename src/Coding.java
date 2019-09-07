import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Coding {

    public String getPassword(int num){
        try {
            String password = String.valueOf(num);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(password.getBytes());
            String string = "";
            for (byte b: bytes){
                string += b;
            }

            return string;
        }catch (NoSuchAlgorithmException e){
            return null;
        }
    }
}
