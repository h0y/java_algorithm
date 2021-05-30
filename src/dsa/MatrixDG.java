package dsa;

public class MatrixDG {

    // 顶点集合
    private char[] mVexs;
    // 邻接矩阵
    private int[][] mMatrix;

    /*
     * 创建图(用已提供的矩阵)
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public MatrixDG(char[] vexs, char[][] edges) {

        // 初始化顶点数和边数
        int vlen = vexs.length;
        int elen = edges.length;

        // 初始化顶点
        mVexs = new char[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            mVexs[i] = vexs[i];
        }

        // 初始化边
        mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(edges[i][0]);
            int p2 = getPosition(edges[i][1]);

            mMatrix[p1][p2] = 1;
        }
    }

    /*
     * 返回 ch 位置
     */
    private int getPosition(char ch) {
        for (int i = 0; i < mVexs.length; i++) {
            if (mVexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /*
     * 返回顶点 v 的第一个邻接顶点的索引，失败则返回 -1
     */
    private int firstVertex(int v) {

        if (v < 0 || v > (mVexs.length - 1)) {
            return -1;
        }
        for (int i = 0; i < mVexs.length; i++) {
            if (mMatrix[v][i] == 1) {
                return i;
            }
        }
        return -1;
    }

    /*
     * 返回顶点 v 相对于 w 的下一个邻接顶点的索引，失败则返回 -1
     */
    private int nextVertex(int v, int w) {

        if (v < 0 || v > (mVexs.length - 1) || w < 0 || w > (mVexs.length - 1)) {
            return -1;
        }

        for (int i = w + 1; i < mVexs.length; i++) {
            if (mMatrix[v][i] == 1) {
                return i;
            }
        }

        return -1;
    }

    /*
     * 深度优先搜索遍历图的递归实现
     */
    private void DFS(int i, boolean[] visited) {

        visited[i] = true;
        System.out.printf("%c ", mVexs[i]);
        // 遍历该顶点的所有邻接顶点。若是没有访问过，那么继续往下走
        for (int w = firstVertex(i); w >= 0; w = nextVertex(i, w)) {
            if (!visited[w]) {
                DFS(w, visited);
            }
        }
    }

    /*
     * 深度优先搜索遍历图
     */
    public void DFS() {
        // 顶点访问标记
        boolean[] visited = new boolean[mVexs.length];

        // 初始化所有顶点都没有被访问
        for (int i = 0; i < mVexs.length; i++) {
            visited[i] = false;
        }

        System.out.printf("DFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i]) {
                DFS(i, visited);
            }
        }
        System.out.printf("\n");
    }

    /*
     * 广度优先搜索（类似于树的层次遍历）
     */
    public void BFS() {
        int head = 0;
        int rear = 0;
        // 辅组队列
        int[] queue = new int[mVexs.length];
        // 顶点访问标记
        boolean[] visited = new boolean[mVexs.length];

        for (int i = 0; i < mVexs.length; i++) {
            visited[i] = false;
        }

        System.out.printf("BFS: ");
        for (int i = 0; i < mVexs.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                System.out.printf("%c ", mVexs[i]);
                // 入队列
                queue[rear++] = i;
            }

            while (head != rear) {
                // 出队列
                int j = queue[head++];
                // k 是为访问的邻接顶点
                for (int k = firstVertex(j); k >= 0; k = nextVertex(j, k)) {
                    if (!visited[k]) {
                        visited[k] = true;
                        System.out.printf("%c ", mVexs[k]);
                        queue[rear++] = k;
                    }
                }
            }
        }
        System.out.printf("\n");
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("Martix Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++) {
                System.out.printf("%d ", mMatrix[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
                {'A', 'B'},
                {'B', 'C'},
                {'B', 'E'},
                {'B', 'F'},
                {'C', 'E'},
                {'D', 'C'},
                {'E', 'B'},
                {'E', 'D'},
                {'F', 'G'}};
        MatrixDG pG;

        // 采用已有的图
        pG = new MatrixDG(vexs, edges);

        pG.print();   // 打印图
        pG.DFS();     // 深度优先遍历
        pG.BFS();     // 广度优先遍历
    }
}
