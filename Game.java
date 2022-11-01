//lyon0210
//yurev003


import java.util.Scanner;
public class Game {
    public static void main(String[] args){
        int row1 = 0; //user input for row size
        int col1 = 0; //user input for column size
        boolean debug = false; //whether run in debug mode
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Battleboats!");
        System.out.println("enter true for debug mode or false to continue.");
        String input1 = sc.nextLine();
        if(input1.equals("true")) //runs debug mode only if true is input, nothing else
            debug = true;


        while(row1 < 3 || row1 > 10) //makes sure it is right size
        {
            System.out.println("Please Enter in a row Size between 3 and 10:");
            row1 = sc.nextInt();
        }

        while(col1 < 3 || col1 > 10) //makes sure it is right size
        {
            System.out.println("Please enter in a column size between 3 and 10:");
            col1 = sc.nextInt();
        }

        Board board1 = new Board(row1, col1); //creates the board with the provided sizes 3-10
        board1.placeBoats(); //creates the randomly placed boats
        System.out.println("Game started");
        userTurns(debug, board1);

    }
    public static void userDrone(Board board1) //ensures proper placement of drone and runs the drone
    {
        String inp = ""; //input row or column
        int rc = -1;
        Scanner s = new Scanner(System.in);
        System.out.println("Would you like to scan a row or column? r for row or c for column");
        while(!inp.equals("r") && !inp.equals("c")) //makes sure proper input.
        {
            inp = s.nextLine();
            if(!inp.equals("r") && !inp.equals("c"))
            {
                System.out.println("Invalid input. Please type in r for row or c for column");
            }
        }
        System.out.println("Which row or column would you like to scan?");
        if(inp.equals("r"))
        {
            rc = s.nextInt(); //sets rc to the row selected
            while(rc < 0 || rc > board1.getRow()) //makes sure proper input
            {
                System.out.println("Invalid input. Type a number within the boundaries of the board.");
                rc = s.nextInt();
            }
        }
        if(inp.equals("c"))
        {
            rc = s.nextInt(); //sets rc to the column selected
            while(rc < 0 || rc > board1.getCol()) //makes sure proper input
            {
                System.out.println("Invalid input. Type a number within the boundaries of the board.");
                rc = s.nextInt();
            }
        }
        int result = board1.drone(inp, rc); //gets the amount of targets from the drone
        System.out.println("Drone has scanned " +result +" targets in the specified area");

    }
    public static void userMissile(Board board1) //makes sure proper use of missile firing
    {
        int missRow = -1;
        int missCol = -1;
        Scanner s = new Scanner(System.in);
        System.out.println("Which row would you like to fire your missile?");
        missRow = s.nextInt();
        while(missRow < 0 || missRow >= board1.getRow())//makes sure proper input for rows
        {
            System.out.println("Invalid input. Type a number within the boundaries of the board.");
            missRow = s.nextInt();
        }
        System.out.println("Which column would you like to fire your missile?");
        missCol = s.nextInt();
        while(missCol < 0 || missCol >= board1.getCol()) //makes sure proper input for columns
        {
            System.out.println("Invalid input. Type a number within the boundaries of the board.");
            missCol = s.nextInt();
        }
        System.out.println("Hits: " + board1.missile(missRow, missCol)); //fires the missile in the selected row and column


    }
    public static void userScanner(Board board1) //makes sure proper use of scanner
    {
        int scanRow = -1;
        int scanCol = -1;
        Scanner s = new Scanner(System.in);
        System.out.println("Which row would you like to scan?");
        scanRow = s.nextInt();
        System.out.println("Which column would you like to scan?");
        scanCol = s.nextInt();
        board1.scanner(scanRow, scanCol); //calls the scanner on selected row and column
    }

    public static int userFire(Board board1) //makes sure proper use of fire and gives out penalty
    {
        Scanner s = new Scanner(System.in);
        int userRow;
        int userCol;
        System.out.println("Please select a row.");
        userRow = s.nextInt();
        System.out.println("Please select a column.");
        userCol = s.nextInt();
        if(userRow >= board1.getRow() || userCol >= board1.getCol() || userRow < 0 || userCol < 0) //if improper use gives extra turn
        {
            System.out.println("Penalty! 1 turn");
            return 2; //how many turns added
        }
        else
        {
            board1.fire(userRow, userCol); //fires at the user row and user column
            return 1; //how many turns added
        }
    }
    public static void userTurns(boolean debug1, Board board1) //runs the user turns until end of game
    {
        Scanner s = new Scanner(System.in);
        int missileAmt = 1; //amount of missiles
        int droneAmt = 1; //amount of drones
        int scannerAmt = 1; //amount of user scanners
        String userChoice; //user choice on which device to use
        int turns = 0; //counts number of turns used
        boolean gameWon = true; //true if game is won, ends game
        while(true) //runs until game is over
        {
            gameWon = true;
            for(int x = 0; x < board1.getRow(); x++)
            {
                for(int y = 0; y < board1.getCol(); y++)
                {
                    if(board1.getCellStatus(x, y) == 'B')
                    {
                        gameWon = false; //checks if any boat pieces remaining
                    }
                }
            }
            if(gameWon == true)
            {
                System.out.println("Congrats! You won in " +turns +" turns");
                return; // ends game if no boats left and prints number of turns
            }
            System.out.print("Turn: " +(turns+1));
            System.out.println(" Would you like to: fire, use missile, use drone, scan or quit");
            if(debug1 == true)
            {
                System.out.println(board1.display()); //displays the board if debug is selected
            }
            if(debug1 == false)
            {
                System.out.println(board1.print()); //prints the hidden board if debug not selected
            }
            userChoice = s.nextLine(); //user selects which device to use
            //int dontPrint = 0;
            if(userChoice.equals("fire"))
            {
                //dontPrint = 1;
                //turns++;
                turns = turns + userFire(board1); //fires on the board and adds the turns
            }
            else if(userChoice.equals("missile"))
            {
                if(missileAmt == 1)
                {
                    userMissile(board1); //fires missile if missile remaining, adds turn and decreases missile amount
                    missileAmt = 0;
                    turns++;
                }
                else //prints if no missiles left
                {
                    System.out.println("No missiles left.");
                }
            }
            else if(userChoice.equals("drone"))
            {
                if(droneAmt == 1)
                {
                    userDrone(board1); //fires drone if drone left
                    droneAmt = 0;
                    turns++; //increases turns used
                }
                else
                {
                    System.out.println("No drones left."); //prints if no drones left
                }
            }
            else if(userChoice.equals("scan"))
            {
                if(scannerAmt == 1)
                {
                    userScanner(board1); //runs scanner if there are scanners left
                    scannerAmt = 0; //removes scanner use
                    turns++; //adds to turns used
                }
                else //prints if no scanners left
                {
                    System.out.println("no scanners left.");
                }
            }
            else if(userChoice.equals("quit"))
            {
                return; //ends game if user quits
            }
            else
            {
                System.out.println("Invalid input"); //prints if user doesnt give a valid input
            }
            board1.boatSunk(); //checks if any ships were sunk on the turn
        }
    }
}