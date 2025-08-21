package com.example.authserver.config;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

final class Jwks {
    private Jwks() {}

    static RSAKey generateRsa() {
        try {
            return new RSAKeyGenerator(2048)
                .keyID("demo-rsa-key")
                .generate();
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate RSA key", ex);
        }
    }
}

