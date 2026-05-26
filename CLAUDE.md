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

## Security
- **Auth**: JWT tokens (stateless)
- **Encoding**: BCrypt (PasswordEncoder bean)
- **Roles**: ROLE_CLIENT, ROLE_ADMIN
- **Endpoints**: @PreAuthorize("hasRole('ADMIN')") en métodos sensibles

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