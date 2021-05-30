package dsa;

class RBTNode<T extends Comparable<T>> {
    boolean color;      // 颜色
    T key;              // 关键字 (键值)
    RBTNode<T> left;    // 左孩子
    RBTNode<T> right;   // 右孩子
    RBTNode<T> parent;  // 父结点

    public RBTNode(T key, boolean color, RBTNode<T> parent,
                   RBTNode<T> left, RBTNode<T> right) {
        this.key = key;
        this.color = color;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
}

public class RBTree<T extends Comparable<T>> {

    private RBTNode<T> root;    // 根结点

    private static final boolean RED   = false;
    private static final boolean BLACK = true;

    /*
     * 对红黑树的节点进行左旋转
     */
    private void leftRotate(RBTNode<T> p) {
        // 设置 p 的右子节点为 v
        RBTNode<T> v = p.right;

        // 将 v 的左子节点设为 p 的右子节点
        p.right = v.left;
        // 如果 v 的左子节点非空，将 p 设为 v 的左子节点的父节点
        if (v.left != null) {
            v.left.parent = p;
        }

        // 将 p 的父节点设为 v 的父节点
        v.parent = p.parent;

        if (p.parent == null) {
            // 如果 p 的父节点是空节点，则将 v 设为根节点
            this.root = v;
        } else {
            if (p.parent.left == p) {
                // 如果 p 是它父节点的左子节点，则将 v 设为 p 的父节点的左子节点
                p.parent.left = v;
            } else {
                // 如果 p 是它父节点的右子节点，则将 v 设为 p 的父节点的右子节点
                p.parent.right = v;
            }
        }

        // 将 p 设为 v 的左子节点
        v.left = p;
        // 将 p 的父节点 设为 v
        p.parent = v;
    }

    /*
     * 对红黑树的节点进行右旋转
     */
    private void rightRotate(RBTNode<T> p) {
        // 设置 f 是当前节点 p 的左子节点
        RBTNode<T> f = p.left;

        // 将 f 的右子节点设为 p 的左子节点
        p.left = f.right;
        // 如果 f 的右子节点不为空，将 p 设为 f 的右子节点的父亲
        if (f.right != null)
            f.right.parent = p;

        // 将 p的父节点设为 f 的父节点
        f.parent = p.parent;

        if (p.parent == null) {
            // 如果 p 的父节点是空节点，则将 f 设为根节点
            this.root = f;
        } else {
            if (p == p.parent.right)
                // 如果 p 是它父节点的右子节点，则将 f 设为 p 的父节点的右子节点
                p.parent.right = f;
            else
                // 如果 p 是它父节点的左子节点，则将 f 设为 p 的父节点的左子节点
                p.parent.left = f;
        }

        // 将 p 设为 f 的右子节点
        f.right = p;

        // 将 p 的父节点设为 f
        p.parent = f;
    }

    // 查找
    private RBTNode<T> search(RBTNode<T> root, T key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return search(root.left, key);
        } else if (cmp > 0) {
            return search(root.right, key);
        }

        return root;
    }
}
