class Queue {
  data = [];
  head = 0;
  tail = 0;

  isEmpty() {
    return this.head === this.tail;
  }

  enqueue(x) {
    this.data[this.tail] = x;
    this.tail = this.tail + 1;
  }

  dequeue() {
    if (this.isEmpty()) {
      throw new Error("queue is empty.");
    }
    const x = this.data[this.head];
    this.head = this.head + 1;
    return x;
  }
}

module.exports = Queue;
