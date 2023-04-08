import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.*;

import static org.junit.jupiter.api.Assertions.*;


public class NodeTest {

    // Test data member existence
    @Test
    public void testDataExists() {
        try {
            Field field = Node.class.getDeclaredField("data");
            assertFalse(Modifier.isPublic(field.getModifiers()));
            assertEquals("int", field.getType().getSimpleName());
        } catch (NoSuchFieldException e) {
            fail("Field data does not exist");
        }
    }

    // Test next member existence
    @Test
    public void testNextExists() {
        try {
            Field field = Node.class.getDeclaredField("next");
            assertFalse(Modifier.isPrivate(field.getModifiers()));
            assertEquals("Node", field.getType().getSimpleName());
        } catch (NoSuchFieldException e) {
            fail("Field next does not exist");
        }
    }

    // Test constructor signature and functionality with data parameter
    @Test
    public void testConstructorWithDataParameter() throws Exception {
        Constructor<Node> constructor = Node.class.getDeclaredConstructor(int.class);
        assertFalse(Modifier.isPrivate(constructor.getModifiers()));

        int data = 5;

        Node node = constructor.newInstance(data);

        Field fieldData = Node.class.getDeclaredField("data");
        fieldData.setAccessible(true);
        int actualData = fieldData.getInt(node);

        assertNull(node.next);
        assertEquals(data, actualData);
    }

    // Test constructor signature and functionality with node parameter
    @Test
    public void testConstructorWithNodeParameter() throws Exception {
        Constructor<Node> constructor = Node.class.getDeclaredConstructor(Node.class);
        assertFalse(Modifier.isPrivate(constructor.getModifiers()));

        Node originalNode = new Node(5);
        originalNode.next = new Node(10);

        Node copyNode = constructor.newInstance(originalNode);

        Field fieldData = Node.class.getDeclaredField("data");
        fieldData.setAccessible(true);
        Field fieldNext = Node.class.getDeclaredField("next");
        fieldNext.setAccessible(true);

        int actualData = fieldData.getInt(copyNode);
        assertNotNull(fieldNext.get(copyNode));
        assertEquals(10, fieldData.getInt(fieldNext.get(copyNode)));
        assertNull(fieldNext.get(fieldNext.get(copyNode)));
        assertEquals(5, actualData);
        assertNull(fieldNext.get(copyNode.next));
    }
}
