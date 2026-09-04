<p align="center">
  <img src="./src/main/resources/static/images/steel-logo2.png" width="260" alt="Smart Steel System logo" />
</p>

<h1 align="center">Smart Steel System</h1>

<p align="center">
  AI 수요예측 결과를 생산계획·재고 데이터와 연결해<br/>
  <b>품목별 생산·재고 리스크를 모니터링하는 의사결정 지원 시스템</b>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-007396?style=flat-square" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.15-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=python&logoColor=white" />
</p>

---

## 1. 프로젝트 소개

Smart Steel System은 철강재의 미래 수요를 예측한 결과와 회사의 생산계획·현재 재고·목표 재고를 함께 비교하여 **검토가 필요한 품목을 빠르게 선별하기 위한 웹 기반 모니터링 시스템**입니다.

AI가 생산계획을 대신 결정하는 것이 아니라, 담당자가 세운 기존 계획을 기준으로 수요예측 결과와 재고 상황을 함께 확인하여 **어떤 품목을 먼저 검토해야 하는지 판단할 수 있도록 지원하는 것**을 목표로 했습니다.

지원 품목은 다음과 같습니다.

| 코드 | 품목 |
| --- | --- |
| HR | 열연강판 |
| CR | 냉연강판 |
| GI | 아연도금강판 |

---

## 2. 프로젝트 배경

철강 수요는 자동차 생산, 건설 수주, 제조업 경기, 환율, 원자재 가격 등 여러 외부 요인의 영향을 받습니다.

단순히 과거 판매량만 확인하는 방식으로는 생산계획과 실제 수요 사이의 차이를 미리 파악하기 어렵기 때문에 다음과 같은 흐름을 하나의 시스템으로 연결하고자 했습니다.

```text
수요예측 결과
      ↓
회사 시장점유율 기준 수요 환산
      ↓
생산계획 / 현재 재고 / 목표 재고 반영
      ↓
품목별 재고 부족 및 리스크 계산
      ↓
검토 우선순위와 Dashboard 제공
```

핵심은 **예측 모델 자체보다 예측 결과를 실제 운영 데이터와 연결하는 백엔드 흐름**을 구현하는 것이었습니다.

---

## 3. 개발 인원 및 담당 역할

- 팀 프로젝트: **3인**
- 담당 영역: **Spring Boot 백엔드 / DB 설계 / Python 실행 연동 / 전체 기능 통합**

### 제가 담당한 부분

- Spring Boot 기반 MVC 구조 및 백엔드 흐름 구성
- Controller / Service / Repository 계층 구현
- JPA Entity 및 MySQL 데이터 구조 설계
- 생산계획·재고 입력 데이터 저장 로직 구현
- Spring Boot에서 Python 예측 모듈을 실행하는 `ProcessBuilder` 연동 구현
- Python 분석 결과를 MySQL에 저장하고 다시 Spring Data JPA로 조회하는 흐름 통합
- Dashboard용 요약·품목별 위험도·수요 변화·인사이트 데이터 조회 구현
- Spring Security 기반 로그인/회원가입 및 BCrypt 비밀번호 해싱 적용
- 팀원이 구현한 프론트엔드 화면과 ML 예측 모듈을 Spring 프로젝트에 통합

> **역할 범위**  
> 프론트엔드 UI 디자인과 ML 모델 자체의 학습·개발은 팀원이 담당했으며, 저는 해당 결과물을 Spring Boot 서비스와 데이터베이스 흐름에 연결하는 백엔드 및 통합 작업을 담당했습니다.

---

## 4. 기술 스택

| 영역 | 기술 |
| --- | --- |
| Backend | Java 21, Spring Boot 3.5.15 |
| Web | Spring MVC, Thymeleaf |
| ORM | Spring Data JPA, Hibernate |
| Security | Spring Security, BCrypt |
| Database | MySQL |
| AI Integration | Python, `ProcessBuilder` |
| Python Data | Pandas, SQLAlchemy, PyMySQL |
| ML Runtime | scikit-learn, XGBoost, LightGBM |
| Build | Gradle |

---

## 5. 시스템 아키텍처

