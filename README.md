### Project Scope & Status

**This is a demonstration MVP**, not a production system currently deployed at Agloval.

**What's Included (v0.1-v1.3):**
- ✅ Fully functional REST API
- ✅ JWT authentication + role-based access
- ✅ Quotation calculation engine
- ✅ PDF generation
- ✅ Docker containerization
- ✅ Professional testing (>60% coverage)

**What's NOT Included Yet:**
- ❌ Frontend/Web UI (planned as separate project)
- ❌ Integration with Agloval's existing systems
- ❌ Deployment on Agloval's production servers
- ❌ Email notifications (could be added)

**Next Steps (After v1.3):**
If Agloval approves this approach, I would:
1. Build a professional frontend (React/Vue)
2. Integrate with their existing database
3. Deploy on their production environment
4. Add email notifications
5. Train staff on the system
   Cambio 3: En "Quick Start" aclarar que es local
   Agregar:
   markdown**Note:** This runs locally in development mode. Production deployment would require additional steps (database migration, server setup, etc).

Demonstration REST API showcasing automated quotation generation for Agloval SL (MVP proof-of-concept).

**Important:** This is a professional portfolio project demonstrating a solution for Agloval. The API is fully functional but not yet integrated with Agloval's production systems. Frontend, database integration, and production deployment would follow if the concept is approved.

**Current Version:** v0.2.0 (Phase B - REST API Complete) | [Releases](../../releases)

---

## 📊 Current Status

**Version:** v0.2.0  
**Phase:** B - REST API Complete  
**Status:** ✅ Complete and Stable  
**Environment:** Development/Demo (Local)

### Features in v0.2.0 (Current)

#### Core Features
- ✅ **15 REST endpoints** (CRUD for User, Product, Quotation)
- ✅ **Input validation** with `@Valid` + custom constraints
- ✅ **Centralized error handling** with `GlobalExceptionHandler`
- ✅ **Correct HTTP status codes** (201 Created, 204 No Content, 400 Bad Request, 404 Not Found, 409 Conflict, 500 Server Error)
- ✅ **Professional error responses** with status + message + timestamp + validation errors list
- ✅ **Swagger/OpenAPI documentation** at `/swagger-ui.html`
- ✅ **MockMvc tests** for controllers + Mockito unit tests for services (~52% coverage)

