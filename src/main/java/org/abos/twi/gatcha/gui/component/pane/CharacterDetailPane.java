package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.layout.StackPane;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.gui.component.CharacterView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CharacterDetailPane extends StackPane {

    protected final @NotNull CharacterBase character;

    protected final @NotNull CharacterView view;

    public CharacterDetailPane(final @NotNull CharacterBase character) {
        this.character = Objects.requireNonNull(character);
        view = new CharacterView(character, false, 650d);
        getChildren().add(view);
    }

}
