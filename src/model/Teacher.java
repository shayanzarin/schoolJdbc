package model;

public class Teacher extends Person {
    @Override
    public String toString() {
        return "Teacher{" +
                " ID=" + getId() +'\'' +
                "First name='" + getFname() + '\'' +
                ", Last name='" + getLname() +
                '}';
    }
}
