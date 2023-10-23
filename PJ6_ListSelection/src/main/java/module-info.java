module main.pj6_listselection {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.pj6_listselection to javafx.fxml;
    exports main.pj6_listselection;
    opens main.pj6_listselection.ListSelection to javafx.fxml;
    exports main.pj6_listselection.ListSelection;
}