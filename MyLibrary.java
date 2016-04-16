import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FilePermission;
import java.security.AccessController;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * Contains methods to write to file or read from file, check file existence, get the list of files in a folder
 * 
 * @author Aleksejs Loginovs
 */
public class MyLibrary
{
    /**
     * Determines which action to do (write/read/duplicate file)
     * 
     * @param action represents the action to do with the file
     * @param file represents the file to do action to
     */
    public static void readwriter(String action, String file)
    {
       if(action.equals("print"))
       {
           writeInFile(file);
       }
       else if(action.equals("read"))
       {
           readFromFile(file);
       }
       else if(action.equals("duplicate"))
       {
           duplicateFile(file);
       }
    }
    
    /**
     * Asks for user input and stores it into a file
     * 
     * @param fileName represents a file to write information into
     */
    public static void writeInFile(String fileName)
    {
       String userInput;
       
       FileOutputStream writeStream = null;
       PrintWriter write = null;
       Scanner doggie = null;
       
       //try creating a stream, writing into file. if failed, print the error message
       try
       {
           writeStream = new FileOutputStream(fileName);
           write = new PrintWriter(writeStream);
           doggie = new Scanner(System.in);
           
           //save user inputs in a file until he inputs a blank line
           userInput = doggie.nextLine();
           while(!userInput.equals(""))
           {
               write.println(userInput);
               userInput = doggie.nextLine();
           }
       }
       catch(IOException ex)
       {
           System.out.println("Could not read the file. Error code RW-wIF-1");
       }
       finally
       {
           //close file and scaller
           if(write!=null)
           {
               write.close();
           }
           if(doggie!=null)
           {
               doggie.close();
           }
       }
    }
   
    /**
     * Reads from file and outputs information on the screen
     * 
     * @param fileName represents a file to read information from
     */
    public static void readFromFile(String fileName)
    {
        boolean fileExistent;
        String line;
        
        fileExistent = checkFileExistence(fileName);
        
        //if file exists, read from it, else ask user for a new file name and try running the method again with the new name
        if(fileExistent)
        {
           FileReader readStream = null;
           BufferedReader read = null;
           
           try
           {
               readStream = new FileReader(fileName);
               read = new BufferedReader(readStream);
               
               //print out the file contents line by line looping through it until the next line is null
               line = read.readLine();
               while(line != null)
               {
                   System.out.println(line);
                   line = read.readLine();
               }
           }
           catch(IOException ex)
           {
               System.out.println("Ooops, something went wrong! Error code: ML-rFF-1");
           }
           finally
           {
               //close file
               try
               {
                   read.close();
               }
               catch(IOException ex)
               {
                   System.out.println("Could not close file");
               }
           } 
        }
        else if(!fileExistent)
        {
           System.out.println("Such file doesn't exist.");
           readFromFile(getNewFileName());
           return;
        }
    }
    
    /**
     * Copies output file contents to an input file
     * 
     * @param fileName represents input and output file paths separated by the <> symbols
     */
    public static void duplicateFile(String fileName)
    {
       boolean[] fileExistent = new boolean[2];
       String copiedLine;
       
       //separates input file path from output file path and stores them into an array
       String[] file = fileName.split("<>"); //INPUT THEN OUTPUT
       
       fileExistent[1] = checkFileExistence(file[1]);
       
       if(fileExistent[1])
       {
           FileReader readStream = null;
           BufferedReader read = null;
           FileOutputStream writeStream = null;
           PrintWriter write = null;
           
           //establishes necessary streams of data and copies file contents
           try
           {
               readStream = new FileReader(file[1]);
               read = new BufferedReader(readStream);
               
               writeStream = new FileOutputStream(file[0]);
               write = new PrintWriter(writeStream);
               
               //copies file contents looping through the output file line by line and printing it to a second file
               copiedLine = read.readLine();
               while(copiedLine != null)
               {
                   write.println(copiedLine);
                   copiedLine = read.readLine();
               }
           }
           catch(IOException ex)
           {
               System.out.println("File could not be read. Error code ML-DF-1");
           }
           finally
           {
               //closes the files
               try
               {
                   read.close();
               }
               catch(IOException ex)
               {
                   System.out.println("Could not close file! Error code ML-DF-1");
               }
               if(write!=null)
               {
                   write.close();
               }
           }
       }
       else if(!fileExistent[1])
       {
           //if output file doens't exist, ask for a new file name, store it in a variable and try running the method with the updated output file name
           System.out.println("The output file doesn't exist. Error code ML-DF-3.");
           String newNames = file[0] + getNewFileName();
           duplicateFile(newNames);
           return;
       }
    }
    
