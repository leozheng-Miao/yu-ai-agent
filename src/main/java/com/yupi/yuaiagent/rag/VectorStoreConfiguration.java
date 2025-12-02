package com.yupi.yuaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @program: yu-ai-agent
 * @description:
 * @author: Miao Zheng
 * @date: 2025-12-02 18:06
 **/
@Configuration
public class VectorStoreConfiguration {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {

        List<Document> documents = loveAppDocumentLoader.loadMarkDown();
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        simpleVectorStore.add(documents);
        System.out.println(documents);
        return simpleVectorStore;

    }
}
