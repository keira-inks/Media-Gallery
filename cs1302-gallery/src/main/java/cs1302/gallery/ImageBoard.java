package cs1302.gallery;

import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the tile board where the queried images from the GalleryApp GUI will show up on.
 *
 */
public class ImageBoard extends TilePane {

    Image defaultImg;
    ImageView[] imgViewArray;

    /**
     * Creates an ImageBoard object that has Image and ImageView array child nodes.
     */
    public ImageBoard() {
        super();
        defaultImg = new Image("file:resources/default.png");
        imgViewArray = new ImageView[20];
        for (int i = 0; i < 20; i++) {
            imgViewArray[i] = (new ImageView(defaultImg));
            this.getChildren().add(imgViewArray[i]);
        } // for

    } // ImageBoard

} // ImageBoard
