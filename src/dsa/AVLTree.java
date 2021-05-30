package dsa;

// AVL树的节点
class AVLTreeNode<T extends Comparable<T>> {
    T key;                  // 关键字 (键值)
    int height;             // 高度
    AVLTreeNode<T> left;    // 左孩子
    AVLTreeNode<T> right;   // 右孩子

    public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.height = 0;
    }
}

public class AVLTree<T extends Comparable<T>> {
    private AVLTreeNode<T> root;    // 根结点

    /*
     * 获取树的高度
     */
    private int height(AVLTreeNode<T> tree) {
        if (tree != null) {
            return tree.height;
        }

        return 0;
    }

    /*
     * 比较两个值的大小
     */
    private int max(int a, int b) {
        return a > b ? a : b;
    }

    /*
     * LL: 左左对应的情况(左单旋转)。
     * 返回值: 旋转后的根节点
     */
    private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2) {
        AVLTreeNode<T> k1;

        k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;

        return k1;
    }

    /*
     * RR: 右右对应的情况(右单旋转)。
     * 返回值: 旋转后的根节点
     */
    private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k1) {
        AVLTreeNode<T> k2;

        k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;

        return k2;
    }

    /*
     * LR: 左右对应的情况(左双旋转)。
     * 返回值: 旋转后的根节点
     */
    private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k3) {
        k3.left = rightRightRotation(k3.left);
        return leftLeftRotation(k3);
    }

    /*
     * RL: 右左对应的情况(右双旋转)。
     * 返回值: 旋转后的根节点
     */
    private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k1) {
        k1.right = leftLeftRotation(k1.right);
        return rightRightRotation(k1);
    }

    /*
     * 查找最大结点: 返回tree为根结点的AVL树的最大结点。
     */
    private AVLTreeNode<T> maximum(AVLTreeNode<T> tree) {
        if (tree == null)
            return null;

        while(tree.right != null)
            tree = tree.right;
        return tree;
    }

    /*
     * 查找最小结点: 返回tree为根结点的AVL树的最小结点。
     */
    private AVLTreeNode<T> minimum(AVLTreeNode<T> tree) {
        if (tree == null)
            return null;

        while(tree.left != null)
            tree = tree.left;
        return tree;
    }

    /*
     * 将结点插入到AVL树中，并返回根节点
     *
     * 参数说明:
     *     tree AVL树的根结点
     *     key 插入的结点的键值
     * 返回值:
     *     根节点
     */
    private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key) {
        if (tree == null) {
            // 新建节点
            tree = new AVLTreeNode<T>(key, null, null);
            if (tree == null) {
                System.out.println("ERROR: create avltree node failed!");
                return null;
            }
        } else {
            int cmp = key.compareTo(tree.key);

            if (cmp < 0) {    // 应该将 key 插入到 "tree 的左子树" 的情况
                tree.left = insert(tree.left, key);
                // 插入节点后，若 AVL 树失去平衡，则进行相应的调节
                if (height(tree.left) - height(tree.right) == 2) {
                    if (key.compareTo(tree.left.key) < 0)
                        tree = leftLeftRotation(tree);
                    else
                        tree = leftRightRotation(tree);
                }
            } else if (cmp > 0) {    // 应该将 key 插入到 "tree 的右子树" 的情况
                tree.right = insert(tree.right, key);
                // 插入节点后，若 AVL 树失去平衡，则进行相应的调节
                if (height(tree.right) - height(tree.left) == 2) {
                    if (key.compareTo(tree.right.key) > 0)
                        tree = rightRightRotation(tree);
                    else
                        tree = rightLeftRotation(tree);
                }
            } else {    // cmp == 0
                System.out.println("添加失败: 不允许添加相同的节点！");
            }
        }

        tree.height = max(height(tree.left), height(tree.right)) + 1;

        return tree;
    }

    /*
     * 删除结点(z)，返回根节点
     *
     * 参数说明:
     *     tree AVL树的根结点
     *     z 待删除的结点
     * 返回值:
     *     根节点
     */
    private AVLTreeNode<T> remove(AVLTreeNode<T> tree, AVLTreeNode<T> node) {
        // 根为空 或者 没有要删除的节点，直接返回null。
        if (tree == null || node == null) {
            return null;
        }

        int cmp = node.key.compareTo(tree.key);
        if (cmp < 0) {        // 待删除的节点在 "tree 的左子树" 中
            tree.left = remove(tree.left, node);
            // 删除节点后，若 AVL 树失去平衡，则进行相应的调节
            if (height(tree.right) - height(tree.left) == 2) {
                AVLTreeNode<T> r = tree.right;
                if (height(r.left) > height(r.right)) {
                    tree = rightLeftRotation(tree);
                } else {
                    tree = rightRightRotation(tree);
                }
            }
        } else if (cmp > 0) {    // 待删除的节点在 "tree 的右子树" 中
            tree.right = remove(tree.right, node);
            // 删除节点后，若 AVL 树失去平衡，则进行相应的调节
            if (height(tree.left) - height(tree.right) == 2) {
                AVLTreeNode<T> l = tree.left;
                if (height(l.right) > height(l.left)) {
                    tree = leftRightRotation(tree);
                } else {
                    tree = leftLeftRotation(tree);
                }
            }
        } else {    // tree 是对应要删除的节点
            // tree 的左右孩子都非空
            if ((tree.left != null) && (tree.right != null)) {
                if (height(tree.left) > height(tree.right)) {
                    // 如果 tree 的左子树比右子树高；
                    // 则 (01) 找出 tree 的左子树中的最大节点
                    //   (02) 将该最大节点的值赋值给 tree
                    //   (03) 删除该最大节点。
                    // 这类似于用 "tree 的左子树中最大节点" 做 "tree" 的替身；
                    // 采用这种方式的好处是: 删除 "tree 的左子树中最大节点" 之后，AVL 树仍然是平衡的
                    AVLTreeNode<T> max = maximum(tree.left);
                    tree.key = max.key;
                    tree.left = remove(tree.left, max);
                } else {
                    // 如果 tree 的左子树不比右子树高 (即它们相等，或右子树比左子树高 1)
                    // 则 (01) 找出 tree 的右子树中的最小节点
                    //    (02) 将该最小节点的值赋值给 tree
                    //    (03) 删除该最小节点
                    // 这类似于用 "tree 的右子树中最小节点" 做 "tree" 的替身
                    // 采用这种方式的好处是: 删除 "tree 的右子树中最小节点" 之后，AVL 树仍然是平衡的
                    AVLTreeNode<T> min = maximum(tree.right);
                    tree.key = min.key;
                    tree.right = remove(tree.right, min);
                }
            } else {
                AVLTreeNode<T> tmp = tree;
                tree = (tree.left != null) ? tree.left : tree.right;
                tmp = null;
            }
        }

        return tree;
    }


    public static void main(String[] args) {
        int arr[] = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};

        int i;
        AVLTree<Integer> tree = new AVLTree<Integer>();

        for(i = 0; i < arr.length; i++) {
            System.out.printf("%d ", arr[i]);
            AVLTreeNode<Integer> node = new AVLTreeNode<>(arr[i], null, null);
            System.out.printf("== 依次添加: ");
            tree.insert(node, arr[i]);
        }
    }
}
