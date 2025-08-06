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

    System.out.println("\n=== Carga Horária por Tipos ===");
    //Utilizado para amarzenar valores em pares, neste caso uma String e um double
    HashMap<String, Double> typeWorkLoad = new HashMap<>();
    for(Course course : courseList){
        
        //amarzena o tipo do curso atual no loop
        String type = course.getType();
        //calcula a carga de treabalho para o tipo atual
        double workLoad = LoadCalculator.calculateWorkLoad(course);

        //checa se o tipo atual ja passou pelo loop, true se sim
        if(typeWorkLoad.containsKey(type)){
            //se ja visto adiciona o total de horas para o hashmap
            typeWorkLoad.put(type, typeWorkLoad.get(type) + workLoad);
        }else{
            // se nao, cria um novo
            typeWorkLoad.put(type, workLoad);
        }

    }

    for(String type : typeWorkLoad.keySet()){
        System.out.println(type + ": " + typeWorkLoad + " horas");
    }

    int totalWorkLoad = 0;
    System.out.println("\n=== Carga Horária Total do Semestre ===");
    for(Course course : courseList){

       totalWorkLoad += LoadCalculator.calculateWorkLoad(course);
       
    }
    System.out.println("Carga Horária Total: " + totalWorkLoad + "horas");

    }
}
