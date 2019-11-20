package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;


public class Assignment4 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Stage
        Group root = new Group();
        primaryStage.setTitle("HUBBM-Racer");
        Scene theScene = new Scene(root, 750, 850);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(750, 850);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill( Color.WHITE );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 40 );
        gc.setFont( theFont );
        //

        ArrayList<Road> roads = new ArrayList<>();
        ArrayList<Car> rivalCars = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        ArrayList<Boolean> gameover = new ArrayList<>();

        Car myCar = new Car(false);

        Road firstRoad = new Road(0, -850);
        Road secondRoad = new Road(0, 0);

        roads.add(firstRoad);
        roads.add(secondRoad);

        Image blackcar=new Image("black.png");
        Image yellowcar=new Image("yellow.png");
        Image pinkcar=new Image("pink.png");
        Image greencar=new Image("green.png");

        for (int i = 0; i < 3; i++) {
            rivalCars.add(new Car(true));
        }

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!input.contains(event.getCode().toString())) {
                    input.add(event.getCode().toString());
                }
            }
        });

        theScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                input.remove(event.getCode().toString());
            }
        });


        new AnimationTimer() {

            int score=0;
            int level=1;
            boolean check = false;

            public void handle(long now) {
                if (!gameover.contains(true)) {
                    gc.clearRect(0, 0, 750, 850);
                    if (input.contains("UP")) {
                        for (Car c : rivalCars) {
                            c.increaseVelocity();
                        }

                        for (Road r : roads) {
                            r.increaseVelocity();
                        }

                    } else if (input.contains("DOWN")) {
                        for (Car c : rivalCars) {
                            if(c.getVelocityY()>0){
                                c.decreaseVelocity();
                            }

                        }
                        for (Road r : roads) {
                            if(r.getVelocityY()>0){
                                r.decreaseVelocity();
                            }

                        }
                    } else if (input.contains("RIGHT")) {
                        myCar.moveRight();

                    } else if (input.contains("LEFT")) {
                        myCar.moveLeft();
                    }

                    for (Road r : roads) {
                        r.move();
                    }

                    for (Road r : roads) {
                        r.render(gc);
                    }

                    detectIntersect(rivalCars);

                    for (Car c : rivalCars) {
                        c.moveForward();
                        if(myCar.getPositionY()<c.getPositionY()){
                            c.setImage(greencar);
                            c.setBehind(true);
                        }
                        else{
                            if(c.isBehind()){
                                score+=1;

                            }
                            c.setBehind(false);

                            c.setImage(yellowcar);
                        }

                        level=score/3+1;

                        for (Road r : roads) {
                            if(r.getVelocityY()>0){
                                r.setVelocityY(10+level*2);
                            }
                        }

                        c.setVelocityY(6+level);


                        if (c.intersects(myCar)){
                            myCar.setImage(blackcar);
                            c.setImage(blackcar);
                            gameover.add(true);
                            check = true;

                        }

                        c.render(gc);

                    }
                    if (check){
                        String gameOver = "GAME OVER!";
                        String yourScore = "Your Score : " + score;
                        String restart = "Press ENTER to restart!";
                        gc.fillText(gameOver,250,200);
                        gc.fillText(yourScore,250,250);
                        gc.fillText(restart,175,350);
                    }


                    String points = "Score : "+ score;
                    String levels = "Level : " + level;
                    gc.fillText( points ,60, 35 );
                    gc.fillText( levels ,60, 70 );

                    myCar.render(gc);


                } else {
                    if (input.contains("ENTER")){
                        gameover.remove(true);
                        restartGame(roads,rivalCars,myCar,yellowcar,pinkcar);
                        level = 1;
                        score = 0;
                        check = false;
                    }
                }
            }
        }.start();

        primaryStage.show();
    }


    public void restartGame(ArrayList<Road> roads,ArrayList<Car> rivalCars, Car myCar,Image yellowcar,Image pinkcar){
        roads.get(0).reset(0,-850);
        roads.get(1).reset(0,0);

        for (Car c: rivalCars){
            c.setPosition();
            c.setImage(yellowcar);
        }
        myCar.setImage(pinkcar);
        myCar.setPositionX((750 - 85) / 2);
    }

    public void detectIntersect(ArrayList<Car> rivalCars) {
        while (true) {
            boolean first = rivalCars.get(0).intersects(rivalCars.get(1));
            boolean second = rivalCars.get(0).intersects(rivalCars.get(2));
            boolean third = rivalCars.get(2).intersects(rivalCars.get(1));
            if ((rivalCars.get(0).intersects(rivalCars.get(1)) || rivalCars.get(0).intersects(rivalCars.get(2))
                    || rivalCars.get(2).intersects(rivalCars.get(1)))) {
                if (first) {
                    rivalCars.get(0).setPosition();
                    rivalCars.get(1).setPosition();
                }
                if (second) {
                    rivalCars.get(0).setPosition();
                    rivalCars.get(2).setPosition();
                }
                if (third) {
                    rivalCars.get(1).setPosition();
                    rivalCars.get(2).setPosition();
                }
            }
            else
                break;
        }
    }
}
