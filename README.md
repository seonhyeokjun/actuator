# Actuator 학습 정리

## 1. 학습 목적
나는 회사에서 PG솔루션을 개발하고 있다. 결제 및 정산에 대한 부분 때문에 오류에 굉장히 빠르게 대응해야 하는 필요를 느껴 모니터링 시스템을 구축하기 위해 학습을 시작하였다. <br>
이번 학습의 목적은 **Spring Boot Actuator**를 활용하여 애플리케이션의 상태를 모니터링하고 관리하는 방법을 익히는 것이었다. <br>
운영 환경에서 애플리케이션의 **헬스 체크, 메트릭, 로거 상태**를 실시간으로 확인하고, 문제 발생 시 빠르게 진단할 수 있는 기능을 학습했다. 이를 통해 프로덕션 환경에서 애플리케이션의 안정성을 강화할 수 있는 방법을 배웠다.

---

## 2. Actuator 기능 개요
Actuator는 **애플리케이션의 상태를 모니터링하고 관리할 수 있는 도구**로, 애플리케이션 내부 정보와 성능을 실시간으로 확인할 수 있는 다양한 엔드포인트를 제공한다.

- **헬스 체크**: 애플리케이션이 정상 동작 중인지 확인할 수 있는 기능이다.
- **메트릭**: JVM 메모리, CPU 사용률, 애플리케이션의 트래픽 등 다양한 성능 지표를 수집하고 노출할 수 있다.
- **로깅 및 트레이싱**: 애플리케이션에서 발생하는 로그를 실시간으로 확인하거나, HTTP 요청과 응답을 기록할 수 있다.
- **관리 엔드포인트**: 애플리케이션의 상태를 쉽게 모니터링하고 관리할 수 있는 다양한 엔드포인트가 제공된다.

---

## 3. 프로젝트 설정
Actuator를 프로젝트에 적용하기 위해서 **`spring-boot-starter-actuator`** 의존성을 추가해야 했다. <br> 
이 설정은 Maven 또는 Gradle에 추가하여 간단하게 적용할 수 있었으며, 기본 설정 파일에서 추가 엔드포인트를 활성화하거나 비활성화할 수 있다.

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

- **Actuator 기본 설정**은 `application.properties` 또는 `application.yml`에서 설정할 수 있다.

---

## 4. 엔드포인트 학습
Actuator는 여러 엔드포인트를 제공하며, 그중 주요 엔드포인트는 다음과 같다:

- **전체** (`/actuator`): 애플리케이션의 전체 정보를 보여준다.
   ```json
  {
  "_links": {
      "self": {
      "href": "http://localhost:8080/actuator",
      "templated": false
      },
      "beans": {
      "href": "http://localhost:8080/actuator/beans",
      "templated": false
      },
      "caches-cache": {
      "href": "http://localhost:8080/actuator/caches/{cache}",
      "templated": true
      },
      "caches": {
      "href": "http://localhost:8080/actuator/caches",
      "templated": false
      },
      "health-path": {
      "href": "http://localhost:8080/actuator/health/{*path}",
      "templated": true
      },
      "health": {
      "href": "http://localhost:8080/actuator/health",
      "templated": false
      },
      "info": {
      "href": "http://localhost:8080/actuator/info",
      "templated": false
      },
      "conditions": {
      "href": "http://localhost:8080/actuator/conditions",
      "templated": false
      },
      "shutdown": {
      "href": "http://localhost:8080/actuator/shutdown",
      "templated": false
      },
      "configprops-prefix": {
      "href": "http://localhost:8080/actuator/configprops/{prefix}",
      "templated": true
      },
      "configprops": {
      "href": "http://localhost:8080/actuator/configprops",
      "templated": false
      },
      "env": {
      "href": "http://localhost:8080/actuator/env",
      "templated": false
      },
      "env-toMatch": {
      "href": "http://localhost:8080/actuator/env/{toMatch}",
      "templated": true
      },
      "loggers": {
      "href": "http://localhost:8080/actuator/loggers",
      "templated": false
      },
      "loggers-name": {
      "href": "http://localhost:8080/actuator/loggers/{name}",
      "templated": true
      },
      "heapdump": {
      "href": "http://localhost:8080/actuator/heapdump",
      "templated": false
      },
      "threaddump": {
      "href": "http://localhost:8080/actuator/threaddump",
      "templated": false
      },
      "prometheus": {
      "href": "http://localhost:8080/actuator/prometheus",
      "templated": false
      },
      "metrics": {
      "href": "http://localhost:8080/actuator/metrics",
      "templated": false
      },
      "metrics-requiredMetricName": {
      "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
      "templated": true
      },
      "scheduledtasks": {
      "href": "http://localhost:8080/actuator/scheduledtasks",
      "templated": false
      },
      "httpexchanges": {
      "href": "http://localhost:8080/actuator/httpexchanges",
      "templated": false
      },
      "mappings": {
      "href": "http://localhost:8080/actuator/mappings",
      "templated": false
      }
    }
  }
   ```
