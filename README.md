# Countries & Weather Information Application

A Spring Boot backend system that lets authenticated users browse a list of countries, view detailed information about a country, and check the current weather in that country's capital city. The system is split into two independent microservices: an **AuthServer** that owns user accounts, authentication and API-token management, and a **Server** (resource server) that aggregates country and weather data from third-party APIs and only serves it to callers the AuthServer has vouched for.

It was built as a practical exercise in service-to-service authentication, external API integration, and response caching in a multi-service Spring Boot architecture.

## Features

- **User registration & login** with BCrypt-hashed passwords (`AuthServer`)
- **Cookie-based session authentication** — login issues an `httpOnly` session cookie that the resource server validates on every request by calling back into the AuthServer
- **Admin capabilities** — an auto-provisioned `admin` account can list all users and activate/deactivate accounts (`AdminController`)
- **Personal API tokens** — logged-in users can create, list and revoke named API tokens for programmatic access (`TokenController`)
- **Country listing** — fetches the full list of countries from a public countries API
- **Country details** — capital, ISO2 code, population, population growth and currency for a given country
- **Capital-city weather lookup** — resolves a country's capital and returns live wind speed/degrees, temperature and humidity for it
- **Response caching** — country list, country info and weather info are cached (Caffeine) to reduce load on upstream APIs and speed up repeat requests
- **Health endpoints** — both services expose Spring Boot Actuator's `/actuator/health` with full details

## Tech Stack

- **Java 22**
- **Spring Boot 3** (Web, Security, Data JPA, Cache, Actuator)
- **Maven** (with Maven Wrapper)
- **H2** in-memory database (AuthServer's user/token store)
- **Caffeine** for in-memory response caching
- **Lombok** for entity/DTO boilerplate
- **org.json** for lightweight JSON handling
- **RestTemplate** for outbound HTTP calls to third-party APIs and between the two services

### External APIs used

- [API Ninjas](https://api.api-ninjas.com/) — `Country` and `Weather` endpoints (require an `X-Api-Key`)
- [Countries Now](https://countriesnow.space/) — public country list endpoint (no key required)

## Project Structure

```
countries-weather-information-application/
├── AuthServer/                # Authentication & user/token management service
│   └── src/main/java/com/sharif/edu/hw1/
│       ├── Configuration/     # Spring Security config, password encoder bean
│       ├── Controller/        # AuthController, UserController, AdminController, TokenController
│       ├── DataBase/          # JPA entities & repositories (User, Token, TokenCookie)
│       ├── Model/             # DTOs and Error enum
│       └── Service/           # UserService, TokenService, CheckTokenService
└── Server/                    # Resource server: countries & weather aggregation
    └── src/main/java/com/sut/web/server/
        ├── Configuration/     # Spring Security config
        ├── Controller/        # CountryController (countries, country info, weather)
        └── Model/             # Request/response DTOs
```

Each service is a self-contained Maven project with its own `pom.xml`, `application.properties` and Maven wrapper, and is intended to be run as a separate process.

## Prerequisites

- JDK 22
- An API key from [API Ninjas](https://api.api-ninjas.com/) (used for the country-info and weather endpoints)
- No local Maven installation is required — both services ship the Maven Wrapper (`mvnw` / `mvnw.cmd`)

## Configuration

Each service reads its settings from `src/main/resources/application.properties`.

**AuthServer** (`AuthServer/src/main/resources/application.properties`)

| Property | Description |
|---|---|
| `server.port` | Port the service listens on (default `6010`) |
| `x.api.key` | Shared key value (kept in sync with the Server's `x.api.key`) |
| `spring.datasource.*` | H2 in-memory database connection |
| `spring.cache.*` | Caffeine cache configuration |

**Server** (`Server/src/main/resources/application.properties`)

| Property | Description |
|---|---|
| `server.port` | Port the service listens on (default `6011`) |
| `x.api.key` | Your API Ninjas API key, sent as the `X-Api-Key` header on outbound requests |
| `weatherInfo.url` | API Ninjas weather endpoint |
| `countryInfo.url` | API Ninjas country endpoint |
| `countryList.url` | Countries Now country-list endpoint |
| `authServer.url` | URL the resource server calls to validate a caller's session cookie against the AuthServer |
| `spring.cache.*` | Caffeine cache configuration |

Replace the placeholder `x.api.key` value in both files with your own API Ninjas key before running the project against real data.

## Installation & Running

Each service is started independently, and the AuthServer should generally be running before you exercise endpoints on the Server, since every country/weather request is authorized against it.

**1. Start the AuthServer**

```bash
cd AuthServer
./mvnw spring-boot:run
```

The AuthServer starts on `http://localhost:6010` and exposes an H2 console at `/h2-console`.

**2. Start the Server**

```bash
cd Server
./mvnw spring-boot:run
```

The Server starts on `http://localhost:6011`.

On Windows, use `mvnw.cmd` in place of `./mvnw`.

To produce a runnable jar for either service instead:

```bash
./mvnw clean package
java -jar target/*.jar
```

## Usage

1. **Register a user**
   `POST http://localhost:6010/users/register` with a JSON body of `{"username": "...", "password": "..."}`.
2. **Activate the user (admin only)**
   Log in as admin via `POST http://localhost:6010/admin/login` (an `admin`/`admin` account is auto-created on first admin login), then
   `PUT http://localhost:6010/admin/users?username=...&active=true`.
3. **Log in**
   `POST http://localhost:6010/users/login` with the same credentials. A successful login returns a `Set-Cookie: token=...` session cookie.
4. **Call the resource server**, sending the session cookie obtained above:
   - `GET http://localhost:6011/countries` — list of all countries
   - `GET http://localhost:6011/countries/{name}` — details for one country
   - `GET http://localhost:6011/countries/{name}/weather` — current weather in that country's capital
5. **Manage personal API tokens** (while logged in):
   - `POST /user/api-tokens` — create a named token
   - `GET /user/api-tokens` — list your tokens
   - `DELETE /user/api-tokens/{tokenName}` — revoke a token

## Implementation Notes

- Authentication is cookie-based rather than stateless JWT: the resource server has no knowledge of user credentials and instead makes a server-to-server call (`POST /auth`) to the AuthServer on every protected request, forwarding the caller's session cookie for validation.
- Passwords are never stored in plain text — `UserService` hashes them with Spring Security's `BCryptPasswordEncoder` before persisting.
- The three external lookups (country list, country info, weather info) are each cached independently via Spring's `@Cacheable` with a Caffeine backend (up to 100 entries, 10 minute expiry), so repeated lookups for the same country don't repeatedly hit third-party APIs.
- CORS is explicitly configured on the AuthServer's user-facing endpoints for a frontend running on `http://localhost:5173` (e.g. a local Vite dev server), though no frontend is included in this repository.
- Test coverage currently consists of Spring Boot context-load smoke tests for both services (`./mvnw test` in either directory).
