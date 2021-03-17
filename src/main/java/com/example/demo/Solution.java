package com.example.demo;

import java.util.*;

public class Solution {
    
    public static void main(String[] args) {
        int []A=new int[3];
        System.out.println(A[2]);

    }
    Map<String, Integer> wordId = new HashMap<String, Integer>();
    List<List<Integer>> edge = new ArrayList<List<Integer>>();
    int nodeNum = 0;

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        for (String word : wordList) {
            addEdge(word);
        }
        addEdge(beginWord);
        if (!wordId.containsKey(endWord)) {
            return 0;
        }

        int[] disBegin = new int[nodeNum];
        Arrays.fill(disBegin, Integer.MAX_VALUE);
        int beginId = wordId.get(beginWord);
        disBegin[beginId] = 0;
        Queue<Integer> queBegin = new LinkedList<Integer>();
        queBegin.offer(beginId);

        int[] disEnd = new int[nodeNum];
        Arrays.fill(disEnd, Integer.MAX_VALUE);
        int endId = wordId.get(endWord);
        disEnd[endId] = 0;
        Queue<Integer> queEnd = new LinkedList<Integer>();
        queEnd.offer(endId);

        while (!queBegin.isEmpty() && !queEnd.isEmpty()) {
            int queBeginSize = queBegin.size();
            for (int i = 0; i < queBeginSize; ++i) {
                int nodeBegin = queBegin.poll();
                if (disEnd[nodeBegin] != Integer.MAX_VALUE) {
                    return (disBegin[nodeBegin] + disEnd[nodeBegin]) / 2 + 1;
                }
                for (int it : edge.get(nodeBegin)) {
                    if (disBegin[it] == Integer.MAX_VALUE) {
                        disBegin[it] = disBegin[nodeBegin] + 1;
                        queBegin.offer(it);
                    }
                }
            }

            int queEndSize = queEnd.size();
            for (int i = 0; i < queEndSize; ++i) {
                int nodeEnd = queEnd.poll();
                if (disBegin[nodeEnd] != Integer.MAX_VALUE) {
                    return (disBegin[nodeEnd] + disEnd[nodeEnd]) / 2 + 1;
                }
                for (int it : edge.get(nodeEnd)) {
                    if (disEnd[it] == Integer.MAX_VALUE) {
                        disEnd[it] = disEnd[nodeEnd] + 1;
                        queEnd.offer(it);
                    }
                }
            }
        }
        return 0;
    }

    public void addEdge(String word) {
        addWord(word);
        int id1 = wordId.get(word);
        char[] array = word.toCharArray();
        int length = array.length;
        for (int i = 0; i < length; ++i) {
            char tmp = array[i];
            array[i] = '*';
            String newWord = new String(array);
            addWord(newWord);
            int id2 = wordId.get(newWord);
            edge.get(id1).add(id2);
            edge.get(id2).add(id1);
            array[i] = tmp;
        }
    }

    public void addWord(String word) {
        if (!wordId.containsKey(word)) {
            wordId.put(word, nodeNum++);
            edge.add(new ArrayList<Integer>());
        }
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        Map<Integer,List<List<String>>> map=new HashMap<>();
        List<List<String>> wordBreaks=backtrack(s, s.length(), new HashSet<String>(wordDict), 0, map);
        List<String> breakList = new LinkedList<String>();
        for(List<String> wordBreak:wordBreaks){
            breakList.add(String.join(" ",wordBreak));
        }
        return breakList;
    }
    private List<List<String>> backtrack(String s,int length,Set<String> wordSet,int index,
                                         Map<Integer,List<List<String>>> map){
        if(!map.containsKey(index)){
            List<List<String>> wordBreaks=new LinkedList<>();
            if(index==length)
                wordBreaks.add(new LinkedList<String>());
            for(int i=index+1;i<=length;i++){
                String word=s.substring(index,i);
                if(wordSet.contains(word)){
                    List<List<String>> nextWordBreaks=backtrack(s,length,wordSet,i,map);
                    for(List<String> nextWordBreak:nextWordBreaks){
                        LinkedList<String> wordBreak=new LinkedList<String>(nextWordBreak);
                        wordBreak.addFirst(word);
                        wordBreaks.add(wordBreak);
                    }
                }
            }
            map.put(index,wordBreaks);
        }
        return map.get(index);
    }
    static public int longestMountain(int[] A) {
        int maxNum=0;
        if(A==null||A.length<=2)
            return 0;
        int start=1;
        while(start<A.length-1){
            if(A[start]<=A[start-1]||A[start]<=A[start+1]){
                start++;
                continue;
            }
            int left=start;
            int right=start;
            while(left-1>=0&&A[left-1]<A[left])
                left=left-1;
            while(right+1<A.length&&A[right+1]<A[right])
                right=right+1;
            if(left<start&&right>start)
                maxNum=Math.max(maxNum,right-left+1);
            start=right+1;
        }
        return maxNum;
    }
    public int videoStitching(int[][] clips, int T) {
        int [] dp=new int[T+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0]=0;
        for(int i=1;i<=T;i++){
            for(int[] clip:clips){
                if(clip[0]<i&&clip[1]>=i)
                    dp[i]=Math.min(dp[i],dp[clip[0]]+1);
            }
        }
        return dp[T]==Integer.MIN_VALUE? -1:dp[T];
    }
    static  public List<Integer> partitionLabels(String S) {
        ArrayList<Integer> res=new ArrayList<>();
        int length= S.length();
        int start=0;
        while(start<length){
            int tmp=start;
            int end=start;
            while(start<=end){
                int curEnd=length-1;
                while(S.charAt(start)!=S.charAt(curEnd)&&start<=curEnd){
                    curEnd--;
                }
                if(curEnd>=end)
                    end=curEnd;
                start++;
            }
            res.add(end-tmp+1);
        }

        return res;
    }
    static public boolean isLongPressedName(String name, String typed) {
        char[] nameList=name.toCharArray();
        char[] typedList=typed.toCharArray();
        int nameLength=nameList.length;
        int typedLength=typedList.length;
        if(typedLength<nameLength)
            return false;
        int nameIndex=0;
        int typedIndex=0;
        while(nameIndex<nameLength&&typedIndex<typedLength){
            if(nameList[nameIndex]==typedList[typedIndex]){
                nameIndex++;
                typedIndex++;
            }
            else if(typedIndex>=1&&typedList[typedIndex]==typedList[typedIndex-1])
                typedIndex++;
            else
                return false;
        }
        while(typedIndex<typedLength){
            if(typedList[typedIndex]==typedList[typedIndex-1])
                typedIndex++;
            else
                return false;
        }
        return true;
    }
    static public boolean backspaceCompare(String S, String T) {
        int s_end=S.length()-1;
        int t_end=T.length()-1;
        int s_num=0;
        int t_num=0;
        while(s_end>=0&&t_end>=0){
            while(s_end>=0){
                if(S.charAt(s_end)=='#'){
                    s_num++;
                    s_end--;
                }
                else if(s_num!=0){
                    s_num--;
                    s_end--;
                }
                else{
                    break;
                }

            }
            while(t_end>=0){
                if(T.charAt(t_end)=='#'){
                    t_num++;
                    t_end--;
                }
                else if(t_num!=0){
                    t_num--;
                    t_end--;
                }
                else
                    break;
            }
            if(t_end==-1&&s_end==-1)
                return true;
            else if(t_end==-1||s_end==-1)
                return false;
            if(S.charAt(s_end)!=T.charAt(t_end))
                return false;
            s_end--;
            t_end--;
        }
        return t_end == -1 && s_end == -1;

    }
    public int missingNumber(int[] nums) {
        if(nums==null)
            return 0;
        boolean[] judge=new boolean[nums.length+1];
        for (int num : nums) {
            judge[num] = true;
        }
        int result=0;
        while(judge[result])
            result++;
        return result;
    }
    public static  int[] exchange(int[] nums) {
        if(nums==null)
            return null;
        int begin=0;
        int end=nums.length-1;
        int tmp;
        while(begin<=end){
            if(nums[begin]/2==0&&nums[end]/2==1){
                tmp=nums[begin];
                nums[begin]=nums[end];
                nums[end]=tmp;
                begin++;
                end--;
            }
            if(nums[begin]/2==0){
                end--;
            }
            if(nums[end]/2==1){
                begin++;
            }
            else{
                begin++;
                end--;
            }
        }
        for (int num : nums) {
            System.out.println(num);
        }
        return nums;
    }
    public ListNode deleteDuplicates(ListNode head) {
        ListNode tmpNode=head;
        while(tmpNode.next!=null){
            if(tmpNode.val==tmpNode.next.val){
                tmpNode.next=tmpNode.next.next;

            }
            else
                tmpNode=tmpNode.next;
        }
        return head;
    }
     public static class ListNode
     { int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }

    public int mySqrt(int x) {
        int left=0;
        int right=x;
        int ans=x;
        while(left<=right){
            int mid=(right-left)/2+left;
            if((long)mid*mid<=x){
                ans=mid;
                left=mid+1;
            }
            else{
                right=mid-1;
            }
        }
        return ans;
    }

    public String longestCommonPrefix(String[] strs) {
        if(strs==null||strs.length==0)
            return "";
        String perfix=strs[0];
        int strLength=perfix.length();
        int length=strs.length;
        for(int i=0;i<strLength;i++){
            char aChar=perfix.charAt(i);
            for(int j=1;j<length;j++){
                if(strs[j].length()==i||strs[j].charAt(i)!=aChar)
                    return perfix.substring(0,i);
            }
        }
        return perfix;
    }
    public int romanToInt(String s) {
        int sum=0;
        s=s.replace("IV","a");
        s=s.replace("IX","b");
        s=s.replace("XL","c");
        s=s.replace("XC","d");
        s=s.replace("CD","e");
        s=s.replace("CM","f");
        for(int i=0;i<s.length();i++){
            sum+=getValue(s.charAt(i));
        }
        return sum;
    }
    private int getValue(char ch){
        switch(ch){
            case 'I':return 1;
            case 'V': return 5;
            case 'X':return 10;
            case 'L':return 50;
            case 'C':return 100;
            case 'D':return 500;
            case 'M':return 1000;
            case 'a':return 4;
            case 'b':return 9;
            case 'c':return 40;
            case 'd':return 90;
            case 'f':return 400;
            case 'e':return 900;
        }
        return 0;
    }

}
