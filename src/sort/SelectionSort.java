package sort;

/*
1. 在一个长度为 N 的无序数组中，第一次遍历 n-1 个数找到最小的和第一个数交换。
2. 第二次从下一个数开始遍历 n-2 个数，找到最小的数和第二个数交换。
3. 重复以上操作直到第 n-1 次遍历最小的数和第 n-1 个数交换，排序完成
平均时间复杂度：O(n2)，最坏：O(n2)，最好：O(n2)
 */
public class SelectionSort {
    public int[] sort(int[] arr) {
        int tmp;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{8, 2, 1, 7, 4};
        new SelectionSort().sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
