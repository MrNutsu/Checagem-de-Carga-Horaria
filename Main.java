import java.util.ArrayList;
import java.util.Scanner;
import model.Course;
import service.LoadCalculator;
//permite calcular a carga horaria pelo tipo
import java.util.HashMap;

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
        
    System.out.println("\n=== Carga Horária Individual ===");
    for(Course course : courseList) {
        double workload = LoadCalculator.calculateWorkLoad(course);
        System.out.println(course.getName() + ": " + workload + " hours\n");
    }

    int typeWorkLoad = 0;
    System.out.println("\n=== Carga Horária por Tipos ===");
    for(Course course : courseList){

        typeWorkLoad += LoadCalculator.calculateWorkLoad(course.getType());
        
    }

    int totalWorkLoad = 0;
    System.out.println("\n=== Carga Horária Total do Semestre ===");
    for(Course course : courseList){

       totalWorkLoad += LoadCalculator.calculateWorkLoad(course);
       
    }
    System.out.println("Carga Horária Total: " + totalWorkLoad + "horas");

    }
}
