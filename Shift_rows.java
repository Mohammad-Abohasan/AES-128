package aes;


public class Shift_rows {

    private String[] output = new String[16];

    public Shift_rows(String[] input) {
        String[] row1 = new String[4], row2 = new String[4], row3 = new String[4];
        for (int i = 0; i < 4; i++) {
            output[i] = input[i];
        }
        for (int i = 0; i < 4; i++) {
            row1[i] = input[i + 4];
        }
        for (int i = 0; i < 4; i++) {
            row2[i] = input[i + 4 * 2];
        }
        for (int i = 0; i < 4; i++) {
            row3[i] = input[i + 4 * 3];
        }
        rotateToLeft(row1, 1);
        rotateToLeft(row2, 2);
        rotateToLeft(row3, 3);
        for (int i = 0; i < 4; i++) {
            output[i + 4] = row1[i];
        }
        for (int i = 0; i < 4; i++) {
            output[i + 4 * 2] = row2[i];
        }
        for (int i = 0; i < 4; i++) {
            output[i + 4 * 3] = row3[i];
        }
    }

    private void rotateToLeft(String[] a, int n) {
        String temp;
        while (n-- > 0) {
            temp = a[0];
            for (int i = 0; i < a.length - 1; i++) {
                a[i] = a[i + 1];
            }
            a[a.length - 1] = temp;
        }
    }

    public String[] getOutput() {
        return output;
    }

}
