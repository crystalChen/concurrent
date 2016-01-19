package concurrent.fina;

/**
 * final:只能在定义的时初始化值，或者在构造器里面初始化
 * 对于final域，编译器和处理器要遵守两个重排序规则：
 * 在构造函数内对一个final域的写入，与随后把这个被构造对象的引用赋值给一个引用变量，这两个操作之间不能重排序。
 * 初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能重排序。
 *
 *
 * 参考http://www.infoq.com/cn/articles/java-memory-model-6/#anch95652及其评论回复
 * Created by crystalChen on 16/1/15.
 */
public class FinalExample {
    int i;                            //普通变量
    final int j;                      //final变量
    static FinalExample obj;

    public FinalExample () {     //构造函数
        i = 1;                        //写普通域
        j = 2;                        //写final域
    }

    public static void writer () {    //写线程A执行
        obj = new FinalExample ();
    }

    public static void reader () {       //读线程B执行
        FinalExample object = obj;       //读对象引用
        int a = object.i;                //读普通域
        int b = object.j;                //读final域
    }
}
/***
 *       线程A                                线程B
 *
 *   1.构造函数开始执行
 *   2.写final域：j=2
 *   3.StoreStore屏障
 *   4.构造函数执行结束
 *   5.把构造对象的引用赋值给obj
 *                                      6.读对象引用obj
 *                                      7.读对象普通域i
 *                                      8.读对象final域j
 *   9.写普通域：i=1
 *
 *
 */

