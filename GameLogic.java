import java.util.ArrayList;
import java.util.List;

public class GameLogic implements PlayableLogic {
    private final int BOARD_SIZE = 8;
    private Player _first_player;
    private Player _seconed_player;
    private ArrayList<Position> _valid_moves;
    private boolean _turn;
    private Disc[][] _board = new Disc[BOARD_SIZE][BOARD_SIZE];


    public GameLogic(){
    }
    /**
     * Attempt to locate a disc on the game board.
     *
     * @param a    The position for locating a new disc on the board.
     * @param disc
     * @return true if the move is valid and successful, false otherwise.
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (!(_board[a.row()][a.col()] == null) ){//|| !_valid_moves.contains(a)){
            return false;
        }
        _board[a.row()][a.col()] = disc;
        _turn = !_turn;
        String player = isFirstPlayerTurn() ? "1" : "2";
        System.out.println("Player " + player + " placed a " + disc.getType() + " in ("+a.row()+","+a.col()+")");
        return true;
    }

    /**
     * Get the disc located at a given position on the game board.
     *
     * @param position The position for which to retrieve the disc.
     * @return The piece at the specified position, or null if no disc is present.
     */
    @Override
    public Disc getDiscAtPosition(Position position) {
        if (_board[position.row()][position.col()] == null){
            return null;
        }
        if (_board[position.row()][position.col()].getType() == "⭕"){
            return new UnflippableDisc(_board[position.row()][position.col()].getOwner());
        }
        if (_board[position.row()][position.col()].getType() == "💣"){
            return new BombDisc(_board[position.row()][position.col()].getOwner());
        }
        return new SimpleDisc(_board[position.row()][position.col()].getOwner());
    }

    /**
     * Get the size of the game board.
     *
     * @return The size of the game board, typically as the number of rows or columns.
     */
    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    /**
     * Get a list of valid positions for the current player.
     *
     * @return A list of valid positions where the current player can place a disc.
     */
    @Override
    public List<Position> ValidMoves() {
        _valid_moves = new ArrayList<>();
        Position pos = new Position(5,5);
        _valid_moves.add(pos);
        return _valid_moves;
    }

    /**
     * The number of discs that will be flipped
     *
     * @param a
     * @return The number of discs that will be flipped if a disc will be placed in the 'a'.
     */
    @Override
    public int countFlips(Position a) {

        return 0;
    }

    /**
     * Get the first player.
     *
     * @return The first player.
     */
    @Override
    public Player getFirstPlayer() {
        return _first_player;
    }

    /**
     * Get the second player.
     *
     * @return The second player.
     */
    @Override
    public Player getSecondPlayer() {
        return _seconed_player;
    }

    /**
     * Set both players for the game.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     */
    @Override
    public void setPlayers(Player player1, Player player2) {
        _first_player = player1;
        _seconed_player = player2;
    }

    /**
     * Check if it is currently the first player's turn.
     *
     * @return true if it's the first player's turn, false if it's the second player's turn.
     */
    @Override
    public boolean isFirstPlayerTurn() {
        return _turn;
    }

    /**
     * Check if the game has finished, indicating whether a player has won or if it's a draw.
     *
     * @return true if the game has finished, false otherwise.
     */
    @Override
    public boolean isGameFinished() {
        return _valid_moves.isEmpty();
    }

    /**
     * Reset the game to its initial state, clearing the board and player information.
     */
    @Override
    public void reset() {
        _board = new Disc[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                _board[i][j] = null;
            }
        }
        _board[3][3] = new SimpleDisc(_first_player);
        _board[4][4] = new SimpleDisc(_first_player);
        _board[3][4] = new SimpleDisc(_seconed_player);
        _board[4][3] = new SimpleDisc(_seconed_player);
        _turn = true;
    }

    /**
     * Undo the last move made in the game, reverting the board state and turn order.
     * Works only with 2 Human Players, and does not work when AIPlayer is playing.
     */
    @Override
    public void undoLastMove() {

    }
}
