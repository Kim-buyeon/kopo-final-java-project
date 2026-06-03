# 🛒 Java E-Commerce CLI (콘솔 기반 쇼핑몰 애플리케이션)

> 순수 Java와 Oracle JDBC로 구현한 콘솔 기반 이커머스 애플리케이션
>
> 프레임워크 없이 인터페이스 · 추상 클래스 · **제네릭(`<T, ID>`)** 만으로 계층형 아키텍처를 구성하고, 여러 도메인이 공통 CRUD 로직을 재사용하도록 설계한 자바 프로젝트입니다.

---

## 📌 프로젝트 소개

회원·상품·카테고리·장바구니·주문으로 이루어진 쇼핑몰의 핵심 흐름을 콘솔 환경에서 구현한 프로젝트입니다. 단순히 기능을 동작시키는 데 그치지 않고, **객체지향 설계(다형성)와 제네릭을 활용해 도메인이 늘어나도 코드 중복이 발생하지 않는 구조**를 만드는 데 초점을 두었습니다.

- **순수 Java + Oracle JDBC** — Spring 등 프레임워크 없이 JDBC로 직접 DB 연동
- **계층형 아키텍처** — Controller / Service / Repository / View / Domain 으로 책임 분리
- **제네릭 기반 추상화** — 공통 CRUD를 상위 계층에 한 번만 작성하고 모든 도메인이 재사용

---

## 💡 핵심 설계 — 제네릭 기반 계층 추상화

이 프로젝트의 핵심은 **타입에 따라 달라지는 부분만 각 도메인이 구현하고, 모든 도메인에 공통되는 로직은 상위 계층에 단 한 번만 작성**한 점입니다. 이를 위해 각 계층을 `<T, ID>` 제네릭 인터페이스로 정의하고, 공통 동작은 추상 클래스에 구현했습니다.

| 계층 | 공통 계약(인터페이스) | 공통 구현(추상 클래스) | 도메인이 구현하는 부분 |
|------|----------------------|------------------------|------------------------|
| Repository | `Repository<T, ID>` — `save` / `findAll` / `findById` / `deleteById` | (도메인별 JDBC 구현) | 도메인별 SQL 및 매핑 |
| Service | `CommonService<T, ID>` — `create` / `remove` / `getOne` / `getAll` | `AbstractService<T, ID>` — Repository에 위임하는 공통 CRUD 구현 | 도메인 고유 비즈니스 로직 |
| Controller | `Controller<T>` — `start` / `processCreate` / `processRead` / `processUpdate` / `processDelete` | `AbstractController<T, ID>` — 생성·수정·삭제 흐름(서비스 호출 + 예외 처리) 공통 구현 | `processRead`, `start` |
| View | `View<T, ID>` — 메뉴·입력·출력 관련 메서드 | `AbstractView<T, ID>` — 헤더·메시지·ID 입력 등 공통 출력 구현 | `showMenu`, `inputData`, `renderDetail`, `renderList` |

### 효과
- **공통 CRUD를 한 번만 작성** — 회원·상품·카테고리·장바구니·주문 5개 도메인이 동일한 추상 계층을 재사용 (예: `MemberService extends AbstractService<Member, String>`).
- **새 도메인 추가 비용 최소화** — 새로운 도메인을 추가할 때 공통 로직을 다시 짤 필요 없이, 타입별로 달라지는 부분만 구현하면 됩니다.
- **공통 정책 변경의 일원화** — 생성·수정·삭제의 공통 흐름이나 예외 처리 정책을 바꿀 때 상위 추상 클래스 한 곳만 수정하면 전체 도메인에 반영됩니다.

> 도메인마다 반복될 공통 로직을 약 476줄에서 119줄로 통합해 보일러플레이트를 약 75% 줄이는 것을 목표로 설계했습니다.

---

## 🏗 아키텍처

