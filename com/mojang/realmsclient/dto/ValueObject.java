package com.mojang.realmsclient.dto;

import java.lang.reflect.*;

public abstract class ValueObject
{
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        final Field[] fields = this.getClass().getFields();
        while (0 < fields.length) {
            final Field field = fields[0];
            if (!isStatic(field)) {
                sb.append(field.getName()).append("=").append(field.get(this)).append(" ");
            }
            int n = 0;
            ++n;
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');
        return sb.toString();
    }
    
    private static boolean isStatic(final Field field) {
        return Modifier.isStatic(field.getModifiers());
    }
}
