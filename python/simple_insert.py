#!/usr/bin/env python

with open("old.txt") as f_old, open("new.txt", "w") as f_new:
    for line in f_old:
	line = line.replace('\n', '').replace('\r', '')
        f_new.write("INSERT INTO Symptoms VALUES ('" + line + "')\n")
