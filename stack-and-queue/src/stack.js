class Stack {
  data = [];
  top = 0;

  isEmpty() {
    return this.top === 0;
  }

  push(x) {
    this.data[this.top] = x;
    this.top = this.top + 1;
  }

  pop() {
    if (this.isEmpty()) {
      throw new Error("stack is empty.");
    }
    this.top = this.top - 1;
    return this.data[this.top];
  }
}

module.exports = Stack;
