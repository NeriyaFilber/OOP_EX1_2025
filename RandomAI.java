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

        // רשימת אפשרויות הדסקיות שזמינות
        List<Disc> availableDiscs = new ArrayList<>();

        // הוספת דסקיות זמינות בהתבסס על המלאי
        if (getNumber_of_bombs() > 0) {
            availableDiscs.add(new BombDisc(this));
        }
        if (getNumber_of_unflippedable() > 0) {
            availableDiscs.add(new UnflippableDisc(this));
        }
        availableDiscs.add(new SimpleDisc(this)); // דסקית פשוטה זמינה תמיד

        // החזרת דסקית רנדומלית מתוך הרשימה
        return availableDiscs.get(random.nextInt(availableDiscs.size()));
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        // קבלת רשימת מהלכים אפשריים
        List<Position> possibleMoves = gameStatus.ValidMoves();

        // אם אין מהלכים חוקיים, החזר null
        if (possibleMoves.isEmpty()) {
            return null;
        }

        // בחר מיקום רנדומלי
        Position randomPosition = getRandomPosition(possibleMoves);

        // בחר דסקית רנדומלית
        Disc randomDisc = getRandomDisc();

        // צור והחזר את המהלך
        return new Move(randomPosition, randomDisc);
    }


}