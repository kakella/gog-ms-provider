package com.gagaovergigs.ms.provider.exceptions;

public class InvalidProviderTypeException extends Exception {
    public InvalidProviderTypeException(String type) {
        super("Invalid `type` attribute value `" + type + "` for `Provider` document");
    }
}
