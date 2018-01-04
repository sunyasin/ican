CREATE TABLE public.subj (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('subj_id_seq'::regclass),
  name CHARACTER VARYING(128) NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE,
  address CHARACTER VARYING(256) DEFAULT NULL,
  type INTEGER NOT NULL,
  login CHARACTER VARYING(16) NOT NULL,
  pass CHARACTER VARYING(128) NOT NULL,
  email CHARACTER VARYING(64) DEFAULT NULL,
  updated TIMESTAMP WITHOUT TIME ZONE,
  description CHARACTER VARYING(2048) DEFAULT NULL,
  contacts CHARACTER VARYING(256) DEFAULT NULL,
  auto_bonus_active BOOLEAN DEFAULT true,
  partner_id INTEGER, -- ид партнера
  partner_code CHARACTER VARYING(16),
  picture CHARACTER VARYING(24)
);
CREATE UNIQUE INDEX subj_login_unique_idx ON subj USING BTREE (login);
COMMENT ON COLUMN public.subj.partner_id IS 'ид партнера';

CREATE TABLE bill (
  id      SERIAL NOT NULL PRIMARY KEY,
  created TIMESTAMP NOT NULL,
  sum     DECIMAL(10,2) NOT NULL
);

CREATE TABLE balance (
  subj_id       SERIAL NOT NULL,
  sum           DECIMAL(10,2) NOT NULL,
  created       TIMESTAMP NOT NULL,
  remark        varchar(255) DEFAULT NULL,
  payment_gate  varchar(64) DEFAULT NULL,
  bill_id       INT NOT NULL,
  CONSTRAINT balance_bill_fk FOREIGN KEY (bill_id) REFERENCES bill (id)
);

--COMMENT='объявы на стене'
CREATE TABLE public.message (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('board_msg_id_seq'::regclass),
  msg CHARACTER VARYING(2048) NOT NULL,
  type INTEGER NOT NULL,
  img CHARACTER VARYING(64),
  event_date DATE,
  event_time TIME WITHOUT TIME ZONE,
  bonus_read INTEGER,
  bonus_tell INTEGER,
  tag_id INTEGER NOT NULL DEFAULT 1,
  FOREIGN KEY (tag_id) REFERENCES public.tag (id)
  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE public.message_ref (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('board_id_seq'::regclass),
  subj_id INTEGER NOT NULL,
  msg_id INTEGER NOT NULL,
  likes INTEGER,
  created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  to_id INTEGER, -- кому. null = всем
  FOREIGN KEY (msg_id) REFERENCES public.message (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (subj_id) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (msg_id) REFERENCES public.message (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (to_id) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
COMMENT ON COLUMN public.message_ref.to_id IS 'кому. null = всем ';

CREATE TABLE public.bonus (
  subj_id INTEGER NOT NULL,
  bonus_amount INTEGER NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  from_subj INTEGER NOT NULL,
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('bonus_id_seq'::regclass),
  FOREIGN KEY (from_subj) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (subj_id) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TABLE public.tag (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('category_id_seq'::regclass),
  name CHARACTER VARYING(64) NOT NULL,
  parent_id INTEGER,
  FOREIGN KEY (parent_id) REFERENCES public.tag (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE public.subj_group (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('subj_group_id_seq'::regclass),
  subj_id INTEGER NOT NULL,
  group_name CHARACTER VARYING(32) NOT NULL,
  FOREIGN KEY (subj_id) REFERENCES public.subj (id)
  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
COMMENT ON TABLE public.subj_group IS 'группы, созданные пользователем';

CREATE TABLE public.subj_tag (
  subj_id INTEGER NOT NULL,
  tag_id INTEGER NOT NULL,
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('subj_tag_id_seq'::regclass),
  FOREIGN KEY (subj_id) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (tag_id) REFERENCES public.tag (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE UNIQUE INDEX subj_like_cat_pkey ON subj_tag USING BTREE (subj_id, tag_id);

CREATE TABLE public.subj_subscribe (
  subscriber INTEGER NOT NULL,
  subscribe_on INTEGER NOT NULL,
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('subj_subscribe_id_seq'::regclass),
  group_id INTEGER, -- своя группа
  FOREIGN KEY (subscribe_on) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (group_id) REFERENCES public.subj_group (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (subscriber) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE INDEX idx_subj_subscribe_subscriber ON subj_subscribe USING BTREE (subscriber);
COMMENT ON COLUMN public.subj_subscribe.group_id IS 'своя группа';


CREATE TABLE public.subj_sees_msg (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('subj_sees_msg_id_seq'::regclass),
  subj_id INTEGER NOT NULL,
  msg_id INTEGER NOT NULL,
  FOREIGN KEY (msg_id) REFERENCES public.message (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
  FOREIGN KEY (subj_id) REFERENCES public.subj (id)  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
CREATE INDEX subj_sees_msg_subj_id_index ON subj_sees_msg USING BTREE (subj_id);

CREATE TABLE public.tokens (
  subj_id INTEGER PRIMARY KEY NOT NULL,
  token CHARACTER VARYING(128) NOT NULL,
  updated TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  FOREIGN KEY (subj_id) REFERENCES public.subj (id)
  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);