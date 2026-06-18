package org.roomrental.group.RoomieHub.exception;

// This new class inherits everything from your AppException
public class DuplicateResourceException extends AppException {

    // Keep ONLY this constructor here!
    public DuplicateResourceException(String message) {
        super(message); // Sends the error message up to AppException
    }
}
