package io.github.byzatic.pqletta.client.impl.query_range.support;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ValueDeserializerTest {
    private final static Logger logger = LoggerFactory.getLogger(ValueDeserializerTest.class);
    @Test
    public void test1(){
        // prepare
        String expected = "2025-05-22T10:33:19Z";
        long data = 1747909999;
        //
        ValueDeserializer aa = new ValueDeserializer();

        // action
        String actual = aa.convert(data).toString();

        // bonus
        logger.debug("b: {}", aa.convert(1747909999.1));


        // verify
        logger.debug("expected: {}", expected);
        logger.debug("actual: {}", actual);

        assertEquals(expected, actual);
    }

}