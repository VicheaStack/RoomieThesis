package org.roomrental.group.RoomieHub.exception;

import org.springframework.security.access.AccessDeniedException;
import java.util.NoSuchElementException;

public class AppException {

    public static RuntimeException of(String message) {
        if (message == null) return new RuntimeException("Unexpected error");

        String lower = message.toLowerCase();

        if (lower.contains("not found") || lower.contains("no such")) {
            return new NoSuchElementException(message);
        }
        if (lower.contains("already") || lower.contains("duplicate")) {
            return new IllegalStateException(message);
        }
        if (lower.contains("only") && (lower.contains("owner") || lower.contains("renter") || lower.contains("admin"))) {
            return new AccessDeniedException(message);
        }
        if (lower.contains("cannot") || lower.contains("must") || lower.contains("invalid") || lower.contains("empty")) {
            return new IllegalArgumentException(message);
        }

        return new RuntimeException(message);
    }
}