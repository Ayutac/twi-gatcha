package org.abos.twi.gatcha.gui.component.pane;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.abos.common.Vec2d;
import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.CharacterModified;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.BattlePhase;
import org.abos.twi.gatcha.core.battle.BattleUi;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.abos.twi.gatcha.core.battle.TerrainType;
import org.abos.twi.gatcha.core.effect.ApplicableEffect;
import org.abos.twi.gatcha.core.effect.EffectType;
import org.abos.twi.gatcha.gui.component.CharacterView;
import org.abos.twi.gatcha.gui.shape.Hexagon;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class BattlefieldPane extends Pane implements BattleUi {

    protected final @NotNull BidiMap<Vec2i, Hexagon> hexagons = new DualHashBidiMap<>();
    protected final @NotNull BattleScreen screen;
    protected final @NotNull Map<Hexagon, Tooltip> tooltips = new HashMap<>();
    protected final @NotNull Map<CharacterInBattle, CharacterView> characterViews = new HashMap<>();

    protected final @NotNull Battle battle;
    protected final @Range(from = 1, to = Integer.MAX_VALUE) int radius;
    protected boolean playerMoving;
    protected boolean playerAttacking;
    protected @Nullable CompletableFuture<Object> playerDone;

    public BattlefieldPane(final @NotNull BattleScreen screen, final @NotNull Battle battle, final @Range(from = 1, to = Integer.MAX_VALUE) int radius) {
        this.screen = Objects.requireNonNull(screen);
        this.battle = Objects.requireNonNull(battle);
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be positive!");
        }
        this.radius = radius;
//        final double width = 2 * radius * this.battle.getWidth() + (this.battle.getHeight() > 1 ? radius : 0);
//        final double height = (this.battle.getHeight() - 1) * radius * (0.5 + Hexagon.RADII_FACTOR) + 2 * radius;
        addHexagons();
        battle.setUi(this);
        battle.setStats(screen.getGui().getPlayer() != null ? screen.getGui().getPlayer().getStats() : null);
        addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> updateGrid(mouseEvent.getX(), mouseEvent.getY()));
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }
            final Optional<Hexagon> hexagon = findHexagonAt(mouseEvent.getX(), mouseEvent.getY());
            if (hexagon.isPresent()) {
                final Vec2i position = hexagons.getKey(hexagon.get());
                if (battle.getPhase() == BattlePhase.PLACEMENT) {
                    // place player character
                    if (battle.isPlayerSpawnAt(position) && !battle.getPlacementParty().isEmpty() && !battle.isCharacterAt(position)) {
                        CharacterModified character = battle.getPlacementParty().poll();
                        final CharacterInBattle cib = battle.placePlayerCharacterAt(character, position);
                        final CharacterView characterView = new CharacterView(character.getBase(), true, 2*radius);
                        characterView.setX(hexagon.get().getLeftUpperCorner().x());
                        characterView.setY(hexagon.get().getLeftUpperCorner().y());
                        BattlefieldPane.this.characterViews.put(cib, characterView);
                        BattlefieldPane.this.getChildren().add(characterView);
                        screen.update();
                    }
                    if (battle.getPlacementParty().isEmpty() || battle.noFreeSpawns()) {
                        battle.start();
                        screen.update();
                    }
                }
                else if (battle.getPhase() == BattlePhase.IN_PROGRESS) {
                    // move with the player character
                    if (isPlayerMoving() && battle.getCurrentCharacter() != null && battle.getPossiblePlayerFields().containsKey(position)) {
                        final Vec2i oldPosition = battle.getCurrentCharacter().getPosition();
                        battle.getCurrentCharacter().setMoved((int) Math.round(battle.getPossiblePlayerFields().get(position)));
                        battle.getCurrentCharacter().setPosition(position);
                        final CharacterView characterView = characterViews.get(battle.getCurrentCharacter());
                        characterView.setX(hexagon.get().getLeftUpperCorner().x());
                        characterView.setY(hexagon.get().getLeftUpperCorner().y());
                        characterMoved(battle.getCurrentCharacter(), oldPosition, battle.getCurrentCharacter().getPosition());
                        playerMoving = false;
                        playerAttacking = true;
                        screen.update();
                    }
                    // attack with the player character
                    else if (isPlayerAttacking() && battle.getCurrentCharacter() != null && battle.getSelectedAttack() != null && battle.getPossibleAttackFields().contains(position)) {
                        for (final ApplicableEffect effect : battle.getSelectedAttack().effects()) {
                            effect.apply(battle.getCurrentCharacter(), position, battle);
                        }
                        playerAttacking = false;
                        if (playerDone != null) {
                            playerDone.complete(null);
                            playerDone = null;
                        }
                        screen.update();
                    }
                }
                updateGrid(mouseEvent.getX(), mouseEvent.getY());
            }
        });
        updateGrid(0d, 0d);
    }

    public void addHexagons() {
        final double yOffset = radius * (0.5 + Hexagon.RADII_FACTOR); // the amount we go down with the next row
        var children = getChildren();
        for (int y = 0; y < battle.getHeight(); y++) {
            for (int x = 0; x < battle.getWidth(); x++) {
                final Hexagon hexagon = new Hexagon(radius, new Vec2d(
                        radius + 2 * radius * x + (y % 2 == 0 ? 0 : radius),
                        radius + yOffset * y));
                hexagons.put(new Vec2i(x, y), hexagon);
                children.add(hexagon);
                final Tooltip tooltip = new Tooltip("empty");
                Tooltip.install(hexagon, tooltip);
                tooltips.put(hexagon, tooltip);
            }
        }
    }

    @Override
    public @NotNull Battle getBattle() {
        return battle;
    }

    @Override
    public void characterPlaced(final @NotNull CharacterInBattle character, final @NotNull Vec2i position) {
        final String msg = String.format("%s appeared at (%d,%d).\n", character.getName(), position.x(), position.y());
        screen.getBattleLog().appendText(msg);
        Platform.runLater(screen::update);
    }

    @Override
    public void characterMoved(final @NotNull CharacterInBattle character, final @NotNull Vec2i from, final @NotNull Vec2i to) {
        final String msg;
        if (!from.equals(to)) {
            msg = String.format("%s moved from (%d,%d) to (%d,%d).\n", character.getName(), from.x(), from.y(), to.x(), to.y());
        }
        else {
            msg = String.format("%s remained on (%d,%d).\n", character.getName(), from.x(), from.y());
        }
        screen.getBattleLog().appendText(msg);
        Platform.runLater(screen::update);
    }

    private String defenderString(final @NotNull CharacterInBattle attacker, final @Nullable CharacterInBattle defender) {
        if (attacker == defender) {
            return attacker.getModified().getBase().self();
        }
        if (defender == null) {
            return "nothing";
        }
        return defender.getName();
    }

    @Override
    public void characterAttacked(final @NotNull CharacterInBattle attacker, final @Nullable CharacterInBattle defender, final @NotNull EffectType type, final @Range(from = 0, to = Integer.MAX_VALUE) int damage) {
        final String msg = switch (type) {
            case DAMAGE_BLUNT -> String.format("%s attacked %s for %d blunt damage.\n", attacker.getName(), defenderString(attacker, defender), damage);
            case DAMAGE_SLASH -> String.format("%s attacked %s for %d slash damage.\n", attacker.getName(), defenderString(attacker, defender), damage);
            case DAMAGE_PIERCE -> String.format("%s attacked %s for %d piercing damage.\n", attacker.getName(), defenderString(attacker, defender), damage);
            case DAMAGE_DEATH -> String.format("%s attacked %s for %d death damage.\n", attacker.getName(), defenderString(attacker, defender), damage);
            case DAMAGE_SOUND -> String.format("%s attacked %s for %d sound damage.\n", attacker.getName(), defenderString(attacker, defender), damage);
            case DAMAGE_FROST -> String.format("%s attacked %s for %d frost damage.\n", attacker.getName(), defenderString(attacker, defender), damage);
            case BUFF_ATTACK -> String.format("%s buffed the attack of %s.\n", attacker.getName(), defenderString(attacker, defender));
            case BUFF_DEFENSE -> String.format("%s buffed the defense of %s.\n", attacker.getName(), defenderString(attacker, defender));
            case BUFF_SPEED -> String.format("%s buffed the speed of %s.\n", attacker.getName(), defenderString(attacker, defender));
            case BUFF_HEALTH -> String.format("%s buffed the health of %s.\n", attacker.getName(), defenderString(attacker, defender));
            case DEBUFF_SPEED -> String.format("%s debuffed the speed of %s.\n", attacker.getName(), defenderString(attacker, defender));
            case LOWER_ACCURACY -> String.format("%s lowered the accuracy of %s.\n", attacker.getName(), defenderString(attacker, defender));
            case RESIST_DEATH -> String.format("%s increased the death resistance of %s.\n", attacker.getName(), defenderString(attacker, defender));
            case BLEED -> String.format("%s made %s bleed.\n", attacker.getName(), defenderString(attacker, defender));
            case HEALING -> String.format("%s healed %s for %d damage.\n", attacker.getName(), defenderString(attacker, defender), damage);
            case INVISIBILITY -> String.format("%s made %s invisible.\n", attacker.getName(), defenderString(attacker, defender));
            case INVULNERABILITY -> String.format("%s made %s invulnerable.\n", attacker.getName(), defenderString(attacker, defender));
            case STUN -> String.format("%s stunned %s.\n", attacker.getName(), defenderString(attacker, defender));
            case TURN_FRIENDLY -> String.format("%s made %s friendly.\n", attacker.getName(), defenderString(attacker, defender));
            case SUMMON -> String.format("%s summoned something.\n", attacker.getName());
            default -> throw new AssertionError("Unknown effect effectType encountered!\n"); // shouldn't happen
        };
        screen.getBattleLog().appendText(msg);
        Platform.runLater(screen::update);
    }

    @Override
    public void characterDefeated(@NotNull CharacterInBattle defeated) {
        final String msg = String.format("%s is almost defeated!\n", defeated.getName());
        screen.getBattleLog().appendText(msg);
        Platform.runLater(screen::update);
    }

    @Override
    public boolean isPlayerMoving() {
        return playerMoving;
    }

    @Override
    public boolean isPlayerAttacking() {
        return playerAttacking;
    }

    @Override
    public CompletableFuture<Object> waitForPlayer() {
        playerDone = new CompletableFuture<>();
        playerMoving = true;
        return playerDone;
    }

    @Override
    public void hasWon() {
        final Player player = screen.getGui().getPlayer();
        if (player != null) {
            final InventoryMap reward = getBattle().getReward();
            player.getInventory().addAll(reward);
            player.getStats().increaseLevelWon(battle.getLevelId());
            Platform.runLater(() -> {
                final Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Congratulations!");
                info.setContentText(String.format("You received %s!", reward));
                info.showAndWait();
                if (screen.getCaller() instanceof CampaignScreen) {
                    screen.getGui().showCampaignScreen();
                }
            });
        }
    }

    @Override
    public void hasTied() {
        final Player player = screen.getGui().getPlayer();
        if (player != null) {
            player.getStats().increaseLevelTied(battle.getLevelId());
            Platform.runLater(() -> {
                final Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Well…");
                info.setContentText("You tied this battle…");
                info.showAndWait();
                if (screen.getCaller() instanceof CampaignScreen) {
                    screen.getGui().showCampaignScreen();
                }
            });
        }
    }

    @Override
    public void hasLost() {
        final Player player = screen.getGui().getPlayer();
        if (player != null) {
            player.getStats().increaseLevelLost(battle.getLevelId());
            Platform.runLater(() -> {
                final Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Uh oh!");
                info.setContentText("You lost this battle!");
                info.showAndWait();
                if (screen.getCaller() instanceof CampaignScreen) {
                    screen.getGui().showCampaignScreen();
                }
            });
        }
    }

    protected void updateGrid(double mouseX, double mouseY) {
        // remove downed character views
        if (battle.getPhase().ordinal() >= BattlePhase.IN_PROGRESS.ordinal()) {
            final Iterator<CharacterInBattle> it = characterViews.keySet().iterator();
            CharacterInBattle current = null;
            while (it.hasNext()) {
                current = it.next();
                if (!battle.getCharacterOrder().contains(current)) {
                    getChildren().remove(characterViews.get(current));
                    it.remove();
                }
            }
        }
        // update the hexagons
        final Optional<Hexagon> hexagon = findHexagonAt(mouseX, mouseY);
        for (final Map.Entry<Vec2i, Hexagon> other : hexagons.entrySet()) {
            if (hexagon.isPresent() && hexagon.get() == other.getValue()) {
                continue;
            }
            final Optional<CharacterInBattle> character = battle.getCharacterAt(other.getKey());
            if (character.isEmpty()) {
                // show possible spawn fields
                if (battle.getPhase() == BattlePhase.PLACEMENT && battle.isPlayerSpawnAt(other.getKey())) {
                    other.getValue().setFill(Color.AQUA);
                }
                // show possible move fields
                else if (isPlayerMoving() && battle.getPossiblePlayerFields().containsKey(other.getKey())) {
                    other.getValue().setFill(Color.LIGHTBLUE);
                }
                // show possible attack fields
                else if (isPlayerAttacking() && !isPlayerMoving() && battle.getPossibleAttackFields().contains(other.getKey())) {
                    other.getValue().setFill(Color.MISTYROSE);
                }
                else {
                    switch (battle.getTerrainTypeAt(other.getKey())) {
                        case BLOCKED -> other.getValue().setFill(Color.BLACK);
                        case HILL -> other.getValue().setFill(Color.GREY);
                        default -> other.getValue().setFill(Color.TRANSPARENT);
                    }
                }
                tooltips.get(other.getValue()).setText("empty");
            }
            else {
                switch (character.get().getTeam()) {
                    case ENEMY -> other.getValue().setFill(Color.RED);
                    case ALLY -> other.getValue().setFill(Color.GREEN);
                    case PLAYER -> other.getValue().setFill(Color.BLUE);
                    default -> throw new AssertionError("Unhandled enum encountered!"); // shouldn't happen
                }
                tooltips.get(other.getValue()).setText(String.format("%s (%d/%d)", character.get().getName(), character.get().getHealth(), character.get().getMaxHealth()));
            }
        }
        if (hexagon.isPresent()) {
            hexagon.get().setFill(Color.ORANGE);
            final Optional<CharacterInBattle> character = battle.getCharacterAt(hexagons.getKey(hexagon.get()));
            if (character.isPresent()) {
                tooltips.get(hexagon.get()).setText(String.format("%s (%d/%d)", character.get().getName(), character.get().getHealth(), character.get().getMaxHealth()));
            }
        }
    }

    protected Optional<Hexagon> findHexagonAt(double x, double y) {
        for (final Hexagon hexagon : hexagons.values()) {
            if (hexagon.intersects(x, y, 1, 1)) {
                return Optional.of(hexagon);
            }
        }
        return Optional.empty();
    }

}
