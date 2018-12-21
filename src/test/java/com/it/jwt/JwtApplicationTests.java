package com.it.jwt;

import com.it.jwt.Lambda.test;
import com.it.jwt.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtApplicationTests {

    List<Employee> list = Arrays.asList(
            new Employee("张三", "上海", 5000, 22),
            new Employee("李四", "北京", 4000, 23),
            new Employee("c五", "日本", 6000, 50),
            new Employee("b七", "香港", 7000, 50),
            new Employee("赵六", "纽约", 1000, 8)
    );

    /**
     *需求1：lambda表达式的使用:
     * 调用COllections.sort方法，通过定制排序比较两个Employee（先按年龄比较，年龄相同按姓名比），使用
     * Lambda作为参数传递。
     */
    @Test
    public void test1(){
        Collections.sort(list,(x, y)->{
            if(x.getAge()!=y.getAge())
                return Integer.compare(x.getAge(),y.getAge());
            else
                return x.getName().compareTo(y.getName());

        });

        for (Employee employee : list) {
            System.out.println(employee);
        }
    }

    /**
     * 需求2：
     * 1.声明函数式接口，接口中声明抽象方法，public String getvalue(String str();
     * 2.声明类TestLambda，类中编写方法使用接口作为参数，讲一个字符串转换成大写，并作为方法的返回值。
     */
    @Test
    public void test2(){
        String str = getvalue("hello world", x -> x.toUpperCase());
        System.out.print(str);

    }
    public String getvalue(String str,test.MyFunction1 my){
        return my.getValue(str);
    }

    @FunctionalInterface
    public interface MyFunction1{
        public String getValue(String str);
    }


    /**
     * 需求3：
     * 1.声明一个带两个泛型的函数式接口，泛型类型是<T,R>,T为参数，R为返回值。
     * 2.接口中声明对应抽象方法
     * 3.在TestLambda类中声明方法，使用接口作为参数，计算两个long型参数的和
     * 4.在计算两个long型参数的乘积
     */
    @Test
    public void test3(){
        Long r = getR(25l,30l, (t1,t2) -> t1 * t2);
        System.out.print(r);

        Long r1 = getR(25l, 23l, (t1, t2) -> t1 + t2);
        System.out.print(r1);

    }
    public <T,R> R getR(T t1,T t2,test.MyFUnction2<T,R> mf){
        return mf.method(t1,t2);
    }

    public interface MyFUnction2<T,R>{
        public R method(T t1,T t2);
    }



    @Test
    public void test4(){
        /**
         * 需求1：
         *给定一个数字，如何返回一个有每个数的平方构成的列表？
         * 给定【1,2,3,4,5】，返回【1,4,9,16,25】
         */
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        List<Integer> collect = list.stream().map(integer -> integer * integer).collect((Collectors.toList()));
        System.out.println(collect);

        /**
         * 需求2：
         * 用reduce和map数一数流中的元素个数
         */
        Optional<Integer> reduce = list.stream()
                .map(e -> 1)//巧妙之处
                .reduce(Integer::sum);
        System.out.println(reduce);
    }

}
