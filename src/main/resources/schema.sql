use helpme_iud;
insert into roles (name, description)
values("ROLE_ADMIN", "Administrator user");
insert into roles (name, description)
values("ROLE_USER", "Normal user");
insert into users (birth_Date, enabled, image, last_name, name, password, social_web, user_name)
values ("2000-02-07", true, "none", "Del Boccio", "Marco", "acertijo21", false, "marco.delboccio@est.iudigital.edu.co");
insert into roles_users(user_id, role_id) values(1, 1);
insert into roles_users(user_id, role_id) values(1, 2);

