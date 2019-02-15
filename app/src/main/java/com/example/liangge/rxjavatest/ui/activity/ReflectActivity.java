package com.example.liangge.rxjavatest.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liangge.rxjavatest.R;
import com.example.liangge.rxjavatest.bean.Fruit;
import com.example.liangge.rxjavatest.common.utils.LogUtil;
import com.example.liangge.rxjavatest.di.component.AppComponent;
import com.example.liangge.rxjavatest.ui.activity.baseactivity.BaseActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by guhongliang on 2018/2/22.
 */

public class ReflectActivity extends BaseActivity implements View.OnClickListener {
    private Button constructor, field, method, type;
    private TextView content_cons, content_field, content_method, content_type;
    private Class mFruit, man;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reflect;
    }

    @Override
    public void initView() {
        constructor = (Button) findViewById(R.id.reflect_construct);
        field = (Button) findViewById(R.id.reflect_filed);
        method = (Button) findViewById(R.id.reflect_method);
        type = (Button) findViewById(R.id.reflect_type);
        content_cons = (TextView) findViewById(R.id.reflect_content_construct);
        content_field = (TextView) findViewById(R.id.reflect_content_field);
        content_method = (TextView) findViewById(R.id.reflect_content_method);
        content_type = (TextView) findViewById(R.id.reflect_content_type);

        constructor.setOnClickListener(this);
        field.setOnClickListener(this);
        method.setOnClickListener(this);
        type.setOnClickListener(this);

    }

    @Override
    public void initData() {
        try {
            mFruit = Class.forName("com.example.liangge.rxjavatest.bean.Fruit");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtil.d("Reflect Error", "Load failed");
        }
    }

    @Override
    public void setUpComponent(AppComponent appComponent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reflect_construct:
                getClasses();
                getOthers();
                break;
            case R.id.reflect_filed:
                getFields();
                break;
            case R.id.reflect_method:
                getMethod();
                break;
            case R.id.reflect_type:
                setType();
                break;
        }
    }

    /**
     * 获取Class对象的三种方式
     * 1 Object ——> getClass();
     * 2 任何数据类型（包括基本数据类型）都有一个“静态”的class属性
     * 3 通过Class类的静态方法：forName（String  className）(常用)
     */
    private void getClasses() {
        //1
        Fruit fruit = new Fruit();//这一new 产生一个Student对象，一个Class对象
        Class fruitClass = fruit.getClass();
        LogUtil.d("Reflect1", fruitClass.getName());
        //2
        LogUtil.d("Reflect2", Fruit.class.toString());
        LogUtil.d("Reflect2", "result==" + (fruitClass == Fruit.class));//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个
        //3
        try {
            Class fruit3 = Class.forName("com.example.liangge.rxjavatest.bean.Fruit");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            LogUtil.d("Reflect3", fruit3.getName());
            LogUtil.d("Reflect3", "result==" + (fruitClass == fruit3));//判断第三种方式获取的Class对象和第二种方式获取的是否是同一个
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtil.d("Reflect3", "Load failed");
        }
    }

    /**
     * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
     * <p>
     * 1.获取构造方法：
     * 1).批量的方法：
     * public Constructor[] getConstructors()：所有"公有的"构造方法
     * public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)
     * <p>
     * 2).获取单个的方法，并调用：
     * public Constructor getConstructor(Class... parameterTypes):获取单个的"公有的"构造方法：
     * public Constructor getDeclaredConstructor(Class... parameterTypes):获取"某个构造方法"可以是私有的，或受保护、默认、公有；
     * <p>
     * 调用构造方法：
     * Constructor-->newInstance(Object... initargs)
     */
    private void getOthers() {

        //获取所有公有构造方法
        Constructor[] constructors = mFruit.getConstructors();
        System.out.println("**********************所有公有构造方法*********************************");
        for (Constructor constructor : constructors) {
            LogUtil.d("Reflect4", "Constructor is " +
                    constructor);
        }
        System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)***************");
        Constructor[] declaredConstructors = mFruit.getDeclaredConstructors();
        try {
            Class[] classes = new Class[1];
            classes[0] = Integer.class;
            Constructor declaredConstructor = mFruit.getDeclaredConstructor(String.class);
//            Constructor declaredConstructor = mFruit.getDeclaredConstructor();
            LogUtil.d("Reflect5", "Specified Constructor is " +
                    declaredConstructor);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LogUtil.d("Reflect5", e.getMessage());
        }
        for (Constructor c : declaredConstructors) {
            LogUtil.d("Reflect6", "All Constructor is " +
                    c);
        }
        System.out.println("*****************获取公有、无参的构造方法*******************************");
        try {
            Constructor constructor = mFruit.getConstructor((Class<?>) null);
            LogUtil.d("Reflect7", "No param Constructor is " +
                    constructor);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            LogUtil.d("Reflect7", e.getMessage());
        }
        System.out.println("******************获取私有构造方法，并调用*******************************");
    }

    /**
     * 获取成员变量并调用：
     * <p>
     * 1.批量的
     * 1).Field[] getFields():获取所有的"公有字段"
     * 2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
     * 2.获取单个的：
     * 1).public Field getField(String fieldName):获取某个"公有的"字段；
     * 2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
     * <p>
     * 设置字段的值：
     * Field --> public void set(Object obj,Object value):
     * 参数说明：
     * 1.obj:要设置的字段所在的对象；
     * 2.value:要为字段设置的值；
     */
    private void getFields() {
        try {
            System.out.println("************获取所有公有的字段********************");
            //获取所有公有的字段
            Field[] fields = mFruit.getFields();
            for (Field f : fields
                    ) {
                LogUtil.d("ReflectField", "All Public Fields are " + f + "\ttype==\t" + f
                        .getType());
            }
            System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
            //包括私有
            Field[] declaredFields = mFruit.getDeclaredFields();
            LogUtil.d("ReflectField", "All Fields are " + Arrays.toString(declaredFields));
            for (Field f : declaredFields
                    ) {
                LogUtil.d("ReflectField", "All Public Fields are " + f + "\ttype==\t" + f
                        .getType());
            }
            System.out.println("*************获取公有字段**并调用***********************************");
            //
            Field url = mFruit.getField("url");
            LogUtil.d("ReflectField", "Url is " + url);
            //获取一个对象
            try {
                Object o = mFruit.getConstructor().newInstance();
                url.set(o, "https://www.baidu.com");
                Fruit fruit = (Fruit) o;
                content_field.append(fruit.getUrl());
                System.out.println("**************获取私有字段****并调用********************************");
                //
                Field name = mFruit.getDeclaredField("name");
                if (!TextUtils.isEmpty(content_field.getText())) {
                    content_field.append("\n" + name);
                } else {
                    content_field.append("result == " + name);
                }
                name.setAccessible(true);//暴力反射，解除私有限定
                name.set(o, "辉煌中国");
                content_field.append("\n" + fruit.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取成员方法并调用：
     * <p>
     * 1.批量的：
     * public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
     * public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
     * 2.获取单个的：
     * public Method getMethod(String name,Class<?>... parameterTypes):
     * 参数：
     * name : 方法名；
     * Class ... : 形参的Class类型对象
     * public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
     * <p>
     * 调用方法：
     * Method --> public Object invoke(Object obj,Object... args):
     * 参数说明：
     * obj : 要调用方法的对象；
     * args:调用方式时所传递的实参；
     * <p>
     * ):
     */
    private void getMethod() {
        try {
            man = Class.forName("com.example.liangge.rxjavatest.bean.Man");
            //获取所有的共有方法
            Method[] methods = man.getMethods();
            content_method.append("***************获取所有的”公有“方法*******************\n");
            for (Method method :
                    methods) {
                content_method.append(method.toString() + "\n");
            }
            content_method.append("***************获取所有的方法包括私有*******************\n");
            Method[] declaredMethods = man.getDeclaredMethods();
            for (Method method : declaredMethods
                    ) {
                content_method.append(method.toString() + "\n");
            }
            content_method.append("***************获取指定的”公有“方法*******************\n");
            Method show1 = man.getMethod("show1", String.class);
            content_method.append(show1 + "\n");
            //实例化一个Man对象
            Object instance = man.getConstructor().newInstance();
            show1.invoke(instance, "Kobe Bryant");
            content_method.append("***************获取 私有 的 show4 方法*******************\n");
            Method show4 = man.getDeclaredMethod("show4", int.class);
            show4.setAccessible(true);
            Object invoke = show4.invoke(instance, 10);
            content_method.append(invoke + "\n");
            content_method.append("***************获取 私有 的 show5 方法*******************\n");
            Field name = mFruit.getDeclaredField("name");
            Object o = mFruit.getConstructor().newInstance();
            name.setAccessible(true);
            name.set(o, "Apple");
            Method show5 = man.getDeclaredMethod("show5", TextView.class, Fruit.class, int.class);
            show5.setAccessible(true);
            show5.invoke(instance, content_method, o, 10);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射越过泛型检查
     * <p>
     * 例如：有一个String泛型的集合，怎样能向这个集合中添加一个Integer类型的值？
     */
    private void setType() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            arrayList.add("this number is " + i);
        }
        Class<? extends ArrayList> aClass = arrayList.getClass();
        //获取add()方法
        try {
            Method add = aClass.getMethod("add", Object.class);
            try {
                //调用add()方法
                add.invoke(arrayList, 1000);
                for (Object o :
                        arrayList) {
                    content_type.append(o + "\n");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
