#!/usr/bin/env python
# coding: utf-8

# In[4]:

import json

def traiter(fileName,writeFileName):
# Read Json File 
    with open(fileName,"r",encoding="utf-8")as f:
        data = json.load(f)

    # Add label name for each element
    DATA = []
    for i in range(len(data)):
        item = {}
        item["text"]=data[i][0]
        ligne = []
        #print(data[i])
        for x in data[i][1:]:
            elem = {}
            elem["ner"]=x[0]
            elem["start"]=x[1]
            elem["end"]=x[2]
            elem["label"]=x[3].upper()
            ligne.append(elem)
        item["labels"]=ligne
        DATA.append(item)

    # write new files in JSON
    with open(writeFileName,"w",encoding = "utf-8") as f:
        json.dump(DATA,f,ensure_ascii=False)
        
print("Which File to add names of label : ")
fileName = input()
print("Name of the new File : ")
writeFileName = input()
traiter(fileName,writeFileName)

