import java.io.IOException;
import java.util.Scanner;

public class PasswordManagerDriver {
   public static void main(String args[]) throws IOException {
      Scanner input = new Scanner(System.in);
      String username = "username not initialized";
      String masterPassword = "masterPassword not initialized";
      PasswordManager man = new PasswordManager();
   
      if (!man.loadFile()) {
         System.out.print("Enter Desired Username for new file: ");
         username = input.next();
         System.out.print("Enter Desired Master Password for the new file: ");
         masterPassword = input.next();
         man.createNewFile(username, masterPassword);
      } 
      else {
         username = man.getUser();
         masterPassword = man.getMasterPassword();
      }
   
      System.out.println("Please enter the master password for " + username + "'s account:");
      String passwordAttempt = input.next();
        
      man.lockAndKey(passwordAttempt);
      man.addEntry();
      
      if (man.lockAndKey(passwordAttempt)) {
         System.out.println("\n" + man.getUser());
         System.out.println(man.getMasterPassword());
      }
      else {
         System.out.println("Passwords do not match... exiting program.");
      }
      	
      System.out.println("Program ended successfully.");
   }
}