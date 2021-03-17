package com.example.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 * @author puyf
 */
public class GroupTest {
    static class Apple {
        public String color;
        public int weight;

        public Apple(String color, int weight) {
            super();
            this.color = color;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Apple [color=" + color + ", weight=" + weight + "]";
        }
    }

    /**
     * @param list
     * @param comparator 比较是否为同一组的比较器
     * @return
     */
    public static <T> List<List<T>> dividerList(List<T> list,Comparator<? super T> comparator) {
        List<List<T>> lists = new ArrayList<>();
        for (T t : list) {
            boolean isContain = false;
            for (List<T> ts : lists) {
                if (ts.size() == 0
                        || comparator.compare(ts.get(0), t) == 0) {
                    ts.add(t);
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                List<T> newList = new ArrayList<>();
                newList.add(t);
                lists.add(newList);
            }
        }
        return lists;
    }

    public static void main(String[] args) {
        List<Apple> list = new ArrayList<>();
        list.add(new Apple("红", 205));
        list.add(new Apple("红", 131));
        list.add(new Apple("绿", 248));
        list.add(new Apple("绿", 153));
        list.add(new Apple("黄", 119));
        list.add(new Apple("黄", 224));
        List<List<Apple>> byColors = dividerList(list, new Comparator<Apple>() {

            @Override
            public int compare(Apple o1, Apple o2) {
                // 按颜色分组
                return o1.color.compareTo(o2.color);
            }
        });
        System.out.println("按颜色分组" + byColors);
        List<List<Apple>> byWeight = dividerList(list, new Comparator<Apple>() {

            @Override
            public int compare(Apple o1, Apple o2) {
                // 按重量级

                return (o1.weight / 100 == o2.weight / 100) ? 0 : 1;
            }
        });
        System.out.println("按重量级分组" + byWeight);
    }
}
