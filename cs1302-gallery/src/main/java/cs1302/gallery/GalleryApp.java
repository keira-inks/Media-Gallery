package cs1302.gallery;

import java.util.Optional;

import cs1302.gallery.ItunesResult;
import cs1302.gallery.ItunesResponse;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.net.URI;

import java.lang.IndexOutOfBoundsException;
import java.io.IOException;
import java.lang.InterruptedException;
import java.lang.IllegalArgumentException;
import javafx.scene.control.ButtonType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;


/**
 * Represents an iTunes Gallery App.
 */
public class GalleryApp extends Application {

    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object
    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object

    private Stage stage;
    private Scene scene;
    private VBox root;
    private MediaBar mediaBar;
    private Label instructions;
    private ImageBoard imgBoard;
    private Footer downloadInfo;
    private int imgCount;
    private String uri;
    private HttpRequest request;
    private HttpResponse<String> response;
    private ItunesResponse itunesResponse;
    private String[] imgURLs;

    /**
     * Constructs a {@code GalleryApp} object}.
     */
    public GalleryApp() {
        this.stage = null;
        this.scene = null;
        this.root = new VBox();
        this.mediaBar = new MediaBar();
        this.instructions = new Label("Type in a term, select a media type,"
        + "then click the button.");
        this.imgBoard = new ImageBoard();
        this.downloadInfo = new Footer();
        this.imgCount = 0;

    } // GalleryApp

