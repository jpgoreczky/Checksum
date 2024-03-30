/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Josie Goreczky
| Language: Java
|
| To Compile: javac pa02.java
|
| To Execute: java -> java pa02 inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Spring 2024
| Instructor: McAlpin
| Due Date: 03/31/2024
|
+=============================================================================*/

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class pa02 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java pa02 input_file checksum_size");
            System.exit(1);
        }

        Path filePath = Paths.get(args[0]);

        // Check if the file exists
        if (!Files.exists(filePath)) {
            System.err.print("\nFile " + args[0] + " does not exist");
            System.exit(1);
        }

        // Read in the checksum size and see if it's a valid integer
        int checkSumSize;
        try {
            checkSumSize = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.print("\nValid checksum sizes are 8, 16, or 32\n");
            System.exit(1);
            return;
        }

        // If checksum sizes are not 8, 16, or 32 bits
        if (checkSumSize != 8 && checkSumSize != 16 && checkSumSize != 32) {
            System.err.print("\nValid checksum sizes are 8, 16, or 32\n");
            System.exit(1);
        }

        // Read the input file
        String input = null;
        try {
            input = new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Calculate the checksum and print the output
        byte[] adjustedBytes = getAdjustedByteArray(input, checkSumSize);
        int checksum = checksum(adjustedBytes, checkSumSize);
        System.out.printf("\n%s\n%2d bit checksum is %8x for all %4d chars", formattedStringOutput(getAdjustedString(input, checkSumSize)), checkSumSize, checksum, adjustedBytes.length);

    }

    // Get the adjusted byte array based on the checksum size
    private static byte[] getAdjustedByteArray(String in, int bit) {
        int originalSize = in.getBytes().length, padding = getPadding(originalSize, bit), newSize;

        newSize = originalSize + padding;
        byte[] temp = new byte[newSize];

        for (int i = 0; i < originalSize; i++) {
            temp[i] = (byte) in.charAt(i);
        }

        if (padding > 0) {
            for (int j = originalSize; j < newSize; j++) {
                temp[j] = 88;
            }
        }
        return temp;
    }

    // Calculate the checksum based on the bit size
    private static int checksum(byte[] bytes, int numBits) {
        switch (numBits) {
            case 8:
                return checksum8(bytes);
            case 16:
                return checksum16(bytes);
            case 32:
                return checksum32(bytes);
            default:
                System.out.println("\nValid checksum sizes are 8, 16, or 32");
                return 0; // Return if none of the valid checksum sizes are provided
        }
    }

    // Calculate 8 bit checksum
    private static int checksum8(byte[] data) {
        int check = 0;

        for (byte b : data) {
            check += b;
        }

        return check & 0xFF;
    }

    // Calculate 16 bit checksum
    private static int checksum16(byte[] data) {
        int check = 0;

        for (int i = 0; i <= data.length - 2; i += 2) {
            check += ((data[i] << 8) | (data[i + 1] & 0xFF));
        }

        return check & 0xFFFF;
    }

    // Calculate 32 bit checksum
    private static int checksum32(byte[] data) {
        int check = 0;

        for (int i = 0; i < data.length; i += 4) {
            check += ((data[i] << 24) | (data[i + 1] << 16) | (data[i + 2] << 8) | (data[i + 3])) & 0xffffffffL;
        }

        return check;
    }

    // Format the output string to 80 characters per line
    private static String formattedStringOutput(String output) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < output.length(); i++) {
            if (i > 0 && i % 80 == 0) {
                res.append("\n");
            }
            res.append(output.charAt(i));
        }
        return res.toString();
    }

    // Pad the input string with 'X' to match the checksum size
    private static String getAdjustedString(String in, int bit) {
        int originalSize = in.getBytes().length, padding = getPadding(originalSize, bit), newSize;
        StringBuilder builder = new StringBuilder();
        newSize = originalSize + padding;

        for (int i = 0; i < originalSize; i++) {
            builder.append(in.charAt(i));
        }

        if (padding > 0) {
            for (int j = originalSize; j < newSize; j++) {
                builder.append("X");
            }
        }

        return builder.toString();
    }

    // Calculate the padding required to match the checksum size
    private static int getPadding(int lengthOriginal, int bit) {
        int length = lengthOriginal;
        int modulus = bit == 32 ? 4 : 2;
        int result = 0;
        while (length % modulus != 0) {
            length = length + 1;
            result++;
        }
        return bit > 8 ? result : 0;
    }
}

/*=============================================================================
| I Josie Goreczky (618900) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+============================================================================*/
