drop schema if exists twitter;

-- -----------------------------------------------------
-- Schema twitter
-- -----------------------------------------------------
create schema if not exists twitter;
use twitter;

-- -----------------------------------------------------
-- Table followers
-- -----------------------------------------------------
drop table if exists followers;

create table if not exists followers (
  user_id bigint not null,
  follows_id bigint not null,
  constraint pk_followers primary key (user_id, follows_id)
);

-- -----------------------------------------------------
-- Table tweets
-- -----------------------------------------------------
drop table if exists tweets;

create table if not exists tweets (
  tweet_id bigint primary key auto_increment,
  user_id bigint not null,
  tweet_ts datetime not null default current_timestamp,
  tweet_text varchar(140) not null
);