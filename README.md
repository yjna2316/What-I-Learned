# What-I-Learned
-------------------------------------------------------
## Index
* Computer Science Knowledges(CS 지식)
  * [Data Structure (자료구조)](#part1-1-data-structure)
  * [Computer Architecture (컴퓨터구조)](#part1-2-computer-architecture)
  * [Operating System (운영체제)](#part1-3-operating-system)
  * [Database (데이터베이스)](#part1-4-database)
  * [Network (네트워크)](#part1-5-network)

* Programming Languages(프로그래밍 언어)
  * [Java](#part2-1-java)
    * [Collections](#part2-2-collections)
-------------------------------------------------------
## CS knowledges
### [Part1-1 Data Structure](https://github.com/yjna2316/What-I-Learned/blob/master/DataStructure/DataStructure.md)
* Array vs ArryaList vs LinkedList
* Stack and Queue
* Sort
* Priority Queue
  * Heap  
  * Heap Sort
* Tree
  * Binary Tree
  * BST(Binary Search Tree)
  * AVL Tree
  * Red Black Tree
* HashTable
  * Hash Function
  * Collision Resolution
  * Resizing
* Graph
  * 그래프 구현
  * 그래프 탐색
    * DFS(깊이우선탐색)
    * BFS(너비우선탐색)
* References

###  [Part1-2 Computer Architecture](https://github.com/yjna2316/What-I-Learned/blob/master/ComputerArchitecture/ComputerArchitecture.md)

* 폰노이만 구조
* CPU
  * 논리 회로
  * 레지스터와 클록
  * CPU 구조
  * 쉼 없이 일하라 - 파이프라이닝
  * 위험과 해결
* 버스
* 메모리
  * 메모리 계층 구조
  * 캐시
    * 적중과 실패
    * 지역성의 원리
    * 캐시 블록 교체
    * 캐시 동기화 문제
* 입출력 장치
  * I/O 컨트롤러
  * I/O 동작 처리 방법
    * 폴링
    * 인터럽트
    * DMA(Direct Memory Access)

### [Part1-3 Operating System](https://github.com/yjna2316/What-I-Learned/blob/master/OperatingSystem/OperatingSystem.md) 
* Background
  * 프로그램 실행과정
  * 동기와 비동기
* 프로세스 관리
    * 프로세스 vs 스레드
      * PCB vs TCB
      * 문맥교환
      * 멀티 프로세싱 vs 멀티스레딩
    * 프로세스간 통신(IPC)
* CPU 스케쥴링
  * 선점스케쥴링 vs 비선점 스케쥴링
* 프로세스 동기화
  * 임계영역(Critical Section)
  * 해결책
    * 락(Lock)
    * 세마포어
    * 모니터 
  * 교착상태
* 메모리 관리
  * Background
    * 스와핑(Swapping)
    * 단편화(Fragmentation)
    * 주소 바인딩(Address Binding)
  * 물리 메모리 할당방식
* 가상메모리
  * 요구 페이징(Demand Paging)
  * 페이지 교체
  * 스레싱(Thrashing)
* References

### [Part1-4 Database](https://github.com/yjna2316/What-I-Learned/blob/master/Database/Database.md)
* Database
* Index
  * Clustered Index vs Unclustered Index
* Transaction
* Normalization(정규화)
* NoSQL

### [Part1-5 Network](https://github.com/yjna2316/What-I-Learned/blob/master/Network/Network.md)
* OSI 7계층 
* HTTP VS HTTPS
  * SSL / TLS
* GET vs Post
* 웹캐싱
* Cookie vs Session
* TCP/IP
  * TCP와 UDP
  * 3 Way Handshaking
* 웹페이지가 표시되기까지 과정
  * DNS 질의 과정(DNS query)
* 동기식 vs 비동기식 통신
* 빅 엔디안 vs 리틀 엔디안
* 대칭키 암호화와 공개키 암호화
  * 전자인증서
  * 전자서명
-----------------------------------------------------------------------
## Programming Languages
### Part2-0 C
### Part2-1 Java
### [Part2-2 Java Collections](https://github.com/yjna2316/What-I-Learned/blob/master/Java/Collections.md)
* Sort
    * Arrays.sort vs Collections.sort
    * Comparable vs Comparator
* Collection Interfaces 
    * List Interface
        * ArrayList
        * LinkedList
        * Vector    
     * Set Interface
        * HashSet 
        * LinkedHashSet
        * TreeSet        
    * Methods
        * List
            * Remove
            * Array to ArrayList
* Map Interface
    * HashMap vs HashTable 
    * LinkedHashMap
    * TreeMap 
* Exceptions and Errors
    * ConcurrentModificationException
* References

