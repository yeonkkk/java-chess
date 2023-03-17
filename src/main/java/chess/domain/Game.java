package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.square.Square;
import java.util.Map;

public class Game {

    private final Board board;

    public Game(final Board board) {
        this.board = board;
    }

    public Map<Square, Piece> getPieces() {
        return board.getPieces();
    }
}
