import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * RandomAI represents an AI player that makes random moves.
 * It selects a random valid position on the board and chooses
 * a random type of disc (from its inventory) to place at that position.
 */
public class RandomAI extends AIPlayer {

    /**
     * Constructs a RandomAI player.
     *
     * @param isPlayerOne true if this player is player one, false otherwise.
     */
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Selects a random position from a list of possible moves.
     *
     * @param possibleMoves The list of valid positions where the AI can play.
     * @return A randomly selected position from the list.
     */
    private Position getRandomPosition(List<Position> possibleMoves) {
        Random random = new Random();
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }

    /**
     * Selects a random disc from the player's inventory.
     * If bombs or unflappable discs are available, they are added
     * to the selection pool along with the simple disc.
     *
     * @return A randomly selected disc from the player's available inventory.
     */
    private Disc getRandomDisc() {
        Random random = new Random();

        // List of available disc options
        List<Disc> availableDiscs = new ArrayList<>();

        // Adding available discs based on inventory
        if (getNumber_of_bombs() > 0) {
            availableDiscs.add(new BombDisc(this));
        }
        if (getNumber_of_unflippedable() > 0) {
            availableDiscs.add(new UnflippableDisc(this));
        }
        availableDiscs.add(new SimpleDisc(this)); // A simple disc is always available

        // Return a random disc from the list
        return availableDiscs.get(random.nextInt(availableDiscs.size()));
    }

    /**
     * Makes a move by randomly selecting a valid position and a disc type.
     *
     * @param gameStatus The current state of the game, providing information
     *                   about valid moves and game logic.
     * @return A {@link Move} object containing a randomly selected position
     *         and disc type, or null if there are no valid moves.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        // Get the list of possible moves
        List<Position> possibleMoves = gameStatus.ValidMoves();

        // If there are no legal moves, return null
        if (possibleMoves.isEmpty()) {
            return null;
        }

        // Select a random position
        Position randomPosition = getRandomPosition(possibleMoves);

        // Select a random disc
        Disc randomDisc = getRandomDisc();

        return new Move(randomPosition, randomDisc);
    }
}
