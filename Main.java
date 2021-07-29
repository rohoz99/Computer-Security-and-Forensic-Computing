package com.company;
import java.util.Random;
public class Main {

    public static void main(String[] args) {
        // write your code here
        int res = 0;


        if (args != null && args.length > 0) { // Check for <input> value
            res = hashF1(args[0]); // call hash function with <input>
            if (res < 0) { // Error
                System.out.println("Error: <input> must be 1 to 64 characters long.");
            }
            else {
                System.out.println("input = " + args[0] + " : Hash = " + res);
                System.out.println("Start searching for collisions");

                // Your code starts here!

                int numCollisions=0;
                int  newVal= hashF1("Bamb0"); // find hashVal of Bamb0
                System.out.println(newVal);
                //loop 100000000 times and ouput the collisions
                System.out.println("\nCollisions - Random String - Hash Values ");
                System.out.println("-----------------------------------");
                for (int i=0; i<100000000;i++) {

                    String randomString = ranStringGenerator(); // initialise a random string
                    int hashedRandomString = hashF1(randomString); // hash this random string

                    if (hashedRandomString == newVal) {
                        numCollisions++;  //store collisons
                        //output the collisions
                        System.out.println(""+numCollisions + " - "+ randomString + " - " + hashedRandomString);
                    }

                }
                System.out.println("Number of Collisions Found :" + numCollisions);// print the total number of collisions found

            }
        }
        else { // No <input>
            System.out.println("Use: CT437_HashFunction1 <Input>");
        }

    }
    // Random String generator
    private static String ranStringGenerator() {

        String randomString = "";
        int stringLen = (int) (1 + Math.floor(Math.random() * 64)); // length of string is 1 to 64

        // Iterating for the length of the string
        for (int i = 0; i < stringLen; i++) {
            int randomNum = (int) Math.floor(Math.random() * 62); // 62 possible characters. This serves as an index
            String charSet = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); // set of characters to be used for the random String
            randomString += (charSet.substring(randomNum, randomNum + 1)); // Add the random character to the String
        }
        return randomString;
    }


    private static int hashF1(String s){
        int ret = -1, i;
        int[] hashA = new int[]{1, 1, 1, 1};

        String filler, sIn;

        filler = new String("ABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGHABCDEFGH");

        if ((s.length() > 64) || (s.length() < 1)) { // String does not have required length
            ret = -1;
        }
        else {
            sIn = s + filler; // Add characters, now have "<input>HABCDEF..."
            sIn = sIn.substring(0, 64); // // Limit string to first 64 characters
            // System.out.println(sIn); // FYI
            for (i = 0; i < sIn.length(); i++){
                char byPos = sIn.charAt(i); // get i'th character
                // increase the values by which the hash positions are multiplied by
                hashA[0] += (byPos * 7900); // Note: A += B means A = A + B
                hashA[1] += (byPos * 6800);
                hashA[2] += (byPos * 5600);
                hashA[3] += (byPos * 9999);
            }

            // increase the value of the modulus operator
            hashA[0] %= 1087;  // % is the modulus operation, i.e. division with rest
            hashA[1] %= 1087;
            hashA[2] %= 1087;
            hashA[3] %= 1087;

            ret = hashA[0] + (hashA[1] * 256) + (hashA[2] * 256 * 256) + (hashA[3] * 256 * 256 * 256);
            if (ret < 0) ret *= -1;
        }
        return ret;
    }



}








