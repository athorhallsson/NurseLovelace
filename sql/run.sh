#!/bin/bash

psql \
    -X \
    -U postgres \
    -h localhost \
    -f create.sql \
    --set AUTOCOMMIT=on \
    --set ON_ERROR_STOP=off \
    diagnosisdb

psql \
    -X \
    -U postgres \
    -h localhost \
    -f fill_all.sql \
    --set AUTOCOMMIT=on \
    --set ON_ERROR_STOP=off \
    diagnosisdb

psql \
    -X \
    -U postgres \
    -h localhost \
    -f fill_names.sql \
    --set AUTOCOMMIT=on \
    --set ON_ERROR_STOP=off \
    diagnosisdb


exit 0
