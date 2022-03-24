package com.idontwantagirlfriend.Trie;

public interface Node {
    char getLetter();

    ArrayNode getChild(char letter);

    ArrayNode setChild(char letter, ArrayNode child);

    Boolean hasChild(char letter);

    Boolean hasNoChild();

    ArrayNode removeChild(char letter);

    ArrayNode addChild(char letter);

    void setEOW(Boolean isEOW);

    Boolean getEOW();
}
