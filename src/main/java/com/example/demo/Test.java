package com.example.demo;


import com.sun.source.tree.Tree;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;

import javax.swing.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    enum Weekday {monday, tuesday, wednesday, thursday, friday, saturday, sunday}

    ;
    EnumSet<Weekday> always = EnumSet.allOf(Weekday.class);
    EnumSet<Weekday> never = EnumSet.noneOf(Weekday.class);
    EnumSet<Weekday> workday = EnumSet.range(Weekday.monday, Weekday.friday);
    EnumSet<Weekday> mwf = EnumSet.of(Weekday.monday, Weekday.wednesday, Weekday.friday);

    public static void main(String[] args)
            throws Exception {
        char[][] A = new char[2][1];
        A[0] = new char[]{'1'};
        A[1] = new char[]{'1'};
        //findSubstringInWraproundString("aa");
        int[][] edges = {{0, 1, 1}, {1, 2, 1}, {2, 3, 2}, {0, 3, 2}, {0, 4, 3}, {3, 4, 3}, {1, 4, 6}};
        //System.out.println("hello world");
        //new Test().canReorderDoubled(new int[]{1,2,1,-8,8,-4,4,-4,2,-2});
        //new Test().findNumOfValidWords(new String[]{"aaaa","asas","able","ability","actt","actor","access"},new String[]{"aboveyz","abrodyz","abslute","absoryz","actresz","gaswxyz"});
        Deque<Integer> que = new ArrayDeque<>();
        new Test().numDistinct("babgbag","bag");
    }
    public int numDistinct(String s, String t) {
        int n=s.length();
        int[][] dp=new int[2][n];
        int m=t.length();
        if(m==0||n==0)  return 0;
        int times=0;
        for(int i=0;i<n;i++){
            if(s.charAt(i)==t.charAt(0))
                times++;
            dp[0][i]=times;
        }
        for(int i=1;i<m;i++){
            int row=i&1;
            dp[row][0]=0;
            for(int j=1;j<n;j++)
                dp[row][j]=dp[row][j-1]+(s.charAt(j)==t.charAt(i)?dp[1-row][j-1]:0);
        }
        return dp[(m-1)&1][n-1];
    }
    public int numDistinct1(String s, String t) {
        int n=s.length();
        int m=t.length();
        if(m>n) return 0;
        int[][] judge=new int[n][m];
        for(int i=0;i<n;i++)
            Arrays.fill(judge[i],-1);
        return numDistinct_backtracing(s,t,n,m,0,0,judge);

    }
    private int numDistinct_backtracing(String s,String t,int n, int m, int s_index,
                                         int t_index,int[][] judge){
        if(judge[s_index][t_index]!=-1){
            return judge[s_index][t_index];
        }
        if(t_index>=m){
            return 1;
        }
        if(n-s_index<m-t_index) return 0;
        int cur_res=0;
        for(int i=s_index;i<=n-m+t_index;i++){
            if(s.charAt(i)==t.charAt(t_index)){
                cur_res+=numDistinct_backtracing(s,t,n,m,i+1,t_index+1,judge);
            }

        }
        judge[s_index][t_index]=cur_res;
        return cur_res;
    }
    public int countHomogenous(String s) {
        int n=s.length();
        int left=0;
        int res=0;
        int mod=1000000007;
        while(left<n){
            int right=left;
            while(right<n-1&&s.charAt(right+1)==s.charAt(right))
                right++;
            res+=((long)(right-left+1)*(right-left+2))/2%mod;
            System.out.println(Integer.MAX_VALUE);
            left=right+1;
        }
        return res;
    }
    public int[][] generateMatrix(int n) {
        int[][] res=new int[n][n];
        int left = 0;
        int right = n - 1;
        int down = 0;
        int up = n - 1;
        int index=1;
        while (left <= right && down <= up) {
            for (int i = left; i < right; i++,index++)
                res[down][i]=index*index;
            for (int i = down; i <= up; i++,index++)
                res[i][right]=index*index;
            if (down != up) {
                for (int i = right - 1; i > left; i--,index++)
                    res[up][i]=index*index;
            }
            if (left != right) {
                for (int i = up; i > down; i--,index++)
                    res[i][left]=index*index;
            }
            left++;
            right--;
            down++;
            up--;
        }
        return res;
    }
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = n - 1;
        int down = 0;
        int up = m - 1;
        while (left <= right && down <= up) {
            for (int i = left; i < right; i++)
                res.add(matrix[down][i]);
            for (int i = down; i <= up; i++)
                res.add(matrix[i][right]);
            if (down != up) {
                for (int i = right - 1; i > left; i--)
                    res.add(matrix[up][i]);
            }
            if (left != right) {
                for (int i = up; i > down; i--)
                    res.add(matrix[i][left]);
            }
            left++;
            right--;
            down++;
            up--;
        }
        return res;
    }

    public int numTimesAllBlue(int[] light) {
        int n = light.length;
        boolean[] is_light = new boolean[n];
        numTimes_unionFind unionFind = new numTimes_unionFind(n);
        int max_num = Integer.MIN_VALUE;
        int res = 0;
        for (int i = 0; i < n; i++) {
            is_light[light[i] - 1] = true;
            max_num = Math.max(max_num, light[i] - 1);
            if (light[i] >= 2 && is_light[light[i] - 2])
                unionFind.union(light[i] - 1, light[i] - 2);
            if (light[i] < n && is_light[light[i]])
                unionFind.union(light[i] - 1, light[i]);
            if (unionFind.isCollected(0, max_num))
                res++;
        }
        return res;
    }

    class numTimes_unionFind {
        int[] parents;

        numTimes_unionFind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public void union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent != j_parent) {
                parents[i_parent] = j_parent;
            }
        }

        public boolean isCollected(int i, int j) {
            return find(i) == find(j);
        }

    }

    public int[][] diagonalSort(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        int begin_x = 0;
        int begin_y = n - 1;
        while (true) {
            List<Integer> list = new ArrayList<>();
            int cur_x = begin_x;
            int cur_y = begin_y;
            while (cur_x < m && cur_y < n) {
                list.add(mat[cur_x][cur_y]);
                cur_x++;
                cur_y++;
            }
            Collections.sort(list);
            cur_x = begin_x;
            cur_y = begin_y;
            int index = 0;
            while (cur_x < m && cur_y < n) {
                mat[cur_x][cur_y] = list.get(index++);
                cur_x++;
                cur_y++;
            }
            if (begin_x == 0 && begin_y == 0) {
                begin_x = 1;
                begin_y = 0;
            } else if (begin_x == 0) {
                begin_y--;
            } else if (begin_x < n - 1) {
                begin_x++;
            } else break;
        }
        return mat;
    }

    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] dp = new int[n];
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1];
            int small_index = 0;
            int mid_index = i - 1;
            while (small_index < i - 1) {
                if (mid_index < small_index)
                    dp[i] += i - 1 - small_index;
                else {
                    while (mid_index > small_index && nums[mid_index] + nums[small_index] > nums[i])
                        mid_index--;
                    dp[i] += i - 1 - mid_index;
                }
                small_index++;
            }
        }
        return dp[n - 1];
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> cur_list = new ArrayList<>();
        subsetsWithDup_helper(res, cur_list, 0, nums.length, nums);
        return res;

    }

    private void subsetsWithDup_helper(List<List<Integer>> res, List<Integer> cur_list, int index, int n, int[] nums) {
        if (index == n) {
            List<Integer> list_copy = new ArrayList<>(cur_list);
            res.add(list_copy);
            return;
        }
        int cur_end = index;
        while (cur_end < n - 1 && nums[cur_end + 1] == nums[cur_end])
            cur_end++;
        List<Integer> tmp_list = new ArrayList<>();
        for (int i = 0; i <= cur_end - index + 1; i++) {
            cur_list.add(nums[index]);
            subsetsWithDup_helper(res, cur_list, cur_end + 1, n, nums);
        }
        for (int i = 0; i < cur_end - index; i++)
            cur_list.remove(cur_list.size() - 1);

    }

    class MyHashMap {
        private final static int size = 1023;
        private LinkedList[] map_list;

        private class pair_map {
            private int x;
            private int y;

            public pair_map(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public int get_x() {
                return x;
            }

            public int get_y() {
                return y;
            }

            public void set_y(int new_y) {
                y = new_y;
            }
        }

        private int hash(int i) {
            return i % size;
        }

        /**
         * Initialize your data structure here.
         */
        public MyHashMap() {
            map_list = new LinkedList[size];
            for (int i = 0; i < size; i++)
                map_list[i] = new LinkedList<pair_map>();
        }

        /**
         * value will always be non-negative.
         */
        public void put(int key, int value) {
            int h = hash(key);
            boolean is_put = false;
            pair_map cur_pair;
            Iterator<pair_map> iterator = map_list[h].iterator();
            while (iterator.hasNext()) {
                if ((cur_pair = iterator.next()).get_x() == key) {
                    cur_pair.set_y(value);
                    is_put = true;
                }
            }
            if (!is_put) map_list[h].offerLast(new pair_map(key, value));
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
         */
        public int get(int key) {
            int h = hash(key);
            pair_map cur_pair;
            Iterator<pair_map> iterator = map_list[h].iterator();
            while (iterator.hasNext()) {
                if ((cur_pair = iterator.next()).get_x() == key) {
                    return cur_pair.get_y();
                }
            }
            return -1;
        }

        /**
         * Removes the mapping of the specified value key if this map contains a mapping for the key
         */
        public void remove(int key) {
            int h = hash(key);
            pair_map cur_pair;
            Iterator<pair_map> iterator = map_list[h].iterator();
            while (iterator.hasNext()) {
                if ((cur_pair = iterator.next()).get_x() == key) {
                    map_list[h].remove(cur_pair);
                }
            }
        }
    }

    class MyHashSet {
        private final static int size = 1023;
        LinkedList[] data;

        /**
         * Initialize your data structure here.
         */
        public MyHashSet() {
            data = new LinkedList[size];
            for (int i = 0; i < size; i++)
                data[i] = new LinkedList<Integer>();
        }

        public void add(int key) {
            int h = hash(key);
            for (int i : (Iterable<Integer>) data[h]) {
                if (i == key)
                    return;
            }
            data[h].offerLast(key);
        }

        public void remove(int key) {
            int h = hash(key);
            for (int i : (Iterable<Integer>) data[h]) {
                if (i == key) {
                    data[h].remove(i);
                    return;
                }
            }
        }

        /**
         * Returns true if this set contains the specified element
         */
        public boolean contains(int key) {
            int h = hash(key);
            for (int i : (Iterable<Integer>) data[h]) {
                if (i == key) {
                    return true;
                }
            }
            return false;
        }

        private int hash(int num) {
            return num % size;
        }
    }

    int isValid_index = 0;
    boolean isValid_true = true;

    public boolean isValidSerialization(String preorder) {
        String[] preorder_list = preorder.split(",");
        inValidSerialization_dfs(preorder_list, preorder_list.length, 0);
        return isValid_true && isValid_index == preorder_list.length - 1;
    }

    private void inValidSerialization_dfs(String[] preorder, int n, int cur_index) {
        if (!isValid_true) return;
        if (cur_index >= n) {
            isValid_true = false;
            return;
        } else {
            if (preorder[cur_index].equals("#")) {
                isValid_index = cur_index;
                return;
            }
        }
        inValidSerialization_dfs(preorder, n, cur_index + 1);
        inValidSerialization_dfs(preorder, n, isValid_index + 1);
    }

    public int calculate(String s) {
        Deque<Integer> num_stack = new ArrayDeque<>();
        Deque<Character> operator_stack = new ArrayDeque<>();
        s = '(' + s + ')';
        int n = s.length();
        int index = 0;
        while (index < n) {
            while (index < n && s.charAt(index) == ' ')
                index++;
            if (index == n) break;
            if (isDigit(s.charAt(index))) {
                int cur_end = index;
                while (cur_end < n && isDigit(s.charAt(cur_end)))
                    cur_end++;
                int cur_num = Integer.parseInt(s.substring(index, cur_end));
                num_stack.push(cur_num);
                index = cur_end;
            } else {
                if (s.charAt(index) == '(')
                    operator_stack.push('(');
                else if (s.charAt(index) == '+' || s.charAt(index) == '-' || s.charAt(index) == ')') {
                    while (!operator_stack.isEmpty() && operator_stack.peek() != '(') {
                        int num1 = num_stack.pop();
                        int num2 = num_stack.isEmpty() ? 0 : num_stack.pop();
                        int cur_num;
                        if (operator_stack.peek() == '+')
                            cur_num = num1 + num2;
                        else if (operator_stack.peek() == '-')
                            cur_num = num2 - num1;
                        else if (operator_stack.peek() == '*')
                            cur_num = num1 * num2;
                        else
                            cur_num = num2 / num1;
                        operator_stack.pop();
                        num_stack.push(cur_num);
                    }

                    if (s.charAt(index) == ')')
                        operator_stack.pop();
                    else
                        operator_stack.push(s.charAt(index));
                } else if (s.charAt(index) == '*' || s.charAt(index) == '/') {
                    while (!operator_stack.isEmpty() && (operator_stack.peek() == '*' || operator_stack.peek() == '/')) {
                        int num1 = num_stack.pop();
                        int num2 = num_stack.isEmpty() ? 0 : num_stack.pop();
                        num_stack.push(operator_stack.pop() == '*' ? num1 * num2 : num2 / num1);
                    }
                    operator_stack.push(s.charAt(index));
                }
                index++;
            }

        }
        return num_stack.pop();
    }

    private boolean isDigit(char a) {
        return a - '0' >= 0 && a - '0' <= 9;
    }

    public int maxChunksToSorted2(int[] arr) {
        int n = arr.length;
        int index = 0;
        int res = 0;
        while (index < n) {
            int cur_max_index = arr[index];
            while (index < cur_max_index) {
                int tmp = cur_max_index;
                for (int i = index; i <= cur_max_index; i++) {
                    cur_max_index = Math.max(cur_max_index, arr[i]);
                }
                index = tmp;
            }
            res++;
        }
        return res;
    }

    public int calculate1(String s) {
        Deque<Integer> num_stack = new ArrayDeque<>();
        Deque<Character> operator_stack = new ArrayDeque<>();
        s = '(' + s + ')';
        int n = s.length();
        int index = 0;
        while (index < n) {
            while (index < n && s.charAt(index) == ' ')
                index++;
            if (index == n) break;
            if (isDigit(s.charAt(index))) {
                int cur_end = index;
                while (cur_end < n && isDigit(s.charAt(cur_end)))
                    cur_end++;
                int cur_num = Integer.parseInt(s.substring(index, cur_end));
                num_stack.push(cur_num);
                index = cur_end;
            } else {
                if (s.charAt(index) == '(')
                    operator_stack.push('(');
                else {
                    while (!operator_stack.isEmpty() && operator_stack.peek() != '(') {
                        int num1 = num_stack.pop();
                        int num2 = num_stack.pop();
                        if (operator_stack.pop() == '+')
                            num_stack.push(num1 + num2);
                        else
                            num_stack.push(num2 - num1);
                    }
                    operator_stack.push(s.charAt(index));
                }
                if (s.charAt(index) == ')')
                    operator_stack.pop();
                index++;
            }

        }
        return num_stack.pop();
    }


    private boolean isOperator(char a) {
        return a == '+' || a == '-';
    }

    public String removeDuplicates(String S) {
        int n = S.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (builder.length() == 0 || builder.charAt(builder.length() - 1) != S.charAt(i))
                builder.append(S.charAt(i));
            else
                builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    public int minCut(String s) {
        int n = s.length();
        int[] dp = new int[n];
        boolean[][] isPalindrome = new boolean[n][n];
        for (int i = 0; i < n; i++)
            for (int j = n - 1; j >= 0; j--) {
                if (j <= i)
                    isPalindrome[i][j] = true;
                else {
                    if (s.charAt(i) == s.charAt(j) && isPalindrome[i + 1][j - 1])
                        isPalindrome[i][j] = true;
                }
            }
        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1] + 1;
            for (int j = 0; j < i; j++)
                if (isPalindrome[j][i])
                    dp[i] = Math.min(dp[i], (j == 0 ? 0 : dp[j - 1] + 1));
        }
        return dp[n - 1];
    }

    public List<List<String>> partition1(String s) {
        List<List<String>> res = new ArrayList<>();
        partition_backtracing(s, s.length(), 0, res, new ArrayList<>());
        return res;
    }

    private void partition_backtracing(String s, int n, int begin, List<List<String>> res, List<String> cur_list) {
        if (begin == n) {
            List<String> copy_list = new ArrayList<>(cur_list);
            res.add(copy_list);
        } else {
            for (int end = begin; end < n; end++) {
                int cur_begin = begin;
                int cur_end = end;
                while (cur_begin < cur_end) {
                    if (s.charAt(cur_begin) == s.charAt(cur_end)) {
                        cur_begin++;
                        cur_end--;
                    } else
                        break;
                }
                if (cur_begin >= cur_end) {
                    cur_list.add(s.substring(begin, end + 1));
                    partition_backtracing(s, n, end + 1, res, cur_list);
                    cur_list.remove(cur_list.size() - 1);
                }
            }
        }
    }

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            while (!stack.isEmpty() && stack.peek() < nums[i % n]) {
                int j = stack.pop();
                if (j < n)
                    res[j] = i % n;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            if (j < n)
                res[j] = -1;
        }
        return res;
    }

    public int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int res = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = (dp[i - 1] < 0 ? 0 : dp[i - 1]) + nums[i];
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[] dp_max = new int[n];
        dp_max[0] = nums[0];
        int[] dp_min = new int[n];
        dp_min[0] = nums[0];
        int res = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] >= 0) {
                dp_max[i] = Math.max(nums[i], dp_max[i - 1] * nums[i]);
                dp_min[i] = Math.min(nums[i], dp_min[i - 1] * nums[i]);
            } else {
                dp_max[i] = Math.max(nums[i], dp_min[i - 1] * nums[i]);
                dp_min[i] = Math.min(nums[i], dp_max[i - 1] * nums[i]);
            }
            res = Math.max(res, dp_max[i]);
        }
        return res;
    }

    class MyQueue {
        Deque<Integer> put_stack;
        Deque<Integer> take_stack;

        /**
         * Initialize your data structure here.
         */
        public MyQueue() {
            put_stack = new ArrayDeque<>();
            take_stack = new ArrayDeque<>();
        }

        /**
         * Push element x to the back of queue.
         */
        public void push(int x) {
            put_stack.push(x);
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public int pop() {
            if (take_stack.isEmpty()) {
                while (!put_stack.isEmpty())
                    take_stack.push(put_stack.pop());
            }
            return take_stack.pop();
        }

        /**
         * Get the front element.
         */
        public int peek() {
            if (take_stack.isEmpty()) {
                while (!put_stack.isEmpty())
                    take_stack.push(put_stack.pop());
            }
            return take_stack.peek();
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return take_stack.isEmpty() && put_stack.isEmpty();
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        return addTwoNumbers_helper(l1, l2, 0);
    }

    private ListNode addTwoNumbers_helper(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null) {
            if (carry == 0)
                return null;
            else
                return new ListNode(1, null);
        }
        int val1 = (l1 == null ? 0 : l1.val);
        int val2 = (l2 == null ? 0 : l2.val);
        int cur_val;
        int cur_carry;
        if (carry + val1 + val2 >= 10) {
            cur_carry = 1;
            cur_val = carry + val1 + val2 - 10;
        } else {
            cur_carry = 0;
            cur_val = carry + val1 + val2;
        }
        return new ListNode(cur_val, addTwoNumbers_helper((l1 == null ? l1 : l1.next), (l2 == null ? l2 : l2.next), cur_carry));

    }

    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0])
                    return Integer.compare(o1[0], o2[0]);
                else
                    return Integer.compare(o2[1], o1[1]);
            }
        });
        int n = envelopes.length;
        int[] dp = new int[n];
        int res = 1;
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (envelopes[1][j] < envelopes[1][i])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public int countTriplets(int[] arr) {

        int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int xor_sum = 0;
            for (int j = i; j < n; j++) {
                xor_sum ^= arr[j];
                if (xor_sum == 0)
                    res += j - i;
            }
        }
        return res;
    }

    public List<Integer> findDuplicates(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int num = Math.abs(nums[i]);
            if (nums[num - 1] < 0)
                list.add(num);
            nums[num - 1] = -nums[num - 1];

        }
        return list;
    }

    public int[] countBits2(int num) {
        int[] res = new int[num + 1];
        res[0] = 0;
        for (int i = 1; i <= num; i++) {
            res[i] = res[i / 2] + i % 2;
        }
        return res;

    }

    int[][] exist_directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] isVisit = new boolean[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (board[i][j] == word.charAt(0)) {
                    isVisit[i][j] = true;
                    if (exist_dfs(board, m, n, word, i, j, 0, isVisit))
                        return true;
                    isVisit[i][j] = false;
                }

        return false;

    }

    //index:当前索引值
    private boolean exist_dfs(char[][] board, int m, int n, String word, int i, int j, int index, boolean[][] isVisit) {

        if (index == word.length() - 1) return true;
        boolean res = false;
        char target = word.charAt(index + 1);
        for (int[] exist_direction : exist_directions) {
            int next_i = i + exist_direction[0];
            int next_j = j + exist_direction[1];
            if (next_i >= 0 && next_i < m && next_j >= 0 && next_j < n && !isVisit[next_i][next_j] && board[next_i][next_j] == target) {
                isVisit[next_i][next_j] = true;
                if (exist_dfs(board, m, n, word, next_i, next_j, index + 1, isVisit))
                    return true;
                isVisit[next_i][next_j] = false;
            }
        }
        return false;
    }


    public int minIncrementForUnique(int[] A) {
        int[] freq = new int[40000];
        int max_val = 0;
        for (int a : A) {
            freq[a]++;
            max_val = Math.max(a, max_val);
        }
        int res = 0;
        for (int i = 0; i < max_val; i++) {
            if (freq[i] > 1) {
                res += freq[i] - 1;
                freq[i + 1] += freq[i] - 1;
            }
        }
        return res + (freq[max_val] - 1) * freq[max_val] / 2;
    }

    class NumMatrix {
        int[][] prefix;

        public NumMatrix(int[][] matrix) {
            int m = matrix.length;
            int n = matrix[0].length;
            prefix = new int[m + 1][n + 1];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    prefix[i + 1][j + 1] = matrix[i][j] + prefix[i][j] - prefix[i + 1][j] - prefix[i][j + 1];
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            return prefix[row2 + 1][col2 + 1] + prefix[row1][col1] - prefix[row1][col2 + 1] - prefix[row2 + 1][col1];
        }
    }

    public int findLengthOfShortestSubarray1(int[] arr) {
        int n = arr.length;
        int left_end = 0;
        int right_begin = n - 1;
        while (left_end < n - 1 && arr[left_end] <= arr[left_end + 1])
            left_end++;
        if (left_end == n - 1) return 0;
        while (right_begin > 0 && arr[right_begin] >= arr[right_begin - 1])
            right_begin--;
        if (arr[left_end] <= arr[right_begin])
            return right_begin - left_end - 1;
        int index = right_begin;
        int res = Math.min(right_begin, n - left_end - 1);
        for (int i = 0; i <= left_end; i++) {
            while (index < n && arr[index] < arr[i])
                index++;
            res = Math.min(res, index - i - 1);

        }
        return res;

    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.get(triangle.size() - 1).size();
        int[] res = new int[n];
        for (int i = 0; i < n; i++)
            res[i] = triangle.get(triangle.size() - 1).get(i);
        int m = triangle.size();
        for (int i = m - 2; i >= 0; i--) {
            for (int j = 0; j < triangle.get(i).size(); j++)
                res[j] = Math.min(res[j], res[j + 1]) + triangle.get(i).get(j);
        }
        return res[0];
    }

    public int smallestDifference(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        int index_1 = 0;
        int index_2 = 0;
        int n = a.length;
        int m = b.length;
        long res = Integer.MAX_VALUE;
        while (index_1 < n && index_2 < m) {
            res = Math.min(res, Math.abs((long) a[index_1] - (long) b[index_2]));
            if (a[index_1] > b[index_2])
                index_2++;
            else
                index_1++;
        }
        return (int) res;
    }

    class NumArray {
        int[] prefix;

        public NumArray(int[] nums) {
            int n = nums.length;
            prefix = new int[n + 1];
            prefix[1] = nums[0];
            for (int i = 2; i <= n; i++)
                prefix[i] = prefix[i - 1] + nums[i - 1];
        }

        public int sumRange(int i, int j) {
            return prefix[j + 1] - prefix[i];
        }
    }

    public boolean isMonotonic(int[] A) {
        int n = A.length;
        if (n == 1) return true;
        int positive = 0;
        for (int i = 1; i < n; i++) {
            if (A[i] < A[i - 1]) {
                if (positive == 1)
                    return false;
                positive = -1;
            } else if (A[i] > A[i - 1]) {
                if (positive == -1)
                    return false;
                positive = 1;
            }

        }
        return true;

    }

    public List<Integer> findNumOfValidWords(String[] words, String[] puzzles) {
        Map<Integer, Integer> map = new HashMap<>();
        for (String word : words) {
            int cur_int = 0;
            int n = word.length();
            for (int i = 0; i < n; i++) {
                int tmp = word.charAt(i) - 'a';
                cur_int = (cur_int | (1 << tmp));
            }
            map.put(cur_int, map.getOrDefault(cur_int, 0) + 1);
        }
        List<Integer> res = new ArrayList<>();
        for (String puzzle : puzzles) {
            int cur_res = 0;
            int cur_int = 0;
            int n = puzzle.length();
            for (int i = 0; i < n; i++) {
                int tmp = puzzle.charAt(i) - 'a';
                cur_int = (cur_int | (1 << tmp));
            }
            int addition = 1 << (puzzle.charAt(0) - 'a');
            cur_int = (cur_int & ~addition);
            if (map.containsKey(addition))
                cur_res += map.get(addition);
            for (int i = cur_int; i != 0; i = (i - 1) & cur_int) {
                if (map.containsKey(i | addition))
                    cur_res += map.get(i | addition);
            }
            res.add(cur_res);
        }
        return res;
    }

    public int longestSubstring(String s, int k) {
        return longestSubstring_helper(s, k, 0, s.length() - 1);
    }

    private int longestSubstring_helper(String s, int k, int left, int right) {
        int n = s.length();
        if (n == 0) return -1;
        int[] freqs = new int[26];
        int res = -1;
        for (int i = left; i <= right; i++)
            freqs[s.charAt(i) - 'a']++;
        int cur_left = left;
        int cur_right = left;
        while (cur_right <= right) {
            int freq = freqs[s.charAt(cur_right) - 'a'];
            if (freq >= 1 && freq < k) {
                res = Math.max(longestSubstring_helper(s, k, cur_left, cur_right - 1), res);
                cur_left = cur_right;
            }
            cur_right++;
        }
        if (cur_left == left) return right - left + 1;
        else return Math.max(longestSubstring_helper(s, k, cur_left, cur_right - 1), res);
    }

    public int minSubarray(int[] nums, int p) {
        int n = nums.length;
        int[] prefix = new int[n];
        prefix[0] = nums[0] % p;
        for (int i = 1; i < n; i++) {
            prefix[i] = (prefix[i - 1] + nums[i] % p) % p;
        }
        if (prefix[n - 1] == 0) return 0;
        int res = Integer.MAX_VALUE;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < n; i++) {
            int tmp = (prefix[i] - prefix[n - 1] + p) % p;
            if (map.containsKey(tmp))
                res = Math.min(res, i - map.get(tmp));
            map.put(prefix[i] % p, i);
        }
        return (res == Integer.MAX_VALUE || res == n - 1) ? -1 : res;
    }

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        res[0] = nums[0];
        for (int i = 1; i < n; i++) {
            res[i] = res[i - 1] * nums[i];
        }
        int suffix = 1;
        for (int i = n - 1; i >= 1; i--) {
            res[i] = res[i - 1] * suffix;
            suffix *= nums[i];
        }
        res[0] = suffix;
        return res;
    }

    public int[][] transpose(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] res = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = matrix[j][i];
            }
        }
        return res;
    }

    public int[][] flipAndInvertImage(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int tmp = A[i][left];
                A[i][left] = (A[i][right] == 0 ? 1 : 0);
                A[i][right] = (tmp == 0 ? 1 : 0);
                left++;
                right--;
            }
            if (left == right) A[i][left] = 1 - A[i][left];
        }
        return res;
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;
        if (n == 0) return false;
        int left = 0;
        int right = n - 1;
        int up = m - 1;
        int down = 0;
        while (left <= right && down <= up) {
            int tmp_down = down;
            int tmp_up = up;
            while (tmp_down < tmp_up) {
                int mid = (tmp_down + tmp_up) / 2;
                if (matrix[mid][right] < target)
                    tmp_down = mid + 1;
                else if (matrix[mid][right] == target) return true;
                else tmp_up = mid;
            }
            int tmp_down1 = down;
            int tmp_up1 = up;
            while (tmp_down1 < tmp_up1) {
                int mid = (tmp_down1 + tmp_up1) / 2 + 1;
                if (matrix[mid][left] > target)
                    tmp_up1 = mid - 1;
                else if (matrix[mid][left] == target) return true;
                else tmp_down1 = mid;
            }
            down = tmp_down;
            up = tmp_up1;
            int tmp_left = left;
            int tmp_right = right;
            while (tmp_left < tmp_right) {
                int mid = (tmp_left + tmp_right) / 2 + 1;
                if (matrix[down][mid] > target)
                    tmp_right = mid - 1;
                else if (matrix[down][mid] == target) return true;
                else tmp_left = mid;
            }
            int tmp_left1 = left;
            int tmp_right1 = right;
            while (tmp_left1 < tmp_right1) {
                int mid = (tmp_left1 + tmp_right1) / 2;
                if (matrix[up][mid] < target)
                    tmp_left1 = mid + 1;
                else if (matrix[up][mid] == target) return true;
                else tmp_right1 = mid;
            }
            left = tmp_left1;
            right = tmp_right;
            if (left == right && up == down) return matrix[up][left] == target;
        }
        return matrix[up][left] == target;
    }

    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int n = customers.length;
        int origin = 0;
        int add = 0;

        for (int i = 0; i < X; i++)
            if (grumpy[i] == 0)
                origin += customers[i];
            else
                add += customers[i];
        int max_add = add;
        for (int i = X; i < n; i++) {
            if (grumpy[i - X] == 1)
                add -= customers[i - X];
            if (grumpy[i] == 0)
                origin += customers[i];
            else
                add += customers[i];
            max_add = Math.max(max_add, add);
        }
        return max_add + origin;
    }

    public boolean isToeplitzMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;
        int j = n - 1;
        while (i != m - 1 || j != 0) {
            int next_i = i + 1;
            int next_j = j + 1;
            if (next_i < m && next_j < n) {
                if (matrix[next_i][next_j] != matrix[i][j])
                    return false;
                i = next_i;
                j = next_j;
            } else {
                i = (next_i > next_j ? next_i - next_j : 0);
                j = (next_i > next_j ? 0 : next_j - next_i);
            }
        }
        return true;
    }

    public int maxChunksToSorted(int[] arr) {
        int[] nums = new int[10];
        int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int num = arr[i];
            nums[num]++;
            if (num <= i) {
                int j;
                for (j = num + 1; j < 10; j++)
                    if (nums[j] > 0)
                        break;
                if (j == 10) res++;
            }
        }
        return res;
    }

    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int sum = 0;
        int n = arr.length;
        int res = 0;
        for (int i = 0; i < k; i++)
            sum += arr[i];
        if (sum >= k * threshold)
            res++;
        for (int i = k; i < n; i++) {
            sum = sum - arr[i - k] + arr[i];
            if (sum >= k * threshold)
                res++;
        }
        return res;
    }

    public int longestSubarray(int[] nums, int limit) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int n = nums.length;
        int left = 0;
        int right = 0;
        int res = 0;
        while (right < n) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
            while (map.lastKey() - map.firstKey() > limit) {
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0)
                    map.remove(nums[left]);
            }
            res = Math.max(res, right - left + 1);
            right++;
        }
        return res;
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> cur_list = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum2_backtracing(candidates, target, 0, res, cur_list);
        return res;
    }

    private void combinationSum2_backtracing(int[] candidates, int target, int index,
                                             List<List<Integer>> res, List<Integer> cur_list) {
        if (target == 0) {
            List<Integer> list_copy = new ArrayList<>(cur_list);
            res.add(list_copy);
            return;
        }
        if (index >= candidates.length) return;
        for (int i = index; i < candidates.length; i++) {
            if (candidates[i] > target)
                return;
            int times = 1;
            while (i < candidates.length - 1 && candidates[i] == candidates[i + 1]) {
                times++;
                i++;
            }
            for (int j = 1; j <= times; j++) {
                if (target < candidates[i] * j)
                    break;
                for (int k = 0; k < j; k++)
                    cur_list.add(candidates[i]);
                combinationSum2_backtracing(candidates, target - j * candidates[i], i + 1, res, cur_list);
                for (int k = 0; k < j; k++)
                    cur_list.remove(cur_list.size() - 1);
            }

        }
    }

    public int findShortestSubArray(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            List<Integer> list = map.getOrDefault(nums[i], new ArrayList<>());
            list.add(i);
            map.put(nums[i], list);
        }
        int res = Integer.MAX_VALUE;
        int max_freq = 0;
        for (int num : map.keySet()) {
            List<Integer> list = map.get(num);
            if (list.size() == max_freq) {
                //max_freq=Math.max(list.size(),max_freq);
                res = Math.min(res, list.get(list.size() - 1) - list.get(0) + 1);
            } else if (list.size() > max_freq) {
                max_freq = list.size();
                res = list.get(list.size() - 1) - list.get(0) + 1;
            }
        }
        return res;
    }

    class SubrectangleQueries {
        int[][] rectangle;
        List<int[]> list;

        public SubrectangleQueries(int[][] rectangle) {
            this.rectangle = rectangle;
            list = new ArrayList<>();
        }

        public void updateSubrectangle(int row1, int col1, int row2, int col2, int newValue) {
            list.add(new int[]{row1, col1, row2, col2, newValue});

        }

        public int getValue(int row, int col) {
            int size = list.size();
            for (int i = size - 1; i >= 0; i--) {
                int row1 = list.get(i)[0];
                int col1 = list.get(i)[1];
                int row2 = list.get(i)[2];
                int col2 = list.get(i)[3];
                int val = list.get(i)[4];
                if (row >= row1 && row <= row2 && col >= col1 && col <= col2)
                    return val;
            }
            return rectangle[row][col];
        }
    }

    public boolean canReorderDoubled(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        int n = arr.length;
        Integer[] arr_copy = new Integer[n];
        for (int i = 0; i < n; i++) {
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
            arr_copy[i] = arr[i];
        }
        Arrays.sort(arr_copy, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(Math.abs(o1), Math.abs(o2));
            }
        });
        for (int i = 0; i < n; i++) {
            if (arr_copy[0] == 0) {
                if (map.containsKey(0)) {
                    if (map.get(0) % 2 != 0) return false;
                    map.remove(0);
                }
            }
            if (map.containsKey(arr_copy[i])) {
                int time = map.get(arr_copy[i]);
                if (!map.containsKey(arr_copy[i] * 2) || map.get(arr_copy[i] * 2) < 2 * time) return false;
                if (map.get(arr_copy[i] * 2) == 2 * time) map.remove(arr_copy[i] * 2);
                else map.put(arr_copy[i] * 2, map.get(arr_copy[i] * 2) - 2 * time);
                map.remove(arr_copy[i]);
            }
        }
        return true;
    }

    public TreeNode buildTree_1(int[] inorder, int[] postorder) {
        int n = postorder.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++)
            map.put(inorder[i], i);
        TreeNode root = buildTree_dfs(postorder, map, 0, n - 1, n - 1);
        return root;
    }

    private TreeNode buildTree_dfs(int[] postorder, Map<Integer, Integer> map,
                                   int left, int right, int post_pos) {
        if (right < left) return null;
        TreeNode root = new TreeNode(postorder[post_pos]);
        int index = map.get(postorder[post_pos]);
        root.left = buildTree_dfs(postorder, map, left, index - 1, post_pos - right + index - 1);
        root.right = buildTree_dfs(postorder, map, index + 1, right, post_pos - 1);
        return root;
    }

    public List<Integer> pancakeSort(int[] arr) {
        int index = arr.length - 1;
        List<Integer> res = new ArrayList<>();
        while (index >= 0) {
            if (arr[index] != index + 1) {
                int i;
                for (i = 0; i < index; i++)
                    if (arr[i] == index + 1)
                        break;
                res.add(i);
                reverse_array(arr, i);
                reverse_array(arr, index);
                res.add(index);
            }
            index--;
        }
        return res;
    }

    private void reverse_array(int[] arr, int n) {
        int left = 0;
        int right = n;
        while (left < right) {
            int tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }

    public int longestOnes(int[] A, int K) {
        int n = A.length;
        int left = 0;
        int right = 0;
        int cur_k = 0;
        int res = 0;
        while (right < n) {
            if (A[right] == 0) cur_k++;
            if (cur_k > K) {
                while (left < n && A[left] == 1)
                    left++;
                cur_k--;
                left++;
            }
            right++;
            res = Math.max(res, right - left);
        }
        return res;
    }

    public int getWinner(int[] arr, int k) {
        int max_num = Integer.MIN_VALUE;
        int n = arr.length;
        for (int i = 0; i < Math.min(n, k + 1); i++)
            max_num = Math.max(max_num, arr[i]);
        if (arr[0] == max_num)
            return 0;
        for (int i = 1; i < n; i++) {
            if (k - 1 + i < n)
                max_num = Math.max(max_num, arr[k - 1 + i]);
            if (arr[i] == max_num)
                return i;
        }
        return -1;
    }

    public int findDuplicate(int[] nums) {
        int n = nums.length;
        int org_total = n * (n - 1) / 2;
        for (int num : nums)
            org_total -= num;
        return org_total;
    }

    public int minKBitFlips(int[] A, int K) {
        int n = A.length;
        int ans = 0, revCnt = 0;
        for (int i = 0; i < n; ++i) {
            if (i >= K && A[i - K] > 1) {
                revCnt += 1;
                A[i - K] -= 2; // 复原数组元素，若允许修改数组 A，则可以省略
            }
            if ((A[i] + revCnt) % 2 == 0) {
                if (i + K > n) {
                    return -1;
                }
                ++ans;
                revCnt += 1;
                A[i] += 2;
            }
        }
        return ans;
    }

    public int numFriendRequests(int[] ages) {
        int[] age_num = new int[121];
        for (int age : ages)
            age_num[age]++;
        int res = 0;
        for (int i = 15; i <= 120; i++) {
            int begin = i / 2 + 8;
            for (int j = begin; j <= i; j++)
                res += age_num[i] * age_num[j];
            res -= age_num[i];
        }
        return res;
    }

    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean row_0 = false;
        boolean col_0 = false;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (matrix[i][j] == 0) {
                    if (i == 0) row_0 = true;
                    if (j == 0) col_0 = true;
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0)
                    matrix[i][j] = 0;
            }
        if (row_0) {

            for (int j = 0; j < n; j++)
                matrix[0][j] = 0;
        }
        if (col_0) {
            for (int i = 0; i < m; i++)
                matrix[i][0] = 0;
        }

    }

    public int movesToMakeZigzag(int[] nums) {
        int res_odd = 0;
        int res_even = 0;
        int n = nums.length;
        if (n == 1) return 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 1) {
                int tmp = Math.min(nums[i - 1], (i + 1 < n ? nums[i + 1] : Integer.MAX_VALUE));
                res_odd += (nums[i] < tmp ? 0 : nums[i] - tmp - 1);
            } else {
                int tmp = Math.min((i - 1 < 0 ? Integer.MAX_VALUE : nums[i - 1]), (i + 1 < n ? nums[i + 1] : Integer.MAX_VALUE));
                res_even += (nums[i] < tmp ? 0 : nums[i] - tmp - 1);
            }
        }
        return Math.min(res_even, res_odd);
    }

    public int countSquares(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[] up = new int[n];
        int[][] dp = new int[2][n + 1];
        int res = 0;
        for (int i = 0; i < m; i++) {
            int left = 0;
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    left++;
                    up[j]++;
                } else {
                    left = 0;
                    up[j] = 0;
                    continue;
                }
                int cur = i & 1;
                if (Math.min(left, up[j]) - 1 >= dp[1 - cur][j])
                    dp[cur][j + 1] = dp[1 - cur][j] + 1;
                else
                    dp[cur][j + 1] = Math.min(left, up[j]);
                res += dp[cur][j + 1];
            }
        }
        return res;

    }

    public int[][] matrixReshape(int[][] nums, int r, int c) {
        int row = nums.length;
        int col = nums[0].length;
        if (row * col != r * c) return nums;
        int[][] res = new int[r][c];
        int index = 0;
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++) {
                int cur_row = index / row;
                int cur_col = index % row;
                res[i][j] = nums[cur_row][cur_col];
                index++;
            }
        return res;
    }

    class ProductOfNumbers {
        LinkedList<Integer> list;

        public ProductOfNumbers() {
            list = new LinkedList<>();
        }

        public void add(int num) {
            int n = list.size();
            for (int i = 0; i < n; i++) {
                int tmp = list.get(i);
                list.remove(i);
                list.add(i, tmp * num);
            }

            list.add(num);
        }

        public int getProduct(int k) {
            return list.get(list.size() - k);
        }
    }

    public int removeDuplicates(int[] nums) {
        int index = 0;
        int left = 0;
        int right = 0;
        while (right < nums.length) {
            while (nums[right] == nums[right + 1])
                right++;
            int cur_num = right - left + 1;
            nums[index++] = nums[left];
            if (cur_num > 1) {
                nums[index++] = nums[left];
            }
            left = right + 1;
            right++;
        }
        return index;

    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int[] cand = new int[2];
        int[] cnt = new int[2];
        for (int num : nums) {
            if (cand[0] == num) {
                cnt[0]++;
                continue;
            } else if (cand[1] == num) {
                cnt[1]++;
                continue;
            }
            if (cnt[0] == 0) {
                cand[0] = num;
                cnt[0] = 1;
                continue;
            } else if (cnt[1] == 0) {
                cand[1] = num;
                cnt[1] = 1;
                continue;
            }
            cnt[0]--;
            cnt[1]--;
        }
        cnt[0] = 0;
        cnt[1] = 0;
        for (int num : nums) {
            if (num == cand[0]) cnt[0]++;
            if (num == cand[1]) cnt[1]++;
        }
        int n = nums.length;
        if (cnt[0] > n / 3) res.add(cand[0]);
        if (cnt[1] > n / 3 && cand[1] != cand[0]) res.add(cand[1]);
        return res;

    }


    public boolean isIdealPermutation(int[] A) {
        int n = A.length;
        for (int i = 0; i < n; i++)
            if (A[i] < i - 1 || A[i] > i + 1)
                return false;
        return true;
    }


    public boolean search(int[] nums, int target) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        while (left < right) {
            while (left < n - 1 && nums[left] == nums[left + 1])
                left++;
            while (right > left && nums[right] == nums[right - 1])
                right--;
            int mid = (left + right) / 2;
            if (nums[mid] >= nums[left]) {
                if (target >= nums[left] && target <= nums[mid])
                    right = mid;
                else
                    left = mid + 1;
            } else {
                if (target > nums[mid] && target <= nums[right])
                    left = mid + 1;
                else
                    right = mid;
            }
        }
        return target == nums[left];
    }

    public int numOfSubarrays(int[] arr) {
        int[] freq = new int[2];
        freq[0] = 1;
        int n = arr.length;
        int sum = 0;
        int res = 0;
        int mod = 1000000007;
        for (int value : arr) {
            sum = (sum + value) % 2;
            if (sum == 0) res = (res + freq[1] % mod) % mod;
            else res = (res + freq[0] % mod) % mod;
        }
        return res;
    }

    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        int n = nums.length;
        for (int i = 0; i < n; i += 2)
            res += nums[i];
        return res;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        TreeNode pre_node = null;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                pre_node = node;
                node = node.left;
            }
            node = stack.pop();
            if (node.right == null || node.right == pre_node) {
                res.add(node.val);
                pre_node = node;
                node = null;
            } else {
                stack.push(node);
                pre_node = node;
                node = node.right;
            }

        }
        return res;
    }

