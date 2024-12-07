import java.util.Comparator;
import java.util.List;

/**
 * GreedyAI is an AIPlayer that makes moves based on a greedy strategy.
 * It evaluates possible positions based on the maximum number of flips,
 * prioritizing moves that result in higher flips, higher column values,
 * and higher row values in descending order.
 */
public class GreedyAI extends AIPlayer {

    /**
     * Constructs a GreedyAI player.
     *
     * @param isPlayerOne true if this AI is player one, false otherwise.
     */
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Comparator for comparing positions based on the number of flips
     * and secondary criteria like column and row values.
     */
    public static class PositionCompare implements Comparator<Position> {
        private final PlayableLogic gameStatus;

        /**
         * Constructs a PositionCompare object.
         *
         * @param gameStatus the current game logic object that provides
         *                   information about valid moves and flips.
         */
        public PositionCompare(PlayableLogic gameStatus) {
            this.gameStatus = gameStatus;
        }

        /**
         * Compares two positions based on their potential flips, column, and row values.
         *
         * @param pos1 the first position to compare.
         * @param pos2 the second position to compare.
         * @return a negative integer if pos1 is "better", a positive integer if pos2 is "better",
         *         or zero if they are equal based on the criteria.
         */
        @Override
        public int compare(Position pos1, Position pos2) {
            int flips1 = gameStatus.countFlips(pos1);
            int flips2 = gameStatus.countFlips(pos2);
            // Primary comparison: by number of flips (higher is better)
            if (flips1 != flips2) {
                return Integer.compare(flips2, flips1);
            }
            // Secondary comparison: by the highest column (col)
            if (pos1.col() != pos2.col()) {
                return Integer.compare(pos2.col(), pos1.col());
            }
            // Tertiary comparison: by the highest row
            return Integer.compare(pos2.row(), pos1.row());
        }
    }

    /**
     * Makes a move based on the greedy strategy.
     *
     * @param gameStatus the current state of the game providing available moves
     *                   and game logic.
     * @return the best move chosen by the AI or null if no legal moves are available.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves.isEmpty()) {
            return null; // No legal move to make
        }
        // Sorting the moves using a comparator
        validMoves.sort(new PositionCompare(gameStatus));

        // Choose the best move (the first in the list)
        Position bestMove = validMoves.getFirst();

        Disc simpleDisc = new SimpleDisc(this);

        return new Move(bestMove, simpleDisc);
    }
}
