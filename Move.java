import java.util.Stack;

public class Move {
    private Stack<Disc[][]> _moves = new Stack<>();
    private Position position;
    private  Disc disc;

    public Move(Position position, Disc disc) {
        this.position = position;
        this.disc = disc;
    }

    public Position position() {
        return position;
    }

    public Disc disc() {
        return disc;
    }

    public Move() {
    }

    public void enter_to_stack(Disc[][] board) {
        _moves.push(board);
    }

    public void set_moves(Stack<Disc[][]> moves) {
        _moves = moves;
    }

    public Disc[][] get_last_move() {
        return _moves.pop();
    }
}
