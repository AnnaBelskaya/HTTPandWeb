package ui;

import channel.ChannelResponse;
import com.jfoenix.controls.JFXButton;
import com.mashape.unirest.http.exceptions.UnirestException;
import entity.ActivityResponse;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserActions extends UserInterface {
    private List<ChannelResponse> channelHistory = new ArrayList<ChannelResponse>();
    private List<ActivityResponse> activityHistory = new ArrayList<ActivityResponse>();

    public UserActions(BorderPane root, TilePane tilePane, ScrollPane scrollPane) {
        super(root,tilePane,scrollPane);
        setActions();
    }

    private void setActions(){
        search.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    setResponse();
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        });

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                root.setCenter(scrollPane);
            }
        });

        channel_title.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    root.setCenter(new VBox(back, new ChannelInfo(channelResponse).infoBox()));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        history.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                showHistory();
            }
        });
    }

    private void setResponse() throws UnirestException {
        if (max_results_input.getText().equals("")) results = 20;
        else results = Integer.parseInt(max_results_input.getText());

        channelResponse = getChannelInfo();
        channelHistory.add(channelResponse);
        activityResponse = getVideosInfo();
        activityHistory.add(activityResponse);

        show_video_list(channelResponse, activityResponse);
    }

    private void show_video_list(ChannelResponse channelResponse, ActivityResponse activityResponse) throws UnirestException {
        channel_title.setText("CHANNEL TITLE\n" + channelResponse.items.get(0).snippet.title);
        channel_title.setStyle("-fx-font-size: 16pt;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #7d0000;");

        channel_title.setTranslateX(10);
        channel_title.setTranslateY(10);
        channel_title.setGraphic(new ImageView(new Image(
                    "https://addons.cdn.mozilla.net/user-media/addon_icons/479/479484-64.png?modified=1506946821")));

        tilePane.getChildren().clear();
        tilePane.getChildren().addAll(channel_title);


        final ActivityResponse ACTIVITY = activityResponse;
        for (int i = 0; i < results; i++) {
            final int I = i;
            new Thread(new Runnable() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            try {
                                tilePane.getChildren().add(
                                        new VideoInfo(history,ACTIVITY.items.get(I),
                                                root, scrollPane, days_input.getText()).getBox());
                            } catch (NullPointerException ex) {}
                        }
                    });
                }
            }).start();

        }
    }

    private void showHistory(){
        tilePane.getChildren().clear();

        int size = activityHistory.size();
        if (size == 0){
            tilePane.getChildren().clear();
            Label empty = new Label("The history is empty.");
            tilePane.getChildren().add(empty);
            return;
        }

        for (int i = 0; i < size; i++){
            JFXButton title = new JFXButton(channelHistory.get(i).items.get(0).snippet.title);
            title.setGraphic(ImageUtils.crop(
                    channelHistory.get(i).items.get(0).snippet.thumbnails.medium.url));
            title.setStyle("-fx-font-size: 16pt;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #7d0000;");

            final ActivityResponse ACTIVITY = activityHistory.get(i);
            final ChannelResponse CHANNEL = channelHistory.get(i);

            title.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    try {
                        show_video_list(CHANNEL, ACTIVITY);
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    }
                }
            });

            tilePane.getChildren().add(title);
        }
    }
}