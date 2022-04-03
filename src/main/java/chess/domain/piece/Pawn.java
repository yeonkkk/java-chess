package chess.domain.piece;

import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.board.position.Rank;
import chess.domain.piece.attribute.Name;
import chess.domain.piece.attribute.Team;
import java.util.List;

public final class Pawn extends DefaultPiece {
    private static final String NO_MOVE_MESSAGE = "폰이 이동할 수 없는 위치입니다.";
    private static final String NO_MOVE_MESSAGE_DIAGONAL = "대각선 방향에 상대 기물이 없으면 이동할 수 없습니다.";
    private static final int INIT_MAX_DISTANCE = 2;
    private static final double SCORE = 1;
    private static final int STRAIGHT_INDEX = 0;

    public Pawn(Team team) {
        super(Name.PAWN, team);
    }

    @Override
    public boolean canMove(Piece targetPiece, Position from, Position to) {
        List<Direction> directions = pawnDirection();
        Direction nowDirection = Direction.of(from, to);

        if (Direction.isInvalidDistance(from, to, directions)) {
            validateInitDirection(team, from, to, directions);
        }
        validateDiagonal(targetPiece, nowDirection);
        return nowDirection != pawnStraightDirection();
    }

    private void validateDiagonal(Piece targetPiece, Direction nowDirection) {
        if (isDiagonal(nowDirection, pawnDiagonalDirection()) && targetPiece.isSameTeamOrEmpty(team)) {
            throw new IllegalArgumentException(NO_MOVE_MESSAGE_DIAGONAL);
        }
    }

    public List<Direction> pawnDirection() {
        if (team == Team.WHITE) {
            return List.of(Direction.TOP, Direction.TOP_LEFT, Direction.TOP_RIGHT);
        }
        return List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_RIGHT);
    }

    private Direction pawnStraightDirection() {
        return pawnDirection().get(STRAIGHT_INDEX);
    }

    private List<Direction> pawnDiagonalDirection() {
        return pawnDirection().subList(STRAIGHT_INDEX + 1, pawnDirection().size());
    }

    private void validateInitDirection(Team team, Position from, Position to, List<Direction> directions) {
        if (isInitLine(team, from) && isInitDistance(from, to, directions.get(0))) {
            return;
        }
        throw new IllegalArgumentException(NO_MOVE_MESSAGE);
    }

    private boolean isInitLine(Team team, Position from) {
        return (team == Team.BLACK && from.isEqualRank(Rank.SEVEN) ||
                (team == Team.WHITE && from.isEqualRank(Rank.TWO)));
    }

    private boolean isInitDistance(Position from, Position to, Direction direction) {
        return from.getXDistance(to) == direction.getX()
                && direction.getY() * from.getYDistance(to) <= INIT_MAX_DISTANCE;
    }

    private boolean isDiagonal(Direction now, List<Direction> directions) {
        return directions.contains(now);
    }

    @Override
    public double getScore() {
        return SCORE;
    }
}
