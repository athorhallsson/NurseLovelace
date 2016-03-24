#!/usr/bin/env python

with open("Symptoms50.txt") as f_old, open("Fill.txt", "w") as f_new:
    for line in f_old:
	line = line.replace('\n', '').replace('\r', '')
        f_new.write("INSERT INTO Symptoms (sName) VALUES ('" + line + "');\n")
