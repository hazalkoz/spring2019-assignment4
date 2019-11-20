package sample;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
public class Road {
    private Image image = new Image("road.png");
    private double velocityY = 10;
    private int positionX;
    private double positionY;

    public Road(int positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void move() {
        positionY += getVelocityY();
        if (positionY > 850){
            positionY = -850 + positionY - 850;
        }
    }

    public void increaseVelocity(){
        setVelocityY(getVelocityY() + 1);
    }


    public void decreaseVelocity(){
        setVelocityY(getVelocityY() - 1);
    }

    public void render(GraphicsContext gc){
        gc.drawImage(image, positionX, positionY);
    }

    public void reset(int positionX, int positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}


