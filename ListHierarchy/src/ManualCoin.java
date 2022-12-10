import java.util.Random;
import java.util.Scanner;

/**
 * ManualCoin is a Coin class that lets the user determine the coin flip value
 * as the program is running.
 *
 * Use this one for manual testing when you want full and dynamic control of coin
 * flips.  Would not be good to use in automated tests.
 */
public class ManualCoin implements Coin {
    /**
     * Flip a coin by asking the user for the result of the flip.
     *
     * @return true or false, depending on the user's selection
     */
    public boolean flip( ) {
        Scanner instream = new Scanner( System.in );

        // Get the input from the user on what they want to return as the flip.

        System.out.println("Enter coin flip value: 1 for true, 0 for false");
        int choice = instream.nextInt();

        return choice == 1;
    }
}

