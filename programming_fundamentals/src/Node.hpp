#pragma once
#include <memory>
#include <string>
#include <iostream>
#include "Token.hpp"

/**
 * @brief 木構造のノード
 * @note 本来なら多分木にするべきだが、話を簡略にするために2分木ノードとして実装する
 */
struct Node
{
    std::shared_ptr<Node> _left = nullptr;
    std::shared_ptr<Node> _right = nullptr;
    Token _token;

    /**
     * コンストラクタ
     * @param const std::string &value
     */
    Node(const Token &token) : _token(token)
    {
    }

    /**
     * ノード情報を出力
     */
    void printNode()
    {
        using std::cout;
        using std::endl;
        cout << this->_token.getValue() << " ";
    }
};
