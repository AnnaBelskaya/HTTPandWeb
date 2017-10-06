package utils;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.util.concurrent.ExecutionException;

public class VideoUtils {
    private static String AUTO_PLAY = "?autoplay=1";
    private static String URL = "http://www.youtube.com/embed/";
    private static BorderPane root;
    private static ScrollPane scrollPane;
    private static WebView webView;

    private VideoUtils(){}

    public static void play(String id, BorderPane bPane, ScrollPane sPane,
                            final JFXButton history) throws ExecutionException, InterruptedException {
        root = bPane;
        scrollPane = sPane;

        webView = new WebView();
        webView.getEngine().load(URL + id + AUTO_PLAY);
        webView.setPrefSize(640, 390);

        JFXButton close = new JFXButton("X close");
        close.setStyle("-fx-font-size: 12pt;");
        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                root.setBottom(null);
                root.setCenter(scrollPane);
                root.setBottom(history);
                webView.getEngine().load(null);
            }
        });

        root.setCenter(webView);
        root.setBottom(close);
        close.setTranslateX(root.getPrefWidth()/2);
    }
}
