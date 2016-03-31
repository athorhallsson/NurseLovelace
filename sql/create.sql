﻿DROP TABLE IF EXISTS Cases, Diagnosis, SymptomNames, Symptoms, Positions, Pain, PainSx, PainPosition;

CREATE TABLE Diagnosis (
	dId serial,
	dName varchar(100) NOT NULL,
	PRIMARY KEY (dId)
);

CREATE TABLE Positions (
	posId serial,
	head boolean,
	temporal boolean,
	occipital boolean,
	frontal boolean,
	neck boolean,
	maxilar_sinuses boolean,
	frontal_sinuses boolean,
	throat boolean,
	right_ear boolean,
	left_ear boolean,
	jaw boolean,
	chest boolean,
	right_chest boolean,
	left_chest boolean,
	right_shoulder boolean,
	left_shoulder boolean,
	right_shoulder_blade boolean,
	left_shoulder_blade boolean,
	between_shoulder_blades boolean,
	right_arm boolean,
	left_arm boolean,
	abdomen boolean,
	epigastrium boolean,
	periumbilical boolean,
	right_upper_quadrant boolean,
	left_upper_quadrant boolean,
	right_lower_quadrant boolean,
	left_lower_quadrant boolean,
	right_flank boolean,
	left_flank boolean,
	suprapubic boolean,
	upper_back boolean,
	lower_back boolean,
	right_costovertebral boolean,
	left_costovertebral boolean,
	pelvic boolean,
	right_groin boolean,
	left_groin boolean,
	right_buttock boolean,
	left_buttock boolean,
	anal boolean,
	testicular boolean,
	vaginal boolean,
	right_thigh boolean,
	left_thigh boolean,
	right_knee boolean,
	left_knee boolean,
	right_kalf boolean,
	left_kalf boolean,
	right_foot boolean,
	left_foot boolean,
	PRIMARY KEY (posId)
);

CREATE TABLE Pain (
	pId serial, 
	positionId integer NOT NULL,
	referred_pain_position integer NOT NULL,
	sharp boolean,
	dull boolean,
	burning boolean,
	clenching boolean,
	pleuritic boolean,
	colicky boolean,
	constant boolean,
	on_rest boolean,
	on_exertion boolean,
	dysuria boolean,
	dysparenunia boolean,
	four_hours boolean,
	eight_hours boolean,
	one_day boolean,
	two_days boolean,
	one_week boolean,
	one_month boolean,
	six_months boolean,
	better_with_rest boolean,
	better_with_lying_still boolean,
	better_with_moving_around boolean,
	worse_with_breathing boolean,
	worse_with_eating boolean,
	worse_with_moving_around boolean,
	worse_with_bending_forward boolean,
	FOREIGN KEY (positionId) REFERENCES Positions (posId),
	FOREIGN KEY (referred_pain_position) REFERENCES Positions (posId),
	PRIMARY KEY (pId)
);

CREATE TABLE Symptoms (
	sxId serial,
	palpitations boolean,
	chills boolean,
	clammy_skin boolean,
	cloudy_urine boolean,
	cold_sweat boolean,
	conjunctivitis boolean,
	cough boolean,
	dry_cough boolean,
	productive_cough boolean,
	hemoptysis boolean,
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
	sense_of_incomplete_bladder_emptying boolean,
	dyspnea boolean,
	smelly_urine boolean,
	sneezing boolean,
	diaphoresis boolean,
	tachycardia boolean,
	polyuria boolean,
	vomiting boolean,
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
	agraphia boolean,
	dystonia boolean,
	akinesia boolean,
	alexia boolean,
	chorea boolean,
	loss_of_appetite boolean,
	diarrhea boolean,
	abdominal_distension boolean,
	weight_loss boolean,
	melena boolean,
	hematochesia boolean,
	bloating boolean,
	indigestion boolean,
	jaundice boolean,
	light_colored_stools boolean,
	dark_urine boolean,
	shortness_of_breath boolean,
	lightheadedness boolean,
	constipation boolean,
	PRIMARY KEY (sxId)
);

CREATE TABLE Cases (
	caseId serial,
	diagnosisId integer,
	age integer NOT NULL,
	gender char NOT NULL, 
	majorSx integer NOT NULL,
	minorSx integer NOT NULL,
	painId integer NOT NULL, 
	FOREIGN KEY (diagnosisId) REFERENCES Diagnosis (dId),
	FOREIGN KEY (majorSx) REFERENCES Symptoms (sxId),
	FOREIGN KEY (minorSx) REFERENCES Symptoms (sxId),
	FOREIGN KEY (painId) REFERENCES Pain (pId),
	PRIMARY KEY (caseId)
);

CREATE TABLE SymptomNames (
	sId serial,
	sName varchar(100) NOT NULL,
	PRIMARY KEY (sId)
);

CREATE TABLE PainSx (
	psId serial,
	psName varchar(100) NOT NULL,
	PRIMARY KEY (psId)
);

CREATE TABLE PainPosition (
	ppId serial,
	ppName varchar(100) NOT NULL,
	PRIMARY KEY (ppId)
);
