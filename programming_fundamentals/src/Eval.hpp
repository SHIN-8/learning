#pragma once
#include <vector>
#include <sstream>
#include <iostream>
#include "Node.hpp"
#include "Token.hpp"

int strToInt(const std::string &value)
{
    int result = 0;
    std::stringstream ss;
    ss << value;
    ss >> result;
    return result;
}

int calc(Token &op, int left, int right)
{
    if (op.isAddOperator())
    {
        return left + right;
    }
    if (op.isSubOperator())
    {
        return left - right;
    }
    if (op.isMultiplyOperator())
    {
        return left * right;
    }
    if (op.isDivideOperator())
    {
        return left / right;
    }

    return 0;
}

/**
 * @brief 構文木の走査
 * 
 * @param root 
 */
void eval(std::shared_ptr<Node> &root, std::vector<int> &tmp)
{
    using std::cout;
    using std::endl;

    if (root->_left != nullptr)
    {
        eval(root->_left, tmp);
    }
    if (root->_right != nullptr)
    {

        eval(root->_right, tmp);
    }

    if (root->_token.isAddOperator() ||
        root->_token.isSubOperator() ||
        root->_token.isMultiplyOperator() ||
        root->_token.isDivideOperator())
    {
        auto right = tmp.back();
        tmp.pop_back();
        auto left = tmp.back();
        tmp.pop_back();

        auto v = calc(root->_token, left, right);
        tmp.push_back(v);
    }
    else
    {
        auto v = strToInt(root->_token.getValue());
        tmp.push_back(v);
    }
}
