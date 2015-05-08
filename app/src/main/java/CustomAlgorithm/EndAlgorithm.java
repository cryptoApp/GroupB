package CustomAlgorithm;

/**
 * Created by Christian on 2015-05-08.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
public class EndAlgorithm {
    int VALUE_FOR_MODULUS = 2;
    int FIXED_LENGTH = -5;
    boolean hasUpper, hasLower, hasSpecChar;
    List<Integer> valuesBeingUsed = new ArrayList<Integer>();
    String mPass;
    String orgin;
    char[] lib = {',', '-', '=', '?', '%', '#', '"', '!', '+', '$', '.', '*', '@', '_', '^', '~', '(', ')', '/', '[', ']', '&', '½', '§'};
    char[] alphalib = {'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l',
            'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'Z', 'z'};

    public EndAlgorithm(String AesEncryptedPass, String orgin) {
        this.mPass = AesEncryptedPass;
        this.orgin = orgin;
    }

    public EndAlgorithm(String AesEncryptedPass, String orgin, int fixedLength, boolean hasLower, boolean hasUpper, boolean hasSpecChar) {
        this.mPass = AesEncryptedPass;
        this.orgin = orgin;
        this.hasLower = hasLower;
        this.hasUpper = hasUpper;
        this.hasSpecChar = hasSpecChar;
        FIXED_LENGTH = fixedLength;
    }

    public String finalAlgorithm() {
        String finalPassword = "";
        for (int i = 0; i < mPass.length(); i++) {
            if (!Character.isDigit(mPass.charAt(i)) && !Character.isLetter(mPass.charAt(i)) || mPass.charAt(i) == ' ') {
                finalPassword += ".";
            } else {
                finalPassword += mPass.charAt(i);
            }

        }

        finalPassword = addCustomLibToPassword(finalPassword);
        try {
            File f = new File("pass");
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(finalPassword.getBytes());
            fos.close();
        } catch (Exception e) {

        }

        return finalPassword;
    }

    private String addCustomLibToPassword(String finalPassword) {

        int orginCounter = 0;
        int val = 0;
        String passEvo = "";
        for (int i = 0; i < finalPassword.length(); i++) {

            if (finalPassword.charAt(i) == '.') {
                if (orginCounter < orgin.length()) {
                    val = (int) orgin.charAt(orginCounter);
                } else {
                    orginCounter = 0;
                    val = (int) orgin.charAt(orginCounter);
                }

                if (val < lib.length) {
                    passEvo += Character.toString(lib[val]);
                } else {

                    while (val >= lib.length) {

                        if (valuesBeingUsed.contains(VALUE_FOR_MODULUS)) {
                            VALUE_FOR_MODULUS++;
                        }

                        val = (val % VALUE_FOR_MODULUS);

                        valuesBeingUsed.add(VALUE_FOR_MODULUS);
                    }
                    passEvo += Character.toString(lib[val]);
                }

            } else {

                passEvo += Character.toString(finalPassword.charAt(i));
            }
            orginCounter++;

        }

        return passEvo;

    }

}
