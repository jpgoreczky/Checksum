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

import java.util.*;
import java.io.*;

public class pa02 {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java pa02 input_file checksum_size");
            return;
        }

        // If checksum sizes are not 8, 16, or 32 bits
        if (args[1] != "8" && args[1] != "16" && args[1] != "32") {
            System.err.println("Valid checksum sizes are 8, 16, or 32\n");
            return;
        }

        String inputFile = args[0];
        int checksumSize = Integer.parseInt(args[1]); // checksum size in bits

        // Read in input file
        String inputText = readInputFile(inputFile, checksumSize);

        // Print the input file
        printInputFile(inputText);

        // Calculate the checksum
        calculateChecksum(inputText, checksumSize);
    }

    public static String readInputFile(String inputFile, int checksumSize) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        Scanner textScanner = new Scanner(new File(inputFile));

        // Read the input file
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            // Read each line of the file
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        text = new StringBuilder(text.toString());

        // Pad with 'X' to match the checksum size for the checksum calculation
        while (text.length() % (checksumSize / 8) != 0) {
            text.append('X');
        }

        textScanner.close();

        return text.toString();
    }

    // Calculate 8 bit checksum
    // Remember that the checksum is a running total with no overflow.
    // Resolve the calculations and padding for both 16 and 32 bit checksums.
    public static void calculateChecksum(String inputText, int checksumSize) {
        int checksum = 0;
        // Calculate the number of characters in the input file
        int characterCnt = inputText.length();

        // Calculate the checksum
        for (int i = 0; i < inputText.length(); i++) {
            checksum += inputText.charAt(i);
        }

        System.out.printf("%2d bit checksum is %8x for all %4d chars\n", checksumSize, checksum, characterCnt);
    }

    public static void printInputFile(String inputText) {
        // Print the input file 80 characters per line
        for (int i = 0; i < inputText.length(); i++) {
            if (i > 0 && i % 80 == 0) {
                System.out.println();
            }
            System.out.print(inputText.charAt(i));
        }
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
