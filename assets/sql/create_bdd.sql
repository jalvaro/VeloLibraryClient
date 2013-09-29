--Android requires a table named 'android_metadata' with a 'locale' column
--DROP TABLE "android_metadata";
create table if not exists "android_metadata" ("locale" TEXT DEFAULT 'es_ES');
--INSERT INTO "android_metadata" VALUES ('es_ES');
-- Only for update
 
create table if not exists "fleets" ("gid" INTEGER primary key autoincrement ,"org" TEXT, "cou" TEXT, "lat" DOUBLE, "lng" DOUBLE, "zoom" INTEGER, "desc" TEXT, "web" TEXT, "reg" TEXT, "gmt" TEXT, "loc" TEXT, "timestamp" TEXT DEFAULT CURRENT_TIMESTAMP);
create table if not exists "stations" ("gid" INTEGER primary key autoincrement, "wcom" TEXT, "disp" INTEGER, "lat" DOUBLE, "lng" DOUBLE, "tc" INTEGER, "ac" INTEGER, "ap" INTEGER, "ab" INTEGER, "id" INTEGER, "name" TEXT, "timestamp" TEXT DEFAULT CURRENT_TIMESTAMP);
create table if not exists "favourite_stations" ("gid" INTEGER primary key autoincrement, "id" INTEGER, "name" TEXT, "timestamp" TEXT DEFAULT CURRENT_TIMESTAMP);