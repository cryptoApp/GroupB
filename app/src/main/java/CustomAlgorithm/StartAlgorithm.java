package CustomAlgorithm;

/**
 * Created by Christian on 2015-05-08.
 */

import android.content.Context;
import android.widget.Toast;

/**
 * @author Christian
 */
public class StartAlgorithm {


    EncryptGenerator encGen;
    KeyGenerator keyGen;
    EndAlgorithm endAlg;
    String pass;

    //basic constructor
    public StartAlgorithm(String keyFromUser, String password) throws Exception {

        encGen = new EncryptGenerator();
        keyGen = new KeyGenerator();

        byte[] key = keyGen.getKey(keyFromUser);    //GEnereates a key
        encGen.setKey(key);

        pass = new String(encGen.encryptPassword(password.getBytes())); //Get the AES encrypted password

        endAlg = new EndAlgorithm(pass, keyFromUser);
        //Encrypt the password in our engine
        pass = endAlg.finalAlgorithm();
    }

    public StartAlgorithm(String keyFromUser, String password, int fixedLength, boolean hasLower, boolean hasUpper, boolean hasSpecChar,boolean hasNumbers) throws Exception {

        encGen = new EncryptGenerator();
        keyGen = new KeyGenerator();

        byte[] key = keyGen.getKey(keyFromUser);    //Generates a key
        encGen.setKey(key);

        pass = new String(encGen.encryptPassword(password.getBytes())); //Get the AES encrypted password

        endAlg = new EndAlgorithm(pass, keyFromUser,fixedLength,hasLower,hasUpper,hasSpecChar,hasNumbers);
        //Encrypt the password in our engine
        pass = endAlg.finalAlgorithm();
    }

    public String getPass() {
        return this.pass;
    }
}

