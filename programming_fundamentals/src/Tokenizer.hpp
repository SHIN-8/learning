#pragma once
#include <vector>
#include "Token.hpp"

/**
 * @brief 文字列をパースして、トークン列を生成
 * 
 * @param source 
 * @return std::vector<Token> 
 */

std::vector<Token> tokenize(const std::string &source)
{
    std::vector<Token> tokens = std::vector<Token>();
    for (auto pos = 0; pos < source.length(); pos++)
    {
        char v = source[pos];
        if (v == WhiteSpace)
        {
            continue;
        }

        if (v == LeftBracket)
        {
            const auto value = source.substr(pos, 1);
            tokens.push_back(Token(TokenType::LeftBracket, value));
        }
        else if (v == RightBracket)
        {
            const auto value = source.substr(pos, 1);
            tokens.push_back(Token(TokenType::RightBracket, value));
        }
        else if (v == AddOperator)
        {
            const auto value = source.substr(pos, 1);
            tokens.push_back(Token(TokenType::AddOperator, value));
        }
        else if (v == SubOperator)
        {
            const auto value = source.substr(pos, 1);
            tokens.push_back(Token(TokenType::SubOperator, value));
        }
        else if (v == MultiplyOperator)
        {
            const auto value = source.substr(pos, 1);
            tokens.push_back(Token(TokenType::MultiplyOperator, value));
        }
        else if (v == DivideOperator)
        {
            const auto value = source.substr(pos, 1);
            tokens.push_back(Token(TokenType::DivideOperator, value));
        }
        else
        {
            auto length = 1;
            for (auto i = pos; i < source.length(); i++)
            {
                if (source[i] == RightBracket ||
                    source[i] == AddOperator ||
                    source[i] == SubOperator ||
                    source[i] == MultiplyOperator ||
                    source[i] == DivideOperator ||
                    source[i] == WhiteSpace)
                {
                    break;
                }

                length++;
            }

            const auto value = source.substr(pos, length - 1);
            tokens.push_back(Token(TokenType::Atom, value));
            pos += length - 2;
        }
    }

    return tokens;
}
