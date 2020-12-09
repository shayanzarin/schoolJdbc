
import model.Student;
import model.Teacher;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Services {

    public Student buildStudent(){
        Scanner scanner=new Scanner(System.in);
        Student student=new Student();
        System.out.println("Enter student name");
        student.setFname(scanner.next());
        System.out.println("Enter student last name");
        student.setLname(scanner.next());
        System.out.println("Enter student ID");
        student.setId(scanner.nextInt());

        return student;
    }

    public Teacher buildTeacher(){
        Scanner scanner=new Scanner(System.in);
        Teacher teacher=new Teacher();
        System.out.println("Enter teacher name");
        teacher.setFname(scanner.next());
        System.out.println("Enter teacher last name");
        teacher.setLname(scanner.next());
        System.out.println("Enter teacher ID");
        teacher.setId(scanner.nextInt());

        return teacher;
    }

    public void saveStudent(){
        Student newStudent=buildStudent();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into student(fname, lname, id) values(?,?,?)")) {
            preparedStatement.setString(1,newStudent.getFname());
            preparedStatement.setString(2,newStudent.getLname());
            preparedStatement.setInt(3,newStudent.getId());
            preparedStatement.executeUpdate();
            howManyTeachers(newStudent);
            } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

public void printStudentForTeacher() {
    List<Teacher> teachers=loadStudentForTeacher();
    for (Teacher t: teachers){
        System.out.println("student name : "+t.getFname());
    }
}

    public void howManyTeachers(Student student){
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("Do you want add teacher? 1.yes / 2.no");
            if (scanner.nextInt() == 2)
                break;
            else
                saveTeacherId(student);
        }
        System.out.println("done!");
    }

    public void saveTeacherId(Student student){
        Scanner scanner=new Scanner(System.in);
        printTable(loadAllTeacher());

        System.out.println("Enter your teacher id");
        int teacherId=scanner.nextInt();

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into s_t(s_id, t_id) values(?,?)")) {
            preparedStatement.setInt(1,student.getId());
            preparedStatement.setInt(2,teacherId);
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void saveTeacher(){
        Teacher teacher=buildTeacher();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into teacher(fname, lname) values(?,?)")) {
            preparedStatement.setString(1,teacher.getFname());
            preparedStatement.setString(2,teacher.getLname());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
//    DELETE FROM `school`.`student` WHERE (`id` = '7');
    public void deleteStudent(){
        Scanner scanner=new Scanner(System.in);
        printTable(loadAllStudent());
        System.out.println("Enter students id: ");
        Student student=new Student();
        student.setId(scanner.nextInt());
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `school`.`student` WHERE (`id` = ?)")) {
            preparedStatement.setInt(1,student.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        deleteStudentFromST(student);
    }

    public void deleteStudentFromST(Student student){
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `school`.`s_t` WHERE (`s_id` = ?)")) {
            preparedStatement.setInt(1,student.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println("done!");
    }

    public void deleteTeacher(){
        Scanner scanner=new Scanner(System.in);
        printTable(loadAllTeacher());
        System.out.println("Enter teacher id: ");
        Teacher teacher=new Teacher();
        teacher.setId(scanner.nextInt());
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `school`.`teacher` WHERE (`id` = ?)")) {
            preparedStatement.setInt(1,teacher.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        deleteTeacherFromST(teacher);
    }

    public void deleteTeacherFromST(Teacher teacher){
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `school`.`s_t` WHERE (`t_id` = ?)")) {
            preparedStatement.setInt(1,teacher.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println("done!");
    }

//    UPDATE `school`.`student` SET `fname` = '11', `lname` = 'hatami2' WHERE (`id` = '7');
    public void editStudent(){
        printTable(loadAllStudent());
        Student student=buildStudent();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE `school`.`student` SET `fname` = ?, `lname` = ? WHERE (`id` = ?)")) {
            preparedStatement.setString(1,student.getFname());
            preparedStatement.setString(2,student.getLname());
            preparedStatement.setInt(3,student.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void editTeacher(){
        printTable(loadAllTeacher());
        Teacher teacher=buildTeacher();
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE `school`.`teacher` SET `fname` = ?, `lname` = ? WHERE (`id` = ?)")) {
            preparedStatement.setString(1,teacher.getFname());
            preparedStatement.setString(2,teacher.getLname());
            preparedStatement.setInt(3,teacher.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }




    public List<Student> loadAllStudent() {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from student");
            ResultSet resultSet = preparedStatement.executeQuery()){
            List<Student> students = new ArrayList<>();
            while (resultSet.next()){
                Student student=new Student();
                student.setId(resultSet.getInt("id"));
                student.setFname(resultSet.getString("fname"));
                student.setLname(resultSet.getString("lname"));
                students.add(student);
            }
            return students;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }

    public List<Teacher>loadAllTeacher() {
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from teacher");
            ResultSet resultSet = preparedStatement.executeQuery()){
            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()){
                Teacher teacher=new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFname(resultSet.getString("fname"));
                teacher.setLname(resultSet.getString("lname"));
                teachers.add(teacher);
            }
            return teachers;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }

    public List<Teacher> loadStudentForTeacher() {
        printTable(loadAllTeacher());
        System.out.println("Enter teacher id: ");
        Scanner scanner=new Scanner(System.in);
        Teacher teacher1=new Teacher();
        teacher1.setId(scanner.nextInt());
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("    select s.fname as studentName, t.fname as teacherName from student s join s_t st on s.id = st.s_id join teacher t on t.id=st.t_id where t.id = ?")){
            preparedStatement.setInt(1,teacher1.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()){
                Teacher teacher=new Teacher();
                teacher.setFname(resultSet.getString("studentName"));
                teacher.setLname(resultSet.getString("teacherName"));
                teachers.add(teacher);
            }
            return teachers;
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }


//    select s.fname as studentName, t.fname as teacherName from student s join s_t st on s.id = st.s_id join teacher t on t.id=st.t_id where t.id = ?

    public <T> void printTable(List<T> tList){
        for (T t : tList) {
            System.out.println(t.toString());
        }
    }



}
//    Scanner scanner = new Scanner(System.in);
//    printTable(loadAllTeacher());
//        System.out.println("Enter teacher id: ");
//        Teacher teacher = new Teacher();
//        teacher.setId(scanner.nextInt());
//        try (Connection connection = ConnectionFactory.getConnection();
//        PreparedStatement preparedStatement = connection.prepareStatement("select s.fname as studentName, t.fname as teacherName from student s join s_t st on s.id = st.s_id join teacher t on t.id=st.t_id where t.id = ?")){
//        preparedStatement.setInt(1, teacher.getId());
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        List<Teacher> teachers = new ArrayList<>();
//        while (resultSet.next()) {
//        Teacher teacher1 = new Teacher();
//        teacher.setFname(resultSet.getString("studentName"));
//        teacher.setLname(resultSet.getString("teacherName"));
//        teachers.add(teacher1);
//        }
//        return teachers;
//
//        } catch (SQLException sqlException) {
//        sqlException.printStackTrace();
//
//        }
//        return null;