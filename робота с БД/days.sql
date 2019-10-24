CREATE TABLE days (
	_id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	day_name	TEXT NOT NULL,
	is_school_day	NUMERIC NOT NULL,
	jingle_type_id integer,
	foreign key (jingle_type_id) references jingle_type(_id)
);