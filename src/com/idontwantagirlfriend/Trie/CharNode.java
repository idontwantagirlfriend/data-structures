package com.idontwantagirlfriend.Trie;

public interface CharNode {
    char getLetter();

    CharNode getChild(char letter);

    Boolean contains(char letter);

    CharNode removeChild(char letter);

    Boolean add(char letter);

    CharNode[] getAllChildren();

    Boolean isLeaf();

    void setEOW(Boolean isEOW);

    Boolean getEOW();
}
