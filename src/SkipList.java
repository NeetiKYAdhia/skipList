import java.util.Random;

class SkipListNode {
    int value;
    SkipListNode[] next;

    public SkipListNode(int value, int level) {
        this.value = value;
        this.next = new SkipListNode[level + 1];
    }
}

public class SkipList {
    private static final int MAX_LEVEL = 16;
    private int level;
    private SkipListNode head;
    private Random random;

    public SkipList() {
        this.level = 0;
        this.head = new SkipListNode(Integer.MIN_VALUE, MAX_LEVEL);
        this.random = new Random();
    }

    // Generate a random level for a new node
    private int randomLevel() {
        int level = 0;
        while (level < MAX_LEVEL && random.nextDouble() < 0.5) {
            level++;
        }
        return level;
    }

    // Insert a value into the skip list
    public void insert(int value) {
        int newLevel = randomLevel();

        if (newLevel > level) {
            level = newLevel;
        }

        SkipListNode newNode = new SkipListNode(value, newLevel);

        SkipListNode current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }

            if (i <= newLevel) {
                newNode.next[i] = current.next[i];
                current.next[i] = newNode;
            }
        }
    }

    // Search for a value in the skip list
    public boolean search(int value) {
        SkipListNode current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
        }

        return current.next[0] != null && current.next[0].value == value;
    }

    // Remove a value from the skip list
    public void remove(int value) {
        SkipListNode[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
            update[i] = current;
        }

        if (current.next[0] != null && current.next[0].value == value) {
            for (int i = 0; i <= level; i++) {
                if (update[i].next[i] != null && update[i].next[i].value == value) {
                    update[i].next[i] = update[i].next[i].next[i];
                }
            }
        }
    }

    // Display the skip list
    public void display() {
        System.out.println("Skip List (level: " + level + ")");
        for (int i = level; i >= 0; i--) {
            SkipListNode current = head.next[i];
            System.out.print("Level " + i + ": ");
            while (current != null) {
                System.out.print(current.value + " ");
                current = current.next[i];
            }
            System.out.println();
        }
    }

    public static void main() {
        SkipList skipList = new SkipList();

        skipList.insert(3);
        skipList.insert(6);
        skipList.insert(7);
        skipList.insert(9);
        skipList.insert(12);
        skipList.insert(19);
        skipList.insert(17);
        skipList.insert(26);
        skipList.insert(21);
        skipList.insert(25);

        skipList.display();

        System.out.println("Search for 19: " + skipList.search(19));
        System.out.println("Search for 20: " + skipList.search(20));

        skipList.remove(19);

        skipList.display();
    }
}
