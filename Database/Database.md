## Part1-3 Database
* [Database](#Database)
* [Index](#Index)
  * Clustered Index vs Unclustered Index
* [Transaction](Transaction)
* [Normalization(정규화)](#Normalization(정규화))
* [NoSQL](#NoSQL)

-------------------------------
## Database
데이터베이스 용도(목적)\
'검색' - 원하는 데이터를 빠르게 가져오는것. 

### 데이터 검색 과정
* 데이터베이스는 보조저장장치(디스크)에 저장되어있다. 
  * 데이터 검색을 위해 DBMS는 필요한 데이터를 블록단위로 디스크에서 주기억 장치로 가져와야 -> 변경된 데이터들은 블록단위로 디스크에 다시 기록해야한다.
  * BUT 디스크 I/O 작업 시간이 오래걸리는 작업으로, DBMS 성능은 디스크 입출력 횟수에 좌우된다. 
  * 만약, 인접한 디스크 블록들을 읽는다면, seek time과 rotational latency가 들지 않기 때문에 입출력 속도가 빨라진다.
* Disk access time = seek time + rotational delay + transfer time
  * seek time: 해당 트랙으로 디스크 헤드 이동시간 (가장 오래걸림)
  * rotational delay(회전 지연 시간): 디스크를 회전시켜 해당 블록이 디스크 헤드 아래로 오게 하는 시간
  * transfer time: 블록을 메모리로 전송하는데 걸리는 시간
### DBMS(Database Management System)
다수의 사용자들이 데이터베이스 내 데이터를 접근할 수 있도록 해주는 시스템으로 사용자 또는 다른 프로그램의 요구를 처리하고 응답하여 데이터를 사용할 수 있게 해준다.

### DBMS 필수기능
데이터베이스에 저장될 데이터를 정의하고 접근하여 조작하고 제어할 수 있는 기능을 제공한다.
* **정의(Definition)**
  * 데이터베이스에 저장될 데이터 type과 구조에 대한 정의, 제약 조건 등을 명시하는 기능
* **조작(Manipulation)**
  * 데이터 검색, 갱신, 삽입, 삭제 등을 처리 위한 수단 제공
* **제어(Control)**
  * 데이터베이스를 접근하는 갱신, 삽입 삭제 작업이 정확하게 수행되어 **데이터 무결성**이 유지되도록 제어
  * 정당한 사용자가 **허가된 데이터만** 접근할 수 있도록 보안(Security) 유지 및 권한(Authority) 검사 가능
  * 여러 사용자가 데이터베이스를 **동시에 접근하여** 데이터를 처리할 때 **병행제어**(Concurrency Control)

-------------------------------------------------------
## Index
검색 속도를 높이기 위해 사용하는 기술로, 인덱스는 트리구조로 되어 있어 인덱스 탐색에 O(logN) 시간이 걸린다. 인덱스는 책 뒤에 있는 색인과 같아서 모든 레코드를 full scann 하지 않고 특정 칼럼을 색인화(따로 파일로 저장)한 INDEX 파일을 검색하여 검색 속도를 빠르게 한다.
* 인덱스 탐색 Root -> Branch -> Leaf -> 디스크 저장소 순
* 인덱스도 디스크에 저장되어 있다.
* 균형 트리 이용(B+트리)
* 성능: 디스크 접근 횟수 얼마나 줄이느냐

### 인덱스 장단점
  * 장점: 검색 속도가 빠르다
  * 단점 
    * 인덱스 저장 공간이 추가적으로 필요하기 때문에 디스크 용량 감소 
      * 모든 컬럼 인덱스 하게 되면 데이터 파일보다 인덱스 파일이 더 커질 수 있다.
    * 데이터 갱신 속도는 느려짐
      * 책에 새로운 내용이 추가되면 인덱스에도 함께 추가해줘야 하는 것처럼
      * 데이터에 갱신이 일어나면 인덱스도 함께 수정해줘야하므로 추가적인 I/O 작업 발생 -> 갱신 속도 느려짐
  * **언제 사용? **
    * 데이터 양이 많고 변경보다 검색이 빈번한 경우
    * 인덱스를 걸고자 하는 필드(칼럼)의 값이 다양할 경우
      * 성별같이 데이터 종류가 일정할 경우 필터링이 별로 안되서 인덱스 효과 없음

####  어떤 칼럼을 인덱스로 만드는게 좋을까?
**Cardinality가 높은 컬럼**을 사용한다. 인덱스로 탐색 범위를 최대한 좁혀야 하기 때문
* 남자/여자 10명씩 있을때, 성별을 인덱스로 잡는다면, 남/녀 중 하나를 택하기 때문에 인덱스를 통해 50% 밖에 걸러내지 못하지만
* 주민등록번호나 계좌번호로 잡을 경우, 중복도가 낮기 때문에 데이터의 대부분이 필터링 되고 빠른 검색이 가능해진다.

**Cardinality**
* 집합에서 원소의 개수
* 성별(남,여), 학년은 카디널리티가 낮다고 하지만 주민등록번호, 계좌번호는 높다고 한다.
* 컬럼 여러개를 인덱스로 할땐 -> 카디널리티 높 -> 낮은 순으로 정의

#### 왜 인덱스로 b-tree가 사용되는가? hash table은?
Hash table은 데이터 접근에 상수시간이 걸리지만 적합하지 않다. 등호 연산에 특화된 자료구조로 **범위 검색(<>)에는 사용 할 수 없기 때문이다.** 따라서 **등호 연산뿐만 아니라 범위검색도 가능한 b-tree**를 사용한다. hash인덱스는 주로 메모리 기반의 데이터베이스에 사용한다.

### Clustered index vs Non-clustered index
### replication vs sharding

-------------------------------------------
## Transaction
**논리적 작업 단위**를 말한다. **계좌 이체 과정**처럼 연속적으로 일어나야 하는 일련의 연산들을 하나의 실행 단위로 묶어서 처리하는 것이다. 예로, 계좌이체시 보내는 사람의 계좌를 감소시키고(인출) 받는 사람의 계좌 증가시키는 것이(입금) 하나의 동작으로 일어나야 할 때, 이 동작을 하나의 트랜잭션으로 처리할 수 있다. 그런데 도중에 오류가 발생한다면, 돈이 빠지기 전 처음 상태로 돌아가 아예 수행되지 않도록 보장해야한다. 이를 Rollback이라 한다. 트랜잭션이 성공하면 이를 Commit이라 한다.

* Commit 과 Rollback
  - Commit: 트랜잭션이 성공적으로 끝났고, 결과가 데이터베이스에 반영됨 (지속성)
  - Rollback: 트랜잭션을 성공적으로 끝내지 못했음 의미, 원자성 보장 위해 트랜잭션 수행 전 상태로 되돌리는 것.

### 트랜잭션의 성질 (ACID)
Transaction이 만족시켜야 하는 4가지 특성으로 **원자성, 고립성, 일관성, 지속성**이 있다.
1. 원자성(Atomicity): 트랜잭션의 모든 연산을 100% 수행하거나 그렇지 못하면(도중에 문제 발생시) 어떤 연산도 수행되지 않도록함
2. 일관성 (Consistency): 트랜잭션 수행 전후 상황이 동일하게 무결성 제약 조건 유지 및 데이터의 일관성을 보장 => 동시성 제어, 무결성 제약조건
3. 독립성 (Isolation): 각 트랜잭션은 서로 간섭없이 독립적으로 수행 => 동시성 제어
4. 지속성 (Durability): 정상 종료된 트랜잭션의 결과는 영구적으로 database 저장. hw, sw적으로 고장나도 결과가 보존됨 => 회복

* 계좌에서 돈이 빠지고 들어오는 것이 모두 일어나거나 도중에 고장나면 돈이 빠지기 전 상태로 돌아가 아예 수행되지 않음 보장
송금 전후의 상태가 일관성 있고(두 잔고의 합 일정), 송금 과정 중 다른 사람이 이 계좌에 접근 못하게 독립적으로 수행되어야, 송금 결과는 영구적으로 저장되어야.
* 데이터들은 비휘발성인 디스크에 저장, 일부만 main 메모리의 버퍼에 저장됨. 블록 단위로 이동. 

----------------------------------------------------------------

* 테이블
테이블은 집합이자 함수이다. 
공통점들을 가진 요소들을 모아 놓은 '집합'이고 그 집합을 구성하는 요소들을 기본키(열)로 구별. 
기본키는 다른 열과 함수적 관계 만족 -> 기본키를 통해 그 키 값에 대응하는 다른 열의 정보를 알 수 있다. 
(+) ID를 통해 회원들을 식별할 수 있. 카페 (회원 정보) 테이블
(+) 이때, ID를 알면 그 ID에 대응되는 회원의 다른 정보(열)를 알 수 있다. 만약 동일한 ID를 사용할 수 있다면 타인의 정보도 알게 될것. 
(+) 대형병원에서 환자라는 '공통점'으로 테이블 생성 Patients (이름, 나이, 성별, 진료과) 
환자 ID로 그 환자의 다른 정보에 접근 할 수 있음.

* 무결성 제약 조건이란?
Database 상태가 만족시켜야하는 조건으로 갱신시 일관성을 깨지 않도록 보장.
- 계좌 이체후 보낸 사람의 잔고가 0보다 작아지는 경우가 없어야 한다. 

--------------------------------------------
## Normalization(정규화)

#### 이상현상(Anomaly)
 **중복된 정보**로 인해 테이블 조작(갱신)시 문제가 발생하는 현상
- 삭제이상(deletion anomaly) : 한 튜플을 삭제함으로써 연쇄 삭제로 인해 삭제되지 않아야 할 정보까지 삭제되는 현상 
- 삽입이상(insertion anomaly) : 불필요한 정보까지 함께 삽입되는 현상
- 수정이상(modification anomaly) : 일부만 수정되어 데이터 불일치가 발생하는 현상

* 예: 학생 R (학번(PK), 학생이름, 이메일, 전공번호(PK), 전공)
    * 삭제 - 컴퓨터공학과 학생이 단 한 명 있을 때, 이 학생의 정보를 삭제하면 전공 정보도 함께 삭제됨 (전공번호가 1인 전공 이름이 컴퓨터공학과)
    * 삽입 - 기계공학과를 신설했는데 아직 그 전공을 택한 학생이 없어서 이 전공에 대한 정보 입력 불가. (엔티티 무결성 제약조건에 의해 기본키(학번)은 NULL 불가 
    * 수정 - 컴퓨터공학과를 SW공학과로 수정하려는데 천명중 100명만 수정되어 데이터 불일치 상태 (일부만 변경됨)

### 정규화
관계형 데이터베이스에서 **갱신이상과 중복을 최소화**하기 위해 테이블 분리하는 과정으로,
정규화는 함수적 종속성에 근거해 이뤄진다.
- 이상현상 야기하는 Attribute간 종속관계 제거 위해 무손실 분해하는 과정
- 필요성: 저장 공간 최소화, 테이블 조작으로 인한 이상 현상 제거

* 정규화 과정 
  * 제1정규형: 모든 속성값이 원자값만 갖는다 -> 반복되는 속성값 제거 -> but 아직도 정보가 중복되어 갱신이상 유발
  * 제2정규형: 1정규형 만족 & 기본키 일부에만 부분적 종속하는 열이 존재x -> 부분함수 종속성 제거, 해당 키와 종속하는 열로 이뤄진 테이블 생성
  (+)학번(PK) -> 학과이름, 학과전화번호 / 학번(PK), 과목번호(PK) -> 학점 
  * 제3정규형: 2정규형 만족 & 2단계에 걸쳐서 함수 종속이 존재x  -> 이행함수 종속성 제거, 모든 애트리뷰트가 기본키에 직접 종속
  (+)학번(PK) -> 학과이름 -> 학과전화번호 => 학번(PK) -> 학과이름, 학과이름(PK) -> 학과전화번호
  * BCNF: 3정규형 만족 & 모든 결정자가 후보키 -> 후보키가 아닌 결정자 제거
  학번(PK) -> 학과이름, 학과전화번호 / 학과이름 -> 학과전화번호


* 용어
  * 정규형: 특정 조건을 만족하는 릴레이션의 스키마 형태
  * 함수의 종속성: Attribute간에 성립하는 관계
    * 만약 학번과 학생이름이라는 Attribute가 있을 때, 학번을 통해 학생이름이 유일하게 결정된다면, 학생이름은 학번에 함수적으로 종속된다.
* 정규화를 할 수록 테이블 수 증가 -> 테이블간 관계 파악 어려움 -> ER 다이어그램으로 관계를 한눈에 파악하기 쉽게 그래프화함.
* 목적: 정보 중복으로 인한 공간 낭비와 갱신 이상을 해결하기 위해 

### 정규화의 장단점
* 장점
  - 데이터 중복 최소화 -> 공간 낭비 줄임
  - 이상 현상 해결
  - 새로운 형태의 데이터 삽입시 테이블 재구성 필요성 줄임
* 단점
  - 빈번한 조인 연산 처리 속도 저하 -> 응답시간 느려짐
  - 의미적으로 관련된 정보를 여러개의 테이블로 분리 -> 정보 다시 취합 위해 비용이 큰 조인 연산 수행해야
* 방안: 비 정규화, 고성능 컴퓨터 도입


### 역정규화
검색이 빈번할때 수행 속도를 높이기 위해 정규화로 분해된 두개 이상의 테이블들을 하나로 합치는 작업.  
* 분해되기 전에는 테이블 하나만 접근하면 되지만, 분해된 후에는 많은 테이블 접근해야 하므로 시간이 오래 걸림(디스크 I/O),
* 빈번한 조인 연산 증가 -> 성능 저하, 응답시간 늦어짐.
* 반정규화 과도하게 적용하다 보면 데이터 무결성이 깨질 수 있고, 갱신 질의문에 대한 응답시간 느려질 있음
