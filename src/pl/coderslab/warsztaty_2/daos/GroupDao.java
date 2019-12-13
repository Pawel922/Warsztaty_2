package pl.coderslab.warsztaty_2.daos;

import pl.coderslab.warsztaty_2.DBUtil;
import pl.coderslab.warsztaty_2.models.Group;
import java.sql.*;
import java.util.Arrays;

public class GroupDao {

    private static final String CREATE_USER_GROUPS_QUERY =
            "INSERT INTO user_groups(name) VALUES (?)";
    private static final String READ_USER_GROUPS_QUERY =
            "SELECT * FROM user_groups where id = ?";
    private static final String UPDATE_USER_GROUPS_QUERY =
            "UPDATE user_groups SET name = ? where id = ?";
    private static final String DELETE_USER_GROUPS_QUERY =
            "DELETE FROM user_groups WHERE id = ?";
    private static final String FIND_ALL_USER_GROUPS_QUERY =
            "SELECT * FROM user_groups";

    public Group create(Group group) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_GROUPS_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                group.setId(resultSet.getInt(1));
            }
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Group read(int groupId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_USER_GROUPS_QUERY);
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                return group;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Group group) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_GROUPS_QUERY);
            statement.setString(1, group.getName());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int groupId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_GROUPS_QUERY);
            statement.setInt(1, groupId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Group[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            Group[] groups = new Group[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_GROUPS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("id"));
                group.setName(resultSet.getString("name"));
                groups = addToArray(group, groups);
            }
            return groups;
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }

    private Group[] addToArray(Group g, Group[] groups) {
        Group[] tmpGroups = Arrays.copyOf(groups, groups.length + 1);
        tmpGroups[groups.length - 1] = g;
        return tmpGroups;
    }
}
