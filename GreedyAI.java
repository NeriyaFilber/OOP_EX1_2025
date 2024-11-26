import java.util.List;

public class GreedyAI extends AIPlayer {
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }


    /**
     * @param gameStatus
     * @return
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves.isEmpty()) {
            return null; // אין מהלך חוקי לביצוע
        }

        Position bestMove = null;
        int maxFlips = -1;

        // בדיקה של כל מהלך חוקי
        for (Position move : validMoves) {
            int flips = gameStatus.countFlips(move);
            if (flips > maxFlips) {
                maxFlips = flips;
                bestMove = move;
            } else if (flips == maxFlips) {
                // השוואה על פי סדר קדימויות: ימני ביותר ואז תחתון ביותר
                if (move.col() > bestMove.col() ||
                        (move.col() == bestMove.col() && move.row() > bestMove.row())) {
                    bestMove = move;
                }
            }
        }

        // יצירת דיסקית פשוטה
        Disc simpleDisc = new SimpleDisc(this);

        return new Move(bestMove, simpleDisc);

    }
}
