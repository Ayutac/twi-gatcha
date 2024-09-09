package org.abos.twi.gatcha.gui.component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.core.CharacterModified;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LabelledCharacterView extends VBox {

    protected final @NotNull CharacterModified character;

    protected final @NotNull CharacterView view;
    protected final @NotNull Label label;

    public LabelledCharacterView(CharacterModified character, boolean hex, double size) {
        this.character = Objects.requireNonNull(character);
        view = new CharacterView(character.getBase(), hex, size);
        label = new Label(character.getName());
        setAlignment(Pos.CENTER);
        getChildren().addAll(view, label);
    }

    public @NotNull CharacterModified getCharacter() {
        return character;
    }
}
