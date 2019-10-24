create table lessons(
_id integer primary key autoincrement not null,
day_id integer not null,
position_id integer not null,
subject_id integer not null,
place text,
foreign key (day_id)references days(_id),
foreign key (subject_id) references subjects(_id)
)