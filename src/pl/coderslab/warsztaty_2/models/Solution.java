package pl.coderslab.warsztaty_2.models;

public class Solution {

    private int id;
    private String created;
    private String updated;
    private String description;
    private int exerciseId;
    private int userId;

    public Solution() {}

    public Solution(String created, int exerciseId, int userId) {
        this.created = created;
        this.exerciseId = exerciseId;
        this.userId = userId;
    }

    public Solution(String created, String updated, String description, int exerciseId, int userId){
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exerciseId = exerciseId;
        this.userId = userId;
    }

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

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id:          ").append(this.id).append("\n");
        stringBuilder.append("created:     ").append(this.created).append("\n");
        stringBuilder.append("updated:     ").append(this.updated).append("\n");
        stringBuilder.append("description: ").append(this.description).append("\n");
        stringBuilder.append("exerciseId:  ").append(this.exerciseId).append("\n");
        stringBuilder.append("userId:      ").append(this.userId).append("\n");
        return stringBuilder.toString();
    }

}
