import java.nio.charset.StandardCharsets;

import java.nio.file.Files;

import java.nio.file.Path;



/**

 * Code Breakers — Iteration 3 starter.

 * Copy your six completed Iteration 2 method bodies into the matching methods.

 * Implement breakCode. Keep main unchanged.

 * Compile: javac CodeBreakers3.java

 * Run: java CodeBreakers3 ciphertext.txt

 */

public class CodeBreakers3 {

public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";



public static boolean isLetter(String ch) {
    boolean outcome = false;
        if (ch.length() == 1 && ALPHABET.indexOf(ch) >= 0) {
            outcome = true;
        }

        return outcome;

}
     



public static String shiftLetter(String ch, int shift) {
    String complete = ch;
        int pos = ALPHABET.indexOf(ch);
        int newPos = pos + shift;
        int finalPos = newPos % 26;
        if (!isLetter(ch))
        {
            complete = ch;

        }

        else
        {
            if (finalPos > 25)
            {
                finalPos = finalPos - 26;
            }
            else if (finalPos < 0) {
                finalPos = finalPos + 26;
            }
        
        complete = ALPHABET.substring(finalPos, finalPos + 1);
        }

        return complete;


}



public static String encrypt(String message, int shift) {
     String done = "";
        for (int i = 0; i < message.length(); i++) {
            String ch = message.substring(i, i + 1);
            done = done + shiftLetter(ch, shift);
        }

        return done;


}



public static String decrypt(String message, int shift) {
    int newShift = shift % 26;

        return encrypt(message, -newShift);

}



public static String reverse(String text) {
     String done = "";
        for (int i = 0; i < text.length(); i++) {
            int location = text.length() - 1 - i;
            String finished = text.substring(location, location + 1);
            done = done + finished;

        }

        return done;

}



public static String repeatingThreeKeyShift(String text, int key) {

    if (key < -999 || key > 999) {
            throw new IllegalArgumentException("K: key must be -999 through 999.");
        }
        
        int hundreds = key / 100;
        int tens = (key / 10) % 10;
        int ones = key % 10;
            
        
        String done = "";
        int count = 0;

        for (int i = 0; i < text.length(); i++)
        {   
                
            String word = text.substring(i, i + 1);
                
                
            if (!isLetter(word))
            {
                done = done + word;
            }
            else 
            {
                int newSpot = ALPHABET.indexOf(word);
                if (count % 3 == 0)
                {
                    newSpot = newSpot + hundreds;
                        
                        





                }
                else if (count % 3 == 1)
                {
                    newSpot = newSpot + tens;
                        
                }
                else if (count % 3 == 2)
                {
                    newSpot = newSpot + ones;
                        
                }
                count += 1;
                if (newSpot > 25)
                {
                    newSpot = newSpot - 26;
                }
                else if (newSpot < 0) {
                    newSpot = newSpot + 26;
                }
                done =  done + ALPHABET.substring(newSpot, newSpot + 1);

                    


                

            }
        }
                

                






            

            

        
        

        

        
            
            
        
       
            
        

        return done;

}



/*
    Evaluates the success of the key by checking
    features of the recovered text to see if its likely english.

    examples: How many of cetain letters, words, etc.
                are there any dead giveaways that its NOT english? 

    returns close to 1 if english, close to 0 if not. 
*/
public static double score(String text){
    int points = 0;
    for (int i = 0; i < text.length(); i++)
    {
        String ch = text.substring(i, i +  1);
        if ("AEIOUSTR".indexOf(ch) >= 0)
        {
            points += 1;


        }
        if (text.indexOf(" THE ", i) == i)
        {
            points += 5;
        }
        if (text.indexOf(" AND ", i) == i)
        {
            points += 5;
        }
        if (text.indexOf(" BECAUSE ", i) == i)
        {
            points += 10;
        } 
        if (text.indexOf( " IF ", i) == i)
        {
            points += 3;
        }
        if (text.indexOf(" YOU ", i ) == i)
        {
            points += 5;
        }
        if ("QZX".indexOf(ch) >= 0)
        {
            points -= 1;
        }


    }
    return points;
}

 /** Returns the recovered plaintext. Precondition: ciphertext is not null. */

public static String breakCode(String ciphertext) {
    double top = -100;
    String bestWords = ciphertext;
    String bestTotal = "";
    for (int shift = 0; shift < 26; shift++)
    {
        for (int key = -999; key < 1000; key++)
        {
            String next = ciphertext;
            next = decrypt(next, shift);
            next = repeatingThreeKeyShift(next, -key);
            next = reverse(next);
            double check = score(next);
            if (check  > top)
            {
                top = check;
                bestWords = next;
                bestTotal = "C:" + shift + ", K:" + key +  " R";
            }





        }
    }


    
 // TODO: Find the transformation chain and recover the message.
System.out.println(bestTotal);
return bestWords;

}



 /** Reads the named file and prints the value returned by breakCode. */

public static void main(String[] args) throws java.io.IOException {

if (args.length != 1) {

System.out.println("Usage: java CodeBreakers3 ciphertext.txt");

return;

}

String ciphertext = Files.readString(Path.of(args[0]), StandardCharsets.UTF_8);

System.out.println(breakCode(ciphertext));

}

}