    /**
     * Saves an arrayList of values in a file
     * 
     * @param arrayToSave represents an ArrayList that is about to be stored in a file
     * @param file represents a path to a file
     */
    public static void saveArrayList(ArrayList arrayToSave, String file)
    {
        File saveFile = null;
        PrintWriter write = null;
        FileOutputStream writeStream = null;
        
        //prints the ArrayList contents into a file
        try
        {
            writeStream = new FileOutputStream(file);
            write = new PrintWriter(writeStream);
            
            //loops through the ArrayList saving its contents to a file line by line
            for(int i = 0; i < arrayToSave.size(); i++)
            {
                write.println(arrayToSave.get(i));
            }
        }
        catch(IOException ex)
        {
            System.out.println("Ooops, there was some kind of an error. Error code: ML-SAL-01.");
        }
        finally
        {
            //closes the file
            if(write!=null)
            {
                write.close();
            }
        }
    }
    
    /**
     * Reads a file, stores its contents into an array and returns it
     * 
     * @param file represents a path to a file
     * @return strArray represents an array of lines read from the file
     */
    public static String[] getStringArray(String file)
    {
        boolean existence = checkFileExistence(file);
        String[] strArray = null;
        ArrayList<String> next = new ArrayList<String>();
        
        if(existence)
        {
            FileReader readStream = null;
            BufferedReader read = null;
            int i = 0;
            
            try
            {
                readStream = new FileReader(file);
                read = new BufferedReader(readStream);

                //finds out the number of lines in the file and stores them in the arrayList
                String nextLine = read.readLine();
                while(nextLine!=null)
                {
                    System.out.println(nextLine);
                    i++;
                    next.add(nextLine);
                    nextLine = read.readLine(); 
                }
                
                //creates an array of the appropriate size to store all lines in it and loops through it storing the ArrayList values
                strArray = new String[i];
                for(int x = 0; x<strArray.length;x++)
                {
                    strArray[x] = next.get(x);
                }
                
            }
            catch(IOException ex)
            {
                System.out.println("Could not read file. Error code ML-GSA-1.");
            }
            finally
            {
                //closes the file
                try
                {
                    read.close();
                }
                catch(IOException ex)
                {
                    System.out.println("Could not close the file... Error code ML-GSA-2.");
                }
            }
        }
        else
        {
            //if the read file doens't exist, loads a default field blueprint (only added for the reversi project purposes)
            System.out.println("Such file doesn't exist. Loading a default field instead.");
            strArray = getStringArray("blueprint/fieldBlu.txt");
        }
        return strArray;
    }
   
    /**
     * Asks user to input a new file name
     * 
     * @return newFileName represents a new file name
     */
    public static String getNewFileName()
    {
       Scanner newFileNameScan = new Scanner(System.in);
       String newFileName;
       System.out.println("Please, enter the correct file name:");
       newFileName = newFileNameScan.nextLine();
       return newFileName;
    }
   
    //    public static boolean checkFileReadability(String action, String file)
    //    {
    //        boolean readability;
    //        FilePermission perm = new FilePermission(file, action);
    //        
    //        try
    //        {
    //            AccessController.checkPermission(perm);
    //            readability = true;
    //        }
    //        catch(AccessControlException ex)
    //        {
    //            readability = false;
    //            String newFileName = getNewFileName();
    //            checkFileReadability(action, newFileName);
    //            return readability;
    //        }
    //    }
   
    /**
     * Checks if the fiven file exists
     * 
     * @param file the file to check existence of
     * @return existence represents the existence of the file
     */
    public static boolean checkFileExistence(String file)
    {
       boolean existence;
       File checkedFile = new File(file);
       
       existence = checkedFile.exists();
       return existence;
    }
   
    /**
     * Gets all of the file and folder names in the given folder
     * 
     * @param path represents a path for the folder to get file names from
     * @return fileArrayInString represents the list of files in the given folder stored in one string and separated by '#'
     */
    public static String getFileNamesInFolder(String path)
    {
       File folder = null;
       File[] fileArray = null;
       String fileArrayInString = "";
       
       boolean fileExistence = checkFileExistence(path);
       
       //if the given folder exists, create a file object from it
       if(fileExistence)
       {
           folder = new File(path);
       }
       
       //try getting the list of names in that folder
       try
       {
           fileArray = folder.listFiles();
       }
       catch(Exception ex)
       {
           System.out.println("Ooops, there was a problem while trying to read a file. Error code: ML-GFNIF-01.");
           return fileArrayInString;
       }
       
       //loop through the array of files in the folder storing them into a variable
       for(File file: fileArray)
       {
           String temp; //variable that holds a path to the file with/without a separator
           //if there already are file names in a string variable, add a separator in front of the file path
           if(!fileArrayInString.equals(""))
           {
               temp = "#" + file.getPath();
           }
           else
           {
               temp = file.getPath();
           }
           fileArrayInString +=temp; //add a new value to the end of the string
       }
       return fileArrayInString;
    }
}
