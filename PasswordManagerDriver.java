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
   
      System.out.println("Please enter the master password for " + username + "'s account: ");
      String passwordAttempt = input.next();
   
      while (!man.lockAndKey(passwordAttempt)) {
         System.out.println("Please enter the master password for " + username + "'s account: ");
         passwordAttempt = input.next();
      }
   
      String choice = "";
      
      while (!choice.equals("c")) {
         System.out.println("Select an option: ");
         System.out.println("\t-(A)dd an entry");
         System.out.println("\t-(D)elete an entry");
         System.out.println("\t-(E)ntries");
         System.out.println("\t-(C)lose program");
         System.out.print("\nSelection: ");
         choice = input.next().toLowerCase();
      
         switch (choice) {
            case "a": 
               man.addEntry();
               break;
         
            case "d":
               String categoryToDelete = "";
               System.out.print("\nSpecify category to delete: ");
               categoryToDelete = input.next();
               man.deleteEntry(categoryToDelete);
               break;
         
            case "e":
               System.out.println(man.displayEntries());
               break;
         
            case "c":
               man.saveFile();
               man.close();
               break;
         
            default: 
               System.out.println("Invalid answer choice.");
               break;
         } 
      }
   
      System.out.println("Program ended successfully.");
   }
}