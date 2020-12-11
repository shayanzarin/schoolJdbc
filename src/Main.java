import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void menu(){
        System.out.println("Menu:\n" +
                "1.show student table\n" +
                "2.show teacher table\n" +
                "3.add new student\n" +
                "4.add new teacher\n" +
                "5.edit student\n" +
                "6.edit teacher\n" +
                "7.delete student\n" +
                "8.delete teacher\n" +
                "9.show student's teacher\n" +
                "10.show menu\n" +
                "0.exit"
        );
        }

        public static void start(){
            Scanner scanner=new Scanner(System.in);
            int n = 1;
            while (n != 0){
                menu();
                System.out.println("Enter the number: ");
                try {
                    n=scanner.nextInt();
                }catch (InputMismatchException inputMismatchException){
                    System.out.println("wrong input!");
                    start();
                }
                switchMethod(n);
            }
        }

        public static void switchMethod(int n){
        Services services=new Services();
            switch (n){
                case 1:services.printTable(services.loadAllStudent());
                    break;
                case 2:services.printTable(services.loadAllTeacher());
                    break;
                case 3:services.saveStudent();
                    break;
                case 4:services.saveTeacher();
                    break;
                case 5:services.editStudent();
                    break;
                case 6:services.editTeacher();
                    break;
                case 7:services.deleteStudent();
                    break;
                case 8:services.deleteTeacher();
                    break;
                case 9:services.printStudentForTeacher();
                    break;
                case 10:menu();
                    break;
                case 0: System.out.println("good bye");
                    break;
                default: System.out.println("Try again!");
            }
        }

    public static void main(String[] args)  {
        start();
    }



}
