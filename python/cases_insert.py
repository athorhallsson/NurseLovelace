#!/usr/bin/env python

ages = range(35, 55)

genders = ["M", "F"]

with open("old.txt") as f_old, open("new.txt", "w") as f_new:
    for line in f_old:
    	count = len(line.split())
    	line = line.replace('\n', '').replace('\r', '')
    	for age in ages:	
        	str = "INSERT INTO Cases (" + line + ") VALUES (true"
        	for _ in range(3, count):
        		str += ", true"
        	str += ");\n"
        	f_new.write(str)
