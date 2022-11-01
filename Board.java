//lyon0210
//yurev003


import java.util.Random;

public class Board {
    private int nrows; // number of rows
    private int ncols; //number of columns
    private int boatamt = 0; //boat amounts
    private int boatsHit; //number of boats hit by missle function
    private Cell[][] cells; //cell grid of board

    private Boat[] boatarr; //boat array of all boats on the board
    private Boat[] boatSunk; //used to compare if boat was sunk on fire.


    public Board(int nrows, int ncols) {
        this.nrows = nrows; //sets the row size
        this.ncols = ncols; //sets the column size
        if (nrows < 3 || ncols < 3) {
            System.out.print("Board dimensions too small"); //checks the board dimension
        } else if (nrows > 10 || ncols > 10) {
            System.out.print("Board too big");
        } else {
            cells = new Cell[nrows][ncols]; //creates the grid of size nrows x ncols.

            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    cells[i][j] = new Cell(i, j, '-'); //creates the 2d array of cells
                }
            }
        }


    }

    public int getRow() //returns the row size
    {
        return this.nrows;
    }
    public int getCol() //returns the column size
    {
        return this.ncols;
    }
    public char getCellStatus(int x, int y) //returns the status from cell class at coords x and y.
    {
        return cells[x][y].get_status();
    }

    public void placeBoats() //places the boats randomly on the grid working with createboats()
    {
        int size;
        Boat bo5 = new Boat(5); //creates all potential boats
        Boat bo4 = new Boat(4);
        Boat bo3 = new Boat(3);
        Boat bo2 = new Boat(3);
        if(this.nrows < this.ncols)
        {
            size = nrows; //checks which way is smaller
        }
        else
        {
            size = ncols;
        }
        if(size >= 3) //if statements determine which boats to place.
        {
            if(size >= 9)
            {
                Cell[] c5;
                c5 = createBoat(bo5, 5); //calls createboat() to create random placement of boats
                bo5.setB1(c5); //sets cell status to the created boat at cell array.
                boatamt++; //increases boat amount by one. 
            }
            if(size >= 7)
            {
                Cell[] c4;
                c4 = createBoat(bo4, 4);
                bo4.setB1(c4);
                boatamt++;
            }
            if(size >= 5)
            {
                Cell[] c3;
                c3 = createBoat(bo3, 3);
                bo3.setB1(c3);
                boatamt++;
            }
            if (size >= 4)
            {
                Cell[] c2;
                c2 = createBoat(bo2, 3);
                bo2.setB1(c2);
                boatamt++;

            }
            Cell[] c1;
            Boat bo1 = new Boat(2);
            c1 = createBoat(bo1, 2);
            bo1.setB1(c1);
            boatamt++;

            System.out.println("There are " +boatamt +" Boats in this grid");
            boatarr = new Boat[5]; //creates a boat array
            boatSunk = new Boat[boatamt];
            boatarr[0] = bo1; //creates the boat array of all the boats used.
            boatarr[1] = bo2;
            boatarr[2] = bo3;
            boatarr[3] = bo4;
            boatarr[4] = bo5;
        }

    }
    public Cell[] createBoat(Boat boat1, int size1) //randomly picks boat spots
    {
        Random r = new Random();
        int rowCoord; //randomly generated row and column.
        int colCoord;
        int size = size1; //size of boat
        int valid = 0; //checks if valid placement
        Cell[] c = new Cell[size]; //cell grid of boat coords.
        boolean noOverlap = true;

        while (valid == 0) {
            rowCoord = r.nextInt(this.nrows); //while invalid placement generates random row, column, and orientation
            colCoord = r.nextInt(this.ncols);
            boat1.setOrientation(r.nextBoolean());
            noOverlap = true;

            if (boat1.getOrientation() == true) { //vertical placement
                if(rowCoord + size <= this.nrows) {
                    for (int x1 = 0; x1 < size; x1++) {
                        if (cells[rowCoord + x1][colCoord].get_status() == 'B') {
                            noOverlap = false; //checks if the boat, if placed, overlaps any existing boats
                        }

                    }
                }
                if (rowCoord + size <= this.nrows && noOverlap == true) {
                    valid = 1;
                    for(int x = 0; x < size; x++) {
                        cells[rowCoord + x][colCoord].set_status('B'); //sets the status of the game grid to having a boat
                        c[x] = cells[rowCoord + x][colCoord]; //creates cell array of cells where boat is

                    }

                }
            } else { //horizontal placement
                if(colCoord + size <= this.nrows) {
                    for (int x1 = 0; x1 < size; x1++) {
                        if (cells[rowCoord][colCoord + x1].get_status() == 'B') {
                            noOverlap = false; //makes sure no overlap on any other boats
                        }
                    }
                }
                if (colCoord + size <= this.nrows && noOverlap == true) { //ensures valid placement
                    valid = 1;
                    for(int y = 0; y < size; y++) {
                        cells[rowCoord][colCoord + y].set_status('B'); //sets board status to 'B'
                        c[y] = cells[rowCoord][colCoord + y]; //creates cell array of where boat is
                    }

                }
            }
        }
        return c; //returns the cell array of where the boat coordinates are. 
    }


    public boolean compare(char a, char b) {
        int comp = Character.compare(a, b);
        if (comp < 0) {
            return false;
        } else {
            return true;
        }
    }

    public void fire(int x, int y) {
        //finds where the user fires and reveals the cell status

        char status = cells[x][y].get_status();
        boolean comparison = compare(status, 'B');

        if (comparison == true) {
            if (cells[x][y].get_status() != 'H' && cells[x][y].get_status() != 'M') {
                cells[x][y].set_status('H');
                System.out.println("hit");
            } else {
                System.out.println("Penalty");
            }
        } else {
            if (cells[x][y].get_status() != 'H' && cells[x][y].get_status() != 'M') {
                cells[x][y].set_status('M');
                System.out.println("miss");
            } else {
                System.out.println("Penalty");

            }

        }

    }

    public String print() {//prints the board as the user plays to show their moves
        String s = "";
        for (int i = 0; i < nrows; i++) {
            s += '\n';
            for (int j = 0; j < ncols; j++) {
                if (cells[i][j].get_status() != '-' && cells[i][j].get_status() != 'B') {
                    s += "[" + cells[i][j].get_status() + "]";

                } else {
                    s += "[" + " " + "]";
                }
            }
        }
        return s;
    }

    public String display() { //shows board with all boats and cells revealed, used for debugging
        String s = "";
        for (int i = 0; i < nrows; i++) {// loops through every cell and displays the status of the cell
            s += '\n';
            for (int j = 0; j < ncols; j++) {
                if (cells[i][j] != null) {
                    s += "[" + cells[i][j].get_status() + "]";

                } else {
                    s += "[" + cells[i][j].get_status() + "]";
                }
            }
        }
        return s;
    }

    public int missile(int x, int y) { //fires a 3x3 missle on the gameboard
        //Checks to see where the missile has been fired
        //The problem spots were corners and on the sides of the board because not all cells are within the board

        boatsHit = 0;
        if (x == 0 && y == 0) {
            missileFire(x, y);
            missileFire(x + 1, y);
            missileFire(x + 1, y + 1);
            missileFire(x, y + 1);
            return boatsHit;
        } else if (x == nrows - 1 && y == ncols - 1) {
            missileFire(x, y);
            missileFire(x - 1, y);
            missileFire(x - 1, y - 1);
            missileFire(x, y - 1);
            return boatsHit;
        } else if (x == nrows - 1 && y == 0) {
            missileFire(x, y);
            missileFire(x - 1, y + 1);
            missileFire(x, y + 1);
            missileFire(x - 1, y);
            return boatsHit;
        } else if (x == 0 && y == ncols - 1) {
            missileFire(x, y);
            missileFire(x + 1, y);
            missileFire(x + 1, y - 1);
            missileFire(x, y - 1);
            return boatsHit;
        } else if (x == 0) {
            missileFire(x, y);
            missileFire(x, y + 1);
            missileFire(x, y - 1);
            missileFire(x + 1, y);
            missileFire(x + 1, y + 1);
            missileFire(x + 1, y - 1);
            return boatsHit;

        } else if (y == ncols - 1) {
            missileFire(x, y);
            missileFire(x + 1, y);
            missileFire(x - 1, y);
            missileFire(x, y - 1);
            missileFire(x + 1, y - 1);
            missileFire(x - 1, y - 1);
            return boatsHit;
        } else {
            missileFire(x, y);
            missileFire(x, y + 1);
            missileFire(x, y - 1);
            missileFire(x + 1, y);
            missileFire(x + 1, y - 1);
            missileFire(x + 1, y + 1);
            missileFire(x - 1, y);
            missileFire(x - 1, y + 1);
            missileFire(x - 1, y - 1);
            return boatsHit;
        }
        //method returns the number of hits the user has
    }

    public int drone(String s, int number) { //
        int j = 0;
        if (s.equals("r")) {
            for (int i = 0; i < ncols; i++) {
                if (cells[number][i].get_status() == 'B' || cells[number][i].get_status() == 'H') {
                    j += 1;
                }
            }
        } else if (s.equals("c")) {
            for (int i = 0; i < ncols; i++) {
                if (cells[i][number].get_status() == 'B' || cells[i][number].get_status() == 'H') {
                    j += 1;
                }
            }
        }
        return j;
    }

    public void boatSunk() //checks if boat was sunk
    {
        int numHits = 0; //checks how many time boat has hits
        for(int x = 0; x < boatamt; x++)
        {
            numHits = 0;
            for(int z1 = 0; z1 < boatarr[x].getB1().length; z1++) //runs through all boat cells
            {
                if(boatarr[x].getB1()[z1].get_status() == 'H')
                {
                    numHits++; //adds to every boat hit.
                }
            }
            if(numHits == boatarr[x].getSize() && boatSunk[x] != boatarr[x]) //checks if boat hasnt been sunk and all spots have been hit.
            {
                System.out.println("boat sunk.");
                boatSunk[x] = boatarr[x]; //adds to list of sunken boats
            }
        }
    }
    public void scanner(int x, int y) //method for the scanner
    {
        Boat boatSelec = null; //picks a boat
        Boolean orient1; //used to print orientation of boat
        int boatSize; //used to print size of boat
        if(x < nrows && y < ncols)
        {
            fire(x, y); //fires at chosen spot
            if(cells[x][y].get_status() == 'B' || cells[x][y].get_status() == 'H') //checks if boat is there
            {
                for(int z = 0; z < boatamt; z++)
                {
                    for(int z1 = 0; z1 < boatarr[z].getB1().length; z1++) //runs through all the boats
                        if(boatarr[z].getB1()[z1].get_row() == x && boatarr[z].getB1()[z1].getCol() == y) //checks which boat it is
                        {
                            boatSelec = boatarr[z]; //selects the boat that is at the spot
                        }
                }
                orient1 = boatSelec.getOrientation(); //gets the orientation of the selected boat
                boatSize = boatSelec.getSize(); //gets the size of the boat
                if(orient1)
                {
                    System.out.println("Hit! The boat is size " +boatSize +" and is vertical");
                }
                if(!orient1)
                {
                    System.out.println("Hit! The boat is size " +boatSize +"and is horizontal");
                }
            }
        }
        if(x >= nrows || y >= ncols || cells[x][y].get_status() == '-')
        {
            System.out.println("There are no boats here"); //if missed all the boats
        }
    }
    public void missileFire(int x, int y) { //Very similar to the normal fire method except it keeps track of boats hit and does not print out everytime a boat is hit or missed
        char status = cells[x][y].get_status();
        boolean comparison = compare(status, 'B');

        if (comparison == true) { // comaprison checks to see if there is a boat there
            if (cells[x][y].get_status() != 'H' && cells[x][y].get_status() != 'M') {
                cells[x][y].set_status('H'); //changes cell status to hit if boat is present
                boatsHit += 1;
                //System.out.println("hit");
            }
        } else {
            if (cells[x][y].get_status() != 'H' && cells[x][y].get_status() != 'M') { //if no boat is present changes cell status to miss
                cells[x][y].set_status('M');
                //System.out.println("miss");
            }
        }
    }

}