import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer {
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }
    private Position getRandomPosition(List<Position> possibleMoves) {
        Random random = new Random();
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }
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