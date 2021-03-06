package aignerfrueauf.schach.schach;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class Main extends Application {
    static double resolution = 1.5;
    static int pixels = 272;   //272
    static float gridCount = 8;
    static double gridSize = pixels / gridCount * resolution;

    final Color WHITE =Color.WHEAT;
    final Color BLACK =Color.valueOf("#A68567");
    final Color HIGLIGHT = Color.LIGHTGRAY;
    final Color HITCOLOR = Color.RED;

    static String bKing = "file:piecesImg/bKing.png";
    static String bQueen = "file:piecesImg/bQueen.png";
    static String bBishop = "file:piecesImg/bBishop.png";
    static String bRook = "file:piecesImg/bRook.png";
    static String bKnight = "file:piecesImg/bKnight.png";
    static String bPawn = "file:piecesImg/bPawn.png";
    static String wKing = "file:piecesImg/wKing.png";
    static String wQueen = "file:piecesImg/wQueen.png";
    static String wBishop = "file:piecesImg/wBishop.png";
    static String wRook = "file:piecesImg/wRook.png";
    static String wKnight = "file:piecesImg/wKnight.png";
    static String wPawn = "file:piecesImg/wPawn.png";

    static final String blackKing = "Bk";
    static final String blackQueen = "Bq";
    static final String blackBishop = "Bb";
    static final String blackRook = "Br";
    static final String blackKnight = "Bn";
    static final String blackPawn = "Bp";

    static final String whiteKing = "Wk";
    static final String whiteQueen = "Wq";
    static final String whiteBishop = "Wb";
    static final String whiteRook = "Wr";
    static final String whiteKnight = "Wn";
    static final String whitePawn = "Wp";

    static final String blackIdentifier = "B";
    static final String whiteIdentifier = "W";

    static final String validField = "V";
    static final String hitPieceField = "H";

    GridPane chessPane;
    static String[][] piecesPositions;

    static boolean isWhite = true;
    public Main(){
        piecesPositions = new String[8][8];

    }

    Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        chessPane = new GridPane();
        chessPane.resize(pixels, pixels);
        chessPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < piecesPositions.length; i++) Arrays.fill(piecesPositions[i], "");
        setUpPane();
        Scene chessScene = new Scene(chessPane, pixels * resolution, pixels * resolution);



        stage.setTitle("Schach!");
        stage.getIcons().add(new Image(bKing));
        stage.setScene(chessScene);
        stage.setResizable(false);
        stage.show();
    }

    public void checkMovable(final int INITIALROW,final int INITIALCOLUMN,final int FINALROW,final int FINALCOLUMN){
        if(!piecesPositions[FINALCOLUMN][FINALROW].equals("")){
            movePiece(INITIALROW,INITIALCOLUMN,FINALROW,FINALCOLUMN);
        }
    }

    public void movePiece(final int INITIALROW,final int INITIALCOLUMN,final int FINALROW,final int FINALCOLUMN){
        if(!piecesPositions[FINALCOLUMN][FINALROW].equals("") && !piecesPositions[FINALCOLUMN][FINALROW].contains(validField)){
            removePiece(FINALROW,FINALCOLUMN);
        }

        piecesPositions[FINALCOLUMN][FINALROW] = returnPieceId(INITIALROW,INITIALCOLUMN);

        removePiece(INITIALCOLUMN,INITIALROW);
        updatePane();
        checkIfFinished();
    }

    private void checkIfFinished(){
        boolean whiteWon = true;
        boolean blackWon = true;
        for (String[] piecesPosition : piecesPositions) {
            for (String s : piecesPosition) {
                if (s.equals(blackKing)) {
                    whiteWon = false;
                    break;
                }
                if (s.equals(whiteKing)) {
                    blackWon = false;
                    break;
                }
            }
        }

        if(whiteWon){
            openWinField(true);
        }else if(blackWon){
            openWinField(false);
        }
    }

    private void openWinField(boolean whiteWon){
        Label winText = new Label();
        winText.setFont(Font.font("Arial", 30));
        winText.setStyle("-fx-font-weight: bold;");

        StackPane winLayout = new StackPane();
        winLayout.getChildren().add(winText);

        Scene winScene = new Scene(winLayout, resolution * pixels/2, resolution * pixels/4);
        Stage winWindow = new Stage();
        winWindow.setScene(winScene);

        winWindow.setX(stage.getX() + resolution*pixels/4);
        winWindow.setY(stage.getY() + resolution*pixels/2);

        winWindow.show();
        if(whiteWon){
            winText.setText("White wins");
            winWindow.setTitle("White wins");
            winWindow.getIcons().add(new Image(wKing));
        }else {
            winText.setText("Black wins");
            winWindow.setTitle("Black wins");
            winWindow.getIcons().add(new Image(bKing));
        }
    }


    boolean pieceSelected=false;
    int firstRow;
    int firstColumn;
    int secondColumn;
    int secondRow;
    void  onPress(MouseEvent event){
        Node source;
        if (!pieceSelected){
            source = (Node) event.getSource();

            getFirstVariables(source);
            if(!piecesPositions[firstRow][firstColumn].equals("")){
                if(piecesPositions[firstRow][firstColumn].contains(whiteIdentifier) && isWhite || piecesPositions[firstRow][firstColumn].contains(blackIdentifier) && !isWhite){
                    pieceSelected = true;
                    switch (returnPieceId(firstRow,firstColumn)){
                        case blackPawn, whitePawn://pawn
                            MovementHandler.highlightPawnMoves(chessPane,piecesPositions,firstRow,firstColumn,isWhite);
                            break;
                        case whiteKing, blackKing://king
                            MovementHandler.highlightKingMoves(chessPane,piecesPositions,firstRow,firstColumn,isWhite);
                            break;
                        case whiteQueen,blackQueen://queen
                            MovementHandler.highlightQueenMoves(chessPane,piecesPositions,firstRow,firstColumn,isWhite);
                            break;
                        case whiteRook,blackRook://rook
                            MovementHandler.highlightRookMoves(chessPane,piecesPositions,firstRow,firstColumn,isWhite);
                            break;
                        case whiteKnight,blackKnight://knight
                            MovementHandler.highlightKnightMoves(chessPane,piecesPositions,firstRow,firstColumn,isWhite);
                            break;
                        case whiteBishop,blackBishop://bishop
                            MovementHandler.highlightBishopMoves(chessPane,piecesPositions,firstRow,firstColumn,isWhite);
                            break;
                        default:
                            break;

                    }
                }
            }

            System.out.println("First Press: " + firstRow+","+firstColumn);
        }else {// second click
            source = (Node) event.getSource();

            getSecondVariables(source);
                if((piecesPositions[secondColumn][secondRow].contains(whiteIdentifier) && isWhite) ||(piecesPositions[secondColumn][secondRow].contains(blackIdentifier) && !isWhite)){ //check if selected other piece
                    getFirstVariables(source);
                }else if(secondColumn == firstColumn && secondRow == firstRow){
                    System.out.println("Gleiches Feld");
                    System.out.println("First Press:" + firstColumn + ";" + firstRow);
                    System.out.println("Second Press:" + secondColumn + ";" + secondRow);
                    System.out.println("");
                }
                else {
                    checkMovable(firstRow,firstColumn,secondRow,secondColumn);
                    pieceSelected = false;
                    isWhite=!isWhite;
                }
            cleanArray();
        }
        updatePane();
    }

    private void getFirstVariables(Node source){
        firstColumn = GridPane.getColumnIndex(source);
        firstRow = GridPane.getRowIndex(source);
    }
    private void getSecondVariables(Node source){
        secondColumn = GridPane.getRowIndex(source);
        secondRow = GridPane.getColumnIndex(source);
    }


    private void removePiece( final int FINALROW,final int FINALCOLUMN){
        piecesPositions[FINALCOLUMN][FINALROW] = "";
    }


    private void setUpPane(){
        placeWhitePiecesArray();
        placeBlackPiecesArray();
        updatePane();
    }

    private void updatePane(){
        placeRectangles(chessPane);
        placeValidFields(chessPane);
        placePiecesGrid(chessPane);
        placeButtons(chessPane);
    }

    private void cleanArray(){
        for (int i = 0; i < piecesPositions.length; i++) {
            for (int j = 0; j < piecesPositions[i].length; j++) {
                piecesPositions[i][j] = piecesPositions[i][j].replace(validField,"");
                piecesPositions[i][j] = piecesPositions[i][j].replace(hitPieceField,"");
            }
        }
    }

    private void placeValidFields(GridPane pane){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if(piecesPositions[i][j].equals(validField)){
                    Circle circle = new Circle(gridSize/4);

                    GridPane.setHalignment(circle, HPos.CENTER);
                    GridPane.setValignment(circle, VPos.CENTER);

                    circle.setFill(HIGLIGHT);
                    circle.setOnMouseClicked(this::onPress);
                    pane.add(circle,j,i);
                }else if(piecesPositions[i][j].contains(hitPieceField)){
                    Rectangle r = new Rectangle(gridSize, gridSize, gridSize, gridSize);
                    r.setFill(HITCOLOR);
                    r.setOnMouseClicked(this::onPress);
                    pane.add(r,j,i);
                }
            }
        }
    }

    private void placeRectangles(GridPane pane){
        int count = 0;
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {
                if (count % 2 == 0) {
                    placeRectangleHere(pane,i,j,WHITE);
                }else{
                    placeRectangleHere(pane,i,j,BLACK);
                }
                count++;


            }
        }
    }

    private void placeRectangleHere(GridPane pane, int i, int j, Color color){
        Rectangle r = new Rectangle(gridSize, gridSize, gridSize, gridSize);
        r.setFill(color);
        r.setOnMouseClicked(this::onPress);
        pane.add(r,j,i);
    }

    private void placeButtons(GridPane pane){
        int count = 0;
        for (int i = 0; i < 8; i++) {
            count++;
            for (int j = 0; j < 8; j++) {

                Button bt = new Button();
                bt.setOnMouseClicked(this::onPress);
                bt.setStyle("-fx-background-color:transparent");
                bt.setPrefSize(gridSize,gridSize);
                pane.add(bt,j,i);
                count++;
            }
        }
    }
    private void placePiecesGrid(GridPane pane){
        for (int i = 0; i < piecesPositions.length; i++) {
            for (int j = 0; j < piecesPositions[i].length; j++) {
                ImageView image = returnNewImage(i,j);
                if(image != null){
                    pane.add(returnNewImage(i,j),j,i);
                }
            }
        }
    }
    private void placeBlackPiecesArray(){
        //Black Pawn
        for (int i = 0; i < 8; i++) {
            piecesPositions[1][i] = blackPawn;
        }

        //Black King
        piecesPositions[0][3] = blackKing;

        //Black Queen
        piecesPositions[0][4] = blackQueen;

        //Black Bishop 1
        piecesPositions[0][2] = blackBishop;

        //Black Bishop 2
        piecesPositions[0][5] = blackBishop;

        //Black Knight 1
        piecesPositions[0][1] = blackKnight;

        //Black Knight 2
        piecesPositions[0][6] = blackKnight;

        //Black Rook 1
        piecesPositions[0][0] = blackRook;

        //Black Rook 2
        piecesPositions[0][7] = blackRook;
    }
    private void placeWhitePiecesArray(){
        //White Pawn
        for (int i = 0; i < 8; i++) {
            piecesPositions[6][i] = whitePawn;
        }

        //White King
        piecesPositions[7][3] = whiteKing;

        //White Queen
        piecesPositions[7][4] = whiteQueen;

        //White Bishop 1
        piecesPositions[7][2] = whiteBishop;

        //White Bishop 2
        piecesPositions[7][5] = whiteBishop;

        //White Knight 1
        piecesPositions[7][1] = whiteKnight;

        //White Knight 2
        piecesPositions[7][6] = whiteKnight;

        //White Rook 1
        piecesPositions[7][0] = whiteRook;

        //White Rook 2
        piecesPositions[7][7] = whiteRook;
    }

    public static void main (String[]args){
            launch();
        }

    public static String returnPieceId(int row,int column){return piecesPositions[row][column];}
    public static ImageView returnNewImage(int row,int column){
        String pieceId = piecesPositions[row][column];
        //black
        switch (pieceId) {
            case blackPawn: {
                ImageView viewPawn = new ImageView(new Image(bPawn));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case blackKing: {
                ImageView viewKing = new ImageView(new Image(bKing));
                viewKing.setFitWidth(gridSize);
                viewKing.setFitHeight(gridSize);
                return viewKing;
            }
            case blackQueen: {
                ImageView viewPawn = new ImageView(new Image(bQueen));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case blackBishop: {
                ImageView viewPawn = new ImageView(new Image(bBishop));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case blackKnight: {
                ImageView viewPawn = new ImageView(new Image(bKnight));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case blackRook: {
                ImageView viewPawn = new ImageView(new Image(bRook));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;

                //white
            }
            case whitePawn: {
                ImageView viewPawn = new ImageView(new Image(wPawn));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case whiteKing: {
                ImageView viewPawn = new ImageView(new Image(wKing));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case whiteQueen: {
                ImageView viewPawn = new ImageView(new Image(wQueen));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case whiteBishop: {
                ImageView viewPawn = new ImageView(new Image(wBishop));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case whiteKnight: {
                ImageView viewPawn = new ImageView(new Image(wKnight));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            case whiteRook: {
                ImageView viewPawn = new ImageView(new Image(wRook));
                viewPawn.setFitWidth(gridSize);
                viewPawn.setFitHeight(gridSize);
                return viewPawn;
            }
            default:
                return null;
        }
    }

    /**
    public void setIsWhite(boolean isWhite){
        this.isWhite = isWhite;
    }**/
}