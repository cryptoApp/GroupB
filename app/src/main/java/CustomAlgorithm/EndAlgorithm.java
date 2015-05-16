package CustomAlgorithm;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
public class EndAlgorithm {
    private boolean hasNumbers = false;
    int VALUE_FOR_MODULUS = 2;
    int FIXED_LENGTH = -1;
    boolean hasUpper, hasLower, hasSpecChar;

    boolean REQUEST_FOR_CUSTOM;
    List<Integer> valuesBeingUsed = new ArrayList<Integer>();
    String mPass;
    String orgin;
    char[] numbLib = {'0', '1','2', '3', '4', '5', '6', '7', '8', '9'};
    char[] lib = {',', '-', '=', '?', '%', '#', '"', '!', '+', '$', '.', '*', '@', '_', '^', '~', '(', ')', '/', '[', ']', '&', '½', '§'};
    char[] alphalib = {'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j', 'K', 'k', 'L', 'l',
            'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 't', 'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'Z', 'z'};

    public EndAlgorithm(String AesEncryptedPass, String orgin) {
        this.mPass = AesEncryptedPass;
        this.orgin = orgin;
        REQUEST_FOR_CUSTOM = false;
    }

    public EndAlgorithm(String AesEncryptedPass, String orgin, int fixedLength, boolean hasLower, boolean hasUpper, boolean hasSpecChar, boolean hasNumbers) {
        this.mPass = AesEncryptedPass;
        this.orgin = orgin;
        this.hasLower = hasLower;
        this.hasUpper = hasUpper;
        this.hasSpecChar = hasSpecChar;
        this.hasNumbers = hasNumbers;
        FIXED_LENGTH = fixedLength;
        REQUEST_FOR_CUSTOM = true;
    }

