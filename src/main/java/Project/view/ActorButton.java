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
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        this.wrapTextProperty().setValue(true);
        this.textAlignmentProperty().setValue(TextAlignment.CENTER);
        this.setText(text);
        this.setFont(new Font(10));
        this.setGraphic(imageView);
        this.setContentDisplay(ContentDisplay.TOP);
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(120);
        this.setMaxWidth(120);
        this.setMinHeight(120);
        this.setMaxHeight(120);
        this.getStyleClass().add("actor-button");
    }

    public void fire(){
        super.fire();
        this.setSelected(true);
    }
}
