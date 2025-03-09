## 面向对象和面向过程的区别：

面向过程：分析问题的步骤，用函数一步一步把这些步骤实现，然后在使用的时候一一调用即可

面向对象：把构成问题的事务拆分成各个对象，建立对象的目的是为了描述事物在解决整个问题中发生的行为。

```text
//面向过程
static void cook() {
    //洗菜
    wash();
    //切菜
    cut();
    //炒菜
    stirFry();
    //出锅
    finish();
}


//面向对象,定义厨师
abstract class Chef {
   void cook() {
        //洗菜
        wash();
        //切菜
        cut();
        //做菜：炒、蒸、火锅
        cookInternal();
        //出锅
        finish();
    }
}

//专门做炒菜的厨师
class StirFryChef extends Chef {
    void cookInternal() {
        stirFry();
    }
    
    void stirFry() {//....}
}

//专门做火锅的厨师
class HotPotChef extends Chef {
    //...
}
```


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

