package q1;

public class main1 {
    public static void main(String[] args) {
        DLL dll = new DLL();

        dll.add("4", true);
        dll.add("5", true);
        dll.add("6", true);
        dll.print();

        DLL.Node remove = dll.remove(true);

    }
}
