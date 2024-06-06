/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usermanagament;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author trann
 */
public class UserList {
    
    private static final String FILE_NAME = "user.dat";
    private ArrayList<User> users = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    
    public void add() {
        String username = usernameValidateInput(null);
        String password = passwordValidateInput(null);
        confirmPassword(password);

        System.out.print("Enter user's first name: ");
        String firstname = scanner.nextLine();

        System.out.print("Enter user's last name: ");
        String lastname = scanner.nextLine();

        String phone = phoneNumberValidateInput(null);
        String email = emailValidateInput(null);

        users.add(new User(username, firstname, lastname, password, phone, email));
    }
    
    public void check() {
        ArrayList<User> temp = new ArrayList<User>(users);
        this.loadData();

        System.out.print("Enter username to check: ");
        String username = scanner.nextLine();

        if (isExist(username)) {
            System.out.println("Exist User");
        } else {
            System.out.println("No User Found!");
        }    
        
        users.removeAll(users);
        users = new ArrayList<User>(temp);
    }
    
    public void update() {
        String username = null;
        do {
            System.out.print("Enter username to update: ");
            username = scanner.nextLine();

            if(!isExist(username)) {
                System.out.print("Username does not exist");
            }
        } while (!isExist(username));

        int index = -1;
        for(User user : users) {
            if (username.equals(user.getUsername())) {
                index = users.indexOf(user);
                break;
            }
        }

        username = usernameValidateInput(users.get(index).getUsername());
        String password = passwordValidateInput(users.get(index).getPassword());
        confirmPassword(password);

        System.out.print("Enter user's first name: ");
        String firstname = scanner.nextLine().equals('\n') ? users.get(index).getFirstname() : scanner.nextLine();

        System.out.print("Enter user's last name: ");
        String lastname = scanner.nextLine().equals('\n') ? users.get(index).getLastname() : scanner.nextLine();

        String phone = phoneNumberValidateInput(users.get(index).getPhone());
        String email = emailValidateInput(users.get(index).getEmail());

        users.add(new User(username, firstname, lastname, password, phone, email));
    }
    
    public void delete() {
        String username = null;
        do {
            System.out.print("Enter username to update: ");
            username = scanner.nextLine();

            if(!isExist(username)) {
                System.out.print("Username does not exist");
            }
        } while (!isExist(username));

        boolean isSuccess = false;
        for (User user : users) {
            if (username.equalsIgnoreCase(user.getUsername())) {
                users.remove(user);
                isSuccess = true;
            }
        }

        if(isSuccess) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }
    }
    
    public void search() {
        System.out.print("Enter name to search: ");
        String searchString = scanner.nextLine();
        ArrayList<User> result = new ArrayList<User>();
        for (User user : users) {
            if (user.getFirstname().contains(searchString) || user.getLastname().contains(searchString)) {
                result.add(user);
            }
        }
        if (result.isEmpty()) {
            System.out.println("Have no any user");
        } else {
            result.sort(Comparator.comparing(u -> u.getFirstname()));
            for (User user : result) {
                System.out.println(user);
            }
        }
    }
    
    public void show() {
        ArrayList<User> temp = new ArrayList<User>(users);
        this.loadData();
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.sort(Comparator.comparing(user -> user.getFirstname()));
            for (User user : users) {
                System.out.println(user);
            }
        }
        
        users.removeAll(users);
        users = new ArrayList<User>(temp);
    }
    
    public void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 6) {
                    String username = parts[0];
                    String firstName = parts[1];
                    String lastName = parts[2];
                    String password = parts[3];
                    String phone = parts[4];
                    String email = parts[5];

                    users.add(new User(username, firstName, lastName, password, phone, email));
                } 
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }
            
    public void saveData() {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine(); // Move to the next line for the next user
            }
            System.out.println("Users written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
        
        users.removeAll(users);
    }
    
    
  
    private boolean isExist(String username) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                return true;
            } 
        }
        return false;
    }
            
    private String usernameValidateInput(String data) {
        String input = null;
        
        do {
            System.out.print("Enter Username: ");
            input = scanner.nextLine();   
            if (input.equals('\n')) {
                input = data;
                break;
            }

            if (input.length() < 5) {
                System.out.println("Username must be at least 5 character");
            }

            if (isExist(input)) {
                System.out.println("Username has already exist");
            }
        } while (input.length() < 5 || isExist(input));

        return input;
    }
    
    private String passwordValidateInput(String data) {
        String input = null;
         
        do {
            System.out.print("Enter password: ");
            input = scanner.nextLine();
            if (input.equals('\n')) {
                input = data;
                break;
            }

            if (input.length() < 6) {
                System.out.println("Password must be at least 6 character");
            }

            if (input.contains(" ")) {
                System.out.println("Password must contain no space");
            }
        } while (input.length() < 6 || input.contains(" ")); 
        
        return input;
    }
    
    private void confirmPassword(String password) {
        String confirm = null;
        do {
            System.out.print("Comfirm password: ");
            confirm = scanner.nextLine();
            
            if(!confirm.equals(password)) {
                System.out.println("Wrong password");
            }
        
        } while (!confirm.equals(password));
        
    }
    
    private String phoneNumberValidateInput(String data) {
        String input = data;
        boolean checked ;

        do {
            checked = true ;
            System.out.print("Enter phone number: ");
            input = scanner.nextLine();
            if (input.equals('\n')) {
                    return data;
            }

            if (input.length() != 10) {
                System.out.println("Phone number must be 10 digit");
            }
            for (char c : input.toCharArray()) {
                if (!Character.isDigit(c)) {
                    System.out.println("Phone number must contain numbers");
                    checked = false;
                    break;
                }
            }

        } while (input.length() != 10 || !checked);
        
        return input;
    }
    
    private String emailValidateInput(String data) {
        String input = data;
              
        do {
           System.out.print("Enter email: ");
           input = scanner.nextLine();
           if (input.equals('\n')) {
                input = data;
                break;
           }

           if (!input.contains("@gmail.com")) {
               System.out.println("Email must be *@gmail.com");
           }

           if (input.length() <= 10) {
               System.out.println("Email must have a name");
           }

        } while (!input.contains("@gmail.com") || input.length() <= 10);
        
        return input;
    }
    
}
