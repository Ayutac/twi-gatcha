package org.abos.twi.gatcha.gui.component.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
    private final @NotNull TextArea battleLog = new TextArea();
    private @Nullable BattlefieldPane battlefieldPane;
    private @Nullable AbstractScreen caller;

    private @Nullable Battle battle;

    public BattleScreen(final @NotNull Gui gui) {
        super(gui);
        // turn order at the top
        final VBox topBox = new VBox(new Label("Turn Order"), turnOrder);
        topBox.setAlignment(Pos.CENTER);
        BorderPane.setMargin(topBox, new Insets(1, 0, 5, 0));
        setTop(topBox);
        // batte log at the right
        battleLog.setEditable(false);
        battleLog.setWrapText(true);
        battleLog.setPrefHeight(500d);
        battleLog.setPrefWidth(200d);
        setRight(battleLog);
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
        battleLog.setText("");
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

    public @Nullable AbstractScreen getCaller() {
        return caller;
    }

    public void setCaller(@Nullable AbstractScreen caller) {
        this.caller = caller;
    }

    public @NotNull TextArea getBattleLog() {
        return battleLog;
    }

    public void update() {
        if (battle != null && battle.getPhase().ordinal() > BattlePhase.PLACEMENT.ordinal()) {
            final String text = battle.getCharacterOrder().stream()
                    .map(character -> String.format("%s (%d/%d)", character.getName(), character.getHealth(), character.getMaxHealth()))
                    .collect(Collectors.joining(", "));
            turnOrder.setText(text);
            if (battle.getUi() != null && battle.getUi().isPlayerAttacking() && battle.getCurrentCharacter() != null) {
                final CharacterAttacks attacks = battle.getCurrentCharacter().getModified().getBase().attacks();
                normalAttackButton.setText(attacks.normal().name());
                normalAttackButton.setDisable(battle.getCurrentCharacter().getCooldownNormal() != 0);
                special1AttackButton.setText(attacks.special1().name());
                special1AttackButton.setDisable(battle.getCurrentCharacter().getCooldownSpecial1() != 0);
                special2AttackButton.setText(attacks.special2().name());
                special2AttackButton.setDisable(battle.getCurrentCharacter().getCooldownSpecial2() != 0);
                if ((special1AttackButton.isDisable() && special1AttackButton.isSelected()) ||
                        (special2AttackButton.isDisable() && special2AttackButton.isSelected())) {
                    normalAttackButton.setSelected(true);
                }
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
            }
            else {
                attackBox.setVisible(false);
            }
        }
        if (battlefieldPane != null) {
            battlefieldPane.updateGrid(-10d, -10d);
        }
    }

    public void shutdown() {
        if (battlefieldPane != null && battlefieldPane.playerDone != null) {
            battlefieldPane.playerDone.cancel(true);
        }
    }
}