/*
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode node = root;
        while (node != null) {
            if (node.left == null) {
                node = node.right;
            } else {
                TreeNode tmp_node = node.left;
                while (tmp_node.right != null && tmp_node.right != node)
                    tmp_node = tmp_node.right;
                if (tmp_node.right == null) {
                    tmp_node.right = node;
                    node = node.left;
                } else {
                    tmp_node.right = null;
                    node = node.right;
                }
            }
        }
    }

 */


    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            res.add(node.val);
            node = node.right;
        }
        return res;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        TreeNode node1 = root;
        while (node1 != null) {
            if (node1.left == null) {
                res.add(node1.val);
                node1 = node1.right;
            } else {
                TreeNode node2 = node1.left;
                while (node2.right != null && node2.right != node1)
                    node2 = node2.right;
                if (node2.right == null) {
                    res.add(node1.val);
                    node2.right = node1;
                    node1 = node1.left;
                } else {
                    node2.right = null;
                    node1 = node1.right;
                }

            }
        }
        return res;
    }

    private void preorder_dfs(List<Integer> res, TreeNode root) {
        if (root == null)
            return;
        res.add(root.val);
        preorder_dfs(res, root.left);
        preorder_dfs(res, root.right);
    }

    public int findPeakElement(int[] nums) {
        if (nums.length == 1) return 0;
        int n = nums.length;
        int left = 0;
        int right = nums.length - 1;
        int mid;
        while (true) {
            mid = (left + right) / 2;
            if (mid == 0) {
                if (nums[0] > nums[1]) break;
                left = 1;
            } else if (mid == n - 1) {
                if (nums[n - 1] > nums[n - 2]) break;
                right = n - 2;
            } else {
                if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) break;
                else if (nums[mid - 1] > nums[mid])
                    right = mid;
                else
                    left = mid;
            }
        }
        return mid;
    }

    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        Deque<Integer> pre = new ArrayDeque<>();
        int[] pre_index = new int[n];
        for (int i = 0; i < n; i++) {
            int cur_num = arr[i];
            while (!pre.isEmpty() && arr[pre.peekLast()] >= cur_num)
                pre.pollLast();
            pre_index[i] = (pre.isEmpty() ? -1 : pre.peekLast());
            pre.offerLast(i);
        }
        Deque<Integer> post = new ArrayDeque<>();
        int[] post_index = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int cur_num = arr[i];
            while (!pre.isEmpty() && arr[post.peekLast()] > cur_num)
                post.pollLast();
            post_index[i] = (pre.isEmpty() ? n : post.peekLast());
            post.offerLast(i);
        }
        int mod = 1000000007;
        int res = 0;
        for (int i = 0; i < n; i++) {
            res += (i - pre_index[i]) * (post_index[i] - i) % mod * arr[i] % mod;
        }
        return res;
    }

    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (nums[mid] < nums[right])
                right = mid;
            else
                left = mid + 1;
        }
        return left;
    }

    public int minFlipsMonoIncr(String S) {
        char[] array = S.toCharArray();
        List<Integer> pre_1 = new ArrayList<>();
        pre_1.add(0);
        List<Integer> post_0 = new ArrayList<>();
        post_0.add(0);
        int n = array.length;
        int index = 0;
        int num = 0;
        while (index < n) {
            if (array[index] == '1') {
                while (array[index] == '1') {
                    index++;
                    num++;
                }
            } else {
                while (array[index] == '0') {
                    index++;
                }
            }
            pre_1.add(num);
        }
        index = n - 1;
        num = 0;
        while (index >= 0) {
            if (array[index] == '1') {
                while (array[index] == '1') {
                    index--;
                }
            } else {
                while (array[index] == '0') {
                    index--;
                    num++;
                }
            }
            post_0.add(num);
        }
        int times = pre_1.size() - 1;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i <= times; i++)
            res = Math.min(res, pre_1.get(i) + post_0.get(times - i));
        return res;
    }

    public int findMinFibonacciNumbers(int k) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        int res = 1;
        while (true) {
            int a = list.get(list.size() - 1);
            int b = list.get(list.size() - 2);
            if (a + b > k)
                break;
            list.add(a + b);
        }
        k -= list.get(list.size() - 1);
        while (k > 0) {
            int index = list.size() - 1;
            while (list.get(index) > k)
                index--;
            res++;
            k -= list.get(index);
        }
        return res;
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = 0;
        int res = 0;
        while (right < n) {
            while (left < n && nums[left] == 0)
                left++;
            right = left;
            while (right < n && nums[right] == 1)
                right++;
            res = Math.max(res, right - left);
            left = right;
        }
        return res;
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        int index = 0;
        int n = nums.length;
        while (index < n) {
            while (index < n && (nums[index] == 0 || nums[index] == index + 1))
                index++;
            if (index == n) break;
            int cur_index = index;
            int next_index = nums[cur_index] - 1;
            nums[cur_index] = 0;
            while (next_index >= 0 && nums[next_index] != next_index + 1) {
                int tmp = nums[next_index] - 1;
                nums[next_index] = next_index + 1;
                next_index = tmp;
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if (nums[i] == 0)
                res.add(i + 1);
        return res;
    }

    public int minSwapsCouples(int[] row) {
        int n = row.length / 2;
        minSwapsCouples_unionfind unionfind = new minSwapsCouples_unionfind(n);
        for (int i = 0; i < n; i++) {
            int node1 = row[2 * i] / 2;
            int node2 = row[2 * i + 1] / 2;
            if (node1 != node2) unionfind.union(node1, node2);
        }
        int res = 0;
        for (int i = 0; i < n; i++)
            if (i == unionfind.find(i))
                res += unionfind.get_group_num(i) - 1;
        return res;

    }

    class minSwapsCouples_unionfind {
        int[] parents;
        int[] size;

        public minSwapsCouples_unionfind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
            size = new int[n];
            Arrays.fill(size, 1);
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            else {
                parents[i_parent] = j_parent;
                size[j_parent] += size[i_parent];
            }

            return true;
        }

        public int get_group_num(int i) {
            return size[i];
        }
    }

    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        return numSubarray_helper(A, R) - numSubarray_helper(A, L - 1);
    }

    private int numSubarray_helper(int[] A, int k) {
        int n = A.length;
        int left = 0;
        int right = 0;
        int res = 0;
        for (; right < n; right++) {
            if (A[right] > k) {
                int tmp = right - left;
                res += tmp * (tmp + 1) / 2;
                left = right + 1;
            }
        }
        if (A[n - 1] <= k) {
            int tmp = n - left;
            res += tmp * (tmp + 1) / 2;
        }
        return res;
    }

    public int totalFruit(int[] tree) {
        int n = tree.length;
        int[] freq = new int[n + 1];
        int left = 0;
        int right = 0;
        int cur_num = 0;
        int res = 0;
        while (right < n) {
            if (freq[tree[right]] == 0)
                cur_num++;
            freq[tree[right]]++;
            right++;
            while (cur_num > 2) {
                freq[tree[left]]--;
                if (freq[tree[left]] == 0)
                    cur_num--;
                left++;
            }
            res = Math.max(res, right - left);
        }
        return res;
    }

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int res = 0;
        int n = nums.length;
        int left = 0;
        int right = 0;
        int cur_multi = 1;
        while (right < n) {
            cur_multi *= nums[right];
            right++;
            while (cur_multi >= k) {
                cur_multi /= nums[left];
                left++;
            }
            res += right - left;
        }
        return res;
    }

    public int characterReplacement(String s, int k) {
        int n = s.length();
        int left = 0;
        int right = 0;
        int[] freqs = new int[26];
        int res = 0;
        while (right < n) {
            freqs[s.charAt(right) - 'A']++;
            right++;
            int max_freq = 0;
            for (int freq : freqs)
                max_freq = Math.max(freq, max_freq);
            int others = right - left - max_freq;
            while (others > k) {
                freqs[s.charAt(left) - 'A']--;
                left++;
                others--;
            }
            res = Math.max(res, right - left);
        }
        return res;
    }

    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = 0;
        int res = Integer.MAX_VALUE;
        int cur_sum = 0;
        while (right < n) {
            cur_sum += nums[right];
            right++;
            boolean isOk = false;
            while (cur_sum - nums[left] >= target) {
                isOk = true;
                cur_sum -= nums[left];
                left++;
            }
            if (isOk) res = Math.min(res, right - left);
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    public int subarraysWithKDistinct(int[] A, int K) {
        return inAreaWithKDistinct(A, K) - inAreaWithKDistinct(A, K - 1);
    }

    private int inAreaWithKDistinct(int[] A, int k) {
        int n = A.length;
        int[] freq = new int[n + 1];
        int res = 0;
        int left = 0;
        int right = 0;
        int cnt = 0;
        while (right < n) {
            if (freq[A[right]] == 0)
                cnt++;
            freq[A[right]]++;
            right++;
            while (cnt > k) {
                if (freq[A[left]] == 1)
                    cnt--;
                freq[A[left]]--;
                left++;
            }
            res += right - left;
        }
        return res;
    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> pre_list = new ArrayList<>();
        List<Integer> cur_list = new ArrayList<>();
        for (int i = 0; i <= rowIndex; i++) {
            cur_list = new ArrayList<>();
            cur_list.add(1);
            for (int j = 0; j < pre_list.size(); j++) {
                if (j == pre_list.size() - 1)
                    cur_list.add(1);
                else
                    cur_list.add(pre_list.get(j) + pre_list.get(j + 1));
            }
            pre_list = cur_list;
        }
        return cur_list;
    }

    public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 > len2) return false;
        int[] table = new int[26];
        for (int i = 0; i < len1; i++) {
            table[s1.charAt(i) - 'a']++;
        }
        int left = 0;
        for (int right = 0; right < len2; right++) {
            int index = s2.charAt(right) - 'a';
            table[index]--;
            while (table[index] < 0) {
                table[s2.charAt(left) - 'a']++;
                left++;
            }
            if (right - left + 1 == len1) return true;
        }
        return false;
    }

    class KthLargest {
        Queue<Integer> que;
        int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            que = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1 - o2;
                }
            });
            int n = nums.length;
            int i = 0;
            for (; i < n && i < k; i++)
                que.offer(nums[i]);
            while (i < n) {
                if (nums[i] >= que.peek()) {
                    que.poll();
                    que.offer(nums[i]);
                }
            }
        }

        public int add(int val) {
            if (que.size() < k)
                que.offer(val);
            else {
                if (val >= que.peek()) {
                    que.poll();
                    que.offer(val);
                }
            }
            return que.peek();

        }
    }

    public int maxTurbulenceSize_2(int[] A) {
        int n = A.length;
        if (n == 1) return 1;
        int begin = 0;
        int res = 1;
        while (begin < n) {
            int end = begin + 1;
            while (end < n && A[end - 1] == A[end])
                end++;
            begin = end - 1;
            int pre_flag = Integer.compare(A[begin], A[begin + 1]);
            while (end < n - 1 && pre_flag * Integer.compare(A[end], A[end + 1]) == -1) {
                pre_flag = Integer.compare(A[end], A[end + 1]);
                end++;
            }
            res = Math.max(res, end - begin + 1);
            begin = end;
        }
        return res;
    }

    public boolean canJump(int[] nums) {
        int n = nums.length;
        canJump_unionSet unionSet = new canJump_unionSet(n);
        int cur_index = 0;
        while (true) {
            int jump = nums[cur_index];
            for (int i = 1; i <= jump && cur_index + i < n; i++)
                unionSet.union(cur_index, cur_index + i);
            int next_index;
            for (next_index = cur_index + 1; next_index < n; next_index++)
                if (unionSet.union(0, next_index))
                    break;
            if (next_index == n) break;
            cur_index = next_index;
        }
        return unionSet.isCollected(0, n - 1);
    }

    class canJump_unionSet {
        int[] parents;

        public canJump_unionSet(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            else
                parents[i_parent] = j_parent;
            return true;
        }

        public boolean isCollected(int i, int j) {
            return find(i) == find(j);
        }
    }

    public int longestSubarray(int[] nums) {
        int pre_begin = 0;
        int n = nums.length;
        while (pre_begin < n && nums[pre_begin] == 0)
            pre_begin++;
        if (pre_begin == n) return 0;
        int pre_end = pre_begin;
        while (pre_end < n && nums[pre_end] == 1)
            pre_end++;
        if (pre_begin == 0 && pre_end == n) return n - 1;
        int res = pre_end - pre_begin;
        while (pre_end < n) {
            int cur_begin = pre_end;
            while (cur_begin < n && nums[cur_begin] == 0)
                cur_begin++;
            int cur_end = cur_begin;
            while (cur_end < n && nums[cur_end] == 1)
                cur_end++;
            if (cur_begin == pre_end + 1)
                res = Math.max(res, cur_end - pre_begin - 1);
            else
                res = Math.max(res, pre_end - pre_begin + 1);
            pre_begin = cur_begin;
            pre_end = cur_end;
        }
        return res;
    }

    public boolean checkPossibility(int[] nums) {
        int n = nums.length;
        int num = 0;
        int index = -1;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[i - 1]) {
                index = i;
                num++;
            }
        }
        if (index == -1) return true;
        if (num > 1) return false;
        if (index == n - 1 || (nums[index + 1] >= nums[index - 1])) return true;
        return index == 1 || (nums[index] >= nums[index - 2]);
    }

    public int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        int begin = 0;
        while (begin < n)
            if (nums[begin + 1] < nums[begin])
                break;
            else begin++;
        if (begin == n) return 0;
        int end = n - 1;
        while (end > 0)
            if (nums[end - 1] > nums[end])
                break;
            else end--;
        int max_val = Integer.MIN_VALUE;
        int min_val = Integer.MAX_VALUE;
        for (int i = begin; i <= end; i++) {
            max_val = Math.max(max_val, nums[i]);
            min_val = Math.min(min_val, nums[i]);
        }
        while (begin >= 0)
            if (nums[begin] < min_val)
                break;
            else
                begin--;
        while (end <= n - 1)
            if (nums[end] > max_val)
                break;
            else
                end++;
        return end - begin - 1;

    }

    public int equalSubstring(String s, String t, int maxCost) {
        int n = s.length();
        int[] gap = new int[n];
        for (int i = 0; i < n; i++)
            gap[i] = (s.charAt(i) + 26 - t.charAt(i)) % 26;
        int begin = 0;
        int res = 0;
        int end = begin;
        int cur_cost = 0;
        while (begin < n) {
            while (end < n && cur_cost + gap[end] <= maxCost) {
                cur_cost += gap[end];
                end++;
            }
            res = Math.max(res, end - begin);
            begin++;
            cur_cost -= gap[begin];
        }
        return res;
    }

    public boolean canIWin_2(int maxChoosableInteger, int desiredTotal) {

        if (maxChoosableInteger >= desiredTotal) return true;
        if ((1 + maxChoosableInteger) * maxChoosableInteger / 2 < desiredTotal) return false;
        /**
         *  dp表示"每个"取数(A和B共同作用的结果)状态下的输赢
         *  例如只有1,2两个数选择，那么 (1 << 2) - 1 = 4 - 1 = 3种状态表示：
         *  01,10,11分别表示：A和B已经选了1，已经选了2，已经选了1、2状态下，A的输赢情况
         *  并且可见这个表示所有状态的dp数组的每个状态元素的长度为maxChoosableInteger位的二进制数
         */
        Boolean[] dp = new Boolean[(1 << maxChoosableInteger) - 1];
        return canWin_dfs_2(maxChoosableInteger, desiredTotal, 0, dp);
    }

    /**
     * @param maxChoosableInteger
     * @param desiredTotal
     * @param state
     * @param dp
     * @return
     */
    private boolean canWin_dfs_2(int maxChoosableInteger, int desiredTotal, int state, Boolean[] dp) {
        if (dp[state] != null)
            return dp[state];
        /**
         * 例如maxChoosableInteger=2，选择的数只有1,2两个，二进制只要两位就可以表示他们的选择状态
         * 最大位为2（第2位），也就是1 << (2 - 1)的结果，所以最大的位可以表示为  1 << (maxChoosableInteger - 1)
         * 最小的位可以表示为 1 << (1 - 1)，也就是1（第1位）
         * 这里i表示括号的范围
         */
        for (int i = 1; i <= maxChoosableInteger; i++) {
            //当前待抉择的位，这里的tmp十进制只有一位为1，用来判断其为1的位，对于state是否也是在该位上为1
            //用以表示该位（数字）是否被使用过
            /**
             * (&运算规则，都1才为1)
             * 例如,i=3, tmp = 4, state = 3;
             *  100
             * &011
             * =0  表示该位没有被使用过，也就是第三位没有被使用过，即数字3 (i)没有被使用过
             */
            int tmp = (1 << (i - 1));
            if ((tmp & state) == 0) {  //该位没有被使用过
                //如果当前选了i已经赢了或者选了i还没赢但是后面对方选择输了,tmp|state表示进行状态的更新
                /**
                 * 例如
                 *  100
                 * |011
                 * =111
                 */
                //注意这里并没有像回溯一样进行状态的(赋值化的)更新、回溯
                //其实这里是隐含了回溯的，我们通过参数传递更新后的state
                //但是我们在这个调用者这里的state还是没有进行更新的，所以
                //就相当于回溯了状态。
                if (desiredTotal - i <= 0 || !canWin_dfs_2(maxChoosableInteger, desiredTotal - i, tmp | state, dp)) {
                    dp[state] = true;
                    return true;
                }
            }
        }
        //如果都赢不了
        dp[state] = false;
        return false;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m - 1;
        while (left < right) {
            int mid = (left + right) / 2 + 1;
            if (matrix[mid][0] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        int x = left;
        left = 0;
        right = n - 1;
        while (left < right) {
            int mid = (left + right) / 2;
            if (matrix[x][mid] < target)
                left = mid + 1;
            else
                right = mid;
        }
        return matrix[x][left] == target;
    }

    public int numMagicSquaresInside(int[][] grid) {
        int res = 0;
        int m = grid.length;
        int n = grid[0].length;
        if (m < 3 || n < 3)
            return 0;
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] == 5) {
                    if (isMagicSquare(grid, i, j)) res++;
                }
            }
        }
        return res;
    }

    private boolean isMagicSquare(int[][] grid, int i, int j) {
        int[] times = new int[9];
        for (int p = i - 1; p <= i + 1; p++) {
            if (grid[p][j - 1] + grid[p][j] + grid[p][j + 1] != 15)
                return false;
        }
        for (int q = j - 1; q <= j + 1; q++) {
            if (grid[i - 1][q] + grid[i][q] + grid[i + 1][q] != 15)
                return false;
        }
        for (int p = -1; p <= 1; p++)
            for (int q = -1; q <= 1; q++) {
                int ni = i + p;
                int nj = j + q;
                if (times[grid[ni][nj]]++ == 2)
                    return false;
            }
        return true;
    }

    public int findLengthOfShortestSubarray(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        dp[0] = 1;
        int res = n - 1;
        for (int i = 1; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--)
                if (arr[j] <= arr[i])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            res = Math.min(res, n - dp[i]);
        }
        return res;
    }

    public int maxDistToClosest(int[] seats) {
        int begin = -1;
        int n = seats.length;
        int res = -1;
        while (begin < n) {
            int end = begin + 1;
            while (end < n && seats[end] != 1)
                end++;
            if (begin == -1)
                res = Math.max(res, end);
            else if (end == n)
                res = Math.max(res, n - begin);
            else res = Math.max(res, (end - begin) / 2);
            begin = end + 1;
        }
        return res;
    }

    public double findMaxAverage(int[] nums, int k) {
        int res;
        int cur_sum = 0;
        for (int i = 0; i < k; i++)
            cur_sum += nums[i];
        res = cur_sum;
        for (int i = k; i < nums.length; i++) {
            cur_sum += nums[i];
            cur_sum -= nums[i - k];
            res = Math.max(res, cur_sum);
        }
        return res;
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        medianSlidingWindow_medianHeap heap = new medianSlidingWindow_medianHeap();
        for (int i = 0; i < k; i++)
            heap.add(nums[i]);
        double[] res = new double[nums.length - k + 1];
        res[0] = heap.get_median();
        for (int i = k; i < nums.length; i++) {
            heap.add(nums[i]);
            heap.erase(nums[i]);
            res[i - k + 1] = heap.get_median();
        }
        return res;
    }

    class medianSlidingWindow_medianHeap {
        PriorityQueue<Integer> small;
        PriorityQueue<Integer> large;
        Map<Integer, Integer> map = new HashMap<>();
        int small_size;
        int large_size;

        public medianSlidingWindow_medianHeap() {
            this.small = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2.compareTo(o1);
                }
            });
            this.large = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            small_size = 0;
            large_size = 0;
        }

        public double get_median() {
            return (small_size == large_size ? ((double) small.peek() / 2 + (double) large.peek() / 2) : small.peek());
        }

        public void add(int i) {
            if (small.isEmpty() || i <= small.peek()) {
                small_size++;
                small.offer(i);
            } else {
                large_size++;
                large.offer(i);
            }
            balance();
        }

        public void erase(int i) {
            map.put(i, map.getOrDefault(i, 0) + 1);
            if (i <= small.peek())
                small_size--;
            else
                large_size--;
            balance();

        }

        private void prune(PriorityQueue<Integer> que) {//解决队首元素虚无性
            while (true) {
                if (map.containsKey(que.peek())) {
                    int freq = map.get(que.peek());
                    if (freq > 1)
                        map.put(que.peek(), freq - 1);
                    else
                        map.remove(que.peek());
                    que.poll();
                } else {
                    break;
                }
            }
        }

        private void balance() {
            if (large_size == small_size + 1) {
                small.offer(large.poll());
                small_size++;
                large_size--;
            }
            if (small_size == large_size + 2) {
                large.offer(small.poll());

                small_size--;
                large_size++;
            }
            prune(small);
            prune(large);
        }
    }

    public int[] findDiagonalOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                list.add(new int[]{i, j});
            }
        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] + o1[1] < o2[0] + o2[1])
                    return -1;
                else if (o1[0] + o1[1] > o2[0] + o2[1])
                    return 1;
                else {
                    if ((o1[0] + o1[1]) % 2 == 1) {
                        return Integer.compare(o1[0], o2[0]);
                    } else
                        return Integer.compare(o2[0], o1[0]);
                }
            }
        });
        int[] res = new int[m * n];
        int index = 0;
        for (int[] array : list) {
            res[index++] = matrix[array[0]][array[1]];
        }
        return res;
    }

    public int[] fairCandySwap(int[] A, int[] B) {
        int sum_a = 0;
        int sum_b = 0;
        Set<Integer> set_b = new HashSet<>();
        for (int a : A) {
            sum_a += a;
        }
        for (int b : B) {
            sum_b += b;
            set_b.add(b);
        }
        for (int a : A) {
            int want_b = a - (sum_a - sum_b) / 2;
            if (set_b.contains(want_b))
                return new int[]{a, want_b};
        }
        return null;
    }

    public boolean hasValidPath(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        hasValidPath_UnionFind unionFind = new hasValidPath_UnionFind((2 * n + 1) * (2 * m + 1));
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int cur = (2 * i + 1) * (2 * n + 1) + 2 * j + 1;
                int up = (2 * i) * (2 * n + 1) + 2 * j + 1;
                int down = (2 * i + 2) * (2 * n + 1) + 2 * j + 1;
                int left = (2 * i + 1) * (2 * n + 1) + 2 * j;
                int right = (2 * i + 1) * (2 * n + 1) + 2 * j + 2;
                switch (grid[i][j]) {
                    case 1:
                        unionFind.union(cur, left);
                        unionFind.union(cur, right);
                        break;
                    case 2:
                        unionFind.union(cur, down);
                        unionFind.union(cur, up);
                        break;
                    case 3:
                        unionFind.union(cur, left);
                        unionFind.union(cur, up);
                        break;
                    case 4:
                        unionFind.union(cur, right);
                        unionFind.union(cur, up);
                        break;
                    case 5:
                        unionFind.union(cur, left);
                        unionFind.union(cur, down);
                        break;
                    case 6:
                        unionFind.union(cur, down);
                        unionFind.union(cur, right);
                        break;
                }
            }
        }
        return unionFind.isCollected(1 * (2 * n + 1) + 1, (2 * m - 1) * (2 * n + 1) + 2 * n - 1);
    }

    class hasValidPath_UnionFind {
        int[] parents;

        public hasValidPath_UnionFind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            parents[i_parent] = j_parent;
            return true;
        }

        public boolean isCollected(int i, int j) {
            return find(i) == find(j);
        }
    }

    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        Map<String, Integer> map = new HashMap<>();
        for (String name : names) {
            String cur_name = name.split("\\(")[0];
        }
        return null;
    }

    class trulyMostPopular_unionFind {
        int[] parents;

        public trulyMostPopular_unionFind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            parents[i_parent] = j_parent;
            return true;
        }

        public int group_num() {
            int res = 0;
            for (int i = 0; i < parents.length; i++)
                if (parents[i] == i)
                    res++;
            return res;
        }
    }

    public int numRookCaptures(char[][] board) {
        int cur_row = 0;
        int row = board.length;
        int cur_col = 0;
        int col = board[0].length;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                if (board[i][j] == 'R') {
                    cur_row = i;
                    cur_col = j;
                    break;
                }

        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};
        int res = 0;
        for (int i = 0; i < 4; i++) {
            for (int step = 1; ; step++) {
                int next_row = cur_row + step * dx[i];
                int next_col = cur_col + step * dy[i];
                if (next_row < 0 || next_row >= row || next_col < 0 || next_col >= col || board[next_row][next_col] == 'B')
                    break;
                if (board[next_row][next_col] == 'p')
                    res++;
            }
        }
        return res;
    }

    public String alphabetBoardPath(String target) {
        StringBuilder res = new StringBuilder();
        int n = target.length();
        int begin = 0;
        for (int i = 0; i < n; i++) {
            int end = target.charAt(i) - 'a';
            int begin_row = begin / 5;
            int begin_col = begin % 5;
            int end_row = end / 5;
            int end_col = end % 5;
            if (begin == 25) {
                while (begin_row < end_row) {
                    res.append('D');
                    begin_row++;
                }
                while (end_row < begin_row) {
                    res.append('U');
                    begin_row--;
                }
                while (begin_col < end_col) {
                    res.append('R');
                    begin_col++;
                }
                while (end_col < begin_col) {
                    res.append('L');
                    begin_col--;
                }
            } else {
                while (begin_col < end_col) {
                    res.append('R');
                    begin_col++;
                }
                while (end_col < begin_col) {
                    res.append('L');
                    begin_col--;
                }
                while (begin_row < end_row) {
                    res.append('D');
                    begin_row++;
                }
                while (end_row < begin_row) {
                    res.append('U');
                    begin_row--;
                }

            }
            res.append('!');
            begin = end;
        }
        return res.toString();
    }

    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        numSimilarGroups_UnionFind unionFind = new numSimilarGroups_UnionFind(n);
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (numSimilarGroups_isSimilar(strs[i], strs[j]))
                    unionFind.union(i, j);
        return unionFind.group_num();
    }

    private boolean numSimilarGroups_isSimilar(String str1, String str2) {
        int n = str1.length();
        if (str2.length() != n)
            return false;
        int res = 0;
        for (int i = 0; i < n; i++)
            if (str1.charAt(i) != str2.charAt(i))
                res++;
        return res <= 2;
    }

    static class numSimilarGroups_UnionFind {
        int[] parents;

        public numSimilarGroups_UnionFind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            parents[i_parent] = j_parent;
            return true;
        }

        public int group_num() {
            int res = 0;
            for (int i = 0; i < parents.length; i++)
                if (parents[i] == i)
                    res++;
            return res;
        }
    }

    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> res = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int del : to_delete)
            set.add(del);
        if (delNodes_dfs(root, set, res))
            res.add(root);
        return res;
    }

    private boolean delNodes_dfs(TreeNode root, Set<Integer> set, List<TreeNode> res) {
        if (root == null)
            return false;
        boolean left = delNodes_dfs(root.left, set, res);
        boolean right = delNodes_dfs(root.right, set, res);
        if (!left) root.left = null;
        if (!right) root.right = null;
        if (set.contains(root.val)) {
            if (left) res.add(root.left);
            if (right) res.add(root.right);
            return false;
        } else
            return true;
    }

    public TreeNode removeLeafNodes(TreeNode root, int target) {
        TreeNode dummy = new TreeNode(0);
        dummy.left = root;
        removeLeafNodes_dfs(dummy, target, null, 0);
        return dummy.left;
    }

    private void removeLeafNodes_dfs(TreeNode root, int target, TreeNode parent, int i) {
        if (root == null)
            return;
        removeLeafNodes_dfs(root.left, target, root, 0);
        removeLeafNodes_dfs(root.right, target, root, 1);
        if (root.left == null && root.right == null && root.val == target && parent != null)
            if (i == 0)
                parent.left = null;
            else
                parent.right = null;

    }

    public int swimInWater(int[][] grid) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Set<Integer>> map = new HashMap<>();
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                int index = i * n + j;
                Set<Integer> cur_set;
                if (map.containsKey(grid[i][j])) {
                    cur_set = map.get(grid[i][j]);
                } else {
                    list.add(grid[i][j]);
                    cur_set = new HashSet<Integer>();
                }
                cur_set.add(index);
                map.put(grid[i][j], cur_set);
            }
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        //int res=Math.max(grid[0][0],grid[m-1][n-1]);
        swimInWater_unionFind unionFind = new swimInWater_unionFind(n * m);
        int[] directions = new int[]{-1, 1, -n, n};
        for (int cur_t : list) {
            Set<Integer> cur_set = map.get(cur_t);
            for (int node : cur_set) {
                unionFind.setAvailable(node);
                for (int direction : directions) {
                    int adj_node = node + direction;
                    if ((Math.abs(direction) != 1 || adj_node / n == node / n) && adj_node >= 0 && adj_node < n * m && unionFind.isAvailable(adj_node))
                        unionFind.union(node, adj_node);
                }
            }
            if (unionFind.isCollected(0, n * m - 1))
                return cur_t;
        }
        return -1;
    }

    class swimInWater_unionFind {
        int[] parents;
        boolean[] available;

        public swimInWater_unionFind(int n) {
            parents = new int[n];
            available = new boolean[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            else
                parents[i_parent] = j_parent;
            return true;
        }

        public void setAvailable(int i) {
            available[i] = true;
        }

        public boolean isAvailable(int i) {
            return available[i];
        }

        public boolean isCollected(int i, int j) {
            return find(i) == find(j);
        }

    }

    int[][] minimumEffortPath_dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int minimumEffortPath(int[][] heights) {
        int m = heights.length;
        int n = heights[0].length;
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                int index = i * n + j;
                if (j > 0)
                    list.add(new int[]{index - 1, index, Math.abs(heights[i][j] - heights[i][j - 1])});
                if (i > 0)
                    list.add(new int[]{index - n, index, Math.abs(heights[i][j] - heights[i - 1][j])});
            }
        list.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        minimumEffortPath_unionFind unionFind = new minimumEffortPath_unionFind(n * m);
        for (int[] edge : list) {
            int node_1 = edge[0];
            int node_2 = edge[1];
            unionFind.union(node_1, node_2);
            if (unionFind.isCollected(0, n * m - 1))
                return edge[2];
        }
        return -1;

    }

    class minimumEffortPath_unionFind {
        int[] parents;

        public minimumEffortPath_unionFind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            else {
                parents[i_parent] = j_parent;
            }
            return true;
        }

        public boolean isCollected(int i, int j) {
            return parents[i] == parents[j];
        }
    }

    public boolean isNStraightHand(int[] hand, int W) {
        int n = hand.length;
        if (n % W != 0) return false;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : hand)
            map.put(num, map.getOrDefault(num, 0) + 1);
        Arrays.sort(hand);
        for (int value : hand) {
            int cur_num = map.get(value);
            if (cur_num == 0)
                continue;
            for (int i = 1; i < W; i++) {
                if (map.getOrDefault(value + i, 0) < cur_num)
                    return false;
                map.put(value + i, map.get(value + i) - cur_num);
            }
            map.put(value, 0);
        }
        return true;
    }

    int deepestLeavesSum_deep = 0;
    int deepestLeavesSum_res = 0;

    public int deepestLeavesSum(TreeNode root) {
        deepestLeavesSum_DFS(root, 0);
        return deepestLeavesSum_res;
    }

    private void deepestLeavesSum_DFS(TreeNode root, int deep) {
        if (root == null)
            return;
        if (deep > deepestLeavesSum_deep) {
            deepestLeavesSum_deep = deep;
            deepestLeavesSum_res = root.val;
        } else if (deep == deepestLeavesSum_deep) {
            deepestLeavesSum_res += root.val;
        }
        deepestLeavesSum_DFS(root.left, deep + 1);
        deepestLeavesSum_DFS(root.right, deep + 1);
    }

    public int pivotIndex(int[] nums) {
        int sum = 0;
        int n = nums.length;
        for (int num : nums)
            sum += num;
        int cur_sum = 0;
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                sum -= nums[i - 1];
                cur_sum += nums[i - 1];
            }
            if (cur_sum == (sum - nums[i]))
                return i;
        }
        return -1;
    }

    public int maxNumEdgesToRemove(int n, int[][] edges) {
        maxNum_unionFind[] unionFinds = new maxNum_unionFind[2];
        unionFinds[0] = new maxNum_unionFind(n);
        unionFinds[1] = new maxNum_unionFind(n);
        int res = 0;
        int cur_edge_num = 0;
        for (int[] edge : edges) {
            if (edge[0] == 3) {
                if (!unionFinds[0].union(edge[1], edge[2]))
                    res++;
                else
                    cur_edge_num++;
                unionFinds[1].union(edge[1], edge[2]);
            }
        }
        for (int i = 0; i < 2; i++) {
            int edge_num = cur_edge_num;
            for (int[] edge : edges) {
                if (edge[0] == i + 1) {
                    if (!unionFinds[i].union(edge[1], edge[2]))
                        res++;
                    else
                        edge_num++;
                }
            }
            if (edge_num != n - 1)
                return -1;
        }
        return res;

    }

    class maxNum_unionFind {
        int[] parents;

        public maxNum_unionFind(int n) {
            parents = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            parents[i_parent] = j_parent;
            return true;
        }
    }

    public int numEquivDominoPairs(int[][] dominoes) {
        int[][] table = new int[10][10];
        for (int[] domino : dominoes) {
            if (domino[0] < domino[1])
                table[domino[1]][domino[0]]++;
            else
                table[domino[0]][domino[1]]++;
        }
        int res = 0;
        for (int i = 1; i < 10; i++)
            for (int j = i; j < 10; j++) {
                if (table[i][j] >= 2)
                    res += (table[i][j] - 1) * table[i][j] / 2;
            }
        return res;
    }

    public int findLengthOfLCIS(int[] nums) {
        int res = 0;
        int cur_len = 1;
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1] + 1)
                cur_len++;
            else {
                res = Math.max(res, cur_len);
                cur_len = 1;
            }
        }
        return Math.max(res, cur_len);
    }

    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1)
            return -1;
        makeConnected_unionFind unionFind = new makeConnected_unionFind(n);
        for (int[] connection : connections)
            unionFind.union(connection[0], connection[1]);
        return unionFind.branch_num() - 1;
    }

    private class makeConnected_unionFind {
        int[] parents;
        int[] size;

        makeConnected_unionFind(int n) {
            parents = new int[n];
            size = new int[n];
            Arrays.fill(size, 1);
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        void union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent != j_parent) {
                if (size[i_parent] > size[j_parent]) {
                    size[i_parent] += size[j_parent];
                    parents[j_parent] = i_parent;
                } else {
                    size[j_parent] += size[i_parent];
                    parents[i_parent] = j_parent;
                }
            }
        }

        int branch_num() {
            int num = 0;
            for (int i = 0; i < parents.length; i++)
                if (parents[i] == i)
                    num++;
            return num;
        }

    }

    public boolean canConvertString(String s, String t, int k) {
        int n = s.length();
        int m = t.length();
        int[] need_correct = new int[26];
        if (n != m)
            return false;
        int error_num = 0;
        for (int i = 0; i < n; i++)
            if (s.charAt(i) != t.charAt(i)) {
                error_num++;
                need_correct[(t.charAt(i) + 26 - s.charAt(i)) % 26]++;
            }
        if (error_num > k)
            return false;

        for (int i = 0; i < 26; i++) {
            //System.out.println(i+" "+need_correct[i]);
            if (need_correct[i] > 0 && (need_correct[i] - 1) * 26 + i > k)
                return false;
        }

        return true;
    }

    public String getSmallestString(int n, int k) {
        StringBuilder str = new StringBuilder();
        while ((n - 1) * 26 >= (k - 1)) {
            str.append('a');
            n--;
            k--;
        }
        str.append('a' + k - 26 * (n - 1));
        n--;
        while (n-- > 0)
            str.append('z');
        return str.toString();

    }

    public int minCost(String s, int[] cost) {
        int n = s.length();
        int max_cost = cost[0];
        int res = cost[0];
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                max_cost = Math.max(max_cost, cost[i]);
                res += cost[i];
            } else {
                res = res + cost[i] - max_cost;
                max_cost = cost[i];
            }
        }
        return res - max_cost;
    }

    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> res = new ArrayList<>();
        int n = A.length;
        int carry = 0;
        for (int i = n - 1; i >= 0 || K > 0; i--) {
            int cur_a = (i >= 0 ? A[i] : 0);
            res.add((cur_a + K % 10 + carry) % 10);
            carry = (cur_a + K % 10 + carry) / 10;
            K /= 10;
        }
        if (carry == 1)
            res.add(1);
        Collections.reverse(res);
        return res;

    }

    public int findLongestChain(int[][] pairs) {
        int n = pairs.length;
        List<int[]> list = new ArrayList<>();
        for (int[] pair : pairs) {
            list.add(new int[]{pair[0], pair[1]});
        }
        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int res = 0;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (list.get(j)[1] < list.get(i)[0]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        ArrayList<int[]> list = new ArrayList<>();
        Map<int[], Integer> edge_to_index = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int[] cur_edge = new int[]{edges[i][0], edges[i][1], edges[i][2]};
            list.add(cur_edge);
            edge_to_index.put(cur_edge, i);
        }
        Collections.sort(list, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        int original_MST_length = findCritical_MST(n, list, -1, -1);
        List<Integer> critical_edge = new ArrayList<>();
        List<Integer> non_critical_edge = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (findCritical_MST(n, list, i, -1) != original_MST_length)
                critical_edge.add(edge_to_index.get(list.get(i)));
            else if (findCritical_MST(n, list, i, i) == original_MST_length)
                non_critical_edge.add(edge_to_index.get(list.get(i)));
        }
        List<List<Integer>> res = new ArrayList<>();
        res.add(critical_edge);
        res.add(non_critical_edge);
        return res;
    }

    private int findCritical_MST(int n, ArrayList<int[]> edges, int k, int p) {
        findCritical_UnionFindSet unionFindSet = new findCritical_UnionFindSet(n);
        int m = edges.size();
        int res = 0;
        int edges_num = 0;
        if (p != -1) {
            res += edges.get(p)[2];
            edges_num++;
            unionFindSet.union(edges.get(p)[0], edges.get(p)[1]);
        }
        for (int i = 0; i < m; i++) {
            if (i == k || i == p)
                continue;
            if (unionFindSet.union(edges.get(i)[0], edges.get(i)[1])) {
                edges_num++;
                res += edges.get(i)[2];
            }
            if (edges_num == n - 1)
                break;
        }
        return (edges_num == n - 1 ? res : Integer.MAX_VALUE);
    }

    private class findCritical_UnionFindSet {
        int[] parents;
        int[] size;

        public findCritical_UnionFindSet(int n) {
            parents = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++)
                parents[i] = i;
            Arrays.fill(size, 1);
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            if (size[i] > size[j]) {
                parents[j_parent] = i_parent;
                size[i_parent] += size[j_parent];
            } else {
                parents[i_parent] = j_parent;
                size[j_parent] += size[i_parent];
            }
            return true;

        }
    }

    public int maximumProduct(int[] nums) {
        int[] positive_nums = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        int[] negative_nums = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        for (int num : nums) {
            if (num > positive_nums[2]) {
                int sum = positive_nums[0] + positive_nums[1] + num;
                int tmp0 = Math.max(positive_nums[0], Math.max(positive_nums[1], num));
                int tmp1 = Math.min(positive_nums[0], Math.max(positive_nums[1], num));
                int tmp2 = sum - tmp1 - tmp0;
                positive_nums[0] = tmp0;
                positive_nums[1] = tmp1;
                positive_nums[2] = tmp2;
            } else if (num < negative_nums[1]) {
                int tmp0 = Math.min(negative_nums[0], num);
                int tmp1 = Math.max(negative_nums[0], num);
                negative_nums[0] = tmp0;
                negative_nums[1] = tmp1;
            }
        }
        return Math.max(positive_nums[0] * negative_nums[0] * negative_nums[1],
                positive_nums[0] * positive_nums[1] * positive_nums[2]);
    }

    public int minCostConnectPoints(int[][] points) {
        int n = points.length;
        boolean[] isVisited = new boolean[n];
        minCostUnionFind unionFind = new minCostUnionFind(n);
        List<minCostEdge> edges = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                edges.add(new minCostEdge(minCostDist(points[i], points[j]), i, j));
            }
        Collections.sort(edges, new Comparator<minCostEdge>() {
            @Override
            public int compare(minCostEdge o1, minCostEdge o2) {
                return o1.len - o2.len;
            }
        });
        for (minCostEdge edge : edges) {
            //System.out.println(edge.i+" "+edge.j+" "+edge.len);
        }
        isVisited[0] = true;
        int cur_num = 0;
        int res = 0;
        while (cur_num++ < n - 1) {
            for (minCostEdge edge : edges) {
                if ((isVisited[edge.i] && !isVisited[edge.j]) || (isVisited[edge.j] && !isVisited[edge.i])) {
                    res += edge.len;
                    isVisited[edge.i] = true;
                    isVisited[edge.j] = true;
                    break;
                }
            }
        }
        return res;
    }

    private int minCostDist(int[] point1, int[] point2) {
        return Math.abs(point1[0] - point2[0]) + Math.abs(point1[1] - point2[1]);
    }

    private class minCostEdge {
        int len;
        int i;
        int j;

        minCostEdge(int len, int i, int j) {
            this.len = len;
            this.i = i;
            this.j = j;
        }
    }

    private class minCostUnionFind {
        int[] parents;
        int[] ranks;

        public minCostUnionFind(int n) {
            parents = new int[n];
            ranks = new int[n];
            Arrays.fill(ranks, 1);
            for (int i = 0; i < n; i++)
                parents[i] = i;
        }

        public int find(int i) {
            if (parents[i] != i)
                parents[i] = find(parents[i]);
            return parents[i];
        }

        public boolean union(int i, int j) {
            int i_parent = find(i);
            int j_parent = find(j);
            if (i_parent == j_parent)
                return false;
            if (ranks[i_parent] > ranks[j_parent]) {
                parents[j_parent] = i_parent;
                ranks[i_parent] += ranks[j_parent];
            } else {
                parents[i_parent] = j_parent;
                ranks[j_parent] += ranks[i_parent];
            }
            return true;
        }

    }

    public int minSumOfLengths(int[] arr, int target) {
        List<int[]> list = new ArrayList<>();
        int n = arr.length;
        int pre = 0, cur = 0, sum = 0;
        while (pre < n) {
            while (cur < n && sum < target)
                sum += arr[cur++];
            if (cur == n && sum < target)
                break;
            if (sum == target)
                list.add(new int[]{pre, cur - 1});
            sum -= arr[pre];
            pre++;
        }
        if (list.size() <= 1)
            return -1;
        int[][] dp = new int[2][n];
        int len = Integer.MAX_VALUE;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int start = list.get(i)[1];
            int end;
            if (i == size - 1)
                end = n;
            else
                end = list.get(i + 1)[1];
            len = Math.min(len, list.get(i)[1] - list.get(i)[0] + 1);
            for (int j = start; j < end; j++)
                dp[0][j] = len;
        }
        len = Integer.MAX_VALUE;
        for (int i = size - 1; i >= 0; i--) {
            int start = list.get(i)[0];
            int end;
            if (i == 0)
                end = -1;
            else
                end = list.get(i - 1)[0];
            len = Math.min(len, list.get(i)[1] - list.get(i)[0] + 1);
            for (int j = start; j > end; j--)
                dp[1][j] = len;
        }
        int res = Integer.MAX_VALUE;
        for (int[] array : list) {
            int begin = array[0];
            int end = array[1];
            int len1 = Integer.MAX_VALUE;
            if (begin > 0 && dp[0][begin - 1] != 0)
                len1 = dp[0][begin - 1];
            int len2 = Integer.MAX_VALUE;
            if (end < n - 1 && dp[1][end + 1] != 0)
                len2 = dp[1][end + 1];
            if (len1 != Integer.MAX_VALUE || len2 != Integer.MAX_VALUE)
                res = Math.min(Math.min(len1, len2) + end - begin + 1, res);
        }
        return (res == Integer.MAX_VALUE ? -1 : res);
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> emailToIndex = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        int emailCounts = 0;
        for (List<String> account : accounts) {
            String name = account.get(0);
            int size = account.size();
            for (int i = 1; i < size; i++) {
                if (!emailToIndex.containsKey(name)) {
                    emailToIndex.put(account.get(i), emailCounts++);
                    emailToName.put(account.get(i), name);
                }
            }
        }
        UnionFind unionFind = new UnionFind(emailCounts);
        for (List<String> account : accounts) {
            int cur_index = emailToIndex.get(account.get(1));
            for (int i = 2; i < account.size(); i++)
                unionFind.union(cur_index, emailToIndex.get(account.get(i)));
        }
        Map<Integer, List<String>> indexToEmails = new HashMap<>();
        for (Map.Entry<String, Integer> entry : emailToIndex.entrySet()) {
            int index = unionFind.find(entry.getValue());
            List<String> list = indexToEmails.getOrDefault(index, new ArrayList<>());
            list.add(entry.getKey());
            indexToEmails.put(index, list);
        }
        List<List<String>> res = new ArrayList<>();
        for (List<String> emails : indexToEmails.values()) {
            Collections.sort(emails);
            String name = emailToName.get(emails.get(0));
            List<String> cur_list = new ArrayList<>();
            cur_list.add(name);
            cur_list.addAll(emails);
            res.add(cur_list);
        }
        return res;
    }

    private class UnionFind {
        int[] parent;
        int[] size;

        public UnionFind(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
            Arrays.fill(size, 1);
        }

        public int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            int root_x = find(x);
            int root_y = find(y);
            if (root_x != root_y)
                parent[root_x] = root_y;
            size[root_y] += size[root_x];
        }

        public int getSize(int x) {
            return size[x];
        }
    }

    private class accountsMerge_Pair {
        int index;
        String name;

        accountsMerge_Pair(int index, String name) {
            this.index = index;
            this.name = name;
        }
    }

    public int numRollsToTarget(int d, int f, int target) {
        int[][] dp = new int[2][target + 1];
        for (int i = 1; i <= Math.min(f, target); i++)
            dp[0][i] = 1;
        for (int i = 1; i < d; i++) {
            int cur = i & 1;
            for (int j = 1; j <= target; j++) {
                for (int k = 1; k <= Math.min(f, j); k++)
                    dp[cur][j] = (dp[cur][j] + dp[1 - cur][j - k]) % 1000000007;
            }
        }
        return dp[(d - 1) & 1][target];
    }

    public boolean checkStraightLine(int[][] coordinates) {
        if (coordinates.length <= 2)
            return true;
        int judge_x = coordinates[1][0] - coordinates[0][0];
        int judge_y = coordinates[1][1] - coordinates[0][1];
        int len = coordinates.length;
        for (int i = 2; i < len; i++) {
            int cur_x = coordinates[i][0] - coordinates[0][0];
            int cur_y = coordinates[i][1] - coordinates[0][1];
            if (judge_x * cur_y != judge_y * cur_x)
                return false;
        }
        return true;
    }

    private int hitBricks_row;
    private int hitBricks_col;
    private static final int[][] directions = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

    public int[] hitBricks(int[][] grid, int[][] hits) {
        this.hitBricks_row = grid.length;
        this.hitBricks_col = grid[0].length;
        int[][] copy = new int[hitBricks_row][hitBricks_col];
        for (int i = 0; i < hitBricks_row; i++)
            System.arraycopy(grid[i], 0, copy[i], 0, hitBricks_col);
        for (int[] hit : hits)
            copy[hit[0]][hit[1]] = 0;
        int size = hitBricks_row * hitBricks_col;
        UnionFind unionFind = new UnionFind(size + 1);
        for (int j = 0; j < hitBricks_col; j++)
            if (copy[0][j] == 1)
                unionFind.union(j, size);
        for (int i = 1; i < hitBricks_row; i++)
            for (int j = 0; j < hitBricks_col; j++) {
                if (copy[i][j] == 1) {
                    if (copy[i - 1][j] == 1)
                        unionFind.union(hitBricks_getIndex(i, j), hitBricks_getIndex(i - 1, j));
                    if (j > 0 && copy[i][j - 1] == 1)
                        unionFind.union(hitBricks_getIndex(i, j), hitBricks_getIndex(i, j - 1));
                }
            }
        int hit_size = hits.length;
        int[] res = new int[hit_size];
        for (int i = hit_size - 1; i >= 0; i--) {
            int x = hits[i][0];
            int y = hits[i][1];
            if (grid[x][y] == 0)
                continue;
            int original = unionFind.getSize(size);
            //加砖
            copy[x][y] = 1;
            if (x == 0)
                unionFind.union(y, size);
            for (int[] direction : directions) {
                int new_x = x + direction[0];
                int new_y = y + direction[1];
                if (inArea(new_x, new_y) && copy[new_x][new_y] == 1)
                    unionFind.union(hitBricks_getIndex(new_x, new_y), hitBricks_getIndex(x, y));
            }

            int cur = unionFind.getSize(size);
            res[i] = cur - original - 1;
        }
        return res;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < hitBricks_row && j >= 0 && j < hitBricks_col;
    }

    private int hitBricks_getIndex(int i, int j) {
        return i * hitBricks_col + j;
    }


    public double largestSumOfAverages(int[] A, int K) {
        int n = A.length;
        double[][] dp = new double[n][K + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= K; j++) {
                double cur_sum = 0;
                for (int k = 1; k <= i - j + 1; k++) {
                    cur_sum += A[i - k + 1];
                    dp[i][j] = Math.max(dp[i][j], dp[i - k][j - 1] + cur_sum / k);
                }
            }
        }
        return dp[n - 1][K];
    }

    public int removeStones(int[][] stones) {
        int n = stones.length;
        int[] parents = new int[n];
        for (int i = 0; i < n; i++)
            parents[i] = i;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++)
                if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1])
                    removeStones_union(parents, i, j);
        }
        int res = 0;
        for (int i = 0; i < n; i++)
            if (parents[i] == i)
                res++;
        return res;
    }

    private void removeStones_union(int[] parents, int i, int j) {
        int i_num = removeStones_parents_num(parents, i);
        int j_num = removeStones_parents_num(parents, j);
        if (i_num > j_num)
            parents[removeStones_find_parent(parents, j)] = parents[removeStones_find_parent(parents, i)];
        else
            parents[removeStones_find_parent(parents, i)] = parents[removeStones_find_parent(parents, j)];
    }

    private int removeStones_find_parent(int[] parents, int i) {
        if (parents[i] != i)
            parents[i] = removeStones_find_parent(parents, parents[i]);
        return parents[i];
    }

    private int removeStones_parents_num(int[] parents, int i) {
        int num = 0;
        while (parents[i] != i) {
            i = parents[i];
            num++;
        }
        return num;
    }

    public int numberOfArithmeticSlices(int[] A) {
        int n = A.length;
        if (n <= 2)
            return 0;
        int res = 0;
        int[] dp = new int[2];
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            int cur = i & 1;
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2])
                dp[cur] = dp[1 - cur] + 1;
            else
                dp[cur] = 2;
            res += (dp[cur] - 2);
        }
        return res;
    }

    public int waysToChange(int n) {
        int[] dp = new int[n + 1];
        int[] coins = new int[]{5, 10, 25};
        Arrays.fill(dp, 1);
        for (int coin : coins) {
            for (int i = 1; i <= n; i++)
                if (i >= coin)
                    dp[i] = (dp[i] + dp[i - coin]) % 1000000007;
        }
        return dp[n];
    }

    public List<Boolean> prefixesDivBy5(int[] A) {
        int mod = 0;
        List<Boolean> list = new ArrayList<>();
        for (int a : A) {
            mod = (mod << 1) + a;
            if (mod >= 5)
                mod -= 5;
            list.add(mod == 0);
        }
        return list;
    }

    public int[] findRedundantConnection(int[][] edges) {
        int n = 0;
        for (int[] edge : edges)
            n = Math.max(n, Math.max(edge[0], edge[1]));
        int[] parents = new int[n + 1];
        for (int i = 0; i <= n; i++)
            parents[i] = i;
        int m = edges.length;
        for (int i = m - 1; i >= 0; i--) {
            int first_parent = findRedundant_find_parent(parents, edges[i][0]);
            int second_parent = findRedundant_find_parent(parents, edges[i][1]);
            if (first_parent == second_parent)
                return edges[i];
            else
                findRedundant_union(parents, first_parent, second_parent);
        }
        return new int[0];


    }

    private void findRedundant_union(int[] parents, int i, int j) {
        int i_num = findRedundatn_parents_num(parents, i);
        int j_num = findRedundatn_parents_num(parents, j);
        if (i_num > j_num)
            parents[findRedundant_find_parent(parents, j)] = parents[findRedundant_find_parent(parents, i)];
        else
            parents[findRedundant_find_parent(parents, i)] = parents[findRedundant_find_parent(parents, j)];
    }

    private int findRedundant_find_parent(int[] parents, int i) {
        if (parents[i] != i)
            parents[i] = findRedundant_find_parent(parents, parents[i]);
        return parents[i];
    }

    private int findRedundatn_parents_num(int[] parents, int i) {
        int num = 0;
        while (parents[i] != i) {
            i = parents[i];
            num++;
        }
        return num;
    }

    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        List<List<Integer>> groupID_item = new ArrayList<>();
        for (int i = 0; i < m + n; i++)
            groupID_item.add(new ArrayList<>());
        int leftID = m;
        for (int i = 0; i < n; i++) {
            if (group[i] == -1)
                group[i] = leftID++;
            groupID_item.get(group[i]).add(i);
        }
        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < m + n; i++)
            id.add(i);

        int[] group_in_degree = new int[m + n];
        int[] item_in_degree = new int[n];

        List<List<Integer>> group_graph = new ArrayList<>();
        for (int i = 0; i < m + n; i++)
            group_graph.add(new ArrayList<>());
        List<List<Integer>> item_graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            item_graph.add(new ArrayList<>());

        for (int i = 0; i < n; i++) {
            List<Integer> before_nodes = beforeItems.get(i);
            int cur_groupID = group[i];
            for (int before_node : before_nodes) {
                int pre_groupID = group[before_node];
                if (pre_groupID == cur_groupID) {
                    item_graph.get(before_node).add(i);
                    item_in_degree[i]++;
                } else {
                    group_in_degree[cur_groupID]++;
                    group_graph.get(pre_groupID).add(cur_groupID);
                }
            }
        }
        List<Integer> topSorted_group = topSort(group_in_degree, group_graph, id);
        if (topSorted_group.size() == 0)
            return new int[]{};
        int[] res = new int[n];
        int index = 0;
        for (int cur_group_id : topSorted_group) {
            if (groupID_item.get(cur_group_id).size() == 0)
                continue;
            List<Integer> cur_topSorted_items =
                    topSort(item_in_degree, item_graph, groupID_item.get(cur_group_id));
            if (cur_topSorted_items.size() == 0)
                return new int[]{};
            for (int cur_topSorted_item : cur_topSorted_items)
                res[index++] = cur_topSorted_item;
        }
        return res;
    }

    public List<Integer> topSort(int[] in_degree, List<List<Integer>> edges, List<Integer> nodes) {
        Queue<Integer> que = new ArrayDeque<>();
        for (int node : nodes)
            if (in_degree[node] == 0)
                que.add(node);
        List<Integer> res = new ArrayList<>();
        int index = 0;
        while (!que.isEmpty()) {
            int cur_node = que.poll();
            List<Integer> adj_nodes = edges.get(cur_node);
            for (int adj_node : adj_nodes) {
                if (--in_degree[adj_node] == 0)
                    que.offer(adj_node);
            }
            res.add(index++, cur_node);
        }
        return (index == nodes.size() ? res : new ArrayList<>());
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {

        Deque<Integer> que = new ArrayDeque<>();
        List<List<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++)
            edges.add(new ArrayList<>());
        int[] in_degree = new int[numCourses];
        for (int[] pre : prerequisites) {
            edges.get(pre[0]).add(pre[1]);
            in_degree[pre[1]]++;
        }
        int index = 0;
        int[] res = new int[numCourses];
        for (int i = 0; i < numCourses; i++)
            if (in_degree[i] == 0)
                que.offerLast(i);
        while (!que.isEmpty()) {
            int cur_node = que.pollFirst();
            res[index++] = cur_node;
            List<Integer> adj_edges = edges.get(cur_node);
            for (int adj_node : adj_edges) {
                if (--in_degree[adj_node] == 0)
                    que.offerLast(adj_node);
            }
        }
        if (index != numCourses)
            return new int[]{};
        else
            return res;


    }
        /*
        List<List<Integer>> list=new ArrayList<>();
        for(int i=0;i<numCourses;i++)
            list.add(new ArrayList<>());
        for(int[] pre:prerequisites)
            list.get(pre[0]).add(pre[1]);
        int[] status=new int[numCourses];
        boolean[] isVisited=new boolean[numCourses];
        int[] res=new int[numCourses];
        Deque<Integer> stack=new ArrayDeque<>();
        for(int i=0;i<numCourses;i++){
            if(!isVisited[i])
                if(!findOrder_dfs(list,i,status,isVisited,stack))
                    return new int[]{};
        }
        int index=numCourses-1;
        while(!stack.isEmpty())
            res[index--]=stack.pollLast();
        return res;

         */

    private boolean findOrder_dfs(List<List<Integer>> list, int cur_node, int[] status,
                                  boolean[] isVisited, Deque<Integer> stack) {
        List<Integer> adj_nodes = list.get(cur_node);
        status[cur_node] = 1;
        for (int adj_node : adj_nodes) {
            if (status[adj_node] == 0) {
                if (!findOrder_dfs(list, adj_node, status, isVisited, stack))
                    return false;
            } else if (status[adj_node] == 1)
                return false;
        }
        status[cur_node] = 2;
        isVisited[cur_node] = true;
        stack.offerLast(cur_node);
        return true;
    }

    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int n = s.length();
        int[] groups = new int[n];
        for (int i = 0; i < n; i++)
            groups[i] = i;
        int size = pairs.size();
        for (List<Integer> pair : pairs)
            smallest_union(groups, pair.get(0), pair.get(1));
        int group_num = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++)
            if (groups[i] == i)
                map.put(i, group_num++);
        char[] array = s.toCharArray();
        Queue<Character>[] ques = new PriorityQueue[group_num];
        for (int i = 0; i < n; i++) {
            int group_id = smallest_find_parent(groups, i);
            int j = map.get(group_id);
            if (ques[j] == null)
                ques[j] = new PriorityQueue<>();
            ques[j].offer(array[i]);
        }
        for (int i = 0; i < n; i++)
            array[i] = ques[map.get(groups[i])].poll();
        return String.valueOf(array);
    }

    private void smallest_union(int[] groups, int i, int j) {
        int i_num = smallest_parents_num(groups, i);
        int j_num = smallest_parents_num(groups, j);
        if (i_num < j_num)
            groups[smallest_find_parent(groups, i)] = smallest_find_parent(groups, j);
        else
            groups[smallest_find_parent(groups, j)] = smallest_find_parent(groups, i);
    }

    private int smallest_find_parent(int[] groups, int i) {
        if (groups[i] != i) {
            groups[i] = smallest_find_parent(groups, groups[i]);
        }
        return groups[i];

    }

    private int smallest_parents_num(int[] groups, int i) {
        int res = 0;
        while (groups[i] != i) {
            res++;
            i = groups[i];
        }
        return res;
    }

    public List<String> summaryRanges(int[] nums) {
        List<String> list = new LinkedList<>();
        int pre_index = 0;
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[pre_index] + i - pre_index) {
                if (i == pre_index + 1)
                    list.add("" + nums[pre_index]);
                else {
                    list.add(nums[pre_index] + "->" + nums[i - 1]);
                    pre_index = i;
                }
            }
        }
        if (pre_index != n - 1)
            list.add(pre_index + "->" + nums[n - 1]);
        else
            list.add("" + nums[n - 1]);
        return list;
    }

    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n <= 1)
            return 0;
        if (n == 2)
            return Math.max(0, prices[1] - prices[0]);
        int[][] dp = new int[4][n];
        dp[0][0] = -prices[0];
        dp[1][0] = dp[2][1] = dp[3][2] = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) {
            dp[0][i] = Math.max(dp[0][i - 1], -prices[i]);
            dp[1][i] = Math.max(dp[1][i - 1], dp[0][i - 1] + prices[i]);
            if (i > 1) {
                dp[2][i] = Math.max(dp[2][i - 1], dp[1][i - 1] - prices[i]);
                if (i > 2)
                    dp[3][i] = Math.max(dp[3][i - 1], dp[2][i - 1] + prices[i]);
            }

        }
        int res = Math.max(dp[3][n - 1], dp[1][n - 1]);
        return Math.max(res, 0);
    }

    public void rotate(int[] nums, int k) {
        k %= nums.length;
        rotate_swap(nums, 0, nums.length - 1);
        rotate_swap(nums, 0, k - 1);
        rotate_swap(nums, k, nums.length - 1);
    }

    private void rotate_swap(int[] nums, int begin, int end) {
        while (begin < end) {
            int tmp = nums[begin];
            nums[begin] = nums[end];
            nums[end] = tmp;
            begin++;
            end--;
        }
    }

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] parent = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (isConnected[i][j] == 1)
                    findCircleNum_union(parent, i, j);
        int res = 0;
        for (int i = 0; i < n; i++)
            if (parent[i] == i)
                res++;
        return res;
    }

    private int findCircleNum_find_parent(int[] parent, int index) {
        if (parent[index] != index)
            parent[index] = findCircleNum_find_parent(parent, parent[index]);
        return parent[index];
    }

    private void findCircleNum_union(int[] parent, int i, int j) {
        int i_parents = findCircleNum_parents_num(parent, i);
        int j_parents = findCircleNum_parents_num(parent, j);
        if (i_parents > j_parents)
            parent[findCircleNum_find_parent(parent, j)] = findCircleNum_find_parent(parent, i);
        else
            parent[findCircleNum_find_parent(parent, i)] = findCircleNum_find_parent(parent, j);
    }

    private int findCircleNum_parents_num(int[] parent, int i) {
        int res = 0;
        while (parent[i] != i) {
            i = parent[i];
            res++;
        }
        return res;
    }

    private void findCircleNum_dfs(boolean[] isVisited, int[][] isConnected, int i, int n) {
        for (int k = 0; k < n; k++) {
            if (isConnected[i][k] == 1 && !isVisited[k]) {
                isVisited[k] = true;
                findCircleNum_dfs(isVisited, isConnected, k, n);
            }
        }
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int node_num = 0;
        int size = equations.size();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String va = equations.get(i).get(0);
            String vb = equations.get(i).get(1);
            if (!map.containsKey(va))
                map.put(va, node_num++);
            if (!map.containsKey(vb))
                map.put(vb, node_num++);
        }
        double[][] edges = new double[node_num][node_num];
        for (int i = 0; i < node_num; i++)
            Arrays.fill(edges[i], -1);
        for (int i = 0; i < size; i++) {
            int va = map.get(equations.get(i).get(0));
            int vb = map.get(equations.get(i).get(1));
            edges[va][vb] = values[i];
            edges[vb][va] = 1 / values[i];
        }
        for (int k = 0; k < node_num; k++)
            for (int i = 0; i < node_num; i++)
                for (int j = 0; j < node_num; j++)
                    if (edges[i][k] > 0 && edges[k][j] > 0)
                        edges[i][j] = edges[i][k] * edges[j][k];
        int queries_size = queries.size();
        double[] res = new double[queries_size];
        for (int i = 0; i < queries_size; i++) {
            int va = map.get(queries.get(i).get(0));
            int vb = map.get(queries.get(i).get(1));
            res[i] = edges[va][vb];
        }
        return res;
    }
        /*
        List<calc_pair>[] edges=new List[node_num];
        for(int i=0;i<node_num;i++)
            edges[i]=new ArrayList<>();
        for(int i=0;i<size;i++){
            int va=map.get(equations.get(i).get(0));
            int vb=map.get(equations.get(i).get(1));
            edges[va].add(new calc_pair(vb,values[i]));
            edges[vb].add(new calc_pair(va,1.0/values[i]));
        }
        int queries_size=queries.size();
        double[] res=new double[queries_size];
        for(int i=0;i<queries_size;i++){
            double cur_res=-1;
            String va=queries.get(i).get(0);
            String vb=queries.get(i).get(1);
            if(map.containsKey(va)&&map.containsKey(vb)){
                if(va.equals(vb))
                    cur_res=1;
                else {
                    int a=map.get(va);
                    int b=map.get(vb);
                    Queue<Integer> que=new LinkedList<>();
                    double ratio[]=new double[node_num];
                    que.offer(a);
                    Arrays.fill(ratio,-1);
                    ratio[a]=1;
                    while(!que.isEmpty()&&ratio[b]==-1){
                        int cur_node=que.poll();
                        List<calc_pair> cur_list=edges[cur_node];
                        for(calc_pair new_pair:cur_list){
                            int dst=new_pair.dst;
                            double value=new_pair.value;
                            if(ratio[dst]==-1){
                                ratio[dst]=ratio[cur_node]*value;
                                que.offer(dst);
                            }

                        }
                    }
                    cur_res=ratio[b];
                }
            }
            res[i]=cur_res;
        }
        return res;

         */

    class calc_pair {
        int dst;
        double value;

        public calc_pair(int dst, double value) {
            this.dst = dst;
            this.value = value;
        }
    }

    public List<List<Integer>> largeGroupPositions(String s) {
        int len = s.length();
        List<List<Integer>> list = new LinkedList<>();
        int i;
        for (i = 0; i < len; i++) {
            int cur_len = 1;
            while (i < len - 1 && s.charAt(i) == s.charAt(i + 1)) {
                i++;
                cur_len++;
            }
            if (cur_len >= 3) {
                List<Integer> cur_list = new ArrayList<>();
                cur_list.add(i - cur_len + 1);
                cur_list.add(i);
                list.add(cur_list);
            }
            i++;
        }
        return list;
    }


    public int longestSubsequence(int[] arr, int difference) {
        Map<Integer, Integer> map = new HashMap<>();
        int len = arr.length;
        int[] dp = new int[len];
        int res = 1;
        for (int i = 0; i < len; i++) {
            int pre_num = arr[i] - difference;
            if (map.containsKey(pre_num)) {
                dp[i] = dp[map.get(pre_num)] + 1;
                res = Math.max(dp[i], res);
            } else dp[i] = 1;
            map.put(arr[i], i);

        }
        return res;
    }

    public int fib(int n) {
        int[] dp = new int[2];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            int cur = i & 1;
            dp[cur] = dp[cur] + dp[1 - cur];
        }
        return dp[n & 1];
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null)
            return null;
        ListNode dummy_node1 = new ListNode(0);
        ListNode dummy_node2 = new ListNode(0);
        ListNode cur_node = head;
        ListNode tmp_node1 = dummy_node1;
        ListNode tmp_node2 = dummy_node2;
        while (cur_node != null) {
            if (cur_node.val >= x) {
                tmp_node1.next = cur_node;
                tmp_node1 = cur_node;
            } else {
                tmp_node2.next = cur_node;
                tmp_node2 = cur_node;
            }
            cur_node = cur_node.next;
        }
        tmp_node2.next = dummy_node1.next;
        tmp_node1.next = null;

        return dummy_node2.next;
    }

    public int findLength(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        int[][] dp = new int[2][n + 1];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i] == B[j])
                    dp[1 & i][j + 1] = dp[~i & 1][j] + 1;
                else
                    dp[1 & i][j + 1] = 0;
                res = Math.max(res, dp[1 & i][j]);
            }
        }
        return res;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0)
            return new int[]{};
        Deque<Integer> que = new LinkedList<>();
        Deque<Integer> time = new LinkedList<>();
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            int cur_num = nums[i];
            int cur_time = 0;
            while (!que.isEmpty() && que.peekLast() <= cur_num) {
                cur_time += (time.pollLast() + 1);
                que.pollLast();
            }
            que.offerLast(cur_num);
            time.offerLast(cur_time);
            if (i >= k - 1 && i < res.length - 1 + k) {
                res[i - k + 1] = que.peekFirst();
                int cur_time1 = time.pollFirst();
                if (cur_time1 == 0)
                    que.pollFirst();
                else
                    time.offerFirst(cur_time1 - 1);
            }
        }
        return res;
    }

    class Pair {
        Integer t;
        Integer u;

        Pair(Integer t, Integer u) {
            this.t = t;
            this.u = u;
        }

        public Integer getT() {
            return t;
        }

        public Integer getU() {
            return u;
        }

        public void update(int i) {
            u += i;
        }

    }

    public int[] findBall(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[2][n];
        for (int i = 0; i < n; i++)
            dp[1][i] = i;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[~i & 1][j] == -1)
                    dp[i & 1][j] = -1;
                else {
                    int k = dp[~i & 1][j];
                    if ((k == 0 && grid[i][0] == -1) || (k == n - 1 && grid[i][n - 1] == 1)
                            || (k > 0 && grid[i][k] == -1 && grid[i][k - 1] == 1) || (k < n - 1 && grid[i][k] == 1 && grid[i][k + 1] == -1))
                        dp[i & 1][j] = -1;
                    else
                        dp[i & 1][j] = (grid[i][k] == -1 ? k - 1 : k + 1);
                }
            }
        }
        return dp[m - 1 & 1];
    }

    public int[][] matrixBlockSum(int[][] mat, int K) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i + 1][j + 1] = mat[i][j] + dp[i][j + 1] + dp[i + 1][j] - dp[i][j];
            }
        }
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                int k = Math.max(0, i - K);
                int p = Math.min(m, i + K + 1);
                int q = Math.min(n, j + K + 1);
                int z = Math.max(0, j - K);
                int sub1 = dp[k][z];
                int sub2 = dp[k][q];
                int sub3 = dp[p][z];
                int sub4 = dp[p][q];
                mat[i][j] = sub4 - sub3 - sub2 + sub1;
            }
        return mat;
    }

    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int len = flowerbed.length;
        int res = 0;
        int index = 0;
        int pre_index = -1;
        while (index < len) {
            while (index < len && flowerbed[index] != 1)
                index++;
            int gap = index - pre_index - 1;
            if (pre_index == -1 && index == len)
                res += (gap % 2 == 1 ? gap / 2 + 1 : gap / 2);
            else if (pre_index == -1 || index == len)
                res += gap / 2;
            else
                res += (gap % 2 == 1 ? gap / 2 : gap / 2 - 1);
            if (res >= n)
                return true;
            pre_index = index;
            index++;
        }
        return res >= n;
    }

    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0])
                    return 1;
                else if (o1[0] == o2[0])
                    return Integer.compare(o1[1], o2[1]);
                else
                    return -1;
            }
        });
        int res = 0;
        int pre_end = intervals[0][1];
        int len = intervals.length;
        for (int i = 1; i < len; i++) {
            if (intervals[i][0] >= pre_end) {
                pre_end = intervals[i][1];
                continue;
            }

            if (intervals[i][1] <= pre_end) {
                pre_end = intervals[i][1];

            }
            res++;
        }
        return res + (intervals[len - 1][0] >= pre_end ? 1 : 0);
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] dp = new int[2][n];
        int INF = Integer.MAX_VALUE / 2;
        Arrays.fill(dp[0], INF);
        Arrays.fill(dp[1], INF);
        for (int i = 0; i <= K; i++) {
            for (int[] flight : flights) {
                dp[i & 1][flight[1]] = Math.min(dp[i & 1][flight[1]], dp[~i & 1][flight[0]] + flight[2]);
            }
        }
        return dp[K & 1][dst] == INF ? -1 : dp[K & 1][dst];

    }

    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> que = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int stone : stones)
            que.offer(stone);
        while (que.size() > 1) {
            int stone1 = que.poll();
            int stone2 = que.poll();
            if (stone1 > stone2)
                que.offer(stone1 - stone2);

        }
        return que.isEmpty() ? 0 : que.poll();
    }


    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        k = Math.min(k, n / 2);
        int[][] buy = new int[n][k + 1];
        int[][] sell = new int[n][k + 1];
        buy[0][0] = -prices[0];
        sell[0][0] = 0;
        for (int i = 1; i < n; i++)
            buy[0][i] = sell[0][i] = Integer.MIN_VALUE / 2;
        for (int i = 1; i < n; i++) {
            buy[i][0] = Math.max(buy[i - 1][0], sell[i - 1][0] - prices[i]);
            for (int j = 1; j <= k; j++) {
                buy[i][j] = Math.max(buy[i - 1][j], sell[i - 1][j] - prices[i]);
                sell[i][j] = Math.max(buy[i - 1][j - 1] + prices[i], sell[i - 1][j]);
            }
        }
        int res = -1;
        for (int i = 0; i <= k; i++)
            res = Math.max(res, sell[n - 1][i]);
        return res;
    }

    public int minPatches(int[] nums, int n) {
        long miss = 1;
        int res = 0;
        int index = 0;
        int len = nums.length;
        while (miss <= (long) n) {
            if (index < len) {
                int num = nums[index++];
                while (miss <= n && miss < num) {
                    miss *= 2;
                    res++;
                }
                miss += num;
            } else {
                miss *= 2;
                res++;
            }

        }
        return res;
    }

    static public boolean checkSubarraySum(int[] nums, int k) {
        int sum = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (k != 0)
                sum = sum % k;
            if (map.containsKey(sum)) {
                if (i - map.get(sum) > 1)
                    return true;
            } else
                map.put(sum, i);
        }
        return false;
    }

    static public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] up_cont = new int[m + 1][n + 2];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                up_cont[i + 1][j + 1] = (matrix[i][j] == '1' ? up_cont[i][j + 1] + 1 : 0);
        int res = 0;
        for (int i = 0; i < m; i++) {
            Deque<Integer> stack = new ArrayDeque<>();
            stack.push(0);
            for (int j = 0; j <= n; j++) {
                while (up_cont[i + 1][j + 1] < up_cont[i + 1][stack.peek()]) {
                    int height = up_cont[i + 1][stack.pop()];
                    int weight = j - stack.peek();
                    res = Math.max(height * weight, res);
                }
                stack.push(j + 1);
            }
        }
        return res;
    }

    static public String pushDominoes(String dominoes) {
        int len = dominoes.length();
        String[] dp = new String[len];
        dp[0] = dominoes.substring(0, 1);
        for (int i = 1; i < len; i++) {
            if (dominoes.charAt(i) == 'R')
                dp[i] = dp[i - 1] + 'R';
            else if (dominoes.charAt(i) == '.') {
                if (dp[i - 1].charAt(i - 1) == 'R')
                    dp[i] = dp[i - 1] + 'R';
                else
                    dp[i] = dp[i - 1] + '.';
            } else {
                int index = i - 1;
                while (index >= 1 && dominoes.charAt(index) == '.')
                    index--;
                if (dominoes.charAt(index) == 'L') {
                    int tmp_len = i - index;
                    dp[i] = dp[i - 1] + "L".repeat(Math.max(0, tmp_len));
                } else if (dominoes.charAt(index) == 'R') {
                    int tmp_len = (i - index - 1) / 2;
                    if ((i - index - 1) % 2 == 0)
                        dp[i] = dp[index] + "R".repeat(tmp_len - 1) + "L".repeat(tmp_len);
                    else
                        dp[i] = dp[index] + "R".repeat(tmp_len - 1) + "." + "L".repeat(tmp_len);
                } else
                    dp[i] = "L".repeat(i + 1);
            }
        }
        return dp[len - 1];
    }

    public int findContentChildren(int[] g, int[] s) {
        int index1 = 0;
        int len1 = g.length;
        int index2 = 0;
        int len2 = s.length;
        Arrays.sort(g);
        Arrays.sort(s);
        int res = 0;
        while (index1 < len1 && index2 < len2) {
            while (index2 < len2 && s[index2] < g[index1])
                index2++;
            if (index2 == len2)
                return res;
            res++;
            index2++;
            index1++;
        }
        return res;

    }

    public int minFallingPathSum(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        if (n == 1) {
            int ans = 0;
            for (int[] ints : A) ans += ints[0];
            return ans;
        }
        int[] pre_dp = new int[n];
        int[] cur_dp = new int[n];
        System.arraycopy(A[0], 0, pre_dp, 0, n);
        for (int i = 1; i < m; i++) {
            cur_dp[0] = Math.min(pre_dp[0], pre_dp[1]) + A[i][0];
            for (int j = 1; j < n - 1; j++)
                cur_dp[j] = Math.min(Math.min(pre_dp[j - 1], pre_dp[j]), pre_dp[j + 1]) + A[i][j];
            cur_dp[n - 1] = Math.min(pre_dp[n - 2], pre_dp[n - 1]) + A[i][n - 1];
            System.arraycopy(cur_dp, 0, pre_dp, 0, n);
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++)
            res = Math.min(res, pre_dp[i]);
        return res;
    }

    public int findTargetSumWays(int[] nums, int S) {
        int len = nums.length;
        int res = backtracing_findTargetSumWays(nums, 0, len, 0, S);
        return res;
    }

    private int backtracing_findTargetSumWays(int[] nums, int cur_index, int end, int cur_sum, int S) {
        if (cur_index == end) {
            if (cur_sum == S) {
                return 1;
            }
            return 0;
        }
        return backtracing_findTargetSumWays(nums, cur_index + 1, end, cur_sum + nums[cur_index], S) +
                backtracing_findTargetSumWays(nums, cur_index + 1, end, cur_sum - nums[cur_index], S);
    }

    public List<List<String>> partition(String s) {
        List<List<String>> res = new LinkedList<>();
        List<String> list = new LinkedList<>();
        int end = s.length();
        boolean[][] dp = new boolean[end][end];
        for (int i = end - 1; i >= 0; i--)
            for (int j = end - 1; j >= i; j--) {
                if (j == i || (s.charAt(i) == s.charAt(j) && (j - i == 1 || dp[i + 1][j - 1])))
                    dp[i][j] = true;
            }
        backtracing_partition(s, dp, 0, end, res, list);
        return res;

    }


    private void backtracing_partition(String s, boolean[][] dp, int begin, int end, List<List<String>> res, List<String> list) {
        if (begin == end) {
            List<String> list_copy = new LinkedList<>(list);
            res.add(list_copy);
            return;
        }
        for (int i = begin; i < end; i++) {
            if (!dp[begin][i])
                continue;
            list.add(s.substring(begin, i + 1));
            backtracing_partition(s, dp, i + 1, end, res, list);
            list.remove(list.size() - 1);
        }
    }

    private boolean isPalindrome(String str) {
        int len = str.length();
        int sub_len = str.length() / 2;
        for (int i = 0; i < sub_len; i++) {
            if (str.charAt(i) != str.charAt(len - 1 - i))
                return false;
        }
        return true;
    }

    /*
    public List<List<String>> partition(String s) {
        int len=s.length();
        List<List<String>>[] dp= new List[len];
        dp[0]=new LinkedList<List<String>>();
        List<String> tmp_list=new LinkedList<String>();
        tmp_list.add(s.substring(0,1));
        dp[0].add(tmp_list);
        for(int i=1;i<len;i++){
            dp[i]=new LinkedList<>();
            for(int j=i;j>=1;j--){
                String cur_str=s.substring(j,i+1);
                if(isPalindrome(cur_str)){
                    for(List<String> pre_list:dp[j-1]){
                        List<String> pre_list_copy=new LinkedList<>(pre_list);
                        pre_list_copy.add(s.substring(j,i+1));
                        dp[i].add(pre_list_copy);
                    }
                }
            }
            if(isPalindrome(s.substring(0,i+1))){
                List<String> cur_list=new LinkedList<>();
                cur_list.add(s.substring(0,i+1));
                dp[i].add(cur_list);
            }
        }
        return dp[len-1];
    }
    private boolean isPalindrome(String str){
        int len=str.length();
        int sub_len=str.length()/2;
        for(int i=0;i<sub_len;i++){
            if(str.charAt(i)!=str.charAt(len-1-i))
                return false;
        }
        return true;
    }

     */
    public int candy(int[] ratings) {
        int len = ratings.length;
        int[] left = new int[len];
        int[] right = new int[len];
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);
        for (int i = 1; i < len; i++)
            if (ratings[i] < ratings[i - 1])
                left[i] = left[i - 1] + 1;
        for (int i = len - 1; i > 0; i--)
            if (ratings[i - 1] > ratings[i])
                right[i - 1] = right[i] + 1;
        int res = 0;
        for (int i = 0; i < len; i++)
            res += Math.max(left[i], right[i]);
        return res;
    }

    static public int findSubstringInWraproundString(String p) {
        if (p.length() == 0)
            return 0;
        int[] table = new int[26];
        int len = p.length();
        int res = 1;
        table[p.charAt(0) - 'a'] = 1;
        int pre_index = 0;
        for (int i = 1; i < len; i++) {
            if ((p.charAt(i - 1) != p.charAt(i) - 1) && (p.charAt(i - 1) != 'z' || p.charAt(i) != 'a'))
                pre_index = i;
            int index = p.charAt(i) - 'a';
            res += Math.max(0, i - pre_index + 1 - table[index]);
            table[index] = Math.max(table[index], i - pre_index + 1);
        }
        return res;
    }

    public int countVowelStrings(int n) {
        int[] dp = new int[5];
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            dp[0] = 1;
            dp[1] = dp[1] + 1;
            dp[2] = dp[2] + dp[1];
            dp[3] = dp[3] + dp[2];
            dp[4] = dp[4] + dp[3];
        }
        return dp[0] + dp[1] + dp[2] + dp[3] + dp[4];
    }

    /*
    public int numRollsToTarget(int d, int f, int target) {
        int[] pre_dp=new int[target];
        int[] cur_dp=new int[target];
        Arrays.fill(pre_dp,1);
        int res=0;
        if(target<=f)
            res+=1;
        for(int i=1;i<d;i++){
            if(i>target)
                break;
            int begin=Math.max(i-f,0);
            int end=Math.min(target,(i+1)*f);
            for(int j=begin;j<=end;j++)

        }
    }

     */
    public int firstUniqChar(String s) {
        int len = s.length();
        int[] table = new int[26];
        for (int i = 0; i < len; i++)
            table[s.charAt(i) - 'a']++;
        for (int i = 0; i < len; i++)
            if (table[s.charAt(i)] == 1)
                return i;
        return -1;

    }

    public int deleteAndEarn(int[] nums) {
        if (nums.length == 0)
            return 0;
        int max_num = 0;
        for (int num : nums)
            max_num = Math.max(max_num, num);
        int[] count = new int[max_num + 1];
        for (int num : nums) {
            count[num] += num;
        }
        int[] dp = new int[max_num + 1];
        dp[0] = 0;
        dp[1] = count[1];
        for (int i = 2; i <= max_num; i++)
            dp[i] = Math.max(dp[i - 2] + count[i], dp[i - 1]);
        return dp[max_num];
    }

    public int longestCommonSubsequence(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++)
            for (int j = 1; j <= len2; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        return dp[len1][len2];
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null)
            return null;
        List<List<Integer>> res = new ArrayList<>();
        boolean is_reverse = false;
        int cur_layer_num = 1;
        int next_layer_num = 2;
        Queue<TreeNode> que = new LinkedList<>();
        que.add(root);
        while (!que.isEmpty()) {
            Integer[] cur_list = new Integer[cur_layer_num - 1];
            next_layer_num = cur_layer_num * 2;
            for (int i = 0; i < cur_layer_num; i++) {
                TreeNode cur_node = que.poll();
                if (is_reverse)
                    cur_list[cur_layer_num - 1 - i] = cur_node.val;
                else
                    cur_list[i] = cur_node.val;
                if (cur_node.left == null)
                    next_layer_num--;
                else
                    que.offer(cur_node.left);
                if (cur_node.right == null)
                    next_layer_num--;
                else
                    que.offer(cur_node.right);
            }
            ArrayList<Integer> cur_arraylist = new ArrayList<>();
            cur_arraylist.addAll(Arrays.asList(cur_list));
            is_reverse = !is_reverse;
            res.add(cur_arraylist);
            cur_layer_num = next_layer_num;
        }
        return res;
    }

    static public int[] findSquare(int[][] matrix) {
        int ans_r = Integer.MAX_VALUE;
        int ans_c = Integer.MAX_VALUE;
        int ans_size = -1;
        int n = matrix.length;
        int[][] sub_matrix_size = new int[n][n];
        int[][] up_cont_zeros = new int[n][n];
        int[][] left_cont_zeros = new int[n][n];
        for (int i = 0; i < n; i++) {
            up_cont_zeros[0][i] = (matrix[0][i] == 0 ? 1 : 0);
            left_cont_zeros[i][0] = (matrix[i][0] == 0 ? 1 : 0);
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0)
                    up_cont_zeros[i][j] = up_cont_zeros[i - 1][j] + 1;
                else
                    up_cont_zeros[i][j] = 0;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0)
                    left_cont_zeros[i][j] = left_cont_zeros[i][j - 1] + 1;
                else
                    left_cont_zeros[i][j] = 0;
            }
        }
        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                sub_matrix_size[0][i] = 1;
                if (ans_r >= 0 && i < ans_c) {
                    ans_r = 0;
                    ans_c = i;
                    ans_size = 1;
                }
            }
            if (matrix[i][0] == 0) {
                sub_matrix_size[i][0] = 1;
                if (ans_r > i) {
                    ans_r = i;
                    ans_c = 0;
                    ans_size = 1;
                }
            }
        }
        for (int i = 1; i < n; i++)
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 1) {
                    sub_matrix_size[i][j] = 0;
                    continue;
                }
                int tmp_size = sub_matrix_size[i - 1][j - 1];
                sub_matrix_size[i][j] = Math.min(Math.min(up_cont_zeros[i][j], left_cont_zeros[i][j]), tmp_size + 1);
                int tmp_r = i - sub_matrix_size[i][j] + 1;
                int tmp_c = j - sub_matrix_size[i][j] + 1;
                if (sub_matrix_size[i][j] > ans_size) {
                    ans_r = tmp_r;
                    ans_c = tmp_c;
                    ans_size = sub_matrix_size[i][j];
                } else if (sub_matrix_size[i][j] == ans_size) {
                    if (tmp_r < ans_r || (tmp_r == ans_r && tmp_c < ans_c)) {
                        ans_r = tmp_r;
                        ans_c = tmp_c;
                    }
                }
            }
        if (ans_r == Integer.MAX_VALUE)
            return new int[]{};
        return new int[]{ans_r, ans_c, ans_size};
    }

    static public int minCostClimbingStairs(int[] cost) {
        int len = cost.length;
        if (len == 1 || len == 0)
            return 0;
        int pre_cost1 = cost[0];
        int pre_cost2 = cost[1];
        int cur_cost = Integer.MAX_VALUE;
        for (int i = 2; i < len; i++) {
            cur_cost = Math.min(pre_cost1, pre_cost2) + cost[i];
            pre_cost1 = pre_cost2;
            pre_cost2 = cur_cost;
        }
        return Math.min(cur_cost, pre_cost1);
    }

    static public int findPaths(int m, int n, int N, int i, int j) {
        if (N == 0)
            return 0;
        int[][] dp = new int[m + 2][n + 2];
        for (int p = 1; p <= n; p++) {
            dp[1][p]++;
            dp[m][p]++;
        }
        for (int q = 1; q <= m; q++) {
            dp[q][1]++;
            dp[q][n]++;
        }
        int res = dp[i + 1][j + 1];

        int fixed_num = 1000000007;
        for (int time = 1; time < N; time++) {
            int[][] cur_dp = new int[m + 2][n + 2];
            for (int p = 1; p <= m; p++)
                for (int q = 1; q <= n; q++) {
                    cur_dp[p][q] = ((dp[p + 1][q] + dp[p - 1][q]) % fixed_num + (dp[p][q - 1] + dp[p][q + 1]) % fixed_num) % fixed_num;
                    if (p == i + 1 && q == j + 1)
                        res = (res + cur_dp[p][q]) % fixed_num;
                }
            dp = cur_dp;
        }
        return res;
    }

    public boolean canPartition(int[] nums) {
        int sum = 0;
        int len = nums.length;
        for (int num : nums)
            sum += num;
        if (sum % 2 == 1)
            return false;
        sum /= 2;
        int[] dp = new int[sum + 1];
        for (int i = 1; i <= len; i++) {
            for (int j = sum; j >= nums[i]; j--)
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
        }
        return dp[sum] == sum;
    }

    public String removeDuplicateLetters(String s) {
        int[] table = new int[26];
        boolean[] seat = new boolean[26];
        int len = s.length();
        for (int i = 0; i < len; i++)
            table[s.charAt(i) - 'a']++;
        Deque<Character> que = new ArrayDeque<>();
        ;
        for (int i = 0; i < len; i++) {
            if (seat[s.charAt(i) - 'a']) {
                table[s.charAt(i) - 'a']--;
                continue;
            }

            while (!que.isEmpty() && que.peek() >= s.charAt(i) && table[que.peek() - 'a'] > 0) {
                char tmp = que.pop();
                seat[tmp - 'a'] = false;
            }
            que.push(s.charAt(i));
            seat[s.charAt(i) - 'a'] = true;
            table[s.charAt(i) - 'a']--;
        }
        StringBuilder res = new StringBuilder();
        while (!que.isEmpty())
            res.append(que.pop());
        return res.reverse().toString();
    }

    public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 1][n + 1];
        for (int j = 1; j <= n; j++) {
            for (int i = j - 1; i >= 1; i--) {
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++)
                    dp[i][j] = Math.max(dp[i][j], Math.max(dp[i][k - 1], dp[k + 1][j]) + k);
            }
        }
        return dp[1][n];
    }

    static public int numDecodings(String s) {
        if (s.charAt(0) == '0')
            return 0;
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= len; i++) {
            int cur = s.charAt(i - 1) - '0';
            int pre = s.charAt(i - 2) - '0';
            if ((pre == 0 || pre > 2) && cur == 0)
                return 0;
            else if (pre == 0 || (pre == 2 && cur >= 7) || pre >= 3)
                dp[i] = dp[i - 1];
            else if (cur == 0)
                dp[i] = dp[i - 2];
            else
                dp[i] = dp[i - 1] + dp[i - 2];

        }
        return dp[len];
    }

    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++)
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
        }
        return dp[n];
    }

    static public void rotate(int[][] matrix) {
        int n = matrix.length;
        int times = n / 2;
        for (int i = 0; i < times; i++) {
            for (int j = i; j < n - 2 * i - 1; j++) {
                int tmp = matrix[j][n - 1 - j];
                matrix[j][n - 1 - i] = matrix[i][j];
                matrix[i][j] = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                matrix[n - 1 - i][n - 1 - j] = tmp;
            }
        }
    }

    public char findTheDifference(String s, String t) {
        int len = s.length();
        int[] table = new int[26];
        for (int i = 0; i < len; i++) {
            table[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < len + 1; i++) {
            int i1 = t.charAt(i) - 'a';
            if (table[i1] > 0)
                table[i1]--;
            else
                return t.charAt(i);
        }
        return ' ';
    }

    public int maxProfit(int[] prices, int fee) {
        int len = prices.length;
        int pre_dp1 = -prices[0];//持有股票最大收益
        int dp1;
        int pre_dp2 = 0;//不持有股票最大收益
        int dp2;
        for (int i = 1; i < len; i++) {
            dp1 = Math.max(pre_dp1, pre_dp2 - prices[i]);
            dp2 = Math.max(pre_dp2, pre_dp1 + prices[i] - fee);
            pre_dp1 = dp1;
            pre_dp2 = dp2;
        }
        return pre_dp2;
    }

    public int maxScore(int[] cardPoints, int k) {
        int[] left_sum = new int[k + 1];
        int[] right_sum = new int[k + 1];
        int len = cardPoints.length;
        left_sum[1] = cardPoints[0];
        right_sum[1] = cardPoints[len - 1];
        for (int i = 2; i <= k; i++) {
            left_sum[i] = left_sum[i - 1] + cardPoints[i - 1];
            right_sum[i] = right_sum[i - 1] + cardPoints[len - i];
        }
        int res = 0;
        for (int i = 0; i <= k; i++)
            res = Math.max(left_sum[i] + right_sum[k - i], res);
        return res;
    }

    static public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for (int stone : stones)
            sum += stone;
        int[] dp = new int[sum / 2 + 1];
        for (int stone : stones) {
            for (int j = sum / 2; j >= stone; j--)
                dp[j] = Math.max(dp[j], dp[j - stone] + stone);
        }
        return sum - 2 * dp[sum / 2];
    }

    static public boolean wordPattern(String pattern, String s) {
        HashMap<Character, String> map = new HashMap<>();
        HashMap<String, Character> map2 = new HashMap<>();
        int len = s.length();
        int len2 = pattern.length();
        int begin = 0;
        int end = 0;
        int index = 0;
        while (index < len2 && begin < len) {
            while (end < len && s.charAt(end) != ' ')
                end++;
            String sub_string = s.substring(begin, end);
            if (map.containsKey(pattern.charAt(index)))
                if (!map.get(pattern.charAt(index)).equals(sub_string))
                    return false;
            if (map2.containsKey(sub_string))
                if (!map2.get(sub_string).equals(pattern.charAt(index)))
                    return false;
            map.put(pattern.charAt(index), sub_string);
            map2.put(sub_string, pattern.charAt(index));

            begin = end + 1;
            end++;
            index++;
        }
        return index == len2 && begin == len + 1;
    }

    public int[] getMaxMatrix(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int[][] prefix_sum = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            int pre_sum = 0;
            for (int j = 0; j < columns; j++) {
                pre_sum += matrix[i][j];
                if (i == 0)
                    prefix_sum[i][j] = pre_sum;
                else
                    prefix_sum[i][j] = pre_sum + prefix_sum[i - 1][j];
            }
        }
        int max_sum = Integer.MIN_VALUE;
        int[] res = new int[4];
        for (int i = 0; i < rows; i++) {
            for (int j = i; j < rows; j++) {
                int pre_sum = -1;
                int[] pre_max_sub_matrix = new int[]{0, 0};
                int cur_sum;
                for (int k = 0; k < columns; k++) {
                    if (pre_sum < 0) {
                        cur_sum = sub_matrix_sum(prefix_sum, new int[]{i, k, j, k});
                        if (cur_sum > max_sum) {
                            max_sum = cur_sum;
                            res[0] = i;
                            res[1] = k;
                            res[2] = j;
                            res[3] = k;
                        }
                        pre_max_sub_matrix[0] = k;
                        pre_max_sub_matrix[1] = k;
                    } else {
                        cur_sum = pre_sum + sub_matrix_sum(prefix_sum, new int[]{i, k, j, k});
                        if (cur_sum > max_sum) {
                            max_sum = cur_sum;
                            res[0] = i;
                            res[1] = pre_max_sub_matrix[0];
                            res[2] = j;
                            res[3] = k;
                        }
                        pre_max_sub_matrix[1] = k;
                    }
                    pre_sum = cur_sum;

                }
            }
        }
        return res;
    }

    private int sub_matrix_sum(int[][] prefix_sum, int[] index) {
        int r1 = index[0];
        int c1 = index[1];
        int r2 = index[2];
        int c2 = index[3];
        if (r1 >= 1 && c1 >= 1)
            return prefix_sum[r2][c2] - prefix_sum[r1 - 1][c2]
                    - prefix_sum[r2][c1 - 1] + prefix_sum[r1 - 1][c1 - 1];
        else if (r1 == 0 && c1 == 0)
            return prefix_sum[r2][c2];
        else if (r1 == 0)
            return prefix_sum[r2][c2] - prefix_sum[r2][c1 - 1];
        else
            return prefix_sum[r2][c2] - prefix_sum[r1 - 1][c2];
    }

    static public int monotoneIncreasingDigits(int N) {
        StringBuilder builder = new StringBuilder();
        while (N != 0) {
            builder.append(N % 10);
            N /= 10;
        }
        char[] tmp_array = builder.reverse().toString().toCharArray();
        int len = tmp_array.length;
        int i;
        for (i = 1; i < len; i++) {
            if (tmp_array[i] < tmp_array[i - 1])
                break;
        }
        while (i >= 2 && tmp_array[i - 2] == tmp_array[i - 1])
            i--;
        if (i < len) {
            tmp_array[i - 1] -= 1;
            for (; i < len; i++)
                tmp_array[i] = '9';
        }
        int res = 0;
        for (i = 0; i < len; i++) {
            res = res * 10 + (int) (tmp_array[i] - '0');
        }
        return res;
    }

    /**
     * 记忆化回溯（也称为递归+备忘录），自顶向下
     * 采用记忆化后的时间复杂度为O(2^n)(如果不进行记忆的话，时间复杂度将是O(n!))，可以理解为已经缩成了只有一个分支了
     * 然后为什么要进行记忆化：
     * 因为我们发现，例如[2,3]和[3,2]之后的玩家选择状态都是一样的，都是可以从除了2,3之外的
     * 数字进行选择，那么就可以对选择2和3后第一个玩家能不能赢进行记忆存储
     * 这里采用state[]数组存储每个数字是否都被选过，选过则记录为1，然后我们将state.toString()
     * 使得[2,3]和[3,2]它们的结果都是一样的"0011"，作为key，存储在HashMap中，value是选了2和3
     * 之后第一个玩家是否稳赢
     *
     * @param maxChoosableInteger
     * @param desiredTotal
     * @return
     */

    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        if (maxChoosableInteger >= desiredTotal)
            return true;
        if ((maxChoosableInteger + 1) * maxChoosableInteger / 2 < desiredTotal)
            return false;
        int[] record = new int[maxChoosableInteger + 1];
        HashMap<String, Boolean> map = new HashMap<>();
        return trace_back_canIWin(record, desiredTotal, map);
    }

    private String stringize(int[] array) {
        int len = array.length;
        StringBuilder builder = new StringBuilder();
        for (int value : array) builder.append(value);
        return builder.toString();
    }

    private boolean trace_back_canIWin(int[] record, int desiredTotal, HashMap<String, Boolean> map) {
        String cur_str = stringize(record);
        if (map.containsKey(cur_str))
            return map.get(cur_str);
        int len = record.length;
        for (int i = 1; i < len; i++) {
            if (record[i] == 0) {
                record[i] = 1;
                if (desiredTotal - i <= 0 || !trace_back_canIWin(record, desiredTotal - i, map)) {
                    map.put(cur_str, true);
                    record[i] = 0;
                    return true;
                }
                record[i] = 0;
            }
        }
        map.put(cur_str, false);
        return false;
    }


    static public void findMaxForm(String[] strs, int m, int n) {
        List<List<String>> list = new LinkedList<>();
        List<String> cur_list = new LinkedList<>();
        findMaxFormHelper(strs, list, cur_list, 0, strs.length - 1, m, n, 0, 0);
        System.out.println(list);
    }

    static public void findMaxFormHelper(String[] strs, List<List<String>> list, List<String> cur_list,
                                         int start, int end, int m, int n, int pre_sum_m, int pre_sum_n) {
        if (start > end)
            return;
        for (int i = start; i <= end; i++) {
            String cur_str = strs[i];
            int zero_num = 0;
            int one_nums = 0;
            int len = cur_str.length();
            for (int j = 0; j < len; j++) {
                if (cur_str.charAt(j) == '1')
                    one_nums++;
                else
                    zero_num++;
            }
            if (pre_sum_m + zero_num <= m && pre_sum_n + one_nums <= n) {
                cur_list.add(cur_str);
                List<String> cur_list_copy = new LinkedList<>(cur_list);
                list.add(cur_list_copy);
                findMaxFormHelper(strs, list, cur_list,
                        i + 1, end, m, n, pre_sum_m + zero_num, pre_sum_n + one_nums);
                cur_list.remove(cur_list.size() - 1);
            }

        }
    }

    static public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<Integer, List<String>> map = new HashMap<>();
        for (String str : strs) {
            int[] cur_array = new int[26];
            int len = str.length();
            for (int i = 0; i < len; i++)
                cur_array[str.charAt(i) - 'a']++;
            if (!map.containsKey(Arrays.hashCode(cur_array))) {
                List<String> cur_list = new LinkedList<>();
                cur_list.add(str);
                map.put(Arrays.hashCode(cur_array), cur_list);
            } else
                map.get(Arrays.hashCode(cur_array)).add(str);
        }
        List<List<String>> list = new LinkedList<>();
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    static public int maxSumDivThree(int[] nums) {
        int[] dp = new int[3];
        for (int num : nums) {
            int tmp = num % 3;
            if (tmp == 0) {
                dp[0] += num;
                dp[1] += num;
                dp[2] += num;
            } else {
                if (tmp == 1) {
                    int t = dp[0];
                    if (dp[2] % 3 == 2) dp[0] = Math.max(dp[0], dp[2] + num);
                    if (dp[1] % 3 == 1) dp[2] = Math.max(dp[2], dp[1] + num);
                    if (dp[0] % 3 == 0) dp[1] = Math.max(dp[1], t + num);
                } else {
                    int t = dp[0];
                    if (dp[1] % 3 == 1) dp[0] = Math.max(dp[0], dp[1] + num);
                    if (dp[2] % 3 == 2) dp[1] = Math.max(dp[1], dp[2] + num);
                    dp[2] = Math.max(dp[2], t + num);
                }
            }
        }
        return dp[0];
    }

    public int minHeightShelves(int[][] books, int shelf_width) {
        int nums = books.length;
        int[] dp = new int[nums];
        dp[0] = books[0][1];
        for (int i = 1; i < nums; i++) {
            dp[i] = dp[i - 1] + books[i][1];
            int total_width = books[i][0];
            int max_height = books[i][1];
            for (int j = i - 1; j >= 1; j--) {
                if (books[j][0] + total_width <= shelf_width) {
                    dp[i] = Math.min(dp[i], dp[j - 1] + max_height);
                    total_width += books[j][0];
                    max_height = Math.max(max_height, books[j][1]);
                }
            }
            if (books[0][0] + total_width <= shelf_width) {
                dp[i] = Math.min(dp[i], max_height);
            }
        }
        return dp[nums - 1];
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (!set.add(num))
                return true;
        }
        return false;
    }

    public int findMinMoves(int[] machines) {
        int machine_num = machines.length;
        int clothes = 0;
        for (int num : machines)
            clothes += num;
        if (clothes % machine_num != 0)
            return -1;
        int avg = clothes / machine_num;
        int res = 0;
        for (int num : machines)
            res = Math.max(res, Math.abs(num - avg));
        return res;

    }

    public int wiggleMaxLength(int[] nums) {
        int len = nums.length;
        int[] up = new int[len];
        int[] down = new int[len];
        up[0] = 1;
        down[0] = 1;
        for (int i = 1; i < len; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = Math.max(up[i - 1], down[i - 1] + 1);
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                down[i] = Math.max(down[i - 1], up[i - 1] + 1);
                up[i] = up[i - 1];
            } else {
                up[i] = up[i - 1];
                down[i] = down[i - 1];
            }
        }
        return Math.max(up[len - 1], down[len - 1]);
    }

    public int knightDialer(int n) {
        int big_num = 1000000007;
        int[] pre_dp = new int[10];
        Arrays.fill(pre_dp, 1);
        int[] dp = new int[10];
        for (int i = 2; i <= n; i++) {
            dp[1] = (pre_dp[6] + pre_dp[8]) % big_num;
            dp[2] = (pre_dp[7] + pre_dp[9]) % big_num;
            dp[3] = (pre_dp[4] + pre_dp[8]) % big_num;
            dp[4] = (pre_dp[3] + pre_dp[9] + pre_dp[0]) % big_num;
            dp[6] = (pre_dp[1] + pre_dp[7] + pre_dp[0]) % big_num;
            dp[7] = (pre_dp[2] + pre_dp[6]) % big_num;
            dp[8] = (pre_dp[1] + pre_dp[3]) % big_num;
            dp[9] = (pre_dp[2] + pre_dp[4]) % big_num;
            dp[0] = (pre_dp[4] + pre_dp[6]) % big_num;
            System.arraycopy(dp, 0, pre_dp, 0, 10);
        }
        int sum = 0;
        for (int i = 0; i < 10; i++)
            sum = (sum + dp[i]) % big_num;
        return sum;
    }

    static public int minSteps(int n) {
        int[] dp = new int[n + 1];
        dp[1] = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = i - 1; j >= 1; j--) {
                if (i % j == 0) {
                    dp[i] = dp[j] + i / j;
                    break;
                }
            }
        }
        return dp[n];
    }

    static public String predictPartyVictory(String senate) {
        Queue<Character> queue = new LinkedList<Character>();
        int len = senate.length();
        int radiant_left_num = 0;
        int dire_left_num = 0;
        int radiant_left_rights = 0;
        int dire_left_rights = 0;
        for (int i = 0; i < len; i++) {
            queue.offer(senate.charAt(i));
            if (senate.charAt(i) == 'D')
                dire_left_num++;
            else
                radiant_left_num++;
        }
        while (!queue.isEmpty() && dire_left_num > 0 && radiant_left_num > 0) {
            if (queue.poll() == 'R') {
                if (dire_left_rights > 0) {
                    radiant_left_num--;
                    dire_left_rights--;
                } else {
                    radiant_left_rights++;
                    queue.offer('R');
                }
            } else {
                if (radiant_left_rights > 0) {
                    dire_left_num--;
                    radiant_left_rights--;
                } else {
                    dire_left_rights++;
                    queue.offer('D');
                }

            }
        }
        return radiant_left_num == 0 ? "Dire" : "Radiant";
    }

    public boolean lemonadeChange(int[] bills) {
        int[] nums = new int[2];
        int len = bills.length;
        for (int bill : bills) {
            if (bill == 5)
                nums[0]++;
            else if (bill == 10)
                if (nums[0] == 0)
                    return false;
                else {
                    nums[0]--;
                    nums[1]++;
                }

            else {
                if (nums[0] >= 1 && nums[1] >= 1) {
                    nums[0]--;
                    nums[1]--;
                } else if (nums[0] >= 3)
                    nums[0] -= 3;
                else
                    return false;
            }
        }
        return true;
    }

    static public double new21Game(int N, int K, int W) {
        if (K == 0 || N == 0)
            return 1;
        double[] dp = new double[N + 1];
        double res = 0;
        dp[0] = 1;
        int pre_start = 0;
        int pre_end = 1;
        double sum = (double) 1 / W;
        for (int i = 1; i <= N; i++) {
            int start = Math.max(0, i - W);
            int end = Math.min(i, K);
            if (start > pre_start) {
                sum -= dp[pre_start] / W;
                pre_start++;
            }
            if (end > pre_end) {
                sum += dp[pre_end] / W;
                pre_end++;
            }
            dp[i] = sum;
            if (i >= K)
                res += dp[i];
        }
        return res;

    }

    public int uniquePaths(int m, int n) {
        int[] res = new int[n];
        Arrays.fill(res, 1);
        for (int i = m - 1; i >= 1; i--) {
            for (int j = n - 2; j >= 0; j--)
                res[j] = res[j] + res[j + 1];
        }
        return res[0];
    }

    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> list = new ArrayList<>();
        splitBacktrace(list, S, S.length(), 0, 0, 0);
        return list;
    }

    public boolean splitBacktrace(List<Integer> list, String s, int len, int index, int sum, int prev) {
        if (index == len)
            return list.size() >= 3;
        long currLong = 0;
        for (int i = index; i < len; i++) {
            if (i > index && s.charAt(index) == '0')
                break;
            currLong = 10 * currLong + s.charAt(i) - '0';
            if (currLong > Integer.MAX_VALUE)
                break;
            int curr = (int) currLong;
            if (list.size() >= 2) {
                if (curr < sum)
                    continue;
                if (curr > sum)
                    break;
            }
            list.add(curr);
            if (splitBacktrace(list, s, len, i + 1, prev + curr, curr))
                return true;
            else
                list.remove(list.size() - 1);
        }
        return false;
    }

    static public int matrixScore(int[][] A) {
        int rows = A.length;
        int columns = A[0].length;
        int[] res = new int[columns];
        res[0] = rows;
        boolean head = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (j == 0) {
                    head = A[i][0] == 0;
                } else {
                    if (head)
                        A[i][j] = (A[i][j] == 0 ? 1 : 0);
                    res[j] += A[i][j];
                }
            }
        }
        int ret = 0;
        for (int i = 0; i < columns; i++) {
            ret += (res[i] > rows / 2 ? res[i] << (columns - 1 - i) : (rows - res[i]) << (columns - 1 - i));
        }
        return ret;
    }

    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        int left = 0b11110000;
        int mid = 0b00111100;
        int right = 0b0000111;
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] seats : reservedSeats) {
            int tmp = (map.getOrDefault(seats[0], 0));
            tmp += 1 << (seats[1] - 2);
            map.put(seats[0], tmp);
        }
        int res = (n - map.size()) * 2;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int row = entry.getKey();
            int maskbit = entry.getValue();
            if ((maskbit | left) == left || (maskbit | mid) == mid || (maskbit | right) == right)
                res++;
        }
        return res;
    }

    static public boolean checkIfCanBreak(String s1, String s2) {
        char[] array_1 = s1.toCharArray();
        int len = s1.length();
        char[] array_2 = s2.toCharArray();
        int len2 = s2.length();
        if (len != len2)
            return false;
        Arrays.sort(array_1);
        Arrays.sort(array_2);
        int index = len - 1;
        while (index >= 0 && array_1[index] == array_2[index])
            index--;
        if (index == -1)
            return true;
        if (array_1[index] > array_2[index]) {
            char[] tmp = array_1;
            array_1 = array_2;
            array_2 = tmp;
        }
        for (int i = index; i >= 0; i--)
            if (array_1[i] > array_2[i])
                return false;
        return true;
    }

    static public boolean canArrange(int[] arr, int k) {
        int[] k_array = new int[k];
        for (int value : arr) {
            k_array[(value % k + k) % k] += 1;
        }
        for (int i = 1; i <= k / 2; i++)
            if (k_array[i] != k_array[k - i])
                return false;
        return k_array[0] % 2 == 0;
    }

    static public int FindPoisonedDuration(int[] timeSeries, int duration) {
        int len = timeSeries.length;
        int res = 0;
        int begin = timeSeries[0];
        int end = timeSeries[0] + duration;
        for (int i = 1; i < len; i++) {
            if (timeSeries[i] >= end) {
                res += end - begin;
                begin = timeSeries[i];
            }
            end = timeSeries[i] + duration;
        }
        res += end - begin;
        return res;
    }

    static public int maxTurbulenceSize(int[] arr) {
        int len = arr.length;
        int res = 1;
        int[] max_flow1 = new int[len];
        max_flow1[0] = 1;
        int[] max_flow2 = new int[len];
        max_flow2[0] = 1;
        for (int i = 1; i < len; i++) {
            if (i % 2 == 0) {
                if (arr[i - 1] > arr[i]) {
                    max_flow1[i] = max_flow1[i - 1] + 1;
                    max_flow2[i] = 1;
                } else if (arr[i - 1] < arr[i]) {
                    max_flow1[i] = 1;
                    max_flow2[i] = max_flow2[i - 1] + 1;
                } else {
                    max_flow1[i] = 1;
                    max_flow2[i] = 1;
                }
            } else {
                if (arr[i - 1] < arr[i]) {
                    max_flow1[i] = max_flow1[i - 1] + 1;
                    max_flow2[i] = 1;
                } else if (arr[i - 1] > arr[i]) {
                    max_flow1[i] = 1;
                    max_flow2[i] = max_flow2[i - 1] + 1;
                } else {
                    max_flow1[i] = 1;
                    max_flow2[i] = 1;
                }
            }
            res = Math.max(Math.max(max_flow1[i], max_flow2[i]), res);
        }
        System.out.println(Arrays.toString(max_flow1));
        System.out.println(Arrays.toString(max_flow2));
        return res;
    }

    static public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> pre_row = new ArrayList<>();
        pre_row.add(1);
        for (int i = 0; i < numRows; i++) {
            res.add(pre_row);
            int len = pre_row.size();
            List<Integer> cur_row = new ArrayList<>();
            cur_row.add(1);
            for (int j = 0; j < len - 1; j++) {
                cur_row.add(pre_row.get(j) + pre_row.get(j + 1));
            }
            cur_row.add(1);
            pre_row = cur_row;
        }
        return res;
    }

    public int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> map = new HashMap<>();
        for (char ch : tasks)
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        int m = map.size();
        List<Integer> next_valid = new ArrayList<>();
        List<Integer> rest = new ArrayList<>();
        Set<Map.Entry<Character, Integer>> set = map.entrySet();
        for (Map.Entry<Character, Integer> entry : set) {
            next_valid.add(1);
            rest.add(entry.getValue());
        }
        int time = 0;
        int len = tasks.length;
        for (int i = 0; i < len; i++) {
            time++;
            int min_valid_time = Integer.MAX_VALUE;
            for (int j = 0; j < m; j++)
                if (rest.get(j) != 0)
                    min_valid_time = Math.min(min_valid_time, next_valid.get(j));
            time = Math.min(time, min_valid_time);
            int next_index = -1;
            for (int j = 0; j < m; j++) {
                if (rest.get(j) > 0 && next_valid.get(j) <= time)
                    if (next_index == -1 || rest.get(next_index) < rest.get(j))
                        next_index = j;
            }
            next_valid.set(next_index, time + n + 1);
            rest.set(next_index, rest.get(next_index) - 1);
        }
        return time;
    }

    static class task_node {
        char task_name;
        int task_num;
        int wait_time;

        task_node(char name, int num, int time) {
            this.task_name = name;
            this.task_num = num;
            this.wait_time = time;
        }

        void add_num(int i) {
            this.task_num += i;
        }

        void set_wait_time(int k) {
            this.wait_time = k;
        }

        void add_wait_time(int i) {
            this.wait_time += i;
        }

    }
    /*
    static public int leastInterval(char[] tasks, int n) {
        int res=0;
        task_node[] task=new task_node[26];
        int len=tasks.length;
        for(int i=0;i<len;i++){
            if(task[(int)(tasks[i]-'A')]==null)
                task[(int)(tasks[i]-'A')]=new task_node(tasks[i],1,0);
            else
                task[(int)(tasks[i]-'A')].add_num(1);
        }
        PriorityQueue<task_node> running_que=new PriorityQueue<>(new Comparator<task_node>() {
            @Override
            public int compare(task_node o1, task_node o2) {
                return o2.task_num-o1.task_num;
            }
        });
        PriorityQueue<task_node> waiting_que=new PriorityQueue<>(new Comparator<task_node>() {
            @Override
            public int compare(task_node o1, task_node o2) {
                return o1.wait_time-o2.wait_time;
            }
        });
        for(int i=0;i<26;i++)
            if(task[i]!=null)
                running_que.offer(task[i]);
        while(!running_que.isEmpty()||!waiting_que.isEmpty()){
            res++;
            //System.out.println("当前时间："+res);
            for(task_node tmp_task:waiting_que){
                tmp_task.add_wait_time(-1);
            }
            while(!waiting_que.isEmpty()&&waiting_que.peek().wait_time==0){
                task_node tmp_task=waiting_que.poll();
                running_que.offer(tmp_task);
                //System.out.println("获得运行资格： "+tmp_task.task_name);
            }

            if(!running_que.isEmpty()){
                task_node cur_task=running_que.poll();
                //System.out.println("执行: "+cur_task.task_name+" 剩余次数："+(cur_task.task_num-1));
                if(cur_task.task_num>1){
                    cur_task.add_num(-1);
                    cur_task.set_wait_time(n+1);
                    waiting_que.offer(cur_task);
                    //System.out.println("加入等待列: "+cur_task.task_name);
                }
            }

            else
                System.out.println("当前运行列为空");

        }
        return res;
    }

     */

    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        dp[0] = 1;
        int res = 0;
        for (int i = 1; i < len; i++) {
            int tmp = 0;
            for (int j = 0; j < i; j++) {
                tmp = Math.max(tmp, dp[j]);
            }
            dp[i] = tmp + 1;
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public boolean isPossible(int[] nums) {
        Map<Integer, Integer> count_map = new HashMap<>();
        Map<Integer, Integer> end_map = new HashMap<>();
        for (int num : nums) {
            count_map.put(num, count_map.getOrDefault(num, 0) + 1);
        }
        for (int num : nums) {
            int num_count = count_map.getOrDefault(num, 0);
            if (num_count == 0)
                continue;
            if (end_map.containsKey(num - 1)) {
                count_map.put(num, num_count - 1);
                end_map.put(num, end_map.getOrDefault(num, 0) + 1);
                if (end_map.get(num - 1) == 1)
                    end_map.remove(num - 1);
                else
                    end_map.put(num - 1, end_map.get(num - 1) - 1);
            } else {
                int count1 = count_map.getOrDefault(num + 1, 0);
                int count2 = count_map.getOrDefault(num + 2, 0);
                if (count1 == 0 || count2 == 0)
                    return false;
                count_map.put(num, num_count - 1);
                count_map.put(num + 1, count1 - 1);
                count_map.put(num + 2, count2 - 1);
                end_map.put(num + 2, end_map.getOrDefault(num + 2, 0) + 1);
            }
        }
        return true;
    }

    /*
    public int lengthOfLISHelper(int[] nums,int length,int left,int right){
        if(left==right)
            return 1;
        int mid=left+(right-left)/2;
        int left_length=lengthOfLISHelper(nums,length,left,mid);
        int right_length=lengthOfLISHelper(nums,length,mid+1,right);
        int mid_length;
        int left_index=mid;
        int right_index=mid+1;
        if(nums[left_index]>nums[right_index])
            mid_length=0;
        while(left_index>=1&&nums[left_index-1]<=nums[left_index])
            left_index--;
        while(right_index<length-1&&nums[right_index+1]>=nums[right_index])
            right_index++;
        mid_length=right_index-left_index+1;
        return Math.max(mid_length,Math.max(left_length,right_length));
    }

     */
    public int countPrimes(int n) {
        if (n <= 2)
            return 0;
        int res = 0;
        int[] isPrime = new int[n];
        Arrays.fill(isPrime, 1);
        int upper_bound = (int) Math.sqrt(n);
        for (int i = 2; i <= upper_bound; i++) {
            if (isPrime[i] == 1)
                res++;
            int tmp = i * i;
            while (tmp < n) {
                isPrime[tmp] = 0;
                tmp += i;
            }
        }
        return res;
    }

    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        if (len == 0)
            return 0;
        int[] new_heights = new int[len + 2];
        int res = 0;
        for (int i = 0; i < len; i++)
            new_heights[i + 1] = heights[i];
        new_heights[0] = 0;
        new_heights[len + 1] = 0;
        heights = new_heights;
        len += 2;
        Stack<Integer> stack = new Stack<>();
        stack.add(0);
        for (int i = 1; i < len; i++) {
            while (!stack.isEmpty() && heights[stack.peek()] > heights[i]) {
                int height = heights[stack.peek()];
                stack.pop();
                int weight = i - stack.peek() - 1;
                res = Math.max(res, height * weight);
            }
            stack.add(i);
        }
        return res;

    }

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;
        int begin = k - Math.min(k, n);
        int end = Math.min(m, k);
        int[] maxArray = new int[k];
        for (int i = begin; i <= end; i++) {
            int[] tmp1 = maxSubArray(nums1, i);
            int[] tmp2 = maxSubArray(nums2, k - i);
            int[] mergeArray = mergeArray(tmp1, tmp2);
            if (compareArray(mergeArray, 0, maxArray, 0) > 0)
                System.arraycopy(mergeArray, 0, maxArray, 0, k);
        }
        return maxArray;
    }

    public int[] mergeArray(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        if (len1 == 0)
            return nums2;
        if (len2 == 0)
            return nums1;
        int[] res = new int[len1 + len2];
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < len1 + len2; i++) {
            if (compareArray(nums1, index1, nums2, index2) > 0)
                res[i] = nums1[index1++];
            else
                res[i] = nums2[index2++];
        }
        return res;
    }

    public int compareArray(int[] nums1, int index1, int[] nums2, int index2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int i = index1;
        int j = index2;
        while (i < len1 && j < len2) {
            int diff = nums1[i] - nums2[j];
            if (diff != 0)
                return diff;
            i++;
            j++;
        }
        return (len1 - i) - (len2 - j);
    }

    public int[] maxSubArray(int[] nums, int k) {
        int[] stack = new int[k];
        int len = nums.length;
        int top = -1;
        int remain = len - k;
        for (int num : nums) {
            while (top >= 0 && stack[top] < num && remain > 0) {
                top--;
                remain--;
            }
            if (top < k - 1)
                stack[++top] = num;
            else
                remain--;
        }
        return stack;
    }


    static public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int min_val = matrix[0][0];
        int max_val = matrix[n - 1][n - 1];
        int mid_val;
        while (min_val < max_val) {
            mid_val = min_val + (max_val - min_val) / 2;
            int sum = kthSmallestHelper(matrix, mid_val);
            if (sum < k)
                min_val = mid_val + 1;

            else
                max_val = mid_val;
        }
        return min_val;
    }

    static public int kthSmallestHelper(int[][] matrix, int val) {
        int n = matrix.length;
        int row = n - 1;
        int column = 0;
        int res = 0;
        while (row >= 0) {
            while (column < n && matrix[row][column] <= val)
                column++;
            res += column;
            row--;
        }
        return res;
    }

    public int[] searchRange(int[] nums, int target) {
        int left_index = searchRangeHelper1(nums, target, 0, nums.length - 1);
        int right_index = searchRangeHelper2(nums, target, 0, nums.length - 1);
        if (nums[left_index] != target)
            return new int[]{-1, -1};
        return new int[]{left_index, right_index};
    }

    private int searchRangeHelper1(int[] nums, int target, int left, int right) {
        if (left == right)
            return left;
        int mid = left + (right - left) / 2;
        if (nums[mid] < target)
            return searchRangeHelper1(nums, target, mid + 1, right);
        else
            return searchRangeHelper1(nums, target, left, mid);
    }

    private int searchRangeHelper2(int[] nums, int target, int left, int right) {
        if (left == right)
            return left;
        int mid = left + (right - left + 1) / 2;
        if (nums[mid] >= target)
            return searchRangeHelper2(nums, target, mid, right);
        else
            return searchRangeHelper2(nums, target, left, mid - 1);
    }

    static public String reorganizeString(String S) {
        int len = S.length();
        int[] store_len = new int[26];
        int max_num = 0;
        for (int i = 0; i < len; i++)
            max_num = Math.max(max_num, ++store_len[S.charAt(i) - 'a']);
        if (max_num > (len + 1) / 2)
            return "";
        PriorityQueue<Character> que = new PriorityQueue<>(new Comparator<Character>() {
            @Override
            public int compare(Character o1, Character o2) {
                return store_len[o2 - 'a'] - store_len[o1 - 'a'];
            }
        });
        for (int i = 0; i < 26; i++)
            if (store_len[i] != 0)
                que.offer((char) (i + 'a'));
        StringBuilder res = new StringBuilder();
        while (que.size() > 1) {
            char char_1 = que.poll();
            char char_2 = que.poll();
            res.append(char_1);
            res.append(char_2);
            store_len[char_1 - 'a']--;
            store_len[char_2 - 'a']--;
            if (store_len[char_1 - 'a'] != 0)
                que.offer(char_1);
            if (store_len[char_2 - 'a'] != 0)
                que.offer(char_2);
        }
        if (que.size() == 1)
            res.append(que.poll());
        return res.toString();

    }

    static public int maxIndex(int[] a) {
        int max_index = 0;
        int len = a.length;
        for (int i = 0; i < len; i++)
            if (a[i] > a[max_index])
                max_index = i;
        return max_index;
    }

    public int largestPerimeter(int[] A) {
        int len = A.length;
        Arrays.sort(A);
        for (int index = len - 1; index >= 2; index--) {
            if (A[index] > A[index - 1] + A[index - 2])
                return A[index] + A[index - 1] + A[index - 2];
        }

        return 0;
    }

    public int reversePairs(int[] nums) {
        TreeSet<Long> set = new TreeSet<>();
        int len = nums.length;
        for (int num : nums) {
            set.add((long) num);
            set.add((long) num * 2);
        }
        Map<Long, Integer> map = new HashMap<>();
        int index = 1;
        for (Long num : set)
            map.put(num, index++);
        segmentNode root = buildSegmentTree(1, map.size());
        int res = 0;
        for (int num : nums) {
            int left = map.get((long) 2 * num);
            int right = map.size();
            res += countSegmentNode(root, 1, right) - countSegmentNode(root, 1, left);
            addSegmentNode(root, map.get((long) num));
        }
        return res;
    }

    private segmentNode buildSegmentTree(int left, int right) {
        segmentNode root = new segmentNode(left, right);
        if (left == right)
            return root;
        int middle = (left + right) / 2;
        root.leftChild = buildSegmentTree(left, middle);
        root.rightChild = buildSegmentTree(middle + 1, right);
        return root;
    }

    private void addSegmentNode(segmentNode root, int val) {
        root.sum++;
        if (root.left == root.right)
            return;
        int middle = (root.left + root.right) / 2;
        if (val <= middle)
            addSegmentNode(root.leftChild, val);
        else
            addSegmentNode(root.rightChild, val);
    }

    private int countSegmentNode(segmentNode root, int l, int r) {
        if (root.left > r || root.right < l)
            return 0;
        else if (root.right <= r && root.left >= l)
            return root.sum;
        else
            return countSegmentNode(root.leftChild, l, r) + countSegmentNode(root.rightChild, l, r);
    }

    static class segmentNode {
        int left;
        int right;
        int sum;
        segmentNode leftChild;
        segmentNode rightChild;

        public segmentNode(int l, int r) {
            left = l;
            right = r;
            leftChild = null;
            rightChild = null;
        }
    }

    class BIT {
        int n;
        int[] tree;

        BIT(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        int lowbit(int x) {
            return x & (-x);
        }

        /*
        在x中增加d
         */
        void update(int x, int d) {
            while (x <= n) {
                tree[x] += d;
                x += lowbit(x);
            }
        }

        /*
        1-x
         */
        int query(int x) {
            int res = 0;
            while (x > 0) {
                res += tree[x];
                x -= lowbit(x);
            }
            return res;
        }
    }

    public int reversePairsHelper(int[] nums, int left, int right) {
        if (left == right)
            return 0;
        int sum = 0;
        int mid = left + (right - left) / 2;
        int left_sum = reversePairsHelper(nums, left, mid);
        int right_sum = reversePairsHelper(nums, mid + 1, right);
        int left_index = left;
        int right_index = mid + 1;
        while (left_index <= mid) {
            while (right_index <= right && (long) 2 * nums[right_index] < (long) nums[left_index])
                right_index++;
            sum += right_index - mid - 1;
            left_index++;
        }
        left_index = left;
        right_index = mid + 1;
        int[] tmp = new int[right - left + 1];
        int index = 0;
        while (left_index <= mid && right_index <= right) {
            if (nums[left_index] <= nums[right_index])
                tmp[index++] = nums[left_index++];
            else
                tmp[index++] = nums[right_index++];
        }
        while (left_index <= mid)
            tmp[index++] = nums[left_index++];
        while (right_index <= right)
            tmp[index++] = nums[right_index++];
        System.arraycopy(tmp, 0, nums, left, right + 1 - left);
        return sum + left_sum + right_sum;
    }

    static public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int a : A)
            for (int b : B)
                map.put(a + b, map.getOrDefault(a + b, 0) + 1);
        int res = 0;
        for (int c : C)
            for (int d : D)
                res += map.getOrDefault(-(c + d), 0);
        return res;
    }

    public static char[] Calculate(char[] data, String polynomial) {
        int data_len = data.length;
        int poly_len = polynomial.length();
        /*
        int index=0;
        for(;index<poly_len;index++){
            if(polynomial.charAt(index)=='1')
                break;
        }
        polynomial=polynomial.substring(index);
        poly_len=polynomial.length();
        */
        char[] res = new char[poly_len - 1];
        for (int i = poly_len; i <= data_len; i++) {
            if (data[i - poly_len] == '1') {
                for (int j = 0; j < poly_len - 1; j++)
                    res[j] = ((data[i - poly_len + 1 + j] == '1') ^ (polynomial.charAt(j + 1) == '1')) ? '1' : '0';
            } else {
                for (int j = 0; j < poly_len - 1; j++)
                    res[j] = (data[i - poly_len + 1 + j] == '1') ? '1' : '0';
            }
            System.arraycopy(res, 0, data, i - poly_len + 1, poly_len - 1);

        }

        return res;
    }

    public int maximumGap(int[] nums) {
        int len = nums.length;
        int maxVal = Arrays.stream(nums).max().getAsInt();
        int exp = 1;
        int[] buf = new int[len];
        while (maxVal >= exp) {
            int[] cnt = new int[10];
            for (int i = 0; i < len; i++) {
                int tmp = (nums[i] / exp) % 10;
                cnt[tmp]++;
            }
            for (int i = 1; i < 10; i++)
                cnt[i] += cnt[i - 1];
            for (int i = len - 1; i >= 0; i--) {
                int tmp = (nums[i] / exp) % 10;
                buf[cnt[tmp] - 1] = nums[i];
                cnt[tmp]--;
            }
            System.arraycopy(buf, 0, nums, 0, len);
            exp *= 10;
        }
        int ret = 0;
        for (int i = 0; i < len - 1; i++)
            ret = Math.max(ret, nums[i + 1] - nums[i]);
        return ret;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSumDfs(candidates, target, res, combine, 0, 0);
        return res;
    }

    public void combinationSumDfs(int[] candidates, int target, List<List<Integer>> res,
                                  List<Integer> combine, int sum, int index) {
        if (sum == target) {
            res.add(new ArrayList<>(combine));
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            if (sum + candidates[i] <= target) {
                combine.add(candidates[i]);
                combinationSumDfs(candidates, target, res, combine, sum + candidates[i], index);
                combine.remove(combine.size() - 1);
            } else {
                break;
            }
        }

    }

    static public int[] countBits(int num) {
        if (num == 0)
            return new int[]{0};
        int[] res = new int[num + 1];
        res[0] = 0;
        for (int i = 1; i <= num; i++) {
            if (i % 2 == 0)
                res[i] = res[i / 2];
            else
                res[i] = res[i - 1] + 1;
        }
        return res;

    }

    static public String sortString(String s) {
        int[] bottle = new int[26];
        int len = s.length();
        for (int i = 0; i < len; i++)
            bottle[s.charAt(i) - 'a']++;
        StringBuilder res = new StringBuilder();
        while (res.length() < len) {
            for (int i = 0; i < 26; i++) {
                if (bottle[i] > 0) {
                    res.append((char) (i + 'a'));
                    bottle[i]--;
                }
            }
            for (int i = 25; i >= 0; i--)
                if (bottle[i] > 0) {
                    res.append((char) (i + 'a'));
                    bottle[i]--;
                }
        }
        return res.toString();
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int countNodes(TreeNode root) {
        int level = 0;
        TreeNode tmpNode = root;
        while (tmpNode != null) {
            level++;
            tmpNode = tmpNode.left;
        }
        int lowerBound = 1 << level;
        int upperBound = 1 << (level + 1) - 1;
        while (lowerBound < upperBound) {
            int mid = (upperBound - lowerBound) / 2 + lowerBound;
            if (judgeExit(root, level, mid))
                lowerBound = mid + 1;
            else
                upperBound = mid;
        }
        return lowerBound;
    }

    public boolean judgeExit(TreeNode root, int level, int k) {
        int bits = 1 << (level - 1);
        TreeNode tmpNode = root;
        while (tmpNode != null && bits > 0) {
            if ((bits & k) == 0)
                tmpNode = tmpNode.left;
            else
                tmpNode = tmpNode.right;
        }
        return tmpNode != null;
    }

    static public int cuttingRope(int n) {
        return cuttingRopeHelper(n, n - 1);

    }

    static public int cuttingRopeHelper(int n, int maxM) {
        if (n > 0 && maxM == 0)
            return -1;
        if (n == 0)
            return 1;
        int res = 0;
        for (int i = 1; i <= maxM && i <= n; i++) {
            res = Math.max(res, i * cuttingRopeHelper(n - i, i));
        }

        return res;
    }

    static public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() == 0)
            return s;
        int len = s.length();
        StringBuilder[] res = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++)
            res[i] = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int tmp = i % (2 * numRows - 2);
            if (tmp <= numRows - 1)
                res[tmp].append(s.charAt(i));
            else
                res[2 * numRows - 2 - tmp].append(s.charAt(i));
        }
        StringBuilder ans = new StringBuilder();
        for (StringBuilder str : res)
            ans.append(str);
        return ans.toString();
    }

    static public int maxValue(int[][] grid) {
        int column_length = grid[0].length;
        int row_length = grid.length;
        if (column_length == 0) return 0;
        for (int i = column_length - 2; i >= 0; i--)
            grid[row_length - 1][i] = grid[row_length - 1][i] + grid[row_length - 1][i + 1];
        for (int i = row_length - 2; i >= 0; i--) {
            grid[i][column_length - 1] = grid[i][column_length - 1] + grid[i + 1][column_length - 1];
            for (int j = column_length - 2; j >= 0; j--)
                grid[i][j] = grid[i][j] + Math.max(grid[i + 1][j], grid[i][j + 1]);
        }
        return grid[0][0];
    }

    static public int findMinArrowShots(int[][] points) {
        if (points.length == 0)
            return 0;
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return (o1[1] < o2[1] ? -1 : (o1[1] == o2[1] ? 0 : 1));
            }
        });
        int res = 0;
        int len = points.length;
        int bra = points[0][1];
        for (int i = 1; i < len; i++) {
            System.out.println("[ " + points[i][0] + ", " + points[i][1] + "]");
            if (points[i][0] > bra) {
                res++;
                bra = points[i][1];
            }
        }
        return res + 1;
    }

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length())
            return false;
        HashMap<Character, Integer> map = new HashMap<>();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);

        }
        for (int i = 0; i < len; i++) {
            if (!map.containsKey(t.charAt(i)))
                return false;
            map.put(t.charAt(i), map.get(t.charAt(i)) - 1);
        }

        for (Character c : map.keySet())
            if (map.get(c) != 0)
                return false;
        return true;
    }


    static public int[] singleNumbers(int[] nums) {
        int res = 0;
        int index = 1;
        for (int i : nums)
            res = res ^ i;
        while ((index & res) == 0)
            index <<= 1;
        int a = 0, b = 0;
        for (int i : nums) {
            if ((i & index) != 0)
                a ^= i;
            else
                b ^= i;
        }
        return new int[]{a, b};

    }


    public ListNode sortList(ListNode head) {
        if (head == null) return null;
        ListNode dummyNode = new ListNode(0, head);
        ListNode node = head;
        int length = 0;
        while (node != null) {
            length++;
            node = node.next;
        }
        for (int subLength = 1; subLength < length; subLength <<= 1) {
            ListNode pre = dummyNode, curr = pre.next;
            while (curr != null) {
                ListNode head1 = curr;
                for (int i = 1; i < subLength && curr.next != null; i++) {
                    curr = curr.next;
                }
                ListNode head2 = curr.next;
                curr.next = null;
                curr = head2;
                for (int i = 1; i < subLength && curr != null && curr.next != null; i++)
                    curr = curr.next;
                ListNode next = null;
                if (curr != null) {
                    next = curr.next;
                    curr.next = null;
                }
                pre.next = merge(head1, head2);
                while (pre.next != null)
                    pre = pre.next;
                curr = next;
            }
        }
        return dummyNode.next;
    }

    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyNode = new ListNode(0);
        ListNode tmp = dummyNode, tmp1 = head1, tmp2 = head2;
        while (tmp1 != null && tmp2 != null) {
            if (tmp1.val < tmp2.val) {
                tmp.next = tmp1;
                tmp = tmp.next;
                tmp1 = tmp1.next;
            } else {
                tmp.next = tmp2;
                tmp = tmp.next;
                tmp2 = tmp2.next;
            }

        }
        if (tmp1 == null)
            tmp.next = tmp2;
        else
            tmp.next = tmp1;
        return dummyNode.next;
    }

    static public int upper_log(int n) {
        if (n <= 0) return -1;
        boolean add_one = false;
        int res = 0;
        while (n != 1) {
            if (n % 2 == 1)
                add_one = true;
            n /= 2;
            res++;
        }
        return add_one ? res + 1 : res;
    }

    //List<String> list=new LinkedList<>();
    static public List<String> translateNum(int num) {
        List<String> cur_list = new LinkedList<>();
        String str = String.valueOf(num);
        if (str.length() == 1) {
            cur_list.add("" + (char) ('a' + num));
            return cur_list;
        } else if (str.length() == 0)
            return cur_list;
        String first_char = "" + (char) ('a' + Integer.parseInt(str.substring(0, 1)));
        List<String> cur_list1 = translateNum(Integer.parseInt(str.substring(1)));
        List<String> cur_list2 = new LinkedList<>();
        String first_two_chars = str.substring(0, 2);
        if (Integer.parseInt(first_two_chars) <= 25) {
            first_two_chars = "" + (char) ('a' + Integer.parseInt(str.substring(0, 2)));
            cur_list2 = translateNum(Integer.parseInt(str.substring(2)));
        }
        for (String tmp_str : cur_list1)
            cur_list.add(first_char + tmp_str);
        for (String tmp_str : cur_list2)
            cur_list.add(first_two_chars + tmp_str);
        return cur_list;

    }

    public ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode(0), pre;
        dummy.next = head;
        while (head != null && head.next != null) {
            if (head.val < head.next.val) {
                head = head.next;
                continue;
            }
            pre = dummy;
            while (pre.next.val < head.next.val)
                pre = pre.next;
            ListNode curr = head.next;
            head.next = curr.next;
            curr.next = pre.next;
            pre.next = curr;
        }
        return dummy.next;
    }

    static public int movingCount(int m, int n, int k) {
        boolean[][] scan = new boolean[m][n];
        return countSum(0, 0, k, m, n, scan);
    }

    static public int countSum(int row, int column, int k, int m, int n, boolean[][] scan) {
        if (row < 0 || row >= m || column < 0 || column >= n || row / 10 + row % 10 + column / 10 + column % 10 > k || scan[row][column])
            return 0;
        scan[row][column] = true;
        //System.out.println("row: "+row+" column: "+column);
        return countSum(row - 1, column, k, m, n, scan) + countSum(row + 1, column, k, m, n, scan) +
                countSum(row, column - 1, k, m, n, scan) +
                countSum(row, column + 1, k, m, n, scan) + 1;

    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0)
            return null;
        Map<Integer, Integer> map = new HashMap<>();
        int len = inorder.length;
        for (int i = 0; i < len; i++)
            map.put(inorder[i], i);
        return buildTreeHelper(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1, map);
    }

    private TreeNode buildTreeHelper(int[] preorder, int preLeft, int preRight,
                                     int[] inorder, int inLeft, int inRight, Map<Integer, Integer> map) {

        int curVal = preorder[preLeft];
        TreeNode curNode = new TreeNode(curVal);
        int index = map.get(curVal);
        if (index == inLeft)
            curNode.left = null;
        else
            curNode.left = buildTreeHelper(preorder, preLeft + 1, preLeft + index - inLeft,
                    inorder, inLeft, index - 1, map);
        if (index == inRight)
            curNode.right = null;
        else
            curNode.right = buildTreeHelper(preorder, preLeft + index - inLeft + 1, preRight,
                    inorder, index + 1, inRight, map);
        return curNode;
    }

    static public void moveZeroes(int[] nums) {
        int i = -1;
        int len = nums.length;
        for (int j = 0; j < len; j++) {
            if (nums[j] != 0) {
                nums[++i] = nums[j];
                nums[j] = 0;
            }
        }
    }

    static public int canCompleteCircuit(int[] gas, int[] cost) {

        int sumCost = gas[0] - cost[0];
        int len = gas.length;
        int left = 0;
        int right = 0;
        int preIndex;
        for (int i = 1; i < len; i++) {
            if (sumCost < 0) {
                preIndex = (left + len - 1) % len;
                sumCost += gas[preIndex] - cost[preIndex];
                left = preIndex;
            } else {
                right = (right + 1) % len;
                sumCost += gas[right] - cost[right];
            }
            System.out.println("left: " + left + " right: " + right + " sumCost: " + sumCost);
        }
        return sumCost < 0 ? -1 : left;
    }

    int[] dr = {1, 1, -1, -1};
    int[] dc = {1, -1, -1, 1};

    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        int maxDis = Math.max(r0, R - 1 - r0) + Math.max(c0, C - 1 - c0);
        int row = r0;
        int column = c0;
        int[][] res = new int[R * C][];
        int index = 0;
        res[index++] = new int[]{r0, c0};
        for (int dis = 1; dis < maxDis; dis++) {
            row--;
            for (int i = 0; i < 4; i++) {
                while ((i % 2 == 0 && row != r0) || (i % 2 == 1 && column != c0)) {
                    if (row >= 0 && row < R && column >= 0 && column < C)
                        res[index++] = new int[]{row, column};
                    row += dr[i];
                    column += dc[i];
                }
            }
        }
        return res;
    }

    static public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0])
                    return o2[1] - o1[1];
                else
                    return o1[0] - o2[0];
            }
        });
        int len = people.length;
        int[][] res = new int[len][];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < len; i++)
            list.add(i);
        for (int[] person : people) {
            int index = person[1];
            res[list.get(index)] = person;
            list.remove(index);
        }
        return res;
    }
    /*
    static public int[][] reconstructQueue(int[][] people) {
        TreeMap<Integer,Set<Integer>> map=new TreeMap<>();
        for(int[] person:people){
           if(!map.containsKey(person[0])){
               TreeSet<Integer> set=new TreeSet<Integer>(new Comparator<Integer>() {
                   @Override
                   public int compare(Integer o1, Integer o2) {
                       return o2-o1;
                   }
               });
               set.add(person[1]);
               map.put(person[0],set);
           }
           else{
               TreeSet<Integer> aSet=(TreeSet<Integer>)map.get(person[0]);
               aSet.add(person[1]);
               map.put(person[0],aSet);
           }
        }
        int len=people.length;
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<len;i++)
            list.add(i);
        for(Integer i:map.keySet()){
            TreeSet<Integer> aSet=(TreeSet<Integer>)map.get(i);
            for(Integer j:aSet){
                //System.out.print("i: "+i+" j: "+j+" ");
                int index=list.get(j);
                //System.out.print("index: "+index+" ");
                people[index]=new int[]{i,j};
                //System.out.print("the position is going to be deleted: "+j+" ");
                list.remove((int)j);
            }
        }
        return people;
    }

    static public String removeKdigits(String num, int k) {
        Deque<Character> deque=new LinkedList<>();
        int len=num.length();
        for(int i=0;i<len;i++){
            char aChar=num.charAt(i);
            while(!deque.isEmpty()&&k>0&&deque.peekLast()>aChar){
                deque.pollLast();
                k--;
            }
            deque.offerLast(aChar);
        }
        for(int i=0;i<k;i++)
            deque.pollLast();
        StringBuilder res=new StringBuilder();
        boolean zeroHead=true;
        while(!deque.isEmpty()){
            char aChar=deque.pollFirst();
            if(!zeroHead||aChar!='0'){
                zeroHead=false;
                res.append(aChar);
            }
        }
        return res.length()==0?"0":res.toString();
    }
    /*
    static public String removeKdigits(String num, int k) {
        int len=num.length();
        int index=0;
        for(;index<len-1;index++){
            if(num.charAt(index)>num.charAt(index+1))
                break;
        }
        num=num.substring(0,index)+num.substring(index+1);
        len--;
        System.out.println("i= "+index+" num= "+num);
        for(int i=1;i<k;i++){
            if(index==len){
                if(len-k+i==0)
                    return "0";
                else
                    return num.substring(0,len-k+i);
            }
            else if(index>0&&num.charAt(index-1)>num.charAt(index)){
                num=num.substring(0,index-1)+num.substring(index);
                index--;
                }
            else{
                while(index<len-1&&num.charAt(index+1)>=num.charAt(index))
                    index++;
                num=num.substring(0,index)+num.substring(index+1);
            }
            System.out.println("index= "+index+" num= "+num);
            len--;
        }
        for(index=0;index<len;index++)
            if(num.charAt(index)!='0')
                break;

        if(index==len-1)
            return "0";
        else
            return num.substring(index);
    }

     */

    /*
    static public String removeKdigits(String num, int k) {
        String tmp=removeKnums(num,k);
        //System.out.println(tmp);
        StringBuilder res=new StringBuilder(tmp);
        int len=tmp.length();
        int index=0;
        for(;index<len;index++){
            if(tmp.charAt(index)!='0')
                break;
        }
        if(index==len)
            return "0";
        else
            return res.substring(index);
    }
    static public String removeKnums(String num,int k){
        if(k==0)
            return num;
        int len=num.length();
        int index=0;
        for(;index<len-1;index++){
            if(num.charAt(index)>num.charAt(index+1))
                break;
        }
        return removeKnums(num.substring(0,index)+num.substring(index+1),k-1);
    }

     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i : arr2)
            map.put(arr2[i], i);
        int len = arr1.length;
        Integer[] arr = new Integer[len];
        for (int i = 0; i < len; i++)
            arr[i] = arr1[i];
        Arrays.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                boolean a = map.containsKey(o1);
                boolean b = map.containsKey(o2);
                if (a && b)
                    return map.get(o1) - map.get(o2);
                else if (a || b)
                    return a ? -1 : 1;
                else
                    return o1 - o2;
            }
        });
        for (int i = 0; i < len; i++)
            arr1[i] = arr[i];
        return arr1;
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null)
            return null;
        ListNode evenNode = head;
        ListNode oddNode = head.next;
        ListNode tmpNode = oddNode;
        while (oddNode.next != null && oddNode.next.next != null) {
            evenNode.next = oddNode.next;
            oddNode.next = oddNode.next.next;
            evenNode = evenNode.next;
            oddNode = oddNode.next;
        }
        if (oddNode.next == null)
            evenNode.next = tmpNode;
        else {
            evenNode.next = oddNode.next;
            evenNode.next.next = tmpNode;
        }
        return head;
    }

    static public int[] sortArrayByParityII(int[] A) {
        int odd = 1;
        int even = 0;
        int len = A.length;
        while (odd < len && even < len) {
            if (A[even] % 2 == 1 && A[odd] % 2 == 0) {
                int tmp = A[even];
                A[even] = A[odd];
                A[odd] = tmp;
                even += 2;
                odd += 2;
            } else if (A[even] % 2 == 1)
                odd += 2;
            else if (A[odd] % 2 == 0)
                even += 2;
            else {
                odd += 2;
                even += 2;
            }
        }
        return A;
    }

    static public int findRotateSteps(String ring, String key) {
        List<Integer>[] pos = new List[26];
        for (int i = 0; i < 26; ++i) {
            pos[i] = new ArrayList<Integer>();
        }
        int length1 = ring.length();
        int length2 = key.length();
        for (int i = 0; i < length1; i++) {
            pos[ring.charAt(i) - 'a'].add(i);
        }
        int[] dp = new int[length1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i : pos[key.charAt(0) - 'a'])
            dp[i] = Math.min(i, length1 - i) + 1;
        for (int i = 1; i < length2; i++) {
            int[] tmp = new int[length1];
            Arrays.fill(tmp, Integer.MAX_VALUE);
            for (int j : pos[key.charAt(i) - 'a']) {
                for (int k : pos[key.charAt(i - 1) - 'a']) {
                    tmp[j] = Math.min(dp[k] + Math.min(Math.abs(j - k), length1 - Math.abs(j - k)) + 1, tmp[j]);
                }
            }
            System.arraycopy(tmp, 0, dp, 0, length1);

        }
        return Arrays.stream(dp).min().getAsInt();

    }

    static public void nextPermutation(int[] nums) {
        int len = nums.length;
        if (len == 1)
            return;
        int index = len - 1;
        while (index >= 1 && nums[index - 1] > nums[index])
            index--;

        for (int i = index; i < index + (len - index) / 2; i++) {
            swap(nums, i, len - 1 + index - i);
        }
        System.out.println(Arrays.toString(nums));
        if (index > 0) {
            for (int i = index; i < len; i++)
                if (nums[i] > nums[index - 1]) {
                    swap(nums, index - 1, i);
                    break;
                }

        }

    }

    static public void swap(int[] a, int l, int r) {
        int tmp = a[l];
        a[l] = a[r];
        a[r] = tmp;
    }

    Random rand = new Random();

    public void randomSelect(int[][] a, int left, int right, int K) {
        int pivot = left + rand.nextInt(right - left + 1);
        int pivotDist = a[pivot][0] * a[pivot][0] + a[pivot][1] * a[pivot][1];
        swap(a, pivot, right);
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (a[j][0] * a[j][0] + a[j][1] * a[j][1] < pivotDist) {
                i++;
                swap(a, i, j);
            }
        }
        i++;
        swap(a, i, right);
        if (K < i - left + 1)
            randomSelect(a, left, i - 1, K);
        else if (K > i - left + 1)
            randomSelect(a, i + 1, right, K - (i - left + 1));

    }

    private void swap(int[][] a, int l, int r) {
        int[] tmp = a[l];
        a[l] = a[r];
        a[r] = tmp;
    }

    public int countRangeSum(int[] nums, int lower, int upper) {
        long[] preSum = new long[nums.length + 1];
        int res = 0;
        preSum[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        Set<Long> allNums = new TreeSet<>();
        for (long aPreSum : preSum) {
            allNums.add(aPreSum);
            allNums.add(aPreSum - upper);
            allNums.add(aPreSum - lower);
        }
        Map<Long, Integer> map = new HashMap<>();

        int index = 0;
        for (long x : allNums)
            map.put(x, index++);
        segmentNode root = buildSegmentTree(0, map.size() - 1);
        for (long x : preSum) {
            int left = map.get(x - upper);
            int right = map.get(x - lower);
            res += countSegmentNode(root, left, right);
            addSegmentNode(root, map.get(x));
        }
        return res;
    }

    /*
    public int countRangeSum(int[] nums, int lower, int upper) {
        if(nums.length==0)
            return 0;
        long[] preSum=new long[nums.length+1];
        preSum[0]=0;
        for(int i=1;i<nums.length+1;i++){
            preSum[i]=preSum[i-1]+nums[i-1];
        }

        return countLocalSum(preSum,0,preSum.length-1,lower,upper);
    }
    private int countLocalSum(long[] preSum,int left,int right,int lower,int upper){
        if(left==right)
            return 0;
        int middle=(left+right)/2;
        int localSum=0;
        int leftLocalSum=countLocalSum(preSum,left,middle,lower,upper);
        int rightLocalSum=countLocalSum(preSum,middle+1,right,lower,upper);
        int l=middle+1;
        int r=middle+1;

        for(int tmp=left;tmp<=middle;tmp++){
            while(l<=right&&preSum[l]<lower+preSum[tmp])
                l++;
            r=l;
            while(r<=right&&preSum[r]<=upper+preSum[tmp])
                r++;
            localSum+=r-l;
        }
        //归并排序
        l=left;r=middle+1;
        int[] tmpArray=new int[right-left+1];
        int index=0;
        while(l<=middle&&r<=right){
            if(preSum[l]<=preSum[r]){
                tmpArray[index++]=(int)preSum[l++];
            }
            else{
                tmpArray[index++]=(int)preSum[r++];
            }
        }
        while(l<=middle){
            tmpArray[index++]=(int)preSum[l++];
        }
        while(r<=right){
            tmpArray[index++]=(int)preSum[r++];
        }
        for(int i=0;i<tmpArray.length;i++)
            preSum[i+left]=tmpArray[i];
        return localSum+leftLocalSum+rightLocalSum;
    }

     */
    public int[] sortByBits(int[] arr) {
        Integer[] arr1 = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = (Integer) arr[i];
        }
        Arrays.sort(arr1, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (int2binarySum(o1) - int2binarySum(o2) > 0)
                    return 1;
                else if (int2binarySum(o1) - int2binarySum(o2) < 0)
                    return -1;
                else
                    return (int) o1 - (int) o2;
            }
        });
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = (int) arr1[i];
        }
        return res;
    }

    private int int2binarySum(int n) {
        int res = 0;
        while (n > 0) {
            res += n % 2;
            n /= 2;
        }
        return res;
    }

    Map<String, Integer> wordId = new HashMap<>();
    List<List<Integer>> edge = new ArrayList<>();
    int nodeNum = 0;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        for (String word : wordList)
            addEdge(word);
        addEdge(beginWord);
        if (!wordId.containsKey(endWord))
            return 0;
        int beginId = wordId.get(beginWord);
        int endId = wordId.get(endWord);
        int[] beginDis = new int[nodeNum];
        Arrays.fill(beginDis, Integer.MAX_VALUE);
        beginDis[beginId] = 0;
        Queue<Integer> beginQue = new LinkedList<>();
        beginQue.offer(beginId);

        int[] endDis = new int[nodeNum];
        Arrays.fill(endDis, Integer.MAX_VALUE);
        endDis[endId] = 0;
        Queue<Integer> endQue = new LinkedList<>();
        endQue.offer(endId);

        while (!beginQue.isEmpty() && !endQue.isEmpty()) {
            int beginLen = beginQue.size();
            for (int i = 0; i < beginLen; i++) {
                int index = beginQue.poll();
                if (endDis[index] != Integer.MAX_VALUE)
                    return (beginDis[index] + endDis[index]) / 2 + 1;
                for (int j : edge.get(index)) {
                    if (beginDis[j] == Integer.MAX_VALUE) {
                        beginDis[j] = beginDis[index] + 1;
                        beginQue.offer(j);
                    }
                }
            }
            int endLen = endQue.size();
            for (int i = 0; i < endLen; i++) {
                int index = endQue.poll();
                if (beginDis[index] != Integer.MAX_VALUE)
                    return (beginDis[index] + endDis[index]) / 2 + 1;
                for (int j : edge.get(index)) {
                    if (endDis[j] == Integer.MAX_VALUE) {
                        endDis[j] = endDis[index] + 1;
                        endQue.offer(j);
                    }
                }
            }
        }
        return 0;


    }

    private void addEdge(String word) {
        if (!wordId.containsKey(word))
            addWord(word);
        int id1 = wordId.get(word);
        char[] charArray = word.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char tmp = charArray[i];
            charArray[i] = '*';
            String newWord = Arrays.toString(charArray);
            if (!wordId.containsKey(newWord))
                addWord(newWord);
            int id2 = wordId.get(newWord);
            edge.get(id1).add(id2);
            edge.get(id2).add(id1);
        }
    }

    private void addWord(String s) {
        if (!wordId.containsKey(s)) {
            wordId.put(s, nodeNum++);
            edge.add(new ArrayList<>());
        }
    }

    public int[][] insert(int[][] intervals, int[] newInterval) {
        int index = 0;
        boolean merge = false;
        List<int[]> res = new ArrayList<>();
        while (index < intervals.length) {
            if (newInterval[1] < intervals[index][0]) {
                res.add(newInterval);
                break;
            } else if (intervals[index][1] >= newInterval[0]) {
                merge = true;
                newInterval = new int[]{Math.min(newInterval[0], intervals[index][0]),
                        Math.max(newInterval[1], intervals[index][1])};
            } else
                res.add(intervals[index]);
            index++;
        }
        if (!merge)
            res.add(newInterval);
        while (index < intervals.length) {
            res.add(intervals[index]);
            index++;
        }
        int len = res.size();
        int[][] a = new int[len][2];
        index = 0;
        for (int[] array : res) {
            a[index++] = array;
        }
        return a;

    }

    public List<String> wordBreak1(String s, List<String> wordDict) {
        Map<Integer, List<List<String>>> map = new HashMap<Integer, List<List<String>>>();
        List<List<String>> wordBreaks = backtrack(s, s.length(), new HashSet<String>(wordDict), 0, map);
        List<String> breakList = new LinkedList<String>();
        for (List<String> wordBreak : wordBreaks) {
            breakList.add(String.join(" ", wordBreak));
        }
        return breakList;
    }

    public List<List<String>> backtrack(String s, int length, Set<String> wordSet,
                                        int index, Map<Integer, List<List<String>>> map) {
        if (!map.containsKey(index)) {
            List<List<String>> wordBreaks = new LinkedList<List<String>>();
            if (index == length) {
                wordBreaks.add(new LinkedList<String>());
            }
            for (int i = index + 1; i <= length; i++) {
                String word = s.substring(index, i);
                if (wordSet.contains(word)) {
                    List<List<String>> nextWordBreaks = backtrack(s, length, wordSet, i, map);
                    for (List<String> nextWordBreak : nextWordBreaks) {
                        LinkedList<String> wordBreak = new LinkedList<String>(nextWordBreak);
                        wordBreak.offerFirst(word);
                        wordBreaks.add(wordBreak);
                    }
                }
            }
            map.put(index, wordBreaks);
        }
        return map.get(index);
    }


    public boolean validMountainArray(int[] A) {
        int length = A.length;
        int index = 0;
        while (index + 1 < length && A[index + 1] > A[index])
            index++;
        if (index + 1 == length || A[index + 1] == A[index])
            return false;

        while (index + 1 < length && A[index + 1] < A[index])
            index++;
        return index == length - 1;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int begin1 = 0;
        int begin2 = 0;
        List<Integer> res = new ArrayList<>();
        while (begin1 < nums1.length && begin2 < nums2.length) {
            if (nums1[begin1] < nums2[begin2])
                begin1++;
            else if (nums1[begin1] > nums2[begin2])
                begin2++;
            else {
                begin1++;
                begin2++;
                res.add(nums1[begin1]);
            }
        }
        int size = res.size();
        int[] a = new int[size];
        int index = 0;
        for (int i : res)
            a[index++] = i;
        return a;

    }

    static public boolean wordBreak(String s, List<String> wordDict) {
        int begin = s.length();

        int end;
        boolean[] res = new boolean[s.length()];
        Arrays.fill(res, false);
        for (String word : wordDict) {
            if (word.length() <= s.length() && s.substring(0, word.length()).equals(word)) {
                res[word.length() - 1] = true;
                begin = Math.min(begin, word.length());
            }
        }
        System.out.println(begin);
        while (begin < s.length()) {
            System.out.println(begin);
            for (String word : wordDict) {
                if (begin + word.length() <= s.length() && s.substring(begin, begin + word.length()).equals(word)) {
                    end = begin + word.length() - 1;
                    System.out.println("begin: " + begin + " end: " + end);
                    if (end == s.length() - 1) {
                        System.out.println(Arrays.toString(res));
                        res[end] = true;
                        return true;
                    }

                }
            }
            int nextBegin = s.length();

            for (int i = begin + 1; i < s.length(); i++)
                if (res[i])
                    nextBegin = i;
            if (nextBegin == s.length())
                break;
            else begin = nextBegin;
        }
        System.out.println(Arrays.toString(res));
        return res[s.length() - 1];
    }

    public boolean uniqueOccurrences(int[] arr) {

        Map<Integer, Integer> counter = new HashMap<>();
        for (int elem : arr)
            counter.put(elem, counter.getOrDefault(elem, 0) + 1);

        return counter.size() == new HashSet<Integer>(counter.values()).size();
    }

    static public void changeChar(char[] src) {
        src[0] = '1';
    }

    static public String decimalToNBCD(String decimal) {
        //TODO:
        StringBuilder strBuilder = new StringBuilder();
        int length = decimal.length();
        String[] resString = new String[length];
        for (int i = 0; i < length; i++) {
            resString[i] = decimal.substring(i, i + 1);
        }
        int begin = 0;
        int end = length - 1;
        if (resString[0].equals("-"))
            begin++;
        for (int i = begin; i <= end; i++) {
            switch (resString[i]) {
                case "0":
                    strBuilder.append("0000");
                    break;
                case "1":
                    strBuilder.append("0001");
                    break;
                case "2":
                    strBuilder.append("0010");
                    break;
                case "3":
                    strBuilder.append("0011");
                    break;
                case "4":
                    strBuilder.append("0100");
                    break;
                case "5":
                    strBuilder.append("0101");
                    break;
                case "6":
                    strBuilder.append("0110");
                    break;
                case "7":
                    strBuilder.append("0111");
                    break;
                case "8":
                    strBuilder.append("1000");
                    break;
                case "9":
                    strBuilder.append("1001");
                    break;
            }

        }
        return strBuilder.toString();
    }

    static public String floatToBinary(String floatStr) {
        //TODO:
        String[] resString = new String[32];
        double num;
        double thePower = 0.5;
        if (floatStr.substring(0, 1).equals("-")) {
            resString[0] = "1";
            num = Double.parseDouble(floatStr.substring(1));
        } else {
            num = Double.parseDouble(floatStr);
            resString[0] = "0";
        }
        int exp = 0;
        if (num < 1) {
            while (num < 1 && exp > -126) {
                num *= 2;
                exp--;
            }
        }
        if (num > 1) {
            while (num / 2 > 1 && exp < 127) {
                num /= 2;
                exp++;
            }

        }
        if (exp == 127 && num / 2 > 1) {
            if (resString[0].equals("1"))
                return "+Inf";
            else
                return "-Inf";
        } else if (exp == -126 && num < 1) {
            for (int i = 1; i <= 8; i++)
                resString[i] = "0";
            for (int i = 1; i <= 23; i++) {
                if (num >= thePower)
                    resString[8 + i] = "1";
                else
                    resString[8 + i] = "0";
                num -= thePower;
                thePower /= 2;
            }
        } else if (exp >= -126 && exp <= 127) {
            exp = exp + 127;
            for (int i = 8; i >= 1; i--) {
                int myReminder = exp % 2;
                if (myReminder == 1)
                    resString[i] = "1";
                else
                    resString[i] = "0";
                exp /= 2;
            }
            num -= 1;
            for (int i = 1; i <= 23; i++) {
                if (num >= thePower)
                    resString[8 + i] = "1";
                else
                    resString[8 + i] = "0";
                num -= thePower;
                thePower /= 2;
            }
        }
        StringBuilder res = new StringBuilder();
        for (int j = 0; j < 32; j++)
            res.append(resString[j]);
        return res.toString();
    }

    static public String binaryToInt(String binStr) {
        int length = binStr.length();
        int tmpPower = 1;
        int res = 0;
        for (int i = length - 1; i > 0; i--) {
            if (binStr.substring(i, i + 1).equals("1"))
                res += tmpPower;
            tmpPower *= 2;
        }
        if (binStr.substring(0, 1).equals("1"))
            res -= tmpPower;
        return res + "";
    }

    static public String intToBinary(String numStr) {
        //TODO:
        int num;
        if (numStr.substring(0, 1).equals("-"))
            num = Integer.parseInt(numStr.substring(1));
        else
            num = Integer.parseInt(numStr);
        String[] resString = new String[32];
        int i = 31;
        while (num > 0 && i >= 0) {
            int remainder = num % 2;
            num /= 2;
            if (remainder == 1)
                resString[i] = "1";
            else
                resString[i] = "0";
            i--;
        }
        while (i >= 0) {
            resString[i] = "0";
            i--;
        }
        if (numStr.substring(0, 1).equals("-")) {
            int index = 31;
            while (resString[index].equals("0"))
                index--;
            index--;
            for (; index >= 0; index--) {
                if (resString[index].equals("1"))
                    resString[index] = "0";
                else
                    resString[index] = "1";
            }
        }
        StringBuilder res = new StringBuilder();
        for (int j = 0; j < 32; j++)
            res.append(resString[j]);


        return res.toString();
    }

    public static void allPairs(int[][] a, int[][] d, int[][] path) {
        int n = a.length;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                d[i][j] = a[i][j];
                path[i][j] = -1;
            }
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    if (d[i][k] + d[k][j] < d[i][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                        path[i][j] = k;
                    }
                }
    }

    public static void optMatrix(int[] c, long[][] m, int[][] lastChange) {
        int n = c.length - 1;
        for (int k = 1; k < n; k++)
            for (int left = 1; left <= n - k; left++) {
                int right = left + k;
                m[left][right] = Long.MAX_VALUE;
                for (int i = left; i < right; i++) {
                    long thisCost = m[left][i] + m[i + 1][right] + c[left - 1] * c[i] * c[right];
                    if (thisCost < m[left][right]) {
                        m[left][right] = thisCost;
                        lastChange[left][right] = i;
                    }
                }
            }

    }

    public static void optMatrix2(String[] str, double[] frequent, double[][] cost, int[][] lastChange) {
        int n = str.length - 1;
        for (int k = 0; k < n; k++) {
            for (int left = 1; left <= n - k; left++) {
                int right = left + k;
                cost[left][right] = Double.POSITIVE_INFINITY;
                double thisSumFrequent = 0;
                for (int j = left; j <= right; j++) {
                    thisSumFrequent += frequent[j];
                }
                for (int i = left; i <= right; i++) {
                    double thisCost = cost[left][i - 1] + cost[i + 1][right] + thisSumFrequent;
                    if (thisCost < cost[left][right]) {
                        cost[left][right] = thisCost;
                        lastChange[left][right] = i;
                    }
                }
            }
        }
    }

    public static void printMatrix(int left, int right, String[] str, int[][] lastChange) {
        if (left == right)
            return;
        int median = lastChange[left][right];
        if (median == left) {
            System.out.println(str[median] + "-->null(left)");
            System.out.println(str[median] + "-->" + str[lastChange[median + 1][right]] + "(right)");
            printMatrix(median + 1, right, str, lastChange);
            return;
        }
        if (median == right) {
            System.out.println(str[median] + "-->null(right)");
            System.out.println(str[median] + "-->" + str[lastChange[left][median - 1]] + "(left)");
            printMatrix(left, median - 1, str, lastChange);
            return;
        }

        System.out.println(str[median] + "-->" + str[lastChange[left][median - 1]] + "(left)");
        System.out.println(str[median] + "-->" + str[lastChange[median + 1][right]] + "(right)");
        printMatrix(left, median - 1, str, lastChange);
        printMatrix(median + 1, right, str, lastChange);
    }

    /*
    var sort=new MySort<Integer>();
    var random=new Random();
    var a=new Integer[10];
    for(int i=0;i<10;i++)
        a[i]=random.nextInt(100);
    sort.quickSort(a);
    for(int i=0;i<10;i++){
        System.out.println(a[i]);
    }
    sort.quickSelect(a,3);
    System.out.println("third: "+a[2]);

     */
    public static List<String> findChain(Map<String, List<String>> adjacentWords, String first, String second) {
        Map<String, String> previousWord = new HashMap<String, String>();
        previousWord.put(first, null);
        LinkedList<String> q = new LinkedList<>();
        q.addLast(first);
        while (!q.isEmpty()) {
            String current = q.removeFirst();
            List<String> adj = adjacentWords.get(current);
            if (adj != null)
                for (String adjWord : adj)
                    if (previousWord.get(adjWord) == null) {
                        previousWord.put(adjWord, current);
                        q.addLast(adjWord);
                    }
        }
        return getChainFromPreviousMap(previousWord, first, second);
    }

    public static List<String> getChainFromPreviousMap(Map<String, String> prev,
                                                       String first, String second) {
        LinkedList<String> result = null;
        if (prev.get(second) != null) {
            result = new LinkedList<>();
            for (String str = second; str != null; str = prev.get(str))
                result.addFirst(str);
        }
        return result;
    }

    /*
           var heap=new MinMaxHeap<Integer>(20);
           var random=new Random();
           for(int i=0;i<12;i++)
               heap.insert(random.nextInt(100));

           heap.printHeap();
           heap.deleteMin();
           System.out.println("After:");
           heap.printHeap();

    */
    /*
 var heap1=new LeftistHeap<Integer>();
 var heap2=new LeftistHeap<Integer>();
 for(int i=0;i<5;i++){
     heap1.insert(i);
     heap2.insert(i+5);
 }
 heap2.merge(heap1);
 while(!heap2.isEmpty()){
     System.out.println(heap2.deleteMin());
 }

  */
    /*
            var r=new Random();
            int temp;
            var table1=new LinearProbingHashTable<Integer>();
            var table2=new QuadraticProbingHashTable<Integer>();
            for(int i=0;i<1000;i++){
                temp=r.nextInt(1000);
                table1.insert(temp);
                table2.insert(temp);
            }
            System.out.println("Linear:"+table1.getCollisionTime());
            System.out.println("Quadratic:"+table2.getCollisionTime());

     */
    private static int nextPrime(int n) {
        if (n % 2 == 0)
            n++;
        for (; ; n += 2) {
            if (isPrime(n))
                break;
        }
        return n;
    }

    public static boolean isPrime(int n) {

        int tempInt = (int) Math.sqrt(n);

        for (int i = 2; i <= tempInt; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
    /*
    public static void printHighChangeables(Map<String,List<String>> adjWords,
                                            int minWords){
        for(Map.Entry<String,List<String>> entry:adjWords.entrySet()){
            List<String> words=entry.getValue();
            if(words.size()>=minWords){
                System.out.print(entry.getKey()+"(");
                System.out.print(words.size()+"):");
                for(String w:words)
                    System.out.print(" "+w);
                System.out.println();
            }
        }
    }
    public static boolean oneCharOff(String word1,String word2){
        if(word1.length()!=word2.length())
            return false;
        int diffs=0;
        for(int i=0;i<word1.length();i++){
            if(word1.charAt(i)!=word2.charAt(i))
                if(++diffs>1)
                    return false;
        }
        return diffs==1;
    }
    private static <KeyType> void update (Map<KeyType,List<String>> m,
                                          KeyType key,String value){
        List<String> lst = m.computeIfAbsent(key, k -> new ArrayList<>());
        lst.add(value);
    }

    public static Map<String,List<String>>
    computeAdjacentWords(List<String> theWords){
        Map<String,List<String>> adjWords=new TreeMap<>();
        Map<Integer,List<String>> wordsByLength=new TreeMap<>();
        for(String w:theWords)
            update(wordsByLength,w.length(),w);
        for(Map.Entry<Integer,List<String>> entry:wordsByLength.entrySet()){
            List<String> groupsWords=entry.getValue();
            int groupNum=entry.getKey();

            for(int i=0;i<groupNum;i++){
                Map<String,List<String>> repToWord=new TreeMap<>();
                for(String str:groupsWords){
                    String rep=str.substring(0,i)+str.substring(i+1);
                    update(repToWord,rep,str);
                }
                for(List<String> wordClique:repToWord.values())
                    if(wordClique.size()>=2)
                        for(String s1:wordClique)
                            for(String s2:wordClique)
                                if(!s1.equals(s2))
                                    update(adjWords,s1,s2);
            }
        }
        return adjWords;
    }

     */
    /*
    public static Map<String,List<String>>
    computeAdjacentWords(List<String> theWords){
        Map<String,List<String>> adjWords=new TreeMap<>();
        Map<Integer,List<String>> wordsByLength=new TreeMap<>();
        for(String w:theWords)
            update(wordsByLength,w.length(),w);
        for(List<String> groupsWords:wordsByLength.values()){
            String[] words=new String[groupsWords.size()];
            groupsWords.toArray(words);
            for(int i=0;i<words.length;i++)
                for(int j=i+1;j<words.length;j++)
                    if(oneCharOff(words[i],words[j])){
                        update(adjWords,words[i],words[j]);
                        update(adjWords,words[j],words[i]);
                    }
        }
        return adjWords;

    }

     */

    /*
    public static Map<String,List<String>>
    computeAdjacentWords(List<String> theWords){
        Map<String,List<String>> adjWords=new TreeMap<>();
        String [] words=new String[theWords.size()];
        theWords.toArray(words);
        for(int i=0;i<words.length;i++)
            for(int j=i+1;j<words.length;j++)
                if(oneCharOff(words[i],words[j])){
                    update(adjWords,words[i],words[j]);
                    update(adjWords,words[j],words[i]);
                }
        return adjWords;
    }
    */


    /*
    String str="a+b*c+(d*e+f)*g";
    middleToRear(str);

     */
    public static MyTree<Character> rearToTree(String str) {
        char[] chars = str.toCharArray();
        var stackTree = new MyLinkedListStack<MyTree<Character>>();
        for (char aChar : chars) {
            if (aChar >= 'a' && aChar <= 'z')
                stackTree.push(new MyTree<Character>(aChar));
            if (aChar == '+' || aChar == '-' || aChar == '*' || aChar == '/') {
                var tempTree1 = stackTree.pop();
                var tempTree2 = stackTree.pop();
                stackTree.push(new MyTree<Character>(aChar, tempTree2, tempTree1));
            }
        }
        return stackTree.pop();
    }

    public static void middleToRear(String str) {
        char[] chars = str.toCharArray();
        var stack = new MyLinkedListStack<Character>();
        for (char aChar : chars) {
            if (aChar >= 'a' && aChar <= 'z')
                System.out.print(aChar);
            switch (aChar) {
                case '(':
                    stack.push(aChar);
                    break;
                case ')':
                    while (!stack.isEmpty() && !stack.getTop().equals('('))
                        System.out.print(stack.pop());
                    stack.pop();

                    break;
                case '+':
                    while (!stack.isEmpty() && !(stack.getTop().equals('(')))
                        System.out.print(stack.pop());
                    stack.push('+');
                    break;

                case '-':
                    while (!stack.isEmpty() && !(stack.getTop().equals('(')))
                        System.out.print(stack.pop());
                    stack.push(('-'));
                    break;
                case '*':
                    while (!stack.isEmpty() &&
                            (stack.getTop().equals('/') || stack.getTop().equals('*')
                                    || stack.getTop().equals('^')))
                        System.out.print(stack.pop());
                    stack.push('*');
                    break;
                case '/':
                    while (!stack.isEmpty() &&
                            (stack.getTop().equals('/') || stack.getTop().equals('*')
                                    || stack.getTop().equals('^')))
                        System.out.print(stack.pop());
                    stack.push('/');
                    break;
                case '^':
                    stack.push('^');
                    break;


            }

        }
        while (stack.getTop() != null) System.out.print(stack.pop());
    }

    public static boolean checkBalance(String str) {
        char[] charArray = str.toCharArray();
        var stack = new MyArrayStack<Character>();
        try {
            for (char symbol : charArray) {
                if (((Character) brother(symbol)).equals(stack.getTop())) stack.pop();
                else stack.push(symbol);
            }
            return stack.getTopOfStack() == -1;
        } catch (Exception e) {
            return false;
        }
    }

    public static char brother(char symbol) throws Exception {
        if (symbol == '/') return '/';
        if (symbol == '*') return '*';
        if (symbol == '(') return ')';
        if (symbol == ')') return '(';
        if (symbol == '[') return ']';
        if (symbol == ']') return '[';
        if (symbol == '{') return '}';
        if (symbol == '}') return '{';
        throw new Exception();
    }


    /*
    var stack=new MyArrayStack<String>();
    stack.push("hello");
    stack.push("world");
    String str=stack.pop();
    System.out.println(str);
    String str1=stack.pop();System.out.println(str1);
    String str2=stack.pop();

     */
    /*
  var list=new MyArrayList<Integer>();
  for(int i=0;i<10;i++)
      list.add(i);

  var iterator1=list.iterator();
  var iterator2=list.iterator();
  System.out.println(iterator1.next());
  System.out.println(iterator1.next());
  System.out.println(iterator1.next());
  System.out.println(iterator1.previous());
  iterator1.remove();
  System.out.println(list);
  iterator1.set(99);
  System.out.println(list);
  iterator2.next();

   */
    public static int Josephus(int M, int N) {
        LinkedList<Integer> survive = new LinkedList<Integer>();
        int initialNumber = 0;
        int killedNumber;
        int survivedNumber = N;
        for (int i = 0; i < N; i++)
            survive.add(i + 1);
        while (survivedNumber > 1) {
            killedNumber = (initialNumber + M) % survivedNumber;
            System.out.println("this time I'll kill number: " + survive.get(killedNumber));
            survive.remove(killedNumber);
            survivedNumber--;
            initialNumber = killedNumber % survivedNumber;
        }
        System.out.println("the final winner is: " + survive.get(0));
        return survive.get(0);
    }

    /*
    var list1=new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,9));
    var list2=new ArrayList<Integer>(Arrays.asList(2,3,5,6,7,9,10));
    var list3=new ArrayList<Integer>();
    union(list1,list2,list3);
    System.out.println(list3);

     */
    public static <AnyType extends Comparable<? super AnyType>>
    void intersection(List<AnyType> L1, List<AnyType> L2, List<AnyType> intersect) {
        ListIterator<AnyType> iteratorL1 = L1.listIterator();
        ListIterator<AnyType> iteratorL2 = L2.listIterator();
        AnyType L1element = null, L2element = null;
        if (iteratorL1.hasNext() && iteratorL2.hasNext()) {
            L1element = iteratorL1.next();
            L2element = iteratorL2.next();
        }

        while (L1element != null && L2element != null) {
            if (L1element.compareTo(L2element) == 0) {
                intersect.add(L1element);
                L1element = iteratorL1.hasNext() ? iteratorL1.next() : null;
                L2element = iteratorL2.hasNext() ? iteratorL2.next() : null;
            } else if (L1element.compareTo(L2element) > 0)
                L2element = iteratorL2.hasNext() ? iteratorL2.next() : null;
            else
                L1element = iteratorL1.hasNext() ? iteratorL1.next() : null;
        }
    }

    public static <AnyType extends Comparable<? super AnyType>>
    void union(List<AnyType> L1, List<AnyType> L2, List<AnyType> result) {
        ListIterator<AnyType> iteratorL1 = L1.listIterator();
        ListIterator<AnyType> iteratorL2 = L2.listIterator();
        AnyType itemL1 = iteratorL1.hasNext() ? iteratorL1.next() : null;
        AnyType itemL2 = iteratorL2.hasNext() ? iteratorL2.next() : null;
        while (itemL1 != null && itemL2 != null) {
            int compareResult = itemL1.compareTo(itemL2);
            if (compareResult == 0) {
                result.add(itemL1);
                itemL1 = iteratorL1.hasNext() ? iteratorL1.next() : null;
                itemL2 = iteratorL2.hasNext() ? iteratorL2.next() : null;
            } else if (compareResult > 0) {
                result.add(itemL2);
                itemL2 = iteratorL2.hasNext() ? iteratorL2.next() : null;
            } else {
                result.add(itemL1);
                itemL1 = iteratorL1.hasNext() ? iteratorL1.next() : null;
            }
        }
        if (itemL1 != null) result.add(itemL1);
        if (itemL2 != null) result.add(itemL2);
        while (iteratorL1.hasNext())
            result.add(iteratorL1.next());
        while (iteratorL2.hasNext())
            result.add(iteratorL2.next());

    }

    /*
    var P=new ArrayList<Integer>(Arrays.asList(1,3));
    var L=new ArrayList<String>(Arrays.asList("hello","world","new","happy"));
    printLots(L,P);

     */
    public static Object getObj() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("ab");
        return list;
    }

    public static <AnyType> void printLots(List<AnyType> L, List<Integer> P) {
        var iteratorL = L.iterator();
        var iteratorP = P.iterator();
        Integer itemP;
        AnyType itemL = null;
        int record = 0;
        while (iteratorL.hasNext() && iteratorP.hasNext()) {
            itemP = iteratorP.next();
            while (iteratorL.hasNext() && record < itemP) {
                record++;
                itemL = iteratorL.next();
            }
            if (record == itemP) System.out.print(itemL + " ");
        }
        System.out.println();
    }

    public static int maxSubSum1(int[] a) {
        int maxSum = a[0];
        int thisSum;
        for (int i = 0; i < a.length; i++) {
            thisSum = 0;
            for (int j = i; j < a.length; j++) {
                thisSum += a[j];
                if (thisSum > maxSum)
                    maxSum = thisSum;
            }
        }
        return maxSum;
    }

    public static int maxSumRec(int[] a, int left, int right) {
        if (left == right)
            return Math.max(a[left], 0);
        int center = (left + right) / 2;
        int maxLeftSum = maxSumRec(a, left, center);
        int maxRightSum = maxSumRec(a, center + 1, right);
        int leftSum = a[center];
        int rightSum = a[center + 1];
        int currentLeftSum = 0;
        int currentRightSum = 0;
        for (int i = center; i >= left; i--) {
            currentLeftSum += a[i];
            if (leftSum < currentLeftSum)
                leftSum = currentLeftSum;
        }
        for (int i = center + 1; i <= right; i++) {
            currentRightSum += a[i];
            if (rightSum < currentRightSum)
                rightSum = currentRightSum;
        }
        int middleSum = leftSum + rightSum;
        return Math.max(Math.max(maxLeftSum, maxRightSum), middleSum);
    }

    public static int maxSubSum4(int[] a) {
        int maxSum = 0;
        int thisSum = 0;
        for (int i : a) {
            thisSum += i;
            if (thisSum > maxSum)
                maxSum = thisSum;
            else if (thisSum < 0)
                thisSum = 0;
        }
        return maxSum;
    }


                /*
        int n=2000000;
        long start=System.currentTimeMillis();
        var bitSet=new BitSet(n+1);
        int count=0;
        int i;
        for(i=2;i<n;i++)
            bitSet.set(i);
        i=2;
        while(i*i<=n){
            if(bitSet.get(i)){
                count++;
                int k=2*i;
                while(k<=n){
                    bitSet.clear(k);
                    k+=i;
                }
            }
            i++;
        }
        while(i<=n){
            if(bitSet.get(i)) count++;
            i++;
        }
        long end=System.currentTimeMillis();
        System.out.println(count+" primes");
        System.out.println((end-start)+" milliseconds");

         */
        /*
        var staff=new LinkedList<String>();
        staff.add("Cherry");
        staff.add("Dan");
        staff.add("Ben");
        staff.add("Amy");
        System.out.println(staff);
        Collections.sort(staff);
        System.out.println(staff);
        staff.sort(Collections.reverseOrder());
        System.out.println(staff);

         */
        /*
        var numbers=new ArrayList<Integer>();
        for(int i=1;i<=49;i++) numbers.add(i);
        Collections.shuffle(numbers);
        List<Integer> winningCombination=numbers.subList(0,6);
        Collections.sort(winningCombination);
        System.out.println(winningCombination);

         */

         /*
        var staff=new LinkedHashMap<String,Employee>();
        staff.put("144-25-5464",new Employee("Amy Lee"));
        staff.put("567-24-2546",new Employee("Harry Hacker"));
        staff.put("157-62-7935",new Employee("Gary Cooper"));
        staff.put("456-62-5527",new Employee("Francesca Cruz"));
        var iter=staff.values().iterator();
        while(iter.hasNext()){
            System.out.println(iter.next());

         */
        /*
        System.out.println(staff);
        staff.remove("567-24-2546");
        staff.put("456-62-5527",new Employee("Francesca Miller"));
        System.out.println(staff.get("157-62-7935"));
        System.out.println("");
        staff.forEach((k,v)->System.out.println("key="+k+",value="+v));


        for(var entry:staff.entrySet()){
            String k=entry.getKey();
            Employee v=entry.getValue();
            System.out.println(k+" "+v);
        }
     */


    /*
    var staff=new HashMap<String,Employee>();
    var harry=new Employee("Harry Hacker",500,1900,01,02);
    staff.put("987-98-9996",harry);
    var id="987-98-9996";
    Employee e=staff.get(id);
    System.out.println(e);

     */
    /*
    var pq=new PriorityQueue<LocalDate>();
    pq.add(LocalDate.of(1906,12,9));
    pq.add(LocalDate.of(1815,12,10));
    pq.add(LocalDate.of(1903,12,3));
    pq.add(LocalDate.of(1910,6,22));
    System.out.println("Iterating over elements . . .");
    for(LocalDate date:pq)
        System.out.println(date);
    System.out.println("Removing elements...");
    while(!pq.isEmpty())
        System.out.println(pq.remove());

     */
    /*
    var parts=new TreeSet<Item>();
    parts.add(new Item("Toaster",1234));
    parts.add(new Item("Widget",4562));
    parts.add(new Item("Modem",9912));
    System.out.println(parts);
    var descIter=parts.descendingIterator();
    descIter.next();
    descIter.remove();
    System.out.println(parts);

     */
    /*
    var sorter=new TreeSet<String>();
    sorter.add("Bob");
    sorter.add("Amy");
    sorter.add("Carl");
    for(String s:sorter)
        System.out.println(s);

     */
    /*
    var words=new HashSet<String>();
    long totalTime=0;
    try(var in=new Scanner(System.in)){
        while(in.hasNext()){
            String word=in.next();
            long callTime=System.currentTimeMillis();
            words.add(word);
            callTime=System.currentTimeMillis()-callTime;
            totalTime+=callTime;
        }
    }
    Iterator<String> iter=words.iterator();
    for(int i=1;i<=20&&iter.hasNext();i++)
        System.out.println(iter.next());
    System.out.println("...");
    System.out.println(words.size()+" distinct words. "+totalTime+" milliseconds.");

     */
    /*
    var a=new LinkedList<String>();
    a.add("Amy"); a.add("Carl");a.add("Erica");
    var b=new LinkedList<String>();
    b.add("Bob");b.add("Doug");b.add("Frances");b.add("Gloria");
    ListIterator<String> aIter=a.listIterator();
    Iterator<String> bIter=b.listIterator();
    while(bIter.hasNext()){
        if(aIter.hasNext()) aIter.next();
        aIter.add(bIter.next());
    }
    System.out.println(a);
    bIter=b.iterator();
    while(bIter.hasNext()){
        bIter.next();
        if(bIter.hasNext()){
            bIter.next();
            bIter.remove();
        }
    }
    System.out.println(b);
    a.removeAll(b);
    System.out.println(a);

     */
    /*
var staff=new LinkedList<String>();
staff.add("Amy");
staff.add("Bob");
staff.add("Carl");
ListIterator<String> iter1=staff.listIterator();
iter1.next();
iter1.remove();

System.out.println(iter1.nextIndex());

 */
    /*
String name;

if(args.length>0) name=args[0];
else{
  try(var in=new Scanner(System.in)){
      System.out.println("Enter class name(e.g., java util.Collections): ");
      name=in.next();
  }
}


try{
  Class<?> cl=Class.forName(name);
  printClass(cl);
  for(Method m:cl.getDeclaredMethods()){
      printMethod(m);
  }
}
catch(ClassNotFoundException e){
  e.printStackTrace();
}


    public static void printClass(Class<?> cl) {
        System.out.print(cl);
        printTypes(cl.getTypeParameters(), "<", ", ", ">", true);
        Type sc = cl.getGenericSuperclass();
        if (sc != null) {
            System.out.print(" extends ");
            printType(sc, false);
        }
        printTypes(cl.getGenericInterfaces(), " implements ", ", ", "", false);
        System.out.println();
    }

    public static void printMethod(Method m) {
        String name = m.getName();
        System.out.print(Modifier.toString(m.getModifiers()));
        System.out.println(" ");
        printTypes(m.getTypeParameters(), "<", ", ", "> ", true);
        printType(m.getGenericReturnType(), false);
        System.out.print(" ");
        System.out.print(name);
        System.out.print("(");
        printTypes(m.getGenericParameterTypes(), "", ", ", "", false);
        System.out.println(")");
    }

    public static void printTypes(Type[] types, String pre, String sep, String suf,
                                  boolean isDefinition) {
        if (pre.equals(" extends ") && Arrays.equals(types, new Type[]{Object.class})) return;
        if (types.length > 0) System.out.print(pre);
        for (int i = 0; i < types.length; i++) {
            if (i > 0) System.out.print(sep);
            printType(types[i], isDefinition);
        }
        if (types.length > 0) System.out.print(suf);
    }

    public static void printType(Type type, boolean isDefinition) {
        if (type instanceof Class) {
            var t = (Class<?>) type;
            System.out.print(t.getName());
        } else if (type instanceof TypeVariable) {
            var t = (TypeVariable<?>) type;
            System.out.print(t.getName());

            if (isDefinition)
                printTypes(t.getBounds(), " extends ", " & ", "", false);


        } else if (type instanceof WildcardType) {
            var t = (WildcardType) type;
            System.out.print("?");
            printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
            printTypes(t.getLowerBounds(), " super ", " & ", "", false);

        } else if (type instanceof ParameterizedType) {
            var t = (ParameterizedType) type;
            Type owner = t.getOwnerType();
            if (owner != null) {
                printType(owner, false);
                System.out.print(".");
            }
            printType(t.getRawType(), false);
            printTypes(t.getActualTypeArguments(), "<", ", ", ">", false);

        } else if (type instanceof GenericArrayType) {
            var t = (GenericArrayType) type;
            System.out.print("");
            printType(t.getGenericComponentType(), isDefinition);
            System.out.print("[]");
        }
    }

     */


    /*
    var ceo=new Manager("Gus Greedy",800000,2003,12,15);
    var cfo=new Manager("Sid Sneaky",600000,2003,12,15);
    var buddies=new Pair<Manager>(ceo,cfo);
    printBuddies(buddies);
    ceo.setBonus(1000000);
    cfo.setBonus(500000);
    Manager[] managers={ceo,cfo};

    var result=new Pair<Employee>();
    minmaxBonus(managers,result);
    System.out.println("first: "+result.getFirst().getName()+", second: "+result.getSecond().getName());
    maxminBonus(managers,result);
    System.out.println("first: "+result.getFirst().getName()+", second: "+result.getSecond().getName());


    }
    public static void printBuddies(Pair<? extends Employee> p){
    Employee first=p.getFirst();
    Employee second=p.getSecond();
    System.out.println(first.getName()+" and "+second.getName()+" are buddies.");
     */
    /*
    public static void minmaxBonus(Manager[] a,Pair<? super Manager,? super Manager> result) {
        if (a.length == 0) return;
        Manager min = a[0];
        Manager max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.getBonus() > a[i].getBonus()) min = a[i];
            if (max.getBonus() < a[i].getBonus()) max = a[i];
        }
        result.setFirst(min);
        result.setSecond(max);
    }
    public static  void maxminBonus(Manager[] a,Pair<? super Manager,? super Manager> result){
        minmaxBonus(a,result);
        PairAlg.swap(result);
    }

     */

        /*
        Employee ceo=new Employee("one",100,1970,07,04);
        Employee cfo=new Employee("two",1000,1980,02,03);
        Manager lowlyEmployee=new Manager("three",10000,1990,03,04);
        var managerBuddies=new Pair<Employee>(ceo,cfo);
        Pair<? super Manager> wildcardBuddies=managerBuddies;
        wildcardBuddies.setFirst(lowlyEmployee);
        Object one=wildcardBuddies.getFirst();
        }
        public static void minmaxBonus(Manager[] a,Pair<? super Manager> result){
        if(a.length==0)return;
        Manager min=a[0];
        Manager max=a[0];
        for(int i=1;i<a.length;i++){
            if(min.getBonus()>a[i].getBonus()) min=a[i];
            if(max.getBonus()<a[i].getBonus()) max=a[i];
        }
        result.setFirst(min);
        result.setSecond(max);

         */

    /*
            var thread=new Thread(Task.asRunnable(()->
            {
                Thread.sleep(2000);
                System.out.println("Hello, world!");
                throw new Exception("Check this out");
            }));
            thread.start();

     */



    /*
  var table=new ArrayList<Pair<String>>();
  var pair1=new Pair<String>("a1","a2");
  var pair2=new Pair<String>("b1","b2");
  addAll(table,pair1,pair2);

   */
    /*
    @SafeVarargs
    public static <E> E[] array(E... array){return array;}

    @SuppressWarnings("unchecked")
    public static <T> void addAll(ArrayList<T> coll, T... ts){
        coll.addAll(Arrays.asList(ts));
    }
    public static Pair<String> maxmin(String[] a){
        if(a==null||a.length==0){return null;}
        String min=a[0];
        String max=a[0];
        for(String str:a){
            if (min.compareTo(str)>0) min=str;
            if(max.compareTo(str)<0) max=str;
        }
        return new Pair<String>(min,max);
    }
    public static <T extends Comparable> T min(T[] a){
        if(a==null||a.length==0) return null;
        T smallest=a[0];
        for(T t:a){
            if(smallest.compareTo(t)>0) smallest=t;
        }
        return  smallest;
    }

    @SafeVarargs
    public static <T> T getMiddle(T... a){
        return a[a.length/2];
    }
    public static Object[] badCopyOf(Object[] a, int newLength){
        var newArray=new Object[newLength];
        System.arraycopy(a,0,newArray,0,Math.min(a.length,newLength));
        return newArray;
    }
    public static Object goodCopyOf(Object a,int newLength){
        Class cl=a.getClass();
        if(!cl.isArray()) return null;
        Class componentType=cl.getComponentType();
        int length= Array.getLength(a);
        Object newArray=Array.newInstance(componentType,length);
        System.arraycopy(a,0,newArray,0,Math.min(newLength,length));
        return  newArray;
    }
    public static void printConstructors(Class cl){
        Constructor[] constructors=cl.getDeclaredConstructors();
        for(Constructor constructor:constructors){
            System.out.print("   ");
            String modifier=Modifier.toString(constructor.getModifiers());
            if(modifier.length()>0) System.out.print(modifier+" ");
            String name=constructor.getName();
            System.out.print(name+"(");
            Class[] paramType=constructor.getParameterTypes();
            for(int j=0;j<paramType.length;j++){
                if(j!=0) System.out.print(",");
                System.out.print(paramType[j].getName());
            }
            System.out.println(");");
        }
    }
    public static void printMethods(Class cl){
        Method[] methods=cl.getDeclaredMethods();
        for(Method method:methods){
            System.out.print("   ");
            String modifier=Modifier.toString(method.getModifiers());
            Class retType=method.getReturnType();
            String name=method.getName();
            Class[] paramTypes=method.getParameterTypes();
            if(modifier.length()>0) System.out.print(modifier+" ");
            System.out.print(retType+" "+name+" (");
            for(int j=0;j<paramTypes.length;j++){
                if(j!=0) System.out.print(",");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }
    public static void printFields(Class cl){
        Field[] fields=cl.getDeclaredFields();
        for(Field field:fields){
            System.out.print("   ");
            String modifier=Modifier.toString(field.getModifiers());
            Class type=field.getType();
            String name=field.getName();
            if(modifier.length()>0)System.out.print(modifier+" ");
            System.out.println(type.getName()+" "+name+";");
        }
    }
    public static  int factorial(int n){
        System.out.println("factorial("+n+"):");
        StackWalker walker=StackWalker.getInstance();
        walker.forEach(System.out::println);
        int r;
        if(n<=1) r=1;
        else r=n*factorial(n-1);
        System.out.println("return "+r);
        return r;
    }
    public static void repeat(int n, IntConsumer action){
        for(int i=0;i<n;i++) action.accept(i);
    }
    public static void returnName(){
        System.out.println(new Test(){}.getClass().getEnclosingClass());
    }
    public static void repeatMessage(String text,int delay){
        ActionListener listener=event->{
            System.out.println(text);
            Toolkit.getDefaultToolkit().beep();
        };
        new Timer(delay,listener).start();
    }



    public static enum Size{
        SMALL("S"),MEDIUM("M"),LARGE("L"),EXTRA_LARGE("XL");
        private String abbreviation;
        private Size(String abbreviation){this.abbreviation=abbreviation; }
        public String getAbbreviation(){return abbreviation;}
    }


    public static double max(double ... values){
        double largest=Double.NEGATIVE_INFINITY;
        for(double value:values){
            if(value>largest) largest=value;
        }
        return largest;
    }

     */
}

    /*
    Method square=Test.class.getMethod("square",double.class);
    Method sqrt=Math.class.getMethod("sqrt",double.class);
    printTable(1,10,10,square);
    printTable(1,10,10,sqrt);
    }
    public static double square(double x){
    return x*x;
    }
    public static void printTable(double from,double to,int n,Method f)
            throws InvocationTargetException, IllegalAccessException {
    System.out.println(f);
    double dx=(to-from)/(n-1);
    for(double x=from;x<=to;x+=dx){
        double y=(Double) f.invoke(null,x);
        System.out.printf("%10.4f  |%10.4f%n",x,y);
    }

     */
    /*
    Employee harry=new Employee("harry potter",50000,1998,07,04);
    Class cl=Employee.class;
    Method method=cl.getMethod("getName");
    String str=(String)method.invoke(harry);
    System.out.println(str);

     */
    /*
   int[] a={1,2,3};
   a=(int[]) goodCopyOf(a,10);
   System.out.println(Arrays.toString(a));

   String[] b={"Tom","Dick","Harry"};
   b=(String[]) goodCopyOf(b,10);
   System.out.println(Arrays.toString(b));

   System.out.println("The following call will generate an exception.");
   b=(String[]) badCopyOf(b,10);

    */

    /*
   var squares=new ArrayList<Integer>();
   for(int i=1;i<=5;i++){
       squares.add(i*i);
   }
   System.out.println(new ObjectAnalyzer().toString(squares));

    */
    /*
    var harry=new Employee("Harry Hacker",50000,1998,07,04);
    Class cl=harry.getClass();
    Field f=cl.getDeclaredField("salary");
    f.setAccessible(true);
    Object v=f.get(harry);
    System.out.print(v);

     */
    /*
    String name;
    if(args.length>0) name=args[0];
    else {
        var in=new Scanner(System.in);
        System.out.println("Enter class name(e.g. java.util.Date): ");
        name=in.next();
    }
    Class cl=Class.forName(name);
    Class supercl=cl.getSuperclass();
    String modifiers= Modifier.toString(cl.getModifiers());
    if(modifiers.length()>0) System.out.print(modifiers+" ");
    System.out.print("class "+name);
    if(supercl!=null&&supercl!=Object.class) System.out.print(" extends "+supercl.getName());
    System.out.println("\n{\n");
    printConstructors(cl);
    System.out.println();
    printMethods(cl);
    System.out.println();
    printFields(cl);
    System.out.println("}");

     */

    /*
       public static void printConstructors(Class cl){
           Constructor[] constructors=cl.getDeclaredConstructors();
           for(Constructor c:constructors)
           {
               String name=c.getName();
               System.out.print("   ");
               String modifiers=Modifier.toString(c.getModifiers());
               if(modifiers.length()>0) System.out.print(modifiers+" ");
               System.out.print(name+"(");
               Class[] paramTypes=c.getParameterTypes();
               for(int j=0;j<paramTypes.length;j++){
                   if(j>0) System.out.print(",");
                   System.out.print(paramTypes[j].getName());
               }
               System.out.println(");");
           }

       }

    */
    /*
    public static void printMethods(Class cl){
        Method[] methods=cl.getDeclaredMethods();
        for(Method m:methods){
            Class retType=m.getReturnType();
            String name=m.getName();
            System.out.print("   ");
            String modifiers=Modifier.toString(m.getModifiers());
            if(modifiers.length()>0) System.out.print(modifiers+" ");
            System.out.print(retType.getName()+" "+name+"(");
            Class[] paramTypes=m.getParameterTypes();
            for(int j=0;j<paramTypes.length;j++){
                if(j>0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

     */

    /*
    public static void printFields(Class cl){
        Field[] fields=cl.getDeclaredFields();
        for(Field f:fields){
            String name=f.getName();
            Class type=f.getType();
            System.out.print("   ");
            String modifiers=Modifier.toString(f.getModifiers());
            if(modifiers.length()>0) System.out.print(modifiers+" ");
            System.out.println(type.getName()+" "+name+";");
        }
    }

     */

    /*
 ArrayList<pair<String>> table=new ArrayList<pair<String>>();
 pair<String> pair1=new pair<String>("a1","a2");
 var pair2=new pair<String>("b1","b2");
 addAll(table,pair1,pair2);
 pair<String>[] table2=array(pair1,pair2);

  */

    /*
String middle=Test.getMiddle("John","Q.","Public");
double mid=Test.getMiddle(100.0,5.4);
System.out.println(middle);
var str=new String[]{"Hi","Hello"};
String smallest=Test.min(str);
System.out.println(smallest);
 */

    /*
    Stack s = new Stack<String>();
    s.push("me");
    s.push("you");
    try{
        s.pop();
    }
    catch (EmptyStackException e){

    }
    System.out.println("Success!");

     */
    /*
   try(var in =new Scanner(System.in))
   {
       System.out.println("Enter n:" );
       int n=in.nextInt();
       factorial(n);
   }

    */


        /*
        Throwable t=new Throwable();
        StringWriter out=new StringWriter();
        t.printStackTrace(new PrintWriter(out));
        String description=out.toString();
        System.out.println(description);

         */
        /*
        TalkingClock clock=new TalkingClock();
        clock.start(1000,true);
        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
        TalkingClock jabberer=new TalkingClock();

         */
