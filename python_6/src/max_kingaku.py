import openpyxl

#jiko_ichiran.xlsxファイルを開く
wb = openpyxl.load_workbook('jiko_ichiran.xlsx')
ws = wb['incident_list']

max_kingaku = 0  #最大事故金額を保存する変数
row = 2          #事故金額を取得する行
while row < 500:
    #5列目に事故金額のセルがあるので、行を更新しながら事故金額を取得する
    #valueは文字列なので整数に変換
    kingaku = int(ws.cell(row=row, column=5).value)

    #最大事故金額より大きいか判定
    if max_kingaku < kingaku:
        max_kingaku = kingaku
    row = row + 1

print("最大事故金額は", max_kingaku, "です")