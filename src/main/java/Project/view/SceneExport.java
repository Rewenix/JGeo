package Project.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SceneExport {
    private DrawingPane drawingPane;
    private Stage stage;

    public SceneExport(Stage stage){
        this.stage = stage;
    }

    public void setDrawingPane(DrawingPane drawingPane){
        this.drawingPane = drawingPane;
    }

    void exportImage(){
        WritableImage image = drawingPane.snapshot(null, null);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.png"));
        File outFile = fileChooser.showSaveDialog(stage);
        if(outFile != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outFile);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
}
