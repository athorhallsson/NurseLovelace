#!/usr/bin/env python

import sys
import random

# Disease names
diseases = ["appendicitis", "biliary_colic", "cholecystitis", "choledocholithiasis"]

for disease in diseases:
    # Initialize random number generator
    random.seed("this is a seed")

    f_old = open("../cases/" + disease + ".txt")
    f_new = open("../sql/fill_" + disease + ".sql", "w")

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

    for ageIndex in range(99):
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
        query += str(diseases.index(disease) + 1) + ", "
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
