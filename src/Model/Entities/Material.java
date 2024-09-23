package Model.Entities;

public class Material extends Component{

    private double unitCost;
    private double quantity;
    private double transportCost;
    private double qualityCoefficient;


    // Constructor
    public Material(String name, double vatRate, Project project, double unitCost, double quantity, double transportCost, double qualityCoefficient) {
        super(name, "Material", vatRate, project);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    // default constructor
    public Material(){

    }

    // Getters and Setters
    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTransportCost() {
        return transportCost;
    }



    public void setTransportCost(double transportCost) {
        this.transportCost = transportCost;
    }

    public double getQualityCoefficient() {
        return qualityCoefficient;
    }


    public void setQualityCoefficient(double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    @Override
    public String toString() {
        return "Material{" +
                "unitCost=" + unitCost +
                ", quantity=" + quantity +
                ", transportCost=" + transportCost +
                ", qualityCoefficient=" + qualityCoefficient +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", componentType='" + componentType + '\'' +
                ", vatRate=" + vatRate +
                ", project=" + project +
                '}';
    }
}