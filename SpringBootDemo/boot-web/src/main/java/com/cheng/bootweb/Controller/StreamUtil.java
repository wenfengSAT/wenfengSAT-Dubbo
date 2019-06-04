package com.cheng.bootweb.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamUtil {


    static class Color {
        Integer high;
        Integer width;

        Color() {
        }

        Color(Integer high, Integer width) {
            this.high = high;
            this.width = width;
        }

        public Integer getHigh() {
            return high;
        }

        public void setHigh(Integer high) {
            this.high = high;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }
    }


    public static void main(String[] args) {

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(new Color(3, 3));
        colors.add(new Color(3, 2));
        colors.add(new Color(3, 2));
        colors.add(new Color(4, 2));
        colors.add(new Color(5, 3));


        Integer reduce = colors.stream().reduce(0, (sum, x) -> sum + x.getWidth(), Integer::sum);

        int sum2 = colors.parallelStream().mapToInt(v -> v.getWidth()).sum();


        ArrayList<Object> collect = colors.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);


        List<String> collect1 = colors.stream().map(Object::toString).collect(Collectors.toList());

        collect1.forEach(v -> System.out.println(v));

    }

}
