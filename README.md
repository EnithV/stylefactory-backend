# 💈 Style Factory – Backend

API REST para la gestión de una sala de belleza. Permite administrar usuarios, empleados, servicios, horarios y reservas, con autenticación basada en JWT y control de acceso por roles.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 4.0.6**
- **Spring Security** + **JWT** (io.jsonwebtoken)
- **Spring Data JPA** (Hibernate)
- **PostgreSQL**
- **Swagger / OpenAPI** (springdoc-openapi)
- **JUnit 5** + **Mockito** para pruebas unitarias
- **Maven**

## 📁 Estructura del proyecto

```
src/main/java/com/backend/styleFactory/
├── auth/                    # Autenticación (registro y login)
│   ├── AuthController.java
│   ├── LoginRequestDTO.java
│   └── RegisterRequestDTO.java
├── config/                  # Configuraciones de seguridad, CORS y Swagger
│   ├── ApplicationConfig.java
│   ├── CorsConfig.java
│   ├── SecurityConfig.java
│   └── SwaggerConfig.java
├── controller/              # Endpoints REST
│   ├── EmpleadoController.java
│   ├── HorarioController.java
│   ├── ReservaController.java
│   ├── ServicioController.java
│   └── UsuarioController.java
├── DTO/                     # Objetos de transferencia de datos
│   ├── EmpleadoRequestDTO.java
│   ├── EmpleadoResponseDTO.java
│   ├── HorarioRequestDTO.java
│   ├── HorarioResponseDTO.java
│   ├── ReservaRequestDTO.java
│   ├── ReservaResponseDTO.java
│   ├── ServicioRequestDTO.java
│   ├── ServicioResponseDTO.java
│   ├── UsuarioRequestDTO.java
│   └── UsuarioResponseDTO.java
├── exception/               # Manejo global de excepciones
│   └── GlobalExceptionHandler.java
├── model/                   # Entidades JPA
│   ├── Empleado.java
│   ├── Horario.java
│   ├── Reserva.java
│   ├── RolUsuario.java      # Enum: ADMIN, EMPLEADO, CLIENTE
│   ├── Servicio.java
│   └── Usuario.java
├── repository/              # Acceso a datos (Spring Data JPA)
│   ├── EmpleadoRepository.java
│   ├── HorarioRepository.java
│   ├── ReservaRepository.java
│   ├── ServicioRepository.java
│   └── UsuarioRepository.java
├── security/                # Lógica de JWT
│   ├── JwtFilter.java
│   └── JwtUtil.java
└── service/                 # Lógica de negocio
    ├── EmpleadoService.java
    ├── HorarioService.java
    ├── ReservaService.java
    ├── ServicioService.java
    └── UsuarioService.java

src/main/resources/
└── application.properties   # Configuración local (NO se sube al repositorio)

src/test/java/com/backend/styleFactory/service/
└── ReservaServiceTest.java  # Prueba unitaria del servicio de reservas
```

## ⚙️ Configuración inicial

1. **Clonar el repositorio** y abrir el proyecto en IntelliJ (o el IDE de tu preferencia).
2. **Crear una base de datos** en PostgreSQL llamada `styleFactory` (o el nombre que prefieras).
3. **Configurar `application.properties`** con tus datos locales (este archivo no se incluye en el repositorio). Ejemplo:

