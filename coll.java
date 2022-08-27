package aes;

/**
 *
 * @author Mohammad_AboHasan
 */
public class coll {

//                              0  1  2  3  4  5  6  7  8
    protected final int[] mX = {1, 1, 0, 1, 1, 0, 0, 0, 1}; // irreducible polynomial m(x)
    protected int[] Q = new int[9];
    protected int[] rem = new int[9];
    protected String[] output = new String[16];
    private final String[] hexToBinary = {
        "0000", "0001", "0010", "0011",
        "0100", "0101", "0110", "0111",
        "1000", "1001", "1010", "1011",
        "1100", "1101", "1110", "1111"
    };

    protected String BinaryToHex(int[] a) {
        String binNum = "";
        for (int i = 0; i < 8; i++) {
            binNum += a[8 - i - 1];
        }
        String b1 = binNum.substring(0, 4), b2 = binNum.substring(4);
        String hexNum, h1 = "", h2 = "";
        for (int i = 0; i < hexToBinary.length; i++) {
            if (b1.equals(hexToBinary[i])) {
                h1 = (char) (i >= 0 && i <= 9 ? i + '0' : (i - 10) + 'A') + "";
            }
            if (b2.equals(hexToBinary[i])) {
                h2 = (char) (i >= 0 && i <= 9 ? i + '0' : (i - 10) + 'A') + "";
            }
        }
        hexNum = h1 + h2;
        hexNum = hexNum.toLowerCase();
        return hexNum;
    }

    protected int[] HexToBinary(String s) {
        String hexNum = s.toUpperCase();
        char h1 = hexNum.charAt(0), h2 = hexNum.charAt(1);
        String binNum = hexToBinary[h1 >= '0' && h1 <= '9' ? h1 - '0' : h1 - 'A' + 10]
                + hexToBinary[h2 >= '0' && h2 <= '9' ? h2 - '0' : h2 - 'A' + 10];
        int[] arr = new int[8];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = binNum.charAt(8 - i - 1) - '0';
        }
        return arr;
    }

    protected int Degree(int[] a) {
        int deg = -1;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] == 1) {
                deg = i;
                break;
            }
        }
        return deg;
    }

    protected void Division(int[] a, int[] b) {
        if (Degree(b) == -1) {
            System.arraycopy(b, 0, rem, 0, rem.length);
            return;
        }
        int diffDeg = Degree(a) - Degree(b);
        if (diffDeg < 0) {
            System.arraycopy(a, 0, rem, 0, rem.length);
            return;
        }
        Q[diffDeg] = 1;
        int[] tempA = new int[a.length];
        System.arraycopy(a, 0, tempA, 0, a.length);
        int[] tempB = new int[b.length];
        System.arraycopy(b, 0, tempB, 0, b.length);
        while (diffDeg-- > 0) {
            for (int i = tempB.length - 1; i > 0; i--) {
                tempB[i] = tempB[i - 1];
            }
            tempB[0] = 0;
        }
        for (int i = 0; i < tempA.length; i++) {
            tempA[i] ^= tempB[i];
        }
        Division(tempA, b);
    }

    protected void Multiplication(int[] a, int[] b) {
        int[] M = new int[9];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == 0 || b[j] == 0) {
                    continue;
                }
                M[i + j] += a[i] * b[j];
            }
        }
        for (int i = 0; i < M.length; i++) {
            M[i] %= 2;
        }
        Division(M, mX);
    }

    protected void Clear(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
    }

    public String[] getOutput() {
        return output;
    }

}
