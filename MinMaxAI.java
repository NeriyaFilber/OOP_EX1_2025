import java.util.List;

public class MinMaxAI extends AIPlayer {

    public MinMaxAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * מחשב את האפשרויות לצעדים הבאים.
     *
     * @param gl  מצב המשחק הנוכחי.
     * @param pos מהלכים חוקיים לשחקן.
     * @param k   עומק רקורסיה.
     * @return ניקוד המבוסס על דיסקים שהתהפכו.
     */
    public int maxFlips(GameLogic gl, List<Position> pos, int k) {
        if (k > 1) return 0;

        if (pos == null || pos.isEmpty()) {
            int myCounter = 0, opponentCounter = 0;
            for (int i = 0; i < gl.getBoardSize(); i++) {
                for (int j = 0; j < gl.getBoardSize(); j++) {
                    Position p = new Position(i, j);
                    if (gl.getDiscAtPosition(p) != null) {
                        if (gl.getDiscAtPosition(p).getOwner().isPlayerOne() == this.isPlayerOne()) {
                            myCounter++;
                        } else {
                            opponentCounter++;
                        }
                    }
                }
            }
            return 1000000000 * (opponentCounter - myCounter);
        }

        int maxFlips = gl.countFlips(pos.get(0));
        gl.locate_disc(pos.get(0), new SimpleDisc(gl.get_current_player()));
        maxFlips -= maxFlips(gl, gl.ValidMoves(), k + 1);
        gl.undoLastMove();

        for (Position p : pos) {
            for (int i = 0; i < 3; i++) {
                int t = gl.countFlips(p);
                boolean validMove = (i == 0 && gl.locate_disc(p, new SimpleDisc(gl.get_current_player()))) ||
                        (i == 1 && this.getNumber_of_unflippedable() > 0 && gl.locate_disc(p, new UnflippableDisc(gl.get_current_player()))) ||
                        (i == 2 && this.getNumber_of_bombs() > 0 && gl.locate_disc(p, new BombDisc(gl.get_current_player())));
                if (validMove) {
                    t -= maxFlips(gl, gl.ValidMoves(), k + 1);
                    if (t < -100000000 || t > 100000000) return t;
                    if (t > maxFlips) maxFlips = t;
                    gl.undoLastMove();
                }
            }
        }
        return maxFlips;
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> pos = gameStatus.ValidMoves();

        // אם אין מהלך חוקי
        if ( pos.isEmpty()) {
            return null; // אין מהלך אפשרי, האחריות על סיום המשחק נשארת ב-GUI.
        }

        Position best = pos.get(0);
        double bestScore = gameStatus.countFlips(best);
        ((GameLogic) gameStatus).locate_disc(best, new SimpleDisc(((GameLogic) gameStatus).get_current_player()));
        bestScore -= maxFlips((GameLogic) gameStatus, gameStatus.ValidMoves(), 0);
        ((GameLogic) gameStatus).undoLastMove();

        int discType = 0;
        for (Position p : pos) {
            double score = gameStatus.countFlips(p);
            for (int j = 0; j < 3; j++) {
                double tempScore = score;
                boolean validMove = (j == 0 && ((GameLogic) gameStatus).locate_disc(p, new SimpleDisc(((GameLogic) gameStatus).get_current_player()))) ||
                        (j == 1 && this.getNumber_of_unflippedable() > 0 && ((GameLogic) gameStatus).locate_disc(p, new UnflippableDisc(((GameLogic) gameStatus).get_current_player()))) ||
                        (j == 2 && this.getNumber_of_bombs() > 0 && ((GameLogic) gameStatus).locate_disc(p, new BombDisc(((GameLogic) gameStatus).get_current_player())));
                if (validMove) {
                    tempScore -= maxFlips((GameLogic) gameStatus, gameStatus.ValidMoves(), 0);
                    if (tempScore > bestScore) {
                        bestScore = tempScore;
                        best = p;
                        discType = j;
                    }
                    ((GameLogic) gameStatus).undoLastMove();
                }
            }
        }

        if (discType == 0) {
            return new Move(best, new SimpleDisc(((GameLogic) gameStatus).get_current_player()));
        } else if (discType == 1) {
            return new Move(best, new UnflippableDisc(((GameLogic) gameStatus).get_current_player()));
        } else {
            return new Move(best, new BombDisc(((GameLogic) gameStatus).get_current_player()));
        }
    }
}