- **헬스 체크** (`/actuator/health`): 애플리케이션의 가용 상태를 실시간으로 확인할 수 있었습니다. 애플리케이션이 정상적이면 `UP`, 비정상이면 `DOWN` 상태로 표시된다.
- **애플리케이션 정보** (`/actuator/info`): 애플리케이션의 이름, 버전과 같은 기본 정보를 확인할 수 있다.
- **메트릭** (`/actuator/metrics`): 메모리, CPU 사용량 등 애플리케이션의 다양한 성능 지표를 노출할 수 있다.
- **로거** (`/actuator/loggers`): 애플리케이션의 로그 레벨을 실시간으로 조정하고, 로그 정보를 확인할 수 있다.
- **HTTP 트레이스** (`/actuator/httptrace`): 최근에 발생한 HTTP 요청과 응답 기록을 확인할 수 있다.

특히 **헬스 체크**와 **메트릭**은 운영 환경에서 필수적인 엔드포인트로, 시스템 가용성과 성능을 모니터링하는 데 매우 유용했다.

---

## 5. 엔드포인트 설정
기본적으로 헬스 체크와 애플리케이션 정보 엔드포인트만 활성화되어 있었지만, 설정 파일에서 추가 엔드포인트를 활성화할 수 있다. `management.endpoints.web.exposure.include` 옵션을 통해 노출할 엔드포인트를 선택할 수 있다.

예를 들어, 모든 엔드포인트를 활성화하려면 다음과 같이 설정할 수 있다.
```properties
management.endpoints.web.exposure.include=*
```
또한, 특정 엔드포인트만 노출하려면 아래와 같이 설정했다.
```properties
management.endpoints.web.exposure.include=health,info
```

---

## 6. 보안 설정 학습
Actuator는 기본적으로 모든 엔드포인트가 보호되며, 인증이 필요했다. 이를 통해 중요한 정보가 외부에 노출되는 것을 방지할 수 있다. **`spring-boot-starter-security`** 의존성을 추가하여 엔드포인트에 대한 인증 및 접근 권한을 관리할 수 있었으며, 이를 통해 애플리케이션의 보안을 강화할 수 있다.

보안 설정 예시:
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

## 7. 실습 결과 및 결론
Actuator를 통해 **운영 환경에서 애플리케이션의 상태를 실시간으로 모니터링하고 관리**할 수 있다. 이를 통해 애플리케이션의 성능과 가용성을 개선할 수 있었으며, 문제 발생 시 신속하게 진단하여 해결할 수 있는 기반을 마련할 수 있다.

특히, 다양한 엔드포인트를 활용하여 시스템의 성능과 안정성을 모니터링하고 관리할 수 있는 강력한 기능들을 습득할 수 있다.

---

## 8. 필요한 추가 학습
이제는 이런 엔드포인트를 이용해서 모니터링 할 수 있는 방법을 찾았으나 이를 편리하게 볼 수 있는 Prometheus와 Grafana를 학습할 필요가 있다.
