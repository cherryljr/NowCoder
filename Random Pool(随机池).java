/*
设计一种结构， 在该结构中有如下三个功能：
    insert(key)：将某个key加入到该结构， 做到不重复加入。
    delete(key)：将原本在结构中的某个key移除。
    getRandom()：等概率随机返回结构中的任何一个key。
要求：Insert、delete和 getRandom 方法的时间复杂度都是 O(1)
 */

/**
 * Approach: Two HashMap
 * 起初考虑使用 数组 来实现这个结构，但是后来发现行不通。
 * 这是因为数组在实现 insert 和 getRandom 的时候确实没问题，但是无法处理 delete 的问题。
 * 如果我们需要删除一个特定的 Key,那么我们需要遍历一遍整个数组，这与要求的 O(1) 时间复杂度不符合。
 * 因此很自然地，插入，删除，查询 均为 O(1) 时间复杂的毫无疑问就是 HashMap 这个数据结构了。
 *
 * 但是使用 HashMap 的时候，我们也遇到了一个问题，如何保证在 O(1) 的时间内获得一个随机 Key （概率相等）
 * 我们通常会选择使用 Random 这个方法来实现，但是 Key 并不一定就是 int 类型，或者说大部分情况下并不是 int.
 * 所以我们还需要储存一个 key 所对应的 index 信息，以便进行随机获得 key.
 * 因此我们使用了两个 HashMap，分别储存 (Key, Index) 和 (Index, Key).
 * 这样我们便可以轻易地通过 key 或者 index 获得相对应的信息。
 *
 * 接下来我们还需要处理一个问题：delete操作 将会导致 [0...size-1] 范围上出现空当。
 * 这就会使得我们通过 random.nextInt(size) 获得的 index 很可能并没有对应的 Key 储存在上面。
 * 因此我们将 delete操作 改为如下操作：
 *      1. 首先获得需要删除的 Key 对应的 Index.
 *      2. 获取最后一个 lastIndex 以及对应的 lastKey.
 *      3. 修改两张 HashMap，将要删除的 deleteIndex 对应的 Key 改为 lastKey；
 *      同时将 lastKey 对应的 Index 改为 deleteIndex。最后 size--。
 *      即将 lastKey 与将被删除的 deleteKey 做了了交互（当然 index 也做了相应的修改）
 *      效果就相当于用lastKey去 填补 了因为 delete操作 导致的空当。
 */
import java.util.HashMap;

public class RandomPool {

    public static class Pool<String> {
        private HashMap<String, Integer> keyIndexMap;
        private HashMap<Integer, String> indexKeyMap;
        private int size;

        public Pool() {
            this.keyIndexMap = new HashMap<>();
            this.indexKeyMap = new HashMap<>();
            this.size = 0;
        }

        public void insert(String key) {
            if (!keyIndexMap.containsKey(key)) {
                keyIndexMap.put(key, size);
                indexKeyMap.put(size++, key);
            }
        }

        public void delete(String key) {
            if (keyIndexMap.containsKey(key)) {
                // 获取将被删除的 key 所对应的 deleteIndex
                int deleteIndex = keyIndexMap.get(key);
                // 获取map中的最后一个 index
                int lastIndex = --size;
                // 获取最后一个index所对应的 lastKey
                String lastKey = indexKeyMap.get(lastIndex);
                // 利用 lastKey 覆盖掉 deleteKey 的位置
                // 即将 lastKey 与 deleteKey 的位置信息互换(其实只是将 lastKey 的信息改了而已)
                keyIndexMap.put(lastKey, deleteIndex);
                indexKeyMap.put(deleteIndex, lastKey);
                // 最后在两个 map 中移除所要删除的信息
                keyIndexMap.remove(key);
                indexKeyMap.remove(lastIndex);
            }
        }

        public String getRandom() {
            if (size == 0) {
                return null;
            }
            int randomIndex = (int) (Math.random() * size); // 0 ~ size -1
            return indexKeyMap.get(randomIndex);
        }

    }

    public static void main(String[] args) {
        Pool<String> pool = new Pool<String>();
        pool.insert("boku");
        pool.insert("kimi");
        pool.insert("sekai");
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
        System.out.println(pool.getRandom());
    }

}
