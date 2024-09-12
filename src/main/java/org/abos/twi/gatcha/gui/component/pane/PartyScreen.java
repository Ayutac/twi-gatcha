package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.Party;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.gui.Gui;
import org.abos.twi.gatcha.gui.component.LabelledCharacterView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public final class PartyScreen extends AbstractScreen {

    private @Nullable PartyGalleryPane galleryPane;
    private final @NotNull TextField nameField = new TextField();
    private final @NotNull Button saveBtn = new Button("Save");

    private @Nullable Player player;

    public PartyScreen(@NotNull Gui gui) {
        super(gui);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showHomeScreen();
            }
        });
        // bottom menu
        saveBtn.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }
            try {
                saveParty();
            }
            catch (IllegalArgumentException ex) {
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Oh no!");
                alert.setContentText("Doubled Characters detected!");
                alert.showAndWait();
            }
        });
        final HBox bottomBox = new HBox(new Label("Name:"), nameField, saveBtn);
        bottomBox.setSpacing(2d);
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public void setPlayer(final @Nullable Player player) {
        this.player = player;
        if (this.player != null) {
            galleryPane = new PartyGalleryPane(this, player, 125);
            setCenter(galleryPane);
        }
        else {
            galleryPane = null;
            nameField.setText("");
            setCenter(null);
        }
    }

    public void select(final @Nullable CharacterModified character) {
        if (galleryPane != null && galleryPane.getSelectedCharIndex() != null) {
            final LabelledCharacterView view = galleryPane.getViews().get(galleryPane.getSelectedCharIndex());
            view.setCharacter(character == view.getCharacter() ? null : character);
        }
    }

    public void update() {
        if (galleryPane != null) {
            galleryPane.update();
        }
        if (player != null) {
            if (player.getNumberOfParties() > 0) {
                nameField.setText(player.getParty(0).getName());
            }
            else {
                nameField.setText("New Party");
            }
        }
    }

    public void saveParty() {
        if (player == null || galleryPane == null) {
            return;
        }
        final String name = nameField.getText();
        final List<CharacterModified> characters = new LinkedList<>();
        for (final LabelledCharacterView view : galleryPane.views) {
            final CharacterModified character = view.getCharacter();
            if (character != null) {
                characters.add(character);
            }
        }
        final Party party = new Party(name, characters); // throws IAE
        final int partyIndex = galleryPane.getSelectedPartyIndex();
        if (partyIndex == -1) {
            player.addParty(party);
        }
        else {
            player.updateParty(party, partyIndex);
        }
        update();
    }
}
