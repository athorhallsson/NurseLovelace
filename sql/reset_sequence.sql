﻿INSERT INTO Positions VALUES (0)
INSERT INTO Symptoms VALUES (0)
INSERT INTO Pain VALUES (0, 0, 0)

ALTER SEQUENCE positions_posid_seq RESTART WITH 10000;
ALTER SEQUENCE symptoms_sxid_seq RESTART WITH 10000;
ALTER SEQUENCE cases_caseid_seq RESTART WITH 10000;
ALTER SEQUENCE pain_pid_seq RESTART WITH 10000;