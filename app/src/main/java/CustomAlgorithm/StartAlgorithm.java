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

    //basic constructor
    public StartAlgorithm(String keyFromUser, String password, Context c) throws Exception {

        encGen = new EncryptGenerator();
        keyGen = new KeyGenerator();

        byte[] key = keyGen.getKey(keyFromUser);    //GEnereates a key
        encGen.setKey(key);

        String pass = new String(encGen.encryptPassword(password.getBytes())); //Get the AES encrypted password

        endAlg = new EndAlgorithm(pass, keyFromUser);
        //Encrypt the password in our engine
        pass = endAlg.finalAlgorithm();
        Toast.makeText(c, pass, Toast.LENGTH_SHORT).show();
    }

    public StartAlgorithm(String keyFromUser, String password, Context c, int fixedLength, boolean hasLower, boolean hasUpper, boolean hasSpecChar) throws Exception {

        encGen = new EncryptGenerator();
        keyGen = new KeyGenerator();

        byte[] key = keyGen.getKey(keyFromUser);    //GEnereates a key
        encGen.setKey(key);

        String pass = new String(encGen.encryptPassword(password.getBytes())); //Get the AES encrypted password

        endAlg = new EndAlgorithm(pass, keyFromUser);
        //Encrypt the password in our engine
        pass = endAlg.finalAlgorithm();
        Toast.makeText(c, pass, Toast.LENGTH_SHORT).show();
    }
}

