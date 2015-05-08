package CustomAlgorithm;

/**
 * Created by Christian on 2015-05-08.
 */


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Christian
 */
public class EncryptGenerator {

    private SecretKeySpec myKey;
    private static Cipher encoder;

    public EncryptGenerator() throws Exception {
        encoder = Cipher.getInstance("AES");
    }

    public void setKey(byte[] key) throws Exception {
        myKey = new SecretKeySpec(key, "AES");

        encoder.init(Cipher.ENCRYPT_MODE, myKey);
    }

    public byte[] encryptPassword(byte[] pass) throws Exception {
        return encoder.doFinal(pass);
    }

}
