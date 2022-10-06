#pragma once
#include "Node.hpp"

/**
 * @brief 構文木の走査
 * 
 * @param root 
 */
void traverse(std::shared_ptr<Node> root)
{

    if (root->_left != nullptr)
    {
        traverse(root->_left);
    }
    if (root != nullptr)
    {
        root->printNode();
    }
    if (root->_right != nullptr)
    {
        traverse(root->_right);
    }
}

/**
 * @brief トークン列をパースして、木構造を生成
 * 
 * @param tokens 
 * @param root 
 */
template <typename RandomAccessIterator>
std::shared_ptr<Node> parse(RandomAccessIterator &it)
{
    using namespace std;

    auto token = *it;
    //左ブラケットから開始しているはず。
    if (!token.isLeftBracket())
    {
        throw std::exception();
    }

    //左ブラケットの次は、演算子
    it++;
    token = *it;
    if (!token.isAddOperator() && !token.isSubOperator() &&
        !token.isMultiplyOperator() && !token.isDivideOperator())
    {
        throw std::exception();
    }

    auto node = std::make_shared<Node>(token);
    //左辺値の取り出し
    it++;
    token = *it;
    if (token.isLeftBracket())
    {
        node->_left = parse(it);
    }
    else if (token.isAtom())
    {
        node->_left = std::make_shared<Node>(token);
    }

    //右辺値の取り出し
    it++;
    token = *it;
    if (token.isRightBracket())
    {
        it++;
        token = *it;
    }
    if (token.isLeftBracket())
    {
        node->_right = parse(it);
    }
    else if (token.isAtom())
    {
        node->_right = std::make_shared<Node>(token);
    }

    return node;
}
