import csv
import ast
file = open("C:\\Users\\xaris\\Downloads\\keywords.csv","r",encoding="utf8")
csvreader = csv.reader(file)
header = next(csvreader)
file1 = open("C:\\Users\\xaris\\Downloads\\movieid_keywordsid.csv","w",encoding="utf8")
file2 = open("C:\\Users\\xaris\\Downloads\\keywords_set.csv","w",encoding="utf8")
csvwriter1 = csv.writer(file1,lineterminator="\n")
csvwriter2 = csv.writer(file2,lineterminator="\n")
row = ['movie_id','keyword_id']
csvwriter1.writerow(row)
row = ['id','name']
csvwriter2.writerow(row)
keyword_set = set()
keyword_dict = dict()
setItems = len(keyword_set)
for row in csvreader:
    y=row[0]
    data = ast.literal_eval(row[1])
    for k in data:        
        newrow = [y,k['id']]
        csvwriter1.writerow(newrow)
        keyword_set.add(k['id'])
        if len(keyword_set) != setItems:
            setItems = len(keyword_set)
            newrow = [k['id'],k['name']]
            csvwriter2.writerow(newrow)        
file.close()