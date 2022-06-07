import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import java.util.Random;
import java.util.Scanner;

public class BallPop extends Application {

    public static void main(String[] args) {

        Scanner in=new Scanner(System.in);
        System.out.println("Do you want the game to be slow, normal or fast?");
        String speed=in.next();
        if (speed.equalsIgnoreCase("slow")){
            spd=-3;
        }
        else if (speed.equalsIgnoreCase("normal")){
            spd=-5;
        }
        else if (speed.equalsIgnoreCase("fast")){
            spd=-10;
        }
        launch(args);
    }
    public static int spd=0;
    private Ellipse ball;
    private BallAnimation animation;
    private int xVel=1;
    private int yVel=spd;
    private int colorChangeCountdown=180;
    private int active=1;
    private int top;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private Button button;
    private Pane root;
    private RotateTransition rotate;
    private Rectangle bgTemp;
    private Text score;
    private static int scoreNum=0;
    private static int hearts=3;
    private Rectangle heart1;
    private Rectangle heart2;
    private Rectangle heart3;
    private Text startText;
    private Text win;
    private Text lose;
    private Rectangle wlBG;

    @Override
    public void start(Stage primaryStage) {
        root=new Pane();

        //Background
        Rectangle bg=new Rectangle(0,0,500,500);
        bg.setFill(Color.BLACK);
        int randomX=(int) (Math.random()*301)+100;

        //Ball
        ball=new Ellipse(randomX,550,50,50);
        ball.setFill(randomColor());
        ball.setOnMouseEntered(new enter());

        //Score
        score = new Text(10,32,"Score = "+scoreNum);
        score.setFill(Color.WHITE);
        score.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,30));

        //Heart1
        Image h1=new Image("https://i.pinimg.com/originals/4a/2e/6f/4a2e6f1a8aca127b9542d7ddd6ce3a3b.jpg",50,50,true,true);
        ImageInput h1input=new ImageInput();
        h1input.setSource(h1);
        h1input.setX(450);
        h1input.setY(0);
        heart1=new Rectangle();
        heart1.setEffect(h1input);

        //Heart2
        Image h2=new Image("https://i.pinimg.com/originals/4a/2e/6f/4a2e6f1a8aca127b9542d7ddd6ce3a3b.jpg",50,50,true,true);
        ImageInput h2input=new ImageInput();
        h2input.setSource(h2);
        h2input.setX(400);
        h2input.setY(0);
        heart2=new Rectangle();
        heart2.setEffect(h2input);

        //Heart3
        Image h3=new Image("https://i.pinimg.com/originals/4a/2e/6f/4a2e6f1a8aca127b9542d7ddd6ce3a3b.jpg",50,50,true,true);
        ImageInput h3input=new ImageInput();
        h3input.setSource(h3);
        h3input.setX(350);
        h3input.setY(0);
        heart3=new Rectangle();
        heart3.setEffect(h3input);

        //Temporary background
        bgTemp=new Rectangle(0,0,500,500);

        //Button to start the game and text
        button = new Button("Start");
        button.setLayoutX(220);
        button.setLayoutY(250);
        button.setOnAction(new GameStart());
        startText=new Text(90,20,"Pop the ball 10 times before you run out of lives!!!");
        startText.setFill(Color.WHITE);

        //Win or Lose
        wlBG=new Rectangle(0,0,500,500);
        wlBG.setFill(Color.BLACK);
        win=new Text(180,250,"You Won!");
        win.setFill(Color.WHITE);
        win.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,30));

        lose=new Text(180,250,"You Lost!");
        lose.setFill(Color.WHITE);
        lose.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR,30));

        //Animation and setting children
        animation=new BallAnimation();
        root.getChildren().addAll(bg,win,lose,wlBG,ball,score,heart1,heart2,heart3,bgTemp,startText,button);

        Scene scene=new Scene(root,500,500);
        primaryStage.setTitle("Fruit Ninja");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class BallAnimation extends AnimationTimer {
        @Override
        public void handle(long now){
            double x=ball.getCenterX();
            double y=ball.getCenterY();
            top=(int) (Math.random()*76) + 75;

            if (y+yVel<=top){
                yVel*=-1;
            }
            if (y+yVel==600){
                hearts--;
                if (hearts==2){
                    root.getChildren().remove(heart3);
                }
                else if (hearts==1){
                    root.getChildren().remove(heart2);
                }
                else if(hearts==0){
                    root.getChildren().remove(heart1);
                    root.getChildren().removeAll(ball,score,wlBG,win);
                    animation.stop();
                }
                yVel*=-1;
            }
            y+=yVel;
            if (y==550){
                int randomX=(int) (Math.random()*301)+100;
                ball.setCenterX(randomX);
            }
            ball.setCenterY(y);
        }
    }

    private class enter implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e){
            ball.setFill(randomColor());
            scoreNum+=1;
            score.setText("Score = "+scoreNum);
            if (scoreNum>=10){
                animation.stop();
                root.getChildren().removeAll(ball,score,heart1,heart2,heart3,wlBG,lose);
            }
            int randomX=(int) (Math.random()*301)+100;
            ball.setCenterX(randomX);
            ball.setCenterY(600);
            yVel=spd;
        }
    }

    private LinearGradient randomColor(){
        Random r=new Random();
        Color col1=Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        Color col2=Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255));
        Stop[] stops = new Stop[] { new Stop(0, col1), new Stop(1, col2)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        return lg1;
    }

    private class GameStart implements EventHandler<ActionEvent>{
        @Override
        public void handle (ActionEvent e){
            root.getChildren().remove(bgTemp);
            root.getChildren().remove(button);
            root.getChildren().remove(startText);
            rotate=new RotateTransition();
            rotate.setNode(ball);
            rotate.setAxis(Rotate.Z_AXIS);
            rotate.setByAngle(360);
            rotate.setDuration(Duration.millis(3000));
            rotate.setCycleCount(500);
            animation.start();
            rotate.play();
        }
    }
}
