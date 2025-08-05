import java.util.ArrayList;
import java.util.Scanner;
import model.Course;

public class Main {
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        ArrayList<Course> courseList = new ArrayList<>();
        
        Boolean newCourse = true;
        
        while(newCourse){

            System.out.println("Gostaria de adicionar curso? (s/n): ");
            String response = sc.nextLine();

            newCourse = response.equalsIgnoreCase("s") || response.equalsIgnoreCase("sim") || response.equalsIgnoreCase("Sim");

            if(newCourse){
                
                System.out.println("Informe o nome do curso: ");
                String name = sc.nextLine();
                System.out.println("Informe o tipo do curso (MAT, ELO, MEC, etc.): ");
                String type = sc.nextLine();
                System.out.println("Informe a quantidade de aulas na semana: ");
                int weeklyClasses = sc.nextInt();
                //previnir soibra de dados int
                sc.nextLine();
                System.out.println("Informe a quantidade de semanas letivas: ");
                int academicWeeks = sc.nextInt();
                //previnir sobra de dados int
                sc.nextLine();
                
                Course courses = new Course(name, type, academicWeeks, weeklyClasses);
                courseList.add(courses);

            }

            
        }
        
        
    }
}
