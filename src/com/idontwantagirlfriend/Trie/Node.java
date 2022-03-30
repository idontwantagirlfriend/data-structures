package com.idontwantagirlfriend.Trie;

public interface Node {
    char getLetter();

    Node getChild(char letter);

    Boolean hasChild(char letter);

    Node removeChild(char letter);

    Node addChild(char letter);

    Node[] getAllChildren();

    Boolean hasNoChild();

    void setEOW(Boolean isEOW);

    Boolean getEOW();
}
