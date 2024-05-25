package Project.view;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ActorButton extends ToggleButton {

    public ActorButton(String text, String imagePath){
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        this.wrapTextProperty().setValue(true);
        this.textAlignmentProperty().setValue(TextAlignment.CENTER);
        this.setText(text);
        this.setFont(new Font(7));
        this.setGraphic(imageView);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(100);
        this.setMaxWidth(100);
        this.setMinHeight(100);
        this.setMaxHeight(100);
        this.getStyleClass().add("actor-button");
    }

    public void fire(){
        super.fire();
        this.setSelected(true);
    }
}
