package com.gagaovergigs.ms.provider.services;

import com.gagaovergigs.ms.provider.exceptions.ProviderAlreadyExistsException;
import com.gagaovergigs.ms.provider.persistence.springdata.entities.Provider;
import com.gagaovergigs.ms.provider.persistence.springdata.repositories.IProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoSQLService {
    private final IProviderRepository providerRepository;

    @Autowired
    public NoSQLService(IProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider getById(String email) {
        return providerRepository.findOne(Provider.getIdFromEmail(email));
    }

    public void insertProvider(Provider provider) throws ProviderAlreadyExistsException {
        if (providerRepository.findOne(provider.getId()) != null) {
            throw new ProviderAlreadyExistsException(provider.getEmail());
        }
        providerRepository.save(provider);
    }

    public void deleteById(String email) {
        providerRepository.delete(Provider.getIdFromEmail(email));
    }
}
