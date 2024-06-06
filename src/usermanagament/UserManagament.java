/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usermanagament;

import java.util.Scanner;

/**
 *
 * @author trann
 */
public class UserManagament {
    
    private UserList userList = new UserList();
    private Scanner scanner = new Scanner(System.in);
    
    public UserManagament() {

        while (true) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());
            
            do {
                switch (choice) {
                    case 1:
                        userList.add();
                        break;
                    case 2:
                        userList.check();
                        break;
                    case 3:
                        userList.search();
                        break;
                    case 4:
                        userList.update();
                        break;
                    case 5:
                        userList.saveData();
                        break;
                    case 6:
                        userList.show();
                        break;
                    case 7:
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (askToContinue());
        }
    }
    
    private void showMenu() {
        System.out.println("1. Create user account");
        System.out.println("2. Check exist user");
        System.out.println("3. Search user information by name");
        System.out.println("4. Update user");
        System.out.println("5. Save to file");
        System.out.println("6. Print all users");
        System.out.println("7. Quit");
        System.out.print("Enter your choice: ");
    }
    
    private boolean askToContinue() {
        String input = null;
        do {
            System.out.print("Do you want to go back to menu (Y/N):");
            input = scanner.nextLine();

            if(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                System.out.println("Wrong syntax");
            }
        } while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));
        
        if (input.equalsIgnoreCase("y")){
             return false;
        } else {
            return true;
        }
    }
}
