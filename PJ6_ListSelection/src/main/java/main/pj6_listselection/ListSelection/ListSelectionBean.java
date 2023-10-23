package main.pj6_listselection.ListSelection;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;

public class ListSelectionBean<T> {

    private final ObservableList<T> available;
    private final ObservableList<T> selected;

    private final FilteredList<T> availableFilter;
    private final FilteredList<T> selectedFilter;

    public ListSelectionBean() {
        this.available = FXCollections.observableArrayList();
        this.selected = FXCollections.observableArrayList();

        availableFilter = new FilteredList<>(this.available);
        selectedFilter = new FilteredList<>(this.selected);
    }
    public ObservableList<T> getSelected() {return selected;}
    public ObservableList<T> getAvailable() {return available;}
    public ObservableList<T> getAvailableFilter() {return availableFilter;}
    public ObservableList<T> getSelectedFilter() {return selectedFilter;}

    public void setList(List<T>available, List<T>selected){
        for (T availableItem  : available) {
            if (!selected.contains(availableItem )) {
                this.available.add(availableItem );
            }
            this.selected.clear();
            this.selected.addAll(selected);

        }
    }
    public void selectAll() {
        selected.addAll(availableFilter);
        available.removeAll(availableFilter);
    }

    public void unselectAll(){
        available.addAll(selectedFilter);
        selected.removeAll(selectedFilter);
    }

    public void selectOne () {
        if (!available.isEmpty()) {
            T itemToSelect = available.get(0);
            selected.add(itemToSelect);
            available.remove(itemToSelect);

        }
    }
    public void selectMultiple(List<T> itemsToSelect) {
        for (T item : itemsToSelect) {
                selected.add(item);
                available.remove(item);
        }
    }

    public void unselectOne() {
            if (!selected.isEmpty()) {
                T itemToUnselect = selected.get(0);
                available.add(itemToUnselect);
                selected.remove(itemToUnselect);
            }
    }
    public void unselectMultiple(List<T> itemsToUnselect) {
        for (T item : itemsToUnselect) {
            available.add(item);
            selected.remove(item);
        }
    }
    public void setFilterValue(String newValue) {
        selectedFilter.setPredicate(object -> object.toString().toLowerCase().contains(newValue.toLowerCase()));
        availableFilter.setPredicate(object -> object.toString().toLowerCase().contains(newValue.toLowerCase()));
    }



}
