#pragma once
#include <string>
#include <set>

/**
 * @brief 今回のS式で使用する文字
 * 
 */
static const char LeftBracket = '(';
static const char RightBracket = ')';
static const char WhiteSpace = ' ';
static const char AddOperator = '+';
static const char SubOperator = '-';
static const char MultiplyOperator = '*';
static const char DivideOperator = '/';

/**
 * @brief トークン種別
 * 
 */
enum class TokenType
{
    Atom,
    AddOperator,
    SubOperator,
    MultiplyOperator,
    DivideOperator,
    LeftBracket,
    RightBracket,
};

/**
 * @brief トークン
 * 
 */
class Token
{
    TokenType _type;
    std::string _value;

public:
    /**
     * @brief Construct a new Token object
     * 
     * @param type 
     * @param value 
     */
    Token(const TokenType &type, const std::string &value);

    /**
     * @brief Get the Token Type Name object
     * 
     * @return string 
     */
    void printToken();

    /**
     * @brief Get the Token Type object
     * 
     * @return TokenType 
     */
    TokenType getTokenType();

    /**
     * @brief Get the Value object
     * 
     * @return string 
     */
    std::string getValue();

    /**
     * トークン種別が Atom か
     * @return 
     */
    bool isAtom();

    /**
     * トークン種別が + 演算子 か
     * @return 
     */
    bool isAddOperator();

    /**
     * トークン種別が - 演算子 か
     * @return 
     */
    bool isSubOperator();

    /**
     * トークン種別が * 演算子 か
     * @return 
     */
    bool isMultiplyOperator();

    /**
     * トークン種別が / 演算子 か
     * @return 
     */
    bool isDivideOperator();

    /**
     * トークン種別が 左括弧 か
     * @return 
     */
    bool isLeftBracket();

    /**
     * トークン種別が 右括弧 か
     * @return 
     */
    bool isRightBracket();
};