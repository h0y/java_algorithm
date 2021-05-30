package dsa;

class HuffmanNode implements Comparable<HuffmanNode>, Cloneable {
    protected int key;              // 权值
    protected HuffmanNode left;     // 左孩子
    protected HuffmanNode right;    // 右孩子
    protected HuffmanNode parent;   // 父结点

    protected HuffmanNode(int key, HuffmanNode left,
                          HuffmanNode right, HuffmanNode parent) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    @Override
    public Object clone() {
        Object obj = null;

        try {
            // Object 中的 clone() 识别出要复制的是哪一个对象
            obj = (HuffmanNode) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }

        return obj;
    }

    @Override
    public int compareTo(HuffmanNode huffmanNode) {
        return this.key - huffmanNode.key;
    }
}

public class HuffmanTree {
    // 根结点
    private HuffmanNode root;

    /*
     * 创建Huffman树
     *
     * @param 权值数组
     */
    public HuffmanTree(int a[]) {
        HuffmanNode parent = null;
        // 建立数组 a 对应的最小堆
        MinHeap<HuffmanNode> heap = new MinHeap<>();

        for (int i = 0; i < a.length; i++) {
            HuffmanNode node = new HuffmanNode(a[i], null, null, null);
            heap.insert(node);
        }

        for (int i = 0; i < a.length - 1; i++) {
            // 最小节点是左孩子
            HuffmanNode left = heap.dumpFromMinimum();
            // 其次才是右孩子
            HuffmanNode right = heap.dumpFromMinimum();

            // 新建 parent 节点，左右孩子分别是 left / right
            // parent 的大小是左右孩子之和
            parent = new HuffmanNode(left.key + right.key, left, right, null);
            left.parent = parent;
            right.parent = parent;

            // 将parent节点数据拷贝到"最小堆"中
            heap.insert(parent);
        }

        root = parent;

        // 销毁最小堆
        heap.destroy();
    }

}
