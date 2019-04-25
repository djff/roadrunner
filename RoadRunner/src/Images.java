import javafx.scene.image.Image;

import java.awt.*;
import java.io.FileInputStream;

public class Images {
    private String imageName;
    private String altImageName;
    private Image image;
    private Image altImage;
    private int weight;

    public Images(String imageName, String altImageName, int weight) throws Exception{
        this.image = new Image(new FileInputStream("./Images/" + imageName + ".jpg"));

        if (altImageName != null){
            this.altImage = new Image(new FileInputStream("./Images/" + altImageName + ".jpg"));
        }
        this.imageName = imageName;
        this.altImageName = altImageName;
        this.weight = weight;
    }

    public String getImageName(){
        return this.imageName;
    }

    public String getAltImageName(){
        return this.altImageName;
    }

    public Image getImage(){
        return this.image;
    }

    public Image getAltImage(){
        return this.altImage;
    }

    public int getWeight(){
        return this.weight;
    }
}