#### Endpoints Implemented

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/users` | Create user |
| GET | `/api/v1/users` | List all users |
| GET | `/api/v1/users/{id}` | Get user by ID |
| PUT | `/api/v1/users/{id}` | Update user |
| DELETE | `/api/v1/users/{id}` | Delete user |
| POST | `/api/v1/products` | Create product |
| GET | `/api/v1/products` | List all products |
| GET | `/api/v1/products/{id}` | Get product by ID |
| PUT | `/api/v1/products/{id}` | Update product |
| DELETE | `/api/v1/products/{id}` | Delete product |
| POST | `/api/v1/quotations` | Create quotation with lines |
| GET | `/api/v1/quotations` | List all quotations |
| GET | `/api/v1/quotations/{id}` | Get quotation by ID |
| PATCH | `/api/v1/quotations/{id}/status` | Update quotation status |
| GET | `/api/v1/quotations/user/{userId}` | Get quotations by user |

#### Code Quality
- Hexagonal Architecture (domain → application → infrastructure)
- Clean Code principles (methods <30 lines, single responsibility)
- SOLID principles respected
- Manual DTO ↔ entity mapping (no MapStruct dependency)
- Quotation line total calculation with discount factor

#### Testing (v0.2.0)
- 26 tests passing, 0 failures
- `UserServiceTest`, `ProductServiceTest` — Mockito unit tests
- `UserControllerTest`, `ProductControllerTest` — MockMvc integration tests
- `application-test.yml` — H2 in-memory for test slice

### Features in v0.1.0

- ✅ Maven project scaffold with Spring Boot 3.4
- ✅ Domain entities (User, Product, Quotation, QuotationLine) with full JPA mappings
- ✅ OneToMany / ManyToOne relationships with LAZY loading
- ✅ Flyway migration `V001__create_initial_schema.sql`
- ✅ CLAUDE.md development guidelines
- ✅ Git initialized with Conventional Commits

### Roadmap

- **v0.3.0 (Phase C):** Spring Security + JWT authentication + role-based access control
- **v1.0.0 (Phase D):** Quotation calculation engine with volume discounts
- **v1.1.0 (Phase E):** PDF generation
- **v1.2.0 (Phase F):** Docker full containerization (app + DB) + deployment setup
- **v1.3.0 (Phase G):** Code polishing, performance optimization, >60% coverage

---

## 🎯 Project Scope & Status

### What This MVP Demonstrates

This project showcases a complete backend solution for automating Agloval's quotation process:

- **Fully functional REST API** with professional architecture (Hexagonal pattern)
- **JWT authentication** with role-based access control
- **Complex business logic** (quotation calculations, volume discounts, service pricing)
- **PDF generation** for professional quotations
- **Docker containerization** for easy deployment
- **Professional testing** (>60% coverage by v1.3.0)
- **Production-grade code quality** following SOLID principles and Clean Code

### What's NOT Included Yet

- ❌ **Frontend/Web UI** - Would be built as a separate React/Vue project
- ❌ **Integration with Agloval's existing systems** - Current implementation uses demo data
- ❌ **Deployment on Agloval's production servers** - Runs locally in development
- ❌ **Email notifications** - Could be added in future phases
- ❌ **Payment processing** - Not in scope for MVP

### If Agloval Approves This Approach

The next steps would be:

1. **Build Professional Frontend**
   - React or Vue application
   - User dashboard, quotation management UI
   - Real-time calculations and preview
   - PDF download functionality

2. **Integrate with Agloval's Existing Systems**
   - Migrate data from current database
   - Sync with ERP/inventory systems
   - Historical quotation import

3. **Production Deployment**
   - Setup on Agloval's production servers
   - Configure PostgreSQL on production
   - SSL/TLS certificates
   - Backup and disaster recovery

4. **Staff Training**
   - User documentation
   - Admin training
   - Support procedures

5. **Ongoing Maintenance**
   - Bug fixes and improvements
   - Performance monitoring
   - Feature enhancements

### Current Environment

**This MVP runs locally in development mode:**
- Local PostgreSQL (via Docker Compose)
- Development credentials
- Demo data for testing
- H2 in-memory database for testing

**Note:** Production deployment would require additional infrastructure setup, security hardening, and data migration.

---

## 🎯 Problem & Solution

### Current State (Manual Process)

Agloval currently handles quotations manually:
- Manual quotation calculation → 2 days of work per batch
- No centralized history or traceability
- Occasional calculation errors
- No digital archive system
- 200-300 quotations per month → slow, error-prone

### Proposed Solution (This API)

Automated quotation system providing:
1. Professional clients access portal
2. Select products + additional services (cutting, edging)
3. API automatically calculates prices (m², discounts, services)
4. Real-time quotation preview
5. Generate professional PDF
6. Save as draft, share, collect feedback
7. Centralized quotation history with searchable archive
8. Full audit trail of all quotations

---

## 📋 Table of Contents

1. [Tech Stack](#tech-stack)
2. [Architecture](#architecture)
3. [Quick Start](#quick-start)
4. [Project Phases](#project-phases)
5. [Development Guidelines](#development-guidelines)
6. [Testing](#testing)
7. [Documentation](#documentation)
8. [Contributing](#contributing)

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Java 21+ |
| **Framework** | Spring Boot 3.4 |
| **Database** | PostgreSQL 15+ (production), H2 (testing) |
| **ORM** | JPA/Hibernate |
| **Authentication** | JWT + BCrypt |
| **Testing** | JUnit5 + Mockito |
| **API Documentation** | Swagger/OpenAPI 3.0 |
| **PDF Generation** | iText 7 |
| **Database Migrations** | Flyway |
| **Build Tool** | Maven |
| **Containerization** | Docker + Docker Compose |
| **Version Control** | Git + GitHub |

---

## 🏗️ Architecture

**Pattern:** Hexagonal (Ports & Adapters)

### Design Philosophy

The application is structured in three independent layers with clear separation of concerns. Dependencies always point inward toward the domain layer.
┌─────────────────────────────────────────────────────────────┐
│ INFRASTRUCTURE LAYER (Spring, JPA, External Tech)           │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ - REST Controllers (@RestController)                    │ │
│ │ - JPA Repositories (Spring Data JPA)                    │ │
│ │ - Spring Configuration (@Configuration, @Bean)         │ │
│ │ - External Adapters (PDF generation, Email, etc)       │ │
│ └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
↓ depends on
┌─────────────────────────────────────────────────────────────┐
│ APPLICATION LAYER (Use Cases, Ports, DTOs)                  │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ - Application Services (orchestrate use cases)          │ │
│ │ - Input Ports (interfaces defining entry points)        │ │
│ │ - Output Ports (interfaces for external systems)        │ │
│ │ - DTOs (Request/Response objects)                       │ │
│ │ - Mappers (Entity ↔ DTO conversion)                     │ │
│ └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
↓ depends on
┌─────────────────────────────────────────────────────────────┐
│ DOMAIN LAYER (Business Logic - ZERO Spring Dependencies)    │
│ ┌─────────────────────────────────────────────────────────┐ │
│ │ - Entities (@Entity for JPA, but domain-focused)        │ │
│ │ - Domain Services (pure business logic, no @Service)    │ │
│ │ - Value Objects (Money, Dimensions, QuotationStatus)    │ │
│ │ - Custom Exceptions (domain-specific)                   │ │
│ │ - Business Rules (validation logic)                     │ │
│ └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘

### Why Hexagonal Architecture?

- **Decoupled:** Change database from PostgreSQL to MongoDB = modify 1 adapter file
- **Testable:** Test business logic without initializing Spring (10x faster tests)
- **Scalable:** Grows from MVP to enterprise without refactoring
- **Professional:** Industry standard in tier-1 companies (Aubay, Sopra Steria, NTT Data)
- **Maintainable:** Clear boundaries = easy to understand and modify

### Project Structure
agloval-quotation-api/
│
├── src/main/java/com/agloval/
│
│   ├── domain/                              [CORE - Business Logic]
│   │   ├── entity/
│   │   │   ├── User.java
│   │   │   ├── Product.java
│   │   │   ├── Category.java
│   │   │   ├── Quotation.java
│   │   │   └── QuotationLine.java
│   │   │
│   │   ├── service/
│   │   │   ├── PricingCalculator.java       [Pure business logic]
│   │   │   ├── DiscountCalculator.java
│   │   │   └── ValidationService.java
│   │   │
│   │   ├── exception/
│   │   │   ├── QuotationNotFoundException.java
│   │   │   ├── InvalidMeasurementsException.java
│   │   │   └── InsufficientStockException.java
│   │   │
│   │   └── value/
│   │       ├── Money.java
│   │       ├── Dimensions.java
│   │       └── QuotationStatus.java
│   │
│   ├── application/                         [USE CASES]
│   │   ├── service/
│   │   │   ├── CreateQuotationService.java
│   │   │   ├── CalculateQuotationService.java
│   │   │   └── UpdateQuotationService.java
│   │   │
│   │   ├── port/
│   │   │   ├── in/
│   │   │   │   ├── CreateQuotationUseCase.java
│   │   │   │   ├── GetQuotationUseCase.java
│   │   │   │   └── CalculateQuotationUseCase.java
│   │   │   │
│   │   │   └── out/
│   │   │       ├── QuotationPersistencePort.java
│   │   │       ├── UserPersistencePort.java
│   │   │       ├── ProductPersistencePort.java
│   │   │       ├── PdfGenerationPort.java
│   │   │       └── NotificationPort.java
│   │   │
│   │   ├── dto/
│   │   │   ├── CreateQuotationRequest.java
│   │   │   ├── QuotationResponse.java
│   │   │   ├── QuotationLineDTO.java
│   │   │   └── ProductDTO.java
│   │   │
│   │   └── mapper/
│   │       ├── QuotationMapper.java
│   │       └── ProductMapper.java
│   │
│   └── infrastructure/                      [ADAPTERS]
│       ├── config/
│       │   ├── SecurityConfig.java
│       │   ├── JpaConfig.java
│       │   ├── MvcConfig.java
│       │   └── BeanConfiguration.java
│       │
│       ├── input/
│       │   └── rest/
│       │       ├── QuotationController.java
│       │       ├── ProductController.java
│       │       ├── UserController.java
│       │       └── GlobalExceptionHandler.java
│       │
│       ├── output/
│       │   ├── persistence/
│       │   │   ├── QuotationJpaRepository.java
│       │   │   ├── QuotationRepositoryAdapter.java
│       │   │   ├── UserRepositoryAdapter.java
│       │   │   └── ProductRepositoryAdapter.java
│       │   │
│       │   ├── pdf/
│       │   │   ├── ITextPdfGenerator.java
│       │   │   └── PdfGenerationAdapter.java
│       │   │
│       │   └── notification/
│       │       └── EmailNotificationAdapter.java
│       │
│       └── security/
│           ├── JwtTokenProvider.java
│           ├── JwtAuthenticationFilter.java
│           └── UserDetailsServiceImpl.java
│
├── src/main/resources/
│   ├── application.yml
│   ├── application-dev.yml
│   ├── application-prod.yml
│   ├── db/migration/                        [Flyway migrations]
│   │   └── V001__init_schema.sql
│   └── logback-spring.xml
│
├── src/test/java/com/agloval/
│   ├── domain/service/                      [Domain logic tests - no DB]
│   ├── application/service/                 [Service tests - with mocks]
│   └── infrastructure/
│       ├── rest/                            [Controller tests - MockMvc]
│       └── persistence/                     [JPA tests - @DataJpaTest]
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── .gitignore
├── .claudeignore
├── CLAUDE.md
├── README.md                                [You are here]
└── LICENSE

### Key Architecture Principles

1. **Dependency Inversion:** High-level modules (domain) don't depend on low-level modules (infrastructure). Both depend on abstractions (ports).

2. **Single Responsibility:** Each class has one reason to change.

3. **Open/Closed:** Open for extension (new discount types), closed for modification (existing logic unchanged).

4. **Clear Boundaries:** Controllers never import domain directly. Services implement ports. Repositories implement output ports.

5. **Testing Strategy:**
   - Domain services: Fast, no Spring, pure logic
   - Application services: Fast, mocked repositories
   - Controllers: Medium speed, MockMvc
   - Integration: Full stack, real database, slowest but most realistic

---

## 🚀 Quick Start

### Prerequisites

Verify you have the required tools installed:

```bash
# Check Java version (21 or higher)
java -version
# Should output: openjdk version "21" or higher

