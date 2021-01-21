package ru.crpt.cache;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

@Data
@RequiredArgsConstructor
public class Cache<K,V> {

    private final Map<K, Future<V>> cache = new ConcurrentHashMap<>();

    public Future<V> compute(K k, Function<K, V> f) {
        while(true) {
            Future<V> future = cache.get(k);
            
            if (future == null) {
                FutureTask<V> futureTask = new FutureTask<>(() -> f.apply(k));
                future = cache.putIfAbsent(k, futureTask);
                
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            return future;
        }
    }

}
