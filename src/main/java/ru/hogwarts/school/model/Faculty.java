package ru.hogwarts.school.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyid;
    private String name;
    private String color;

    public long getFacultyid() {
        return facultyid;
    }

    public void setFacultyid(long id) {
        this.facultyid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return Objects.equals(getName(), faculty.getName()) && Objects.equals(getColor(), faculty.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getColor());
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + facultyid +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
