# Agloval Quotation API - Project Constitution

## Architecture
- **Pattern**: Hexagonal (Ports & Adapters)
- **Core**: domain/ (logica de negocio pura, sin Spring)
- **Application**: application/ (casos de uso, DTOs, puertos)
- **Infrastructure**: infrastructure/ (controllers, JPA, config)
- **Entry**: Controllers -> Services -> Domain, nunca lo contrario
- **Dependency direction**: Siempre apunta HACIA el domain, nunca hacia afuera

## Code Style
- **Language**: English (todas las clases, métodos, variables, comentarios)
- **Naming**: PascalCase (User, ProductController), camelCase (getUserId), UPPERCASE (MAX_RETRIES)
- **Methods**: <30 líneas máximo, una responsabilidad por método
- **Classes**: Una responsabilidad por clase (Single Responsibility Principle)
- **Package structure**: Sigue la carpeta física (com.agloval.infrastructure.persistence.*)

## Testing
- **Framework**: JUnit5 + Mockito
- **Target coverage**: >60% para producción
- **Unit tests**: services/ sin BD (mocks de repositories)
- **Integration tests**: persistence/ con @DataJpaTest
- **Controller tests**: rest/ con MockMvc
- **Test naming**: testMethod_WhenCondition_ThenExpectedBehavior

