import java.util.Comparator;
import java.util.List;

public class GreedyAI extends AIPlayer {
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves.isEmpty()) {
            return null; // No legal move to make
        }

        // Comparator for comparing positions
        Comparator<Position> positionComparator = (pos1, pos2) -> {
            int flips1 = gameStatus.countFlips(pos1); // Number of flips for position 1
            int flips2 = gameStatus.countFlips(pos2); // Number of flips for position 2

            // Primary comparison: by number of flips (higher comes first)
            if (flips1 != flips2) {
                return Integer.compare(flips2, flips1); // reverse order
            }

            // Secondary comparison: by the highest column (right)
            if (pos1.col() != pos2.col()) {
                return Integer.compare(pos2.col(), pos1.col());
            }

            // Tertiary comparison: by the highest row (down)
            return Integer.compare(pos2.row(), pos1.row());
        };

        validMoves.sort(positionComparator);

        // Choose the best move (the first in the list)
        Position bestMove = validMoves.get(0);

        Disc simpleDisc = new SimpleDisc(this);

        return new Move(bestMove, simpleDisc);
    }
}
