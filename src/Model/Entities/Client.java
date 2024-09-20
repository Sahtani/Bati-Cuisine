package Model.Entities;

import Model.Interfaces.Identifiable;

import java.util.ArrayList;
import java.util.List;

public class Client implements Identifiable {
    private int id;
    private String name;
    private String address;
    private String phone;
    private boolean isProfessional;
    private List<Project> projects;

    // Constructor
    public Client(String name, String address, String phone, boolean isProfessional) {

        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
        this.projects = new ArrayList<>();
    }

    public Client(int id, String name, String address, String phone, boolean isProfessional) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isProfessional = isProfessional;
//        this.projects = new ArrayList<>();
    }

    public Client() {

    }


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isProfessional() {
        return isProfessional;
    }

    public void setProfessional(boolean professional) {
        isProfessional = professional;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Client{" + "name='" + name + '\'' + ", address='" + address + '\'' + ", phone='" + phone + '\'' + ", isProfessional=" + isProfessional + ", projects=" + projects + '}';
    }
}
