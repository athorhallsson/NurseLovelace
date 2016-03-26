#!/usr/bin/env python

with open("painPos.txt") as f_old, open("Fill_painPos.txt", "w") as f_new:
    for line in f_old:
	line = line.replace('\n', '').replace('\r', '')
        f_new.write("INSERT INTO PainPosition (ppName) VALUES ('" + line + "');\n")