# Check Maven version (3.8 or higher)
mvn -version

# Docker (optional but recommended for database)
docker --version

# Git
git --version
```

If any are missing, install via Homebrew (macOS):

```bash
brew install java maven docker git
```

### Setup Local Environment

```bash
# 1. Clone the repository
git clone https://github.com/[your-github-username]/agloval-quotation-api
cd agloval-quotation-api

# 2. Build the project
mvn clean install
# Downloads dependencies and runs tests
# Should end with: BUILD SUCCESS

# 3. Run tests (verify everything works)
mvn test

# 4. View dependencies
mvn dependency:tree
```

### Running the Application (Development Mode)

From Phase B onwards:

```bash
# Option 1: Maven
mvn spring-boot:run

# Option 2: IDE (IntelliJ)
# Right-click AglovalApplication.java → Run

# Option 3: Docker (starts app + PostgreSQL)
docker-compose up
```

Once running, the API is available at: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui.html` (from v0.2.0+)

**Note:** This runs locally in development mode with demo data. Production deployment would require:
- PostgreSQL configured on production server
- Environment variables for security credentials
- SSL/TLS certificates
- Database backup procedures
- Load balancing and monitoring setup

### Running Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=QuotationServiceTest

# Specific test method
mvn test -Dtest=QuotationServiceTest#shouldCalculateTotal

