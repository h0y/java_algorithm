package dsa;

public class ListDG {
    // 邻接表中表对应的链表的顶点
    private class ENode {
        // 该边所指向的顶点的位置
        int ivex;
        // 指向下一条弧的指针
        ENode nextEdge;
    }

    // 邻接表中表的顶点
    private class VNode {
        // 顶点信息
        char data;
        // 指向第一条依附该顶点的弧
        ENode firstEdge;
    }

    // 顶点数组
    private VNode[] mVexs;

    /*
     * 创建图(用已提供的矩阵)
     *
     * 参数说明：
     *     vexs  -- 顶点数组
     *     edges -- 边数组
     */
    public ListDG(char[] vexs, char[][] edges) {

        // 初始化顶点数和边数
        int vlen = vexs.length;
        int elen = edges.length;

        // 初始化顶点
        mVexs = new VNode[vlen];
        for (int i = 0; i < mVexs.length; i++) {
            mVexs[i] = new VNode();
            mVexs[i].data = vexs[i];
            mVexs[i].firstEdge = null;
        }

        // 初始化边
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            char c1 = edges[i][0];
            char c2 = edges[i][1];
            // 读取边的起始顶点和结束顶点
            int p1 = getPosition(edges[i][0]);
            int p2 = getPosition(edges[i][1]);

            // 初始化 node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            // 将 node1 链接到 p1 所在链表的末尾
            if (mVexs[p1].firstEdge == null) {
                mVexs[p1].firstEdge = node1;
            } else {
                linkLast(mVexs[p1].firstEdge, node1);
            }
        }
    }

    /*
     * 将 node节点链接到 list 的最后
     */
    private void linkLast(ENode list, ENode node) {
        ENode p = list;

        while (p.nextEdge != null) {
            p = p.nextEdge;
        }
        p.nextEdge = node;
    }

    /*
     * 返回ch位置
     */
    private int getPosition(char ch) {
        for (int i = 0; i < mVexs.length; i++) {
            if (mVexs[i].data == ch) {
                return i;
            }
        }
        return -1;
    }

    /*
     * 深度优先搜索遍历图的递归实现
     */
    private void DFS(int i, boolean[] visited) {
        ENode node;

        visited[i] = true;
        System.out.printf("%c ", mVexs[i].data);
        node = mVexs[i].firstEdge;
        while (node != null) {
            if (!visited[node.ivex]) {
                DFS(node.ivex, visited);
            }
            node = node.nextEdge;
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
                System.out.printf("%c ", mVexs[i].data);
                // 入队列
                queue[rear++] = i;
            }

            while (head != rear) {
                // 出队列
                int j = queue[head++];
                ENode node = mVexs[j].firstEdge;
                while (node != null) {
                    int k = node.ivex;
                    if (!visited[k]) {
                        visited[k] = true;
                        System.out.printf("%c ", mVexs[k].data);
                        queue[rear++] = k;
                    }
                    node = node.nextEdge;
                }
            }
        }
        System.out.printf("\n");
    }

    /*
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("List Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("%d(%c): ", i, mVexs[i].data);
            ENode node = mVexs[i].firstEdge;
            while (node != null) {
                System.out.printf("%d(%c) ", node.ivex, mVexs[node.ivex].data);
                node = node.nextEdge;
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
        ListDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new ListDG();
        // 采用已有的"图"
        pG = new ListDG(vexs, edges);

        pG.print();   // 打印图
        pG.DFS();     // 深度优先遍历
        pG.BFS();     // 广度优先遍历
    }
}
