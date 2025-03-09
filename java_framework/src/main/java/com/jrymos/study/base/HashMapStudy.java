package com.jrymos.study.base;

import org.junit.Test;


public class HashMapStudy {

    @Test
    public void headInsert() {
        //初始：
        //A -> B -> C
        //nowEntry = A
        //newHead = null;

        //循环1：
        //nextEntry = A.next = B
        //A.next = newHead = null
        //newHead = A
        //nowEntry = nextEntry = B

        //循环2次：
        //nextEntry = B.next = C  (一定是C吗？,并发情况下，B.next有可能是A)
        //B.next = newHead = A
        //newHead = B
        //正常情况下： nowEntry = nextEntry = C  但是由于出现了并发，可能得情况是： nowEntry = nextEntry = A

        //循环3：假设nowEntry = nextEntry = A
        //nextEntry -> A.next -> null
        //A.next = newHead = B
        //newHead = A

        //退出循环后，将会出现 A->B,B->A的循环链表
    }
}
