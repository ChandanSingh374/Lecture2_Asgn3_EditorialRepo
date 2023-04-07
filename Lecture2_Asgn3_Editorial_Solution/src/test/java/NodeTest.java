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


        // Test constructor signature and functionality
        @Test
        public void testConstructorWithDataParameter() {
            Node node = new Node(5);
            assertEquals(5, node.data);
            assertNull(node.next);
        }

        @Test
        public void testConstructorWithNodeParameter() {
            Node originalNode = new Node(5);
            originalNode.next = new Node(10);

            Node copyNode = new Node(originalNode);
            assertEquals(5, copyNode.data);
            assertNotNull(copyNode.next);
            assertEquals(10, copyNode.next.data);
            assertNull(copyNode.next.next);
        }

}

