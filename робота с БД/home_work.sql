CREATE TABLE homeworks (
	_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	date_hw	TEXT NOT NULL,
	subject_id	integer NOT NULL,
	Homework	TEXT NOT NULL,
	FOREIGN KEY("subject_id") REFERENCES subjects(_id)
);
