package org.abos.twi.gatcha.data;

import org.abos.twi.gatcha.core.Group;

import java.util.Set;

public interface Groups {

    String MELEE_ID = "Melee";
    Group MELEE = new Group(MELEE_ID, "Characters who fight in close range.", Set.of(
            Characters.KSMVR,
            Characters.YVLON,
            Characters.SKELETON,
            Characters.ZOMBIE));

    String RANGED_ID = "Ranged";
    Group RANGED = new Group(RANGED_ID, "Characters who fight in long range.", Set.of(
            Characters.SKELETON_ARCHER));

    String MAGE_ID = "Mage";
    Group MAGE = new Group(MAGE_ID, "Characters who use magic.", Set.of(
            Characters.PISCES,
            Characters.CERIA,
            Characters.PISCES_CRELER,
            Characters.CERIA_CRELER));

    String SUPPORT_ID = "Support";
    Group SUPPORT = new Group(SUPPORT_ID, "Characters who support others.", Set.of(
            Characters.ERIN));

    String WEAK_SKELETONS_ID = "Weak Skeletons";
    Group WEAK_SKELETONS = new Group(WEAK_SKELETONS_ID, "Skeletons that can be turned easily.", Set.of(
            Characters.SKELETON,
            Characters.SKELETON_ARCHER));

    String UNDEAD_ID = "Undead";
    Group UNDEAD = new Group(UNDEAD_ID, "The Walking Dead.", Set.of(
            Characters.SKELETON,
            Characters.SKELETON_ARCHER,
            Characters.ZOMBIE));

}
