DROP TABLE IF EXISTS Cases, Diagnois, Symptoms;

CREATE TABLE Cases (
	caseId serial,
	pain boolean, 
	anxiety boolean,
	arrythmia boolean,
	chills boolean,
	clammy_skin boolean,
	cloudy_urine boolean,
	cold_sweat boolean,
	conjunctivitis boolean,
	cough boolean,
	dehydration boolean,
	dizziness boolean,
	dysuria boolean,
	tachypnea boolean,
	fatigue boolean,
	fever boolean,
	hematuria boolean,
	hypotension boolean,
	dyspepsia boolean,
	presyncope boolean,
	syncope boolean,
	malaise boolean,
	rhinitis boolean,
	nausea boolean,
	rhinorrhea boolean,
	sense_incomplete_bladder_emptying boolean,
	dyspnea boolean,
	smelly_urine boolean,
	sneezing boolean,
	pharingitis boolean,
	sweating boolean,
	tachycardia boolean,
	urinary_frequency boolean,
	voimiting boolean,
	voimting boolean,
	wheezing boolean,
	dysphagia boolean,
	ataxia boolean,
	bradykinesia boolean,
	impotence boolean,
	bradypnea boolean,
	apnea boolean,
	steatorrhea boolean,
	apraxia boolean,
	tinnitus boolean,
	dysarthria boolean,
	dysgraphia boolean,
	dystonia boolean,
	akinesia boolean,
	alexia boolean,
	chorea boolean
);

CREATE TABLE Diagnosis (
	dId serial,
	dName varchar(100) NOT NULL
);

CREATE TABLE Symptoms (
	sId serial,
	sName varchar(100) NOT NULL
);