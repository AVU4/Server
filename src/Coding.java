import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Coding {

    private char[] letters = "abcdefghijklmnoprstuvwxyz".toCharArray();
    private char[] lettersUp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private int[] numbers = new int[]{0,1,2,3,4,5,6,7,8,9};

    public String getCode(String num){
        try {
            String password = num;
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

    public  String getPassword(){
        String password = "";
        for (int j =0 ; j < 5; j++) {
            int i = (int) (1 + Math.random() * 3) ;
            switch (i) {
                case 1:
                    password += letters[(int)(1+ Math.random()*(letters.length-1))];
                    break;
                case 2:
                    password += lettersUp[(int) (1+Math.random()*(lettersUp.length -1))];
                    break;
                case 3:
                    password += numbers[(int) (1+Math.random()*(numbers.length-1))];
                    break;
            }

        }
        return password;

    }
}
