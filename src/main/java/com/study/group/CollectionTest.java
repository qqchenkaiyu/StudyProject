package com.study.group;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
}
