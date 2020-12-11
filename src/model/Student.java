package model;

public class Student extends Person {
    @Override
    public String toString() {
        return "Student{" +
                "ID=" + getId() +
                ", First name='" + getFname() + '\'' +
                ", Last name='" + getLname() + '\'' +
                '}';
    }
}
