import java.util.ArrayList;
import java.util.Scanner;
import model.Course;
import service.LoadCalculator;
import service.FileManager;
import java.util.HashMap;
// Import the File class
import java.io.File;
// Import the IOException class to handle errors
import java.io.IOException;

public class Main {
    
    private static final String DATA_DIR = "data";
    private static final String COURSES_FILE = DATA_DIR + "/courses.csv";
    private static final String REPORTS_DIR = DATA_DIR + "/reports";
    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        ArrayList<Course> courseList = new ArrayList<>();
        FileManager fileManager = new FileManager();
        
        // Create necessary directories
        createDirectories();
        
        // Try to load existing courses
        System.out.println("=== Sistema de Checagem de Carga Horária ===\n");
        
        try {
            courseList = fileManager.loadCoursesFromFile(COURSES_FILE);
            if (!courseList.isEmpty()) {
                System.out.println("✓ Carregados " + courseList.size() + " cursos salvos anteriormente.");
                System.out.println("Gostaria de ver os cursos carregados? (s/n): ");
                String viewResponse = sc.nextLine();
                
                if (viewResponse.equalsIgnoreCase("s") || viewResponse.equalsIgnoreCase("sim")) {
                    displayLoadedCourses(courseList);
                }
            }
        } catch (IOException e) {
            System.out.println("ℹ️ Nenhum arquivo de cursos encontrado. Iniciando com lista vazia.");
        }
        
        Boolean newCourse = true;
        
        while(newCourse){

            System.out.println("\nGostaria de adicionar curso? (s/n): ");
            String response = sc.nextLine();

            newCourse = response.equalsIgnoreCase("s") || response.equalsIgnoreCase("sim");

            if(newCourse){
                
                System.out.println("Informe o nome do curso: ");
                String name = sc.nextLine();
                System.out.println("Informe o tipo do curso (MAT, ELO, MEC, etc.): ");
                String type = sc.nextLine().toUpperCase();
                System.out.println("Informe a quantidade de aulas na semana: ");
                int weeklyClasses = sc.nextInt();
                //prevenir sobra de dados int
                sc.nextLine();
                System.out.println("Informe a quantidade de semanas letivas: ");
                int academicWeeks = sc.nextInt();
                //prevenir sobra de dados int
                sc.nextLine();
                
                Course courses = new Course(name, type, academicWeeks, weeklyClasses);
                courseList.add(courses);
                
                System.out.println("✓ Curso adicionado com sucesso!");

            }
        }
        
        // Display results
        displayResults(courseList);
        
        // Save courses to file
        try {
            fileManager.saveCoursesToFile(courseList, COURSES_FILE);
            System.out.println("\n✓ Cursos salvos em: " + COURSES_FILE);
        } catch (IOException e) {
            System.out.println("\n❌ Erro ao salvar cursos: " + e.getMessage());
        }
        
        // Generate report
        System.out.println("\nGostaria de gerar um relatório detalhado? (s/n): ");
        String reportResponse = sc.nextLine();
        
        if (reportResponse.equalsIgnoreCase("s") || reportResponse.equalsIgnoreCase("sim")) {
            generateReport(courseList, fileManager);
        }
        
        // Import/Export options
        showImportExportOptions(sc, courseList, fileManager);
        
