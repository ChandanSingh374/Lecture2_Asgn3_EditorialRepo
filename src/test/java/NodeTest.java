import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;


public class NodeTest {

    // Test data member existence
    @Test
    public void testDataExists() throws Exception {
        try {
            Field field = Node.class.getDeclaredField("data");
//            assertFalse(Modifier.isPublic(field.getModifiers()));
            assertEquals("int", field.getType().getSimpleName());
        } catch (Exception e) {
            fail("Field data does not exist");
        }
    }

    // Test next member existence
    @Test
    public void testNextExists() throws Exception {
        try {
            Field field = Node.class.getDeclaredField("next");
//            assertFalse(Modifier.isPrivate(field.getModifiers()));
            assertEquals("Node", field.getType().getSimpleName());
        } catch (Exception e) {
            fail("Field next does not exist");
        }
    }

    // Test constructor signature and functionality with data parameter
    @Test
    public void testConstructorWithDataParameter() throws Exception {
        try {
            Constructor<Node> constructor = Node.class.getDeclaredConstructor(int.class);
            assertFalse(Modifier.isPrivate(constructor.getModifiers()));

            int data = 5;

            Node node = constructor.newInstance(data);

            Field fieldData = Node.class.getDeclaredField("data");
            fieldData.setAccessible(true);
            int actualData = fieldData.getInt(node);

            Field fieldNext = null;
            try {
                fieldNext = Node.class.getDeclaredField("next");
                fieldNext.setAccessible(true);
            } catch (NoSuchFieldException e) {
            fail("Next attribute is not defined");
            }
            if (fieldNext != null) {
                assertNull(fieldNext.get(node));
            }
            assertEquals(data, actualData);
        } catch (Exception e) {
            fail("Exception thrown: " + e);
        }
    }

    @Test
    public void testConstructorWithNodeParameter() throws Exception {
       try {
            Constructor<Node> constructor = Node.class.getDeclaredConstructor(Node.class);
            assertFalse(Modifier.isPrivate(constructor.getModifiers()));

            int data = 5;
            Constructor<Node> dataConstructor = Node.class.getDeclaredConstructor(int.class);
            Node originalNode = dataConstructor.newInstance(data);
            Field nextField = Node.class.getDeclaredField("next");
            nextField.setAccessible(true);
            nextField.set(originalNode, dataConstructor.newInstance(10));

            Node copyNode = constructor.newInstance(originalNode);

            Field dataField = Node.class.getDeclaredField("data");
            dataField.setAccessible(true);
            Field nextField2 = Node.class.getDeclaredField("next");
            nextField2.setAccessible(true);

            int actualData = dataField.getInt(copyNode);
            assertNotNull(nextField2.get(copyNode));
            assertEquals(10, dataField.getInt(nextField2.get(copyNode)));
            assertNull(nextField2.get(nextField2.get(copyNode)));
            assertEquals(5, actualData);
        } catch (Exception e) {
            fail("Exception thrown: " + e);
        }
    }
}
