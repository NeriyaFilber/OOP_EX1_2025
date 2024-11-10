public class HumanPlayer extends Player {
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
