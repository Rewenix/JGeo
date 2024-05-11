package Project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewTesting extends Application {

    @Override
    public void start(Stage primaryStage) {
        // create a menu
        Menu m = new Menu("Menu");

        // create toggle buttons
        ToggleButton toggleButton1 = new ToggleButton("Toggle Button 1");
        ToggleButton toggleButton2 = new ToggleButton("Toggle Button 2");
        ToggleButton toggleButton3 = new ToggleButton("Toggle Button 3");

        // add actions to toggle buttons
        toggleButton1.setOnAction(event -> { m.hide(); m.setText("Toggle Button 1"); });
        toggleButton2.setOnAction(event -> { m.hide(); m.setText("Toggle Button 2"); });
        toggleButton3.setOnAction(event -> { m.hide(); m.setText("Toggle Button 3"); });

        // create custom menu items
        CustomMenuItem customMenuItem1 = new CustomMenuItem(toggleButton1);
        CustomMenuItem customMenuItem2 = new CustomMenuItem(toggleButton2);
        CustomMenuItem customMenuItem3 = new CustomMenuItem(toggleButton3);

        // prevent the menu from hiding when the toggle buttons are clicked
        customMenuItem1.setHideOnClick(false);
        customMenuItem2.setHideOnClick(false);
        customMenuItem3.setHideOnClick(false);

        // add custom menu items to menu
        m.getItems().addAll(customMenuItem1, customMenuItem2, customMenuItem3);

        // create a menubar
        MenuBar mb = new MenuBar();

        // add menu to menubar
        mb.getMenus().add(m);

        // create a VBox
        VBox vb = new VBox(mb);

        // create a scene
        Scene sc = new Scene(vb, 500, 300);

        // set the scene
        primaryStage.setScene(sc);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}