import java.util.Stack;

public class Move {
    private static Stack <Disc[][]> _moves = new Stack<>();

    public static void enter_to_stack(Disc[][] board){
        _moves.push(board);
    }

    public static void set_moves(Stack<Disc[][]> _moves) {
        Move._moves = _moves;
    }
    public static Disc[][] get_last_move(){
        return _moves.pop();
    }

    //TODO create method to make the move (place specific disc at specific place). The two methods below.
    public Position position() {
        return null;
    }

    public Disc disc() {
        return null;
    }
    //TODO create undo function and arraylist of turns.
    //TODO create function that run and calculate the number of flips.
    //TODO create method to make the move (place specific disc at specific place)

}