# With coverage report
mvn test jacoco:report
# View at: target/site/jacoco/index.html
```

### Building for Production

```bash
mvn clean package -Pprod

# Run the JAR
java -jar target/agloval-quotation-api-1.0.0.jar
```

---

## 📊 Project Phases

This is a comprehensive 14-week project divided into 7 phases (2 weeks each). Each phase culminates in a stable, versioned release.

| Phase | Duration | Feature Focus | Version | Status |
|-------|----------|---------------|---------|--------|
| **A** | 2 weeks | Maven setup, Domain entities, JPA mappings | v0.1.0 | ✅ DONE |
| **B** | 2 weeks | REST API endpoints, input validation, error handling, Swagger | v0.2.0 | ✅ CURRENT |
| **C** | 2 weeks | Spring Security + JWT authentication + role-based access | v0.3.0 | ⏳ NEXT |
| **D** | 2 weeks | Quotation calculation engine, volume discounts, pricing logic | v1.0.0 | ⏳ PLANNED |
| **E** | 2 weeks | PDF generation | v1.1.0 | ⏳ PLANNED |
| **F** | 2 weeks | Docker full containerization + deployment setup | v1.2.0 | ⏳ PLANNED |
| **G** | 2 weeks | Code refactoring, final testing, performance optimization | v1.3.0 | ⏳ PLANNED |

### Phase A: Domain Layer (Current)

**What was built:**
- Maven project structure with Spring Boot 3.4
- 5 domain entities with proper JPA mappings
- OneToMany and ManyToOne relationships
- Flyway migration framework setup
- Development guidelines (CLAUDE.md)
- Test framework initialization

**Key learning outcomes:**
- Spring Boot project setup
- JPA entity design
- Entity relationships (@OneToMany, @ManyToOne)
- Maven dependency management

**Next phase deliverables:**
- Functional REST endpoints for CRUD operations
- Input validation with Bean Validation
- Professional error handling with GlobalExceptionHandler
- API documentation with Swagger

---

## 👨‍💻 Development Guidelines

### Code Style & Conventions

**Language:** English for all code (classes, methods, variables, comments)

**Naming Conventions:**
- Classes: PascalCase (User, ProductController, QuotationService)
- Methods/Variables: camelCase (getUserId, createQuotation, totalPrice)
- Constants: UPPERCASE_WITH_UNDERSCORES (MAX_QUANTITY, DEFAULT_CURRENCY)
- Package names: lowercase.dot.separated (com.agloval.domain.service)

**Method Length:** Maximum 30 lines (sign of too much responsibility)

**Class Responsibility:** Each class should have one reason to change (Single Responsibility Principle)

**Imports:** Explicit imports only (never import *)

### Commit Convention

Uses **[Conventional Commits](https://www.conventionalcommits.org/)** for clear, semantic commit history.

**Format:**
<type>(scope): <description>
[optional body explaining WHY and HOW, not WHAT]
[optional footer for breaking changes]

**Types:**
- `feat:` New feature
- `fix:` Bug fix
- `refactor:` Code restructuring (no behavior change)
- `test:` Adding or updating tests
- `docs:` Documentation changes
- `perf:` Performance improvements
- `chore:` Maintenance tasks (dependencies, build)

**Scopes (for this project):**
- `(setup)` - Project initialization, Maven config
- `(domain)` - Domain entities, business logic
- `(persistence)` - JPA, repositories, migrations
- `(rest)` - REST controllers, HTTP endpoints
- `(validation)` - Input validation, exceptions
- `(auth)` - JWT, security
- `(pdf)` - PDF generation
- `(testing)` - Test infrastructure
- `(docs)` - Documentation
- `(ci)` - CI/CD setup

**Example Commits:**

```bash
# Good
git commit -m "feat(domain): add quotation entity with jpa mappings

