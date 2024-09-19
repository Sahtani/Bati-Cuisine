package Model.Entities;

public class Labor extends Component {

    private double hourlyRate;
    private double hoursWorked;
    private double workerProductivity;

    // Constructor
    public Labor(String name, double hourlyRate, double hoursWorked, double workerProductivity, double vatRate) {
        super(name, "Labor", vatRate);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.workerProductivity = workerProductivity;
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
}