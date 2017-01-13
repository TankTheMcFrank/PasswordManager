//import java.lang.StringBuilder;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;

public class PasswordManager {
	//Fields
   private File file;
   private boolean allowAccess;
   private Scanner readFile;
   private String fileName;
   private Scanner input;
   //private String masterPassword;
   private String user;
   private PasswordSet<Credentials> data = 
         new PasswordSet<Credentials>("username not set", "masterPassword not set");

   private static String DEFAULT_FILENAME = "entry_files.txt";


	//Constructor
	/*
	 * UPDATE NEEDED:
	 * "masterPassword" needs to read the first line of a given file in order
	 * to have the proper password for lockAndKey() to compare with (and
	 * therefore allow access to the rest of the program)
	 */
   public PasswordManager() throws IOException {
      //masterPassword = "";
      input = new Scanner(System.in);
      allowAccess = false;
   }

   /**
    * Returns user field. For testing only.
    *
    * @return user field
    */
   public String getUser() {
      return data.getUsername();
   }

   /**
    * Returns masterPassword field. For testing only.
    *
    * @return masterPassword field
    */
   public String getMasterPassword() {
      return data.getMasterPassword();
   }


	//Public methods
	/**
	 * The lockAndKey() method checks to see if a given "masterPasswordIn"
	 * matches the "masterPassword"; if "masterPasswordIn" is correct, then
	 * changes "allowAccess" to true, allowing access to the rest of the
	 * program.
	 *
	 * @param masterPasswordIn user's input compared to "masterPassword"
	 * @return true/false depending on if "masterPasswordIn" matches
	 *         "masterPassword"
	 */
   public boolean lockAndKey(String masterPasswordIn) {
      if (masterPasswordIn.equals(data.getMasterPassword())) {
         allowAccess = true;
         return true;
      }
   
      return false;
   }


	/**
	 * This version of the loadFile() method will attempt to load a file of
	 * entries based on a default file name.
	 * 
	 * @return true/false based on the success of the method
	 */
   public boolean loadFile() throws IOException {
      file = new File(DEFAULT_FILENAME);
      if (!file.canRead()) {
         System.out.println("File could not be read. Perhaps a new file should be created...");
         return false;
      }
   
      readFile = new Scanner(file);
      Scanner lineReader;
   
      data.setUsername(readFile.nextLine());
      data.setMasterPassword(readFile.nextLine());
   
      while (readFile.hasNext()) {
         lineReader = new Scanner(readFile.nextLine());
         lineReader.useDelimiter(",");
         String tempCategory = lineReader.next();
         String tempUsername = lineReader.next();
         String tempPassword = lineReader.next();
         Credentials temp = new Credentials(tempCategory, tempUsername, tempPassword);
         data.add(temp);
      }
   
      System.out.println("File successfully loaded.");
      readFile.close();
   
      return true;
   }


	/**
	 * This version of the loadFile() method will load a savefile based on a 
	 * given "fileNameIn" to search for.
	 * 
	 * @param fileNameIn name of file to search for and attempt to load
	 * @return true/flase based on the success of the method
	 */
	//public boolean loadFile(String fileNameIn) {
		//return false;
	//}


	/**
	 * 
	 */
   public void createNewFile(String usernameIn, String masterPasswordIn) {
      System.out.println("Attempting to create new file...");
      try {
         PrintWriter writer = new PrintWriter(DEFAULT_FILENAME, "UTF-8");
         writer.println(usernameIn);
         writer.println(masterPasswordIn);
         writer.close();
      }
      catch (IOException e){
         System.out.println("Error creating new file: " + e);
         return;
      }
   
      data.setUsername(usernameIn);
      data.setMasterPassword(masterPasswordIn);
   
      allowAccess = true;
   
      System.out.println("New file successfully created.");
   }


