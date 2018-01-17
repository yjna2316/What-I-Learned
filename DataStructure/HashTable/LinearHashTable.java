package Hash;

/*
 * Open Address ���
 * Hash Table�� 1���� �迭�� ������ �浹 �߻���, ���������� Ž��(Linear Probing)�� ������ ��ġ Ž��
 */
public class LinearHashTable  {
    private final static int TABLE_SIZE = 128;

    HashEntry[] table;

    /* �ؽ� ���̺��� �� ���� ���� ���� ���·� �����. */
    LinearHashTable() {
        table = new HashEntry[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; ++i) {
            table[i] = null;
        }
    }

    public int get(int key) {
        int hash = hashFunction(key);
        int initialHash = -1;
        while (hash != initialHash
                && (table[hash] == DeletedEntry.getUniqueDeletedEntry() || table[hash] != null
                && table[hash].getKey() != key)) {
            if (initialHash == -1) {
                initialHash = hash;
            }
            hash = (hash + 1) % TABLE_SIZE;
        }
        return  (table[hash] == null || hash == initialHash) ? -1 : table[hash].getValue();
    }

    public void put(int key, int value) {
        int hash = hashFunction(key);
        int initialHash = -1;
        int indexOfDeletedEntry = -1;
        while (hash != initialHash
                && (table[hash] == DeletedEntry.getUniqueDeletedEntry() || table[hash] != null
                && table[hash].getKey() != key)) {
            if (initialHash == -1) {
                initialHash = hash;
            }
            if (table[hash] == DeletedEntry.getUniqueDeletedEntry()) {
                indexOfDeletedEntry = hash;
            }
            hash = (hash + 1) % TABLE_SIZE;
        }

        if ((table[hash] == null || hash == initialHash)
                && indexOfDeletedEntry != -1) {
            table[indexOfDeletedEntry] = new HashEntry(key, value);
        } else if (initialHash != hash) {
            if (table[hash] != DeletedEntry.getUniqueDeletedEntry()
                    && table[hash] != null && table[hash].getKey() == key) {
                table[hash].setValue(value);
            } else {
                table[hash] = new HashEntry(key, value);
            }
        }
    }

    public void remove(int key) {
        int hash = hashFunction(key);
        int initialHash = -1;
        while (hash != initialHash
                && (table[hash] == DeletedEntry.getUniqueDeletedEntry() || table[hash] != null
                && table[hash].getKey() != key)) {
            if (initialHash == -1) {
                initialHash = hash;
            }
            hash = (hash + 1) % TABLE_SIZE;
        }

        if (hash != initialHash && table[hash] != null) {
            table[hash] = DeletedEntry.getUniqueDeletedEntry();
        }
    }

    public int hashFunction(int key) {
        return key % TABLE_SIZE;
    }
}


class HashEntry {
    private int key;
    private int value;

    HashEntry(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

class DeletedEntry extends HashEntry {
    private static DeletedEntry entry = null;

    private DeletedEntry() {
        super(-1, -1);
    }

    public static DeletedEntry getUniqueDeletedEntry() {
        if(entry == null) {
            entry = new DeletedEntry();
        }
        return entry;
    }
}