- Create Quotation entity with @OneToMany relationship to QuotationLine
- Add QuotationStatus enum (DRAFT, SENT, ACCEPTED, REJECTED)
- Add validation constraints (@NotNull, @NotBlank, @Positive)
- Include createdAt, updatedAt for audit trail"

# Also Good
git commit -m "fix(persistence): fix n+1 query problem in quotation list

- Replace @OneToMany(fetch=EAGER) with LAZY + @EntityGraph
- Add custom query with LEFT JOIN FETCH
- Reduces 100 queries to 1 for list operation"

# Also Good
git commit -m "docs(readme): update current status for v0.1.0"

# Bad - Too vague
git commit -m "fix: update code"

# Bad - Too detailed in message
git commit -m "feat: add line 1 to class A, line 2 to class B, line 3..."
```

### Code Review Checklist

Before committing, verify:

- [ ] Code compiles without warnings
- [ ] All tests pass
- [ ] No Spring imports in domain/ package
- [ ] Method names clearly describe what they do
- [ ] No methods longer than 30 lines
- [ ] Comments explain WHY, not WHAT
- [ ] No hardcoded values (use constants or configuration)
- [ ] Exception handling is explicit
- [ ] DTOs properly map to entities
- [ ] No SQL queries (use JPA)

---

## 🧪 Testing

### Testing Philosophy

- **Fast:** Domain tests run without Spring (10x faster)
- **Isolated:** Each test is independent, no shared state
- **Clear:** Test names describe what is being tested and expected result
- **Comprehensive:** Cover happy paths, edge cases, and error scenarios

### Test Structure
src/test/java/com/agloval/
├── domain/service/
│   ├── PricingCalculatorTest.java           [Unit tests - pure logic]
│   └── ValidationServiceTest.java
│
├── application/service/
│   ├── CreateQuotationServiceTest.java      [Service tests - with mocks]
│   └── CalculateQuotationServiceTest.java
│
└── infrastructure/
├── rest/
│   ├── QuotationControllerTest.java     [Controller tests - MockMvc]
│   └── ProductControllerTest.java
│
└── persistence/
├── QuotationRepositoryTest.java     [JPA tests - @DataJpaTest]
└── UserRepositoryTest.java

### Test Naming Convention
test[MethodName]_When[Condition]_Then[ExpectedBehavior]
Examples:

testCalculateTotal_WhenValidQuotation_ThenReturnCorrectTotal
testCreateUser_WhenEmailAlreadyExists_ThenThrowDuplicateException
testGetQuotations_WhenNoResults_ThenReturnEmptyList


### Running Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=QuotationServiceTest

# Specific test method
mvn test -Dtest=QuotationServiceTest#testCalculateTotal_WhenValidQuotation_ThenReturnCorrectTotal

# With coverage
mvn test jacoco:report

# View coverage report
open target/site/jacoco/index.html
```

