## 面向对象：


## 数据类型
基础数据类型：byte short int long float double boolean char

## java集合

Set: HashSet TreeSet SynchronizedSet ConcurrentHashSet

List: ArrayList LinkedList SynchronizedList CopyOnWriteArrayList

Map: HashMap TreeMap LinkedHashMap HashTable SynchronizedMap ConcurrentHashMap


## HashMap并发产生的问题：
1. 数据不一致
2. jdk1.7 会产生循环链表而导致死锁: 头插入法，jdk8改成了尾插入法
3. 遍历会ConcurrentModificationException异常，HashMap 的迭代器不支持并发修改。


## ConcurrentHashMap:

jdk1.7 分段锁，双重hash，先获取segment, segment维护了hash表数组

jdk1.8 cas 局部锁， 修改或者添加删除节点时锁住头节点