    public String finalAlgorithm() {
        String finalPassword = "";
        for (int i = 0; i < mPass.length(); i++) {
            if (!Character.isLetter(mPass.charAt(i)) || mPass.charAt(i) == ' ') {
                finalPassword += ".";
            } else {

                if (!Character.toString(mPass.charAt(i)).matches("^[-a-zA-Z0-9._]+")) {
                    finalPassword += ".";
                } else {
                    finalPassword += mPass.charAt(i);
                }
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
        boolean mixer = false, boundMixer = false;
        int val = 0, indexBoundery = 0;
        String passEvo = "";
        int c = 0;
        if (FIXED_LENGTH != -1) {
            c = FIXED_LENGTH;
        } else {
            c = finalPassword.length();
        }
        for (int j = 0; j < c; j++) {

            while (indexBoundery >= finalPassword.length()) {
                indexBoundery = (indexBoundery / 2);
            }

            if (finalPassword.charAt(indexBoundery) == '.') {
                if (orginCounter < orgin.length()) {
                    val = (int) orgin.charAt(orginCounter);
                } else {
                    orginCounter = 0;
                    val = (int) orgin.charAt(orginCounter);
                }

                if (mixer && REQUEST_FOR_CUSTOM && hasSpecChar || !REQUEST_FOR_CUSTOM || !hasLower && !hasUpper && hasSpecChar || REQUEST_FOR_CUSTOM && FIXED_LENGTH != -1 && !hasUpper && !hasLower) {
                    if (val < lib.length) {
                        passEvo += Character.toString(lib[val]);
                    } else {
                        int safeteyCounter = 0;
                        while (val >= lib.length) {

                            if (valuesBeingUsed.contains(VALUE_FOR_MODULUS)) {
                                VALUE_FOR_MODULUS++;
                            }

                            val = (val % VALUE_FOR_MODULUS);

                            valuesBeingUsed.add(VALUE_FOR_MODULUS);
                            safeteyCounter++;
                            if (safeteyCounter >= 2000) {
                                VALUE_FOR_MODULUS = 2;
                                valuesBeingUsed.clear();
                            }

                        }

                        passEvo += Character.toString(lib[val]);
                    }

                } else {
                    if (val < alphalib.length) {
                        passEvo += Character.toString(alphalib[val]);
                    } else {

                        while (val >= alphalib.length) {

                            if (valuesBeingUsed.contains(VALUE_FOR_MODULUS)) {
                                VALUE_FOR_MODULUS++;
                            }

                            val = (val % VALUE_FOR_MODULUS);

                            valuesBeingUsed.add(VALUE_FOR_MODULUS);
                        }
                        if (REQUEST_FOR_CUSTOM && hasUpper && boundMixer && hasLower || REQUEST_FOR_CUSTOM && hasUpper && !hasLower) {
                            passEvo += Character.toString(alphalib[val]).toUpperCase();
                        }

                        if (REQUEST_FOR_CUSTOM && hasLower && !boundMixer && hasUpper || REQUEST_FOR_CUSTOM && !hasUpper && hasLower) {
                            passEvo += Character.toString(alphalib[val]).toLowerCase();
                        }

                        if (!REQUEST_FOR_CUSTOM) {
                            passEvo += Character.toString(alphalib[val]);
                        }

                        boundMixer = !boundMixer;
                    }
                }

            } else {
                if (REQUEST_FOR_CUSTOM && hasUpper && hasLower) {
                    passEvo += Character.toString(finalPassword.charAt(indexBoundery));
                } else if (REQUEST_FOR_CUSTOM && hasLower) {
                    passEvo += Character.toString(finalPassword.charAt(indexBoundery)).toLowerCase();
                } else if (REQUEST_FOR_CUSTOM && hasUpper) {
                    passEvo += Character.toString(finalPassword.charAt(indexBoundery)).toUpperCase();
                } else if (hasSpecChar && REQUEST_FOR_CUSTOM) {
                    j--;
                } else {
                    passEvo += Character.toString(finalPassword.charAt(indexBoundery));
                }
            }
            mixer = !mixer;
            orginCounter++;
            indexBoundery++;

        }

        if (this.REQUEST_FOR_CUSTOM) {
            passEvo = customizeFurther(passEvo);
        }

        return passEvo;
    }

    private String customizeFurther(String passEvo) {

        if (this.hasLower) {
            boolean succeed = false;
            for (int i = 0; i < passEvo.length(); i++) {
                if (Character.isLowerCase(passEvo.charAt(i))) {
                    succeed = true;
                }
            }

            if (!succeed) {
                char[] temp = passEvo.toCharArray();
                temp[0] = Character.toLowerCase(passEvo.charAt(0));
                passEvo = String.valueOf(temp);
            }
        }

        if (this.hasUpper) {
            boolean succeed = false;
            for (int i = 0; i < passEvo.length(); i++) {
                if (Character.isUpperCase(passEvo.charAt(i))) {
                    succeed = true;
                }
            }

            if (!succeed) {
                char[] temp = passEvo.toCharArray();
                temp[1] = Character.toUpperCase(passEvo.charAt(1));
                passEvo = String.valueOf(temp);
            }
        }

        if (this.hasSpecChar) {
            boolean succeed = false;
            for (int i = 0; i < passEvo.length(); i++) {
                if (!Character.isDigit(passEvo.charAt(i)) && !Character.isLetter(passEvo.charAt(i)) || passEvo.charAt(i) == ' ') {
                    succeed = true;
                }
            }

            if (!succeed) {
                char[] temp = passEvo.toCharArray();

                int index = (int) temp[0];
                while (index >= lib.length) {
                    index -= VALUE_FOR_MODULUS;
                }
                temp[2] = lib[index];
                passEvo = String.valueOf(temp);
            }
        }

        if (this.hasNumbers) {
            boolean succeed = false;
            for (int i = 0; i < passEvo.length(); i++) {
                if (Character.isDigit(passEvo.charAt(i))) {
                    succeed = true;
                }
            }

            if (!succeed) {

                char[] temp = passEvo.toCharArray();

                int index = (int) temp[0];
                while (index >= numbLib.length || index < 0) {
                    index -= VALUE_FOR_MODULUS;

                    if(index < 0) {
                        index += VALUE_FOR_MODULUS;
                        VALUE_FOR_MODULUS = (VALUE_FOR_MODULUS/2);
                    }
                }
                temp[3] = numbLib[index];
                passEvo = String.valueOf(temp);
            }

        }
        return passEvo;
    }
}

