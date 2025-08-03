package service;
import model.Course;

public class LoadCalculator {

    public static double calculateWorkLoad(Course course){
        return course.getWeeklyClasses() * 0.8333 * course.getAcademicWeeks();

    }

    public static double calculateWorkLoad(int weeklyClasses, int academicWeeks){
        return weeklyClasses * 0.8333 * academicWeeks;
    }
}
