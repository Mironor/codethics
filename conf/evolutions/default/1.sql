# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "submitions" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"token" VARCHAR(254) NOT NULL,"snippet1" text NOT NULL,"snippet1Votes" INTEGER NOT NULL,"snippet2" text NOT NULL,"snippet2Votes" INTEGER NOT NULL);

# --- !Downs

drop table "submitions";

