package chess.domain.piece.strategy;

import chess.domain.board.Path;
import chess.domain.position.Position;
import java.util.List;

public final class WhitePawnStrategy implements PieceStrategy {

    private static final String NAME = "p";
    private static final double VALUE = 1;
    private static final String TYPE = "white pawn";

    @Override
    public List<Direction> directions() {
        return Direction.whitePawnDirection();
    }

    @Override
    public Path pathFrom(Direction direction, Position position) {
        return position.shortPath(direction);
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public double value() {
        return VALUE;
    }

    @Override
    public String type() {
        return TYPE;
    }
}