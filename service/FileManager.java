//Classe feita com auxilio de IA - Claude 4.0
package service;

import model.Course;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileManager {

    private static final String CSV_HEADER = "nome,tipo,semanas_letivas,aulas_semanais";
    private static final String CSV_SEPARATOR = ",";
    
    /**
     * Salva uma lista de cursos em um arquivo CSV
     * @param courses Lista de cursos para salvar
     * @param filePath Caminho do arquivo
     * @throws IOException Se houver erro na escrita do arquivo
     */

    public void saveCoursesToFile(ArrayList<Course> courses, String filePath) throws IOException {
        File file = new File(filePath);
        
        // Cria o arquivo se não existir
        if (!file.exists()) {
            file.createNewFile();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // Escreve o cabeçalho
            writer.println(CSV_HEADER);
            
            // Escreve cada curso
            for (Course course : courses) {
                String line = String.format("%s%s%s%s%d%s%d",
                    escapeCsvField(course.getName()),
                    CSV_SEPARATOR,
                    course.getType(),
                    CSV_SEPARATOR,
                    course.getAcademicWeeks(),
                    CSV_SEPARATOR,
                    course.getWeeklyClasses()
                );
                writer.println(line);
            }
        }
    }
    
    /**
     * Carrega cursos de um arquivo CSV
     * @param filePath Caminho do arquivo
     * @return Lista de cursos carregados
     * @throws IOException Se houver erro na leitura do arquivo
     */
    public ArrayList<Course> loadCoursesFromFile(String filePath) throws IOException {
        ArrayList<Course> courses = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            throw new IOException("Arquivo não encontrado: " + filePath);
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Pula o cabeçalho
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                try {
                    Course course = parseCsvLine(line);
                    courses.add(course);
                } catch (Exception e) {
                    System.err.println("Erro ao processar linha: " + line + " - " + e.getMessage());
                }
            }
        }
        
        return courses;
    }
    
    /**
     * Gera um relatório detalhado em arquivo texto
     * @param courses Lista de cursos
     * @param filePath Caminho do arquivo de relatório
     * @throws IOException Se houver erro na escrita do arquivo
     */
    public void generateDetailedReport(ArrayList<Course> courses, String filePath) throws IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            file.createNewFile();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // Cabeçalho do relatório
            writer.println("=".repeat(60));
            writer.println("    RELATÓRIO DE CARGA HORÁRIA ACADÊMICA");
            writer.println("=".repeat(60));
            writer.println("Data/Hora: " + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println("Total de Cursos: " + courses.size());
            writer.println();
            
            // Carga horária individual
            writer.println("CARGA HORÁRIA INDIVIDUAL:");
            writer.println("-".repeat(40));
            double totalWorkload = 0;
            
            for (Course course : courses) {
                double workload = LoadCalculator.calculateWorkLoad(course);
                totalWorkload += workload;
                
                writer.printf("%-25s | %-8s | %2d aulas/sem | %2d semanas | %7.2f h%n",
                    course.getName(),
                    course.getType(),
                    course.getWeeklyClasses(),
                    course.getAcademicWeeks(),
                    workload
                );
            }
            
            writer.println();
            
            // Carga horária por tipo
            writer.println("CARGA HORÁRIA POR TIPO:");
            writer.println("-".repeat(40));
            
            HashMap<String, Double> typeWorkload = calculateWorkloadByType(courses);
            for (String type : typeWorkload.keySet()) {
                writer.printf("%-10s: %7.2f horas%n", type, typeWorkload.get(type));
            }
            
            writer.println();
            writer.printf("CARGA HORÁRIA TOTAL: %.2f horas%n", totalWorkload);
            writer.println();
            
            // Estatísticas adicionais
            writer.println("ESTATÍSTICAS ADICIONAIS:");
            writer.println("-".repeat(40));
            writer.printf("Média de horas por curso: %.2f h%n", totalWorkload / courses.size());
            writer.printf("Tipos de curso diferentes: %d%n", typeWorkload.size());
            
            // Curso com maior carga horária
            Course maxCourse = courses.get(0);
            double maxWorkload = LoadCalculator.calculateWorkLoad(maxCourse);
            
            for (Course course : courses) {
                double workload = LoadCalculator.calculateWorkLoad(course);
                if (workload > maxWorkload) {
                    maxCourse = course;
                    maxWorkload = workload;
                }
            }
            
            writer.printf("Curso com maior carga: %s (%.2f h)%n", 
                maxCourse.getName(), maxWorkload);
            
            writer.println();
            writer.println("=".repeat(60));
            writer.println("Relatório gerado pelo Sistema de Checagem de Carga Horária");
        }
    }
    
    /**
     * Exporta cursos para CSV personalizado com formatação especial
     * @param courses Lista de cursos
     * @param filePath Caminho do arquivo
     * @throws IOException Se houver erro na escrita
     */
    public void exportToCustomCsv(ArrayList<Course> courses, String filePath) throws IOException {
        File file = new File(filePath);
        
        if (!file.exists()) {
            file.createNewFile();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            // Cabeçalho personalizado
            writer.println("nome_curso,tipo_curso,semanas_letivas,aulas_semanais,carga_horaria_total");
            
            for (Course course : courses) {
                double workload = LoadCalculator.calculateWorkLoad(course);
                String line = String.format("%s,%s,%d,%d,%.2f",
                    escapeCsvField(course.getName()),
                    course.getType(),
                    course.getAcademicWeeks(),
                    course.getWeeklyClasses(),
                    workload
                );
                writer.println(line);
            }
        }
    }
    
    /**
     * Cria backup dos dados com timestamp
     * @param courses Lista de cursos
     * @param backupDir Diretório de backup
     * @throws IOException Se houver erro na criação do backup
     */
    public void createBackup(ArrayList<Course> courses, String backupDir) throws IOException {
        File dir = new File(backupDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String timestamp = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String backupFile = backupDir + "/backup_" + timestamp + ".csv";
        
        saveCoursesToFile(courses, backupFile);
    }
    
    /**
     * Verifica se um arquivo existe
     * @param filePath Caminho do arquivo
     * @return true se o arquivo existe
     */
    public boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }
    
    /**
     * Obtém informações do arquivo
     * @param filePath Caminho do arquivo
     * @return String com informações do arquivo
     */
    public String getFileInfo(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "Arquivo não encontrado";
        }
        
        return String.format("Arquivo: %s%nTamanho: %d bytes%nÚltima modificação: %s",
            file.getName(),
            file.length(),
            new java.util.Date(file.lastModified()).toString()
        );
    }
    
    // Métodos auxiliares privados
    
    private Course parseCsvLine(String line) {
        String[] parts = line.split(CSV_SEPARATOR);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Linha CSV inválida: " + line);
        }
        
        String name = unescapeCsvField(parts[0]);
        String type = parts[1].trim();
        int academicWeeks = Integer.parseInt(parts[2].trim());
        int weeklyClasses = Integer.parseInt(parts[3].trim());
        
        return new Course(name, type, academicWeeks, weeklyClasses);
    }
    
    private String escapeCsvField(String field) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
    
    private String unescapeCsvField(String field) {
        if (field.startsWith("\"") && field.endsWith("\"")) {
            return field.substring(1, field.length() - 1).replace("\"\"", "\"");
        }
        return field;
    }
    
    private HashMap<String, Double> calculateWorkloadByType(ArrayList<Course> courses) {
        HashMap<String, Double> typeWorkload = new HashMap<>();
        
        for (Course course : courses) {
            String type = course.getType();
            double workload = LoadCalculator.calculateWorkLoad(course);
            
            typeWorkload.put(type, typeWorkload.getOrDefault(type, 0.0) + workload);
        }
        
        return typeWorkload;
    }
    
}
