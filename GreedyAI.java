import java.util.List;

public class GreedyAI extends AIPlayer {
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves.isEmpty()) {
            return null; // No legal move available
        }

        // Sorting the moves using a comparator
        validMoves.sort((pos1, pos2) -> {
            int flips1 = gameStatus.countFlips(pos1); // Number of flips for position 1
            int flips2 = gameStatus.countFlips(pos2); // Number of flips for position 2

            // Primary comparison: based on the number of flips
            if (flips1 != flips2) {
                return Integer.compare(flips2, flips1); // Higher flips come first
            }

            // Secondary comparison:Right based on the column (col)
            if (pos1.col() != pos2.col()) {
                return Integer.compare(pos2.col(), pos1.col());
            }

            // Tertiary comparison:Down based on the row
            return Integer.compare(pos2.row(), pos1.row());
        });

        // Choosing the best move
        Position bestMove = validMoves.get(0);

        // Creating a simple disc
        Disc simpleDisc = new SimpleDisc(this);

        return new Move(bestMove, simpleDisc);
    }
}
