#include <iostream>
#include "Token.hpp"

//----------------------------------
//
Token::Token(const TokenType &type, const std::string &value)
    : _type(type), _value(value)
{
}

//----------------------------------
//
void Token::printToken()
{
    auto typeName = "";

    if (this->isAtom())
    {
        typeName = "Atom";
    }
    else if (this->isLeftBracket())
    {
        typeName = "Left Bracket";
    }
    else if (this->isAddOperator())
    {
        typeName = "Add Operator";
    }
    else if (this->isSubOperator())
    {
        typeName = "Sub Operator";
    }
    else if (this->isMultiplyOperator())
    {
        typeName = "Multiply Operator";
    }
    else if (this->isDivideOperator())
    {
        typeName = "Divide Operator";
    }
    else if (this->isRightBracket())
    {
        typeName = "Right Bracket";
    }

    std::cout << typeName << " : " << this->_value << std::endl;
}

//----------------------------------
//
TokenType Token::getTokenType()
{
    return this->_type;
}

//----------------------------------
//
std::string Token::getValue()
{
    return this->_value;
}

//----------------------------------
//
bool Token::isAtom()
{
    return this->_type == TokenType::Atom;
}

//----------------------------------
//
bool Token::isAddOperator()
{
    return this->_type == TokenType::AddOperator;
}

//----------------------------------
//
bool Token::isSubOperator()
{
    return this->_type == TokenType::SubOperator;
}

//----------------------------------
//
bool Token::isMultiplyOperator()
{
    return this->_type == TokenType::MultiplyOperator;
}

//----------------------------------
//
bool Token::isDivideOperator()
{
    return this->_type == TokenType::DivideOperator;
}

//----------------------------------
//
bool Token::isLeftBracket()
{
    return this->_type == TokenType::LeftBracket;
}

//----------------------------------
//
bool Token::isRightBracket()
{
    return this->_type == TokenType::RightBracket;
}