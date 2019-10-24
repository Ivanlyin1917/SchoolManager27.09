CREATE TABLE Subjects (
	_id	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	Subject_name	text NOT NULL,
	Subject_type_id	integer NOT NULL,
	FOREIGN KEY(Subject_type_id) REFERENCES Subject_type(_id)
);
