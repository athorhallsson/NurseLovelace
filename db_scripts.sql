DROP TABLE IF EXISTS CASES;

CREATE TABLE Cases (
	caseId serial,
	fever boolean NOT NULL DEFAULT false,
	cough boolean NOT NULL DEFAULT false,
	diarrhea boolean NOT NULL DEFAULT false,
	nausea boolean NOT NULL DEFAULT false,
	bloodystool boolean NOT NULL DEFAULT false,
	bloodyvomit boolean NOT NULL DEFAULT false,
	diagnosis varchar(100) NOT NULL
);

INSERT INTO Cases (fever, cough, diarrhea, nausea, bloodystool, bloodyvomit, diagnosis)
VALUES (true, true, true, true, true, true, 'Everything');

INSERT INTO Cases (fever, cough, diarrhea, nausea, bloodystool, bloodyvomit, diagnosis)
VALUES (true, true, false, false, true, false, 'Sickness');

SELECT * FROM Cases