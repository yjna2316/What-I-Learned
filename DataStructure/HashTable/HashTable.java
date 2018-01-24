package HashTable;


public interface HashTable<T> {
  void put(T t1, T t2);

  void remove(T t);

  T get(T t);

  T hashFunction(T t);
}


