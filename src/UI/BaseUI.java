package UI;

import java.sql.SQLException;
import java.util.Scanner;

public class BaseUI {

    private ClientUI clientUI;
   // private ProjectUI projectUI;
    private Scanner scanner = new Scanner(System.in);

    public BaseUI(ClientUI clientUI) {
        this.clientUI = clientUI;
      //  this.projectUI = projectUI;
    }

    public void displayMainMenu() throws SQLException {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Manage Clients");
            System.out.println("2. Manage Projects");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    clientUI.displayClientMenu();
                    break;
                case 2:
                   // projectUI.displayProjectMenu();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }}
}
