package com.fastcampus.ch3.AopNotionTerms;

import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception {
        MyAdvice myAdvice = new MyAdvice();

        Class myClass = Class.forName("com.fastcampus.ch3.AopNotionTerms.MyClass");
        Object obj = myClass.newInstance();

        for(Method m : myClass.getDeclaredMethods()){ // myClass에 정의된 메소드들을 배열로 얻어와 반복문
            myAdvice.invoke(m, obj, null);
        }
    }
}

class MyAdvice{
    Pattern p = Pattern.compile("a.*"); // a로 시작하는 단어

    boolean matches(Method m) {
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }
    
    void invoke(Method m, Object obj, Object... args) throws Exception {
        //if(matches(m))
        if(m.getAnnotation(Transactional.class)!=null) // 해당 메서드에 Transactional 에너테이션이 붙어있는 것만
            System.out.println("[before]{");

        m.invoke(obj, args); ///aaa(), aaa2(), bbb() 호출가능

        //if(matches(m))
        if(m.getAnnotation(Transactional.class)!=null)
            System.out.println("}[after]");
    }
}

class MyClass{
    @Transactional
    void aaa(){
        System.out.println("aaa() is called");
    }
    void aaa2(){
        System.out.println("aaa2() is called");
    }
    void bbb(){
        System.out.println("bbb() is called");
    }
}