```text
┌───────────────────────────────┐
│            User               │
│ 생산계획 / 재고 / 점유율 입력 │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│        Spring Boot Web        │
│                               │
│ DemandInputController         │
│        ↓                      │
│ DemandInputService            │
│        ↓                      │
│ Spring Data JPA               │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│            MySQL              │
│         demand_input          │
└───────────────┬───────────────┘
                │
                │ /forecast/run
                ▼
┌───────────────────────────────┐
│      PythonExecutionService   │
│         ProcessBuilder        │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│        Python AI Module       │
│                               │
│ python/main.py                │
│        ↓                      │
│ 수요예측 + 리스크 계산        │
│        ↓                      │
│ SQLAlchemy                    │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│            MySQL              │
│                               │
│ forecast_summary              │
│ item_risk_status              │
│ item_demand_change            │
│ dashboard_insight             │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│       DashboardService        │
│       Spring Data JPA         │
└───────────────┬───────────────┘
                │
                ▼
┌───────────────────────────────┐
│          Dashboard            │
│ 요약 / 위험도 / 변화 / 인사이트│
└───────────────────────────────┘
```

### 핵심 데이터 흐름

1. 사용자가 HR / CR / GI의 생산계획과 재고 기준을 입력합니다.
2. `DemandInputController`가 입력값을 전달합니다.
3. `DemandInputService`가 JPA를 통해 `demand_input` 테이블에 저장합니다.
4. `/forecast/run` 요청 시 `ForecastController`가 `PythonExecutionService`를 호출합니다.
5. `PythonExecutionService`가 `ProcessBuilder`로 `python/main.py`를 실행합니다.
6. Python은 MySQL에서 사용자 입력 데이터를 읽어 예측 및 리스크 계산을 수행합니다.
7. 계산 결과를 Dashboard 전용 테이블에 저장합니다.
8. `DashboardService`가 결과 데이터를 다시 조회합니다.
9. `DashboardController`가 Thymeleaf 화면에 데이터를 전달합니다.

---

## 6. 주요 기능

### 6.1 회원가입 및 로그인

Spring Security 기반 인증 구조를 적용했습니다.

- 회원가입
- 로그인 / 로그아웃
- 인증되지 않은 사용자의 서비스 접근 제한
- `CustomUserDetailsService` 기반 사용자 인증
- BCrypt 기반 비밀번호 단방향 해싱

```text
User
 ↓
Login Form
 ↓
Spring Security
 ↓
CustomUserDetailsService
 ↓
UserRepository
 ↓
MySQL users
```

---

### 6.2 생산계획·재고 입력

사용자는 HR / CR / GI 품목별로 다음 값을 입력합니다.

- 기존 생산계획
- 현재 재고
- 목표 재고
- 회사 시장점유율

입력값은 `DemandInputController → DemandInputService → DemandInputRepository` 흐름으로 저장됩니다.

현재 프로젝트는 한 번의 검산 결과를 기준으로 동작하기 때문에 새로운 입력 시 기존 운영 기준 데이터를 삭제한 뒤 최신 입력값을 저장하도록 구현했습니다.

---

### 6.3 Python 예측 모듈 실행

Spring 애플리케이션 내부에서 ML 모델 코드를 직접 다시 구현하지 않고, 기존 Python 예측 모듈을 별도 프로세스로 실행하도록 구성했습니다.

```java
ProcessBuilder processBuilder =
        new ProcessBuilder("python", "main.py");

processBuilder.directory(new File("python"));
processBuilder.redirectErrorStream(true);

Process process = processBuilder.start();
int exitCode = process.waitFor();
```

이 방식으로 Java 웹 애플리케이션과 Python 예측 코드를 연결했습니다.

---

### 6.4 예측 결과 저장

Python 모듈은 `demand_input` 데이터를 조회하여 분석을 수행한 뒤 결과를 다음 테이블에 저장합니다.

| 테이블 | 역할 |
| --- | --- |
| `forecast_summary` | 전체 예측 수요, 현재 재고, 부족량, 고위험 품목 수 |
| `item_risk_status` | 품목별 예측 수요, 재고 차이, 위험 등급 |
| `item_demand_change` | 품목별 수요 변화율 및 차트 데이터 |
| `dashboard_insight` | 품목별 주요 분석 메시지 |

Python에서는 Pandas와 SQLAlchemy를 사용해 결과 DataFrame을 MySQL에 저장합니다.

---

### 6.5 리스크 모니터링 Dashboard

Spring은 Python이 저장한 결과 데이터를 다시 조회해 Dashboard에 제공합니다.

Dashboard에서는 다음 정보를 확인할 수 있습니다.

