package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CharacterScreen extends AbstractScreen {

    private @Nullable CharacterModified character;

    private final @NotNull Label name = new Label();
    private final @NotNull Label description = new Label();
    private @Nullable CharacterDetailPane detailPane;

    public CharacterScreen(final @NotNull Gui gui) {
        super(gui);
        final HBox nameBox = new HBox(name);
        nameBox.setAlignment(Pos.CENTER);
        setTop(nameBox);
        final HBox descBox = new HBox(description);
        descBox.setAlignment(Pos.CENTER);
        setBottom(descBox);
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.BACK) {
                this.gui.showRoosterScreen(CharacterScreen.this);
            }
        });
    }

    public @Nullable CharacterModified getCharacter() {
        return character;
    }

    public void setCharacter(@Nullable CharacterModified character) {
        this.character = character;
        if (this.character != null) {
            detailPane = new CharacterDetailPane(character.getBase());
            setCenter(detailPane);
            final VBox statGridBox = new VBox(new CharacterStatGrid(character));
            statGridBox.setAlignment(Pos.CENTER);
            setRight(statGridBox);
            name.setText(character.getName());
            description.setText(character.getDescription());
        }
        else {
            detailPane = null;
            setCenter(null);
            setRight(null);
            name.setText("");
            description.setText("");
        }
    }
}
