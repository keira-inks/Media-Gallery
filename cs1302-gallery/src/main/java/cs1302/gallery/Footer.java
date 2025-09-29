package cs1302.gallery;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.scene.Node;



/**
 * Represents the bottom bar of the GalleryApp GUI that includes a progress bar and a label
 * crediting the iTunes Search API.
 */
public class Footer extends HBox {

    ProgressBar progress;
    Label credit;

    /**
     * Constructs a Footer object with its children: a progress bar
     * that shows the download progress of the images and a label that gives credit
     * to the iTunes Search API.
     */
    public Footer() {
        super();
        progress = new ProgressBar();
        credit = new Label("Images provided by iTunes Search API.");
        progress.setProgress(0.0);
        progress.setMinWidth(250.0);
        progress.setPrefWidth(250.0);
        progress.setMaxWidth(250.0);
        this.getChildren().addAll(progress,credit);
    } // Footer

} // Footer
