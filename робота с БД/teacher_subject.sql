create table teacher_subject(
_id integer not null primary key autoincrement,
teacher_id not null,
subject_id not null,
foreign key(teacher_id) references teachers(_id),
foreign key(subject_id) references subjects(_id)
)