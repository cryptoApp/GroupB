package CustomAlgorithm;

/**
 * Created by Christian on 2015-05-08.
 */

/**
 *
 * @author Christian
 */public class Main {


    EncryptGenerator encGen;
    KeyGenerator keyGen;
    EndAlgorithm endAlg;

    public Main(String keyFromUser,String password) throws Exception {

            encGen = new EncryptGenerator();
            keyGen = new KeyGenerator();

            byte[] key = keyGen.getKey(keyFromUser);    //GEnereates a key
            encGen.setKey(key);

            String pass = new String(encGen.encryptPassword(password.getBytes())); //Get the AES encrypted password
            endAlg = new EndAlgorithm(pass, keyFromUser); //Encrypt the password in our engine
            pass = endAlg.finalAlgorithm();

        }
    }

