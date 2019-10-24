create table jingle(
jingle_id integer primary key autoincrement not null,
position_id integer not null,
time_begin text not null,
time_end text not null,
jingle_type_id integer not null,
foreign key(jingle_type_id)references jingle_type(jingle_type_id)

)