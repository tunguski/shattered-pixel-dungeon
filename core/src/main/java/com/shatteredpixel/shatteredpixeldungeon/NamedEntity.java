package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public interface NamedEntity {

    String name();

    default String title() {
        return Messages.get(this, name());
    }

    default String desc() {
        return Messages.get(this, name() + "_desc");
    }

    default String shortDesc() {
        return Messages.get(this, name() + "_desc_short");
    }
}
