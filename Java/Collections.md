# Part2-2 Collections
* Arrays.sort vs Collections.sort
* Comparable vs Comparator
  * Sort an object in user defined criteria

---------------------------------------------------------------

## Arrays.sort vs Collections.sort
### Array.sort()
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
* Arrays와 Collections 모두 해당
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

#### References
https://www.mkyong.com/java/java-object-sorting-example-comparable-and-comparator/ 