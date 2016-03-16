INSERT INTO Symptoms VALUES ('pain');
INSERT INTO Symptoms VALUES ('anxiety');
INSERT INTO Symptoms VALUES ('arrythmia');
INSERT INTO Symptoms VALUES ('chills');
INSERT INTO Symptoms VALUES ('clammy skin');
INSERT INTO Symptoms VALUES ('cloudy urine');
INSERT INTO Symptoms VALUES ('cold sweat');
INSERT INTO Symptoms VALUES ('conjunctivitis');
INSERT INTO Symptoms VALUES ('cough');
INSERT INTO Symptoms VALUES ('dehydration');
INSERT INTO Symptoms VALUES ('dizziness');
INSERT INTO Symptoms VALUES ('dysuria');
INSERT INTO Symptoms VALUES ('tachypnea');
INSERT INTO Symptoms VALUES ('fatigue');
INSERT INTO Symptoms VALUES ('fever');
INSERT INTO Symptoms VALUES ('hematuria');
INSERT INTO Symptoms VALUES ('hypotension');
INSERT INTO Symptoms VALUES ('dyspepsia');
INSERT INTO Symptoms VALUES ('presyncope');
INSERT INTO Symptoms VALUES ('syncope');
INSERT INTO Symptoms VALUES ('malaise');
INSERT INTO Symptoms VALUES ('rhinitis');
INSERT INTO Symptoms VALUES ('nausea');
INSERT INTO Symptoms VALUES ('rhinorrhea');
INSERT INTO Symptoms VALUES ('sense of incomplete bladder emptying');
INSERT INTO Symptoms VALUES ('dyspnea');
INSERT INTO Symptoms VALUES ('smelly urine');
INSERT INTO Symptoms VALUES ('sneezing');
INSERT INTO Symptoms VALUES ('pharingitis');
INSERT INTO Symptoms VALUES ('sweating');
INSERT INTO Symptoms VALUES ('tachycardia');
INSERT INTO Symptoms VALUES ('urinary frequency');
INSERT INTO Symptoms VALUES ('voimiting');
INSERT INTO Symptoms VALUES ('voimting');
INSERT INTO Symptoms VALUES ('wheezing');
INSERT INTO Symptoms VALUES ('dysphagia');
INSERT INTO Symptoms VALUES ('ataxia');
INSERT INTO Symptoms VALUES ('bradykinesia');
INSERT INTO Symptoms VALUES ('impotence');
INSERT INTO Symptoms VALUES ('bradypnea');
INSERT INTO Symptoms VALUES ('apnea');
INSERT INTO Symptoms VALUES ('steatorrhea');
INSERT INTO Symptoms VALUES ('apraxia');
INSERT INTO Symptoms VALUES ('tinnitus');
INSERT INTO Symptoms VALUES ('dysarthria');
INSERT INTO Symptoms VALUES ('dysgraphia');
INSERT INTO Symptoms VALUES ('dystonia');
INSERT INTO Symptoms VALUES ('akinesia');
INSERT INTO Symptoms VALUES ('alexia');
INSERT INTO Symptoms VALUES ('chorea');

INSERT INTO Diagnosis VALUES ('Heart Attack');
INSERT INTO Diagnosis VALUES ('Pneumonia');
INSERT INTO Diagnosis VALUES ('Urinary Tract Infection (Upper)');
INSERT INTO Diagnosis VALUES ('Urinary Tract Infection (Lower)');
INSERT INTO Diagnosis VALUES ('Acute Upper Respiratory Tract Infection');

INTO Cases (diagnosisId, age, gender, pain, nausea, voimiting, dizziness, fatigue, presyncope, syncope, clammy skin, cold sweat, sweating, dyspepsia, anxiety, arrythmia, dyspnea)
	VALUES (1, 65, 'm', true, true, true, true, true, true, true, true, true, true, true, true, true, true);
INTO Cases (diagnosisId, age, gender, cough, pain, fever, chills, dyspnea, tachypnea, wheezing, tachycardia, dehydration, fatigue, malaise, clammy skin, sweating)
	VALUES (2, 80, 'f', true, true, true, true, true, true, true, true, true, true, true, true, true);
INTO Cases (diagnosisId, age, gender, pain, urinary frequency, dysuria, cloudy urine, hematuria, smelly urine, fatigue, fever, malaise, nausea, voimting, hypotension, tachycardia)
	VALUES (3, 35, 'f', true, true, true, true, true, true, true, true, true, true, true, true, true);
INTO Cases (diagnosisId, age, gender, pain, urinary frequency, dysuria, cloudy urine, hematuria, smelly urine, sense of incomplete bladder emptying, fever)
	VALUES (4, 42, 'f', true, true, true, true, true, true, true, true);
INTO Cases (diagnosisId, age, gender, pain, cough, chills, fever, fatigue, malaise, dehydration, sweating, rhinitis, rhinorrhea, sneezing, dyspnea, pharingitis, conjunctivitis)
	VALUES (5, 3, 'm', true, true, true, true, true, true, true, true, true, true, true, true, true, true);