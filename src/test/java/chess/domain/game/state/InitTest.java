package chess.domain.game.state;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import chess.domain.boardstrategy.InitBoardStrategy;
import chess.domain.game.ChessGame;
import chess.domain.game.state.attribute.StateType;
import chess.dto.CommandDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InitTest {
    private State state;

    @BeforeEach
    void setup() {
        state = new Init(new ChessGame(new InitBoardStrategy()));
    }

    @Test
    @DisplayName("초기화 기능 테스트")
    void execute() {
        assertThat(state)
                .isInstanceOf(Init.class);
        state = state.execute(new CommandDto("start"));
        assertThat(state)
                .isInstanceOf(Play.class);
    }

    @Test
    @DisplayName("강제종료 기능 테스트")
    void exitEnd() {
        assertThat(state)
                .isInstanceOf(Init.class);
        state = state.execute(new CommandDto("end"));
        assertThat(state)
                .isInstanceOf(End.class);
    }

    @Test
    @DisplayName("잘못된 커맨드 실행 시 예외처리 된다.")
    void executeError() {
        assertThatThrownBy(() -> state.execute(new CommandDto("status")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("isRun() 실행 시 true 를 리턴한다")
    void isRun() {
        assertThat(state.getType() != StateType.END)
                .isTrue();
    }

    @Test
    @DisplayName("isPlay() 실행 시 false 를 리턴한다")
    void isPlay() {
        assertThat(state.getType() == StateType.PLAY)
                .isFalse();
    }

    @Test
    @DisplayName("해당 상태가 Status 가 아님을 확인할 수 있다.")
    void isStatus() {
        Assertions.assertThat(state.getType() == StateType.STATUS)
                .isFalse();
    }
}
