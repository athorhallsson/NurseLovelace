#!/usr/bin/env python

import sys
import random

# Initialize random number generator
random.seed("this is a seed")

# Set the error rate %
errorRate = 10

# Disease names
diseases = ["appendicitis", "biliary_colic", "cholecystitis", "choledocholithiasis", "diverticulitis", "uti_upper", "uti_lower", "nephrolithiasis", "acute_upper_rti", "heart_attack", "pneumonia", "crohns_disease", "ulcerative_colitis"]

# Create the output script
f_new = open("../sql/fill_all" + ".sql", "w")

# For each disease
for i in range(len(diseases)):
    f_old = open("../cases/" + diseases[i] + ".txt")
    
    diagnosisId = i + 1
    
    lines = f_old.readlines()
    
    # Make age list
    ages = []
    min = 0
    for n in range(6, 27):
        for c in range(int(lines[n])):
            ages.append(random.randint(min + 1, min + 5))
        min += 5
    
    # Diagnosis
    diagnosis = lines[0].rstrip()
    diagnosisQuery = "Insert INTO Diagnosis (dId, dname) Values (" + str(diagnosisId) + ", '" + diagnosis + "');\n"
    f_new.write(diagnosisQuery)
    
    
    for j in range (1, 101):
        id = 100 * i + j
        
        # Position
        pos = lines[1].split()
        posQuery = "INSERT INTO Positions (posId, "
        for p in pos:
            posQuery += p + ", "
        posQuery = posQuery[:-2]
        posQuery += ") VALUES (" + str(2 * id) + ", "
        for _ in pos:
            rand = random.randint(1, 100)
            if rand < errorRate:
                posQuery += "false, "
            else:
                posQuery += "true, "
        posQuery = posQuery[:-2]
        posQuery += ");\n"
        f_new.write(posQuery)

        # Referred position
        referredPos = lines[2].split()
        rposQuery = "INSERT INTO Positions (posId, "
        for rp in referredPos:
            rposQuery += rp + ", "
        rposQuery = rposQuery[:-2]
        rposQuery += ") VALUES (" + str(2 * id + 1) + ", "
        for _ in referredPos:
            rand = random.randint(1, 100)
            if rand < errorRate:
                rposQuery += "false, "
            else:
                rposQuery += "true, "
        rposQuery = rposQuery[:-2]
        rposQuery += ");\n"
        f_new.write(rposQuery)
        
        # Pain
        pains = lines[3].split()
        painQuery = "INSERT INTO Pain (pid, positionId, referred_pain_position, "
        for pain in pains:
            painQuery += pain + ", "
        painQuery = painQuery[:-2]
        painQuery += ") VALUES (" + str(2 * id) + ", " + str(2 * id) + ", " + str(2 * id + 1) + ", "
        for _ in pains:
            rand = random.randint(1, 100)
            if rand < errorRate:
                painQuery += "false, "
            else:
                painQuery += "true, "
        painQuery = painQuery[:-2]
        painQuery += ");\n"
        f_new.write(painQuery)
        
        # Major symptoms
        majorSymptoms = lines[4].split()
        majorQuery = "INSERT INTO Symptoms (sxId, "
        for symptom in majorSymptoms:
            majorQuery += symptom + ", "
        majorQuery = majorQuery[:-2]
        majorQuery += ") VALUES (" + str(2 * id) + ", "
        for _ in majorSymptoms:
            rand = random.randint(1, 100)
            if rand < errorRate:
                majorQuery += "false, "
            else:
                majorQuery += "true, "
        majorQuery = majorQuery[:-2]
        majorQuery += ");\n"
        f_new.write(majorQuery)

        # Minor symptoms
        minorSymptoms = lines[5].split()
        minorQuery = "INSERT INTO Symptoms (sxId, "
        for symptom2 in minorSymptoms:
            minorQuery += symptom2 + ", "
        minorQuery = minorQuery[:-2]
        minorQuery += ") VALUES (" + str(2 * id + 1) + ", "
        for _ in minorSymptoms:
            rand = random.randint(1, 100)
            if rand < errorRate:
                minorQuery += "false, "
            else:
                minorQuery += "true, "
        minorQuery = minorQuery[:-2]
        minorQuery += ");\n"
        f_new.write(minorQuery)

        # Cases
        malePercent = int(lines[26])
        randomGender = random.randint(1, 100)
        if randomGender <= malePercent:
            gender = "'M'"
        else:
            gender = "'F'"
        caseQuery = "INSERT INTO Cases (diagnosisId, age, gender, majorSx, minorSx, painId) VALUES ("
        caseQuery += str(diagnosisId) + ", "
        caseQuery += str(ages[j]) + ", "
        caseQuery +=  gender + ", "
        caseQuery += str(2 * id) + ", "
        caseQuery += str(2 * id + 1) + ", "
        caseQuery += str(2 * id)
        caseQuery += ");\n"
        f_new.write(caseQuery)
