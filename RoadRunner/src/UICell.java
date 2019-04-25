import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

public class UICell {
    ImageView imageView;
    Images imageObject;
    Image temp = null;
    boolean visited = false;
    int x, y;
    public UICell(int x, int y, Image image, Images imageObject){
        imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        this.imageObject = imageObject;
        this.x = x;
        this.y = y;
    }

    public void setImageView(Image image){
        temp = image;
        imageView.setImage(temp);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setVisited(boolean value){
        visited = value;
    }

   }
