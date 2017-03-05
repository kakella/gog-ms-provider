package com.gagaovergigs.ms.provider.exceptions;

public class ProviderAlreadyExistsException extends Exception {
    public ProviderAlreadyExistsException(String email) {
        super("Provider with email `" + email + "` already exists");
    }
}
