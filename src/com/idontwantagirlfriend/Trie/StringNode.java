package com.idontwantagirlfriend.Trie;

public interface StringNode {
    String getWord();

    StringNode getChild(String substring);

    Boolean contains(String substring);

    StringNode remove(String substring);

    void add(String substring);

    StringNode[] getAllChildren();

    Boolean isLeaf();

    void setEOW(Boolean isEOW);

    Boolean getEOW();
}

