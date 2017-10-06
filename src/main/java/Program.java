import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.UserActions;

import java.io.IOException;

public class Program extends Application {
    public static BorderPane root = new BorderPane();
    public static ScrollPane scrollPane = new ScrollPane();
    public static TilePane tilePane = new TilePane();

    public void start(Stage stage) throws Exception {
        initMapper();

        tilePane.setPadding(new Insets(10,10,10,15));
        tilePane.setPrefColumns(2);
        tilePane.setVgap(20);
        tilePane.setHgap(20);
        scrollPane.setContent(tilePane);
        root.setCenter(scrollPane);
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNIFIED);
        stage.show();

        new UserActions(root, tilePane, scrollPane);
    }

    private static void initMapper() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
