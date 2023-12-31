# Maven(23.08.31)

## 빌드 도구

- 소프트웨어 개발에 있어서 소스 코드를 실행 가능한 애플리케이션으로 만들어주는 도구
- 빌드 도구는 반복적이고 오류가 발생하기 쉬운 활동을 자동화하여 생산성 일관성 및 의존성을 개선
- 빌드 도구의 중요성
    - `자동화`
        - 빌드 도구는 루틴한 작업을 자동화하여 수동 참여와 오류를 제거합니다. 이는 복잡한 프로젝트에서 작업하거나 코드를 자주 수정하는 경우에 유용합니다.
    - `일관성`
        - 빌드 도구는 지정된 빌드 프로세스를 엄격히 준수하여 다양한 개발 환경 및 플랫폼에서 재현 가능한 결과를 보장합니다. 따라서 잘못된 구성으로 인한 소프트웨어 배포 문제가 줄어듭니다.
    - `의존성 관리`
        - 외부 라이브러리와 프레임워크의 요구 사항을 처리함으로써 프로젝트에 타사 코드를 포함하기가 더욱 간단해집니다. 이들은 의존성을 자동으로 다운로드, 관리 및 변경하여 개발 프로세스를 간소화할 수 있습니다.
    - `작업 병렬 처리`
        - 많은 빌드 도구는 여러 작업을 동시에 실행하여 빌드 시간을 줄이고 생산성을 높일 수 있습니다. 이는 많은 부분으로 구성된 대형 프로젝트에 유용합니다.
    - `점진적 빌드`
        - 빌드 도구를 사용하면 점진적인 빌드가 가능해져 시간과 자원을 절약할 수 있습니다. 전체 애플리케이션을 다시 컴파일하는 대신 소스 코드의 변경 사항을 감지하고 영향을 받은 부분만 다시 컴파일합니다.
    - `CI/CD 통합`
        - 빌드 도구는 CI/CD 파이프라인과 투명하게 통합되어 개발자에게 완전한 빌드 및 배포 프로세스 자동화를 지원합니다. 이 동기화로 인해 지속적으로 통합하고 업데이트를 본문 설정에 계속 배포함으로써 신뢰성 있는 소프트웨어 전달이 가능해집니다.
    - `코드 컴파일`
        - 빌드 도구는 소스 코드를 이진 실행 파일이나 중간 표현으로 변환하는 과정을 말합니다. 이 과정은 개발 단계에서 구문 오류 및 기타 문제를 감지하는 데 도움이 됩니다.
    - `테스트 및 품질 보증`
        - 빌드 프로세스의 일부로 자동화된 테스트를 실행하여 새로운 기능과 코드 수정이 문제를 일으키지 않도록하고 코드가 수립된 품질 기준을 충족하는지 확인할 수 있습니다.
    - `호환성 및 확장성`
        - 이러한 도구는 다양한 언어, 프레임워크 및 운영 체제와 호환됩니다. 플러그인이나 직접 작성한 스크립트와 같은 새로운 기능을 추가하여 프로젝트 요구 사항에 맞게 수정할 수 있습니다.
    - `배포 및 패키징`
        - 빌드 도구는 소프트웨어를 최종 사용자나 다른 그룹에 전달하기 쉽게 만들어주며 배포 가능한 형식으로 패키징하는 과정을 도와줍니다.

## xml

- 마크다운 언어
- 태그는 아무거나 사용 가능
- 규칙등을 정의해놓은 문서
- ant나 maven을 위한 빌드 내용, 규칙등을 기술

## maven 이란

- Java의 대표적인 빌드 툴(Build Tool) 중 하나
- apache 재단에서 개발하는 오픈소스 https://maven.apache.org/
- apache ant의 후속으로 개발
- XML을 사용하여 빌드 파일을 기술
- 중앙 저장소를 이용한 편리한 의존 관계 라이브러리 관리
- 중앙 저장소: 메이븐에서 이용 가능한 라이브러리를 모아서 관리하는 웹 서비스

### maven의 장점

- 편리한 의존 관계 라이브러리 관리
- 일관된 디렉토리 구조와 빌드 프로세스 관리
- 다양한 플러그인

### maven의 단점

- maven에서 기본적으로 지원하지 않는 빌드 과정 추가가 복잡해짐

### maven 설치

