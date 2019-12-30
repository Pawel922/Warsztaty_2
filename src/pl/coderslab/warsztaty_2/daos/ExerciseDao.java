package pl.coderslab.warsztaty_2.daos;

import pl.coderslab.warsztaty_2.DBUtil;
import pl.coderslab.warsztaty_2.models.Exercise;
import java.sql.*;
import java.util.Arrays;

public class ExerciseDao {

    private static final String CREATE_EXERCISE_QUERY =
            "INSERT INTO exercises(title, description) VALUES (?,?)";
    private static final String READ_EXERCISE_QUERY =
            "SELECT * FROM exercises where id = ?";
    private static final String UPDATE_EXERCISE_QUERY =
            "UPDATE exercises SET title = ?, description = ? where id = ?";
    private static final String DELETE_EXERCISE_QUERY =
            "DELETE FROM exercises WHERE id = ?";
    private static final String FIND_ALL_EXERCISE_QUERY =
            "SELECT * FROM exercises";
    private static final String FIND_ALL_INCOMPLETED_EXERCISE_QUERY =
            "SELECT DISTINCT exercises.id, exercises.title, exercises.description FROM exercises\n" +
                    "INNER JOIN solutions ON exercises.id = solutions.exercise_id\n" +
                    "WHERE user_id <> ?\n" +
                    "AND exercises.id NOT IN " +
                    "((SELECT DISTINCT exercises.id FROM exercises " +
                    "INNER JOIN solutions ON exercises.id = solutions.exercise_id\n" +
                    "WHERE user_id = ?)) " +
                    "ORDER BY exercises.id";
    private static final String CHECK_IF_ID_IS_PROPER =
            "SELECT exercises.id AS id FROM exercises WHERE id = ?";

    public Exercise create(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_EXERCISE_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                exercise.setId(resultSet.getInt(1));
            }
            return exercise;
        } catch (SQLException e) {
            System.out.println("Adding new exercise failed. Make sure that input data are proper");
            return null;
        }
    }

    public Exercise read(int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                return exercise;
            }
        } catch (SQLException e) {
            System.out.println("Exercise with given id does not exist");
        }
        return null;
    }

    public void update(Exercise exercise) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_EXERCISE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3,exercise.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Updating failed. Make sure that input data are proper");
        }
    }

    public void delete(int exerciseId) {
        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_EXERCISE_QUERY);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Deleting failed. Make sure that input data are proper");
        }
    }

    public Exercise[] findAll() {
        try (Connection conn = DBUtil.getConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_EXERCISE_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            System.out.println("Finding all exercises failed. Make sure that input data are proper");
            return null;
        }
    }

    private Exercise[] addToArray(Exercise e, Exercise[] exercises) {
        Exercise[] tmpExercises = Arrays.copyOf(exercises, exercises.length + 1);
        tmpExercises[tmpExercises.length - 1] = e;
        return tmpExercises;
    }

    public Exercise[] findAllIncompleted(int userId){
        try (Connection conn = DBUtil.getConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_INCOMPLETED_EXERCISE_QUERY);
            statement.setInt(1,userId);
            statement.setInt(2,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(resultSet.getInt("id"));
                exercise.setTitle(resultSet.getString("title"));
                exercise.setDescription(resultSet.getString("description"));
                exercises = addToArray(exercise, exercises);
            }
            return exercises;
        } catch (SQLException e) {
            System.out.println("Finding all incompleted exercises failed. Make sure that input data are proper");
            return null;
        }
    }

    public boolean checkIfIdIsProper(int exerciseId){
        boolean iDIsProper = false;
        try(Connection conn = DBUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CHECK_IF_ID_IS_PROPER);
            statement.setInt(1,exerciseId);
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
