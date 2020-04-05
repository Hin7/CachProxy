package ru.sbt.course.cacheproxy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProxyTester {
    public static void main(String[] args){
        ProxyTester pt = new ProxyTester();
        //pt.TestWithoutProxy();

        //pt.TestCachAtom();

        //pt.TestCachArray();

        pt.TestWithProxy();
    }

    public void TestWithoutProxy(){
        System.out.println("Тест без прокси");
        Service srv = new ServiceImpl();
        System.out.println("Work1 3.0" + " = " + srv.doWork1(3.0));
        Date currDate = new Date(System.currentTimeMillis());
        List<String> list = srv.doWork2("Test", 5.0d, currDate);
        System.out.println("Work2 = " + list);
        list = srv.doWork3("One Two Three Four Five Six");
        System.out.println("Work3 = " + list);
        System.out.println("Work4 2.0 = " + srv.doWork4(2.0));
    }

    public void TestCachAtom(){
        System.out.println("Тест ru.sbt.course.cacheproxy.CachAtom");
        Object[] tstObj = new Object[3];
        tstObj[0] = 10;
        tstObj[1] = "Test string";
        tstObj[2] = 0.5d;
        CachAtom cachAtom = new CachAtom();
        cachAtom.set(tstObj, 888);
        System.out.println("Сравнение = " + cachAtom.equals(tstObj) + ", результат = " + cachAtom.getResult());
        tstObj[0] = 20;
        System.out.println("Сравнение = " + cachAtom.equals(tstObj) + ", результат = " + cachAtom.getResult());
    }

    public void TestCachArray(){
        System.out.println("Тест ru.sbt.course.cacheproxy.CachArray");
        Object[] tstObj = new Object[3];
        tstObj[0] = 10;
        tstObj[1] = "Test string";
        tstObj[2] = 0.5d;
        CachArray cachArray = new CachArray(3);
        System.out.println("Пустой getResult = " + cachArray.getResult(tstObj));
        cachArray.add(tstObj, 10);
        System.out.println("Должен 10 getResult = " + cachArray.getResult(tstObj));
        tstObj[0] = "Second";
        cachArray.add(tstObj, 20);
        System.out.println("Должен 20 getResult = " + cachArray.getResult(tstObj));
        tstObj[0] = 20;
        System.out.println("Должен 10 getResult = " + cachArray.getResult(tstObj));
        List<String> tstList = new ArrayList<>();
        tstList.add("One");
        tstList.add("Two");
        tstList.add("Three");
        tstObj[0] = 20.1d;
        cachArray.add(tstObj, tstList);
        System.out.println("Должен List getResult = " + cachArray.getResult(tstObj));
        tstObj[0] = 20;
        cachArray.add(tstObj, 30);
        System.out.println("Должен 30 getResult = " + cachArray.getResult(tstObj));
        tstObj[0] = "Second";
        System.out.println("Должен 20 getResult = " + cachArray.getResult(tstObj));
        tstObj[0] = 100;
        System.out.println("Должен null getResult = " + cachArray.getResult(tstObj));
    }

    public void TestWithProxy(){
        System.out.println("Тест прокси класса");
        Service srv = new ServiceImpl();
        CacheProxy cacheProxy = new CacheProxy("C:\\Temp");
        srv = cacheProxy.cache(srv);

        System.out.println("Work1 3.0" + " = " + srv.doWork1(3.0));
        System.out.println("Work1 2.0" + " = " + srv.doWork1(2.0));
        System.out.println("Work1 4.0" + " = " + srv.doWork1(4.0));
        System.out.println("Work1 3.0" + " = " + srv.doWork1(3.0));
        System.out.println("Work1 4.0" + " = " + srv.doWork1(4.0));

        Date currDate = new Date(System.currentTimeMillis());
        List<String> list = srv.doWork2("Test", 5.0d, currDate);
        System.out.println("Work2 = " + list);
        currDate.setTime(System.currentTimeMillis()-100000);
        System.out.println("Work2 = " + srv.doWork2("Test", 3.0d, currDate));
        currDate.setTime(System.currentTimeMillis()-300000);
        System.out.println("Work2 = " + srv.doWork2("Test", 5.0d, currDate));

        list = srv.doWork3("One Two Three Four Five Six");
        System.out.println("Work3 = " + list);
        System.out.println("Work3 = " + srv.doWork3("One Two Three Four Five Six"));

        System.out.println("Work4 2.0 = " + srv.doWork4(2.0));
        System.out.println("Work4 2.0 = " + srv.doWork4(2.0));
    }
}
