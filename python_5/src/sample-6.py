import openpyxl

wb = openpyxl.load_workbook('myworkbook.xlsx')

ws = wb['Sheet']  # ワークシートを指定
print(f'sheet name: {ws.title}')  # sheet name: Sheet