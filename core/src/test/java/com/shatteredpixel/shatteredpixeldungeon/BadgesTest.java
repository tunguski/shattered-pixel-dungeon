package com.shatteredpixel.shatteredpixeldungeon;

import com.badlogic.gdx.Preferences;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.function.Consumer;

import static com.shatteredpixel.shatteredpixeldungeon.Badges.Badge;
import static com.shatteredpixel.shatteredpixeldungeon.Badges.monsterSlains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BadgesTest {

    Preferences preferences;
    Messages messages;
    GLog glog;
    Consumer<Badge> showBadge;

    @BeforeAll
    static void beforeClass() {
        SPDSettings.testEnvironment = true;
    }

    @BeforeEach
    void beforeTest() {
        preferences = mock(Preferences.class);
        when(preferences.getString(anyString(), anyString()))
                .thenAnswer(invocation -> invocation.getArgument(1));
        SPDSettings.set(preferences);

        Messages messages = mock(Messages.class);
        when(messages._get(any(Class.class), anyString(), any()))
                .thenAnswer(invocation -> invocation.getArgument(1));
        Messages.setInstance(messages);

        glog = mock(GLog.class);
        GLog.setInstance(glog);

        showBadge = mock(Consumer.class);
        PixelScene.showBadge = showBadge;

        Dungeon.hero = new Hero();
        //Dungeon.init();
        Badges.global = new HashSet<>();
        Badges.reset();
    }

    @Test
    void validateLevels() {
        Badges.validateLevels(monsterSlains, 1);
        assertEquals(0, Badges.local.size());
        Badges.validateLevels(monsterSlains, 11);
        assertEquals(1, Badges.local.size());
        Badges.validateLevels(monsterSlains, 1000);
        assertEquals(5, Badges.local.size());
    }

    @Test
    void validateMonstersSlain() {
        Statistics.enemiesSlain = 1;
        Badges.validateMonstersSlain();
        assertEquals(0, Badges.local.size());
        Statistics.enemiesSlain = 11;
        Badges.validateMonstersSlain();
        assertEquals(1, Badges.local.size());
        Statistics.enemiesSlain = 1_000;
        Badges.validateMonstersSlain();
        assertEquals(5, Badges.local.size());
    }

    @Test
    void validateGoldCollected() {
        Statistics.goldCollected = 1;
        Badges.validateGoldCollected();
        assertEquals(0, Badges.local.size());
        Statistics.goldCollected = 500;
        Badges.validateGoldCollected();
        assertEquals(1, Badges.local.size());
        Statistics.goldCollected = 500_000;
        Badges.validateGoldCollected();
        assertEquals(5, Badges.local.size());
    }

    @Test
    void validateLevelReached() {
        Dungeon.hero.lvl = 1;
        Badges.validateLevelReached();
        assertEquals(0, Badges.local.size());
        Dungeon.hero.lvl = 6;
        Badges.validateLevelReached();
        assertEquals(1, Badges.local.size());
        Dungeon.hero.lvl = 30;
        Badges.validateLevelReached();
        assertEquals(5, Badges.local.size());
    }

    @Test
    void validateStrengthAttained() {
        Dungeon.hero.STR = 10;
        Badges.validateStrengthAttained();
        assertEquals(0, Badges.local.size());
        Dungeon.hero.STR = 12;
        Badges.validateStrengthAttained();
        assertEquals(1, Badges.local.size());
        Dungeon.hero.STR = 20;
        Badges.validateStrengthAttained();
        assertEquals(5, Badges.local.size());
    }

    @Test
    void validateFoodEaten() {
        Statistics.foodEaten = 1;
        Badges.validateFoodEaten();
        assertEquals(0, Badges.local.size());
        Statistics.foodEaten = 10;
        Badges.validateFoodEaten();
        assertEquals(1, Badges.local.size());
        Statistics.foodEaten = 100;
        Badges.validateFoodEaten();
        assertEquals(5, Badges.local.size());
    }
}