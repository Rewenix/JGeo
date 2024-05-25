package Project.view;

import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;

public class ActorButton extends ToggleButton {

    public ActorButton(String text, String imagePath){
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        this.wrapTextProperty().setValue(true);
        this.textAlignmentProperty().setValue(TextAlignment.CENTER);
        this.setText(text);
        this.setGraphic(imageView);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(170);
        this.setMaxWidth(170);
        this.setMinHeight(170);
        this.setMaxHeight(170);
        this.getStyleClass().add("actor-button");
    }

    public void fire(){
        super.fire();
        this.setSelected(true);
    }
}