        sc.close();
    }
    
    private static void createDirectories() {
        try {
            File dataDir = new File(DATA_DIR);
            File reportsDir = new File(REPORTS_DIR);
            
            if (!dataDir.exists()) {
                dataDir.mkdir();
                System.out.println("✓ Diretório de dados criado: " + DATA_DIR);
            }
            
            if (!reportsDir.exists()) {
                reportsDir.mkdir();
                System.out.println("✓ Diretório de relatórios criado: " + REPORTS_DIR);
            }
        } catch (Exception e) {
            System.out.println("⚠️ Aviso: Não foi possível criar diretórios: " + e.getMessage());
        }
    }
    
    private static void displayLoadedCourses(ArrayList<Course> courseList) {
        System.out.println("\n=== Cursos Carregados ===");
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            System.out.println((i + 1) + ". " + course.getName() + " (" + course.getType() + 
                             ") - " + course.getWeeklyClasses() + " aulas/semana, " + 
                             course.getAcademicWeeks() + " semanas");
        }
    }
    
    private static void displayResults(ArrayList<Course> courseList) {
        if (courseList.isEmpty()) {
            System.out.println("\n⚠️ Nenhum curso foi adicionado.");
            return;
        }
        
        System.out.println("\n=== Carga Horária Individual ===");
        for(Course course : courseList) {
            double workload = LoadCalculator.calculateWorkLoad(course);
            System.out.printf("%s: %.2f horas\n", course.getName(), workload);
        }

        System.out.println("\n=== Carga Horária por Tipos ===");
        HashMap<String, Double> typeWorkLoad = new HashMap<>();
        for(Course course : courseList){
            String type = course.getType();
            double workLoad = LoadCalculator.calculateWorkLoad(course);

            if(typeWorkLoad.containsKey(type)){
                typeWorkLoad.put(type, typeWorkLoad.get(type) + workLoad);
            }else{
                typeWorkLoad.put(type, workLoad);
            }
        }

        for(String type : typeWorkLoad.keySet()){
            System.out.printf("%s: %.2f horas\n", type, typeWorkLoad.get(type));
        }

        double totalWorkLoad = 0;
        System.out.println("\n=== Carga Horária Total do Semestre ===");
        for(Course course : courseList){
            totalWorkLoad += LoadCalculator.calculateWorkLoad(course);
        }
        System.out.printf("Carga Horária Total: %.2f horas\n", totalWorkLoad);
    }
    
    private static void generateReport(ArrayList<Course> courseList, FileManager fileManager) {
        try {
            String timestamp = java.time.LocalDateTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
            );
            String reportFile = REPORTS_DIR + "/relatorio_" + timestamp + ".txt";
            
            fileManager.generateDetailedReport(courseList, reportFile);
            System.out.println("✓ Relatório gerado: " + reportFile);
        } catch (IOException e) {
            System.out.println("❌ Erro ao gerar relatório: " + e.getMessage());
        }
    }
    
    private static void showImportExportOptions(Scanner sc, ArrayList<Course> courseList, FileManager fileManager) {
        System.out.println("\n=== Opções de Import/Export ===");
        System.out.println("1. Exportar para CSV personalizado");
        System.out.println("2. Importar de arquivo CSV");
        System.out.println("3. Criar backup dos dados");
        System.out.println("4. Sair");
        
        System.out.print("Escolha uma opção (1-4): ");
        String option = sc.nextLine();
        
        try {
            switch (option) {
                case "1":
                    System.out.print("Nome do arquivo para exportar (sem extensão): ");
                    String exportName = sc.nextLine();
                    String exportFile = DATA_DIR + "/" + exportName + ".csv";
                    fileManager.exportToCustomCsv(courseList, exportFile);
                    System.out.println("✓ Dados exportados para: " + exportFile);
                    break;
                    
                case "2":
                    System.out.print("Caminho do arquivo CSV para importar: ");
                    String importFile = sc.nextLine();
                    ArrayList<Course> importedCourses = fileManager.loadCoursesFromFile(importFile);
                    courseList.addAll(importedCourses);
                    System.out.println("✓ " + importedCourses.size() + " cursos importados!");
                    break;
                    
                case "3":
                    String backupFile = DATA_DIR + "/backup_courses_" + 
                        java.time.LocalDate.now().toString() + ".csv";
                    fileManager.saveCoursesToFile(courseList, backupFile);
                    System.out.println("✓ Backup criado: " + backupFile);
                    break;
                    
                case "4":
                    System.out.println("Encerrando programa...");
                    break;
                    
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (IOException e) {
            System.out.println("❌ Erro na operação: " + e.getMessage());
        }
    }
}