package com.gagaovergigs.ms.provider.persistence.jpa.entities;

import com.couchbase.client.java.repository.annotation.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
public class Provider {
    @Field
    @ReadOnlyProperty
    private static final String docType = "provider";
    @Id
    private String id;
    @Field
    @NotNull
    private ProviderType providerType;

    @Field
    private String email;

    @Field
    private String firstName, lastName;

    public Provider(ProviderType providerType, String email, String firstName, String lastName) {
        this.id = Provider.getIdFromEmail(email);
        this.providerType = providerType;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static String getIdFromEmail(String email) {
        return Provider.docType + ":" + email;
    }

    public String getId() {
        return id;
    }

    public String getDocType() {
        return docType;
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(ProviderType providerType) {
        this.providerType = providerType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.id = docType + ":" + email;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public enum ProviderType {
        HENNA_ARTIST,
        FACE_PAINTER
    }
}