/*
        TimePrinter listener=new TimePrinter();
        Timer timer=new Timer(1000,listener);
        timer.start();
        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);

         */
        /*
        Employee original=new Employee("Harry Hacker", 50000, 1989, 10, 2);
        Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        Employee changed=original.clone();
        Manager ceo= (Manager) boss.clone();
         */

        /*
        Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        boss.setBonus(500);
        Manager boss2 = new Manager("Carl Cracker2", 80000, 1987, 12, 15);
        boss2.setBonus(500);
        Employee aEmployee = new Employee("Harry Hacker", 50000, 1989, 10, 2);
        Employee[] staff = new Manager[3];
        staff[0] = boss;
        staff[1] = new Manager("Harry Hacker", 30000, 1989, 10, 1);
        Manager staff1=(Manager) staff[1];
        staff1.setBonus(1000);
        staff[2] = new Manager("Tony Tester", 40000, 1990, 3, 15);
        Arrays.sort(staff);
        System.out.printf("%8.1f  \n",staff[0].getSalary());
        System.out.printf("%8.1f  \n",staff[1].getSalary());
        System.out.printf("%8.1f  ",staff[2].getSalary());

 */
/*
        String[] planets=new String[]{"Mercury","Venus","Earth","Mars","Jupiter","Saturn","Uranus"
        ,"Neptune"};
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted in dictionary order: ");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted by length: ");
        Arrays.sort(planets,(first,second)->first.length()-second.length());
        System.out.println(Arrays.toString(planets));



        Timer timer=new Timer(1000, System.out::println);
        timer.start();
        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
 */
/*
        repeat(10,i->System.out.println("Countdown: "+(9-i)));

         */
