package HashTable;
/*
 * Collision Resolution: Open Address
 * Hash Table을 1차원 배열로 구현해 충돌 발생시, 순차적으로 탐색(Linear Probing)해 저장할 위치 탐색
 */
public class HashTableOpenAddress
{
  public static final int TABLE_SIZE = 11;
  public float threshold = 0.75f;
  public int maxSize = 96;
  public int size = 0;

  public TableEntry[] table;
  public TableEntry deleted;

  /**
   * Initialize the hash table null.
   */
  public HashTableOpenAddress()
  {
    table = new TableEntry[TABLE_SIZE];
    for (int i = 0; i < TABLE_SIZE; ++i)
    {
      table[i] = null;
    }
    deleted = new TableEntry(-1, -1);
  }

  public void setThreshold(float threshold) {
    this.threshold = threshold;
    maxSize = (int) (table.length * threshold);
  }

  public int hashFunction(int key)
  {
    return key % TABLE_SIZE;
  }

  /**
   * Find the entry with given key in the hashTable
   */
  public TableEntry get(int key)
  {
    int hash = hashFunction(key);
    int index = hash;
    int end = index + TABLE_SIZE;

    /**
     * Check if given key exists, if success, return the entry.
     * Else, move to next bucket until an empty bucket comes out
     * or entire table has been visited.
     */
    while (table[hash] != null && index < end)
    {
      if (hasKey(hash, key))
      {
        return table[hash];
      }
      index = index + 1;
      hash = hashFunction(hash + 1);
    }
    return null;
  }

  /**
   * Add a new entry with given input (key, value) into an empty bucket in the hash table.
   */
  public void put(int key, int value)
  {
    int hash = hashFunction(key);
    int index = hash;
    int end = index + TABLE_SIZE;

    /**
     * Search given key, if exists, update the value.
     * Else, move to the next bucket until an empty bucket comes out
     * or entire table has been visited.
     */
    while (table[hash] != null && index < end)
    {
      if (hasKey(hash, key))
      {
        table[hash].setValue(value);
        return;
      }
      hash = hashFunction(hash + 1);
      index = index + 1;
    }

    /**
     * If bucket is empty, then create the entry and store it.
     */
    if (table[hash] == null)
    {
      table[hash] = new TableEntry(key, value);
      size = size + 1;
    }

    if (size >= maxSize) {
      resize();
    }
  }

  /*
   * If the number of entries of the table get bigger than maxSize,
   * then create a new table double to the size and move contents to the new table from non-empty not deleted buckets.
   */
  public void resize()
  {
    int tableSize = 2 * table.length;
    maxSize = (int) (tableSize * threshold);
    TableEntry[] oldTable = table;
    table = new TableEntry[tableSize];
    size = 0;

    for (int i = 0; i < oldTable.length; ++i)
    {
      if (oldTable[i] != null && oldTable[i] != deleted)
      {
        put(oldTable[i].getKey(), oldTable[i].getValue());
      }
    }
  }

  /*
   * Search given key entry and replace it with deleted entry.
   */
  public void remove(int key)
  {
    int hash = hashFunction(key);
    int index = hash;  // start index to probe
    int end = index + TABLE_SIZE;  // index to check if entire entry has been probed

    while (table[hash] != null && index < end)
    {
      if (hasKey(hash, key))
      {
        table[hash] = deleted;
        size = size - 1;
      }
      hash = hashFunction(hash + 1);
      index = index + 1;
    }
  }

  private boolean hasKey(int hash, int key)
  {
    return table[hash] != deleted && table[hash].getKey() == key;
  }
}


class TableEntry //implements Entry
{
  private  int key;
  private  int value;

  TableEntry(int key, int value)
  {
    this.key = key;
    this.value = value;
  }

  public int getKey()
  {
    return key;
  }

  public void setValue(int value)
  {
    this.value = value;
  }

  public int getValue()
  {
    return value;
  }
}

