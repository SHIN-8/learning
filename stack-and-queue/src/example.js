const Stack = require("./stack");
const Queue = require("./queue");

// スタック
const stack = new Stack(); // []
stack.push(1); // [] => [1]
stack.push(2); // [1] => [1, 2]
console.assert(stack.pop() === 2); // [1, 2] => [1]
stack.push(3); // [1] => [1, 3]
console.assert(stack.pop() === 3); // [1, 3] => [1]
console.assert(stack.pop() === 1); // [1] => []

// キュー
const queue = new Queue(); // []
queue.enqueue(1); // [] => [1]
queue.enqueue(2); // [1] => [1, 2]
console.assert(queue.dequeue() === 1); // [1, 2] => [2]
queue.enqueue(3); // [2] => [2, 3]
console.assert(queue.dequeue() === 2); // [2, 3] => [3]
console.assert(queue.dequeue() === 3); // [3] => []
