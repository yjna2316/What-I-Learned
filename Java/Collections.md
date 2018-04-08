# Part2-2 Collections
* [Sort](#sort)
    * Arrays.sort vs Collections.sort
    * Comparable vs Comparator
* [Collection Interfaces](#collection-interfaces) 
    * [List Interface](#list-interface)
        * ArrayList
        * LinkedList
        * Vector    
    * [Map Interface](#map-interface)
        * HashMap vs HashTable 
        * LinkedHashMap
        * TreeMap      
    * [Set Interface](set-interface)
        * HashSet 
        * LinkedHashSet
        * TreeSet
    * [Methods](#methods)
        * List
            * Remove
            * Array to ArrayList   
* [Exceptions and Errors](#exceptions-and-errors)
    * ConcurrentModificationException
* References
---------------------------------------------------------------
# Sort
------------------------------------------------------
## Arrays.sort vs Collections.sort
### Arrays.sort()
* **Array 대상 오름차순 정렬:** int[], object[],. etc
* Collections로 정렬하고 싶다면 Array를 Arraylist로 만든 후 sort
* 내부 구현
    * Primitive type: Dual-Pivot Quicksort
    * Object type: MergeSort
    * O(nlogn) 

```java
// Sorts arr[] in descending order
Arrays.sort(arr, Collections.reverseOrder());

Arrays.sort(arr);
```
### Collections.sort()
* **List 대상 오름차순 정렬:** ArraysList, LinkedList,. etc

```java
List list = new ArrayList(Arrays.asList(arr));

// in ascending order
Collections.sort(list);

// in descending order
Collections.sort(list, Collections.reverseOrder());
```

-----------------------------------------------------------------------------------
## Comparable vs Comparator
* **Sort an Object in user defined criteria**
* Arrays.sort와 Collections.sort에 모두 사용 가능
### Comparable Interface
* compareTo() 오버라이드 
```java
int compareTo(T o)
```

* 객체 스스로가 다른 객체와 비교하면서 정렬
    * compareTo()에서 정의한 순서가 natural ordering이 됨
    * sort() 인자가 1개
* natural order? 
    * 정렬 기준이 되는 default값 
    * 보통 인간이 정렬하는 순서: 알파벳순(a->z), 오름차순 숫자 정렬(1->100)
* 언제사용? 
    * attribute들에 대해 종속적으로 정렬하고 싶을 때  
    * 나이를 기반으로한 이름순 정렬

 ```java
// A class 'Student' that implements Comparable
class Student implements Comparable<Student>
{
    private String name;
    private int age;
 
    ...
    // Used to sort student by age 
    // if age is same then sort by name
    public int compareTo(Student s)
    {
        if (this.getAge() == s.getAge()) {
          return this.getName().compareTo(s.getName());
        }
        return this.getAge() - s.getAge();
    }
    ...
}

class Main {
    ...
    Collections.sort(list);
}
```

### Comparator Interface
* class를 외부적으로 따로 구현해야함 compare() 오버라이드 
```java
   int compare(Object o1, Object o2)
```

* 여러 attribute에 대해 독립적으로 정렬하고 싶을 때 사용 
    * sort() 인자가 2개
    * 정렬 기준 수만큼 class 구현

```java
class SortbyAge implements Comparator<Student>
{
    // Used for sorting in ascending order of
    // age
    public int compare(Student a, Student b)
    {
        return a.getAge() - b.getAge();
    }
}

class SortbyName implements Comparator<Student>
{
    // Used for sorting in descending order of
    // name
    public int compare(Student a, Student b)
    {
        return b.getName().compareTo(a.getName());
    }
}

class Main {
    public static void main(String[] args)
    {
        ArrayList<Student> list = new ArrayList<Student>();
        list.add(new Student("Jack", 23));
        list.add(new Student("Emily", 19));
        list.add(new Student("Dean", 30));
        list.add(new Student("Ashley",23));
        
        Collections.sort(list, new SortbyName());
        
        // Sort by age : (1) Create an object of SortbyAge
        //               (2) Call Collections.sort
        //               (3) Print Sorted list
        SortbyAge sortbyAge = new SortbyAge();
        Collections.sort(list, sortbyAge);  
        for (Student Student: list)
            System.out.println(Student.getAge() + " " +
                               Student.getName());     
}

```
-------------------------------------------------------
# Collection Interfaces 
------------------------------------------------------
<!-- ## List Interface
### ArrayList
### LinkedList
### Vector
------------------------------------------------------ 
## Map Interface
### HashMap vs HashTable 
### LinkedHashMap
### TreeMap      
------------------------------------------------------
## Set Interface
### HashSet 
### LinkedHashSet
### TreeSet
-------------------------------------------------------
-->
## Methods
### List
#### Remove 
* remove(int index): index로 제거 
* remove(Obejct obj): 값으로 제거

> Index가 아닌 **int값**으로 제거하고 싶다면 int -> **Integer로 변환**해줘야. 그렇지 않으면 index로 인식된다.
``` java
// 인덱스 10번에 있는 원소값 제거
list.remove(10); 
// 리스트 중 가장 먼저 나오는 원소값 10 제거
list.remove((Integer)10);
```
* int parameter vs long parameter
```java
long longNum = 2;
int intNum = 2;
List<Integer> list = new ArrayList<Integer>();
list.add(1);
list.add(2);
list.add(3);
list.remove(longNum);
System.out.println(list.size()); 
// output: 3
list.remove(intNum);
System.out.println(list.size()); 
// output: 2
```
* int값이면 index로 인식하지만, long은 object로 인식함 / long은 int로 자동 convert되지 않기 때문 -> 직접 명시해주면 가능
*  object로 인식하기 위해 [Autoboxing](https://github.com/yjna2316/What-I-Learned/blob/master/Java/Java.md#wrapper-class) 발생: long primitive type -> java.lang.Long object 
* 결과: java.lang.Integer로 autobox되어 있는 list 값들과 비교(equals)했을 때, 같은 값이 없으므로 아무것도 제거되지 않게됨
* Reference\
https://stackoverflow.com/questions/30349653/undesired-behavior-of-arraylist-remove-in-java

#### Array to ArrayList
* Arrays.asList(list)
```java
 List<T> asList(T... a)
```
* Primitive type에 대해서는 사용 안하고 **Loop를** 이용한다: boxing 처리 안되기 때문 
* Wrapper class나 non-primitive type일 때 사용한다.
```java
int[] nums = {1, 2, 3};
List<Integer> intList = new ArrayList<Integer>();
for (int i : nums)
{
    intList.add(i);
}

List<String> stooges = Arrays.asList("Larry", "Moe", "Curly"); // ok
```

-------------------------------------------------------
## Exceptions and Errors
------------------------------------------------------
### ConcurrentModificationException
* **언제발생?** List를 iterate 하면서 동시에 조작도 할 때 발생 
    * read & remove(or add) 동시 수행시
* 이유: List는 동기화가 보장되지 않기 때문에, 다음 원소로 이동할 때마다 현재 list 사이즈와 expected size(주로 initial size)를 비교해서 값이 다른지 확인한다.
* 해결: copy본까지 총 2개를 만들어서 하나는 돌면서 읽고 나머지 한개에서 remove로 해결. 대신 remove한 값을 다른 곳에서 다시 검사하지 않도록 주의해야함( 데이터 불일치로 인한 문제 신경쓰기)



------------------------------------------------------
## References
------------------------------------------------------
* Sorting objects and primitive types\
https://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/ 