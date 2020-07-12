package com.define.domain;

/**
 * JAVA里面对象参数的陷阱
 *
 * @author 老紫竹的家(laozizhu.com)
 */
public class Test {
    public static void main(String[] args) {
        TestValue tv = new TestValue();
        tv.first();
        TestInteger ti = new TestInteger();
        ti.first();
    }
}

class TestValue {
    class Value {
        public int i = 15;
    }

    // 初始化
    Value v = new Value();

    public void first() {
        // 当然是15
        System.out.println(v.i);
        // 第一次调用
        second(v);
        System.out.println(v.i);
        third(v);
        System.out.println(v.i);
    }

    public void second(Value v) {
        // 此时这里的v是一个局部变量
        // 和类属性的v相等
        System.out.println(v == this.v);
        v.i = 20;
    }

    public void third(Value v) {
        // 重新设置一个对象
        v = new Value();
        // 此时这里的v也是一个局部变量
        // 但和类属性的v已经不相等了
        // 修改这个v指向对象的数值，已经不影响类里面的属性v了。
        System.out.println(v == this.v);
        v.i = 25;
    }
}

class TestInteger {
    // 初始化
    Integer v = new Integer(15);

    public void first() {
        // 当然是15
        System.out.println(v);
        // 第一次调用
        second(v);
        System.out.println(v);
        third(v);
        System.out.println(v);
    }

    public void second(Integer v) {
        // 此时这里的v是一个局部变量
        // 和类属性的v相等
        System.out.println(v == this.v);
        // 但这一句和前面的不同，虽然也是给引用赋值，但因为Integer是不可修改的
        // 所以这里会生成一个新的对象。
        v = 20;
        // 当然，他们也不再相等
        System.out.println(v == this.v);
    }

    public void third(Integer v) {
        // 重新设置一个对象
        v = new Integer(25);
        // 此时这里的v也是一个局部变量
        // 但和类属性的v已经不相等了
        // 修改这个v指向对象的数值，已经不影响类里面的属性v了。
        System.out.println(v == this.v);
    }
}