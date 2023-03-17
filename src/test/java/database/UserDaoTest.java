package database;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    private final UserDao userDao = new UserDao();

    // 초기화 코드

    @Test
    void connection() {
        try (final var connection = userDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void insert() {
        final var user = new User("testUserId", "testUser");
        userDao.insert(user);
    }

    @Test
    public void findByUserId() {
        final var user = userDao.findByUserId("testUserId");
        assertThat(user).isEqualTo(new User("testUserId", "testUser"));
    }

//    @Test
//    public void updateUserName() {
//        var user = userDao.findByUserId("testUserId");
//        userDao.updateUserName(user, "newName");
//
//        assertThat(user).isEqualTo(new User("testUserId", "newName"));
//    }
}