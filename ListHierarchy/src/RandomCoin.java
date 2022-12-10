/**
 * A coin flipper that is truly random.
 *
 * The default configuration is a fair coin, generating as many true values as false ones.
 *
 * You can create a biased coin by providing a percentage of number of true values at
 * the initialization of the object.
 */
public class RandomCoin implements Coin {
    // Since we flip the coin by generating a random number between 0 and 1,
    // Store the level at which we switch from true responses to false ones.
    private double threshold = 0.5;

    /**
     * Constructor for the class if we want to create a biased coin.
     *
     * Providing an invalid percentage of true values results in a fair coin.
     *
     * @param percentTrueTarget -- percentage of the time for us to generate a true value for the flip.
     */
    public RandomCoin( double percentTrueTarget ) {
        if ((percentTrueTarget >= 0) && (percentTrueTarget <= 1.0)){
            threshold = percentTrueTarget;
        }
    }

    /**
     * Default random coin constructor.
     *
     * Leaves the default values, which should be a fair coin.
     */
    public RandomCoin( ) {
    }

    /**
     * Generate a random true/false value
     *
     * @return -- random true or false, whose distribution is guided at object initialization time
     */
    public boolean flip( ) {
        return Math.random() >= threshold;
    }
}
