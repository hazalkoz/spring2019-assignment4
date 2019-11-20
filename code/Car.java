package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Car {
    private boolean isBehind = false;
    private Image image;
    private int velocityX;
    private int velocityY;
    private int positionX;
    private int positionY;
    private int width = 85;
    private int height = 170;

    public Car(boolean isRival) {

        if (isRival) {
            setImage(new Image("yellow.png"));
            setPosition();
            setVelocityY(6);
        } else {
            setPositionX((750 - 85) / 2);
            setPositionY(850 - getHeight());
            setVelocityX(4);
            setVelocityY(0);
            setImage(new Image("pink.png"));
        }
    }

    public void setPosition() {
        Random rand = new Random();
        setPositionX(rand.nextInt(573) + 50);
        setPositionY(-rand.nextInt(500) - 180);
    }

    public void moveForward() {
        if(getVelocityY() < 30){
            setPositionY(getPositionY() + getVelocityY());
        }

        if (getPositionY() > 850) {
            setPosition();
        }
    }

    public void moveRight() {
        if  (getPositionX() < 623) {
            setPositionX(getPositionX() + getVelocityX());
        }
    }

    public void moveLeft() {
        if (50 < getPositionX()) {
            setPositionX(getPositionX() - getVelocityX());
        }
    }

    public void increaseVelocity() {
        setVelocityY(getVelocityY() + 1);
    }

    public void decreaseVelocity() {
        if(getVelocityY()>0){
            setVelocityY(getVelocityY() - 1);
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(getImage(), getPositionX(), getPositionY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(getPositionX(), getPositionY(), getWidth() - 20, getHeight() - 35);
    }

    public boolean intersects(Car c) {
        return c.getBoundary().intersects(this.getBoundary());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }


    public boolean isBehind() {
        return isBehind;
    }

    public void setBehind(boolean behind) {
        isBehind = behind;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }
}
