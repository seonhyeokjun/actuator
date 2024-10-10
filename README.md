
# Section 9. Actuator

---

## 1. 프로덕션 준비 기능이란?
- **Spring Boot Actuator**는 애플리케이션을 모니터링하고 관리하는 데 유용한 기능들을 제공하는 모듈입니다.
- 애플리케이션의 **상태, 메트릭, 로거, 헬스 체크** 등의 정보를 엔드포인트로 노출하여 운영 환경에서 **모니터링과 문제 진단**을 쉽게 할 수 있습니다.
- 주요 기능:
  - **헬스 체크**: 애플리케이션의 가용성을 실시간으로 확인할 수 있습니다.
  - **메트릭**: 애플리케이션의 성능, 자원 사용률 등을 추적합니다.
  - **HTTP 요청 추적**: HTTP 요청 및 응답에 대한 정보를 수집하여 분석할 수 있습니다.
  - **로깅 및 트레이싱**: 애플리케이션에서 발생하는 로그 및 트랜잭션을 모니터링할 수 있습니다.

---

## 2. 프로젝트 설정
- Spring Boot 프로젝트에 Actuator를 추가하기 위해서는 `spring-boot-starter-actuator` 의존성을 추가해야 합니다.

### Maven
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Gradle
```gradle
implementation 'org.springframework.boot:spring-boot-starter-actuator'
```

- **Actuator 기본 설정**은 `application.properties` 또는 `application.yml`에서 설정할 수 있습니다.

---

## 3. 액츄에이터 시작
- Actuator는 기본적으로 몇 가지 엔드포인트를 제공합니다. **헬스 체크**와 **애플리케이션 정보** 등 기본적인 기능이 포함됩니다.
- 서버가 실행되면 기본적으로 `http://localhost:8080/actuator` 경로에서 Actuator 엔드포인트에 접근할 수 있습니다.

---

## 4. 엔드포인트 설정
- Actuator 엔드포인트는 기본적으로 **헬스 체크**와 **info** 엔드포인트만 활성화되어 있습니다.
- 엔드포인트를 추가로 활성화하려면 `application.properties`에서 설정을 변경해야 합니다.

### 모든 엔드포인트 활성화
```properties
management.endpoints.web.exposure.include=*
```

### 특정 엔드포인트만 활성화
```properties
management.endpoints.web.exposure.include=health,info
```

---

## 5. 다양한 엔드포인트
- **헬스 체크** (`/actuator/health`): 애플리케이션의 상태를 보여줍니다.
- **애플리케이션 정보** (`/actuator/info`): 애플리케이션 관련 정보(버전, 이름 등)를 제공합니다.
- **메트릭** (`/actuator/metrics`): JVM, CPU, 메모리 사용량 등의 다양한 메트릭을 제공합니다.
- **HTTP 트레이스** (`/actuator/httptrace`): 최근의 HTTP 요청과 응답을 기록합니다.
- **로그** (`/actuator/loggers`): 로깅 레벨을 실시간으로 변경할 수 있습니다.

---

## 6. 헬스 정보
- `/actuator/health` 엔드포인트는 애플리케이션의 헬스 상태를 나타냅니다.
- **상태 확인**:
  - `UP`: 애플리케이션이 정상적으로 작동 중임을 나타냅니다.
  - `DOWN`: 애플리케이션에 문제가 발생했음을 나타냅니다.
- 추가적인 **헬스 인디케이터**를 통해 데이터베이스 연결 상태, 외부 시스템 상태 등을 확인할 수 있습니다.

---

## 7. 애플리케이션 정보
- `/actuator/info` 엔드포인트는 애플리케이션에 대한 기본 정보를 제공합니다.
- **애플리케이션 정보 커스터마이징**:
  - `application.properties`에 정보를 추가하여 애플리케이션 이름, 버전 등을 출력할 수 있습니다.

### 예시:
```properties
info.app.name=My Application
info.app.version=1.0.0
```

---

## 8. 로거
- `/actuator/loggers` 엔드포인트는 애플리케이션의 로거 상태를 확인하고, 로거의 레벨을 실시간으로 변경할 수 있습니다.
- **로거 레벨 변경**:
```json
POST /actuator/loggers/{loggerName}
{
  "configuredLevel": "DEBUG"
}
```

---

## 9. HTTP 요청 응답 기록
- `/actuator/httptrace` 엔드포인트를 통해 최근에 발생한 HTTP 요청과 응답의 기록을 확인할 수 있습니다.
- 이 엔드포인트를 사용하려면 `HttpTraceRepository` 빈을 정의해야 합니다.

### 예시:
```java
@Bean
public HttpTraceRepository httpTraceRepository() {
    return new InMemoryHttpTraceRepository();
}
```

---

## 10. 액츄에이터와 보안
- 기본적으로 Actuator 엔드포인트는 모두 보호되어 있습니다. 보안을 설정하지 않으면 외부에서 접근할 수 없습니다.
- `spring-boot-starter-security`를 추가하여 엔드포인트에 대한 인증과 권한을 관리할 수 있습니다.

### 예시: 특정 엔드포인트에만 접근 허용
```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.health.probes.enabled=true
```

### 보안 설정:
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .requestMatchers(EndpointRequest.to("health", "info")).permitAll()
            .anyRequest().authenticated();
    }
}
```

--- 

**Actuator**는 모니터링, 관리, 그리고 문제 해결을 위한 중요한 기능을 제공하므로, 운영 환경에서 애플리케이션의 상태를 추적하고 성능을 관리하는 데 매우 유용합니다.
