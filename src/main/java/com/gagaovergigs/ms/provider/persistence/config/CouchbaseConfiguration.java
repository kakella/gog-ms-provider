package com.gagaovergigs.ms.provider.persistence.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.gagaovergigs.ms.provider"})
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {
    @Value("${couchbase.host:localhost}")
    private String couchbaseHost;

    @Value("${couchbase.bucketName:default}")
    private String couchbaseBucketName;

    @Value("${couchbase.bucketPass:}")
    private String couchbaseBucketPass;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(couchbaseHost);
    }

    @Override
    protected String getBucketName() {
        return couchbaseBucketName;
    }

    @Override
    protected String getBucketPassword() {
        return couchbaseBucketPass;
    }

}
