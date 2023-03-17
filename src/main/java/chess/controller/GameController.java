package chess.controller;

import static chess.domain.Command.MOVE;
import static chess.domain.Command.START;

import chess.domain.Board;
import chess.domain.Command;
import chess.domain.piece.Piece;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.domain.Team;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameController {

    private final InputView inputView;
    private final OutputView outputView;

    public GameController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        // start, end 커맨드 입력 받기
        Command runCommand = enterRunCommand();

        // 첫 체스판 출력하기
        if (isStart(runCommand)) {
            playGame();
        }
    }

    private void playGame() {
        Board board = new Board();
        printChessBoard(board);
        Command command = MOVE;

        movePieces(board, command);
    }

    private void movePieces(final Board board, Command command) {
        while (command != MOVE) {
            // move, end 입력 받기 -> 이동하기 반복
            List<String> playCommand = enterPlayCommand();
            command = getCommand(playCommand);
            List<String> positions = getPositions(playCommand);

            if (command == MOVE) {
                Team team = Team.WHITE;
                // 이동하기
                movePiece(board, positions, team);
                team = Team.change(team);
            }
        }
    }

    private void movePiece(final Board board, final List<String> positions, final Team team) {
        List<Square> squares = getSquares(positions);
        Square src = squares.get(0);
        Square dst = squares.get(1);

        validateTeam(board, team, src);
        board.move(src, dst);
        printChessBoard(board);
    }

    private void validateTeam(final Board board, final Team team, final Square src) {
        if (!board.isSameColor(src, team)) {
            throw new IllegalArgumentException("다른 색 말을 움직여 주세요.");
        }
    }

    private List<String> getPositions(List<String> playCommand) {
        validatePositionCommand(playCommand);

        return List.of(playCommand.get(1), playCommand.get(2));
    }

    private Command getCommand(final List<String> playCommand) {
        Command command = Command.from(playCommand.get(0));
        checkStartCommand(command);

        return command;
    }

    private List<Square> getSquares(List<String> positions) {
        Square src = getSquare(positions.get(0));
        Square dst = getSquare(positions.get(1));
        
        return List.of(src, dst);
    }

    private Square getSquare(final String inputPosition) {
        List<String> position = Arrays.asList(inputPosition.split(""));
        validatePosition(position);

        File file = File.findFileBy(position.get(0));
        Rank rank = Rank.findRankBy(position.get(1));
        return Square.of(file, rank);
    }

    private void validatePosition(final List<String> position) {
        if (position.size() != 2) {
            throw new IllegalArgumentException("source위치, target 위치는 알파벳(a~h)과 숫자(1~8)로 입력해주세요. 예) a1");
        }
    }

    private void validatePositionCommand(final List<String> gameCommand) {
        if (gameCommand.size() != 3) {
            throw new IllegalArgumentException("move source위치 target위치 형태로 입력해주세요.");
        }
    }

    private List<String> enterPlayCommand() {
        List<String> playCommand = inputView.readGameCommand();
        return playCommand;
    }

    private void checkStartCommand(final Command command) {
        if (command == START) {
            throw new IllegalArgumentException("move 또는 end를 입력해주세요.");
        }
    }

    private void printChessBoard(final Board board) {
        Map<Square, Piece> pieces = board.getPieces();
        outputView.printChessBoard(board.getPieces());
    }

    public boolean isStart(Command command) {
        return command == START;
    }

    public Command enterRunCommand() {
        outputView.printGameStart();
        List<String> gameCommand = inputView.readGameCommand();
        Command command = Command.from(gameCommand.get(0));
        checkMoveCommand(command);

        return command;
    }

    private void checkMoveCommand(final Command command) {
        if (command == MOVE) {
            throw new IllegalArgumentException("start 또는 end를 입력해주세요.");
        }
    }
}
