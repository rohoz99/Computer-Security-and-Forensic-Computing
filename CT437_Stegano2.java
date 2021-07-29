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
import java.util.Base64;
import java.util.Base64.Encoder;

public class CT437_Stegano2
{
  /**
   * Constructor for objects of class Stegano1
   */
  public CT437_Stegano2()
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
      int p=0; // int p is initialised as a counter for the length of the bitstring i.e how many lines to add white space to
      while (line != null) { // While loop which checks if the line is null and proceeds
        if(binString.length() %2 != 0) { // Bitstring length modulus 2 is calculated to determine if the bit string has an even length. If not, a 0 is added for padding
          binString = binString +"0";
        }
// Nested while loop which used for adding white spaces. Since there will always be an even bit string length value and there are two bits added everytime. The number of lines to which whitespaces is binString.length()/2
        while (line != null && p < binString.length()/2) {
          for (int i = 0; i < binString.length(); i+=2) { // i is iterated by a value of 2 every time in order to point the index to the next line without a white space
            char bit = binString.charAt(i);
            char bit2 = binString.charAt(i+1); // bit 2 is the pairing bit value
            if (bit == '0' && bit2 == '0' ) { // checking for combinations of bits and adding whitespaces corresponding to the bit combo
              line = line + " " + " ";
              System.out.println(line);
            } else if (bit == '1' && bit2 == '1') {
              line = line + "  " + "  ";
              System.out.println(line);
            }else if (bit == '1' && bit2 == '0') {
              line = line + "\t "; // the tab whitespace value is used in order to differentiate between 10 and 01
              System.out.println(line);
            }else if (bit == '0' && bit2 == '1') {
              line = line + " \t"; //the tab whitespace value is used in order to differentiate between 01 and 10
              System.out.println(line);
            }

            // Store amended line in output file
            writer.write(line);
            writer.newLine();
            // read next line
            line = reader.readLine();
            if (line == null){ // check if next line is null. If so, then close reader and writer
              reader.close();
              writer.close();
              break;
            }
            else
              p++;// if the next line is not null then iterate p
          }
        } // checks again once the nested while loop is exited. This is used for reading and writing the rest of the non bit encoded lines.
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
        while (line.length() != 0) { // while loop to check and proceed if the length of the line is not 0
          char last = line.charAt(line.length() - 1); // finding and storing the last four characters of the line
          char secondLast = line.charAt(line.length() - 2);
          char thirdLast = line.charAt(line.length() - 3);
          char fourthLast = line.charAt(line.length() - 4);

          // check to determine if the last and second last characters are white spaces. If they are, then we can conclude the combination of bits are 00. This is because they only have one whitespace value each.
          if (last == ' ' && secondLast == ' ' && thirdLast != ' ' && fourthLast != ' ') {
            line = "0" + "\n0";
            System.out.println(line);
 // Must check for "tab character" and a space before for 10
          } else if (line.contains("\t ")) {
            line = "1"+ "\n0";
            System.out.println(line);
          }
          // Must check for "tab character" and a space before for 01
          else if (line.contains(" \t")) {
            line = "0" + "\n1";
            System.out.println(line);
          }
          // check to determine all last four values are white spaces. If that is the case then the bit pair is 11. This is because a bit value of 1 has two white spaces.
          else if (last == ' ' && secondLast == ' ' && thirdLast == ' ' && fourthLast == ' ') {
            line = "1" + "\n1";
            System.out.println(line);
          }
          //  System.out.println(line);
          // read next line
          line = reader.readLine();
          if (line == null) { // if next line is null then close the reader
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
