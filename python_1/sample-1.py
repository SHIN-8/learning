
def echo(message):
    """
    メッセージを出力します
    
    Args:
        message: メッセージ文字列

    Returns:
        None

    Raises:
        No Throw any Exceptions
    
    """
    print(message)

#メイン
if __name__ == '__main__':
    message = input('Please input a message')
    count =0
    while count < 3:
        echo(message)
        count = count + 1