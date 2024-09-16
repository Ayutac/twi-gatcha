package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.Group;

import java.util.Set;

public interface Groups {

    Group MELEE = new Group("Melee", "Characters who fight in close range.", Set.of(
            Characters.KSMVR,
            Characters.YVLON,
            Characters.SKELETON,
            Characters.ZOMBIE));

    Group RANGED = new Group("Ranged", "Characters who fight in long range.", Set.of(
            Characters.SKELETON_ARCHER));

    Group MAGE = new Group("Mage", "Characters who use magic.", Set.of(
            Characters.PISCES,
            Characters.CERIA));

    Group SUPPORT = new Group("Support", "Characters who support others.", Set.of(
            Characters.ERIN));

    Group WEAK_SKELETONS = new Group("Weak Skeletons", "Skeletons that can be turned easily.", Set.of(
            Characters.SKELETON,
            Characters.SKELETON_ARCHER));

    Group UNDEAD = new Group("Undead", "The Walking Dead.", Set.of(
            Characters.SKELETON,
            Characters.SKELETON_ARCHER,
            Characters.ZOMBIE));

}
