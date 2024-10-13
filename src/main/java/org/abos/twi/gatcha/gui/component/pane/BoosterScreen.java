package org.abos.twi.gatcha.gui.component.pane;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abos.common.Named;
import org.abos.twi.gatcha.core.Booster;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.data.Lookups;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class BoosterScreen extends AbstractScreen {

    private final @NotNull Label boosterNameLabel = new Label();
    private final @NotNull Label boosterDescLabel = new Label();
    private final @NotNull Label boosterPriceLabel = new Label();
    private final @NotNull ComboBox<String> comboBox = new ComboBox<>();
    private final @NotNull Button buyBtn = new Button("Summon");

    public BoosterScreen(@NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showHomeScreen();
            }
        });
        // top text
        final VBox topBox = new VBox(boosterNameLabel, boosterDescLabel, boosterPriceLabel);
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);
        // bottom menu
        comboBox.setPrefWidth(200d);
        comboBox.setOnAction(actionEvent -> {
            final Optional<Booster> booster = Gui.getBoosterByName(comboBox.getSelectionModel().getSelectedItem());
            if (booster.isPresent()) {
                boosterNameLabel.setText(booster.get().getName());
                boosterDescLabel.setText(booster.get().getDescription());
                boosterPriceLabel.setText(booster.get().price().toString());
            }
        });
        buyBtn.setOnMouseClicked(this::pull);
        final HBox bottomBox = new HBox(new Label("Select Summoning Circle:"), comboBox, buyBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setSpacing(2d);
        setBottom(bottomBox);
    }

    private void pull(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() != MouseButton.PRIMARY) {
            return;
        }
        final Player player = gui.getPlayer();
        if (player == null) {
            return;
        }
        final Optional<Booster> booster = Gui.getBoosterByName(comboBox.getSelectionModel().getSelectedItem());
        if (booster.isEmpty()) {
            return;
        }
        if (player.getInventory().canSubtract(booster.get().price())) {
            player.getInventory().subtractAll(booster.get().price());
            final CharacterBase pull = booster.get().pull();
            if (player.hasCharacter(pull)) {
                player.getInventory().add(pull.token(), 1);
            }
            else {
                player.addToRooster(pull);
            }
            final Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Summoning successful!");
            info.setContentText(String.format("You pulled %s!", pull.getName()));
            info.showAndWait();
        }
    }

    public void update() {
        final Player player = gui.getPlayer();
        if (player != null) {
            comboBox.setItems(FXCollections.observableList(Lookups.BOOSTERS.values().stream()
                    .filter(booster -> booster.availability().check(player))
                    .map(Named::getName)
                    .sorted(String::compareTo)
                    .toList()));
            final Optional<Booster> booster = Gui.getBoosterByName(comboBox.getSelectionModel().getSelectedItem());
            if (booster.isPresent()) {
                boosterNameLabel.setText(booster.get().getName());
                boosterDescLabel.setText(booster.get().getDescription());
                boosterPriceLabel.setText(booster.get().price().toString());
            }
        }
        else {
            comboBox.setItems(FXCollections.emptyObservableList());
            boosterNameLabel.setText("");
            boosterDescLabel.setText("");
            boosterPriceLabel.setText("");
        }
    }

}
