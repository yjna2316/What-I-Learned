package Hash;

import java.util.HashMap;

/* Chaining - 버킷을 Linked List로 구현 */
public class LinkedHashTable {
    private final static int TABLE_SIZE = 128;

    LinkedHashEntry[] table;

    LinkedHashTable() {
        table = new LinkedHashEntry[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; ++i) {
            table[i] = null;
        }
    }

    /* 해당 버킷에 있는 Linked LIst를 순차적으로 탐색해 저장된 키 탐색 */
    public int get(int key) {
        int hash = hashFunction(key);
        if (table[hash] == null) {
            return -1;
        }
        LinkedHashEntry entry = table[hash];
        while (entry != null && entry.getKey() != key) {
            entry = entry.getNext();
        }
        return (entry == null) ? -1 : entry.getValue();
    }

    /* 체이닝을 이용하여 테이블에 키 삽입 */
    public void put(int key, int value) {
        int hash = hashFunction(key);
        if (table[hash] == null) {
            table[hash] = new LinkedHashEntry(key, value);
        } else {
            LinkedHashEntry entry = table[hash];  // Linked List를 가리키는 포인터
            while (entry.getNext() != null && entry.getKey() != key) {  // Linked List를 순차적으로 탐색
                entry = entry.getNext();
            }
            if (entry.getKey() == key) {  // 탐색 키를 찾았으면 value값 업데이트
                entry.setValue(value);
            } else {  // 찾는 키가 없다면, 새로운 노드를 생성해 마지막에 삽입
                entry.setNext(new LinkedHashEntry(key, value));
            }
        }
    }

    public void remove(int key) {
        int hash = hashFunction(key);
        if (table[hash] != null) {
            LinkedHashEntry prevEntry = null;
            LinkedHashEntry entry = table[hash];
            while (entry.getNext() != null && entry.getKey() != key) {
                prevEntry = entry;
                entry = entry.getNext();
            }
            if (entry.getKey() == key) {  // 키를 찾았을 때,
                if (prevEntry == null) {  // 첫번 째 노드를 삭제하는 경우
                    table[hash] = entry.getNext();
                } else {  // 이전노드(prevEntry)를 삭제할 노드가 가리키는 다음 노드로 연결
                    prevEntry.setNext(entry.getNext());
                }
            }
        }
    }

    public int hashFunction(int key) {
        return key % TABLE_SIZE;
    }

}

/* Bucket points to the LinkedList where the items consist of the list */
class LinkedHashEntry {
    private  int key;
    private  int value;
    private  LinkedHashEntry next;

    LinkedHashEntry(int key, int value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public LinkedHashEntry getNext() {
        return next;
    }

    public void setNext(LinkedHashEntry next) {
        this.next = next;
    }
}
