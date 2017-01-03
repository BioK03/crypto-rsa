/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Bertrand
 */
public class AsciiConversion {

    public static String IntToString(String s) {
        String msg = "";
        while (s.length() % 3 != 0) {
            s = "0" + s;
        }
        for (int i = 0; i < s.length() / 3; i++) {
            int temp = Integer.parseInt(s.subSequence(i * 3, (i + 1) * 3).toString());
            msg += Character.toString((char) temp);
        }
        return msg;
    }

    public static String StringToInt(String s) {
        String msg = "";
        for (int i = 0; i < s.length(); i++) {
            String character = ((int) s.charAt(i)) + "";
            while (character.length() < 3) {
                character = "0" + character;
            }
            msg += character;
        }
        return msg;
    }
    
}
