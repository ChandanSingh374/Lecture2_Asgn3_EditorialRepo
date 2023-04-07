import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        student = new Student();

    }

    @Test
    void ageExists(){
        try {
            Field ageField = student.getClass().getDeclaredField("age");
            assertEquals(ageField.getType().toString(), "int");
        } catch (NoSuchFieldException e) {
            fail("age not found");
        }
    }

    @Test
    void nameExists(){
        try {
            Field ageField = student.getClass().getDeclaredField("name");
            assertEquals(ageField.getType().toString(), "class java.lang.String");
        } catch (NoSuchFieldException e) {
            fail("age not found");
        }
    }

    @Test
    void displayMethodExists(){
        try {
            Method displayMethod = student.getClass().getDeclaredMethod("display");
            assertEquals(displayMethod.toString(), "void Student.display()");
        } catch (NoSuchMethodException e) {
            fail("display not found");
        }
    }

    @Test
    void sayHelloMethodExists(){
        try {
            Method sayHelloMethod = student.getClass().getDeclaredMethod("sayHello", String.class);
            assertEquals(sayHelloMethod.toString(), "void Student.sayHello(java.lang.String)");
        } catch (NoSuchMethodException e) {
            fail("sayHello not found");
        }
    }

    @Test
    void display() {
        student.name = "Test";
        student.age = 10;

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        student.display();

        String expected = "My name is Test. I am 10 years old\n";
        assertEquals(expected, outContent.toString());
    }

    @Test
    void sayHello() {
        student.name = "Test";

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        student.sayHello("Test2");

        String expected = "Test says hello to Test2\n";
        assertEquals(expected, outContent.toString());
    }
}