- 총 예측 수요
- 현재 재고
- 전체 부족량
- 고위험 품목 수
- HR / CR / GI 품목별 위험 등급
- 최근 평균 대비 수요 변화
- 리스크 관련 인사이트

---

## 7. 주요 Backend 클래스

| 클래스 | 역할 |
| --- | --- |
| `DemandInputController` | 생산계획·재고 입력 화면 및 저장 요청 처리 |
| `DemandInputService` | 입력 데이터 저장 비즈니스 로직 |
| `ForecastController` | Python 예측 실행 요청 처리 |
| `PythonExecutionService` | `ProcessBuilder` 기반 Python 프로세스 실행 |
| `DashboardController` | Dashboard 화면 데이터 구성 |
| `DashboardService` | 예측 요약·위험도·차트·인사이트 조회 |
| `UserService` | 회원가입 및 비밀번호 해싱 |
| `CustomUserDetailsService` | Spring Security 사용자 인증 |
| `SecurityConfig` | 접근 권한, 로그인, 로그아웃, PasswordEncoder 설정 |

---

## 8. 주요 Entity / Table

```text
users
 ├─ id
 ├─ username
 ├─ password
 ├─ name
 ├─ role
 ├─ department
 └─ position

 demand_input
 ├─ item_code
 ├─ item_name
 ├─ target_month
 ├─ planned_production
 ├─ current_stock
 ├─ target_stock
 └─ market_share

 forecast_summary
 item_risk_status
 item_demand_change
 dashboard_insight
```

JPA Entity와 Repository를 분리해 조회 및 저장 로직을 관리했습니다.

---

## 9. 프로젝트 구조

```text
smart-steel-system/
├── .env.example
├── src/main/java/com/smartsteel/platform/
│   ├── config/
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   ├── DashboardController.java
│   │   ├── DemandInputController.java
│   │   ├── ForecastController.java
│   │   ├── ForecastResultController.java
│   │   └── LoginController.java
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   └── service/
│       ├── CustomUserDetailsService.java
│       ├── DashboardService.java
│       ├── DemandInputService.java
│       ├── PythonExecutionService.java
│       └── UserService.java
│
├── src/main/resources/
│   ├── static/
│   ├── templates/
│   └── application.properties
│
├── python/
│   ├── configs/
│   ├── data/
│   ├── models/
│   ├── scripts/
│   ├── src/
│   ├── main.py
│   ├── requirements.txt
│   └── README.md
│
└── build.gradle
```

Python 예측 모델에 대한 상세 설명은 [`python/README.md`](./python/README.md)에서 확인할 수 있습니다.

---

## 10. 실행 방법

### Prerequisites

- Java 21
- MySQL
- Python 3

### 1) Repository clone

```bash
git clone https://github.com/Limsungrae/smart-steel-system.git
cd smart-steel-system
```

### 2) MySQL Database 생성

```sql
CREATE DATABASE smartsteel
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

### 3) Database 환경변수 설정

Java와 Python 모두 다음 환경변수를 공통으로 사용합니다.

| 변수 | 설명 | 예시 |
| --- | --- | --- |
| `DB_HOST` | MySQL 호스트 | `localhost` |
| `DB_PORT` | MySQL 포트 | `3306` |
| `DB_NAME` | Database 이름 | `smartsteel` |
| `DB_USERNAME` | MySQL 사용자명 | `smartsteel_user` |
| `DB_PASSWORD` | MySQL 비밀번호 | 로컬 환경에서 직접 설정 |

예시 값은 [`.env.example`](./.env.example)에서 확인할 수 있습니다. 실제 비밀번호가 포함된 `.env` 파일은 Git에서 제외됩니다.

Windows PowerShell:

```powershell
$env:DB_HOST="localhost"
$env:DB_PORT="3306"
$env:DB_NAME="smartsteel"
$env:DB_USERNAME="your_username"
$env:DB_PASSWORD="your_password"
```

macOS / Linux:

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=smartsteel
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

### 4) Python dependency 설치

```bash
pip install -r python/requirements.txt
```

> 현재 `PythonExecutionService`는 시스템의 `python` 명령어를 호출하므로 Python이 PATH에 등록되어 있어야 합니다.

### 5) Spring Boot 실행

Windows:

```bash
gradlew.bat bootRun
```

macOS / Linux:

```bash
./gradlew bootRun
```

### 6) 접속

```text
http://localhost:8080/login
```

로그인 후 생산계획·재고 입력 화면에서 값을 저장하면 Python 예측 프로세스가 실행되고 Dashboard로 이동합니다.

---

## 11. 구현하면서 고민한 점

### Java와 Python을 어떻게 연결할 것인가

예측 코드는 Python으로 이미 구성되어 있었고 웹 서비스는 Spring Boot로 개발했습니다.

초기 프로젝트에서는 별도의 AI API 서버를 추가하는 대신 `ProcessBuilder`를 사용하여 Spring에서 Python 프로세스를 직접 실행하는 방식을 선택했습니다.

이 방식은 짧은 프로젝트 기간 안에 기존 Python 모델을 Spring 서비스와 연결하기에는 단순하고 빠르다는 장점이 있었습니다.

반면 애플리케이션 서버와 AI 실행 환경이 강하게 결합되기 때문에 실제 서비스 확장 시에는 FastAPI 등의 별도 추론 서버로 분리하는 것이 더 적절하다고 판단했습니다.

### AI 결과를 어떻게 서비스 데이터로 사용할 것인가

예측값을 화면에 그대로 출력하는 대신 다음 데이터를 계산·저장하도록 구성했습니다.

```text
전국 수요예측
   ↓