	/**
	 * The addEntry() method will add an entry to the established "fileName"
	 * as long as it is not a duplicate.
	 *
	 * @param entryIn category/username/password to be added to the file
	 * @return true/false; true if entry is added to the file, otherwise
	 *         false (for instance, if the cateogry already exists in 
	 *          the file)
	 */
   public boolean addEntry() {
      if (!allowAccess) {
         System.out.println("Access denied. Provide master password.");
         return false;
      }
   
      //Scanner input = new Scanner(System.in);
   
      System.out.print("Category for entry: ");
      String newCategory = input.nextLine();
      
      System.out.print("Username: ");
      String newUsername = input.nextLine();
   
      System.out.print("Password: ");
      String newPassword = input.nextLine();
   
      Credentials temp = new Credentials(newCategory, newUsername, newPassword);
      data.add(temp);
   
      //input.close();
      return true;
   }


	/**
	 * NOTE:
	 * This method is likely to be one of the last to be implemented.
	 *
	 * The encryptEntry() method will encrypt an entry based on the 
	 * "masterPassword" field.
	 * 
	 * @param entryIn entry to be encrypted
	 */
   public void encryptEntry(String entryIn) {
    return;
   }


	/**
	 * The deleteEntry() method will delete an entry from the established
	 * "fileName" unless the entry is not present.
	 * 
	 * @param categoryIn entry to be deleted
	 * @return true/false; true if the entry is found and deleted, otherwise
	 *         false (for instnace, if the entry is not found)
	 */
   public boolean deleteEntry(String categoryIn) {
      if (!allowAccess) {
         System.out.println("Access denied. Provide master password.");
         return false;
      }
      
      data.findAllWithCategory(categoryIn);
      System.out.println()
      Credentials toRemove = new Credentials(categoryIn, "empty", "empty");
      if (data.remove(toRemove)) {
         return true;
      }
   
      return false;
   }


   /**
    * The displayEntries() method returns a list of entries, including category,
    * username, and password for each entry.
    * 
    * @return data.toString() String displaying the entries in data field
    */
   public String displayEntries() {
      return data.toString();
   }


	/**
	 * NOTE:
	 * This method is likely to be one of the last to be implemented.
	 * 
	 * The decryptEntry() method will decrypt an entry based on the 
	 * "masterPassword" field. 
	 *
	 * @param entryIn entry to be decrypted
	 */
   public void decryptEntry(String entryIn) {
    return;
   }


	/**
	 * The accessEntry() method will attempt to access an entry based on a
	 * given "categoryIn".
	 * 
	 * @param categoryIn cateogry of the entry to be accessed
	 * @return String entry being accessed; otherwise, the entry is not found,
	 *         null
	 */
   public String accessEntry(String categoryIn) {
      return null;
   }


	/**
	 * The isFound() method is a product of refactoring. This method will check
	 * to see whether a particular entry is found in the file based on a given
	 * "categoryIn".
	 * 
	 * @param categoryIn to be searched for
	 * @return true/false true if the cateogry is found, otherwise false
	 */
   public boolean isFound(String categoryIn) {
      return false;
   }

   /**
    * The saveFile() method will save the contents of the data field to a txt file named "entry_files.txt" so that
    * the Credentials objects can be reloaded in a later iteration of this program. 
    * 
    * @return true/false true if the method belives that the file was saved properly, otherwise false
    */
   public boolean saveFile() {
      try {
         PrintWriter writer = new PrintWriter(DEFAULT_FILENAME, "UTF-8");
         writer.println(data.getUsername());
         writer.println(data.getMasterPassword());
      
         Iterator<Credentials> itr = data.iterator();
      
         while (itr.hasNext()) {
            Credentials temp = itr.next();
            String toAdd = temp.getCategory() + "," + temp.getUsername() + "," + temp.getPassword();
            writer.println(toAdd);
         }
         writer.close();
      }
      catch (IOException e){
         System.out.println("Error creating save file: " + e);
         return false;
      }
   
      return false;
   }

   public void close() {
      input.close();
   }
}