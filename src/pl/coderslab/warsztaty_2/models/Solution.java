package pl.coderslab.warsztaty_2.models;

public class Solution {

    private int id;
    private String created;
    private String updated;
    private String description;
    private int exerciseId;
    private int userId;

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
