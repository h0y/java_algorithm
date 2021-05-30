package dsa;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class BSTNode<T extends Comparable<T>> {
    T key;                // 关键字(键值)
    BSTNode<T> left;      // 左孩子
    BSTNode<T> right;     // 右孩子
    BSTNode<T> parent;    // 父结点

    public BSTNode(T key, BSTNode<T> parent, BSTNode<T> left, BSTNode<T> right) {
        this.key = key;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
}

public class BSTree<T extends Comparable<T>> {

    private BSTNode<T> root;    // 根结点

    // 插入
    private void insert(BSTree<T> tree, BSTNode<T> node) {
        int cmp;
        BSTNode<T> tmpNode = null;
        BSTNode<T> root = tree.root;

        // 查找 node 的插入位置
        while (root != null) {
            tmpNode = root;
            cmp = node.key.compareTo(root.key);
            if (cmp < 0) {
                root = root.left;
            } else {
                root = root.right;
            }
        }

        node.parent = tmpNode;

        if (tmpNode == null) {
            tree.root = node;
        } else {
            cmp = node.key.compareTo(tmpNode.key);
            if (cmp < 0) {
                tmpNode.left = node;
            } else {
                tmpNode.right = node;
            }
        }
    }

    // 先序遍历
    private void preOrder(BSTNode<T> node) {
        if (node != null) {
            System.out.print(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void travPreIteration(BSTNode<T> node) {

        Stack<BSTNode<T>> s = new Stack<>();

        if(node != null) {
            s.push(node);
        }

        while(! s.empty()) {
            node = s.pop();

            System.out.println(node.key);

            // 每次都是弹出栈时被访问，所以应先将右子树推入栈中，后将左子树入栈
            // 以使得左子树先被访问
            if (node.right != null) {
                s.push(node.right);
            }

            if (node.left != null) {
                s.push(node.left);
            }
        }
    }

    // 中序遍历
    private void inOrder(BSTNode<T> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        }
    }

    // 后序遍历
    private void postOrder(BSTNode<T> node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.key + " ");
        }
    }

    private void levelOrder(BSTNode<T> node) {

        Queue<BSTNode<T>> queue = new LinkedList<>();
        // offer == enqueue
        queue.offer(node);

        while (!queue.isEmpty()) {
            // pol == dequeue
            node = queue.poll();

            System.out.print(node.key + " ");

            if (node.left != null) {
                queue.offer(node.left);
            }

            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    // 查找
    private BSTNode<T> search(BSTNode<T> node, T key) {
        if (node == null) {
            return node;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return search(node.left, key);
        } else if (cmp > 0) {
            return search(node.right, key);
        }

        return node;
    }

    public BSTNode<T> iterativeSearch(BSTNode<T> node, T key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return node;
    }

    private BSTNode<T> maximum(BSTNode<T> node) {
        if (node == null) {
            return null;
        }

        while(node.right != null) {
            node = node.right;
        }
        return node;
    }

    private BSTNode<T> minimum(BSTNode<T> node) {
        if (node == null) {
            return null;
        }

        while(node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 前驱
    private BSTNode<T> predecessor(BSTNode<T> node) {
        // 如果 x 存在左孩子，则 "x的前驱结点" 为 "以其左孩子为根的子树的最大结点"
        if (node.left != null) {
            return maximum(node.left);
        }

        // 如果 x 没有左孩子。则 x 有以下两种可能:
        // x 是 "右孩子"，则 "x的前驱结点" 为 "它的父结点"
        // x 是 "左孩子"，则查找 "x 的最低的父结点，并且该父结点要具有右孩子"
        // 找到的这个 "最低的父结点" 就是 "x 的前驱结点"
        BSTNode<T> tmp = node.parent;
        while ((tmp != null) && (node == tmp.left)) {
            node = tmp;
            tmp = tmp.parent;
        }

        return tmp;
    }

    private BSTNode<T> successor(BSTNode<T> node) {
        // 如果 x 存在右孩子，则 "x的后继结点" 为 "以其右孩子为根的子树的最小结点"
        if (node.right != null) {
            return minimum(node.right);
        }

        // 如果 x 没有右孩子。则 x 有以下两种可能:
        // x 是 "左孩子"，则 "x 的后继结点" 为 "它的父结点"
        // x 是 "右孩子"，则查找 "x 的最低的父结点，并且该父结点要具有左孩子"
        // 找到的这个 "最低的父结点" 就是 "x 的后继结点"
        BSTNode<T> tmp = node.parent;
        while ((tmp != null) && (node == tmp.right)) {
            node = tmp;
            tmp = tmp.parent;
        }

        return tmp;
    }

    // 删除
    private BSTNode<T> remove(BSTree<T> tree, BSTNode<T> node) {
        BSTNode<T> x = null;
        BSTNode<T> y = null;

        if ((node.left == null) || (node.right == null)) {
            y = node;
        } else {
            y = successor(node);
        }

        if (y.left != null) {
            x = y.left;
        } else {
            x = y.right;
        }

        if (x != null) {
            x.parent = y.parent;
        }

        if (y.parent == null) {
            tree.root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        if (y != node) {
            node.key = y.key;
        }

        return y;
    }

    public static void main(String[] args) {
        int arr[] = {8, 3, 10, 6, 1, 14, 4, 7, 13};
        BSTree<Integer> tree = new BSTree<Integer>();

        for (int i = 0; i < arr.length; i++) {
            BSTNode<Integer> node = new BSTNode<>(arr[i], null, null, null);
            tree.insert(tree, node);
        }

        tree.preOrder(tree.root);
        System.out.println();
        tree.inOrder(tree.root);
        System.out.println();
        tree.postOrder(tree.root);
        System.out.println();
        tree.levelOrder(tree.root);
        System.out.println();

//        BSTNode<Integer> snode = tree.search(tree.root, 15);
        BSTNode<Integer> snode = tree.iterativeSearch(tree.root, 15);
        if (snode != null) {
            System.out.println(snode.key);
        } else {
            System.out.println("not found key");
        }

        System.out.println(tree.maximum(tree.root).key);
        System.out.println(tree.minimum(tree.root).key);

        System.out.println(tree.predecessor(tree.root).key);
        System.out.println(tree.successor(tree.root).key);
    }
}