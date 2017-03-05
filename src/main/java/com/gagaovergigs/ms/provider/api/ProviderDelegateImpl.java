package com.gagaovergigs.ms.provider.api;

import com.gagaovergigs.ms.provider.api.models.V1ErrorResponse;
import com.gagaovergigs.ms.provider.api.models.V1Provider;
import com.gagaovergigs.ms.provider.exceptions.InvalidProviderTypeException;
import com.gagaovergigs.ms.provider.exceptions.ProviderAlreadyExistsException;
import com.gagaovergigs.ms.provider.mappers.ProviderResourceEntityMapper;
import com.gagaovergigs.ms.provider.services.NoSQLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

@Component
public class ProviderDelegateImpl implements V1ApiDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderDelegateImpl.class);

    private NoSQLService persistenceService;

    private ProviderResourceEntityMapper providerResourceEntityMapper;

    @Autowired
    public ProviderDelegateImpl(NoSQLService persistenceService, ProviderResourceEntityMapper mapper) {
        this.persistenceService = persistenceService;
        this.providerResourceEntityMapper = mapper;
    }

    @Override
    public ResponseEntity<V1Provider> createResource(V1Provider provider) {

        try {
            persistenceService.insertProvider(providerResourceEntityMapper.resourceToEntity(provider));
        } catch (InvalidProviderTypeException | ProviderAlreadyExistsException | ConstraintViolationException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace().toString());

            V1ErrorResponse error = new V1ErrorResponse();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(provider);
    }
}
