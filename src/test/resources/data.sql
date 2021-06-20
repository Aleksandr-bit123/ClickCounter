create table if not exists t_counter
(
    id bigserial not null default 1,
    counter bigint default 0,
    constraint t_counter_pk primary key (id)
);

create unique index if not exists t_counter_id_uindex
	on public.t_counter (id);