```
              ┌─────────────────────────────────────────────┐
              │                  Main                        │
              │  의존성 조립(Repository→Service→Controller)   │
              │  로그인 세션 / 역할(MANAGER·MEMBER) 분기       │
              └───────────────────────┬─────────────────────┘
                                      │
        ┌─────────────────┐   ┌───────▼────────┐   ┌──────────────────┐
        │ EcommerceManager│   │  EcommerceMember│   │   (콘솔 메뉴 루프)│
        │   관리자 모드    │   │   쇼핑몰 모드   │   │                  │
        └────────┬────────┘   └───────┬────────┘   └──────────────────┘
                 │                    │
                 ▼                    ▼
          ┌──────────────────────────────────┐
          │   Controller  (AbstractController) │  ← 사용자 입력 흐름 제어
          └──────────────┬───────────────────┘
                         ▼
          ┌──────────────────────────────────┐
          │     Service   (AbstractService)    │  ← 비즈니스 로직 / Repository 위임
          └──────────────┬───────────────────┘
                         ▼
          ┌──────────────────────────────────┐
          │   Repository  (Repository<T, ID>)  │  ← JDBC CRUD
          └──────────────┬───────────────────┘
                         ▼
                   ┌───────────┐
                   │ JdbcUtil  │  ← Connection / 트랜잭션 / 스키마 초기화
                   └─────┬─────┘
                         ▼
                  ┌──────────────┐
                  │  Oracle DB   │
                  └──────────────┘

   ※ View(AbstractView)는 Controller와 연결되어 콘솔 입출력을 담당
```

데이터 흐름은 `View ↔ Controller → Service → Repository → JdbcUtil → Oracle DB` 순으로 이어지며, 각 계층은 자신의 책임에만 집중합니다.

---

## ✨ 주요 기능

### 회원 / 인증
- **회원가입 · 로그인 · 로그아웃** 및 세션 관리(로그인한 사용자 ID 유지)
- **역할 기반 분기** — 회원 등급(`MANAGER` / `MEMBER`)에 따라 진입 모드가 분리됩니다.

### 관리자 모드 (`EcommerceManager`)
- **상품 관리** — 상품 등록 · 조회 · 수정 · 삭제(CRUD)
- **카테고리 관리** — 카테고리 CRUD (상위 카테고리 참조 구조)
- **회원 정보 조회**

### 쇼핑몰 모드 (`EcommerceMember`, 일반 회원)
- **상품 둘러보기**
- **장바구니 담기 / 주문하기**

### 데이터 계층
- **Oracle `MERGE` 기반 Upsert** — `save()` 하나로 신규 삽입과 갱신을 함께 처리
- **트랜잭션 관리** — `setAutoCommit(false)` 후 `commit` / 오류 시 `rollback`
- **스키마 자동 초기화** — 최초 실행 시 필요한 테이블 존재 여부를 확인하고, 없으면 `schema.sql`을 실행해 자동 생성

---

## 🛠 기술 스택

| 구분 | 내용 |
|------|------|
| Language | Java |
| Build | Gradle |
| Database | Oracle Database (JDBC) |
| DB Driver | ojdbc11 (23.3.0.23.09) |
| DB 보안 연결 | oraclepki, osdt_cert, osdt_core (Oracle Cloud Wallet) |
| Test | JUnit 5 |
| 설정 | `application.properties` (DB 접속 정보), `schema.sql` (테이블 정의) |

> 별도 ORM/프레임워크 없이 표준 JDBC(`Connection`, `PreparedStatement`, `ResultSet`)로 데이터 접근을 직접 구현했습니다.

---

## 📂 프로젝트 구조

