package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.abos.twi.gatcha.core.CharacterModified;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CharacterStatGrid extends GridPane {

    protected final @NotNull CharacterModified character;

    public CharacterStatGrid(final @NotNull CharacterModified character) {
        super(3, 3);
        this.character = Objects.requireNonNull(character);
        int row = 0;
        final ColumnConstraints colConst = new ColumnConstraints();
        colConst.setHalignment(HPos.RIGHT);
        getColumnConstraints().add(colConst);
        add(new Label("Rarity:"), 0, row);
        add(new Label(character.getBase().rarity().getName()), 1, row++);
        final HBox statsBox = new HBox(new Label("Stats"));
        statsBox.setAlignment(Pos.CENTER);
        add(statsBox, 0, row++, 2, 1);
        add(new Label("Health:"), 0, row);
        add(new Label(Integer.toString(character.getMaxHealth())), 1, row++);
        add(new Label("Attack:"), 0, row);
        add(new Label(Integer.toString(character.getAttack())), 1, row++);
        add(new Label("Defense:"), 0, row);
        add(new Label(Integer.toString(character.getDefense())), 1, row++);
        add(new Label("Speed:"), 0, row);
        add(new Label(Integer.toString(character.getSpeed())), 1, row++);
        add(new Label("Initiative:"), 0, row);
        add(new Label(Double.toString(character.getInitiative())), 1, row++);
        add(new Label("Movement:"), 0, row);
        add(new Label(Integer.toString(character.getMovement())), 1, row++);
        final HBox attacksBox = new HBox(new Label("Attacks"));
        attacksBox.setAlignment(Pos.CENTER);
        add(attacksBox, 0, row++, 2, 1);
        final HBox attackNormalBox = new HBox(new Label(character.getBase().attacks().normal().name()));
        attackNormalBox.setAlignment(Pos.CENTER_LEFT);
        add(attackNormalBox, 0, row++, 2, 1);
        final HBox attackSpecial1Box = new HBox(new Label(character.getBase().attacks().special1().name()));
        attackSpecial1Box.setAlignment(Pos.CENTER_LEFT);
        add(attackSpecial1Box, 0, row++, 2, 1);
        final HBox attackSpecial2Box = new HBox(new Label(character.getBase().attacks().special2().name()));
        attackSpecial2Box.setAlignment(Pos.CENTER_LEFT);
        add(attackSpecial2Box, 0, row++, 2, 1);
    }

}
