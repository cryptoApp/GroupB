package CustomAlgorithm;

/**
 * Created by Christian on 2015-05-08.
 */

/**
 *
 * @author Christian
 */
public class KeyGenerator {
    int DIVIDER = 5;

    public byte[] getKey(String s) {
        byte[] b = new byte[16];
        int val = 0;

        if(s.length() >0) {
            DIVIDER = (int)s.charAt(0);
        }

        for (int i = 0; i < s.length(); i++) {
            val += (int) s.charAt(i);
        }

        for (int i = 0; i < 16; i++) {
            b[i] = (byte) (val / DIVIDER);
            val -= (val / DIVIDER);
        }
        return b;
    }

}


