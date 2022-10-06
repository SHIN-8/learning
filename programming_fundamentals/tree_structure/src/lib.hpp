#pragma once
#include <memory>
#include <string>
#include <functional>

/**
 *  ノード
 */
template <typename T>
struct node
{
    std::shared_ptr<node> _left;
    std::shared_ptr<node> _right;
    T _value;

    node(const T &value)
        : _left(nullptr), _right(nullptr), _value(value)
    {
    }
};

/**
 *  ノードを追加（２分探索木）
 */
template <typename T>
void add_node(std::shared_ptr<node<T>> &v, const T &value)
{
    if (v == nullptr)
    {
        v = std::make_shared<node<T>>(value);
    }
    else
    {
        if (v->_value < value)
        {
            if (v->_left == nullptr)
            {
                v->_left = std::make_shared<node<T>>(value);
            }
            else
            {
                add_node(v->_left, value);
            }
        }
        else
        {
            if (v->_right == nullptr)
            {
                v->_right = std::make_shared<node<T>>(value);
            }
            else
            {
                add_node(v->_right, value);
            }
        }
    }
}

/*
 * 行きがけ順の走査
 */
template <typename T>
void pre_order_traverse(const std::shared_ptr<node<T>> &n, std::function<void(T)> callback)
{
    callback(n->_value);

    if (n->_left != nullptr)
    {
        pre_order_traverse(n->_left, callback);
    }
    if (n->_right != nullptr)
    {
        pre_order_traverse(n->_right, callback);
    }
}

/*
 * 通りがけ順の走査
 */
template <typename T>
void in_order_traverse(const std::shared_ptr<node<T>> &n, std::function<void(T)> callback)
{
    if (n->_left != nullptr)
    {
        in_order_traverse(n->_left, callback);
    }
    callback(n->_value);
    if (n->_right != nullptr)
    {
        in_order_traverse(n->_right, callback);
    }
}

/*
 * 帰りがけ順の走査
 */
template <typename T>
void post_order_traverse(const std::shared_ptr<node<T>> &n, std::function<void(T)> callback)
{

    if (n->_left != nullptr)
    {
        post_order_traverse(n->_left, callback);
    }
    if (n->_right != nullptr)
    {
        post_order_traverse(n->_right, callback);
    }

    callback(n->_value);
}
