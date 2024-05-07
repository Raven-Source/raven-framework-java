package org.raven.commons.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializableTypeArrayUtilsTest {

    public static void main(String[] args) {

        // 创建一个包含10000个元素的List
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            list.add(i);
        }

        // 创建一个包含10000个键值对的Map
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 500; i++) {
            map.put(i, i);
        }

        // 在List中查找一个特定的元素
        long startTime = System.nanoTime();
        int index = list.indexOf(250);
        long endTime = System.nanoTime();
        long durationList = endTime - startTime;
        System.out.println("List查找耗时：" + durationList + " 纳秒");

        // 在Map中查找一个特定的键
        startTime = System.nanoTime();
        int value = map.get(499);
        endTime = System.nanoTime();
        long durationMap = endTime - startTime;
        System.out.println("Map查找耗时：" + durationMap + " 纳秒");

    }

}
