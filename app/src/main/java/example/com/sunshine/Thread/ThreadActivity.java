package example.com.sunshine.Thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.MessageQueue;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import example.com.sunshine.R;

import static android.R.attr.host;
import static android.os.Environment.isExternalStorageRemovable;

/**
 * Created by qianxiangsen on 2017/4/17.
 */

public class ThreadActivity extends AppCompatActivity{

    ArrayList<Song> arrayList = new ArrayList<>();

    //以有序状态保存并可防止重复
    TreeSet<String> treeSet = new TreeSet<>();
    //放在重复的集合，可快速的找到相应的元素。
    HashSet<String> hashSet = new HashSet<>();

    // 可用成对的name/value 来保存与取出
    HashMap<String,Bitmap> hashMap = new HashMap<>();

    //针对经常插入或删除中间元素所设计的高效率集合(实际上Array还是比较实用)
    LinkedList<String> linkedList = new LinkedList<>();

    //类似HashMap，但可记住元素插入位置的顺序，也可以设定成依照元素上次存取的先后来排序。
    //将最近引用的对象保留在强引用的LinkedHashMap中，并在缓存超过其之前驱逐最近最少使用的成员 指定大小。
    //注意：在过去，一个流行的内存缓存实现是SoftReference或WeakReference位图缓存，但是这不是推荐的。
    //从Android 2.3（API Level 9）开始，垃圾收集器收集软/弱引用更为积极，这使得它们相当无效。
    // 此外，在Android 3.0（API 11级）之前，位图的后台数据存储在本机内存中，该内存不会以可预测的方式释放，可能导致应用程序短暂超出其内存限制并崩溃。
    LinkedHashMap<String,Bitmap> linkedHashMap = new LinkedHashMap<>();


    BankAccount account = new BankAccount();

    private int balance;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);



        ClassLoader classLoader = ThreadActivity.class.getClassLoader();

            while (classLoader != null) {
                Log.d("ClassLoader", "onCreate: "+classLoader.toString());
                classLoader = classLoader.getParent();
            }

