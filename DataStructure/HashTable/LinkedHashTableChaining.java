package HashTable;
/*
 * Collision Resolution: Seperated Chaining
 * HashTable Implemented with seperated Chaining using LinkedList.
 */
public class LinkedHashTableChaining// implements HashTable<Integer>
{
  public static final int TABLE_SIZE = 5;
  LinkedHashEntry[] hashTable;

  /*
   * Initialize the HashTable.
   */
  LinkedHashTableChaining()
  {
    hashTable = new LinkedHashEntry[TABLE_SIZE];
    for (int i = 0 ; i < TABLE_SIZE; ++i)
    {
      hashTable[i] = null;
    }
  }

  public void put(int key, int value)
  {
    int hash = hashFunction(key);
    /*
     *  If the bucket is empty, then create the entry and store it
     *  as the first element of a linked-list.
     */
    if (hashTable[hash] == null)
    {
      hashTable[hash] = new LinkedHashEntry(key, value);
    }
    else
    {
      LinkedHashEntry entry = hashTable[hash];
      // Iterate the linked list entry until the last node or the node we find comes out.
      while (entry.getNext() != null && entry.getKey() != key)
      {
        entry = entry.getNext();
      }

      // If the node with given key exists, update the value.
      if (entry.getKey() == key)
      {
        entry.setValue(value);
      }
      // Otherwise, create the new entry and insert into the list at the end.
      else
      {
        entry.setNext(new LinkedHashEntry(key, value));
      }
    }
  }

  public void remove(int key)
  {
    int hash = hashFunction(key);
    if (hashTable[hash] != null)
    {
      LinkedHashEntry prevEntry = null;
      LinkedHashEntry entry = hashTable[hash];
      // Iterate the linked list until we find the node or reach the end of the list.
      while (entry.getNext() != null && entry.getKey() != key)
      {
        prevEntry = entry;
        entry = entry.getNext();
      }

      if (entry.getKey() == key) {
        // If the first Node is to remove.
        if (prevEntry == null)
        {
          hashTable[hash] = entry.getNext();
        }
        else
        {
          prevEntry.setNext(entry.getNext());
        }
      }
    }
  }

  public int get(int key)
  {
    int hash = hashFunction(key);
    LinkedHashEntry entry = hashTable[hash];
    if (entry != null) {
      while (entry != null && entry.getKey() != key) {
        entry = entry.getNext();
      }
    }
    return (entry == null)? -1 : entry.getValue();
  }

  int hashFunction(int key)
  {
    return key % TABLE_SIZE;
  }
}

class LinkedHashEntry //implements Entry<Integer, Object>
{
  private  int key;
  private  int value;
  private  LinkedHashEntry next;

  LinkedHashEntry(int key, int value)
  {
    this.key = key;
    this.value = value;
    this.next = null;
  }

  public int getValue()
  {
    return value;
  }

  public void setValue(int value)
  {
    this.value = value;
  }

  public int getKey()
  {
    return key;
  }

  public LinkedHashEntry getNext()
  {
    return next;
  }

  public void setNext(LinkedHashEntry next)
  {
    this.next = next;
  }
}