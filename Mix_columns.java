package aes;

import java.util.Arrays;

public class Mix_columns extends coll {

    private String[] output = new String[16];
    private String[] mixColTransformation = {
        "02", "03", "01", "01",
        "01", "02", "03", "01",
        "01", "01", "02", "03",
        "03", "01", "01", "02"
    };

    public Mix_columns(String[] input) {
        int[] out = new int[8];
        for (int i = 0; i < input.length; i++) {
            Clear(out);
            for (int j = (i / 4) * 4, k = i % 4, cnt = 0; cnt < 4; j++, k += 4, cnt++) {
                /*
                output[0]  = mi[0]*in[0]  + mi[1]*in[4]  + mi[2]*in[8]   + mi[3]*in[12]
                output[1]  = mi[0]*in[1]  + mi[1]*in[5]  + mi[2]*in[9]   + mi[3]*in[13]
                output[2]  = mi[0]*in[2]  + mi[1]*in[6]  + mi[2]*in[10]  + mi[3]*in[14]
                output[3]  = mi[0]*in[3]  + mi[1]*in[7]  + mi[2]*in[11]  + mi[3]*in[15]
                
                output[4]  = mi[4]*in[0]  + mi[5]*in[4]  + mi[6]*in[8]   + mi[7]*in[12]
                output[5]  = mi[4]*in[1]  + mi[5]*in[5]  + mi[6]*in[9]   + mi[7]*in[13]
                output[6]  = mi[4]*in[2]  + mi[5]*in[6]  + mi[6]*in[10]  + mi[7]*in[14]
                output[7]  = mi[4]*in[3]  + mi[5]*in[7]  + mi[6]*in[11]  + mi[7]*in[15]
                
                output[8]  = mi[8]*in[0]  + mi[9]*in[4]  + mi[10]*in[8]  + mi[11]*in[12]
                output[9]  = mi[8]*in[1]  + mi[9]*in[5]  + mi[10]*in[9]  + mi[11]*in[13]
                output[10] = mi[8]*in[2]  + mi[9]*in[6]  + mi[10]*in[10] + mi[11]*in[14]
                output[11] = mi[8]*in[3]  + mi[9]*in[7]  + mi[10]*in[11] + mi[11]*in[15]
                
                output[12] = mi[12]*in[0] + mi[13]*in[4] + mi[14]*in[8]  + mi[15]*in[12]
                output[13] = mi[12]*in[1] + mi[13]*in[5] + mi[14]*in[9]  + mi[15]*in[13]
                output[14] = mi[12]*in[2] + mi[13]*in[6] + mi[14]*in[10] + mi[15]*in[14]
                output[15] = mi[12]*in[3] + mi[13]*in[7] + mi[14]*in[11] + mi[15]*in[15]
                 */
                
//                System.out.println(i + " " + j + " " + k);
//                System.out.println(Arrays.toString(rem));
                Multiplication(HexToBinary(mixColTransformation[j]), HexToBinary(input[k]));
                out = XOR(out, rem);
            }
            for (int q = 0; q < out.length; q++) {
                out[q] = Math.abs(out[q] % 2);
            }
            output[i] = BinaryToHex(out);
        }
    }

    private int[] XOR(int[] a, int[] b) {
        int[] xor = new int[8];
        for (int i = 0; i < xor.length; i++) {
            xor[i] = a[i] ^ b[i];
        }
        return xor;
    }

    public String[] getOutput() {
        return output;
    }

}
