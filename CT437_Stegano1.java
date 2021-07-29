/**
 * Skeleton code for Steganography assignment.
 *
 * @author Michael Schukat
 * @version 1.0
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class CT437_Stegano1
{
    /**
     * Constructor for objects of class Stegano1
     */
    public CT437_Stegano1()
    {
    }

    public static void main(String[] args) {
        String arg1, arg2, arg3, arg4;
        Boolean err = false;

        if (args != null && args.length > 1) { // Check for minimum number of arguments
            arg1 = args[0];
            arg2 = args[1];

            if (arg2.equals("") ){
                err = true;
            }
            else if ((arg1.equals("A")) && (args.length > 2)){
                // Get other arguments
                arg3 = args[2];
                arg4 = args[3];
                if (arg3.equals("") || arg4.equals("")) {
                    err = true;
                }
                else {
                    // Hide bitstring
                    hide(arg2, arg3, arg4);
                }
            }
            else if (arg1.equals("E")){
                // Extract bitstring from text
                retrieve(arg2);
            }
            else {
                err = true;
            }
        }
        else {
            err = true;
        }

        if (err == true) {
            System.out.println();
            System.out.println("    Use: CT437_Stegano1 <A:E><Input File><OutputFile><Bitstring>");
            System.out.println("Example: CT437_Stegano1 A inp.txt out.txt 0010101");
            System.out.println("Example: CT437_Stegano1 E inp.txt");

        }

    }
    static void hide(String inpFile, String outFile, String binString) {
        //
        BufferedReader reader;
        BufferedWriter writer;

        try {
            reader = new BufferedReader(new FileReader(inpFile));
            writer = new BufferedWriter(new FileWriter(outFile));
            String line = reader.readLine();
            int p=0;// int p is initialised as a counter for the length of the bitstring i.e how many lines to add white space to
            while (line != null) {
                while (line != null && p < binString.length()) { // iterate till p is less than the length of the bit string. This loop is used for adding whitespaces to the number of lines corresponding to the length of the bitstring.
                    for (int i = 0; i < binString.length(); i++) { // for loop used for looping through the bitstring using i as an index.
                        char bit = binString.charAt(i);
                        if (bit == '0') { // if the bit is 0 then a single white space is added
                            line = line + " ";
                            System.out.println(line);
                        } else if (bit == '1') { // if the bit is 1 then a double whitespace is added
                            line = line + "  ";
                            System.out.println(line);
                        }
                        // Store amended line in output file
                        writer.write(line);
                        writer.newLine();
                        // read next line
                        line = reader.readLine();
                        if (line == null){ // check to see if next line is null, if so close the reader and writer
                            reader.close();
                            writer.close();
                            break;
                        }
                        else
                        p++;
                    }
                }// checking the rest of the lines of the file if they are null after the whitespaces are added
                if (line == null){
                    reader.close();
                    writer.close();
                    break;}
                writer.write(line);
                writer.newLine();
                // read next line
                line = reader.readLine();

            }
                reader.close();
                writer.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

            static void retrieve (String inpFile){
                BufferedReader reader;

                try {
                    reader = new BufferedReader(new FileReader(inpFile));
                    String line = reader.readLine();
                    while (line != null) {
                        // Your code starts here
                        while (line.length() != 0) { // // while loop to check and proceed if the length of the line is not 0
                        char last = line.charAt(line.length() - 1); // storing the last character of the line for checking
                        char secondLast = line.charAt(line.length() - 2); // storing the secondLast character of the line for checking.
// both last and second last characters are checked as only one ' ' character can be checked at a time.
                            // if last is a white space and second last isnt we can conclude the bit is 0
                            if (last == ' ' && secondLast != ' ') {
                                line = "0";
                                System.out.println(line);
// check if both the last and second are whitespaces. If they are, then because we have two whitespce values the bit is 1.
                            } else if (last == ' '&& secondLast == ' ') {
                                line = "1";
                                System.out.println(line);
                            }
                            //  System.out.println(line);
                            // read next line
                            line = reader.readLine();
                            if (line == null) { // check if next line is null. If so, close the reader
                                reader.close();
                                break;
                            }
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
