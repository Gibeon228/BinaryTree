package Tree;

public class BinaryTree {
    Node root;

    public class Node {
        int value;
        Node left;
        Node right;
        Color color;
    }

    public boolean exists(int value) {
        if (root != null) {
            if (find(value) != null) {
                return true;
            }
        }
        return false;
    }

    private Node find(int value) {
        Node current = root;
        while (current != null) {
            if (current.value == value) {
                return current;
            }
            if (current.value < value) {
                current = current.right;
            } else {
                current = current.left;
            }
        }
        return null;
    }


    public void addNode(Node node, int value) {
        if (node.value == value) {
            return;
        }

        if (node.value > value) {
            if (node.left != null) {
                addNode(node.left, value);
                node.left = rebalance(node.left);
                return;
            }
            node.left = new Node();
            node.left.color = Color.RED;
            node.left.value = value;
            return;
        }
        if (node.right != null) {
            addNode(node.right, value);
            node.right = rebalance(node.right);
            return;
        }
        node.right = new Node();
        node.right.color = Color.RED;
        node.right.value = value;
    }

    public void addN(int value) {
        if (root != null) {
            addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return;
        }
        root = new Node();
        root.color = Color.BLACK;
        root.value = value;
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;

            if (result.right != null && result.right.color == Color.RED &&
                    (result.left == null || result.left.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }

            if (result.left != null && result.left.color == Color.RED &&
                    result.left.left != null && result.left.left.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }

            if (result.left != null && result.left.color == Color.RED &&
                    result.right != null && result.right.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }

        }
        while (needRebalance);
        return result;
    }

    private void colorSwap(Node node) {
        node.right.color = Color.BLACK;
        node.left.color = Color.BLACK;
        node.color = Color.RED;
    }

    private Node leftSwap(Node node) {
        Node leftChild = node.left;
        Node betweenChild = leftChild.right;
        leftChild.right = node;
        node.left = betweenChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private Node rightSwap(Node node) {
        Node rightChild = node.right;
        Node betweenChild = rightChild.left;
        rightChild.left = node;
        node.right = betweenChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private enum Color {
        RED, BLACK;
    }
}
