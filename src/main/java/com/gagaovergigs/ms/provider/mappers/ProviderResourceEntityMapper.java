package com.gagaovergigs.ms.provider.mappers;

import com.gagaovergigs.ms.provider.api.models.V1Provider;
import com.gagaovergigs.ms.provider.exceptions.InvalidProviderTypeException;
import com.gagaovergigs.ms.provider.persistence.springdata.entities.Provider;
import org.springframework.stereotype.Component;

@Component
public class ProviderResourceEntityMapper {

    private static final String HENNA_ARTIST = "HENNA_ARTIST";

    public Provider resourceToEntity(V1Provider resource) throws InvalidProviderTypeException {
        Provider.ProviderType providerType = this.typeStringToEnum(resource.getProviderType());

        return new Provider(
                providerType,
                resource.getEmail(),
                resource.getFirstName(),
                resource.getLastName());
    }

    private Provider.ProviderType typeStringToEnum(String type) throws InvalidProviderTypeException {
        switch (type) {
            case HENNA_ARTIST:
                return Provider.ProviderType.HENNA_ARTIST;
            default:
                throw new InvalidProviderTypeException(type);
        }
    }
}
