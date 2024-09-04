package org.abos.twi.gatcha.gui.component.pane;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.abos.common.Named;
import org.abos.twi.gatcha.core.CharacterAttacks;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.BattlePhase;
import org.abos.twi.gatcha.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Collectors;

public final class BattleScreen extends StackPane {

    private final Gui gui;

    private final @NotNull BorderPane borderPane = new BorderPane();
    private final @NotNull Label turnOrder = new Label();
    private final @NotNull RadioButton normalAttackButton = new RadioButton();
    private final @NotNull RadioButton special1AttackButton = new RadioButton();
    private final @NotNull RadioButton special2AttackButton = new RadioButton();
    private final @NotNull HBox attackBox;
    private @Nullable BattlefieldPane battlefieldPane;

    private @Nullable Battle battle;

    public BattleScreen(final @NotNull Gui gui) {
        this.gui = Objects.requireNonNull(gui);
        // turn order at the top
        final VBox topBox = new VBox(new Label("Turn Order"), turnOrder);
        borderPane.setTop(topBox);
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
        attackBox.setVisible(false);
        final VBox bottomBox = new VBox(new Label("Attack"), attackBox);
        borderPane.setBottom(bottomBox);
        // set layout
        getChildren().add(borderPane);
    }

    public @Nullable Battle getBattle() {
        return battle;
    }

    public void setBattle(final @Nullable Battle battle) {
        this.battle = battle;
        if (this.battle != null) {
            battlefieldPane = new BattlefieldPane(this, battle, 30);
            borderPane.setCenter(battlefieldPane);
        }
        else {
            battlefieldPane = null;
            borderPane.setCenter(null);
            turnOrder.setText("");
        }
    }

    public void update() {
        if (battle != null && battle.getPhase().ordinal() > BattlePhase.PLACEMENT.ordinal()) {
            final String text = battle.getCharacterOrder().stream().map(Named::getName).collect(Collectors.joining(", "));
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
