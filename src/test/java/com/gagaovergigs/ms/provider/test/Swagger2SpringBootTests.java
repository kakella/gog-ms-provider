package com.gagaovergigs.ms.provider.test;

import com.gagaovergigs.ms.provider.api.models.V1Provider;
import com.gagaovergigs.ms.provider.exceptions.InvalidProviderTypeException;
import com.gagaovergigs.ms.provider.mappers.ProviderResourceEntityMapper;
import com.gagaovergigs.ms.provider.persistence.springdata.entities.Provider;
import com.gagaovergigs.ms.provider.services.NoSQLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private V1Provider providerResource;
    private Provider providerEntity;

    @Before
    public void init() {
        providerResource = new V1Provider();
        providerResource.setEmail("a@b.com");
        providerResource.setFirstName("F");
        providerResource.setLastName("L");
        providerResource.setProviderType("henna-artist");

        try {
            providerEntity = mapper.resourceToEntity(providerResource);
        } catch (InvalidProviderTypeException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPOSTRequest_documentDoesNotExist() {
        persistenceService.deleteById(providerEntity.getEmail());

        V1Provider response = this.restTemplate.postForObject(
                "/v1/provider",
                providerResource,
                V1Provider.class);

        assertThat(response).isEqualToComparingFieldByField(providerResource);
    }

    @Test
    public void testPOSTRequest_documentAlreadyExists() {
        ResponseEntity<V1Provider> response = this.restTemplate.postForEntity(
                "/v1/provider",
                providerResource,
                V1Provider.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPOSTRequest_invalidProviderType() {
        providerResource.setEmail("c@d.com");
        providerResource.setProviderType("dummy");
        ResponseEntity<V1Provider> response = this.restTemplate.postForEntity(
                "/v1/provider",
                providerResource,
                V1Provider.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPOSTRequest_invalidEmailAddress() {
        providerResource.setEmail("ab.com");
        ResponseEntity<V1Provider> response = this.restTemplate.postForEntity(
                "/v1/provider",
                providerResource,
                V1Provider.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPOSTRequest_verifyDataPersisted() {
        Provider entity = persistenceService.getById(this.providerEntity.getEmail());
        assertThat(entity).isEqualToComparingFieldByField(this.providerEntity);
        assertThat(entity.getId()).isEqualTo("provider:" + entity.getEmail());
        assertThat(entity.getDocType()).isEqualTo("provider");
    }

    @Test
    public void testEntityKeyHandling() {
        this.providerEntity.setEmail("x@y.com");
        assertThat(this.providerEntity.getId()).isEqualTo("provider:" + this.providerEntity.getEmail());
    }

}
