package org.abos.twi.gatcha.gui.component;

import javafx.scene.image.ImageView;
import org.abos.twi.gatcha.core.CharacterBase;
import org.abos.twi.gatcha.gui.Gui;

public class CharacterView extends ImageView {

    public CharacterView(CharacterBase character, boolean hex) {
        super(hex ? Gui.IMAGE_HEX_MAP.get(character) : Gui.IMAGE_MAP.get(character));
        if (hex) {
            setFitWidth(60d);
        }
        else {
            setFitHeight(680d);
        }
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
    }

}
