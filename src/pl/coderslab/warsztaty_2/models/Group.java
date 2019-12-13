package pl.coderslab.warsztaty_2.models;

public class Group {

    private int id;
    private String name;

    public Group() {}

    public Group(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id:   ").append(this.id).append("\n");
        stringBuilder.append("name: ").append(this.name).append("\n");
        return stringBuilder.toString();
    }

}
