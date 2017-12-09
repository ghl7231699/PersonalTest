package com.ghl.init

import org.gradle.api.Plugin
import org.gradle.api.Project

class DelLogPlugin implements Plugin<Project> {

    //有两类可以在生成脚本中声明的变量： 局部变量和额外属性
    /**
     *
     * a.局部变量
     * 在groovy 中，没有固定的类型，局部变量是用def关键字声明的。它们只在定义它们的范围内可以被访问
     */
    def name = 'ghl'
    //    变量定义时，也可以直接指定类型
    def int x = 1
    //    单引号与双引号的区别
    //单引号中字符串仅仅是字符串，即Java中String
    def name1 = 'ghl'
    //双引号可以进行转义，ghl，因此name2的值为Tony，ghl
    def name2 = "Tony ，$name1"
    def list = [1, 2, 3, 4, 5]
    def list3 = ['a', 1, 'a', 'a', 2.5, 2.5f, 2.5d, 'hello', 7g, null]

    def map = ['key1': '你是猪吗', 'key2': true]

    def range = 1..5
    def Rangerange = 5..<10

    def myCourse = { String s ->
        println(s)
    }

    @Override
    void apply(Project project) {
        System.out.println("=================================================")
        System.out.println("this is DelLogPlugin，my second Gradle Plugin")
        System.out.println("=================================================")

        System.out.println("name==" + name)
        System.out.println("name2==" + name2)
        def num = getName(2)
        System.out.println("num==" + num + "我的哈希值是" + num.hashCode())

        System.out.println("List--------------->" + list)
        System.out.println("list的第二值为------->" + list[1])
        list3.each {
            println it
        }
        System.out.println("List3等于--------------->" + list3)


        System.out.println("map第一个值为--------------->" + map.key1)
        System.out.println("map第二个值为--------------->" + map.key2)


        System.out.println("range--------------->" + range)
        System.out.println("Rangerange--------------->" + Rangerange)


        System.out.println("myCourse--------------->" + myCourse("hello"))

    }
    //无类型函数
    def getName(def num) {
        num * num
    }
}