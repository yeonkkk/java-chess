package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDao {
    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:13306/chess?useSSL=false&serverTimezone=UTC",
                    "root",
                    "root");
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void insert(final User user) {
        final var query = "INSERT INTO user VALUES(?, ?)";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByUserId(final String userId) {
        final var query = "SELECT * FROM user WHERE user_id = ?";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("user_id"),
                        resultSet.getString("name")
                );
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

//    public User updateUserName(final User user, String newName) {
//        final var query = "UPDATE user SET name = ? WHERE user_id = ?";
//        try (final var connection = getConnection();
//             final var preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setString(1, user.getId());
//
//            final var resultSet = preparedStatement.executeUpdate();
//            if (resultSet.next()) {
//                return new User(
//                        resultSet.getString("user_id"),
//                        resultSet.getString("name")
//                );
//            }
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
//
//
//    public User delete(final String userId) {
//        final var query = "DELETE FROM user WHERE user_id = ?";
//        try (final var connection = getConnection();
//             final var preparedStatement = connection.prepareStatement(query)) {
//            preparedStatement.setString(1, userId);
//
//            final var resultSet = preparedStatement.executeQuery();
//
//        } catch (final SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
}
