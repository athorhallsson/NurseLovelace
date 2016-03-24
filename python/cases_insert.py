#!/usr/bin/env python

import sys
import random

# Take command line argument for file name
dName = str(sys.argv[1])
dId = int(sys.argv[2])

# Initialize random number generator
random.seed(dName)

f_old = open(dName + ".txt")
f_new = open("fill_" + dName + ".txt", "w")

lines = f_old.readlines()

diagnosis = lines[0]
majorSymptoms = lines[1].split()
minorSymptoms = lines[2].split()
malePercent = int(lines[24])

# Make age list
ages = []
min = 0
for n in range(3, 23):
	for c in range(int(lines[n])):
        	ages.append(random.randint(min + 1, min + 5))
    	min += 5

for ageIndex in range(100):
	randomGender = random.randint(1, 100)
	if randomGender <= malePercent:
       		gender = "'M'"
    	else:
        	gender = "'F'"
    	query = "INSERT INTO Cases (diagnosisId, age, gender, "
    	for symptom in majorSymptoms:
       		query += symptom + ", "
    	for symptom2 in minorSymptoms:
		query += symptom2 + ", "
	# delete last two characters
	query = query[:-2]

    	query += ") VALUES ("
    	query += str(dId) + ", "
    	query += str(ages[ageIndex]) + ", "
    	query +=  gender + ", "

    	for _ in majorSymptoms:
		rand = random.randint(1, 10)
		if rand is 10:
			query += "false, "
		else:
			query += "true, "
    	for _ in minorSymptoms:
		rand = random.randint(1, 10)
		if rand is 10:
			query += "false, "
		else:
			query += "true, "
    	# delete last two characters
    	query = query[:-2]
    	query += ");\n"
    	f_new.write(query)
