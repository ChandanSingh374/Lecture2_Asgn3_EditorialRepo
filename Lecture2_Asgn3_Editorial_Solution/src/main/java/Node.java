public class Node {
    int data;
    Node next;

    Node(int data){
        this.data = data;
    }




    Node(Node node){
        this(node.data);
        if(node.next != null) {
            this.next = new Node(node.next);
        }
    }
}

