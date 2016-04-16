import java.util.Scanner;
/**
 * Contains the main method & methods to display/process main menu
 */
public class Menu
{
    /**
     * A default constructor that does nothing
     */
    public Menu()
    {
    }
    
    /**
     * Opens the main menu and starts the whole process
     */
    public static void main(String args[])
    {
        Menu menu = new Menu();
        menu.mainMenu();
    }
    
    /**
     * Displays the main game menu and processes user main menu input
     */
    public void mainMenu()
    {
        Scanner menuOptionListener = new Scanner(System.in);
        String userChoice;
        
        System.out.println("\u000c");
        System.out.println("#######################################");
        System.out.println("#                                     #");
        System.out.println("# 1. Start a new game.                #");
        System.out.println("# 2. Load the game.                   #");
        System.out.println("# 3. Help                             #");
        System.out.println("# 4. Exit the program.                #");   
        System.out.println("#                                     #");
        System.out.println("#######################################");
        userChoice = menuOptionListener.nextLine();
        
        
        processUserChoice(userChoice);
    }
    
    /**
     * Processes user main menu input
     * 
     * @param userChoice represents user's menu choice
     */
    public void processUserChoice(String userChoice)
    {
        Game game = new Game();
        switch(userChoice)
        {
            case "1":   game.startGame();
                        break;
            case "2":   game.loadGame();
                        break;
            case "3":   game.openHelp();
                        break;
            case "4":   game.closeGame();
                        break;
            default:    mainMenu();
                        break;
        }
        mainMenu();
    }
    
    
}