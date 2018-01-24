package HashTable;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import java.util.*;

public class HashTableOpenAddressTest {
  private HashTableOpenAddress openAddressTable;
  private TableEntry entry;

  @Before
  public void init() {
    openAddressTable = new HashTableOpenAddress();
  }

  @Test
  public void getTest() {
    put(1, 5);
    print();
    int count = 0;  // to check the number of non-empty buckets
    for (TableEntry entry : openAddressTable.table) {
      if (entry != null) {
        assertEquals(entry, openAddressTable.get(entry.getKey()));
        count = count + 1;
      }
    }
    assertEquals(count, openAddressTable.size);
  }

  @Test
  public void putTest() throws Exception {
    openAddressTable.put(1, 1);  // put new entry
    for (TableEntry entry : openAddressTable.table) {
      if (entry != null) {
        assertEquals(1, entry.getKey());
        assertEquals(1, entry.getValue());
      }
    }
    print();

    openAddressTable.put(1, 2);  // update entry value
    for (TableEntry entry : openAddressTable.table) {
      if (entry != null) {
        assertEquals(1, entry.getKey());
        assertEquals(2, entry.getValue());
      }
    }
    assertEquals(1, openAddressTable.size);
    print();
  }

  // 질문1. key값이 (-1,-1)이라면?? 키값이 중복되면,,? 다른 방법 있나?
  // ex. node field로 추가하는 방법 (민지처럼)
  // 질문2. 테이블 내용을 출력해서 test해도 되나.

  @Test
  public void removeTest() throws Exception {
    put(10);
    System.out.println("before remove");
    print();

    System.out.println("after remove");
    openAddressTable.remove(12);
    print();
  }

  /* Put elements to the hash table with different index */
  private void put(int num) {
    for (int i = 0; i < num; ++i) {
      openAddressTable.put(i + openAddressTable.TABLE_SIZE*i, i);
    }
  }

  /* Put elements to the hash table with same index */
  private void put(int offset, int num) {
    for (int i = 0; i < num; ++i) {
      openAddressTable.put(offset + openAddressTable.TABLE_SIZE*i, i);
    }
  }

  /* Print out the entries in hash table */
  private void print() {
    for (TableEntry entry : openAddressTable.table) {
      System.out.print("(k, v) = ");
      if (entry == null) {
        System.out.print("null");
      } else {
        System.out.print(
            "(" + entry.getKey() +
                ", " + entry.getValue() +
                ") ");
      }
      System.out.println();
    }
    System.out.println();
  }
}