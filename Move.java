import java.util.Stack;

public class Move {
    private  Stack <Disc[][]> _moves = new Stack<>();

    public void enter_to_stack(Disc[][] board){
        _moves.push(board);
    }

    public void set_moves(Stack<Disc[][]> moves) {
        _moves = moves;
    }
    public Disc[][] get_last_move(){
        return _moves.pop();
    }

    //TODO create method to make the move (place specific disc at specific place). The two methods below.
    public Position position() {
        return null;
    }

    public Disc disc() {
        return null;
    }
}
