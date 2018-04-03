# Part1-3 Operating System
* [Background](#background)
  * 프로그램 실행과정
  * 동기와 비동기
* [프로세스 관리](#프로세스-관리)
    * 프로세스 vs 스레드
      * PCB vs TCB
      * 문맥교환
      * 멀티 프로세싱 vs 멀티스레딩
    * 프로세스간 통신(IPC)
* [CPU 스케쥴링](#cpu-스케쥴링)
  * 선점스케쥴링 vs 비선점 스케쥴링
* [프로세스 동기화](#프로세스-동기화)
  * 임계영역(Critical Section)
    * 해결책
  * 교착상태
* [메모리 관리](#메모리-관리)
  * Background
    * Swapping
    * Fragmentation
    * Address Binding
  * Physical Memory Allocationg
  * Paging
  * Segmentation
  * Paged Segmentation
* [Virtual Memory](#virtual-memory)
  * Demand Paging
    * Page Fault
  * Page Replacement 
  * Page Frame Allocation
    * Global vs Local Replacement
  * Thrashing
* References


## Background
### Pragram Execution
'프로그램이 실행되고 있다'는 다음과 같은 의미를 갖는다. 
1. 디스크에 존재하던 실행 파일이 Memory에 올라감
2. 프로그램이 CPU를 할당 받고 명령어를 수행하고 있는 상태

프로그램이 실행되기 전 컴파일된 실행 파일은(.exe) 디스크(File System)에 존재한다. 프로그램이 실행되면 프로그램마다 독자적인 주소공간이 생성되고 주소변환(논리 주소 -> 실제 주소)에 의해 프로세스의 가상 주소 공간이 Physical Memory에 적재된다. 이때 실행 파일 전체가 Memory에 통째로 올라가기보단 당장 CPU 수행에 필요한 부분만 올라가게 되고 나머지는 디스크(Swap Area)에 내려가 있는다. 이는 여러 Process가 공유하는 Memory 공간을 효율적으로 사용하기 위해서이다. 

### 동기와 비동기
동기란 작업의 결과가 나올 때까지 blocking 상태로 기다리는 것, 비동기는 blocking 되지 않고 (이벤트 큐에 넣거나 백그라운드 스레드에게 해당 task를 위임하고) 바로 다음 코드를 실행하는 것을 말한다. 즉, 작업 요청 해놓고 다른 일 하다가 작업 완료되면 그에 따른 작업을 한다.

**동기식 I/O와  Blocked I/O**
* 공통: I/O 작업이 끝날 때까지 대기. 완료된 후에 함수를 반환
* 차이: blocked는 대기큐에 머문다(필수). syn는 필수 x


**비동기식 I/O와 Non-blocked I/O**
* 공통: I/O 작업이 끝날 때까지 기다리지 않고 바로 다음 명령어를 수행. 함수 바로 리턴. 작업 완료는 Event Driven(인터럽트, 콜백함수, 시그널링)으로 전달됨. 
* 차이: 리턴값이 있으면 Non-block, 없으면 Asyn

#### Sync vs Async
시스템 콜의 완료를 기다리면 Sync, 기다리지 않으면 Async

#### Blocked I/O vs Non-Blocked I/O
대기 큐에 들어가고 리턴이 시스템콜이 완료된 후에 오면 Blocked I/O, 완료되지 않아도 오면 Non-blocked I/O. 

#### Sync vs Blocking
시스템콜의 리턴을 기다리는 동안 **대기 큐**에 머무는 것이 필수이면 Blocking, 아니면 Sync

#### Async vs Nonblocking
시스템콜이 즉시 **리턴될 때 데이터**와 함께 오면 non-block, 함께  오지 않으면 Async

-----------------------------------

## 프로세스 관리
### 프로세스
프로세스는 실행중인 프로그램으로 디스크로부터 메모리에 적재되어 CPU를 할당 받을 수 있는 상태를 말한다. 프로세스마다 독립적인 메모리 공간과 PCB가 생성된다.

**프로세스의 주소 공간** 
* code: 수행할 코드
* data: global / static 변수 
* stack: 지역 / 매개 변수, 복귀 주소 
* heap: 동적 할당

#### 프로세스 제어 블록(PCB)
운영체제가 프로세스들을 관리하기 위해 유지하는 프로세스 정보로 자료구조 형태로 커널 data에 존재한다. *언제?* 프로세스 생성과 동시에 고유한 PCB 생성된다. *용도?* 프로세스 문맥전환시 사용됨. 

**PCB에 담겨있는 정보(구조체로 유지)와 이유**
1. OS가 관리상 사용하는 정보
    - 프로세스 상태(new, ready, blocked..): CPU 할당해도 되는지 결정 위해 필요
    - PID: 프로세스 식별자
    - CPU 스케쥴링 정보: CPU 스케쥴링 위해 필요
2. CPU 수행 관련 하드웨어 값
    - 프로그램 카운터(program counter): 다음에 수행할 명령 위치
    - CPU 레지스터: CPU 연산 위해 현 시점에 레지스터에 어떤 값 저장하고 있는지 나타냄
3. 메모리 관련 정보 
    - code, stack, data의 위치, 페이지 / 세그먼트 테이블 정보 -> 메모리 할당 위해 필요
4. 파일 관련
    - 입출력 상태 정보: 프로세스에 할당된 입출력장치와 열린 파일 목록 
    - 자원 사용 정보: 사용된 CPU시간, 시간제한, 계정번호 등


#### 문맥전환(Context Switching)
CPU 제어권이 하나의 프로세스에서 다른 프로세스로 넘어가는 과정을 말한다. 이 과정에서 운영체제는 CPU를 내놓는 프로세스의 상태를 해당 프로세스의 PCB에 저장하고, CPU를 얻는 프로세스의 상태를 PCB에서 읽어온다. *언제 발생?* 타이머 인터럽트나 I/O 요청 시스템콜(blocked)에 의해 발생한다. *왜?* 시분할 시스템 환경(timesharing)에서 중간에 다른 프로세스를 실행하거나 되돌아왔을 때 어디서부터 시작하는지 그 프로세스의 '문맥'을 알아야하기 때문이다. 그 밖의 인터럽트나 시스템 콜 발생시에는 문맥 교환이 아니라 모드 변경만 있을 뿐이다.(사용자 -> 커널모드) 

*프로세스 문맥이란,* 프로세스가 현재 어떤 상태에서 수행되고 있는지 규명한 정보로, 그 프로세스의 주소 공간, CPU 수행 상태(PC, 레지스터), 시스템 콜 등을 통해 커널에서 수행한 일의 상태, 프로세스 관련 커널 자료구조(PCB, 커널 스택)를 포함한다.

### 스레드
스레드는 프로세스 내부의 독립적인 CPU 수행 단위로 프로세스 내 주소 공간과 자원을 공유한다. 

스레드 공유 - data, code, heap, OS 자원

스레드 별도 - stack, PCB의 cpu 관련 정보(레지스터, pc) 제외한 나머진 공유

#### 프로세스 vs 스레드
1. Process는 독립적인 주소 공간을 갖지만, Thread는 자원공유 가능
2. Thread는 문맥 정보가 적어 -> 생성 및 문맥 교환이 빠름.

#### 멀티스레드 장단점
장점:
1. 높은 처리율
2. 빠른 응답성
3. 자원 공유 통한 효율적 사용
4. 문맥 교환이 빠름 (생성 및 문맥 교환 오버헤드 감소)

단점: 
1. 공유자원에 대한 동기화 필요 (작업순서, 자원 접근 컨트롤)
2. 교착상태 주의
2. 각 쓰레드가 효율적으로 고르게 실행되도록 해야함

#### 멀티스레드 vs 멀티프로세스
멀티스레드는 멀티프로세스보다 적은 메모리 공간을 차지하고 문맥 전환이 빠르다는 장점이 있지만, 하나의 스레드가 종료되면 전체 스레드에 영향을 줄 수 있다는 점과 자원공유로 인한 동기화 문제를 갖고 있다. 멀티프로세스는 하나의 프로세스가 죽더라도 다른 프로세스에 영향을 주지 않는다는 장점이 있지만, 멀티 스레드보다 많은 메모리 공간과 CPU 시간을 차지한다는 단점이 존재한다. 모두 동시에 여러 작업을 수행한다는 점에서 같다. 


cf. **용어**

* 멀티태스킹(multi-tasking): CPU가 짧은 시간 단위로 여러 프로그램들을 번갈아 수행함으로써 여러 작업을 동시에 수행하는 것.

* 멀티스레딩(multi-threading): 하나의 프로세스 안에 여러 쓰레드가 병렬로 처리되는 것.

* 멀티 프로세싱(mult-processing): 하나의 컴퓨터 안에 여러 cpu가 설치되어 여러개의 프로그램을 병렬로 처리되는 것 

* 멀티 프로그래밍(multi-programming): 여러 프로그램을 동시에 메모리에 올려놓고 처리

멀티태스킹이 프로그램간의 멀티태스킹을 의미한다면, 멀티쓰레딩은 프로그램 내에서의 멀티태스킹을 구현한 것으로 볼 수 있다.
예로, 하나의 시스템에서 인터넷, 메신저 등을 동시에 사용하는 것은 멀티태스킹이고,
MS-Word내에서 문서를 작성하는 동안, 그때마다 바로바로 맞춤법 검사를 하는 것이 멀티쓰레딩이라고 말할 수 있다.


**사용자 레벨 스레드 vs 커널 레벨 스레드**
* 유저 레벨 스레드 : 사용자 공간에서 스레드 라이브러리 통해 관리되는 스레드로 운영체제가 지원하지 않기 때문에 커널은 스레드의 존재 알 수 없다. 때문에 모드변경이 필요없음. 스케줄러는 프로세스 단위로 관리하게 된다. User Thread가 스스로 관리
* 커널 레벨 스레드 : 커널이 관리하고 생성하는 스레드로 모드 변경이 필요하기 때문에 스레드 생성 및 관리가 사용자 스레드보다 느리다. 스케줄러는 스레드 단위로 관리. 운영체제에서 지원만큼 안정적, 다양한 기능 제공.

### 프로세스간 통신(IPC)
1. 커널 통한 메시지 전달
2. 메모리 영역 공유 (주소공간이 겹침)
3. 소켓 이용한 다른 컴퓨터에 있는 프로세스와의 통신
4. 세마포어: 자원에 대한 접근 제어하기 위한 목적.
5. 파이프: 하나의 p는 읽기만 다른 p는 쓰기만. 한쪽 방향으로만 통신. 양쪽으로 하려면 파이프 2개 필요

-----------------------------------------------
## CPU 스케쥴링
CPU 스케쥴링이란 Ready 상태의 프로세스 중 누구에게 CPU를 줄지 결정하는 것을 말한다.(스케쥴러 = OS 코드) 스케쥴링의 목적은 I/O대기, Memory stall같은 CPU 유휴시간을 최소화하여 CPU 자원의 활용을 극대화 하는 것이다. 

### 선점 스케쥴링 vs 비선점 스케쥴링
사용중인 자원을 빼앗을 수 있는지 여부에 따라 스케쥴링 방식은  선점과 비선점 나뉜다. 선점의 경우 우선순위가 높은 프로세스가 오면 자원을 빼앗길 수 있다. 따라서 우선순위가 낮은 Process가 **기아상태**에 빠지는 것을 막기 위해 시간이 지나면 우선순위도 함께 올려주는 **aging**을 활용한다.

  * Round Robin:(RR): 동일한 크기의 할당시간 지나면 **선점**당함. 공정한 스케쥴링. 평균 응답 시간 빨라짐
  * Priority
    * **선점**: 현재 실행중인 것보다 우선순위가 높은게 도착하면 실행중인 프로세스 멈추고 선점당함
    * **비선점**: 더 높은 우선순위 도착하면 Ready Queue의 Head에 넣음
  * Short Job First(SJF/SRTF): cpu 사용이 짧은 애. 평균 대기 시간 가장 짧지만, 평균 줄이는 것이 항상 좋은 방식은 아님 (starvation)
    * **선점**: 현재 실행중인 프로세스의 남은 CPU 시간보다 더 짧은 애가 도착하면 선점 당함 
    * **비선점**: 더 짧은 애가 도착하면 일단 현재 CPU 사용이 끝날 때까지 기다림  
  * FCFS: 도착순으로 할당 **비선점**
  
-----------------------------------------------

## 프로세스 동기화
Race Condition란, 여러 프로세스가 공유 데이터에 함께 접근하는 현상을 말한다. 공유 자원에 동시 접근함으로써 데이터 불일치 문제가 발생 --> 프로세스간 접근 순서를 정해줘야 한다. _언제 발생?_ 임계영역 수행 중 context switching, 다중 인터럽트, CPU가 여러개인 환경시 필요하다.

### Critical Section(임계영역)
Multi-Threading / Processing 같은 환경에서 동시에 접근 할 수 있는 코드 부분

**race condition 해결 위한 조건**
1. Mutual Exclusion(상호배제): 한번에 하나씩만 접근
2. Process: Critical Section 수행중인 프로세스가 없다면 진입할 수 있게 해줘야함.
3. Bounded Wating(한정된 대기): 기아현상 방지 위해 제한된 대기 시간을 가져야 한다.

### 해결책
Critical Section 진입부분에 lock 걸기, 세마포어, 모니터 

#### 세마포어
**세마포어 변수**를 이용해 **자원을 카운팅**함으로써 접근을 제한하는 방법
- P연산과 V연산을 통해 semaphor 변수에 접근-> 자원 개수 증감
- Block & Wakeup 구현함으로써 process 상태를 변화시킨다. busy waiting로 인한 cpu 낭비 방지
- 종류: 동기화 대상의 갯수에 따라 구분 Binary Semaphore(1개 = Mutex), Counting Semaphore

#### 뮤텍스
뮤텍스란 **lock**을 가지고 있을 경우에만 공유 자원에 접근 가능.
- 화장실이 critical section, 문고리 mutex일 때 화장실 사용시 문고리를 잠그고, 나올때 풀어주는 것에 비유할 수 있다. 
- 1개의 자원 동기화, Binary Semaphore와 같다.
- 세마포어는 화장실이 여러칸(5개, 공유자원)일때, 5명만 들어갈 수 있도록 허용하고 나머지 사용자는 밖에서 대기하도록 하는 것.

#### 모니터 
모니터란 **모니터의 함수를 호출**함으로써 동기화 해주는 방법. **모니터 자체가 보장, 락 없이 실행 가능**
- wait, signal 연산을 통해 condition variable(조건변수) 접근-> process가 큐에서 기다릴수 있게함
- 모니터 안에서는 하나의 p만 활동 가능 구조


**세마포어 vs 모니터**
동기화 방법이 다르다. 세마포어의 경우 각 process가 **자신의 코드**를 통해 syn, 모니터는 **모니터 호출**을 통해 syn 보장.
모니터는 Thread-Safe한 클래스, 객체, 모듈


**세마포어 vs Spinlock**
세마포어는 sleep lock으로 해당 프로세스를 sleep(blocked)시키고 세마포어 대기 큐에 넣고, 세마포어가 반납되면 다시 깨어 ready queue로 옮긴다.
* 스핀락: CPU를 점유한채 루프를 돌면서 자원 획득 시도하는것 (= busy-waiting) 
  * 장점: Context Switch 오버헤드 줄일 수 있음. 무한 루프 돌기 보단 일정시간 lock을 얻을 수 없다면 잠시 sleep하는 back off 알고리즘 사용 
  * 단점: cpu 낭비 

### Deadlock(교착상태)
프로세스들이 서로가 가진 자원을 요청하며 기다리고 있는 상태로 (block 상태) 결국 아무것도 완료하지 못하는 상황. 정체된 상황.

**데드락 조건**
* 비선점
* 한 프로세스만 자원을 가질 수 있음(Mutual Exclution,상호배제)
* 사이클 형성
* 자원을 내놓지 않고 기다림(Hold and Wait, 보유대기)

**해결 방안**
* 예방: 데드락 조건 중 하나 만족시키지 않도록 할당.
* 회피: 자원 요청에 대한 부가 정보를 이용해 데드락 가능성이 없는 경우에만 할당 (Banker's 알고리즘 - 자원당 인스턴스가 여러개일 때, cycle 생기지 않을 경우에만 할당)
* 감지 후 회복: 데드락 발생은 허용하되, 감지가 되면 그때 해결
* 무시: 데드락 발생시 아무 조치 취하지 않음. 해당 프로세스 죽이기.




-----------------------------------------------


## 메모리 관리
* Swap Area vs File System
Swap 영역은 Disk내 File System과 별도로 존재하는 영역으로, 메모리 연장 공간으로 사용된다. 프로세스가 수행중인 동안에만 Disk에 **일시적**으로 저장된다. 즉, 전원

### Swapping
메모리에 올라가 있는 프로세스 전체를 디스크의 Swap 영역으로 가져가는걸 말한다. 디스크에서 메모리로 옮기는 작업을 Swap In, 메모리에서 디스크로 옮기는 작업을 Swap Out이라 한다.

#### Physical Memory 영역
Physical Memory는 **운영체제 상주 영역**과 **사용자 프로세스 영역**으로 나뉘어 사용된다. OS 상주 영역은 인터럽트 벡터와 함께 메모리 낮은 주소 영역을 사용하며 OS 커널이 이곳에 위치한다. 사용자 영역은 메모리의 높은 주소를 사용하며 여러 프로세스들이 이곳에 적재되어 실행된다. 여기에서는 사용자 영역의 관리 방법에 대해 살펴볼 것이다.

### Fragmentation(단편화) 
프로세스들이 메모리에 적재되고 제거되는 일이 반복되다보면, 프로세스들이 차지하는 메모리 틈 사이에 사용하지 못하는 빈 공간들이 늘어나게 되는데 이것이 단편화이다. 2가지 종류가 있다.
* 외부 조각: 프로그램 크기보다 분할의 크기가 작아 사용하지 못하는 메모리 공간
* 내부 조각: 프로세스가 사용하고 남은 메모리 공간. 이 공간은 특정 프로그램에게 이미 할당되었으므로 이 공간을 사용할 수 있는 작은 크기의 프로그램이 있다 해도 사용될 수가 없다. 공간 낭비 
ex) 지하철 좌석 

#### Compaction(압축)
외부 조각 문제를 해결하기 위해 프로세스가 사용중인 메모리 영역을 한쪽으로 몰아 사용할 수 있는 하나의 큰 공간을 만드는 방법이다. 이 방법은 현재 수행중인 프로세스의 메모리 위치를 상당 부분 이동시켜야 하므로 작업 효율이 좋지 못하다. 또한, 실행 도중에 프로세스 주소가 동적으로 바뀌어야 하므로 실행 시간 바인딩 방식이 지원되어야 한다.

## Physical Memory Allocation
### Contiguous Allocation
프로세스를 **통째로** 메모리에 올리는 방법으로 Memory를 미리 나눠놓느냐 아니냐에 따라 고정 분할 할당과 가변 분할 할당으로 나뉜다. Memory 크기보다 큰 프로세스는 못 올리는 단점이 있다.

#### Fixed Partition Allocation 
메모리를 고정된 크기(영구적)로 미리 나눠놓고 하나의 분할에 하나의 프로세스를 올리는 방식이다. 올릴 수 있는 프로그램 수가 고정되어 있고, 분할 크기보다 큰 프로세스는 못 올린다는 제약이 있다. 또한, 외부 조각과 내부 조각이 발생할 수 있다.

#### Variable Partition Allocation
메모리를 미리 나눠놓지 않고 프로그램 크기에 따라 메모리를 할당해 주는 방식으로 분할 크기와 개수가 달라진다. 외부 조각이 발생할 수 있다.

#### 동적 메모리 할당 문제
프로세스를 메모리에 올릴 때 물리적 메모리 내 가용 공간 중 어떤 위치에 올릴 것인지를 결정하는 문제로 가용 공간이란 사용되지 않은 메모리 공간을 말한다. 이 문제를 해결하는 방법으로 
* First Fit: 프로그램 크기보다 큰 것 중 최초로 찾아지는 공간에 할당. 속도 측면 효과적
* Best Fit: 프로그램 크기와 가장 비슷한 공간 찾아 할당. 공간 이용률 좋음
* Worst Fit: 가장 큰 공간에 할당. 

### Noncontiguous Allocation
하나의 프로세스를 여러개로 쪼개서 분산시켜 메모리에 올리는 방법. 프로그램을 동일한 크기로 나누어 메모리에 올리는 Paging, 의미 단위로 나누어 메모리에 올리는 Segmentation, 세그멘테이션 내에서 동일한 크기의 페이지를 나누어 메모리에 올리는 Paged Segment가 있다.

## Paging
메모리와 프로세스 모두 동일한 크기로 나누어 페이지를 프레임에 매핑 시키는 방법으로, 프로세스의 어느 페이지가 몇 번 프레임에 매핑되는지 알기 위해 Page Table을 프로세스마다 구성한다. 이때, 크기 단위를 프로세스에서는 Page, 메모리에서는 Frame이라 부른다. Paging 기법에서는 일부만 메모리에 올리고 나머지는 Swap 영역(Backing Store)에 저장한다.

* 장점
  * 크기가 모두 동일하므로 외부 조각 없음, 압축 작업 해소
  * 페이지 크기가 작아(4KB이하) 메모리에 올라갈 수 있는 Process 수 증가(동시 수행 수 증가)
* 단점
  * 고정 크기 분할로 프레임내 여유공간이 생기는 내부 조각 발생
  * 주소 변환 절차 복잡

### Page Table
Paging 기법에서 논리 주소를 실제 주소로 변환하기 위한 자료구조로 주로 배열로 구현된다. 프로세스마다 생성되며 Page 수 만큼 Table Entry가 존재해 용량이 커서 Main Memory에 위치하며 때문에 메모리 접근 오버헤드가 생긴다. 즉, 메모리에 접근할 때마다 주소 변환을 위한 테이블 접근과 실제 데이터에 접근으로 2번씩 접근하게 된다. 

Memory 접근 속도를 향상시키기 위해 Associative Register(TLB) 고속의 주소 변환용 캐시 메모리를 사용한다. TLB에는 자주 사용되는 Page Table의 Entry가 저장되고, 메모리 접근 전에 TLB부터 검색하고 있으면 바로 Memory 접근, 없으면 Page Table을 검색함으로서 접근 시간을 단축시킨다. 일반적으로 TLB는 모든 항목을 동시에 확인하는 병렬 탐색이 가능한 연관 레지스터를 사용한다.

### Shared Page

### Page Size

## Segmentation
프로세스 주소 공간을 기능 또는 의미 단위로 나누어 Memory에 올리는 방법으로 크기가 균일하지 않다. 일반적을 Code, Data, Stack 부분이 하나씩 세그먼트로 정의되고, 함수 단위로 나누기도 한다. Paging과 마찬가지로 의미단위로 필요한 부분만 메모리에 올라가게 된다. 
* 장점
  * 내부 조각 없음
  * 논리 단위로 묶여 있으므로 용도에 따라 메모리 접근에 차용 권한을 줄 수 있다.
  * 프로그래밍 용이, 코드 수정 악용 방지
* 단점
  * 동시 수행 Process 수 감소

* Segment Table
* Shared Segment

## Paged Segmentation
세그먼테이션과 페이징 기법의 장점만을 취한 주소 변환 방법. 프로그램을 의미 단위의 세그먼트로 나누지만 각 세그먼트는 여러개의 페이지들로 구성된다. 그리고 메모리에 적재하는 단위는 페이지 단위이다. 그 결과 세그멘테이션 기법의 외부 조각 문제를 해결하고, 세그먼트 단위로 프로세스 간 공유나 프로세스 내 접근 권한 보호가 이루어지도록 함으로써 페이징 기법 약점을 해소한다. 주소 변환을 위해 외부의 Segment Table과 내부의 Page Table, 2단계의 데이블을 이용한다.

# Virtual Memory
프로세스마다 생성되는 **독립적인** 공간으로 0번지 부터 시작된다. 이들 중 일부는 physical memory에 위치하고, 나머지는 디스크 swap area에 존재한다. 프로세스의 주소 공간은 Code, Data, Stack, Heap등으로 구성되며, 메모리로 적재하는 단위에 따라 가상 메모리 기법은 demand paging 방식과 demand segmentation 방식이 있다.

## Demand Paging
요구 페이징이란 프로세스를 구성하는 모든 페이지를 통째로 메모리에 올리는 것이 아니라 당장 사용될 페이지만 올리는 방식을 말한다. 즉, cpu의 요청이 들어왔을 때 해당 페이지를 메모리에 load하는 것이다. 당장 필요한 페이지만을 메모리에 올리기 때문에, **메모리 사용량이 감소**하고 프로세스 전체를 메모리에 올리는 데 들었던 **입출력 오버헤드**도 감소. 이것은 사용되지 않을 영역에 대한 입출력까지 수행하던 기존 방식에 비해 **응답 시간을 단축**시킬 수 있으며, **더 많은 프로세스**를 메모리에 올릴 수 있게 된다. 페이지 중 일부만을 적재할 수 있게 되므로, physical memory 크기 보다 큰 프로그램도 실행할 수 있게 된다.

#### valid-invalid Bit
프로세스의 일부만이 메모리에 적재되므로 어떤 페이지가 메모리에 올라와 있고, 안 올라와 있는지 구별하기 위한 방안으로 valid-invalid bit를 두어 해당 페이지가 메모리에 존재하는지 표시하게 된다. 이 비트는 모든 페이지에 대해 표시되어야 하므로 page table의 각 항목별로 저장된다.

#### page fault
**cpu가 참조하려는 페이지가 현재 메모리에 올라와 있지 않아** invalid로 세팅되어 있는 경우 page fault(페이지 부재)가 일어났다고 한다. 

### Page Replacement
Demand Paging 기법의 **성능**에 가장 큰 영향을 미치는 요소는 **Page Fault 발생 빈도**이다. 페이지 부재가 일어나면, 요청된 페이지를 디스크에서 메모리로 읽어오는 막대한 오버헤드가 발생하기 때문에 **Page Fault를 최소화**한다.

페이지 부재가 발생하면 요청된 페이지를 디스크에서 메모리로 읽어와야 하는데, 메모리에 빈 프레임이 존재하지 않을 수 있다. 이 경우 메모리에 있는 페이지 중 하나를 디스크로 쫓아내서 빈 공간을 확보하는 작업이 필요한데, 이것을 Page Replacement(페이지 교체)라 한다. 페이지 교체를 할 때 어떤 Frame에 있는 Page를 쫓아낼 것인지 결정하는 알고리즘을 Replacement Algorithm(교체 알고리즘)이라 한다. 이 알고리즘은 Page Fault를 최소화하는 것이 목표로 **앞으로 참조될 가능성이 가장 적은 페이지를 쫓아내는** 방안이다. 

정리: 
페이지 부재가 발생하면 페이지를 디스크에서 메모리로 가져와야 하는데, 메모리에 빈 공간이 없을 경우 공간 확보를 위해 페이지 하나를 쫓아내고 이때 앞으로 참조될 가능성이 가장 적은 페이지를 쫓아내야합니다. 이때 쫓아낼 페이지를 선택하는 방법으로 여러 교체 알고를 사용.

## Replacement Algorithm
#### OPT (Optimal Page Replacement)
가장 오랫동안 사용되지 않을 페이지를 찾아 교체하는 알고리즘으로 Belady의 최적 알고리즘이라고도 부른다. 
* 장점
  * 알고리즘 중 가장 적은 Page Fault를 보장한다.
* 단점
  * 어떤 순서로 참조될지 미리 알고 있어야 하므로 구현이 어려움. 모든 프로세스의 메모리 참조 계획을 미리 파악할 방법이 없기 때문이다.

#### FIFO 알고리즘
물리 메모리에 온어온 순서대로 교체하는 알고리즘으로 앞으로 참조될 가능성을 고려하지 않는다.
* 장점
  * 구현이 간단하고 이해하기 쉽다.
* 단점
  * 계속 참조가 이루어지는 페이지를 교체해 페이지 부재율을 높일 수 있다.
  * FIFO Anomaly(Belady's Anomaly): 메모리 공간이 늘어나 저장할 수 있는 페이지 프레임 수가 늘어났음에도 페이지 부재율이 더 많이 발생하는 현상

#### LRU 알고리즘
**참조 시점**이 가장 오래된 페이지를 교체하는 알고리즘으로 최근에 참조된 Page가 다시 참조되는 경향을 이용한 것(Time Locality) 
* 장단점
  * 참조 시점의 최근성 반영
  * 장기적인 시간 규모를 보지 못해 page 인기도를 반영 불가

#### LFU 알고리즘
**참조 횟수**가 가장 적은 페이지를 교체하는 알고리즘으로 활발히 사용되는 페이지는 참조 횟수가 앞으로도 많아질 것이라는 가정에서 만들어짐
* 장단점
  * LRU는 직전 참조 시점만을 보지만 LFU는 장기적인 규모에서의 참조 성향을 고려하므로 Page 인기도 반영 가능
  * LRU보다 구현이 복잡

* Paging System에선 LRU, LFU 알고리즘을 사용 못한다.

#### Clock 알고리즘 (LRU 근사 알고리즘)
LRU와 LFU 알고리즘은 페이지의 최근 참조 시각 및 참조 횟수를 소프트웨어적으로 유지해야 하므로 알고리즘 운영에 시간적인 오버헤드가 발생한다. 클럭 알고리즘은 하드웨어 지원을 통해 운영 오버헤드를 줄인 방식이다. 

클럭 알고리즘은 최근에 참조되지 않은 페이지를 교체하는 알고리즘으로 LRU와 달리 참조 시점이 가장 오래되었음을 보장하진 못한다. 하지만 하드웨어적인 지원으로 동작하기 때문에 교체 페이지 선정이 LRU보다 훨씬 빠르다. 교체할 페이지 선정 방법은 페이지 프레임들의 reference bit들을 첫번째 프레임부터 순차적으로 조사해 참조 비트가 0인 페이지를 찾아 교체한다.

## Page Frame Allocation
여러개 프로세스가 동시에 수행되는 상황에서 각 프로세스에 얼만큼의 메모리 공간(Page Frame)을 할당할지 결정해야 한다. 모든 프로세스에 똑같은 갯수를 할당하는 방식과 프로세스 크기에 비례하여 할당하는 방식, 그리고 프로세스의 우선순위에 따라 할당하는 방식이 있다. 이 방식은 프로세스 중에서 당장 cpu에서 실행될 프로세스와 그렇지 않은 프로세스를 구분하여 전자 쪽에 더 많은 페이지 프레임을 할당하는 방식이다.





