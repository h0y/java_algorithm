package sort;

/*
1. 将第一待排序序列第一个元素看做一个有序序列，把第二个元素到最后一个元素当成是未排序序列。

2. 从头到尾依次扫描未排序序列，将扫描到的每个元素插入有序序列的适当位置。
（如果待插入的元素与有序序列中的某个元素相等，则将待插入元素插入到相等元素的后面。）

平均时间复杂度：O(n2)，最好：O(n)，最坏：O(n2)
 */
public class InsertionSort {
    public int[] sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j = i;
            while (j > 0 && tmp < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = tmp;
        }
        return arr;
    }

    public static void insertSort(int[] a) {
        int i, j, k;

        for (i = 1; i < a.length; i++) {
            // 为 a[i] 在前面的 a[0...i-1] 有序区间中找一个合适的位置
            for (j = i - 1; j >= 0; j--) {
                if (a[j] < a[i]) {
                    break;
                }
            }
            // 如找到了一个合适的位置
            if (j != i - 1) {
                //将比 a[i] 大的数据向后移
                int temp = a[i];
                for (k = i - 1; k > j; k--) {
                    a[k + 1] = a[k];
                }
                // 将 a[i] 放到正确位置上
                a[k + 1] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{8, 2, 1, 7, 4};
        new InsertionSort().sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
