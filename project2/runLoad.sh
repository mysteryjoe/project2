#!/bin/bash
chmod a+x runLoad.sh
# Drop the tables if exist
mysql CS144 < drop.sql

# Create the table
mysql CS144 < create.sql

# Compile and run the parser
ant
ant run-all

# Load the data
mysql CS144 < load.sql

# clean all .txt temporary file 
rm *.txt;
rm -r bin

# mysql CS144 < queries.sql