## Database
- **ORM**: JPA/Hibernate
- **Migration**: Flyway (src/main/resources/db/migration/V###__*.sql)
- **Entities**: Sí bidireccionales @ManyToOne/@OneToMany, con LAZY loading
- **Validation**: @NotNull, @NotBlank, @Min, @Max en entidades

## Security (Enterprise-Grade - Fase D)

### JWT Configuration
**Access Token:**
- Duration: 15 minutes (short-lived)
- Claims: sub (userId), roles, iat, exp, jti
- Algorithm: HS256 (HMAC with SHA-256)
- Signed with: application.jwt.secret (load from env)

**Refresh Token:**
- Duration: 7 days (long-lived)
- Stored in: separate token table (JwtRefreshToken entity)
- Rotation: each refresh generates new refresh + access token
- Revocation: delete from DB

**Token Provider Class (JwtTokenProvider.java):**
```java
@Component
public class JwtTokenProvider {
    private final String jwtSecret = env.getProperty("app.jwt.secret");
    private final long ACCESS_TOKEN_EXPIRY = 15 * 60 * 1000; // 15 min
    private final long REFRESH_TOKEN_EXPIRY = 7 * 24 * 60 * 60 * 1000; // 7 days
    
    // generateAccessToken(userId, roles) → JWT
    // generateRefreshToken(userId) → JWT + save to DB
    // validateToken(token) → true/false
    // getClaimsFromToken(token) → Map
    // revokeRefreshToken(token) → delete from DB
}
```

### Password Security
- **Encoding:** BCrypt with strength 12 (rounds)
- **Validation Rules:**
    - Minimum 8 characters
    - At least 1 uppercase letter
    - At least 1 number
    - At least 1 special character (!@#$%^&*)
- **No plaintext logging** - passwords never in logs
- **Password Reset:** email link with temporary token (valid 1 hour)

### Endpoint Protection
Public endpoints:
POST /api/auth/login        (no auth required)
POST /api/auth/register     (no auth required)
Protected (ROLE_CLIENT):
GET /api/quotations         (own quotations)
POST /api/quotations        (create own)
GET /api/quotations/{id}    (own only)
PUT /api/quotations/{id}    (own only)
Protected (ROLE_ADMIN):
GET /api/quotations         (all)
DELETE /api/quotations/{id} (any)
POST /api/products          (create)
PUT /api/products/{id}      (update)
DELETE /api/products/{id}   (delete)

### Roles
- **ROLE_CLIENT:** create/view own quotations, view own profile
- **ROLE_ADMIN:** manage products, view all quotations, delete any

### CORS Configuration
**Development:**
Allowed Origins: http://localhost:3000, http://localhost:8080
Allowed Methods: GET, POST, PUT, DELETE, OPTIONS
Allowed Headers: Authorization, Content-Type, Accept
Credentials: true

**Production:**
Allowed Origins: https://agloval.es, https://app.agloval.es
Allowed Methods: GET, POST, PUT, DELETE
Allowed Headers: Authorization, Content-Type
Credentials: true

### Rate Limiting
- `/api/auth/login`: max 5 failed attempts per IP in 15 minutes → 429 Too Many Requests
- Protected endpoints: max 100 requests per minute per user
- Implementation: Spring Cloud Gateway or custom interceptor

### Exception Handling (HTTP Status Codes)
401 Unauthorized:

Token expired
Invalid token signature
Token missing
Invalid credentials (login failed)

403 Forbidden:

Valid token, but insufficient permissions
Client accessing admin endpoint
Accessing quotation of other user

400 Bad Request:

Invalid password format
Missing required fields

500 Server Error:

Don't expose JWT secret or internal errors


### Audit Logging
**Log events (without sensitive data):**

User login: user_id, timestamp, ip_address, success/failure
Token refresh: user_id, timestamp
Endpoint access (admin-only): user_id, endpoint, timestamp
Unauthorized access attempts: ip_address, attempted_endpoint, timestamp
Password change: user_id, timestamp


**What NOT to log:**
- ❌ Passwords (plaintext or hashed)
- ❌ JWT tokens (full token)
- ❌ Refresh tokens
- ❌ API secrets or keys

### Testing Security (@SpringBootTest or @WebMvcTest)
JwtTokenProvider tests:
✓ generateAccessToken returns valid JWT
✓ generateRefreshToken saves to DB
✓ validateToken returns true for valid token
✓ validateToken returns false for expired token
✓ validateToken returns false for tampered token
✓ getClaimsFromToken extracts correct userId and roles
✓ revokeRefreshToken deletes from DB
SecurityConfig tests:
✓ ROLE_CLIENT cannot access /api/products (403)
✓ ROLE_ADMIN can access /api/products (200)
✓ Client can access own quotations (200)
✓ Client cannot access other user's quotations (403)
AuthController tests:
✓ POST /api/auth/login with valid credentials returns access_token + refresh_token
✓ POST /api/auth/login with invalid credentials returns 401
✓ POST /api/auth/login with invalid email format returns 400
✓ POST /api/auth/refresh with expired refresh token returns 401
✓ POST /api/auth/logout revokes refresh token
✓ After logout, refresh token cannot be used
Integration tests:
✓ Login → get token → access protected endpoint (200)
✓ Login → token expires → refresh → new token works (200)
✓ Login → token modified → access fails (401)
✓ Login → logout → token revoked (401 on refresh)
✓ Rate limiting: 6th login attempt in 15 min returns 429

### Folder Structure
infrastructure/security/
├─ JwtTokenProvider.java              (token generation/validation)
├─ JwtAuthenticationFilter.java        (JWT request interceptor)
├─ SecurityConfig.java                 (Spring Security @Configuration)
├─ PasswordValidator.java              (password strength validation)
├─ PasswordEncoderConfig.java         (BCrypt bean, strength=12)
├─ UserDetailsServiceImpl.java         (load user from DB)
├─ RoleAuthorizationException.java    (custom exception)
└─ SecurityUtil.java                  (extract userId from token, etc.)
domain/entity/
├─ User.java                           (updated: add password, roles, createdAt)
├─ JwtRefreshToken.java               (store refresh tokens in DB)
└─ AuditLog.java                      (optional: log security events)

### Configuration (application.yml)
```yaml
app:
  jwt:
    secret: ${JWT_SECRET:your-secret-key-min-32-chars}  # Load from env
    access-token-expiry: 900000     # 15 minutes in ms
    refresh-token-expiry: 604800000 # 7 days in ms
  
  security:
    bcrypt-strength: 12
    password-min-length: 8
    rate-limit:
      login-attempts: 5
      window-minutes: 15

spring:
  security:
    cors:
      allowed-origins: http://localhost:3000, http://localhost:8080
      allowed-methods: GET,POST,PUT,DELETE,OPTIONS
      allowed-headers: Authorization,Content-Type
      allow-credentials: true
```

### Dependencies (pom.xml)
```xml
<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Rate limiting (optional) -->
<dependency>
    <groupId>io.github.bucket4j</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>7.6.0</version>
</dependency>
```

### Key Points
- **Stateless:** No sessions, every request self-contained with JWT
- **Token Rotation:** Refresh tokens are rotated to prevent token reuse
- **Audit Trail:** Security events logged (logins, endpoint access, failures)
- **Rate Limiting:** Prevents brute force on login
- **Secure Defaults:** Short token expiry, strong password requirements
- **Error Handling:** Don't expose internal details in error messages

## Building & Running
- **Build**: mvn clean install
- **Dev**: mvn spring-boot:run
- **Test**: mvn test
- **Docker**: docker-compose -f docker-compose.yml up
- **Database**: PostgreSQL en docker-compose, H2 en tests

## Commits
- **Format**: Conventional Commits (feat:, fix:, refactor:, test:, docs:, chore:)
- **Scope**: (auth), (quotation), (persistence), (pdf), (validation)
- **Body**: Incluye WHY y HOW si es complejo (no QUÉ, eso es el código)
- **Example**: feat(quotation): implement quotation calculation with volume discounts

## Common Commands
```bash
mvn clean install
mvn spring-boot:run
mvn test
docker-compose up
```

## Key Files to Remember
- pom.xml: Dependencies, profiles
- application.yml: Main config
- db/migration/: All DB schema changes
- domain/: NEVER import Spring here
- infrastructure/config/: All @Configuration, @Bean

## When Stuck
1. Check CLAUDE.md first
2. Review domain/entity/ para entender modelo
3. Revisa application/port/out/ para interfaces
4. Mira infrastructure/output/ para implementaciones
5. Tests in src/test/ for examples

## Established Patterns
- Services in application/ inject ports (interfaces), not implementations
- Repositories implement ports, not controllers
- DTOs map between REST and domain
- Exceptions in domain/ inherits RuntimeException
- GlobalExceptionHandler catches and converts to HTTP