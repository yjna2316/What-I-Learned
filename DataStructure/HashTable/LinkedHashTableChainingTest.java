package HashTable;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LinkedHashTableChainingTest {
  private LinkedHashTableChaining table;

  @Before
  public void init() {
    table = new LinkedHashTableChaining();
  }

  @Test
  public void testGet() {
    put(1, 5);
    put(5);
    print();

    for (int i = 0; i < 6; ++i) {
      int key = i + table.TABLE_SIZE*i;
      System.out.print("key: " + key);
      System.out.print(", hash: " + table.hashFunction((key)));
      System.out.println(", val: " + table.get(key));
    }
  }

  @Test
  public void testPut() {
    put(1, 1000);
    assertEquals(0, table.get(1));
    print();
  }

  @Test
  public void testRemove() {
    put(1, 5);
    print();

    table.remove(1);
    assertEquals(-1, table.get(1));
    print();

    table.remove(11);
    assertEquals(-1, table.get(11));
    print();

    table.remove(21);
    assertEquals(-1, table.get(21));
    print();
  }


  /* Put elements to the hash table with different index */
  private void put(int num) {
    for (int i = 0; i < num; ++i) {
      table.put(i + table.TABLE_SIZE*i, i);
    }
  }

  /* Put elements to the hash table with same index */
  private void put(int offset, int num) {
    for (int i = 0; i < num; ++i) {
      table.put(offset + table.TABLE_SIZE*i, i);
    }
  }

  /* Print out the entries in hash table */
  private void print() {
    for (LinkedHashEntry entry : table.hashTable) {
      LinkedHashEntry head = entry;
      System.out.print("(k, v) = ");
      if (head == null) {
        System.out.print("null");
      }
      while (head != null) {
        System.out.print(
            "(" + head.getKey() +
            ", " + head.getValue() +
            ") ");
        head = head.getNext();
      }
      System.out.println();
    }
    System.out.println();
  }
}