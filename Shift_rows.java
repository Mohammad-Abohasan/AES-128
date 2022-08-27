package aes;

public class Shift_rows {

    private String[] output = new String[16];

    public Shift_rows(String[] input) {
        String[] row1 = new String[4], row2 = new String[4], row3 = new String[4];
        System.arraycopy(input, 4 * 0, output, 0, 4);
        
        System.arraycopy(input, 4 * 1, row1, 0, 4);
        System.arraycopy(input, 4 * 2, row2, 0, 4);
        System.arraycopy(input, 4 * 3, row3, 0, 4);
        rotateToLeft(row1, 1);
        rotateToLeft(row2, 2);
        rotateToLeft(row3, 3);
        System.arraycopy(row1, 0, output, 4 * 1, 4);
        System.arraycopy(row2, 0, output, 4 * 2, 4);
        System.arraycopy(row3, 0, output, 4 * 3, 4);
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
