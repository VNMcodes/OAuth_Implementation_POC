## Spring Boot OAuth 2.0 Demo (Java 17, Maven)

This demo showcases a simple OAuth 2.0 setup with:

- Authorization Server (Spring Authorization Server)
- Resource Server (validates JWTs issued by the auth server)

### Modules

- `auth-server` (port 9000)
- `resource-server` (port 8081)

### Requirements

- Java 17
- Maven 3.9+

### Build

```bash
mvn -q -f ./pom.xml clean package
```

### Run

In separate terminals:

```bash
mvn -q -f ./auth-server/pom.xml spring-boot:run
```

```bash
mvn -q -f ./resource-server/pom.xml spring-boot:run
```

### Default Users (in-memory)

- `user` / `password` (ROLE_USER)
- `admin` / `password` (ROLE_ADMIN, ROLE_USER)

### Registered OAuth Client (in-memory)

- `client_id`: `demo-client`
- `client_secret`: `demo-secret`
- `redirect_uris`:
  - `http://localhost:8081/login/oauth2/code/demo-client`
  - `http://127.0.0.1:8081/login/oauth2/code/demo-client`
- `scopes`: `openid`, `profile`, `api.read`
- `grants`: Authorization Code, Client Credentials, Refresh Token

### Test the Flow

1) Get Authorization Code and exchange for token

Open:

`http://localhost:9000/oauth2/authorize?response_type=code&client_id=demo-client&scope=openid%20profile%20api.read&redirect_uri=http://localhost:8081/login/oauth2/code/demo-client`

Login with `user/password`, consent, then you'll be redirected to the resource server path with a `code` parameter. Since the resource server isn't acting as an OAuth2 client in this demo, copy the code and exchange via curl:

```bash
curl -u demo-client:demo-secret \
  -d grant_type=authorization_code \
  -d redirect_uri=http://localhost:8081/login/oauth2/code/demo-client \
  -d code=REPLACE_WITH_CODE \
  http://localhost:9000/oauth2/token
```

The response includes an `access_token` (JWT).

2) Call protected API

```bash
ACCESS_TOKEN=REPLACE
curl -H "Authorization: Bearer $ACCESS_TOKEN" http://localhost:8081/api/hello
```

3) Client Credentials flow

```bash
curl -u demo-client:demo-secret \
  -d grant_type=client_credentials \
  -d scope=api.read \
  http://localhost:9000/oauth2/token
```

Then call the API using the returned access token.

### Notes

- Keys are generated in-memory on startup; tokens become invalid after restart.
- For production, persist clients, users, and keys; enable HTTPS; and configure stronger policies.

