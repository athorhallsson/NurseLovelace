#!/usr/bin/env python

import sys
import random

# Take command line argument for file name
dName = str(sys.argv[1])
dId = int(sys.argv[2])

# Initialize random number generator
random.seed(dId)

f_old = open(dName + ".txt")
f_new = open("fill_" + dName + ".txt", "w")

lines = f_old.readlines()

diagnosis = lines[0]
malePercent = int(lines[1])
minAge = int(lines[2])
maxAge = int(lines[3])
majorSymptoms = lines[4].split()
minorSymptoms = lines[5].split()

for _ in range(50):
    age = random.randint(minAge, maxAge)
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
	query += str(age) + ", "
	query +=  gender + ", "

	for _ in majorSymptoms:
		query += "true, "
    for _ in minorSymptoms:
        query += "true, "
    # delete last two characters
    query = query[:-2]
    query += ");\n"
    f_new.write(query)
        	

