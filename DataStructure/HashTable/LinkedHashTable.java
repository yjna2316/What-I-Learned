package Hash;

import java.util.HashMap;

/* Chaining - ��Ŷ�� Linked List�� ���� */
public class LinkedHashTable {
    private final static int TABLE_SIZE = 128;

    LinkedHashEntry[] table;

    LinkedHashTable() {
        table = new LinkedHashEntry[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; ++i) {
            table[i] = null;
        }
    }

    /* �ش� ��Ŷ�� �ִ� Linked LIst�� ���������� Ž���� ����� Ű Ž�� */
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

    /* ü�̴��� �̿��Ͽ� ���̺� Ű ���� */
    public void put(int key, int value) {
        int hash = hashFunction(key);
        if (table[hash] == null) {
            table[hash] = new LinkedHashEntry(key, value);
        } else {
            LinkedHashEntry entry = table[hash];  // Linked List�� ����Ű�� ������
            while (entry.getNext() != null && entry.getKey() != key) {  // Linked List�� ���������� Ž��
                entry = entry.getNext();
            }
            if (entry.getKey() == key) {  // Ž�� Ű�� ã������ value�� ������Ʈ
                entry.setValue(value);
            } else {  // ã�� Ű�� ���ٸ�, ���ο� ��带 ������ �������� ����
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
            if (entry.getKey() == key) {  // Ű�� ã���� ��,
                if (prevEntry == null) {  // ù�� ° ��带 �����ϴ� ���
                    table[hash] = entry.getNext();
                } else {  // �������(prevEntry)�� ������ ��尡 ����Ű�� ���� ���� ����
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
