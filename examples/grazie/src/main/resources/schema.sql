create table if not exists users (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name character varying (253) NOT NULL,
	phone character varying(40) NOT NULL,
	email character varying(253),
	password character varying(155) NOT NULL,
	clean character varying (251),
	image_uri character varying (251),
	stripe_account_id character varying (250),
	stripe_customer_id character varying (251),
	activated boolean default false,
	description text,
	guid character varying(155),
	uuid character varying(255),
	date_created bigint default 0
);

create table if not exists tips (
	id bigint PRIMARY KEY AUTO_INCREMENT,
    guid character varying(250) default '',
	patron_id bigint REFERENCES users(id),
	recipient_id bigint REFERENCES users(id),
    amount decimal default 0.0,
    amount_cents bigint default 0,
    charge_id character varying(201),
    subscription_id character varying(201),
    processed boolean default false,
    tip_date bigint default 0,
    email character varying (250),
    recurring boolean default false,
    cancelled boolean default false
);

create table products(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    nickname character varying (255),
	stripe_id character varying (251)
);

create table prices(
	id bigint PRIMARY KEY AUTO_INCREMENT,
	stripe_id character varying (250),
    amount decimal default 0.0,
    nickname character varying (255),
	product_id bigint NOT NULL REFERENCES products(id)
);

create table if not exists roles (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name character varying(65) NOT NULL UNIQUE
);

create table if not exists user_permissions(
	user_id bigint REFERENCES users(id),
	permission character varying(55)
);

create table if not exists user_roles(
	role_id bigint NOT NULL REFERENCES roles(id),
	user_id bigint NOT NULL REFERENCES users(id)
);

create table if not exists towns (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name character varying(250) NOT NULL UNIQUE,
	latitude character varying (51),
	longitude character varying (51)
);

create table if not exists businesses (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	name character varying (254),
	address character varying (254),
	phone character varying(40),
	latitude character varying (51),
	longitude character varying (51),
	active boolean default true,
	town_id bigint not null references towns(id)
);

create table if not exists user_businesses (
	id bigint PRIMARY KEY AUTO_INCREMENT,
	active boolean default true,
	date_started bigint default 0,
	years bigint default 0,
	position character varying (250),
	part_time boolean default false,
	user_id bigint NOT NULL REFERENCES users(id),
	business_id bigint NOT NULL REFERENCES businesses(id)
);

create table if not exists signup_requests(
	id bigint PRIMARY KEY AUTO_INCREMENT,
    name character varying (254),
    address character varying (254)
);
