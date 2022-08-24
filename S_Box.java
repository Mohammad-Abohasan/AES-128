package aes;

import static java.lang.Math.abs;
import java.util.Arrays;

/**
 *
 * @author Mohammad_AboHasan
 */
public class S_Box {
//                            0  1  2  3  4  5  6  7  8

    private final int[] mX = {1, 1, 0, 1, 1, 0, 0, 0, 1}; // irreducible polynomial m(x)
    private int[] Q = new int[9];
    private int[] rem = new int[9];
    private int[] multiplicativeInverse = new int[9];
    private String[] hexToBinary = {
        "0000", "0001", "0010", "0011",
        "0100", "0101", "0110", "0111",
        "1000", "1001", "1010", "1011",
        "1100", "1101", "1110", "1111"
    };
    private String[] output = new String[16];

    public S_Box(String[] input) {
        for (int i = 0; i < input.length; i++) {
            ExtendedEuclidAlgorithm(HexToBinary(input[i]));
//            MultiplicativeInverse();
            output[i] = BinaryToHex(Transformation());
        }
    }

    public String[] getOutput() {
//        System.out.println(Arrays.toString(output));
        return output;
    }

    private String BinaryToHex(int[] a) {
        String binNum = "";
        for (int i = 0; i < a.length; i++) {
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

    private int[] HexToBinary(String s) {
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

    public int[] Transformation() {
        int[] B = new int[8]; // (b0, b1, b2, b3, b4, b5, b6, b7)
        for (int i = 0; i < B.length; i++) {
            B[i] = multiplicativeInverse[i];
        }
        int[] c = {1, 1, 0, 0, 0, 1, 1, 0}; // (c0c1c2c3c4c5c6c7) = (11000110) = 36
        int[] bPrime = new int[8];
        for (int i = 0; i < bPrime.length; i++) {
            bPrime[i] = B[i] ^ B[(i + 4) % 8] ^ B[(i + 5) % 8] ^ B[(i + 6) % 8] ^ B[(i + 7) % 8] ^ c[i];
        }
//        System.out.println(Arrays.toString(bPrime));
        return bPrime;
    }

    public void MultiplicativeInverse() {
        System.out.println(Arrays.toString(multiplicativeInverse));
    }

    public void ExtendedEuclidAlgorithm(int[] b) {
        /*
        EXTENDED EUCLID(m, b)
            1. (A1, A2, A3):=(1, 0, m); (B1, B2, B3):=(0, 1, b);
            2. if B3 = 0 return A3 = gcd(m, b); no inverse      // always exists inverse cuz using irreducible m(x)
            3. if B3 = 1 return B3 = gcd(m, b); B2 = b-1 mod m
            4. Q = floor(A3 / B3)
            5. (T1, T2, T3) := (A1-QB1, A2-QB2, A3-QB3)
            6. (A1, A2, A3) := (B1, B2, B3)
            7. (B1, B2, B3) := (T1, T2, T3)
            8. goto 2
         */
        int[] A1 = {1, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] A2 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] A3 = new int[9];

        int[] B1 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] B2 = {1, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] B3 = new int[9];

        int[] T1 = new int[9];
        int[] T2 = new int[9];

        System.arraycopy(mX, 0, A3, 0, mX.length);
        System.arraycopy(b, 0, B3, 0, b.length);
        while (Degree(B3) != 0) {
            Clear(Q);
            Clear(rem);
            Clear(multiplicativeInverse);
            Division(A3, B3);
            int[] QB1 = Multiplication(Q, B1);
            int[] QB2 = Multiplication(Q, B2);

            for (int i = 0; i < QB1.length; i++) {
                T1[i] = A1[i] - QB1[i];
                T1[i] = abs(T1[i] % 2);
            }
            for (int i = 0; i < QB2.length; i++) {
                T2[i] = A2[i] - QB2[i];
                T2[i] = abs(T2[i] % 2);
            }
            System.arraycopy(B1, 0, A1, 0, B1.length);
            System.arraycopy(B2, 0, A2, 0, B2.length);
            System.arraycopy(B3, 0, A3, 0, B3.length);
            System.arraycopy(T1, 0, B1, 0, T1.length);
            System.arraycopy(T2, 0, B2, 0, T2.length);
            System.arraycopy(rem, 0, B3, 0, rem.length);

//            System.out.println("Q   : " + Arrays.toString(Q));
//            System.out.println("A1  : " + Arrays.toString(A1));
//            System.out.println("A2  : " + Arrays.toString(A2));
//            System.out.println("A3  : " + Arrays.toString(A3));
//            System.out.println("B1  : " + Arrays.toString(B1));
//            System.out.println("B2  : " + Arrays.toString(B2));
//            System.out.println("B3  : " + Arrays.toString(B3));
//            System.out.println("===============================");
        }
        System.arraycopy(B2, 0, multiplicativeInverse, 0, B2.length);
    }

    private void Clear(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = 0;
        }
    }

    private int[] Multiplication(int[] a, int[] b) {
        int[] M = new int[9];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == 0 || b[j] == 0) {
                    continue;
                }
                M[i + j] += a[i] * b[j];
            }
        }
//        System.out.println("Q  : " + Arrays.toString(a));
//        System.out.println("B  : " + Arrays.toString(b));
//        System.out.println("QB : " + Arrays.toString(M));
        return M;
    }

    private void Division(int[] a, int[] b) {
        if (Degree(b) == -1) {
            System.out.println("0 has no multiplicative inverse");
            return;
        }
//        System.out.println("before : " + Arrays.toString(a));
//        System.out.println("before : " + Arrays.toString(b));
//        System.out.println(Degree(a) + "  " +  Degree(b));
        int diffDeg = Degree(a) - Degree(b);
        if (diffDeg < 0) {
            for (int i = 0; i < rem.length; i++) {
                rem[i] = a[i];
            }
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
//        System.out.println("after : " + Arrays.toString(tempA));
//        System.out.println("after : " + Arrays.toString(tempB));
        Division(tempA, b);
    }

    private int Degree(int[] a) {
        int deg = -1;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] == 1) {
                deg = i;
                break;
            }
        }
        return deg;
    }

    public static void main(String[] args) {
//        S_Box s1 = new S_Box();
////                 0  1  2  3  4  5  6  7  8
//        int[] b = {0, 0, 0, 1, 0, 0, 0, 1, 0};
//        s1.ExtendedEuclidAlgorithm(b);
////        System.err.println(" 0  1  2  3  4  5  6  7");
//        s1.MultiplicativeInverse();
//        s1.Transformation();
    }
}
