package entities;

import java.io.Serializable;

public class Vacancy implements Serializable {

    private String id;
    private String surname;
    private String name;
    private String post;
    private String salary;

    public Vacancy(String id, String surname, String name, String post, String salary) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.post = post;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPost() {
        return post;
    }

    public String getSalary() {
        return salary;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
