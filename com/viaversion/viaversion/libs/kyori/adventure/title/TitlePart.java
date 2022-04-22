package com.viaversion.viaversion.libs.kyori.adventure.title;

import org.jetbrains.annotations.*;

@ApiStatus.NonExtendable
public interface TitlePart
{
    public static final TitlePart TITLE = new TitlePart() {
        @Override
        public String toString() {
            return "TitlePart.TITLE";
        }
    };
    public static final TitlePart SUBTITLE = new TitlePart() {
        @Override
        public String toString() {
            return "TitlePart.SUBTITLE";
        }
    };
    public static final TitlePart TIMES = new TitlePart() {
        @Override
        public String toString() {
            return "TitlePart.TIMES";
        }
    };
}
