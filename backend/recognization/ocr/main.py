import easyocr

reader = easyocr.Reader(['en','pt'])
result = reader.readtext('resources/test1.jpg')

for (bbox, text, prob) in result:
    print(f'{text} ({prob:.2f})')