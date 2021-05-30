package sort;

public class ShellSort {
    /**
     * 对希尔排序中的单个组进行排序
     * 参数说明:
     * a -- 待排序的数组
     * n -- 数组总的长度
     * i -- 组的起始位置
     * gap -- 组的步长
     * 组是从 i 开始，将相隔 gap 长度的数都取出所组成的
     */
    public static void groupSort(int[] a, int n, int i, int gap) {
        for (int j = i + gap; j < n; j += gap) {
            // 如果 a[j] < a[j - gap]，则寻找 a[j] 位置，并将后面数据的位置都后移
            if (a[j] < a[j - gap]) {
                int tmp = a[j];
                int k = j - gap;
                while (k >= 0 && a[k] > tmp) {
                    a[k + gap] = a[k];
                    k -= gap;
                }
                a[k + gap] = tmp;
            }
        }
    }

    /**
     * 希尔排序
     * 参数说明:
     * a -- 待排序的数组
     * n -- 数组的长度
     */
    public static void shellSort(int[] a, int n) {
        // gap为步长，每次减为原来的一半
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 共 gap 个组，对每一组都执行直接插入排序
            for (int i = 0; i < gap; i++) {
                groupSort(a, n, i, gap);
            }
        }
    }
}
