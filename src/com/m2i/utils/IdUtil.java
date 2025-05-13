package com.m2i.utils;

import java.util.UUID;

public final class IdUtil {
    private IdUtil() { /* empêche l'instanciation */ }

    public static String generateId() {
        return "ID-" + UUID.randomUUID();
    }
}
