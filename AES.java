package aes;

import java.util.Scanner;

/**
 *
 * @author Mohammad_AboHasan
 */
public class AES {

    public static void main(String[] args) {

/* ============================================================================================================================= */
        // checker S-Box
        Scanner sc = new Scanner(System.in);
        String[] inputS_Box = new String[16];
        for (int i = 0; i < inputS_Box.length; i++) {
            inputS_Box[i] = sc.next();
        }
        S_Box sBox = new S_Box(inputS_Box);
        String[] outputS_Box = sBox.getOutput();
        System.out.println("==============================");
        System.out.println("\tS-Box");
        System.out.println("==============================");
        System.out.println("Input :");
        for (int i = 0; i < inputS_Box.length; i++) {
            System.out.print(inputS_Box[i] + (i != inputS_Box.length - 1 ? ", " : "") + ((i + 1) % 4 == 0 ? "\n" : ""));
        }
        System.out.println("===========");
        System.out.println("Output :");
        for (int i = 0; i < outputS_Box.length; i++) {
            System.out.print(outputS_Box[i] + (i != outputS_Box.length - 1 ? ", " : "") + ((i + 1) % 4 == 0 ? "\n" : ""));
        }
        // end checker S-Box
/* ============================================================================================================================= */
        // checker Shift rows
        Shift_rows shiftRows = new Shift_rows(outputS_Box);
        String[] output_shiftRows = shiftRows.getOutput();
        System.out.println("==================================");
        System.out.println("\tShift rows");
        System.out.println("==================================");
        System.out.println("Input :");
        for (int i = 0; i < outputS_Box.length; i++) {
            System.out.print(outputS_Box[i] + (i != outputS_Box.length - 1 ? ", " : "") + ((i + 1) % 4 == 0 ? "\n" : ""));
        }
        System.out.println("===========");
        System.out.println("Output :");
        for (int i = 0; i < output_shiftRows.length; i++) {
            System.out.print(output_shiftRows[i] + (i != output_shiftRows.length - 1 ? ", " : "") + ((i + 1) % 4 == 0 ? "\n" : ""));
        }
        // end checker Shift rows
/* ============================================================================================================================= */
        // checker Mix columns
        Mix_columns mixColumns = new Mix_columns(output_shiftRows);
        String[] output_mixColumns = mixColumns.getOutput();
        System.out.println("==================================");
        System.out.println("\tMix columns");
        System.out.println("==================================");
        System.out.println("Input :");
        for (int i = 0; i < output_shiftRows.length; i++) {
            System.out.print(output_shiftRows[i] + (i != output_shiftRows.length - 1 ? ", " : "") + ((i + 1) % 4 == 0 ? "\n" : ""));
        }
        System.out.println("===========");
        System.out.println("Output :");
        for (int i = 0; i < output_mixColumns.length; i++) {
            System.out.print(output_mixColumns[i] + (i != output_mixColumns.length - 1 ? ", " : "") + ((i + 1) % 4 == 0 ? "\n" : ""));
        }
        // end checker Mix columns
/* ============================================================================================================================= */

    }

}
