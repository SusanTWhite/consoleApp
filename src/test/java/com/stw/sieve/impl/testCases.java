package com.stw.sieve.impl;
//ideally Mockito or something for mocking, but that takes more time
//refactor to use constants instead of repeating string variables

import com.stw.sieve.impl.consoleApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.commons.util.ReflectionUtils.*;

public class testCases {
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setOut(systemOut);
    }

    private static Optional<Class<?>> getClass(final String className) {
        Try<Class<?>> aClass = tryToLoadClass(className);
        return aClass.toOptional();
    }

    public Optional<Class<?>> getAppClass() {
        final String classToFind = "com.stw.sieve.impl.consoleApp";
        return getClass(classToFind);
    }

    public Optional<Class<?>> getInnerClass(String className) {
        final String classToFind = className;
        return getClass(classToFind);
    }

    @Test
    public void assertMainMethodExistence() {
        final String main = "main";
        final Optional<Class<?>> maybeClass = getAppClass();
        assertTrue(maybeClass.isPresent(), "com.stw.sieve.impl.consoleApp class must be present");
        Class<?> c = maybeClass.get();
        List<Method> methods = Arrays.stream(c.getDeclaredMethods())
                .filter(m -> m.getName().equals(main))
                .collect(Collectors.toList());

        assertEquals(1, methods.size(), main + " must be defined as a method in " + c.getCanonicalName());

        final Method method = methods.get(0);
        assertEquals(void.class, method.getReturnType(), main + " must return 'void' as the return type");
        assertTrue(isStatic(method), main + " must be a static method");
        assertTrue(isPublic(method), main + " must be a public method");

        Class<?>[] parameterTypes = method.getParameterTypes();
        assertEquals(1, parameterTypes.length, main + " should accept 1 parameter");
        assertEquals(String[].class, parameterTypes[0], main + " parameter type should be of 'String[]' type");
    }

    @Test
    public void assertValidOutputForValidInputWithNoPrimes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"1"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "There are no positive prime numbers less than 1\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForValidInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"99"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "List of positive prime numbers up to 99 are : 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertMainMethodEmptyInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String expectedOutput = "Enter a positive integer value\n" +
                "No more input!\n";
        final String[] inputValues = {""};
        consoleApp.main(inputValues);
        assertEquals(expectedOutput, getOutput());
    }

    @Test
    public void assertValidOutputForStringInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"s"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "You did not enter a positive integer value. The program will exit gracefully.\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForNegativeInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"-1"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "You did not enter a positive integer value. The program will exit gracefully.\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForFloatInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"0.05"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "You did not enter a positive integer value. The program will exit gracefully.\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForInputZero() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"0"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "You did not enter a positive integer value. The program will exit gracefully.\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForInputTwo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"2"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "The only positive prime number up to 2 is 1\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForInputOne() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"1"};
        final String expectedOutput = "Enter a positive integer value\n" +
                "There are no positive prime numbers less than 1\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForNullInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = null;
        final String expectedOutput = "Enter a positive integer value\n" +
                "You did not enter a positive integer value. The program will exit gracefully.\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForLargeInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"2147483649"}; //(Integer.MAX_VALUE+2)
        final String expectedOutput = "Enter a positive integer value\n" +
                "You did not enter a positive integer value. The program will exit gracefully.\n";
        consoleApp.main(inputValues);
        String outValue=getOutput();
        assertEquals(expectedOutput, outValue);
    }

    @Test
    public void assertValidOutputForMaxInput() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final String[] inputValues = {"2147483647"}; //(Integer.MAX_VALUE)
        OutOfMemoryError exception = assertThrows(OutOfMemoryError.class, () -> {
            consoleApp.main(inputValues);
        });
    }

}
