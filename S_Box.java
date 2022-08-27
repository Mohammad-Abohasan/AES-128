package aes;

import java.util.Scanner;
import java.util.Arrays;

/**
 *
 * @author Mohammad_AboHasan
 */
public class S_Box extends coll {

    private int[] multiplicativeInverse = new int[9];
    private String[][] S_boxTable = new String[16][16];

    public S_Box() {
        GenerateS_boxTable();
    }

    public S_Box(String[] input) {
        this();
        for (int i = 0; i < input.length; i++) {
            char h1 = input[i].toUpperCase().charAt(0), h2 = input[i].toUpperCase().charAt(1);
            output[i] = S_boxTable[h1 >= '0' && h1 <= '9' ? h1 - '0' : h1 - 'A' + 10][h2 >= '0' && h2 <= '9' ? h2 - '0' : h2 - 'A' + 10];
        }
    }

    private void GenerateS_boxTable() {
        String L, R;
        for (int i = 0; i <= 15; i++) {
            for (int j = 0; j <= 15; j++) {
                L = "" + (char) (i >= 0 && i <= 9 ? i + '0' : (i - 10) + 'A') + "";
                R = "" + (char) (j >= 0 && j <= 9 ? j + '0' : (j - 10) + 'A') + "";
                S_boxTable[i][j] = Generator(L + R);
            }
        }
    }

    private String Generator(String input) {
        ExtendedEuclidAlgorithm(HexToBinary(input));
        return BinaryToHex(Transformation());
    }

    private void ExtendedEuclidAlgorithm(int[] b) {
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
        while (Degree(B3) > 0) {
            Clear(Q);
            Division(A3, B3);
            System.arraycopy(B3, 0, A3, 0, B3.length);
            System.arraycopy(rem, 0, B3, 0, rem.length);

            Multiplication(Q, B1);
            int[] QB1 = new int[9];
            System.arraycopy(rem, 0, QB1, 0, rem.length);
            Multiplication(Q, B2);
            int[] QB2 = new int[9];
            System.arraycopy(rem, 0, QB2, 0, rem.length);

            for (int i = 0; i < QB1.length; i++) {
                T1[i] = A1[i] - QB1[i];
                T1[i] = Math.abs(T1[i] % 2);
            }
            for (int i = 0; i < QB2.length; i++) {
                T2[i] = A2[i] - QB2[i];
                T2[i] = Math.abs(T2[i] % 2);
            }
            System.arraycopy(B1, 0, A1, 0, B1.length);
            System.arraycopy(B2, 0, A2, 0, B2.length);
            System.arraycopy(T1, 0, B1, 0, T1.length);
            System.arraycopy(T2, 0, B2, 0, T2.length);
        }
        Clear(multiplicativeInverse);
        if (Degree(B3) == 0) {
            System.arraycopy(B2, 0, multiplicativeInverse, 0, B2.length);
        }
    }

    private int[] Transformation() {
        int[] B = new int[8]; // (b0, b1, b2, b3, b4, b5, b6, b7)
        for (int i = 0; i < B.length; i++) {
            B[i] = multiplicativeInverse[i];
        }
        int[] c = {1, 1, 0, 0, 0, 1, 1, 0}; // (c0c1c2c3c4c5c6c7) = (11000110) = 36
        int[] bPrime = new int[8];
        for (int i = 0; i < bPrime.length; i++) {
            bPrime[i] = B[i] ^ B[(i + 4) % 8] ^ B[(i + 5) % 8] ^ B[(i + 6) % 8] ^ B[(i + 7) % 8] ^ c[i];
        }
        return bPrime;
    }

    public void MultiplicativeInverse() {
        System.out.println(Arrays.toString(multiplicativeInverse));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] inputS_Box = new String[16];
        for (int i = 0; i < inputS_Box.length; i++) {
            inputS_Box[i] = sc.next();
        }
        S_Box s = new S_Box(inputS_Box);
        System.out.println(Arrays.deepToString(s.S_boxTable));
        System.out.println(Arrays.toString(s.getOutput()));
    }

}
