/**
 * A Coin class that lets you replay a sequence of coin flips predictably.
 *
 * Use this version of the Coin for testing, especially for automated testing,
 * to reliably force your code outcomes into a deterministic output structure.
 */
public class ArrayCoin implements Coin {
    // Store a list of coin flips through which we cycle when asked for a flip.
    private int[] flipList = null;
    private int position = 0;

    /**
     * Manage a set of flips by the values given to us in an array.  A value of 1 in the array
     * means a true value and any other value is a false value.
     *
     * We can initialize with an empty array.  That behaviour is defined by flip().
     *
     * @param flips -- array of coin flips to make and to cycle through.
     */
    public ArrayCoin( int[] flips ) {
        flipList = new int[flips.length];

        // Make a copy of the array for our string flips in the future.
        System.arraycopy( flips, 0, flipList, 0, flips.length );
    }

    /**
     * Manage a set of flips by the values given to us in string.  Each character in the string
     * is either 1 or 0 (well, non-1) to represent true and false respectively.
     *
     * We can initialize with an empty string.  That behaviour is defined by flip().
     *
     * @param flips -- string reprsentation of coin flips to make and to cycle through.
     */
    public ArrayCoin( String flips ) {
        flipList = new int[flips.length()];

        // Copy all the elements of the string into the integer array of flips to make
        for (int i = 0; i < flips.length(); i++) {
            flipList[i] = (flips.charAt(i) == '1' ? 1 : 0);
        }
    }

    /**
     * Return a flip of the coin.  The flip comes from the stored pattern given at initialization.
     * If we exhaust that initialization pattern then we cycle through it again.
     *
     * If the initialization pattern has no flips in it then we always return false.
     *
     * @return the boolean coin flip represented by the initialization pattern we were given.
     */
    public boolean flip( ) {
        int flipValue;

        if (flipList.length > 0) {
            flipValue = flipList[position];
            position = (position + 1) % flipList.length;
        } else {
            flipValue = 0;
        }

        return flipValue == 1;
    }
}
