package com.gagaovergigs.ms.provider.mappers;

import com.gagaovergigs.ms.provider.api.models.V1Provider;
import com.gagaovergigs.ms.provider.exceptions.InvalidProviderTypeException;
import com.gagaovergigs.ms.provider.persistence.jpa.entities.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderResourceEntityMapper {
    public Provider resourceToEntity(V1Provider resource) throws InvalidProviderTypeException {
        Provider.ProviderType type = this.typeStringToEnum(resource.getType());

        return new Provider(
                type,
                resource.getEmail(),
                resource.getFirstName(),
                resource.getLastName());
    }

    private Provider.ProviderType typeStringToEnum(String type) throws InvalidProviderTypeException {
        switch (type) {
            case "henna-artist":
                return Provider.ProviderType.HENNA_ARTIST;
            case "face-painter":
                return Provider.ProviderType.FACE_PAINTER;
            default:
                throw new InvalidProviderTypeException(type);
        }
    }
}
