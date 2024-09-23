package Model.Entities;

public class Labor extends Component {

    private double hourlyRate;
    private double hoursWorked;
    private double workerProductivity;

    // Constructor
    public Labor(String name, double vatRate, Project project, double hourlyRate, double hoursWorked, double workerProductivity) {
        super(name, "Labor", vatRate, project);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.workerProductivity = workerProductivity;
    }

    public Labor() {

    }

    // Getters and Setters
    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }

    @Override
    public String toString() {
        return "Labor{" +
                "hourlyRate=" + hourlyRate +
                ", hoursWorked=" + hoursWorked +
                ", workerProductivity=" + workerProductivity +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", componentType='" + componentType + '\'' +
                ", vatRate=" + vatRate +
                ", project=" + project +
                '}';
    }
}