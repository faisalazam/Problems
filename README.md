## Tree Traversal Techniques

Unlike linear data structures (Array, Linked List, Queues, Stacks, etc) which have only one logical way to traverse
them, trees can be traversed in different ways.

A Tree Data Structure can be traversed in following ways:

* Depth First Search or DFS
    * Inorder Traversal
    * Preorder Traversal
    * Postorder Traversal
* Level Order Traversal or Breadth First Search or BFS
* Boundary Traversal
* Diagonal Traversal

### Uses of Inorder Traversal:

In the case of binary search trees (BST), Inorder traversal gives nodes in non-decreasing order. To get nodes of BST
in non-increasing order, a variation of Inorder traversal where Inorder traversal is reversed can be used.

### Uses of Preorder:

Preorder traversal is used to create a copy of the tree. Preorder traversal is also used to get prefix expressions on
an expression tree.

### Uses of Postorder:

Postorder traversal is used to delete the tree. Postorder traversal is also useful to get the postfix expression of
an expression tree.

# Runtime Complexity of Java Collections

https://www.bigocheatsheet.com/

Below are the Big O performance of common functions of different Java Collections.

| List                 | Add  | Remove | Get  | Contains | Next | Data Structure |
|----------------------|------|--------|------|----------|------|----------------|
| ArrayList            | O(1) | O(n)   | O(1) | O(n)     | O(1) | Array          |
| LinkedList           | O(1) | O(1)   | O(n) | O(n)     | O(1) | Linked List    |
| CopyOnWriteArrayList | O(n) | O(n)   | O(1) | O(n)     | O(1) | Array          |

| Set                   | Add      | Remove   | Contains | Next     | Size | Data Structure           |
|-----------------------|----------|----------|----------|----------|------|--------------------------|
| HashSet               | O(1)     | O(1)     | O(1)     | O(h/n)   | O(1) | Hash Table               |
| LinkedHashSet         | O(1)     | O(1)     | O(1)     | O(1)     | O(1) | Hash Table + Linked List |
| EnumSet               | O(1)     | O(1)     | O(1)     | O(1)     | O(1) | Bit Vector               |
| TreeSet               | O(log n) | O(log n) | O(log n) | O(log n) | O(1) | Red-black tree           |
| CopyOnWriteArraySet   | O(n)     | O(n)     | O(n)     | O(1)     | O(1) | Array                    |
| ConcurrentSkipListSet | O(log n) | O(log n) | O(log n) | O(1)     | O(n) | Skip List                |

| Queue                   | Offer    | Peak | Poll     | Remove | Size | Data Structure |
|-------------------------|----------|------|----------|--------|------|----------------|
| PriorityQueue           | O(log n) | O(1) | O(log n) | O(n)   | O(1) | Priority Heap  |
| LinkedList              | O(1)     | O(1) | O(1)     | O(1)   | O(1) | Array          |
| ArrayDequeue            | O(1)     | O(1) | O(1)     | O(n)   | O(1) | Linked List    |
| ConcurrentLinkedQueue   | O(1)     | O(1) | O(1)     | O(n)   | O(n) | Linked List    |
| ArrayBlockingQueue      | O(1)     | O(1) | O(1)     | O(n)   | O(1) | Array          |
| PriorirityBlockingQueue | O(log n) | O(1) | O(log n) | O(n)   | O(1) | Priority Heap  |
| SynchronousQueue        | O(1)     | O(1) | O(1)     | O(n)   | O(1) | None!          |
| DelayQueue              | O(log n) | O(1) | O(log n) | O(n)   | O(1) | Priority Heap  |
| LinkedBlockingQueue     | O(1)     | O(1) | O(1)     | O(n)   | O(1) | Linked List    |

| Map                   | Get      | ContainsKey | Next     | Data Structure           |
|-----------------------|----------|-------------|----------|--------------------------|
| HashMap               | O(1)     | O(1)        | O(h / n) | Hash Table               |
| LinkedHashMap         | O(1)     | O(1)        | O(1)     | Hash Table + Linked List |
| IdentityHashMap       | O(1)     | O(1)        | O(h / n) | Array                    |
| WeakHashMap           | O(1)     | O(1)        | O(h / n) | Hash Table               |
| EnumMap               | O(1)     | O(1)        | O(1)     | Array                    |
| TreeMap               | O(log n) | O(log n)    | O(log n) | Red-black tree           |
| ConcurrentHashMap     | O(1)     | O(1)        | O(h / n) | Hash Tables              |
| ConcurrentSkipListMap | O(log n) | O(log n)    | O(1)     | Skip List                |