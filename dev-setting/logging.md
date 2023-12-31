# Java Logging(23.09.01)

### Logging

- 시스템 동작 시, 시스템의 상태와 작동 정보를 시간 경과에 따라 기록하는 것
- java에서의 Logging 방법은 `System.out.println`, `log4j`, `logback` 등이 있음

## System.out.println()

- System.out은 표준 출력 작업을 수행하는 메서드를 제공함
- Logging에도 활용할 수 있다고 생각할 수 있으나, **System.out으로 로그를 남기면 안됨**

### 로깅 시 해당 메서드를 활용할 수 없는 이유는?

- **에러 발생 시 추적할 수 있는 최소한의 정보가 남지 않음**
    - 날짜 및 시간, 수준(error, info, debug 등), 발생 위치 등의 정보를 기록하기가 어려움
- **로그 출력 레벨을 사용할 수 없음**
    - 로깅 라이브러리를 이용하면 TRACE, DEVUG, INFO, WARN, ERROR 등의 다양한 로그 레벨을 사용할 수 있지만, System.out은 해당 기능을 사용할 수 없음
- **성능 저하 발생**
    - System.out.println()에서 사용하는 newLine() 메서드에는 `synchronized` 키워드가 붙어있으므로 해당 메서드는 critical section이 됨. 멀티 쓰레드 환경에서 어떠한 쓰레드가 해당 메서드를 실행하는 도중에는 다른 쓰레드에서 해당 메서드 실행이 불가함. 그러므로 멀티 쓰레드 환경에서 성능 저하가 발생하게 됨.

## Java Standard Logging

### java.util.logging.Logger

- java에 내장되어 있는 로깅용 유틸 클래스
- java.util.logging 패키지에 속해있음
- 로그 레벨 지원
    - SEVERE > WARNING > INFO > CONFIG > FINE > FINER > FINEST
    - 로깅을 끄는 OFF, 모든 로깅을 찍는 ALL도 있음
- 장점
    - 외부 라이브러리를 사용할 필요가 없음
- 단점
    - 다른 라이브러리와 비교 했을 때, 속도가 느림
    - 타 라이브러리에 비해 기능이 부족함
    - 커스텀 레벨을 만들 때 메모리 누수가 일어남

## Logback

- slf4j(Simple Logging Facade for Java) 구현체
- 다양한 Logging framework 중 하나로, log4j보다 향상된 기능을 갖고 있으며 가장 널리 사용되고 있음
- 로그 레벨
    - error > warn > info > debug > trace
    - error : 요청 처리 중 문제가 발생함을 뜻함
    - warn : 프로그램 실행은 문제가 없지만, 향후 문제를 일으킬수도 있음을 뜻함
    - info : 정보성 메시지
    - debug : 개발 시 디버그 용도로 사용됨
    - trace : 좀 더 상세한 이벤트를 나타낼 때 사용됨
- Logback 의존성 주입
    
    ```java
    <dependencies>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.7</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.4.8</version>
        </dependency>
    </dependencies>
    ```
    

- resoures 디렉토리 생성 수 logback.xml 파일 생성  → 로그 설정
- 로그 명령어 설정

```java
log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);
```
