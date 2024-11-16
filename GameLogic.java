import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameLogic implements PlayableLogic {
    private final int BOARD_SIZE = 8;
    private static final int[][] DIRECTIONS = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };
    private Player _first_player;
    private Player _seconed_player;
    private ArrayList<Position> _valid_moves;
    private boolean _turn;
    private Disc[][] _board = new Disc[BOARD_SIZE][BOARD_SIZE];
    private Move _moves = new Move();


    /**
     * Default constructor.
     */
    public GameLogic() {
    }

    /**
     * Attempt to locate a disc on the game board.
     *
     * @param a    The position for locating a new disc on the board.
     * @param disc The disc to locate.
     * @return true if the move is valid and successful, false otherwise.
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (!(_board[a.row()][a.col()] == null) || !_valid_moves.contains(a)) {
            return false;
        }
        Player player = isFirstPlayerTurn()? _first_player:_seconed_player;
        if ((Objects.equals(disc.getType(), "⭕") && player.getNumber_of_unflippedable() <= 0) //handle bomb and unflappable disc
            || (Objects.equals(disc.getType(), "💣") && player.getNumber_of_bombs() <= 0)){
            return false;
        }
        if (Objects.equals(disc.getType(), "⭕")){player.reduce_unflippedable();}
        if (Objects.equals(disc.getType(), "💣")){player.reduce_bomb();}
        _moves.enter_to_stack(copy_board());
        _board[a.row()][a.col()] = disc;
        System.out.printf("Player %s placed a %s in (%d, %d)\n", isFirstPlayerTurn() ? "1" : "2", disc.getType(), a.row(), a.col());
        countFlipsInDirection(a.row(), a.col(), true);
        _turn = !_turn;
        System.out.println();
        return true;
    }

    /**
     * Get the disc located at a given position on the game board .
     *
     * @param position The position for which to retrieve the disc.
     * @return Deep copy of the piece at the specified position, or null if no disc is present.
     */
    @Override
    public Disc getDiscAtPosition(Position position) {
        if (_board[position.row()][position.col()] == null) {
            return null;
        }
        if (Objects.equals(_board[position.row()][position.col()].getType(), "⭕")) {
            return new UnflippableDisc(_board[position.row()][position.col()].getOwner());
        }
        if (Objects.equals(_board[position.row()][position.col()].getType(), "💣")) {
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
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Position a = new Position(i, j);
                if (_board[i][j] == null && countFlips(a) != 0) {
                    _valid_moves.add(a);
                }
            }
        }
        return _valid_moves;
    }

    /**
     * The number of discs that will be flipped
     *
     * @param a Count the possible flips of position a
     * @return The number of discs that will be flipped if a disc will be placed in the 'a'.
     */
    @Override
    public int countFlips(Position a) {
          return countFlipsInDirection(a.row(), a.col(), false);
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
        _valid_moves = (ArrayList<Position>) ValidMoves();
        if(!_valid_moves.isEmpty()){
            _valid_moves = null;
            return false;
        }
        int player_1_discs = 0;
        int player_2_discs = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(_board[i][j] != null){
                    if (_board[i][j].getOwner() == _first_player) {
                        player_1_discs++;
                    } else {
                        player_2_discs++;
                    }
                }
            }
        }
        int win_disc = Math.max(player_1_discs, player_2_discs);
        int loser_disc = Math.min(player_1_discs,player_2_discs);
        String winner = player_1_discs >= player_2_discs ? "1" : "2";
        String loser = player_1_discs < player_2_discs ? "1" : "2";
        System.out.printf("Player %s wins with %d discs! Player %s had %d discs.\n\n",winner,win_disc,loser,loser_disc);
        if(winner.equals("1")){
            _first_player.addWin();
        }
        else {
            _seconed_player.addWin();
        }
        return true;
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
        _first_player.reset_bombs_and_unflippedable();
        _seconed_player.reset_bombs_and_unflippedable();
        _moves = new Move();
    }

    /**
     * Undo the last move made in the game, reverting the board state and turn order.
     * Works only with 2 Human Players, and does not work when AIPlayer is playing.
     */
    @Override
    public void undoLastMove() {
        try {
            Disc[][] last_move = _moves.get_last_move();
            print_undo(last_move);
            _board = last_move;
            _turn = !_turn;
        }
        catch (Exception e){
            System.out.println("Undoing last move:\n\tNo previous move available to undo.\n");
        }
    }


    /**
     * Count the flips in a specific direction based on the position and direction.
     *
     * @param row      The starting row of the position.
     * @param col      The starting column of the position.

     * @param flip     Whether to flip the discs during this process.
     * @return The number of discs that will be flipped in this direction.
     */
    private int countFlipsInDirection(int row, int col,boolean flip) {
        ArrayList<Disc> to_flip = new ArrayList<>();
        for (int[] direction : DIRECTIONS) {
            Player opponent = isFirstPlayerTurn() ? _seconed_player : _first_player;
            Player player = isFirstPlayerTurn() ? _first_player : _seconed_player;
            int rowDir = direction[0];
            int colDir = direction[1];
            int currentRow = row + rowDir;
            int currentCol = col + colDir;
            ArrayList<Disc> flip_in_direction = new ArrayList<>();

            // Traverse in the specified direction
            while (isInBounds(currentRow, currentCol) &&
                    _board[currentRow][currentCol] != null &&
                    _board[currentRow][currentCol].getOwner().equals(opponent)) {
                if (!Objects.equals(_board[currentRow][currentCol].getType(), "⭕")) {

                    flip_in_direction.add(_board[currentRow][currentCol]);
                }

                currentRow += rowDir;
                currentCol += colDir;
            }

            // Validate the sequence to ensure it ends with the current player's piece
            if (!isInBounds(currentRow, currentCol) ||
                    _board[currentRow][currentCol] == null ||
                    _board[currentRow][currentCol].getOwner() != player) {
                flip_in_direction.clear();  // Reset flips if not bounded by the player's piece
            }
            if (!flip_in_direction.isEmpty()) {
                for (int i = 0; i < flip_in_direction.size(); i++) {
                    Position a = find_disc(flip_in_direction.get(i));
                    if (Objects.equals(flip_in_direction.get(i).getType(), "💣")) {
                        flip_bomb(a.row(), a.col(), flip_in_direction);
                    }


                }
            }
            if (flip && !flip_in_direction.isEmpty()){
                for (int i = 0; i < flip_in_direction.size(); i++) {
                    Position a = find_disc(flip_in_direction.get(i));
                        flip_in_direction.get(i).setOwner(player);
                        System.out.printf("Player %s flipped the %s in (%d, %d) \n", isFirstPlayerTurn() ? "1" : "2", flip_in_direction.get(i).getType(), a.row(), a.col());
                }
            }
            for (Disc disc : flip_in_direction) {
                if (!to_flip.contains(disc)) {
                    to_flip.add(disc);
                }
            }
        }
        return to_flip.size();
    }

    /**
     * Checks if a position is within board boundaries.
     *
     * @param row The row to check.
     * @param col The column to check.
     * @return True if the position is within bounds, false otherwise.
     */
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < _board.length && col >= 0 && col < _board[0].length;
    }

    /**
     * Create a deep copy of the current game board.
     *
     * @return A deep copy of the game board as a 2D array of discs.
     */
    private Disc[][] copy_board() {
        Disc[][] board = new Disc[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (_board[i][j] == null) {
                    board[i][j] = null;
                } else if (Objects.equals(_board[i][j].getType(), "⭕")) {
                    board[i][j] = new UnflippableDisc(_board[i][j].getOwner());
                } else if (Objects.equals(_board[i][j].getType(), "💣")) {
                    board[i][j] = new BombDisc(_board[i][j].getOwner());
                } else if (Objects.equals(_board[i][j].getType(), "⬤")) {
                    board[i][j] = new SimpleDisc(_board[i][j].getOwner());
                }
            }
        }
        return board;
    }


    /**
     * Flips all discs around a bomb disc in all valid directions.
     *
     * @param row           The row position of the bomb disc.
     * @param col           The column position of the bomb disc.
     * @param flipped_disc  The list of discs already flipped to avoid duplication.
     * @return The number of additional discs flipped due to the bomb effect.
     */
    private int flip_bomb(int row, int col, ArrayList<Disc> flipped_disc) {
        int flip = 0;
        Player player = isFirstPlayerTurn() ? _first_player : _seconed_player;
        for (int[] direction : DIRECTIONS) {
            int currentRow = row + direction[0];
            int currentCol = col + direction[1];
            if (isInBounds(currentRow,currentCol)
                    && _board[currentRow][currentCol] != null
                    && _board[currentRow][currentCol].getOwner() != player
                    && !Objects.equals(_board[currentRow][currentCol].getType(), "⭕")
                    && !flipped_disc.contains(_board[currentRow][currentCol])){
                if (Objects.equals(_board[currentRow][currentCol].getType(), "💣")){
                    flipped_disc.add(_board[currentRow][currentCol]);
                    flip += flip_bomb(currentRow,currentCol,flipped_disc) +1;
                }
                else {
                    flipped_disc.add(_board[currentRow][currentCol]);
                    flip++;
                }
            }
        }
        return flip;
    }

    /**
     * Find the position of a specific disc on the board.
     *
     * @param disc The disc to locate.
     * @return The position of the disc, or null if it is not found.
     */
    private Position find_disc(Disc disc){
        Position pos = null;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (_board[i][j] == disc){
                    pos = new Position(i,j);
                }
            }
        }
        return pos;
    }


    /**
     * Print the changes made during an undo operation.
     *
     * @param prev_board The state of the board before the last move.
     */
    private void print_undo(Disc[][] prev_board){
        System.out.println("Undoing last move:");
        Position a = find_new_piece(prev_board);
        System.out.printf("\tUndo: removing %s from (%d, %d)\n", _board[a.row()][a.col()].getType(), a.row(),a.col());
        ArrayList<Position> list_of_flips = find_flip_pieces(prev_board);
        for (Position pos : list_of_flips) {
            System.out.printf("\tUndo: flipping back %s in (%d, %d)\n", _board[pos.row()][pos.col()].getType(), pos.row(), pos.col());
        }
        System.out.println();
    }

    /**
     * Find the position of a newly placed piece by comparing the current and previous boards.
     *
     * @param prev_board The state of the board before the last move.
     * @return The position of the newly placed piece.
     */

    private Position find_new_piece(Disc[][] prev_board){
        Position a =null;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((_board[i][j] != null) && (prev_board[i][j] == null)){
                    a = new Position(i, j);
                }
            }

        }
        return a;
    }

    /**
     * Identify the positions of pieces that were flipped during the last move.
     *
     * @param prev_board The state of the board before the last move.
     * @return A list of positions where pieces were flipped.
     */
    private ArrayList<Position> find_flip_pieces(Disc[][] prev_board){
        ArrayList<Position> ans = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(_board[i][j] != null
                        && prev_board[i][j]!= null
                        && _board[i][j].getOwner()!= prev_board[i][j].getOwner()){
                    ans.add(new Position(i,j));
                }
            }

        }
        return ans;
    }
}

