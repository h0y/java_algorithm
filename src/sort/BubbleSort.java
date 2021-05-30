package sort;

public class BubbleSort {
    public int[] sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            // 设定标记，若为 true，此次循环没有进行交换
            // 即待排序列已有序，排序完成
            boolean flag = true;

            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;

                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{8, 2, 1, 7, 4};
        new BubbleSort().sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
