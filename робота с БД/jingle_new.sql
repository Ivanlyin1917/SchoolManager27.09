CREATE TABLE jingle (
	_id	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	position_id	integer NOT NULL,
	time_begin	text NOT NULL,
	time_end	text NOT NULL,
	jingle_type_id	integer,
	FOREIGN KEY(jingle_type_id) REFERENCES jingle_type(_id)
);
