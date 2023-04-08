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
//            assertFalse(Modifier.isPublic(field.getModifiers()));
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
//            assertFalse(Modifier.isPrivate(field.getModifiers()));
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
//    @Test
//    public void testConstructorWithNodeParameter() throws Exception {
//        Constructor<Node> constructor = Node.class.getDeclaredConstructor(Node.class);
//
//        assertFalse(Modifier.isPrivate(constructor.getModifiers()));
//
//        int data = 5;
//        Node originalNode = new Node(data);
//        Field nextField = Node.class.getDeclaredField("next");
//        nextField.setAccessible(true);
//        nextField.set(originalNode, new Node(10));
//
//        Node copyNode = constructor.newInstance(originalNode);
//
//        Field dataField = Node.class.getDeclaredField("data");
//        dataField.setAccessible(true);
//        Field nextField2 = Node.class.getDeclaredField("next");
//        nextField2.setAccessible(true);
//
//        int actualData = dataField.getInt(copyNode);
//        assertNotNull(nextField2.get(copyNode));
//        assertEquals(10, dataField.getInt(nextField2.get(copyNode)));
//        assertNull(nextField2.get(nextField2.get(copyNode)));
//        assertEquals(5, actualData);
//        assertNull(nextField2.get(copyNode.next));
//    }
    @Test
    public void testConstructorWithNodeParameter() throws Exception {
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
        assertNull(nextField2.get(copyNode.next));
    }


}
