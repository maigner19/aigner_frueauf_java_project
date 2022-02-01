package aignerfrueauf.schach.schach;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class MovementHandler {
    public static void highlightPawnMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite){
        if(isWhite){
            if(column == 6){   //erster Zug weiß
                highlightGrid(grid,column-1,row, Main.validField);
                highlightGrid(grid,column-2,row, Main.validField);
            }else {
                highlightGrid(grid,column-1,row, Main.validField);
            }

            if(grid[column-1][row+1].contains(Main.blackIdentifier)){
                highlightGrid(grid,column-1,row+1, Main.hitPieceField);
            }else if(grid[column-1][row-1].contains(Main.whiteIdentifier)){
                highlightGrid(grid,column-1,row-1, Main.hitPieceField);
            }
        }else {
            if(column == 1){   //erster Zug schwarz
                highlightGrid(grid,column+1,row, Main.validField);
                highlightGrid(grid,column+2,row, Main.validField);
            }else {
                highlightGrid(grid,column+1,row, Main.validField);
            }

            if(grid[column+1][row+1].contains(Main.whiteIdentifier)){
                highlightGrid(grid,column+1,row+1, Main.hitPieceField);
            }else if(grid[column+1][row-1].contains(Main.whiteIdentifier)){
                highlightGrid(grid,column+1,row-1, Main.hitPieceField);
            }
        }
    }

    public static void highlightBishopMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        for (int i = 0; i < grid.length; i++) {
            if(highlightGrid(grid,column+i,row+i, Main.validField)) break;
        }
        for (int i = 0; i < grid.length; i++) {
            if(highlightGrid(grid,column-i,row-i, Main.validField)) break;
        }
        for (int i = 0; i < grid.length; i++) {
            if(highlightGrid(grid,column+i,row-i, Main.validField)) break;
        }
        for (int i = 0; i < grid.length; i++) {
            if(highlightGrid(grid,column+i,row+i, Main.validField)) break;
        }
    }

    public static void highlightKingMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        highlightGrid(grid,column+1,row+1, Main.validField);
        highlightGrid(grid,column+1,row, Main.validField);
        highlightGrid(grid,column+1,row-1, Main.validField);
        highlightGrid(grid,column,row-1, Main.validField);
        highlightGrid(grid,column-1,row-1, Main.validField);
        highlightGrid(grid,column-1,row, Main.validField);
        highlightGrid(grid,column-1,row+1, Main.validField);
        highlightGrid(grid,column,row+1, Main.validField);
    }

    public static void highlightQueenMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        highlightBishopMoves(chessPane,grid,column,row,isWhite);
        highlightRookMoves(chessPane,grid,column,row,isWhite);
    }

    public static void highlightRookMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        for (int i = 0; i < grid.length; i++) {
            if (highlightGrid(grid,column+i,row, Main.validField))break;
        }
        for (int i = 0; i <grid.length ; i++) {
            if(highlightGrid(grid,column-i,row, Main.validField))break;
        }
        for (int i = 0; i < grid.length; i++) {
            if(highlightGrid(grid,column,row+i, Main.validField))break;
        }
        for (int i = 0; i < grid.length; i++) {
            if(highlightGrid(grid,column,row-i, Main.validField))break;
        }
    }

    public static void highlightKnightMoves(GridPane chessPane, String[][] grid, int column, int row, boolean isWhite) {
        highlightGrid(grid,column+2,row+1, Main.validField);
        highlightGrid(grid,column+2,row-1, Main.validField);

        highlightGrid(grid,column-2,row+1, Main.validField);
        highlightGrid(grid,column-2,row-1, Main.validField);

        highlightGrid(grid,column+1,row+2, Main.validField);
        highlightGrid(grid,column-1,row+2, Main.validField);

        highlightGrid(grid,column+1,row-2, Main.validField);
        highlightGrid(grid,column-1,row-2, Main.validField);
    }

    private static boolean  highlightGrid(String[][] grid, int colum, int row, String identifier){
        if(colum < 8 && row < 8 && colum >= 0 && row >= 0){
            if(( (grid[colum][row].equals("")))){
                grid[colum][row] = identifier;
            }else {
                return true;
            }
        }
        return false;
    }


}
