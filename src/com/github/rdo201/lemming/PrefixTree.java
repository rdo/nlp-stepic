package com.github.rdo201.lemming;

import java.util.ArrayList;
import java.util.List;

public class PrefixTree {

    private Node src = new Node();

    public void put(String key, String data) {
        src.put(key, data);
    }

    public String get(String key) {
        return src.get(key);
    }

    public static class Node {
        private char key;
        private List<Node> children = new ArrayList<>();
        private String data;

        public Node(char key) {
            this.key = key;
        }

        public Node(String data) {
            this.data = data;
        }

        public Node() {
        }

        public char getKey() {
            return key;
        }

        public Node get(char key) {
            for(Node n : children) {
                if (n.getKey() == key) {
                    return n;
                }
            }
            return null;
        }

        public String get(String key) {
            if (key.equals("")) {
                return data;
            }
            char ch = key.charAt(0);
            Node child = get(ch);
            if (child != null) {
                return child.get(key.substring(1));
            }
            return null;
        }

        public void put(String key, String data) {
            if (key.equals("")) {
                this.data = data;
                return;
            }
            char ch = key.charAt(0);
            Node child = get(ch);
            if (child == null) {
                child = new Node(ch);
                children.add(child);
            }
            child.put(key.substring(1), data);
        }

        @Override
        public String toString() {
            if (key != '0') {
                return "[" + key + "]";
            } else if (data != null) {
                return data;
            } else {
                return "";
            }
        }
    }

}
