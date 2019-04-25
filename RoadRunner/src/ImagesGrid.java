public class ImagesGrid {
    private Images [] imagesGrid;

    String [] images = new String[]{"road", "boulder", "pothole", "explosive", "coyote", "tarred", "gold",
        "start", "road_runner", "goal"};

    int [] weights = new int[]{-1, 0, -1, -4, -8, 1, 5, 0, 0, 0};


    public ImagesGrid(int num) throws Exception{
        imagesGrid = new Images[num];
        createImagesGrid();
    }

    public void createImagesGrid() throws Exception{
        int counter = 0;
        String altImage;
        for(String image: this.images){
            if (image.equals("boulder") || image.equals("goal") || image.equals("start")){
                altImage = null;
            }
            else if(image.equals("road_runner")){
                altImage = "start";
            }
            else{
                altImage = image + "_alt";
            }
            Images imageObect = new Images(image , altImage, weights[counter]);
            this.imagesGrid[counter] = imageObect;
            counter += 1;
        }
    }

    public Images[] getImagesGrid(){
        return this.imagesGrid;
    }
}
