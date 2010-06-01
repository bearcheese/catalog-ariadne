
    alter table discs 
        drop 
        foreign key FK5B2A57EC0C11B09;

    alter table discs 
        drop 
        foreign key FK5B2A57E8708A981;

    alter table itemprops 
        drop 
        foreign key FK814F423D80AFB745;

    alter table items 
        drop 
        foreign key FK5FDE7C06AD37B77;

    drop table if exists categories;

    drop table if exists discs;

    drop table if exists itemprops;

    drop table if exists items;

    drop table if exists types;

    create table categories (
        id bigint not null auto_increment,
        name varchar(20),
        description varchar(100),
        primary key (id)
    );

    create table discs (
        id bigint not null auto_increment,
        name varchar(50) unique,
        volumeName varchar(20) unique,
        categoryid bigint not null,
        typeid bigint not null,
        created date,
        comment text,
        primary key (id)
    );

    create table itemprops (
        pid bigint not null,
        mvalue varchar(255),
        mkey varchar(255) not null,
        primary key (pid, mkey)
    );

    create table items (
        id bigint not null auto_increment,
        name varchar(150),
        path text,
        length bigint,
        discid bigint not null,
        primary key (id)
    );

    create table types (
        id bigint not null auto_increment,
        name varchar(10),
        primary key (id)
    );

    alter table discs 
        add index FK5B2A57EC0C11B09 (categoryid), 
        add constraint FK5B2A57EC0C11B09 
        foreign key (categoryid) 
        references categories (id);

    alter table discs 
        add index FK5B2A57E8708A981 (typeid), 
        add constraint FK5B2A57E8708A981 
        foreign key (typeid) 
        references types (id);

    alter table itemprops 
        add index FK814F423D80AFB745 (pid), 
        add constraint FK814F423D80AFB745 
        foreign key (pid) 
        references items (id);

    alter table items 
        add index FK5FDE7C06AD37B77 (discid), 
        add constraint FK5FDE7C06AD37B77 
        foreign key (discid) 
        references discs (id);
