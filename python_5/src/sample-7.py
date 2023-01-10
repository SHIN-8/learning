import openpyxl

wb = openpyxl.load_workbook('myworkbook.xlsx')

ws = wb.active

print(ws.cell(column=1, row=1).value)