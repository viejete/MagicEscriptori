package magic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class Controller {
    public AnchorPane apMainPane;
    public ListView<Card> lvLlistaCartes;
    public ArrayList<Card> cartes;
    public ImageView ivCartes;

    public void initialize() throws MalformedURLException {

        cartes = CardsAPI.getAllCards();

        // Personalitzem la CellFactory
        lvLlistaCartes.setCellFactory((list) -> {
            return new ListCell<Card>() {
                @Override
                public void updateItem(Card item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Image image = new Image(item.getImageUrl() , 75 , 75 , true , true , true);
                        setGraphic(new ImageView(image));
                        setText(item.getName());
                    }
                }
            };
        });

        // Afegir llista observable d'items
        ObservableList<Card> cards = FXCollections.observableArrayList(cartes);
        lvLlistaCartes.setItems(cards);

        // Afegir un item
        //lvLlistaCartes.getItems().add(new Person("Pepi", "icon.png"));

        // Handle ListView selection changes with a listener
        lvLlistaCartes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    Image image = new Image(newValue.getImageUrl());
                    ivCartes.setImage(image);
            }
        );
    }
}
