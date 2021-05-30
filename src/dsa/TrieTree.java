package dsa;

import java.util.TreeMap;

class TrieNode {

    // 是否是某个单词的结束
    public boolean isWord;
    // 到下一个节点的映射
    public TreeMap<Character, TrieNode> next;

    public TrieNode(boolean isWord) {
        this.isWord = isWord;
        // 初始化字典树
        next = new TreeMap<>();
    }

    public TrieNode() {
        this(false);
    }
}

public class TrieTree {
    // 根节点
    private TrieNode root;
    // Trie 单词个数
    private int size;

    public TrieTree() {
        root = new TrieNode();
        size = 0;
    }

    // 获得 Trie 中存储的单词数量
    public int getSize() {
        return size;
    }

    // 将字符串添加到 TrieTree 中
    public void addIteration(String word) {
        TrieNode cur = root;
        // 循环判断新的 cur 节点是否包含下一个字符到下一个节点的映射
        for (int i = 0; i < word.length(); i++) {
            //将 c 当成一个节点插入 Trie 中
            char c = word.charAt(i);
            // 判断 cur.next 是不是已经指向要找的 c 字符相应的节点
            if (cur.next.get(c) == null) {
                //新 建节点
                cur.next.put(c, new TrieNode());
            }
            // 否则，就直接走到该节点位置即可
            cur = cur.next.get(c);
        }
        // 判断该单词并不表示任何一个单词的结尾
        if (!cur.isWord) {
            //确定 cur 是新的单词
            cur.isWord = true;
            size++;
        }
    }

    /**
     * 向 Trie 中添加一个新的单词 word (递归写法接口)
     */
    public void recursionAdd(String word) {
        TrieNode cur = root;
        add(root, word, 0);
    }

    /**
     * 递归写法调用方法实现递归添加
     *
     * @param node 传入要进行添加的节点
     * @param word 传入要进行添加的单词
     */
    public void add(TrieNode node, String word, int index) {
        // 确定终止条件,这个终止条件在没加 index 这个参数时,很难确定
        // 此时一个单词已经遍历完成了,如果这个结束节点没有标记为单词,就标记为单词
        if (!node.isWord && index == word.length()) {
            node.isWord = true;
            size++;
        }

        if (word.length() > index) {
            char addLetter = word.charAt(index);
            // 判断 trie 的下个节点组中是否有查询的字符,如果没有,就添加
            if (node.next.get(addLetter) == null) {
                node.next.put(addLetter, new TrieNode());
            }
            // 基于已经存在的字符进行下个字符的递归查询
            add(node.next.get(addLetter), word, index + 1);
        }
    }

    /**
     * 查询单词 word 是否在 Trie 中 (非递归写法)
     *
     * @param word
     */
    public boolean contains(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            } else {
                cur = cur.next.get(c);
            }
        }
        return cur.isWord;
    }

    /**
     * 查询单词 word 中是否在 Trie 中接口(递归写法)
     */
    public boolean recursionContains(String word) {
        TrieNode cur = root;
        return contains(root, word, 0);
    }

    /**
     * 查询 word 中是否在 Trie 中递归写法
     */
    private boolean contains(TrieNode node, String word, int index) {
        if (index == word.length()) {
            return node.isWord;
        }
        char c = word.charAt(index);
        if (node.next.get(c) == null) {
            return false;
        } else {
            return contains(node.next.get(c), word, index + 1);
        }
    }

    /**
     * 查询是否在 Trie 中有单词 prefix 为前缀
     */
    public boolean isPrefix(String prefix) {
        TrieNode cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }

    /**
     * 查询是否在 Trie 中有单词 prefix 为前缀 (递归调用)
     */
    public boolean recursionIsPrefix(String prefix) {
        TrieNode node = root;
        return recursionIsPrefix(root, prefix, 0);
    }

    /**
     * 查询是否在 Trie 中有单词 prefix 为前缀(递归实现)
     */
    public boolean recursionIsPrefix(TrieNode root, String prefix, int index) {
        if (prefix.length() == index) {
            return true;
        }
        char c = prefix.charAt(index);
        if (root.next.get(c) == null) {
            return false;
        } else {
            return recursionIsPrefix(root.next.get(c), prefix, ++index);
        }
    }
}