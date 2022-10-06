#include <iostream>
#include "Node.hpp"
#include "Parser.hpp"
#include "Token.hpp"
#include "Tokenizer.hpp"
#include "Eval.hpp"

int main(int argc, char *argv[])
{
    using std::cout;
    using std::endl;

    if (argc < 2)
    {
        cout << "please specify s-expression" << endl;
        return -1;
    }

    //入力文字列をトークン列に分解
    std::string source = std::string(argv[1]);
    auto tokens = tokenize(source);
    for (auto it = tokens.begin(); it != tokens.end(); ++it)
    {
        it->printToken();
    }

    //トークン列をパースして構文木を作成
    std::shared_ptr<Node> root = nullptr;
    auto it = tokens.begin();
    root = parse(it);

    //構築した構文木を中置記法で表示。
    traverse(root);

    //構築した構文木を評価
    std::vector<int> v = {};
    eval(root, v);
    cout << "= " << v[0] << endl;

    return 0;
}