//        RyanAndMonicaJob theJob = new RyanAndMonicaJob();
//        Thread one = new Thread(theJob);
//        Thread two = new Thread(theJob);
//        one.setName("杰伦");
//        two.setName("费城");
//        one.start();
//        two.start();

        //内部存储 应用的内部目录的 File
        Log.d("TAG", "getFilesDir: --->"+getFilesDir());
        //内部存储缓存目录 返回表示您的应用临时缓存文件的内部目录的 File。
        // 务必删除所有不再需要的文件并对在指定时间您使用的内存量实现合理大小限制，比如，1MB。
        // 如果在系统即将耗尽存储，它会在不进行警告的情况下删除您的缓存文件。
        Log.d("TAG", "getCacheDir: --->"+getCacheDir());

        //外部存储 公共文件 应供其他应用和用户自由使用的文件。
        // 当用户卸载您的应用时，用户应仍可以使用这些文件。
        Log.d("TAG", "getExternalStoragePublicDirectory: --->"+ Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES));

        //外部存储 私有文件 属于您的应用且在用户卸载您的应用时应予删除的文件。
        // 尽管这些文件在技术上可被用户和其他应用访问（因为它们存储在外部存储中）， 但它们实际上不向您的应用之外的用户提供任何输出值。
        // 当用户卸载您的应用时，系统会删除应用外部私有目录中的所有文件。
        Log.d("TAG", "getExternalFilesDir: --->"+ getExternalFilesDir(Environment.DIRECTORY_PICTURES));

        // 外部存储 缓存目录
        Log.d("TAG", "getExternalCacheDir: "+getExternalCacheDir());

        //LruCache LinkedHashMap
        //类似HashMap，但可记住元素插入位置的顺序，也可以设定成依照元素上次存取的先后来排序。
        //将最近引用的对象保留在强引用的LinkedHashMap中，并在缓存超过其之前驱逐最近最少使用的成员 指定大小。
        //注意：在过去，一个流行的内存缓存实现是SoftReference或WeakReference位图缓存，但是这不是推荐的。
        //从Android 2.3（API Level 9）开始，垃圾收集器收集软/弱引用更为积极，这使得它们相当无效。
        // 此外，在Android 3.0（API 11级）之前，位图的后台数据存储在本机内存中，该内存不会以可预测的方式释放，可能导致应用程序短暂超出其内存限制并崩溃。
        //内存缓存对加速访问最近浏览的位图非常有用，但是您无法依赖此缓存中可用的映像。
        // 具有较大数据集的GridView等组件可以轻松填满内存缓存。
        // 您的应用程序可能会被另一个任务（如电话）中断，而在后台可能会被杀死并且内存缓存被破坏。
        // 一旦用户恢复，您的应用程序必须再次处理每个图像。

        //DiskLruCache
        //在这些情况下，可以使用磁盘缓存来持久处理位图，并有助于减少内存缓存中图像不再可用的加载时间。
        // 当然，从磁盘获取图像比从内存加载速度慢，应该在后台线程中完成，因为磁盘读取时间是不可预知的。
        Log.d("TAG", "DiskLruCache: "+ getDiskCacheDir("cacheDir").getAbsolutePath());

        // HashMap
        //给定一个键和一个值，您可以将值存储在Map对象中。存储该值后，可以使用其键来检索该值。
        //调用映射中没有任何项目时，有几种方法会抛出NoSuchElementException异常。
        //当对象与地图中的元素不兼容时，会抛出ClassCastException。
        //如果尝试使用空对象并且映射中不允许使用null，则会抛出NullPointerException异常
        //尝试更改不可修改的地图时，会抛出UnsupportedOperationException异常。
        Map m1 = new HashMap();
        m1.put("Zara", "8");
        m1.put("Mahnaz", "31");
        m1.put("Ayan", "12");
        m1.put("Daisy", "14");
        Log.d("TAG"," HashMap Elements");
        Log.d("TAG","\t" + m1);
        //{Daisy=14, Mahnaz=31, Zara=8, Ayan=12} 遍历结果是无序的 不是元素插入位置的顺序


        ////类似HashMap，但可记住元素插入位置的顺序，也可以设定成依照元素上次存取的先后来排序。
        LinkedHashMap<String,Bitmap> linkedHashMap = new LinkedHashMap<>();
        LinkedHashMap lhm = new LinkedHashMap();

        // Put elements to the map
        lhm.put("Zara", new Double(3434.34));
        lhm.put("Mahnaz", new Double(123.22));
        lhm.put("Ayan", new Double(1378.00));
        lhm.put("Daisy", new Double(99.22));
        lhm.put("Qadir", new Double(-19.08));

        Log.d("TAG"," LinkedHashMap Elements");
        Log.d("TAG","\t" + lhm);

        //	{Zara=3434.34, Mahnaz=123.22, Ayan=1378.0, Daisy=99.22, Qadir=-19.08}

        //  元素插入位置的顺序
        // Get a set of the entries
        Set set = lhm.entrySet();
        // Get an iterator
        Iterator i = set.iterator();
        // Display elements
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            Log.d("TAG", me.getKey() + ": "+me.getValue()+"");

            //Zara: 3434.34
            // Mahnaz: 123.22
            //Ayan: 1378.0
            //Daisy: 99.22
            //Qadir: -19.08
        }
        System.out.println();
        // Deposit 1000 into Zara's account
        double balance = ((Double)lhm.get("Zara")).doubleValue();
        lhm.put("Zara", new Double(balance + 1000));

        Log.d("TAG", "Zara's new balance: " + lhm.get("Zara"));
        // Zara's new balance: 4434.34 改变value值

        //Vertor 实现一个动态数组。它类似于ArrayList，但有两个区别 -
        // 1.Vector  是同步的
        // 2.Vector  包含许多不是集合框架一部分的传统方法
        //如果您不提前知道数组的大小，或者您只需要一个可以在程序生命周期内更改大小的内容，则Vertor证明是非常有用的。
        Vector<String> vector = new Vector<>();
        //Vector<>()
        // 该构造函数创建一个初始大小为10的默认向量。
        //Vector（int size）
        // 该构造函数接受等于所需大小的参数，并创建一个向量，其初始容量由大小指定。
        //Vector（int size，int incr）
        //该构造函数创建一个向量，其初始容量由大小指定，其增量由incr指定。该增量指定每次向量向上调整向量时要分配的元素数。

        //Vector ()
        //除了继承自父类的方法之外，Vector定义了以下方法
        //void add（int index，Object element）
        //在此Vector中的指定位置插入指定的元素。
        //boolean add（Object o）
        //将指定的元素追加到此Vector的末尾。
        //boolean addAll（Collection c）
        //将指定集合中的所有元素追加到该向量的末尾，按照它们由指定集合的​​迭代器返回的顺序。
        //boolean addAll（int index，Collection c）
        //将指定集合中的所有元素插入到此向量中的指定位置
        //void addElement（Object obj）
        //将指定的组件添加到此向量的末尾，将其大小增加1。
        //int capacity（）
        //返回此向量的当前容量。
        //void clear（）
        //从此向量中删除所有元素。
        //Object clone()
        //返回此向量的克隆。
        //boolean contains（Object elem）
        //测试指定对象是否是此向量中的组件。
        //void copyInto（Object [] anArray）
        //将此向量的组件复制到指定的数组中。
        // initial size is 3, increment is 2
        Vector v = new Vector(3, 2);
        Log.d("TAG", "Initial size: " + v.size());
        //Initial size: 0
        //capacity
        //返回此向量的当前容量。
        Log.d("TAG", "Initial capacity: " + v.capacity());
        //Initial capacity: 3

        v.addElement(new Integer(1));
        v.addElement(new Integer(2));
        v.addElement(new Integer(3));
        v.addElement(new Integer(4));
        //capacity
        //返回此向量的当前容量。
        Log.d("TAG", "Capacity after four additions: " + v.capacity());
        //Capacity after four additions: 5

        v.addElement(new Double(5.45));
        //capacity
        //返回此向量的当前容量。
        Log.d("TAG", "Current capacity: " + v.capacity());
        // Current capacity: 5

        v.addElement(new Double(6.08));
        v.addElement(new Integer(7));
        //capacity
        //返回此向量的当前容量。
        Log.d("TAG", "Current capacity: " + v.capacity());
        //Current capacity: 7

        v.addElement(new Float(9.4));
        v.addElement(new Integer(10));
        Log.d("TAG", "Current capacity: " + v.capacity());
        //Current capacity: 9

        v.addElement(new Integer(11));
        v.addElement(new Integer(12));
        //Object firstElement()
        //返回此向量的第一个组件（索引为0的项目）。
        Log.d("TAG", "First element: " + (Integer)v.firstElement());
        //First element: 1

        //Object lastElement() 返回向量的最后一个组件。
        Log.d("TAG", "Last element: " + (Integer)v.lastElement());
        //Last element: 12

        //boolean contains（Object elem）
        //测试指定对象是否是此向量中的组件。
        if(v.contains(new Integer(3)))
            Log.d("TAG", "Vector contains 3.");
            //Vector contains 3.

        // enumerate the elements in the vector.
        Enumeration vEnum = v.elements();
        Log.d("TAG", "\nElements in vector:");
        //Elements in vector:

        while(vEnum.hasMoreElements())
            Log.d("TAG", vEnum.nextElement() + " ");

        //1
        //2
        //3
        //4
        // 5.45
        // 6.08
        //7
        //9.4
        //10
        //1 1
        //12
        System.out.println();



        //Stack是Vector的一个子类，它实现了一个标准的后进先出的堆栈。
        //堆栈只定义默认构造函数，它创建一个空堆栈。堆栈包括由Vector定义的所有方法，并添加了自己的几个。

        Stack shack = new Stack();
        // Stack( )
        //除了继承自父类Vector的方法之外，Stack定义了以下方法
        //boolean empty（）
        //测试此堆栈是否为空。如果堆栈为空，则返回true，如果堆栈包含元素，则返回false。
        //Object peek( )
        //返回堆栈顶部的元素，但不会将其删除。
        //Object pop( )
        //返回堆栈顶部的元素，将其删除。
        //int search(Object element)
        //搜索堆栈中的元素。如果找到，则返回其与堆栈顶部的偏移量。否则返回.1。
        Stack st = new Stack();
        Log.d("TAG", "stack: " + st);
        //stack: []
        showpush(st, 42);
        //push(42)  tack: [42]
        showpush(st, 66);
        //push(66)   stack: [42, 66]
        showpush(st, 99);
        //push(99)  stack: [42, 66, 99]
        showpop(st);
        //99  stack: [42, 66]
        showpop(st);
        //66 stack: [42]
        showpop(st);
        //42 stack: []
        try {
            showpop(st);
        }catch (EmptyStackException e) {
            Log.d("TAG", "empty stack");
            //empty stack
        }


        //Dictionary
        //是一个抽象类，表示一个键/值存储库，操作非常像Map。
        //给定键和值，您可以将值存储在Dictionary对象中。
        // 一旦存储该值，您可以使用其键来检索该值。
        // 因此，像map一样，Dictionary可以被认为是键/值对的列表。
        //Dictionary所定义的抽象方法如下：
        //Enumeration elements( )
        //返回字典中包含的值的枚举。
        //Object get（Object key）
        //返回包含与该关键字关联的值的对象。如果该键不在字典中，则返回一个空对象。
        //boolean isEmpty（）
        //如果字典为空，则返回true，如果包含至少一个键，则返回false。
        //Enumeration keys( )
        //返回Dictionary中包含的键的枚举。
        //Object put（Object key，Object value）
        //在Dictionary中插入一个键及其值。如果键不在Dictionary中则返回null; 如果键已经在Dictionary中，则返回与键相关联的上一个值。
        //Object remove(Object key)
        //删除密钥及其值。返回与该关键字关联的值。如果键不在Dictionary中，则返回null。
        //int size（）
        //返回Dictionary中的条目数。

        //Hashtable是原始java.util的一部分，是Dictionary的具体实现。
        // 然而，Java 2重新设计了Hashtable，以便它也实现了Map接口。
        // 因此，Hashtable现在被集成到集合框架中。它类似于HashMap，但是是同步的。
        // 像HashMap一样，Hashtable将密钥/值对存储在哈希表中。
        // 使用Hashtable时，可以指定用作键的对象，以及要链接到该键的值。
        // 然后将密钥哈希，并将生成的哈希码用作值存储在表中的索引。
        // 以下是HashTable类提供的构造函数的列表。

        //Hashtable（int size）
       // 此构造函数接受一个整数参数，并创建一个具有由整数值大小指定的初始大小的哈希表。
        //Hashtable（int size，float fillRatio）
       //这将创建一个哈希表，其具有由大小指定的初始大小和fillRatio指定的填充比率。此比率必须介于0.0和1.0之间，并确定哈希表在向上调整大小之前的完整度。
        //Hashtable（Map <？extends K，？extends V> t）
        //这将构造一个具有给定映射的Hashtable。
        //void clear（）
        //重置和清空哈希表。
        //Object clone( )
        //返回调用对象的副本。
        //boolean contains（Object value）
        //如果某个值等于该值存在于哈希表中，则返回true。如果找不到值，则返回false。
        //boolean containsKey（Object key）
        //如果哈希表中存在与密钥相等的密钥，则返回true。如果找不到键，则返回false。
        //boolean containsValue（Object value）
        //如果某个值等于该值存在于哈希表中，则返回true。如果找不到值，则返回false。
        //Enumeration elements( )
        //返回哈希表中包含的值的枚举。
        //Object get（Object key）
        //返回包含与该关键字关联的值的对象。如果密钥不在哈希表中，则返回一个空对象
        //boolean isEmpty（）
        //如果散列表为空，则返回true; 如果至少包含一个密钥，则返回false。
        //Enumeration keys( )
        //在哈希表中插入一个键和一个值。如果密钥尚未在哈希表中返回null; 如果密钥已经在哈希表中，则返回与密钥相关联的上一个值。
        //void rehash（）
        //增加哈希表的大小，并重新显示所有的键。
        //Object remove(Object key)
        //删除密钥及其值。返回与该关键字关联的值。如果密钥不在哈希表中，则返回一个空对象。
        //int size（）
        //返回哈希表中的条目数。
        //String toString（）
        //返回等价于哈希表的字符串。


        // Create a hash map
        Hashtable hashtable = new Hashtable();
        Enumeration names;
        String str;
        double bal;

        hashtable.put("Zara", new Double(3434.34));
        hashtable.put("Mahnaz", new Double(123.22));
        hashtable.put("Ayan", new Double(1378.00));
        hashtable.put("Daisy", new Double(99.22));
        hashtable.put("Qadir", new Double(-19.08));
        Log.d("TAG", "hashtable: "+hashtable);
        //{Daisy=99.22, Mahnaz=123.22, Qadir=-19.08, Zara=3434.34, Ayan=1378.0}
        // Show all balances in hash table.
        names = hashtable.keys();

        while(names.hasMoreElements()) {
            str = (String) names.nextElement();
            Log.d("TAG", str + ": " + hashtable.get(str));
            //Daisy: 99.22
            //Mahnaz: 123.22
            //Qadir: -19.08
            //Zara: 3434.34
            //Ayan: 1378.0
        }
        System.out.println();

        // Deposit 1,000 into Zara's account
        bal = ((Double)hashtable.get("Zara")).doubleValue();
        hashtable.put("Zara", new Double(bal + 1000));
        Log.d("TAG", "Zara's new balance: " + hashtable.get("Zara"));
        //Zara's new balance: 4434.34



        //队列先进先出 MessageQueue
        //小明买完东西然后给钱出来 小红才可以进来
