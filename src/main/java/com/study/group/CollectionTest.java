package com.study.group;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.PageUtil;

import java.util.*;
import java.util.function.Function;

public class CollectionTest {

    public static <T, K, G> Map<K, Map<G, List<T>>> groupList(List<T> list, Function<T,K> classifier1
            , Function<T,G> classifier2) {
        HashMap<K, Map<G, List<T>>> res = new HashMap<>();
        list.forEach(t1 -> {
            res.computeIfAbsent(classifier1.apply(t1), k -> new HashMap<>())
                    .computeIfAbsent(classifier2.apply(t1), k -> new LinkedList<>())
                    .add(t1);
        });
        return res;
    }


    public <T> void page(List<T> list, int pageNo, int pageSize) {
    }
}
