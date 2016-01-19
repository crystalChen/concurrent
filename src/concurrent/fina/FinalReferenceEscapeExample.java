package concurrent.fina;

/**
 * 写final域的重排序规则可以确保：
 * 在引用变量为任意线程可见之前，该引用变量指向的对象的final域已经在构造函数中被正确初始化过了。
 * 其实要得到这个效果，还需要一个保证：在构造函数内部，不能让这个被构造对象的引用为其他线程可见，
 * 也就是对象引用不能在构造函数中“逸出”。
 *
 * FinalReferenceEscapeExample
 * 问题：i为设值，this由obj逃逸，被其他线程使用，导致obj.i出错。
 * 正常情况下是，构造函数执行完毕才返回this引用，而对final的设值必定比构造函数执行完毕先执行。
 * @author crystalChen
 * @date 16/1/19 16:07
 */
public class FinalReferenceEscapeExample {
    final int i;
    static FinalReferenceEscapeExample obj;

    public FinalReferenceEscapeExample () {
        i = 1;                               //1 写final域
        obj = this;                          //2 this引用在此“逸出”
    }

    public static void writer() {
        new FinalReferenceEscapeExample ();
    }

    public static void reader() {
        if (obj != null) {                    //3
            int temp = obj.i;                 //4
        }
    }
}
