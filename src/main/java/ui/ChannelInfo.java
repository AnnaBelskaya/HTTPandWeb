package ui;

import channel.ChannelResponse;
import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ChannelInfo {
    private ChannelResponse channelResponse;

    public ChannelInfo(ChannelResponse channelResponse) {
        this.channelResponse = channelResponse;
    }

    public HBox infoBox() throws ExecutionException, InterruptedException {
        FutureTask<HBox> futureTask = new FutureTask<HBox>(new Callable<HBox>() {
            public HBox call() throws Exception {
                ImageView imageView = new ImageView(new Image(
                        channelResponse.items.get(0).snippet.thumbnails.medium.url));

                JFXTextArea video_title = new JFXTextArea(channelResponse.items.get(0).snippet.title);
                video_title.setEditable(false);
                video_title.setMaxHeight(15);
                video_title.setFocusColor(Color.TRANSPARENT);
                video_title.setUnFocusColor(Color.TRANSPARENT);
                video_title.setStyle("-fx-font-size: 13pt;");

                JFXTextArea video_description = new JFXTextArea(channelResponse.items.get(0).snippet.description);
                video_description.setEditable(false);
                video_description.setMaxHeight(400);
                video_description.setFocusColor(Color.TRANSPARENT);
                video_description.setUnFocusColor(Color.TRANSPARENT);
                video_description.setStyle("-fx-font-size: 11pt;");

                Label videosCount = new Label("Videos\t" + channelResponse.items.get(0).statistics.videoCount);
                videosCount.setStyle("-fx-font-size: 11pt;" +
                        "-fx-font-style: italic;" +
                        "-fx-text-fill: dimgray;" +
                        "-fx-font-family: cursive;");
                Label viewCount = new Label("Views\t" + channelResponse.items.get(0).statistics.viewCount);
                viewCount.setStyle("-fx-font-size: 11pt;" +
                        "-fx-font-style: italic;" +
                        "-fx-text-fill: dimgray;" +
                        "-fx-font-family: cursive;");
                Label commentsCount = new Label("Comments\t" +channelResponse.items.get(0).statistics.commentCount);
                commentsCount.setStyle("-fx-font-size: 11pt;" +
                        "-fx-font-style: italic;" +
                        "-fx-text-fill: dimgray;" +
                        "-fx-font-family: cursive;");
                Label subsCount = new Label("Subscribers\t" + channelResponse.items.get(0).statistics.subscriberCount);
                subsCount.setStyle("-fx-font-size: 11pt;" +
                        "-fx-font-style: italic;" +
                        "-fx-text-fill: dimgray;" +
                        "-fx-font-family: cursive;");

                VBox v1 = new VBox(video_title, videosCount, viewCount,
                        commentsCount, subsCount, video_description);
                v1.setSpacing(20);
                v1.setSpacing(15);

                HBox hBox = new HBox(imageView, v1);
                hBox.setPadding(new Insets(10,10,10,10));
                hBox.setSpacing(10);

                return hBox;
            }
        });

        new Thread(futureTask).start();
        return futureTask.get();
    }
}
