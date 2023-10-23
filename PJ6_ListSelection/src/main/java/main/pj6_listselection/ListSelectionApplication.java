package main.pj6_listselection;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.pj6_listselection.ListSelection.ListSelectionController;
import java.util.ArrayList;
import java.util.List;

public class ListSelectionApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {

        // Create an instance of ListSelectionController
        ListSelectionController<String> listSelectionController = new ListSelectionController<>();


        // Create a sample ArrayList with data
        List<String> availableItems = new ArrayList<>();
        // Iterate over the color map and add colors to the availableItems list

        availableItems.add(Color.getColor(1,"RED"));
        availableItems.add(Color.getColor(2,"GREEN"));
        availableItems.add(Color.getColor(3,"BLUE"));
        availableItems.add(Color.getColor(4,"yellow"));
        availableItems.add(Color.getColor(5,"purple"));
        availableItems.add(Color.getColor(6,"orange"));
        availableItems.add(Color.getColor(7,"pink"));
        availableItems.add(Color.getColor(8,"brown"));
        availableItems.add(Color.getColor(9, "gray"));
        availableItems.add(Color.getColor(11,"black"));


        List<String> selectedItems = new ArrayList<>();
        selectedItems.add(Color.getColor(10,"BLACK"));
        selectedItems.add(Color.getColor(11,"WHITE"));
        selectedItems.add(Color.getColor(3,"blue"));
        selectedItems.add(Color.getColor(6,"ORANGE"));

        // Set the lists for listAvailable and listSelection
        listSelectionController.setLists(availableItems, selectedItems);
        // Create a scene and set the ListSelectionController as the root
        Scene scene = new Scene(listSelectionController, 800, 600);
        // Add CSS styles to the ListView components
        scene.getStylesheets().add(getClass().getResource("StyleListSelection.css").toExternalForm());

        //set an event handler to print selected items when the application was stopped
        primaryStage.setOnCloseRequest(event -> {
            listSelectionController.printAllSelectedItems();
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("List Selection App");
        primaryStage.show();
    }

}
