package pl.coderslab.warsztaty_2.daos;

import pl.coderslab.warsztaty_2.DBUtil;
import pl.coderslab.warsztaty_2.models.User;
import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password, user_groups) VALUES (?, ?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ?, user_groups = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";
    private static final String FIND_ALL_BY_GROUP_ID =
            "SELECT * FROM users WHERE user_groups = ?";
    private static final String CHECK_IF_USER_EXIST =
            "SELECT users.id AS id FROM users WHERE email = ?";
    private static final String CHECK_IF_ID_IS_PROPER =
            "SELECT users.id AS id FROM users WHERE id = ?";

    public User create(User user) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserGroupId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            System.out.println("Adding new user failed. Make sure that input data are proper");
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_groups"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("User with given id does not exist");
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getUserGroupId());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Updating failed. Make sure that input data are proper");
        }
    }

    public void delete(int userId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Deleting failed. Make sure that input data are proper");
        }
    }

    public User[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_groups"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Finding all users failed. Make sure that input data are proper");
            return null;
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[tmpUsers.length - 1] = u;
        return tmpUsers;
    }

    public User[] findAllByGroupId(int groupId){
        try (Connection conn = DBUtil.getConnection()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_GROUP_ID);
            statement.setInt(1,groupId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserGroupId(resultSet.getInt("user_groups"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Finding all users by group failed. Make sure that input data are proper");
            return null;
        }
    }

    public int checkIfUserExist(String userEmail){
        int userExist = 0;
        try(Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CHECK_IF_USER_EXIST);
            statement.setString(1,userEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
              userExist = resultSet.getInt("id");
            }
            return userExist;
        } catch ( SQLException e){
            System.out.println("Something goes wrong");
            return userExist;
        }
    }

    public boolean checkIfIdIsProper(int userId){
        boolean iDIsProper = false;
        try(Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CHECK_IF_ID_IS_PROPER);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                iDIsProper = true;
            }
            return iDIsProper;
        } catch ( SQLException e){
            System.out.println("Something goes wrong");
            return iDIsProper;
        }
    }


}
