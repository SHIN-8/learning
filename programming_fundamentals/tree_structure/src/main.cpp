#include <algorithm>
#include <iostream>
#include <vector>
#include "lib.hpp"

void print(int v)
{
    using std::cout;
    using std::endl;

    cout << v << " " << endl;
}

int main()
{
    using std::cout;
    using std::endl;

    std::shared_ptr<node<int>> root = nullptr;
    std::vector<int> values = {1, 2, 3, 4, 5, 6};

    std::for_each(values.begin(), values.end(), [&root](auto value) {
        add_node(root, value);
    });

    cout << "====pre order traverse====" << endl;
    pre_order_traverse<int>(root, print);

    cout << "====in order traverse====" << endl;
    in_order_traverse<int>(root, print);

    cout << "====post order traverse====" << endl;
    post_order_traverse<int>(root, print);
}