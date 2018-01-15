# Part1 DataStructure
*[Priority Queue](#priority-queue)
 *[Heap](#heap)

## Priority Queue
우선순위 개념을 큐에 도입한 자료구조로, 우선순위가 높은 데이터 순으로 삭제된다. 배열, 연결리스트, 힙 등을 통해 구현할 수 있으며, 힙(heap)이 가장 효율적이다. 

### Heap
한 노드의 값이 서브트리의 모든 노드 값보다 항상 크거나 작은 완전 이진 트리이다. Priority Queue 구현에 사용되며 최대/최소값 탐색에 용이하다. Heap은 완전 이진 트리이기 때문에 배열로 구현 가능하며, 0이 아닌 1번 index부터 루트노드가 
시작된다. 이는 노드의 고유번호 값과 배열의 index를 일치시켜 계산상 혼동을 줄이기 위함이다 Heap에는 최대힙(max heap), 최소힙(min heap) 두 종류가 있다.

MaxHeap에서 최대값을 찾는데 걸리는 timeComplexity는 O(1)이다. 새 노드를 삽입하거나 삭제하는데 걸리는 시간은 O(logN)이다. 이는 heap 성질을 계속 유지시키기 위해 heapify 과정을 거치기 때문이다. 


Binary Search Tree는 중복을 허용하지 않으나, Heap에서는 중복을 허용한다.
