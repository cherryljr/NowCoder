/*
Description:
Write a program to reverse a stack using recursion. 
You are not allowed to use loop constructs like while, for..etc, 
and you can only use the following ADT functions on Stack S:
    isEmpty(S)
    push(S)
    pop(S)

This is a quetions on: http://www.geeksforgeeks.org/reverse-a-stack-using-recursion/
*/

/**
 * Approach 1: Using Recursion
 * 不适用其他的数据结构或者开辟额外的空间，只使用 递归 来完成栈的逆序操作。
 * （这里递归过程所使用的栈空间不计算为额外空间）
 * 这道题目的想法还是很简单的，就是将 Stack 中所有的元素 pop() 出来，直到 Stack 为空。
 * 然后再将 pop() 出来的元素依次 push() 回去就能够完成逆序了。
 * 但是本题的难点在于要求使用 递归 来实现这个过程，这就意味着我们不能够使用额外的空间来存储被 pop() 出来的元素。
 *
 * 为了实现这一点，本题使用了两个 递归函数。（本题这两个递归函数的应用还是十分令人佩服的）
 * getAndRemoveLastElement(): 该函数的功能是获得并移除当前栈的栈底元素
 *  为了更好地理解该函数，我们以 1, 2, 3 为例（栈顶到栈底顺序）
 *      第一次调用：栈变成 2, 3; element = 1, stack非空, last = ? (进行递归调用)
 *      第二次调用：栈变成 3; element = 2, stack非空, last = ? (进行递归调用)
 *      第三次调用：栈变成 null; element = 3, stack为空, 返回 element = 3
 *      回到第二次调用处：element = 2, last = 3, 将 element 压入栈中; 栈变成 2
 *      回到第一次调用处：element = 1, last = 3, 将 element 压入栈中; 栈变成 1, 2
 *      执行完毕，返回 3，栈变成 1, 2
 * reverse(): 该函数的功能是利用 getAndRemoveLastElement() 函数从而实现对栈的逆序
 *      这里同样以 1, 2, 3 为例。
 *      第一次调用：stack非空; 调用 getAndRemoveLastElement() 函数，获得 last = 3; 栈变成 1, 2. 继续递归调用
 *      第二次调用：stack非空; 调用 getAndRemoveLastElement() 函数，获得 last = 2; 栈变成 1. 继续递归调用
 *      第三次调用：stack非空; 调用 getAndRemoveLastElement() 函数，获得 last = 1; 栈变成 空. 继续递归调用
 *      第四次调用：stack为空,返回结束。
 *      回到第三次调用处：将 last = 1 push回栈中; 栈变成 1
 *      回到第二次调用处：将 last = 2 push回栈中; 栈变成 2, 1
 *      回到第一次调用处：将 last = 3 push回栈中; 栈变成 3, 2, 1
 *      整个过程执行完毕，栈变成了 3, 2, 1 完成逆序。
 * 至此，可见本题对于递归的使用可以说是相当好了...
 */
import java.util.Stack;

public class Main {
    // Using Stack class for stack implementation
    static Stack<Character> stack = new Stack<>();

    // Below is a recursive function that
    // get and remove the element at the bottom of a stack
    static char getAndRemoveLastElement() {
        char element = stack.pop();
        if (stack.isEmpty()) {
            return element;
        } else {
            char last = getAndRemoveLastElement();
            stack.push(element);
            return last;
        }
    }

    // Below is the function that reverses the given stack using getAndRemoveLastElement()
    static void  reverse() {
        if (stack.isEmpty()) {
            return;
        }
        char last = getAndRemoveLastElement();
        reverse();
        stack.push(last);
    }

    public static void main(String[] args) {
        //push elements into the stack
        stack.push('1');
        stack.push('2');
        stack.push('3');
        stack.push('4');
        System.out.println("Original Stack");
        System.out.println(stack);

        //function to reverse the stack
        reverse();
        System.out.println("Reversed Stack");
        System.out.println(stack);
    }
}
