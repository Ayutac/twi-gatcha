package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.abos.twi.gatcha.core.CharacterAttacks;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.BattlePhase;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public final class BattleScreen extends AbstractScreen {

    private final @NotNull Label turnOrder = new Label();
    private final @NotNull RadioButton normalAttackButton = new RadioButton();
    private final @NotNull RadioButton special1AttackButton = new RadioButton();
    private final @NotNull RadioButton special2AttackButton = new RadioButton();
    private final @NotNull HBox attackBox;
    private @Nullable BattlefieldPane battlefieldPane;

    private @Nullable Battle battle;

    public BattleScreen(final @NotNull Gui gui) {
        super(gui);
        // turn order at the top
        final VBox topBox = new VBox(new Label("Turn Order"), turnOrder);
        topBox.setAlignment(Pos.CENTER);
        BorderPane.setMargin(topBox, new Insets(1, 0, 5, 0));
        setTop(topBox);
        // group the radio buttons
        final ToggleGroup attackGroup = new ToggleGroup();
        normalAttackButton.setToggleGroup(attackGroup);
        normalAttackButton.setSelected(true);
        normalAttackButton.selectedProperty().addListener((obs, prev, now) -> update());
        special1AttackButton.setToggleGroup(attackGroup);
        special1AttackButton.selectedProperty().addListener((obs, prev, now) -> update());
        special2AttackButton.setToggleGroup(attackGroup);
        special2AttackButton.selectedProperty().addListener((obs, prev, now) -> update());
        // add them to the bottom
        attackBox = new HBox(normalAttackButton, special1AttackButton, special2AttackButton);
        attackBox.setAlignment(Pos.CENTER);
        attackBox.setVisible(false);
        final VBox bottomBox = new VBox(new Label("Attack"), attackBox);
        bottomBox.setAlignment(Pos.CENTER);
        BorderPane.setMargin(bottomBox, new Insets(5, 0, 1, 0));
        setBottom(bottomBox);
    }

    public @Nullable Battle getBattle() {
        return battle;
    }

    public void setBattle(final @Nullable Battle battle) {
        this.battle = battle;
        if (this.battle != null) {
            battlefieldPane = new BattlefieldPane(this, battle, 30);
            final HBox box = new HBox(battlefieldPane);
            box.setAlignment(Pos.CENTER);
            setCenter(box);
        }
        else {
            battlefieldPane = null;
            setCenter(null);
            turnOrder.setText("");
        }
    }

    public void update() {
        if (battle != null && battle.getPhase().ordinal() > BattlePhase.PLACEMENT.ordinal()) {
            final String text = battle.getCharacterOrder().stream()
                    .map(character -> String.format("%s (%d/%d)", character.getName(), character.getHealth(), character.getMaxHealth()))
                    .collect(Collectors.joining(", "));
            turnOrder.setText(text);
            if (battle.isPlayerAttack() && battle.getCurrentCharacter() != null) {
                final CharacterAttacks attacks = battle.getCurrentCharacter().getModified().getBase().attacks();
                normalAttackButton.setText(attacks.normal().name());
                special1AttackButton.setText(attacks.special1().name());
                special2AttackButton.setText(attacks.special2().name());
                if (normalAttackButton.isSelected()) {
                    battle.setSelectedAttack(attacks.normal());
                }
                else if (special1AttackButton.isSelected()) {
                    battle.setSelectedAttack(attacks.special1());
                }
                else if (special2AttackButton.isSelected()) {
                    battle.setSelectedAttack(attacks.special2());
                }
                attackBox.setVisible(true);
                battlefieldPane.updateGrid(0d, 0d);
            }
            else {
                attackBox.setVisible(false);
            }
        }
    }
}