- `brew install mvn`

### maven 프로젝트 생성

- `mvn -B archetype:generate -DgroupId=com.nhnacademy.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4`
    - `B` : batch Mode, Interactive한 입력이나 진행상황 표시없이 명령어를 실행
    - `archetype:generate` : 미리 정의된 템플릿을 기반으로 새로운 프로젝트를 생성
    - `DgroupId=com.nhnacademy.app` : Project의 그룹 ID
        - (ex) nhnacademy.com -> com.nhnacademy
    - `DartifactId=my-app` : 프로젝트의 artifact ID
        - artifact 사전적 의미 : `인공물`, `공예품`, `인공 유물`, `인공 산물`
        - Project를 식별할 수 있는 ID
    - `DarchetypeArtifactId=maven-archetype-quickstart`
        - `maven-archetype-quickstart` 이라는 archetype을 사용하여 프로젝트를 생성
        - 빠르게 시작할 수 있는 간단한 Java 프로젝트 템플릿을 제공합니다.
    - `DarchetypeVersion=1.4` archetype version
- 일관된 프로젝트 템플릿을 제공하기에 많은 사람들이 사용

## Life Cycle

### `clean`

- 프로젝트를 정리하고 이전 빌드에서 생성된 모든 파일을 제거합니다.
    - `project_root/target` 삭제합니다.
    - 기존의 **Pacakge**는 `clean` 하고 새로 만들것 → 덮어쓰기가 원칙이기에 삭제한 코드가 있을 수 있음.

### `vaildate`

- 프로젝트의 상태를 점검하고, 필드에 필요한 정보의 존재유무를 체크합니다.
    - 프로젝트의 POM 및 구성을 검증합니다.

### `compile`

- 프로젝트의 소스 코드를 컴파일 합니다.

### `test`

- 프로젝트에 대한 테스트를 실행합니다.

### `package`

- 프로젝트에 대한 `JAR(Java ARchive)` 또는 `WAR(Web Archive)` 파일을 생성하여 배포 가능한 형식으로 변환합니다.
- `package` 실행시 위의 `vaildate` ~`test`까지 동시에 실행한다.

## Dependency Scope

### `compile`

- default scope
- 모든 상황에 포함

### `provided`

- compile과 유사하게 모든상화에 포함되어 수행되지만 package단계에서는 포함하지 않음
- 즉 배포환경(실행환경)에서 해당 Library를 제공

### `runtime`

- compile시 불필요하지만 runtime시 필요할 경우.
- 즉 runtime 및 test할 때 classpath에 추가 되지만, compile시 추가 되지 않음

### `test`

- test에서만 사용
- junit

## Dependency Management

### Junit5

- `JUnit 5` = `JUnit Platform` + `JUnit Jupiter` + `JUnit Vintage`

### JUnit Platform

- JUnit 테스트를 실행하고 테스트 엔진과 통신하기 위한 인프라를 제공합니다. 테스트 실행, 확장, 리포팅 등의 기능을 담당합니다.

### JUnit Jupiter

- JUnit 5에서 도입된 새로운 테스트 프레임워크입니다. Jupiter는 JUnit 5의 주요 기능으로서 다양한 테스트 유형을 지원하고, 확장 가능한 테스트 API를 제공합니다. `@Test`, `@Display` ,`@BeforeEach`, `@AfterEach`

### JUnit Vintage

- `JUnit 4` 및 이전 버전과 호환성을 제공하기 위한 모듈입니다. 이전에 작성된 JUnit 4 스타일의 테스트 코드를 JUnit 5 플랫폼에서 실행할 수 있도록 도와줍니다.

### BOM (Bill Of Materials)

- 프로젝트나 라이브러리에서 사용되는 dependency 버전을 관리하는 데 도움을 주는 메커니즘입니다. Maven BOM은 Maven 프로젝트에서 여러 모듈 또는 하위 프로젝트 간에 공유되는 의존성의 버전을 일관되게 유지하기 위해 사용되는 특별한 종류의 POM 파일입니다.
 ``` <dependencies>
    <dependency>
        <groupId>org.junit.platform</groupId>
        <artifactId>junit-platform-launcher</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.vintage</groupId>
        <artifactId>junit-vintage-engine</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.junit</groupId>
            <artifactId>junit-bom</artifactId>
            <version>5.10.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
