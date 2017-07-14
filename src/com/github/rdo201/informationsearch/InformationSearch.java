package com.github.rdo201.informationsearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformationSearch {

    public static void main(String[] args) {
        Library l = new Library();
        l.addDocument(new Document("Doc1", "яблоко", "груша", "яблоко", "апельсин", "слива"));
        l.addDocument(new Document("Doc2", "банан", "ананас", "яблоко", "ананас", "ананас"));
        l.addDocument(new Document("Doc3", "лимон", "апельсин", "мандарин", "помело", "лайм"));
        l.addDocument(new Document("Doc4", "яблоко", "дуриан", "папайя", "манго", "маракуйя"));
        l.find("яблоко", "банан").entrySet().stream().forEach(e -> System.out.println(e.getKey().title + ":" + e.getValue()));
    }

    public static class Document {

        public String title;
        public List<String> tokens;

        public Document(String title, String... tokens) {
            this.title = title;
            this.tokens = Arrays.asList(tokens);
        }
    }

    public static class Library {

        private double lambda = 0.7;
        private List<Document> documents = new ArrayList<>();

        public void addDocument(Document doc) {
            this.documents.add(doc);
        }

        public Map<Document, Double> find(String... query) {
            Map<Document, Double> result = new HashMap<>();
            documents.stream().forEach(document -> result.put(document, getQueryValue(document, query)));
            return result;
        }

        private double getSingleTokenValue(Document doc, String token) {
            double total = this.documents.stream().map(document -> document.tokens.size()).reduce((integer, integer2) -> integer + integer2).get();
            double totalExact = this.documents.stream().map(d -> d.tokens.stream().filter(s -> s.equals(token)).count()).reduce((aLong, aLong2) -> aLong + aLong2).get();
            double document = doc.tokens.size();
            double documentExact = doc.tokens.stream().filter(s -> s.equals(token)).count();
            return lambda * totalExact / total + (1 - lambda) * documentExact / document;
        }

        private double getQueryValue(Document doc, String... query) {
            return Arrays.asList(query).stream().map(s -> getSingleTokenValue(doc, s)).reduce((aDouble, aDouble2) -> aDouble*aDouble2).get();
        }
    }
}
