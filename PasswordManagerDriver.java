import java.util.Scanner;

public class PasswordManagerDriver {
   public static void main(String args[]) {
		Scanner input = new Scanner(System.in);

     	PasswordManager man = new PasswordManager();

        if (!man.loadFile()) {
      		System.out.print("Enter Desired Username for new file: ");
      		String username = input.next();
      		System.out.print("Enter Desired Master Password for the new file: ");
      		String masterPassword = input.next();
      		man.createNewFile(username, masterPassword);
     	}
      	System.out.println("Program ended successfully.");
   }
}