```
kopo-final-java-project
└── src/main/java/com/finaljavaproject/www
    ├── Main.java                 # 진입점: 의존성 조립, 세션·역할 분기, 메인 루프
    ├── controller                # 사용자 입력 흐름 제어
    │   ├── Controller.java            # 제네릭 인터페이스
    │   ├── AbstractController.java    # 공통 생성·수정·삭제 흐름 구현
    │   ├── MemberController.java
    │   ├── ProductController.java
    │   ├── CategoryController.java
    │   └── CartController.java
    ├── service                   # 비즈니스 로직
    │   ├── CommonService.java         # 제네릭 인터페이스
    │   ├── AbstractService.java       # Repository 위임 공통 CRUD
    │   ├── MemberService.java
    │   ├── ProductService.java
    │   ├── CategoryService.java
    │   ├── CartService.java
    │   └── OrderService.java
    ├── repository                # JDBC 데이터 접근
    │   ├── Repository.java            # 제네릭 인터페이스 (save/findAll/findById/deleteById)
    │   ├── MemberRepository.java
    │   ├── ProductRepository.java
    │   ├── CategoryRepository.java
    │   ├── CartItemRepository.java
    │   └── OrderRepository.java
    ├── view                      # 콘솔 입출력
    │   ├── View.java                  # 제네릭 인터페이스
    │   ├── AbstractView.java          # 공통 출력/입력 구현
    │   ├── MemberView.java
    │   ├── ProductView.java
    │   ├── CategoryView.java
    │   └── CartView.java
    ├── domain                    # 도메인 모델
    │   ├── Member.java
    │   ├── Product.java
    │   ├── Category.java
    │   ├── CartItem.java
    │   ├── Orders.java
    │   └── constant
    │       ├── MemberClassfication.java  # MEMBER / MANAGER
    │       └── ProductStatus.java        # NORMAL / SUSPENSION
    ├── ecommerce                 # 모드별 메뉴 흐름
    │   ├── EcommerceManager.java      # 관리자 모드
    │   └── EcommerceMember.java       # 쇼핑몰(회원) 모드
    └── util
        └── JdbcUtil.java          # DB 연결, 트랜잭션, 스키마 초기화
```

---

## 🗄 도메인 모델

| 도메인 | 설명 | 주요 필드 |
|--------|------|-----------|
| Member | 회원 | id, password, name, telNo, email, status, 회원등급 |
| Product | 상품 | 상품ID, 이름, 설명, 가격, 재고, 상태 |
| Category | 카테고리 | 카테고리ID, 이름, 정렬순서, 상위 카테고리 |
| CartItem | 장바구니 항목 | 장바구니ID, 회원ID, 상품ID, 수량 |
| Orders | 주문 | 주문ID, 회원ID, 상품ID, 수량 |

테이블은 Oracle 기준으로 정의되어 있으며(`VARCHAR2`, `CLOB`, `NUMBER`, `CHECK` 제약, 외래키 참조), `schema.sql`에 명세되어 있습니다.

---

## 🚀 실행 방법

> 요구 사항: **JDK**, **Gradle**, **접속 가능한 Oracle Database**(Oracle Cloud Autonomous DB 등)

```bash
# 1. 저장소 클론
git clone https://github.com/Kim-buyeon/kopo-final-java-project.git
cd kopo-final-java-project

# 2. DB 접속 정보 설정
#    src/main/resources/application_ex.properties 를 복사해 application.properties 생성 후 값 입력
#      db.driver=oracle.jdbc.OracleDriver
#      db.url=jdbc:oracle:thin:@...
#      db.user=...
#      db.password=...
#    (Oracle Cloud Wallet 사용 시 wallet 경로 설정 포함)

# 3. 빌드 및 실행
./gradlew build
./gradlew run
```

최초 실행 시 `JdbcUtil.setupDatabase("schema.sql")` 가 테이블 존재 여부를 확인하고, 없으면 `schema.sql`을 실행해 자동으로 테이블을 생성합니다.

---

## 👤 작성자

- **김부연** — [@Kim-buyeon](https://github.com/Kim-buyeon)

---

## 📎 Repository

- https://github.com/Kim-buyeon/kopo-final-java-project
