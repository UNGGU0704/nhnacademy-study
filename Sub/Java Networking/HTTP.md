# HTTP(23. 10 .27)

### WWW(World Wide Web)

- 1989년 3월 팀 버너스리 외 n 명의 과학자들의 제안으로 시작
- Web 브라우저가 Web Server의 HTML로 기술된 리소스를
- URL을 통해 요청하여
- HTTP를 사용하여 받아서 표현하는 것

### HTML (HyperText Markup Language)

- 자원들 사이를 쉽게 항해 할 수 있는 언어
    - HTML 3.2, 4.0, 4.1, 5.0, 5.1, 5.2 ..

### URL (Uniform Resource Locator)

- 통일된 웹 자원(Resource)의 위치 지정 방법

### HTTP (HyperText Transfer Protocol)

- **HyperText** (**HyperMedia**)를 클라이언트와 서버 사이에 주고 받을 수 있게 정의한 프로토콜
- TCP/IP 프로토콜 위에서 동작하는 **Text Based 프로토콜**

## 인증, 쿠키, 세션

### 쿠키

- 서버가 클라이언트에 붙인 일종의 스티커
- 클라이언트는 서버에게 보내는 요청 해더에 쿠키를 표시해서 전송
- 웹 브라우저와 웹 서버 간의 상태 정보를 유지하기 위해 사용되는 데이터 (사용 세션 관리)
- 민감한 개인정보를 가지고 있기에 보안 취약점으로 사용될 위험 있음
- **Session Cookie(세션 쿠키)**
    - 사용자가 브라우저를 사용하는 동안만 유효함.
    - 브라우저는 사용자가 브라우저를 사용하는 동안 Cookie 정보를 서버로 전달.
- **Persistent Cookie(지속 쿠키)**
    - 사용자가 브라우저를 종료하더라도 유지되는 쿠키

## HTTPS

- 보안이 강화된 HTTP
- 일반적인 HTTP 계층해서 SSL/TLS 가 추가된 형태
    - 대칭, 비대칭키 암호화 알고리즘
    - SSL 인증서
    - 키 교환 알고리즘…
