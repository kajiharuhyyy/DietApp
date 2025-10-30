create table profiles (
  id bigserial primary key,
  diet_type varchar(32) not null,
  height_cm int not null,
  weight_kg double precision not null,
  age int not null,
  sex varchar(16) not null,
  activity_factor double precision not null,
  override_energy_kcal int,
  ckd_stage int
);

create table daily_targets (
  id bigserial primary key,
  target_date date not null,
  profile_id bigint not null references profiles(id) on delete cascade,
  energy_kcal int,
  carb_g double precision,
  protein_g double precision,
  fat_g double precision,
  fiber_g double precision,
  sodium_g double precision,
  potassium_mg double precision,
  phosphorus_mg double precision,
  unique (profile_id, target_date)
);
