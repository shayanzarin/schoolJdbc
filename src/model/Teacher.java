package model;

public class Teacher {
    private String fname;
    private String lname;
    private int id;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                " ID=" + id +'\'' +
                "First name='" + fname + '\'' +
                ", Last name='" + lname +
                '}';
    }
}
