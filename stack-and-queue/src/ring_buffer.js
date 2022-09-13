/**
 * `$ node src/ring_buffer.js`で実行できます
 */

class RingBuffer {
    data = [...Array(3)]; // 要素数 3 を undefined で初期化
    head = 0;
    tail = 0;

    isEmpty() {
        return this.head === this.tail;
    }

    enqueue(x) {
        // NOTE: 循環させたインデックスを求める方法
        // 対象インデックスを要素数で割った余り
        const index = this.tail % this.data.length;
        this.data[index] = x;
        this.tail = index + 1;
    }

    dequeue() {
        const index = this.head % this.data.length;
        const x = this.data[index];
        this.data[index] = undefined; // 要素を初期化
        this.head = index + 1;
        return x;
    }
}

const printQueue = (queue) => (callback) => {
    console.log(`${callback(queue)} -> ${queue.data.map(x => x === undefined ? "." : x.toString())}`);
};

const enqueue = (x) => (queue) => {
    queue.enqueue(x);
    return `enqueue ${x}`;
};

const dequeue = (queue) => {
    return `dequeue ${queue.dequeue()}`;
};

const rb = new RingBuffer();
const print = printQueue(rb);
print(enqueue(1));
print(enqueue(2));
print(enqueue(3));
print(dequeue);
print(dequeue);
print(enqueue(4));
print(enqueue(5));

/*
enqueue 1 -> 1,.,.
enqueue 2 -> 1,2,.
enqueue 3 -> 1,2,3
dequeue 1 -> .,2,3
dequeue 2 -> .,.,3
enqueue 4 -> 4,.,3
enqueue 5 -> 4,5,3
*/
