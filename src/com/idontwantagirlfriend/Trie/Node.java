package com.idontwantagirlfriend.Trie;

public interface Node {
    char getLetter();

    Node getChild(char letter);

    Boolean hasChild(char letter);

    Boolean hasNoChild();

    Node removeChild(char letter);

    Node addChild(char letter);

    void setEOW(Boolean isEOW);

    Boolean getEOW();
}
