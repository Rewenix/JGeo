package Project.view;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class ActorButtonGroup extends BorderPane {
    private final Label groupName;
    private final FlowPane buttons;

    public ActorButtonGroup(String name){
        groupName = new Label(name);
        groupName.setFont(new Font(30));
        HBox nameBox = new HBox(groupName);
        nameBox.setAlignment(Pos.CENTER);
        buttons = new FlowPane();
        this.setTop(nameBox);
        this.setCenter(buttons);
        this.setMaxWidth(Double.MAX_VALUE);
        this.getStyleClass().add("actor-button-group");
    }

    public void addButton(ButtonBase button){
        buttons.getChildren().add(button);
    }

}
