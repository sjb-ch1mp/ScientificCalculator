package com.anu.calculator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ExpressionParserTest.class,
        FunctionParserTest.class,
        HistoryTest.class,
        TokenizerTest.class,
        GraphTest.class
})
public class TestSuite {
}