    /** {@inheritDoc} */
    @Override
    public void init() {
        // feel free to modify this method
        System.out.println("init() called");
        this.root.getChildren().
            addAll(this.mediaBar,this.instructions,this.imgBoard,this.downloadInfo);

        // GetImages Button ActionEvent
        EventHandler<ActionEvent> getImagesHandler = (ActionEvent e) -> {
            this.clickGetImages(e);
        };
        mediaBar.getImages.setOnAction(getImagesHandler);
        // Play Button ActionEvent
        EventHandler<ActionEvent> playHandler = (ActionEvent a) -> {
            this.clickPlay(a);
        };
        mediaBar.play.setOnAction(playHandler);
    } // init

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(this.root);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.setTitle("GalleryApp!");
        this.stage.setScene(this.scene);
        this.stage.sizeToScene();
        this.stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
    } // start

    /** {@inheritDoc} */
    @Override
    public void stop() {
        // feel free to modify this method
        System.out.println("stop() called");
    } // stop

    /**
     * Event that happens when the Play button is clicked; displayed images will be randomly swapped
     * with images that are already downloaded every two seconds. If button is displayed as "Pause",
     * when clicked again the button displays "Play" again and stops random replacements.
     * @param a event that happens when the "Play" (or pause) button is clicked
     */
    public void clickPlay(ActionEvent a) {

        // If "Play" mode is not yet activated:
        if (mediaBar.play.getText().equals("Play")) {

            //1) Will set button text to "Pause"
            mediaBar.play.setText("Pause");


            // 2) Random replacements every TWO seconds
            // Must first calculate random URL (should be within 0-20 EXCLUDING 20):
            double dRandomIndex1 = Math.floor(((Math.random()) * 20));
            int randomIndex1 = (int) (dRandomIndex1);

            // randomly chosen URL that is ALREADY DISPLAYED
            String chosenDisplayedURL =
                this.imgBoard.imgViewArray[randomIndex1].getImage().getUrl();
            // Now must calculate random URL for image that is NOT displayed
            // (should be within 0-length of alldownloadedimagesarray EXCLUDING LENGTH #
            int numOfDistinctImages = this.numOfDistinctImgs(this.itunesResponse);
            double dRandomIndex2 = Math.floor(((Math.random()) * numOfDistinctImages));
            int randomIndex2 = (int) (dRandomIndex2);
            // Must make sure that URL is not already displayed
            //while it is a duplicate, keep calculating new random image URLs and comparing; will
            // fall out when that random index value is no longer a duplicate
            while (this.generalDuplicate(this.imgBoard, this.itunesResponse
            , randomIndex2)) {
                numOfDistinctImages = this.numOfDistinctImgs(this.itunesResponse);
                dRandomIndex2 = Math.floor(((Math.random()) * numOfDistinctImages));
                randomIndex2 = (int) (dRandomIndex2);
            } // while
            // URL that is not already displayed taken from itunesResponse

            // reassigning displayed image
            imgBoard.imgViewArray[randomIndex1]
                .setImage(new Image(this.imgURLs[randomIndex2]));


        } else if (mediaBar.play.getText().equals("Pause")) {
            // If "Play" mode is activated:
            mediaBar.play.setText("Play");

        } // if

    } // clickPlay

    /**
     * Method that defines that defines what happens when the user clicks the "Get Images" button.
     * @param e event that happens when the "Get Images" button is clicked
     */
    public void clickGetImages(ActionEvent e) {
        mediaBar.getImages.setDisable(true); // 1) Disables the "Get Images" button
        // 2 Instructions message replaced with "Getting images..." messgae
        this.instructions = new Label("Getting images...");
        this.root.getChildren().remove(1);
        this.root.getChildren().add(1,this.instructions);
        // 3 Disabled "Play" button reset to "Play" (if was set to "Pause")
        mediaBar.play.setText("Play");
        // 4 Steps to constructing a request; first forms the uri, then builds the request
        String term = URLEncoder.encode(mediaBar.searchBar.getText(), StandardCharsets.UTF_8);
        String media = URLEncoder.encode(mediaBar.mediaSelect.getValue(), StandardCharsets.UTF_8);
        String limit = URLEncoder.encode("200", StandardCharsets.UTF_8);
        String query = String.format("?term=%s&media=%s&limit=%s", term, media, limit);
        this.uri = "https://itunes.apple.com/search" + query;
        this.request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        try { // send request and receive request as a string
            this.response = HTTP_CLIENT.send(request, BodyHandlers.ofString());
            this.ensureGoodResponse(response);// making sure the received response isn't problematic
            String jsonString = response.body();
            itunesResponse = GSON.fromJson(jsonString, ItunesResponse.class);
            runNow(() -> this.updateProgressBar(downloadInfo));
            // if the response only has 20 or less results, will throw (and catch) an IAE

            if (this.numOfDistinctImgs(itunesResponse) <= 20) {
                throw new IllegalArgumentException(this.numOfDistinctImgs(itunesResponse)
                + " distinct result(s) found, but 21 or more are needed.");
            } // if
            // downloads ALL DISTINCT images into an array before it displays 20 distinct images
            imgURLs = this.downloadAllImages(itunesResponse);
            // displays 20 distinct images

            this.display20Images(this.imgBoard,imgURLs);
            // replaces label with the URL
            this.instructions = new Label(uri);
            this.root.getChildren().remove(1);
            this.root.getChildren().add(1,this.instructions);
        } catch (IllegalArgumentException | IOException | InterruptedException ex) {

            this.instructions = new Label("Last attempt to get images failed...");
            this.root.getChildren().remove(1);
            this.root.getChildren().add(1,this.instructions);
            Alert alert = new Alert(AlertType.ERROR, "URI: " + uri
                + "\n\nException:" + ex.toString());
            Optional<ButtonType> consequence = alert.showAndWait();
        } // try

        // 1) "Play/Pause" button only enabled if there is at least one displayed image
        // that is not the default image
        if (GalleryApp.numOfDefaultImg(imgBoard) != 20) {
            mediaBar.play.setDisable(false);
        } // if
        // "Get Images" button enabled regardless
        mediaBar.getImages.setDisable(false);

    } // clickGetImages


    /**
     * Prints individual {@code imgURL} from a given String array.
     * @param imgURLs the given String array containing image URLs
     */
    private static void printURLs(String[] imgURLs) {
        System.out.println("PRINTING URLs of (hopefully) not duplicate imgURLs: ");
        for (int i = 0; i < imgURLs.length; i++) {
            System.out.println(imgURLs[i]);
        } // for
    } // printURLs

    /**
     * Displays 20 distinct images (the first 20) onto {@code imgBoard} using image URLs from the
     * given {@code imgURLs} String array.
     * @param imgBoard the object that contains the array of imgView objects to display image of URL
     * @param imgURLs the array containing the imgURLs to be displayed
     */
    private static void display20Images(ImageBoard imgBoard, String[] imgURLs) {

        for (int i = 0; i < 20; i++) {
            imgBoard.imgViewArray[i].setImage(new Image(imgURLs[i]));
        } // for

    } // display20Images

    /**
     * Returns the number of DISTINCT URL images from the {@code itunesResponse} query.
     * @param itunesResponse the response
     * @return int number of distinct image URLs
     */
    private int numOfDistinctImgs(ItunesResponse itunesResponse) {
        GalleryApp.removeDuplicates(itunesResponse);
        // counting how many images there are that aren't duplicates for length of array
        int count = 0;
        for (int i = 0; i < itunesResponse.results.length; i++) {
            if (!(GalleryApp.isDuplicateAt(itunesResponse, i))) {
                count++;
            } // if

        } // for
        return count;

    } // numOfDistinctImgs


    /**
     * Downloads every DISTINCT image from the received {@code itunesResponse}.
     * @param itunesResponse the received response from the API query
     * @return String[] the String array containing the distinct image URLs from received response
     */
    private String[] downloadAllImages(ItunesResponse itunesResponse) {
        int numOfDistinct = this.numOfDistinctImgs(itunesResponse);
            // array that will not include duplicate image urls
        String[] updatedResultsURLs = new String[numOfDistinct];

        // copying over URLs
        int j;
        boolean flag;

        for (int i = 0; i < updatedResultsURLs.length; i++) {
            flag = true;
            j = i;
            while (flag) {
                if (GalleryApp.isDuplicateAt(itunesResponse, j)) {
                    j++;
                } else if (i != 0 && GalleryApp.imgViewDuplicate(updatedResultsURLs
                    ,itunesResponse, j)) {
                    j++;
                } else {
                    updatedResultsURLs[i] = itunesResponse.results[j].artworkUrl100;
                    flag = false;
                } // else
            } // while

        } // for
        return updatedResultsURLs;

    } // downloadAllImages


    /**
     * Checks whether or not a URL from {@code itunesResponse} is already present at index {@code i}
     * in {@code imgBoard}.
     * @param imgBoard the display of images
     * @param itunesResponse the response where the URL comes from
     * @param i the index to check
     * @return true if it is a duplicate, returns false if otherwise.
     */
    private boolean generalDuplicate(ImageBoard imgBoard
                    , ItunesResponse itunesResponse, int i) {
        for (int j = 0; j < this.imgBoard.imgViewArray.length
                 && this.imgBoard.imgViewArray[j] != null; j++) {
            if (this.imgBoard.imgViewArray[j].getImage().getUrl()
                .equals(itunesResponse.results[i].artworkUrl100)) {
                return true;
            } // if
        } // for

        return false;
    } // generalDuplicate


    /**
     * Checks to make sure that a URL from an {@code itunesResponse} at index {@code i}
     * of {@code imgURLs} hasn't already been put into new array of 20 image URLs.
     * @param imgURLs String array of every (distinct) image URLs received from response
     * @param itunesResponse received response
     * @param i index
     * @return true if URL has already been put into new array, false if otherwise.
     */
    private static boolean imgViewDuplicate(String[] imgURLs
                    , ItunesResponse itunesResponse, int i) {
        for (int j = 0; j < imgURLs.length && imgURLs[j] != null; j++) {
            if (imgURLs[j].equals(itunesResponse.results[i].artworkUrl100)) {
                return true;
            } // if
        } // for

        return false;
    } // imgViewDuplicate

    /**
     * Updates the progress bar contained within {@code downloadInfo}.
     * @param downloadInfo where the progress bar is contained
     */
    private void updateProgressBar(Footer downloadInfo) {
        downloadInfo.progress.setProgress(this.numOfDefaultImg(imgBoard) / 20);

    } // updateProgressBar

    /**
     * Counts the number of default images currently displayed on {@code imgBoard}.
     * @param imgBoard where default images are displayed
     * @return int number of default images currently displayed
     */
    private static int numOfDefaultImg(ImageBoard imgBoard) {
        int count = 0;
        for (int i = 0; i < 20; i++) {
            // if there is a default image present
            if (imgBoard.imgViewArray[i].getImage().getUrl().equals("file:resources/default.png")) {
                count++;
            } // if
        } // for
        return count;
    } // numOfDefaultImg

    /**
     * Checks if an image URL from the {@code itunesReponse} at {@code index} is a duplicate
     *  (String = "DUPLICATE"). Returns true if it is a duplicate and false if otherwise.
     * @param itunesResponse the received http response
     * @param index the index of the image URL to be checked
     * @return boolean true if image URL is a duplicate, false if otherwise
     */
    private static boolean isDuplicateAt(ItunesResponse itunesResponse,int index) {
        if (itunesResponse.results[index].artworkUrl100.equals("DUPLICATE")) {
            return true;
        } else {
            return false;
        } // if

    } // isDuplicate



    /**
     * Removes duplicate URLs received from the iTunes Search API.
     * @param itunesResponse the received response
     */
    private static void removeDuplicates(ItunesResponse itunesResponse) {
        for (int i = 0; i < itunesResponse.results.length; i++) {
            String temp = (itunesResponse.results[i]).artworkUrl100;
            // evaluating whether or not two URLs are duplicates
            for (int j = i + 1; j < itunesResponse.results.length; j++) {
                if (temp != "DUPLICATE" &&  temp.equals((itunesResponse
                    .results[j]).artworkUrl100)) {
                    // if duplicates, removes second one by setting value of URL to empty string
                    itunesResponse.results[j].artworkUrl100 = "DUPLICATE";
                } // if
            } // for
        } // for

    } // removeDuplicates



    /**
     * Print a response from the iTunes Search API (credit for this method goes to
     * the CS1302 course online book, found in Chapter 16's complete examples, Example 3).
     * @param itunesResponse the response object
     */
    private static void printItunesResponse(ItunesResponse itunesResponse) {
        System.out.println();
        System.out.println("********** PRETTY JSON STRING: **********");
        System.out.println(GSON.toJson(itunesResponse));
        System.out.println();
        System.out.println("********** PARSED RESULTS: **********");
        System.out.printf("resultCount = %s\n", itunesResponse.resultCount);
        for (int i = 0; i < itunesResponse.results.length; i++) {
            System.out.printf("itunesResponse.results[%d]:\n", i);
            ItunesResult result = itunesResponse.results[i];
            System.out.printf(" - wrapperType = %s\n", result.wrapperType);
            System.out.printf(" - kind = %s\n", result.kind);
            System.out.printf(" - artworkUrl100 = %s\n", result.artworkUrl100);
        } // for
    } // printItunesResponse

    /**
     * Throw an {@link java.io.IOException} if the HTTP status code of the
     * {@link java.net.http.HttpResponse} supplied by {@code response} is
     * not {@code 200 OK} (credit for this method goes to
     * the CS1302 course online book, found in Chapter 16's complete examples, Example 3).
     * @param <T> response body type
     * @param response response to check
     * @see <a href="https://httpwg.org/specs/rfc7231.html#status.200">[RFC7232] 200 OK</a>
     */
    private static <T> void ensureGoodResponse(HttpResponse<T> response) throws IOException {
        if (response.statusCode() != 200) {
            throw new IOException(response.toString());
        } // if
    } // ensureGoodResponse

    /**
     * Creates and immediately starts a new daemon thread that executes
     * {@code target.run()}. This method, which may be called from any thread,
     * will return immediately its the caller (credit for this method goes to
     * the CS1302 course online book, found in Chapter 14's example starter code).
     * @param target the object whose {@code run} method is invoked when this
     * thread is started
     */
    public static void runNow(Runnable target) {
        Thread t = new Thread(target);
        t.setDaemon(true);
        t.start();
    } // runNow


} // GalleryApp
