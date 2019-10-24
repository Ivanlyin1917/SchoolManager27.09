CREATE TABLE Hobby (
	_id	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	Hobby_name	text NOT NULL,
	day_id	integer NOT NULL,
	hobby_time	text NOT NULL,
	Hobby_place	text,
	FOREIGN KEY(day_id) REFERENCES Days(_id)
);
