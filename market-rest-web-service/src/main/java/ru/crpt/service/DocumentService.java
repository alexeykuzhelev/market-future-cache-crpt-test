package ru.crpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import ru.crpt.cache.Cache;
import ru.crpt.model.Document;
import ru.crpt.model.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final Cache<String, Document> cache;

    public List<Document> getAllDocuments(Boolean inCache) {
        if (inCache) {
            List<Document> documents = new ArrayList<>();
            cache.getCache().forEach(
                    (k,v) -> {
                        try {
                            documents.add(v.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
            );
            return documents;
        }

        Product product1 = Product.builder().name("milk").code("2364758363546").build();
        Product product2 = Product.builder().name("water").code("3656352437590").build();
        log.info("product1: {}; product2: {}", product1.toString(), product2.toString());
        return Collections.singletonList(Document.builder()
                .customer("648563524")
                .seller("123534251")
                .products(Arrays.asList(product1, product2)).build());
    }


    public void createDocument(Document document) {
        cache.compute(document.getUUID(), (doc) -> document);
    }
}