### Current Testing Status

**Phase A Coverage:**
- Project structure validates
- Build succeeds
- Framework initialized

**Phase B & Beyond:**
- Unit tests for all domain services
- Integration tests for controllers
- Repository tests with @DataJpaTest
- Target: >60% overall coverage

---

## 📚 Documentation

### Available Documentation

- **[CLAUDE.md](CLAUDE.md)** - Complete development guide, patterns, common commands, architecture details
- **[BUSINESS_RULES.md](docs/BUSINESS_RULES.md)** - Business requirements from Agloval, pricing models, validation rules
- **API Documentation** - Swagger/OpenAPI (available from v0.2.0+)
- **This README** - Project overview and quick reference

### Documentation Standards

- Every public method has JavaDoc
- Complex logic has inline comments explaining WHY
- Architecture decisions are documented in CLAUDE.md
- Business rules are documented in BUSINESS_RULES.md

### JavaDoc Example

```java
/**
 * Calculates the total quotation price including discounts and services.
 *
 * @param quotation the quotation to calculate total for
 * @return the final price as BigDecimal
 * @throws InvalidMeasurementsException if quotation has invalid dimensions
 * @throws QuotationNotFoundException if quotation line references missing product
 */
public BigDecimal calculateTotal(Quotation quotation) {
    // implementation
}
```

---

## 🤝 Contributing

This is a professional portfolio project. Contributions welcome via GitHub issues and pull requests.

**Process:**
1. Create an issue describing the improvement
2. Discuss the approach
3. Create a feature branch
4. Commit changes with Conventional Commits
5. Submit pull request with clear description

---

## 📝 License

MIT License - See [LICENSE](LICENSE) for details

---

## 👤 Author

**Borja Rodríguez**  
Backend Developer in Training | Java + Spring Boot Specialist  
📍 Valencia, Spain  
🔗 [GitHub](https://github.com/borja8dev)

*Building professional APIs with Hexagonal Architecture and best practices*

---

## 🔗 Quick Navigation

- [Current Status](#current-status) - What's included in current version
- [Project Scope](#project-scope--status) - Demo vs Production clarity
- [Tech Stack](#tech-stack) - Technologies used
- [Architecture](#architecture) - System design explanation
- [Quick Start](#quick-start) - How to run locally
- [Project Phases](#project-phases) - Development roadmap
- [Development Guidelines](#development-guidelines) - Code standards
- [Testing](#testing) - Testing approach and status

---

**Last Updated:** May 27, 2026  
**Current Phase:** B - REST API Complete  
**Next Milestone:** v0.3.0 - Spring Security + JWT  
**Repository:** [GitHub](https://github.com/borja8dev/agloval-quotation-api)
