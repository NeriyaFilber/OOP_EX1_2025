import java.util.Comparator;
import java.util.List;

public class GreedyAI extends AIPlayer {
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }
    //Comparator
    public class PositionCompare implements Comparator<Position> {
        private final PlayableLogic gameStatus;

        public PositionCompare(PlayableLogic gameStatus) {
            this.gameStatus = gameStatus;
        }
        // Number of flips for each position
        public int compare(Position pos1, Position pos2) {
            int flips1 =gameStatus.countFlips(pos1);
            int flips2 =gameStatus.countFlips(pos2);
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

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves.isEmpty()) {
            return null; // No legal move to make
        }
        // Sorting the moves using a comparator
        validMoves.sort(new PositionCompare(gameStatus));

        // Choose the best move (the first in the list)
        Position bestMove = validMoves.get(0);

        Disc simpleDisc = new SimpleDisc(this);

        return new Move(bestMove, simpleDisc);
    }
}