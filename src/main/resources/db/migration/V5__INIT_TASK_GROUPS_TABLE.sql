create table task_groups(
id bigint primary key auto_increment,
description varchar(100) not null,
done bit null
);
alter table tasks add column task_group_id bigint null;
alter table tasks add foreign key (task_group_id) references task_groups (id);
