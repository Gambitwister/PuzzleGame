package others;

import java.util.ArrayList;
import java.util.Random;

public class CodeUtil {
    // do not let anyone initialize this class
    private CodeUtil() {
    }

    static String code;

    public static String getCode() {
        Random r = new Random();
        // store the lower and upper case of all characters in an ArrayList
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a' + i));
            list.add((char)('A' + i));
        }

        // generate a 5-bit code with 4 characters and 1 number
        char[] codeArr = new char[5];
        // set codeArr as 4-char + 1-numb, like "abcd1" at first
        for (int i = 0; i < codeArr.length; i++) {
            // set the number
            if (i == codeArr.length-1) {
                codeArr[i] =  (char) ('0' + (r.nextInt(9) + 1));
            }
            // set the character
            else {
                codeArr[i] = list.get(r.nextInt(list.size()));
            }
        }
        // shuffle codeArr
        for (int i = 0; i < codeArr.length; i++) {
            char temp;
            int randIndex = r.nextInt(codeArr.length);
            temp = codeArr[i];
            codeArr[i] = codeArr[randIndex];
            codeArr[randIndex] = temp;
        }

        // convert codeArr to String and return
        return new String(codeArr);
    }
}
