package com.idontwantagirlfriend.Trie;

import java.util.HashMap;

public class ConcreteStringNode implements StringNode{
    private String word;
    private HashMap<String, ConcreteStringNode> subtrees;
    private Boolean isEOW;

    public ConcreteStringNode(String word) {
        this.word = word;
        this.subtrees = new HashMap<>();
        this.isEOW = false;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public ConcreteStringNode getChild(String substring) {
        return subtrees.get(substring);
    }

    @Override
    public Boolean contains(String substring) {
        return subtrees.containsKey(substring);
    }

    @Override
    public ConcreteStringNode remove(String substring) {
        return subtrees.remove(substring);
    }

    @Override
    public void add(String substring) {
        subtrees.put(substring, new ConcreteStringNode(substring));
    }

    @Override
    public ConcreteStringNode[] getAllChildren() {
        return subtrees.values().toArray(ConcreteStringNode[]::new);
    }

    @Override
    public Boolean isLeaf() {
        return subtrees.isEmpty();
    }

    @Override
    public void setEOW(Boolean isEOW) {
        this.isEOW = isEOW;
    }

    @Override
    public Boolean getEOW() {
        return isEOW;
    }
}
