package model;

public class Course {

    private int weeklyClasses;
    private int academicWeeks;
    private String name;
    private String type;

    public Course(String name, String type, int academicWeeks, int weeklyClasses){
        this.name = name;
        this.type = type;
        this.academicWeeks = academicWeeks;
        this.weeklyClasses = weeklyClasses;
    }

    public int getWeeklyClasses() {
        return weeklyClasses;
    }

    public void setWeeklyClasses(int weeklyClasses) {
        this.weeklyClasses = weeklyClasses;
    }

    public int getAcademicWeeks() {
        return academicWeeks;
    }

    public void setAcademicWeeks(int academicWeeks) {
        this.academicWeeks = academicWeeks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
