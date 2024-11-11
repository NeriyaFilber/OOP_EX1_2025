public class Position {
    private int _row;
    private int _col;
    private Disc disc;
    private boolean _isEmpty;

    public Position(int row, int col) {
        _row = row;
        _col = col;
        _isEmpty = true;
        disc = null;
    }


    public Disc get_disc() {
        return disc;
    }

    public void setDisc(Disc disc) {
        this.disc = disc;
        this._isEmpty = false;
    }

    public boolean getIsEmpty() {
        return _isEmpty;
    }

    public void setIsEmpty() {
        this._isEmpty = true;
    }


    public int row() {
        return _row;
    }

    public int col() {
        return _col;
    }
}
