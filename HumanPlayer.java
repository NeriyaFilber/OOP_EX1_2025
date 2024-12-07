/**
 * HumanPlayer represents a player controlled by a human in the game.
 * This class extends the {@link Player} class and overrides specific behavior
 * to indicate that the player is human.
 */
public class HumanPlayer extends Player {
    /**
     * Constructs a HumanPlayer object.
     *
     * @param b true if this player is player one, false otherwise.
     */
    public HumanPlayer(boolean b) {
        super(b);
    }

    /**
     * Determines whether this player is human.
     *
     * @return true if the player is human.
     */
    @Override
    boolean isHuman() {
        return true;
    }
}
