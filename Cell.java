//lyon0210
//yurev003


public class Cell {
    public int row; //row of cell
    public int col; //column of cell
    public char status = '-'; //status of the coordinate '-' nothing, 'B' boat, 'M' miss, 'H' hit.

    public Cell(int row, int col, char status){
        this.row = row;
        this.col = col;
        this.status = status;
    }

    public char get_status(){
        return status;
    }

    public void set_status(char c){
        this.status = c;
    }
    public int get_row()
    {
        return this.row;
    }
    public void set_row(int row1)
    {
        this.row = row1;
    }
    public int getCol()
    {
        return this.col;
    }
    public void setCol(int col1)
    {
        this.col = col1;
    }

}