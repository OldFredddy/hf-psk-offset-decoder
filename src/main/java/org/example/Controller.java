package org.example;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javax.imageio.IIOException;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label mainLabel;

    @FXML
    private AnchorPane myPane;

    @FXML
    private ScrollPane parentPane;

    @FXML
    private SplitPane superParentPane;

    @FXML
    private VBox vbMenu;

    @FXML
    void initialize() throws IIOException {
        myPane.setOnDragOver(event -> {
            if (event.getGestureSource() != myPane && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        AtomicReference<String> result= new AtomicReference<>("");
        myPane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                result.set(Decode.processFile(db.getFiles().get(0).toPath()));
                mainLabel.setText(result.get());
            }
            event.setDropCompleted(success);
            event.consume();
        });
        mainLabel.setOnDragOver(event -> {
            if (event.getGestureSource() != mainLabel && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        mainLabel.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                result.set(Decode.processFile(db.getFiles().get(0).toPath()));
                mainLabel.setText(result.get());
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}
