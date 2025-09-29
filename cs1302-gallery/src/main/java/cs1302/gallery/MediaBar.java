package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Priority;


/**
 * Represents the top bar of the GalleryApp GUI where the user can type in a term, select a
 * media type, and press a button to search for images from chosen media type.
 */
public class MediaBar extends HBox {
    protected Button play;
    protected Label searchLabel;
    protected TextField searchBar;
    protected ComboBox<String> mediaSelect;
    protected Button getImages;

    /**
     * Constructs the MediaBar object with its children: a "Play" and "Get Images" button, a search
     * bar, and a selection box.
     */
    public MediaBar() {
        super();
        // Play Button properties
        play = new Button("Play"); // default state is disabled
        this.setHgrow(play,Priority.ALWAYS);
        play.setDisable(true);


        // Label properties
        searchLabel = new Label("Search:");
        searchLabel.setMinHeight(30.0);
        searchLabel.setPrefHeight(30.0);
        searchLabel.setMaxHeight(30.0);
        //searchLabel.setCenterShape(true);
        this.setHgrow(searchLabel,Priority.ALWAYS);

        // TextField properties
        searchBar = new TextField("muse");
        this.setHgrow(searchBar,Priority.ALWAYS);

        // ComboBox Properties
        mediaSelect = new ComboBox<>();
        this.setHgrow(mediaSelect,Priority.ALWAYS);
        mediaSelect.getItems().addAll("movie","podcast","music","musicVideo",
            "audioBook","shortFilm","tvShow","software","ebook","all");
        mediaSelect.setValue("music");

        // GetImages Button properties
        getImages = new Button("Get Images");

        // Adding all nodes to the HBox object
        this.getChildren().addAll(play,searchLabel,searchBar,mediaSelect,getImages);

    } // MediaBar

} // MediaBar
