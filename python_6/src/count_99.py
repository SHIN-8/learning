import openpyxl

file_name = input("xlsxファイル名を入力してください。")

#jiko_ichiran.xlsxファイルを開く
wb = openpyxl.load_workbook(filename=file_name)
ws = wb.active

count = 0  #最大事故金額を保存する変数
row = 2         #事故金額を取得する行
while row < 500:
    #トラッカーが99か判定する
    tracker = ws.cell(row=row, column=2).value
    if not tracker == "【99】":
        row = row + 1
        continue
    row = row + 1
    count = count + 1
    


print("99の件数は", count, "件です")