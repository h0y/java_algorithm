package dsa;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<T extends Comparable<T>> {

    // 队列 (实际上是动态数组 ArrayList 的实例)
    private List<T> heap;

    public MaxHeap() {
        this.heap = new ArrayList<T>();
    }

    /*
     * 最大堆的向上调整算法(从 start 开始向上直到 0，调整堆)
     * 注：数组实现的堆中，第 N 个节点的左孩子的索引值是 (2N + 1)，右孩子的索引是 (2N + 2)
     * 参数说明：
     *     start -- 被上调节点的起始位置(一般为数组中最后一个元素的索引)
     */
    protected void filterUp(int start) {
        // 当前节点(current)的位置
        int current = start;
        // 父 (parent) 结点的位置
        int parent = (current - 1) / 2;
        // 当前节点 (current) 的大小
        T tmp = heap.get(current);

        while (current > 0) {
            int cmp = heap.get(parent).compareTo(tmp);
            if (cmp >= 0) {
                break;
            } else {
                heap.set(current, heap.get(parent));
                current = parent;
                parent = (parent - 1) / 2;
            }
        }

        heap.set(current, tmp);
    }

    /*
     * 将 data 插入到二叉堆中
     */
    public void insert(T data) {
        int size = heap.size();
        // 将数组插在表尾
        heap.add(data);
        // 向上调整堆
        filterUp(size);
    }

    /*
     * 最大堆的向下调整算法
     * 注：数组实现的堆中，第N个节点的左孩子的索引值是(2N+1)，右孩子的索引是(2N+2)。
     * 参数说明：
     *     start -- 被下调节点的起始位置(一般为0，表示从第1个开始)
     *     end   -- 截至范围(一般为数组中最后一个元素的索引)
     */
    protected void filterDown(int start, int end) {
        // 当前 (current) 节点的位置
        int current = start;
        // 左 (left) 孩子的位置
        int left = 2 * current + 1;
        // 当前 (current) 节点的大小
        T tmp = heap.get(current);

        while (left <= end) {
            int cmp = heap.get(left).compareTo(heap.get(left + 1));
            // "left" 是左孩子，"left + 1" 是右孩子
            if (left < end && cmp < 0) {
                // 左右两孩子中选择较大者，即 heap[left + 1]
                left++;
            }
            cmp = tmp.compareTo(heap.get(left));
            if (cmp >= 0) {
                // 调整结束
                break;
            } else {
                heap.set(current, heap.get(left));
                current = left;
                left = 2 * left + 1;
            }
        }
        heap.set(current, tmp);
    }

    /*
     * 删除最大堆中的data
     *
     * 返回值：
     *      0，成功
     *     -1，失败
     */
    public int remove(T data) {
        // 如果 "堆" 已空，则返回 -1
        if (heap.isEmpty()) {
            return -1;
        }

        // 获取 data 在数组中的索引
        int index = heap.indexOf(data);
        if (index == -1) {
            return -1;
        }

        int size = heap.size();
        // 用最后元素填补
        heap.set(index, heap.get(size - 1));
        // 删除最后的元素
        heap.remove(size - 1);

        if (heap.size() > 1) {
            // 从 index 号位置开始自上向下调整为最小堆
            filterDown(index, heap.size() - 1);
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < heap.size(); i++) {
            sb.append(heap.get(i) + " ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        int i;
        int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
        MaxHeap<Integer> tree = new MaxHeap<>();

        System.out.printf("== 依次添加: ");
        for (i = 0; i < a.length; i++) {
            System.out.printf("%d ", a[i]);
            tree.insert(a[i]);
        }

        System.out.printf("\n== 最 大 堆: %s", tree);

        i = 85;
        tree.insert(i);
        System.out.printf("\n== 添加元素: %d", i);
        System.out.printf("\n== 最 大 堆: %s", tree);

        i = 90;
        tree.remove(i);
        System.out.printf("\n== 删除元素: %d", i);
        System.out.printf("\n== 最 大 堆: %s", tree);
        System.out.printf("\n");
    }
}