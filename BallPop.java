import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;

public class BallPop extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    private Ellipse ball;
    private BallAnimation animation;
    private int xVel=1;
    private int yVel=-2;
    private int colorChangeCountdown=180;
    private int active=1;
    private int top;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    @Override
    public void start(Stage primaryStage) {
        Pane root=new Pane();
        Rectangle bg=new Rectangle(0,0,500,500);
        bg.setFill(Color.BLACK);
        int randomX=(int) (Math.random()*301)+100;
        ball=new Ellipse(randomX,550,50,50);
        ball.setFill(Color.RED);
        ball.setOnMouseEntered(new enter());
        animation=new BallAnimation();
        animation.start();
        root.getChildren().addAll(bg,ball);

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
                yVel*=-1;
            }
            y+=yVel;
            if (y==550){
                int randomX=(int) (Math.random()*301)+100;
                ball.setCenterX(randomX);
            }
            ball.setCenterY(y);
            colorChangeCountdown--;
            if (colorChangeCountdown==0){
                ball.setFill(randomColor());
                colorChangeCountdown=180;
            }
        }
    }

    private class enter implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e){

            int randomX=(int) (Math.random()*301)+100;
            ball.setCenterX(randomX);
            ball.setCenterY(600);
            yVel=-2;
        }
    }

    private Color randomColor(){
        Random r=new Random();
        return Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255));
    }
}
