package aignerfrueauf.schach.schach;

import javafx.scene.layout.GridPane;

public class MovementHandler {
    public static void highlightPawnMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite){
            if(isWhite){
                try {
                    if(column == 6){   //erster Zug weiß
                        highlightGrid(grid,column-1,row, true, true);
                        highlightGrid(grid,column-2,row, true, true);
                    }else {
                        highlightGrid(grid,column-1,row, true, true);
                    }
                }catch (ArrayIndexOutOfBoundsException ignored){}

                try{
                    if(grid[column-1][row+1].contains(Main.blackIdentifier)){
                        highlightGrid(grid,column-1,row+1, true, false);
                    }
                }catch (ArrayIndexOutOfBoundsException ignored){}

                try{
                    if(grid[column-1][row-1].contains(Main.blackIdentifier)){
                        highlightGrid(grid,column-1,row-1, true, false);
                    }
                }catch (ArrayIndexOutOfBoundsException ignored){}

            }else {
                try {
                    if(column == 1){   //erster Zug schwarz
                        highlightGrid(grid,column+1,row, false, true);
                        highlightGrid(grid,column+2,row, false, true);
                    }else {
                        highlightGrid(grid,column+1,row, false, true);
                    }
                }catch (ArrayIndexOutOfBoundsException ignored){}


                try{
                    if(grid[column+1][row+1].contains(Main.whiteIdentifier)){
                        highlightGrid(grid,column+1,row+1, false, false);
                    }
                }catch (ArrayIndexOutOfBoundsException ignored){

                }

                try{
                    if(grid[column+1][row-1].contains(Main.whiteIdentifier)){
                        highlightGrid(grid,column+1,row-1, false, false);
                    }
                }catch (ArrayIndexOutOfBoundsException ignored){

                }
            }
    }

    public static void highlightBishopMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        for (int i = 1; i < grid.length; i++) {
            if(highlightGrid(grid,column+i,row+i, isWhite, false)) break;
        }
        for (int i = 1; i < grid.length; i++) {
            if(highlightGrid(grid,column-i,row-i, isWhite, false)) break;
        }
        for (int i = 1; i < grid.length; i++) {
            if(highlightGrid(grid,column+i,row-i, isWhite, false)) break;
        }
        for (int i = 1; i < grid.length; i++) {
            if(highlightGrid(grid,column-i,row+i, isWhite, false)) break;
        }
    }

    public static void highlightKingMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        highlightGrid(grid,column+1,row+1, isWhite, false);
        highlightGrid(grid,column+1,row, isWhite, false);
        highlightGrid(grid,column+1,row-1, isWhite, false);
        highlightGrid(grid,column,row-1, isWhite, false);
        highlightGrid(grid,column-1,row-1, isWhite, false);
        highlightGrid(grid,column-1,row, isWhite, false);
        highlightGrid(grid,column-1,row+1, isWhite, false);
        highlightGrid(grid,column,row+1, isWhite, false);
    }

    public static void highlightQueenMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        highlightBishopMoves(chessPane,grid,column,row,isWhite);
        highlightRookMoves(chessPane,grid,column,row,isWhite);
    }

    public static void highlightRookMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        for (int i = 1; i < grid.length; i++) {
            if (highlightGrid(grid,column+i,row, isWhite, false))break;
        }
        for (int i = 1; i <grid.length ; i++) {
            if(highlightGrid(grid,column-i,row, isWhite, false))break;
        }
        for (int i = 1; i < grid.length; i++) {
            if(highlightGrid(grid,column,row+i, isWhite, false))break;
        }
        for (int i = 1; i < grid.length; i++) {
            if(highlightGrid(grid,column,row-i, isWhite, false))break;
        }
    }

    public static void highlightKnightMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        highlightGrid(grid,column+2,row+1, isWhite, false);
        highlightGrid(grid,column+2,row-1, isWhite, false);

        highlightGrid(grid,column-2,row+1, isWhite, false);
        highlightGrid(grid,column-2,row-1, isWhite, false);

        highlightGrid(grid,column+1,row+2, isWhite, false);
        highlightGrid(grid,column-1,row+2, isWhite, false);

        highlightGrid(grid,column+1,row-2, isWhite, false);
        highlightGrid(grid,column-1,row-2, isWhite, false);
    }

    private static boolean  highlightGrid(String[][] grid, int colum, int row, boolean isWhite, boolean isPawn){
        try{
        if(colum < 8 && row < 8 && colum >= 0 && row >= 0){
            if(( (grid[colum][row].equals("")))){
                grid[colum][row] = grid[colum][row]+Main.validField;
                return false;

            }else if(((grid[colum][row].contains(Main.blackIdentifier) && isWhite) || (grid[colum][row].contains(Main.whiteIdentifier) && !isWhite)) && !isPawn){
                grid[colum][row] = grid[colum][row]+ Main.hitPieceField;
                return true;

            }else if(((grid[colum][row].contains(Main.whiteIdentifier) && isWhite) || (grid[colum][row].contains(Main.blackIdentifier) && !isWhite)) && !isPawn){
                return true;
            }
        }
        }catch (ArrayIndexOutOfBoundsException e){
            return true;
        }
        return false;
    }
}
