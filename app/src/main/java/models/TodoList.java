package models;

public class TodoList {
    private Integer id;
    private String name;
    private String created;

    public TodoList(Integer id, String name, String created) {
        this.id = id;
        this.name = name;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return name;
    }
}
