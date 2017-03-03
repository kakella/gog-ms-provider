package com.gagaovergigs.ms.provider.api;

import com.gagaovergigs.ms.provider.api.models.V1Provider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProviderDelegateImpl implements V1ApiDelegate {
    @Override
    public ResponseEntity<V1Provider> createResource(V1Provider provider) {
        return ResponseEntity.ok(provider);
    }
}
