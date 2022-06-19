create table forms (
   id  bigserial not null,
    created_at timestamp not null,
    updated_at timestamp,
    account_id int8,
    background_color varchar(255),
    description varchar(255),
    schema varchar(255),
    text_font varchar(255),
    theme_color varchar(255),
    title varchar(255),
    form_template_id int8 not null,
    primary key (id)
);

create table form_templates (
   id  bigserial not null,
    created_at timestamp not null,
    updated_at timestamp,
    account_id int8,
    background_color varchar(255),
    description varchar(255),
    enabled boolean,
    schema varchar(255),
    text_font varchar(255),
    theme_color varchar(255),
    title varchar(255),
    primary key (id)
);

create table form_template_actions (
   id  bigserial not null,
    action_name varchar(255),
    enabled boolean,
    message varchar(255),
    form_template_id int8 not null,
    primary key (id)
);

create table form_template_emails (
   id  bigserial not null,
    action_name varchar(255),
    attachment varchar(255),
    email_address varchar(255),
    email_body varchar(255),
    email_subject varchar(255),
    enabled boolean,
    from_address varchar(255),
    from_name varchar(255),
    reply_email_address varchar(255),
    form_template_id int8 not null,
    primary key (id)
);

create table users (
   id  bigserial not null,
    created_at timestamp not null,
    updated_at timestamp,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    phone_number varchar(255),
    role varchar(255),
    primary key (id)
);

alter table if exists users
   add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table if exists forms
   add constraint FK9fh5d9yjl6uj31ecsfrkgpfho
   foreign key (form_template_id)
   references form_templates;
alter table if exists form_template_actions
   add constraint FKdedb0jfy2ggi8aopa3uls753
   foreign key (form_template_id)
   references form_templates;
alter table if exists form_template_emails
   add constraint FK7xlm3fa183eh7fr81f1ktu5x6
   foreign key (form_template_id)
   references form_templates;

INSERT INTO users (first_name, last_name, phone_number, email , role , password, created_at)
 VALUES ('Admin', 'Admin', '+3751111111111111', 'admin@test.com', 'ADMIN', '$2a$10$EY83TaZXfzSPEBCKKbiSuu6al5OTRhdRxYfoW3M899BGVWwBqsh26', now());
