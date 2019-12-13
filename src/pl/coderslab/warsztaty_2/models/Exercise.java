package pl.coderslab.warsztaty_2.models;

public class Exercise {

    private int id;
    private String title;
    private String description;

    public Exercise() {}

    public Exercise(String title, String description){
        this.title = title;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id:          ").append(this.id).append("\n");
        stringBuilder.append("title:       ").append(this.title).append("\n");
        stringBuilder.append("description: ").append(this.description).append("\n");
        return stringBuilder.toString();
    }

}
