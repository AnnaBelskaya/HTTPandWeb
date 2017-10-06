package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import entity.Activity;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import utils.ImageUtils;
import utils.VideoUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class VideoInfo {
    private JFXButton history;
    private Activity item;
    private BorderPane root;
    private ScrollPane scrollPane;
    private String days = "";

    public VideoInfo(JFXButton history, Activity item, BorderPane root, ScrollPane scrollPane, String days) {
        this.history = history;
        this.item = item;
        this.root = root;
        this.scrollPane = scrollPane;
        this.days = days;
    }

    public HBox getBox(){
        if (days.equals("") || checker())
            return infoBox();
        else {
            return null;
        }
    }

    private HBox infoBox(){
        ImageView imageView = ImageUtils.crop(
                item.snippet.thumbnails.medium.url);

        JFXButton view = new JFXButton("View");
        view.setStyle("-fx-background-color: red;" +
                "-fx-border-color: #691211;" +
                "-fx-border-radius: 5,5,5,5;");
        view.setTextFill(Color.WHITE);
        view.setMinWidth(150);
        view.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            VideoUtils.play(item.contentDetails.upload.videoId,
                                    root, scrollPane, history);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        VBox v1 = new VBox(imageView, view);
        v1.setTranslateY(10);
        v1.setTranslateX(10);

        JFXTextArea video_title = new JFXTextArea(item.snippet.title);
        video_title.setEditable(false);
        video_title.setMaxHeight(60);
        video_title.setFocusColor(Color.TRANSPARENT);
        video_title.setUnFocusColor(Color.TRANSPARENT);
        video_title.setStyle("-fx-font-style: italic;" +
                "-fx-font-size: 13pt;");

        JFXTextArea video_description = new JFXTextArea(item.snippet.description);
        video_description.setEditable(false);
        video_description.setMaxHeight(100);
        video_description.setFocusColor(Color.TRANSPARENT);
        video_description.setUnFocusColor(Color.TRANSPARENT);
        video_description.setStyle("-fx-font-size: 12pt;");

        Label date = new Label("Published at: " + item.snippet.publishedAt.substring(0,10));
        VBox v2 = new VBox(video_title, date, video_description);
        v2.setMinHeight(200);
        v2.setMaxWidth(250);
        v2.setSpacing(10);

        HBox hBox = new HBox(v1,v2);
        hBox.setSpacing(20);
        hBox.setStyle("-fx-background-color: white;" +
                "-fx-border-color: lightslategray;" +
                "-fx-border-radius: 10,10,10,10;");

        return hBox;
    }

    private boolean checker(){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = myFormat.parse(item.snippet.publishedAt.substring(0,10));
            Date date2 = myFormat.parse(myFormat.format(new Date()));
            long diff = date2.getTime() - date1.getTime();
            if (diff / (1000*60*60*24) > Integer.parseInt(days))
                return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}