package main.pj6_listselection.ListSelection;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListSelectionController<T> extends BorderPane {
    @FXML
    private Button allLeft;
    @FXML
    private Button allRight;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;

    @FXML
    private ListView<T> listAvailable;
    @FXML
    private ListView<T> listSelection;
    @FXML
    private TextField textFieldSearch;
    private ListSelectionBean<T> listSelectionBean;
    BorderPane borderPane;

    public ListSelectionController() {
        listSelectionBean = new ListSelectionBean<>();
        loadComponent();
        listAvailable.setItems(listSelectionBean.getAvailableFilter());
        listSelection.setItems(listSelectionBean.getSelectedFilter());
        multiSelection();
        setItemClick();
        setDragStart();
        setDragDropped();
        setDragOver();
        textFieldSearch.textProperty().addListener((observable, oldvalue, newvalue) -> listSelectionBean.setFilterValue(newvalue));

        //function for button
        leftButton.setOnAction(event -> selectMultiAction(event));
        rightButton.setOnAction(event -> selectMultiAction(event));
        allLeft.setOnAction(event -> selectAllAction(event));
        allRight.setOnAction(event -> selectAllAction(event));

        //Set style for the row of listcell
        listSelection.getStyleClass().add("list-view");
        listAvailable.getStyleClass().add("list-view");
        setCustomCellFactory(listAvailable);
        setCustomCellFactory(listSelection);


    }
    public void loadComponent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListSelection.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            borderPane = fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    // Add the custom cell factory for even and odd backgrounds
    private void setCustomCellFactory(ListView<T> listView) {
        listView.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
    }
    public void setLists(List<T> available, List<T>selected) {
        listSelectionBean.setList(available,selected);
    }
    public void setItemClick() {
        listAvailable.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                    listSelectionBean.selectOne();
                    listAvailable.getSelectionModel().clearSelection();
            }
            else if (event.getClickCount() == 3) {
                listSelectionBean.selectAll();
            }
        });
        listSelection.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                listSelectionBean.unselectOne();
                listSelection.getSelectionModel().clearSelection();
            }
            if (event.getClickCount() == 3) {
                listSelectionBean.unselectAll();
            }
        });
    }
    public void setDragStart() {
        listAvailable.setOnDragDetected(dragEvent -> {
            final Dragboard dragboard = listAvailable.startDragAndDrop(TransferMode.MOVE);
            final ClipboardContent content = new ClipboardContent();
            content.putString("Selected");
            dragboard.setContent(content);
            dragEvent.consume();
        });
        listSelection.setOnDragDetected(dragEvent -> {
            final Dragboard dragboard = listSelection.startDragAndDrop(TransferMode.MOVE);
            final ClipboardContent content = new ClipboardContent();
            content.putString("Unselected");
            dragboard.setContent(content);
            dragEvent.consume();
        });

    }
    public void setDragDropped(){
        listSelection.setOnDragDropped(dragEvent -> {
            if (dragEvent.getGestureSource() == listAvailable) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
                // Get the dragged items from the source list
                List<T> draggedItems = new ArrayList<>(listAvailable.getSelectionModel().getSelectedItems());
                listSelectionBean.selectMultiple(draggedItems);
                //listSelectionBean.selectOne();
                dragEvent.consume();
            }
        });

        listAvailable.setOnDragDropped(dragEvent -> {
            if (dragEvent.getGestureSource() == listSelection) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
                List<T> draggedItems = new ArrayList<>(listSelection.getSelectionModel().getSelectedItems());
                listSelectionBean.unselectMultiple(draggedItems);
                //listSelectionBean.unselectOne();
                dragEvent.consume();
            }
        });
         textFieldSearch.setOnDragDropped(dragEvent -> {
             if (dragEvent.getGestureSource() == listSelection) {
                 dragEvent.acceptTransferModes(TransferMode.MOVE);
                 textFieldSearch.setText(listSelection.getSelectionModel().getSelectedItem().toString());
             } else if(dragEvent.getGestureSource() == listAvailable) {
                 dragEvent.acceptTransferModes(TransferMode.MOVE);
                 textFieldSearch.setText(listAvailable.getSelectionModel().getSelectedItem().toString());
             }
         });
    }
    public void setDragOver(){
        listSelection.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() == listAvailable) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
            dragEvent.consume();
        });
        listAvailable.setOnDragOver(dragEvent -> {
            if (dragEvent.getGestureSource() == listSelection) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
            dragEvent.consume();
        });
        textFieldSearch.setOnDragOver(dragEvent -> {
            if ((dragEvent.getGestureSource() == listSelection
                    && listSelection.getSelectionModel().getSelectedItems().size()==1)
                    || (dragEvent.getGestureSource() == listAvailable && listAvailable.getSelectionModel().getSelectedItems().size() ==1))
                dragEvent.acceptTransferModes(TransferMode.ANY);
            dragEvent.consume();
        });
    }
    public void multiSelection(){
        listSelection.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listAvailable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    private void selectMultiAction(ActionEvent event){
        if(event.getSource() == rightButton) {
            List<T> draggedItems = new ArrayList<>(listAvailable.getSelectionModel().getSelectedItems());
            listSelectionBean.selectMultiple(draggedItems);
            listAvailable.getSelectionModel().clearSelection();
        } else if (event.getSource() == leftButton) {
            List<T> draggedItems = new ArrayList<>(listSelection.getSelectionModel().getSelectedItems());
            listSelectionBean.unselectMultiple(draggedItems);
            listSelection.getSelectionModel().clearSelection();
        }
    }
    @FXML
    private void selectAllAction(ActionEvent event){
        if(event.getSource() == allRight) {
            listSelectionBean.selectAll();
        } else if (event.getSource() == allLeft) {
            listSelectionBean.unselectAll();
        }
    }

    public void printAllSelectedItems() {
        ObservableList<T> selectedItems = listSelectionBean.getSelectedFilter();
        System.out.println("All Selected Items are");
        for (T item : selectedItems) {
            System.out.println(item.toString());
        }
    }
}