시장점유율 기준 회사 수요 환산
   ↓
기존 생산계획과 비교
   ↓
현재 재고 / 목표 재고 반영
   ↓
품목별 부족량과 위험도 계산
   ↓
Dashboard 데이터 생성
```

이를 통해 AI 결과를 단순 출력하는 것이 아니라 **백엔드의 생산·재고 관리 흐름 안에서 활용할 수 있도록 연결**했습니다.

---

## 12. 현재 한계

본 프로젝트는 교육 과정에서 제작한 프로토타입으로 다음과 같은 한계가 있습니다.

- 실제 기업 ERP / MES 데이터와 연동하지 않음
- 회사 실제 월별 판매량 대신 입력된 시장점유율을 사용해 회사 기준 수요를 추정
- 일부 화면과 서비스에서 분석 대상 월이 고정값으로 설정되어 있음
- Dashboard의 일부 월 라벨이 임시 데이터로 구성되어 있음
- Python 실행이 Spring 서버 내부 `ProcessBuilder`에 동기적으로 결합되어 있음
- Python 프로세스 timeout / retry / 비동기 작업 관리가 구현되어 있지 않음
- 개발·테스트 과정에서 CSRF를 비활성화한 상태이므로 운영 환경 보안 설정 보강 필요

---

## 13. 개선 방향

실제 서비스로 확장한다면 다음 순서로 개선할 계획입니다.

### Backend

- 고정된 `targetMonth` 제거 및 사용자 선택형 조회 구현
- DTO Validation 및 전역 예외 처리 추가
- 운영/개발 환경 설정 분리
- 테스트 코드 확대

### AI Integration

```text
현재
Spring Boot
   ↓
ProcessBuilder
   ↓
Python

개선
Spring Boot
   ↓ REST API
FastAPI Inference Server
   ↓
ML Model
```

- Python 추론 서버를 FastAPI로 분리
- timeout / retry / failure handling 구현
- 비동기 예측 작업 처리
- 모델 버전 관리 추가

### Data / Operation

- ERP / MES 연동
- 회사 실제 판매량 기반 수요 보정
- 월별 시장점유율 이력 관리
- 사용자별 실행 이력 저장
- 예측 결과와 실제 결과의 지속적인 오차 추적

---

## 14. 프로젝트에서 얻은 경험

이 프로젝트를 통해 단순 CRUD를 넘어 다음 경험을 할 수 있었습니다.

- Spring Boot MVC 기반 서비스 구조 설계
- JPA Entity / Repository 기반 데이터 처리
- Spring Security 인증 흐름 구성
- 서로 다른 Java와 Python 런타임 연동
- AI 결과를 DB 중심 데이터 파이프라인으로 서비스에 통합
- 팀원이 만든 프론트엔드와 ML 모듈을 하나의 웹 프로젝트로 통합
- 프로토타입 구조의 한계와 실제 운영 환경에서 필요한 개선점 분석

특히 **AI 모델 자체를 만드는 것뿐 아니라, 모델 결과를 실제 사용자가 활용할 수 있는 서비스 흐름으로 연결하는 백엔드의 역할**을 경험한 프로젝트입니다.

---

## Developer

**임성래**  
Backend Developer

GitHub: [Limsungrae](https://github.com/Limsungrae)
