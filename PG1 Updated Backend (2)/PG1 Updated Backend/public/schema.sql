
    create table complaints (
        department_id bigint not null,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;

    create table complaints (
        department_id bigint,
        district_id bigint not null,
        id bigserial not null,
        taluk_id bigint not null,
        address varchar(255),
        email varchar(255),
        file_path varchar(255),
        location varchar(255),
        name varchar(255),
        phone varchar(255),
        pincode varchar(255),
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    create table departments (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table districts (
        id bigserial not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table taluks (
        district_id bigint not null,
        id bigserial not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table users (
        id bigserial not null,
        password varchar(8),
        address varchar(255),
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        phone_number varchar(255),
        role varchar(255) not null,
        status varchar(255) not null check (status in ('PENDING','RESOLVED','REJECT','ASSIGNED','ADMIN_VERIFY')),
        primary key (id)
    );

    alter table if exists complaints 
       add constraint FKrtes9yyruyvj69lxh3c9k05yf 
       foreign key (department_id) 
       references departments;

    alter table if exists complaints 
       add constraint FK2868shpbd2harskn9jcb4rkl0 
       foreign key (district_id) 
       references districts;

    alter table if exists complaints 
       add constraint FKioux63rfkx409hore4mibt7jn 
       foreign key (taluk_id) 
       references taluks;

    alter table if exists taluks 
       add constraint FKpiqc4njel8t6doxr1cfqkvowc 
       foreign key (district_id) 
       references districts;
