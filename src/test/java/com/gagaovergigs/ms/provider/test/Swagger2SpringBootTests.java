package com.gagaovergigs.ms.provider.test;

import com.gagaovergigs.ms.provider.api.models.V1Provider;
import com.gagaovergigs.ms.provider.exceptions.InvalidProviderTypeException;
import com.gagaovergigs.ms.provider.mappers.ProviderResourceEntityMapper;
import com.gagaovergigs.ms.provider.persistence.jpa.entities.Provider;
import com.gagaovergigs.ms.provider.services.NoSQLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class Swagger2SpringBootTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoSQLService persistenceService;

    @Autowired
    private ProviderResourceEntityMapper mapper;

    private V1Provider resource;
    private Provider entity;

    @Before
    public void init() {
        resource = new V1Provider();
        resource.setEmail("a@b.com");
        resource.setFirstName("F");
        resource.setLastName("L");
        resource.setType("henna-artist");

        try {
            entity = mapper.resourceToEntity(resource);
        } catch (InvalidProviderTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPOSTRequest() {
        persistenceService.deleteById(entity.getEmail());

        V1Provider response = this.restTemplate.postForObject(
                "/v1/provider",
                resource,
                V1Provider.class);

        assertThat(response).isEqualToComparingFieldByField(resource);
    }

    @Test
    public void testPOSTedData() {
        Provider entity = persistenceService.getById(this.entity.getEmail());
        assertThat(entity).isEqualToComparingFieldByField(this.entity);
    }

}