//        ClassLoader loader = new NetworkClassLoader(host, port);
//        Object main = loader.loadClass("Main", true).newInstance();

        ImageView imageView = (ImageView) findViewById(R.id.image_view);

        Picasso.with(this).load("http:\\/\\/img.test.cnrmobile.com\\/artist\\/f04\\/924\\/58a2a426cb6cc.jpg")
                .into(imageView);
        arrayList.add(new Song("Longs","14255"));
        arrayList.add(new Song("Ebeert","14433"));
        arrayList.add(new Song("Maroon","14153"));
        arrayList.add(new Song("Maroon","14153"));
        arrayList.add(new Song("Maroon","14153"));
        arrayList.add(new Song("castle","14156"));
        Log.d("TAG", "onCreate: "+arrayList);

        Glide.with(this).load("http:\\/\\/img.test.cnrmobile.com\\/artist\\/f04\\/924\\/58a2a426cb6cc.jpg")
            .into(imageView);

//        ArtistCompare ar = new ArtistCompare();
//        Collections.sort(arrayList,ar);
        Collections.sort(arrayList);
//        HashSet<Song> songset = new HashSet<>();
//        songset.addAll(arrayList);

        TreeSet<Song> songset = new TreeSet<>();
        songset.addAll(arrayList);
        Log.d("TAG", "onCreate: "+songset);

        // ArrayList
        //List接口扩展Collection并声明存储一系列元素的集合的行为。
        //元素可以通过其在列表中的位置使用基于零的索引进行插入或访问。
        //如果集合无法修改，则列表方法中的几个将抛出UnsupportedOperationException异常，并且当一个对象与另一个对象不兼容时会生成ClassCastException。
        List a1 = new ArrayList();
        a1.add("Zara");
        a1.add("Mahnaz");
        a1.add("Ayan");
        Log.d("TAG", " ArrayList Elements");
        Log.d("TAG", "\t" + a1);
        //[Zara, Mahnaz, Ayan]

        // LinkedList
        List l1 = new LinkedList();
        l1.add("Zara");
        l1.add("Mahnaz");
        l1.add("Ayan");
        System.out.println();
        Log.d("TAG"," LinkedList Elements");
        Log.d("TAG","\t" + l1);
        //[Zara, Mahnaz, Ayan]

        // HashSet
        Set s1 = new HashSet();
        s1.add("Zara");
        s1.add("Mahnaz");
        s1.add("Ayan");
        s1.add("Ayan");
        System.out.println();
        Log.d("TAG"," HashSet Elements");
        Log.d("TAG","\t" + s1);
        //[Mahnaz, Zara, Ayan]
        // HashMap


        //集合是不能包含重复元素的集合
        //Set界面只包含从Collection继承的方法，并添加禁止重复元素的限制。
        //设置还会对equals和hashCode操作的行为增加更强的契约，即使实例类型不同，也可以有意义地比较Set实例。
        int count[] = {34, 22,10,60,30,22,22};
        Set<Integer> hashset = new HashSet<Integer>();
        try {
            for(int j = 0; j < 6; j++) {
                hashset.add(count[j]);
            }
            Log.d("TAG",hashset+"");
            //[60, 10, 30, 22, 34]

            TreeSet sortedSet = new TreeSet<Integer>(hashset);
            Log.d("TAG","The sorted list is:");
            Log.d("TAG",""+sortedSet);
            //[10, 22, 30, 34, 60]

            Log.d("TAG","The First element of the set is: "+ (Integer)sortedSet.first());
            //The First element of the set is: 10
            Log.d("TAG","The last element of the set is: "+ (Integer)sortedSet.last());
            //The last element of the set is: 60
        }
        catch(Exception e) {
        }

        //SortedSet接口扩展了Set并声明了按升序排列的集合的行为
        //当调用集中没有任何项目时，有几种方法抛出NoSuchElementException异常。当对象与集合中的元素不兼容时，将抛出ClassCastException。
         //如果尝试使用空对象并且不允许在集合中使用null，则抛出NullPointerException。
        // Create the sorted set
        SortedSet sortedset = new TreeSet();
        // Add elements to the set
        sortedset.add("b");
        sortedset.add("c");
        sortedset.add("a");
        sortedset.add("a");
        // Iterating over the elements in the set
        Iterator it = sortedset.iterator();
        while (it.hasNext()) {
            // Get element
            Object element = it.next();
            Log.d("TAG",element.toString());
        }


        //Map.Entry interface 使您能够使用Map条目。
        //Map接口声明的entrySet（）方法返回一个包含映射条目的集合。这些集合元素中的每一个都是Map.Entry对象。
        // Create a hash map
        HashMap hm = new HashMap();

        // Put elements to the map
        hm.put("Zara", new Double(3434.34));
        hm.put("Mahnaz", new Double(123.22));
        hm.put("Ayan", new Double(1378.00));
        hm.put("Daisy", new Double(99.22));
        hm.put("Qadir", new Double(-19.08));

        // Get a set of the entries
        Set entriesset = hm.entrySet();

        // Get an iterator
        Iterator e = entriesset.iterator();

        // Display elements
        while(e.hasNext()) {
            Map.Entry me = (Map.Entry)e.next();
            System.out.print(me.getKey() + ": ");
            System.out.println(me.getValue());
        }
        System.out.println();

        // Deposit 1000 into Zara's account
        double balan = ((Double)hm.get("Zara")).doubleValue();
        hm.put("Zara", new Double(balan + 1000));
        System.out.println("Zara's new balance: " + hm.get("Zara"));



        // Create arrays of Integer, Double and Character
        Integer[] intArray = { 1, 2, 3, 4, 5 };
        Double[] doubleArray = { 1.1, 2.2, 3.3, 4.4 };
        Character[] charArray = { 'H', 'E', 'L', 'L', 'O' };

        System.out.println("Array integerArray contains:");
        printArray(intArray);   // pass an Integer array

        System.out.println("\nArray doubleArray contains:");
        printArray(doubleArray);   // pass a Double array

        System.out.println("\nArray characterArray contains:");
        printArray(charArray);   // pass a Character array

    }

    // generic method printArray
    public static < E > void printArray( E[] inputArray ) {
        // Display array elements
        for(E element : inputArray) {
            System.out.printf("%s ", element);
        }
        System.out.println();
    }

    //ArrayList在数组上的优点是什么？
    //ArrayList可以动态增长，并提供比数组更强大的插入和搜索机制。
    //为什么在LinkedList中删除比ArrayList快？
    //Linked中的删除很快，因为它仅涉及在删除的节点之前更新节点中的下一个指针，并在删除的节点之后更新节点中的先前指针。

    //如何决定何时使用ArrayList和LinkedList？
    //如果需要从列表中间频繁地添加和删除元素，并且只能顺序地访问列表元素，那么应该使用LinkedList。
    // 如果您需要支持随机访问，而不从任何位置插入或删除元素，则应使用ArrayList。

    //>>和>>>运算符有什么区别？
    //>>操作符向右移动时携带符号位。>>>零填充已被移出的位。

    //为什么在Java中使用泛型？
    //泛型提供编译时类型的安全性，允许程序员在编译时捕获无效类型。
    // Java泛型方法和通用类使程序员可以使用单个方法声明来指定一组相关方法，或者使用单个类声明来指定一组相关类型。

    //什么是守护线程？
    //守护线程是一个低优先级的线程，它在后台执行间歇性运行，为Java运行时系统进行垃圾回收操作。

    //使用哪种方法来创建守护进程线程？
    //setDaemon方法用于创建守护进程线程。

    //Android中的ANR是什么？
    //ANR代表应用程序没有响应，基本上是一个对话框，当应用程序没有响应时出现。

    //为什么你不能在Android上运行java字节码？
    //Android使用DVM（Dalvik虚拟机）而不是使用JVM（Java虚拟机），如果需要，我们可以访问.jar文件作为库。

    //android如何跟踪进程中的应用程序？
    //Android为所有应用程序提供唯一的ID，称为Linux ID，此ID用于跟踪每个应用程序。

    //什么是android中的睡眠模式？
    //休眠模式意味着CPU将处于睡眠状态，并且不接受来自Android设备的任何命令，除了Radio接口层和闹钟。

    //哪个内核在android中使用？
    //Android是定制的Linux 3.6内核。

    //
    static void showpush(Stack st, int a) {
        st.push(new Integer(a));
        Log.d("TAG", "push(" + a + ")");
        Log.d("TAG", "stack: " + st);
    }

    static void showpop(Stack st) {
        Log.d("TAG", "pop -> ");
        Integer a = (Integer) st.pop();
        Log.d("TAG", a+"");
        Log.d("TAG", "stack: " + st);
    }


    public File getDiskCacheDir(String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? getExternalCacheDir().getPath() :
                        getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }



    class RyanAndMonicaJob implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                incrrement();
                Log.d("TAG",Thread.currentThread().getName()+ " "+balance);

            }
        }
    }
    private synchronized void incrrement(){
        int i = balance;
        balance = i + 1;
    }
}

//        @Override
//        public void run() {
//            for (int x = 0; x < 10 ; x++){
//                makeWithdrawal(10);
//                if (account.getBalance() < 0){
//                    Log.d("TAG", " 透支!");
//                }
//            }
//        }
//    }
//    private void makeWithdrawal(int amount){
//        if (account.getBalance() >= amount){
//            Log.d("TAG",Thread.currentThread().getName()+ " 即将退出");
//
//            try{
//                Thread.sleep(500);
//            }catch (InterruptedException ex){
//                ex.printStackTrace();
//            }
//            Log.d("TAG",Thread.currentThread().getName()+ " 醒来");
//            account.withdraw(amount);
//            Log.d("TAG",Thread.currentThread().getName()+ " 完成退出");
//        }else {
//            Log.d("TAG", "抱歉不够 "+Thread.currentThread().getName());
//        }
//    }


// Creates a unique subdirectory of the designated app cache directory. Tries to use external
// but if not mounted, falls back on internal storage.
