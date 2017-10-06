package ui;

import channel.ChannelResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import entity.ActivityResponse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class UserInterface {
    protected TilePane tilePane;
    protected BorderPane root;
    protected ScrollPane scrollPane;

    protected String apiKey = "AIzaSyDpjUAUHTfwenqP67d1KaaY7DRIJXQ9p4Q";

    protected ChannelResponse channelResponse = null;
    protected ActivityResponse activityResponse = null;

    protected boolean isAdvanced = false;
    protected int results = 10;

    protected JFXTextField channel_id_input = new JFXTextField();
    protected JFXTextField max_results_input = new JFXTextField();
    protected JFXTextField days_input = new JFXTextField();

    protected JFXButton search = new JFXButton("Search");
    protected JFXButton back = new JFXButton("Back to results");
    protected JFXButton channel_title = new JFXButton();
    protected JFXButton history = new JFXButton("History");

    private HBox hBox = new HBox();


    public UserInterface(BorderPane root, TilePane tilePane, ScrollPane scrollPane) {
        this.root = root;
        this.tilePane = tilePane;
        this.scrollPane = scrollPane;
        setElements();
    }

    private void setElements(){
        ImageView backIMG = new ImageView(new Image("http://xnodenet.com/icons/left_arrow_grey_icon_100.gif"));
        backIMG.setFitHeight(50);
        backIMG.setPreserveRatio(true);
        back.setGraphic(backIMG);
        back.setStyle("-fx-font-size: 12pt;");

        ImageView imageView = new ImageView
                (new Image("https://www.seeklogo.net/wp-content/uploads/2016/06/YouTube-icon-400x400.png"));
        imageView.setFitHeight(50);
        imageView.setTranslateX(30);
        imageView.setPreserveRatio(true);

        channel_id_input.setPromptText("Channel ID");
        channel_id_input.setFocusColor(Color.DIMGRAY);
        channel_id_input.setMinWidth(600);
        channel_id_input.setMinHeight(20);
        channel_id_input.setStyle("-fx-font-size: 11pt;" +
                "-fx-background-color: white;" +
                "-fx-border-insets: 10pt,10pt,10pt,10pt;" +
                "-fx-border-radius: 0pt,0pt,0pt,0pt;");

        search.setStyle("-fx-background-color: ghostwhite;" +
                "-fx-font-size: 11pt;" +
                "-fx-color-label-visible: true;");

        max_results_input.setPromptText("max.results");
        max_results_input.setStyle("-fx-font-size: 10pt;");
        max_results_input.setFocusColor(Color.DIMGRAY);
        max_results_input.setMaxWidth(100);
        max_results_input.setTranslateY(10);
        max_results_input.setStyle("-fx-font-size: 11pt;" +
                "-fx-background-color: white;" +
                "-fx-border-insets: 10pt,10pt,10pt,10pt;" +
                "-fx-border-radius: 0pt,0pt,0pt,0pt;");

        max_results_input.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });

        days_input.setPromptText("days");
        days_input.setStyle("-fx-font-size: 10pt;");
        days_input.setFocusColor(Color.DIMGRAY);
        days_input.setMaxWidth(100);
        days_input.setTranslateY(10);
        days_input.setStyle("-fx-font-size: 11pt;" +
                "-fx-background-color: white;" +
                "-fx-border-insets: 10pt,10pt,10pt,10pt;" +
                "-fx-border-radius: 0pt,0pt,0pt,0pt;");

        days_input.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });

        JFXToggleButton toggleButton = new JFXToggleButton();
        toggleButton.setToggleColor(Color.CORAL);
        toggleButton.setUnToggleColor(Color.GREEN);
        toggleButton.setTranslateY(-10);

        toggleButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (isAdvanced) {
                    setSimple();
                    max_results_input.clear();
                    days_input.clear();
                } else
                    setAdvanced();
                isAdvanced = !isAdvanced;
            }
        });


        HBox searchBox = new HBox(channel_id_input, search, toggleButton);
        searchBox.setTranslateY(10);
        searchBox.setSpacing(10);

        hBox.getChildren().addAll(imageView, searchBox);
        hBox.setSpacing(40);
        hBox.setMinSize(900,20);
        hBox.setStyle("-fx-background-color: #424242;");

        root.setTop(hBox);

        ImageView historyImage = new ImageView(new Image(
                "http://www.free-icons-download.net/images/history-button-17037.png"));
        historyImage.setFitHeight(40);
        historyImage.setPreserveRatio(true);
        history.setGraphic(historyImage);
        root.setBottom(history);
    }

    private void setAdvanced(){
        channel_id_input.setMinWidth(300);
        hBox.getChildren().addAll(max_results_input, days_input);
    }

    private void setSimple(){
        channel_id_input.setMinWidth(600);
        hBox.getChildren().removeAll(max_results_input,days_input);
    }

    public ChannelResponse getChannelInfo() throws UnirestException {
        HttpResponse<ChannelResponse> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", "AIzaSyCPALiCXC-2GmLoJySc-AdyxW63vpSki6E")
                .queryString("part", "snippet,contentDetails,statistics")
                .queryString("id", channel_id_input.getText())
                .asObject(ChannelResponse.class);
        return response.getBody();
    }

    public ActivityResponse getVideosInfo() throws UnirestException {
        HttpResponse<ActivityResponse> response = Unirest.get("https://www.googleapis.com/youtube/v3/activities")
                .queryString("key", apiKey)
                .queryString("maxResults", results)
                .queryString("part", "snippet,contentDetails")
                .queryString("channelId", channel_id_input.getText())
                .asObject(ActivityResponse.class);
        return response.getBody();
    }
}