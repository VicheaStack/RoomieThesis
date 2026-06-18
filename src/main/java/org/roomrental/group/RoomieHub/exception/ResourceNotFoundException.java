package org.roomrental.group.RoomieHub.exception;

// This must extend AppException!
public class ResourceNotFoundException extends AppException {
    
    public ResourceNotFoundException(String message) {
        super(message); // Passes the text message up the chain
    }
}
