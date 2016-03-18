INSERT INTO Symptoms (sName) VALUES ('pain');
INSERT INTO Symptoms (sName) VALUES ('anxiety');
INSERT INTO Symptoms (sName) VALUES ('arrythmia');
INSERT INTO Symptoms (sName) VALUES ('chills');
INSERT INTO Symptoms (sName) VALUES ('clammy skin');
INSERT INTO Symptoms (sName) VALUES ('cloudy urine');
INSERT INTO Symptoms (sName) VALUES ('cold sweat');
INSERT INTO Symptoms (sName) VALUES ('conjunctivitis');
INSERT INTO Symptoms (sName) VALUES ('cough');
INSERT INTO Symptoms (sName) VALUES ('dehydration');
INSERT INTO Symptoms (sName) VALUES ('dizziness');
INSERT INTO Symptoms (sName) VALUES ('dysuria');
INSERT INTO Symptoms (sName) VALUES ('tachypnea');
INSERT INTO Symptoms (sName) VALUES ('fatigue');
INSERT INTO Symptoms (sName) VALUES ('fever');
INSERT INTO Symptoms (sName) VALUES ('hematuria');
INSERT INTO Symptoms (sName) VALUES ('hypotension');
INSERT INTO Symptoms (sName) VALUES ('dyspepsia');
INSERT INTO Symptoms (sName) VALUES ('presyncope');
INSERT INTO Symptoms (sName) VALUES ('syncope');
INSERT INTO Symptoms (sName) VALUES ('malaise');
INSERT INTO Symptoms (sName) VALUES ('rhinitis');
INSERT INTO Symptoms (sName) VALUES ('nausea');
INSERT INTO Symptoms (sName) VALUES ('rhinorrhea');
INSERT INTO Symptoms (sName) VALUES ('sense of incomplete bladder emptying');
INSERT INTO Symptoms (sName) VALUES ('dyspnea');
INSERT INTO Symptoms (sName) VALUES ('smelly urine');
INSERT INTO Symptoms (sName) VALUES ('sneezing');
INSERT INTO Symptoms (sName) VALUES ('pharingitis');
INSERT INTO Symptoms (sName) VALUES ('sweating');
INSERT INTO Symptoms (sName) VALUES ('tachycardia');
INSERT INTO Symptoms (sName) VALUES ('urinary frequency');
INSERT INTO Symptoms (sName) VALUES ('voimiting');
INSERT INTO Symptoms (sName) VALUES ('voimting');
INSERT INTO Symptoms (sName) VALUES ('wheezing');
INSERT INTO Symptoms (sName) VALUES ('dysphagia');
INSERT INTO Symptoms (sName) VALUES ('ataxia');
INSERT INTO Symptoms (sName) VALUES ('bradykinesia');
INSERT INTO Symptoms (sName) VALUES ('impotence');
INSERT INTO Symptoms (sName) VALUES ('bradypnea');
INSERT INTO Symptoms (sName) VALUES ('apnea');
INSERT INTO Symptoms (sName) VALUES ('steatorrhea');
INSERT INTO Symptoms (sName) VALUES ('apraxia');
INSERT INTO Symptoms (sName) VALUES ('tinnitus');
INSERT INTO Symptoms (sName) VALUES ('dysarthria');
INSERT INTO Symptoms (sName) VALUES ('dysgraphia');
INSERT INTO Symptoms (sName) VALUES ('dystonia');
INSERT INTO Symptoms (sName) VALUES ('akinesia');
INSERT INTO Symptoms (sName) VALUES ('alexia');
INSERT INTO Symptoms (sName) VALUES ('chorea');

INSERT INTO Diagnosis (dName) VALUES ('Heart Attack');
INSERT INTO Diagnosis (dName) VALUES ('Pneumonia');
INSERT INTO Diagnosis (dName) VALUES ('Urinary Tract Infection (Upper)');
INSERT INTO Diagnosis (dName) VALUES ('Urinary Tract Infection (Lower)');
INSERT INTO Diagnosis (dName) VALUES ('Acute Upper Respiratory Tract Infection');

INSERT INTO Cases (diagnosisId, age, gender, pain, nausea, voimiting, dizziness, fatigue, presyncope, syncope, clammy_skin, cold_sweat, sweating, dyspepsia, anxiety, arrythmia, dyspnea)
	VALUES (1, 65, 'm', true, true, true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO Cases (diagnosisId, age, gender, cough, pain, fever, chills, dyspnea, tachypnea, wheezing, tachycardia, dehydration, fatigue, malaise, clammy_skin, sweating)
	VALUES (2, 80, 'f', true, true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO Cases (diagnosisId, age, gender, pain, urinary_frequency, dysuria, cloudy_urine, hematuria, smelly_urine, fatigue, fever, malaise, nausea, voimting, hypotension, tachycardia)
	VALUES (3, 35, 'f', true, true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO Cases (diagnosisId, age, gender, pain, urinary_frequency, dysuria, cloudy_urine, hematuria, smelly_urine, sense_incomplete_bladder_emptying, fever)
	VALUES (4, 42, 'f', true, true, true, true, true, true, true, true);
INSERT INTO Cases (diagnosisId, age, gender, pain, cough, chills, fever, fatigue, malaise, dehydration, sweating, rhinitis, rhinorrhea, sneezing, dyspnea, pharingitis, conjunctivitis)
	VALUES (5, 3, 'm', true, true, true, true, true, true, true, true, true, true, true, true, true, true);
