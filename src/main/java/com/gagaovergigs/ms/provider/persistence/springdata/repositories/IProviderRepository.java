package com.gagaovergigs.ms.provider.persistence.springdata.repositories;

import com.gagaovergigs.ms.provider.persistence.springdata.entities.Provider;
import org.springframework.data.repository.CrudRepository;

public interface IProviderRepository extends CrudRepository<Provider, String> {

}