```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/styleFactory
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

4. **Ejecutar la aplicación** desde la clase `StyleFactoryApplication`.
5. **Swagger UI** disponible en: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) (también redirige desde `/swagger-ui.html`)

## 🔐 Seguridad (JWT + roles)

### Roles disponibles

| Rol        | Permisos principales                                              |
|------------|-------------------------------------------------------------------|
| `ADMIN`    | Acceso total a todos los endpoints y panel de control             |
| `EMPLEADO` | Acceso al módulo de empleados y horarios                          |
| `CLIENTE`  | Registro, login y realización de reservas                         |

### Flujo de autenticación

**Registro** — `POST /auth/register`

- Recibe los datos del usuario, encripta la contraseña con BCrypt y asigna el rol.
- Si no se envía rol, se asigna `CLIENTE` por defecto.

**Login** — `POST /auth/login`

- Valida credenciales contra la base de datos.
- Si son correctas, devuelve un token JWT con validez de **24 horas**.

**Peticiones protegidas**

- El cliente debe enviar el token en el encabezado: `Authorization: Bearer <token>`
- El filtro `JwtFilter` extrae y valida el token, carga el usuario y establece los roles en el contexto de seguridad.
- Las reglas de autorización en `SecurityConfig` restringen el acceso según los roles.

### Endpoints públicos

- `POST /auth/register`
- `POST /auth/login`
- Swagger UI (`/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs`, `/v3/api-docs/**`, `/webjars/**`)

Todos los demás endpoints requieren autenticación. Algunos exigen un rol específico:

- `/admin/**` → `ADMIN` (reservado en `SecurityConfig`; aún sin controladores implementados)
- `/empleados/**` → `ADMIN` o `EMPLEADO`

### Diagrama del flujo JWT

```mermaid
sequenceDiagram
    participant C as Cliente
    participant API as API REST
    participant Auth as AuthController
    participant DB as PostgreSQL
    participant Filter as JwtFilter
    participant Sec as SecurityConfig

    Note over C,Sec: Registro / Login (público)
    C->>API: POST /auth/register o /auth/login
    API->>Auth: Procesar credenciales
    Auth->>DB: Guardar o validar usuario
    Auth-->>C: JWT (24 h) en login exitoso

    Note over C,Sec: Petición protegida
    C->>API: Request + Authorization: Bearer token
    API->>Filter: Interceptar petición
    Filter->>Filter: Validar token (JwtUtil)
    Filter->>DB: Cargar UserDetails por username
    Filter->>Sec: Establecer roles en SecurityContext
    Sec->>Sec: Evaluar reglas (/empleados, /admin, etc.)
    alt Token válido y rol permitido
        API-->>C: 200 + respuesta
    else Token inválido o sin permiso
        API-->>C: 401 / 403
    end
```

## 🌐 Despliegue (producción)

| Componente | URL |
|------------|-----|
| **Frontend** (GitHub Pages, repo `EnithV/stylefactory`) | https://enithv.github.io/stylefactory/ |
| **Backend** (Render) | https://stylefactoryapi.onrender.com |
| **Swagger** | https://stylefactoryapi.onrender.com/swagger-ui/index.html |

El frontend llama al API con `API_BASE` en `stylefactory-frontend/assets/js/config.js`.  
CORS en `CorsConfig.java` permite el origen `https://enithv.github.io` (GitHub Pages).

## 📡 API REST

Base URL local: `http://localhost:8081` (o el valor de `server.port` en `application.properties`)  
Base URL producción: `https://stylefactoryapi.onrender.com`

Leyenda de acceso:

| Acceso        | Descripción                                              |
|---------------|----------------------------------------------------------|
| **Público**   | No requiere token                                        |
| **JWT**       | Cualquier usuario autenticado con token válido           |
| **ADMIN**     | Rol `ADMIN`                                              |
| **ADMIN/EMP** | Rol `ADMIN` o `EMPLEADO` (rutas bajo `/empleados/**`)    |

### Auth (`AuthController`)

| Método | Ruta              | Acceso   | Descripción                    |
|--------|-------------------|----------|--------------------------------|
| POST   | `/auth/register`  | Público  | Registro de usuario            |
| POST   | `/auth/login`     | Público  | Login; devuelve JWT (24 h)     |

### Usuarios (`UsuarioController` — `/usuarios`)

| Método | Ruta            | Acceso | Descripción              |
|--------|-----------------|--------|--------------------------|
| POST   | `/usuarios`     | JWT    | Crear usuario            |
| GET    | `/usuarios`     | JWT    | Listar usuarios          |
| GET    | `/usuarios/{id}`| JWT    | Obtener por ID           |
| PUT    | `/usuarios/{id}`| JWT    | Actualizar usuario       |
| DELETE | `/usuarios/{id}`| JWT    | Desactivar (borrado lógico) |

### Empleados (`EmpleadoController` — `/empleados`)

| Método | Ruta               | Acceso    | Descripción              |
|--------|--------------------|-----------|--------------------------|
| GET    | `/empleados`       | ADMIN/EMP | Listar empleados         |
| GET    | `/empleados/{id}`  | ADMIN/EMP | Obtener por ID           |
| POST   | `/empleados`       | ADMIN/EMP | Crear empleado           |
| PUT    | `/empleados/{id}`  | ADMIN/EMP | Actualizar empleado      |
| DELETE | `/empleados/{id}`  | ADMIN/EMP | Desactivar (borrado lógico) |

### Servicios (`ServicioController` — `/servicios`)

| Método | Ruta               | Acceso | Descripción        |
|--------|--------------------|--------|--------------------|
| GET    | `/servicios`       | JWT    | Listar servicios   |
| GET    | `/servicios/{id}`  | JWT    | Obtener por ID     |
| POST   | `/servicios`       | JWT    | Crear servicio     |
| PUT    | `/servicios/{id}`  | JWT    | Actualizar servicio|
| DELETE | `/servicios/{id}`  | JWT    | Eliminar servicio  |

### Horarios (`HorarioController` — `/horarios`)

| Método | Ruta        | Acceso | Descripción           |
|--------|-------------|--------|-----------------------|
| GET    | `/horarios` | JWT    | Listar horarios       |
| POST   | `/horarios` | JWT    | Crear o guardar horario |

### Reservas (`ReservaController` — `/reservas`)

| Método | Ruta              | Acceso | Descripción        |
|--------|-------------------|--------|--------------------|
| GET    | `/reservas`       | JWT    | Listar reservas    |
| GET    | `/reservas/{id}`  | JWT    | Obtener por ID     |
| POST   | `/reservas`       | JWT    | Crear reserva      |
| PUT    | `/reservas/{id}`  | JWT    | Actualizar reserva |
| DELETE | `/reservas/{id}`  | JWT    | Eliminar reserva   |

### Documentación (Swagger)

| Método | Ruta                    | Acceso  | Descripción              |
|--------|-------------------------|---------|--------------------------|
| GET    | `/swagger-ui/**`        | Público | Interfaz Swagger UI      |
| GET    | `/v3/api-docs/**`       | Público | Especificación OpenAPI   |

### Ejemplo de petición autenticada

```http
GET /reservas HTTP/1.1
Host: localhost:8080
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 📦 Dependencias principales

- `spring-boot-starter-webmvc`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-validation`
- `jjwt-api`, `jjwt-impl`, `jjwt-jackson` (0.12.6)
- `springdoc-openapi-starter-webmvc-ui` (3.0.0 — requerido con Spring Boot 4)
- `postgresql`
- `spring-boot-starter-test` (incluye JUnit y Mockito)

## 🧪 Pruebas unitarias

El proyecto incluye pruebas para el servicio de reservas (`ReservaServiceTest`) que verifican:

- Creación exitosa de una reserva cuando todas las entidades relacionadas existen.
- Lanzamiento de una excepción cuando el usuario asociado no se encuentra.

Se ejecutan con **JUnit 5** y **Mockito**. Para correrlas: clic derecho sobre la clase → **Run 'ReservaServiceTest'**.

También puedes ejecutarlas con Maven:

```bash
mvn test
```

## ♻️ Borrado lógico

Las entidades `Usuario` y `Empleado` no se eliminan físicamente; se **desactivan** (`estado = false`). Esto preserva la integridad referencial con las reservas históricas.

## 📌 Notas importantes

- `application.properties` **nunca** se incluye en los commits. Cada desarrollador mantiene su propia configuración local.
- La base de datos se recrea en cada inicio (`ddl-auto=create`); esto es solo para entorno de desarrollo.
- La documentación Swagger se genera automáticamente desde `SwaggerConfig.java` (sin necesidad de anotaciones en los controladores).

## ✅ Estado del proyecto

- CRUD completo para Usuario, Empleado, Servicio, Reserva y Horario.
- Autenticación y autorización con JWT y roles.
- Manejo global de excepciones.
- Configuración centralizada de Swagger.
- Pruebas unitarias funcionales.
- Código integrado en la rama **Dev**, listo para conectar con el frontend.
