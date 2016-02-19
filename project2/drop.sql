--drop the table that reference other tables's key first
DROP TABLE IF EXISTS Belong;
DROP TABLE IF EXISTS Bid;
--, then drop the referenced table
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS User;