package Model.Entities;

public abstract class Component {
    protected int id;
    protected String name;
    protected String componentType;
    protected double vatRate;
    protected Project project ;

    // Constructor
    public Component(String name,String componentType, double vatRate ) {

        this.name = name;
        this.componentType = componentType;
        this.vatRate = vatRate;

    }

    public Component(){

    }


    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }
    public Project getProject(){
        return project ;
    }
    public void setProject(Project project){
        this.project = project ;
    }
}