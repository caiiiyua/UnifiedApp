package org.caiiiyua.unifiedapp.parser;

import java.io.IOException;
import java.util.LinkedList;

public interface ContentParser<T> {

    public LinkedList<T> parse() throws IOException;
}
