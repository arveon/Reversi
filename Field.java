
/**
 * Contains methods to store and print the field
 * 
 * @author Aleksejs Loginovs
 */
public class Field
{
    private String[][] field; //the field array
    final int FIELDSIZE; //size of the field
    private boolean firstTurn = true; //stores the player, who has the first turn after the field was loaded
    /**
     * Default constructor that sets the size of the field
     */
    public Field()
    {
        FIELDSIZE = 8;
    }
    
    /**
     * Sets the field
     * 
     * @return field contains a game field with players' discs
     */
    public String[][] setField(String file)
    {
        String[] tempField; //1-d array that will be transformed into 2d array
        String turn;
        tempField = MyLibrary.getStringArray(file);
        field = new String[FIELDSIZE][FIELDSIZE]; //initialise the 2d field array
        
        //define who's turn it's supposed to be after the field loads
        turn = tempField[0];
        firstTurn = Boolean.parseBoolean(turn);
        
        //transform a 1d field array into a 2d field array
        for(int i = 0; i<FIELDSIZE;i++)
        {
            for(int j = 0;j<FIELDSIZE;j++)
            {
                field[i][j] = tempField[i+1].substring(j,j+1);
            }
        }
        return field;
    }
    
    /**
     * Returns who's turn it's supposed to be after the field loads
     * 
     * @return firstTurn represents the player who's turn it will be
     */
    public boolean getFirstTurn()
    {
        return firstTurn;
    }
    
    /**
     * Prints the field
     * 
     */
    public void printField()
    {
        //loops through the field array and prints it
        for(int i = -1; i<=field.length;i++)
        {
            //if the array didn't start printing the field yet/finished printing it, print the coordinates on the top and on the bottom of it, else print the array contents
            if(i == -1 || i == field.length)
            {
                System.out.print("   a b c d e f g h  ");
            }
            else
            {
                for(int j = 0; j<field.length;j++)
                {
                    //prints coordinates on the left and right side of the field
                    if(j == 0)
                    {
                        System.out.print((i+1) + "  " +field[i][j] + " ");
                    }
                    else if(j == 7)
                    {
                        System.out.print(field[i][j] + "  " + (i+1));
                    }
                    else
                    {
                        System.out.print(field[i][j] + " ");
                    }
                }
            }
            System.out.println();
        }
    }
}
