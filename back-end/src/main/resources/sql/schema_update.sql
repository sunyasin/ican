INSERT INTO public.tag (id, name, parent_id) VALUES (1, 'cat1', 2);
INSERT INTO public.tag (id, name, parent_id) VALUES (2, 'cat2', null);

INSERT INTO public.subj (id, name, created, address, type, login, pass, email, updated, description, contacts, auto_bonus_active, partner_id, partner_code, picture) VALUES (2, 'name2', '2017-09-22 22:50:51.808000', 'adr2', 1, 'login2', 'pass2', 'email2', null, 'descr2', 'contacts2', true, null, null, null);
INSERT INTO public.subj (id, name, created, address, type, login, pass, email, updated, description, contacts, auto_bonus_active, partner_id, partner_code, picture) VALUES (4, 'newname', '2017-10-07 23:29:36.005000', null, 0, 'login1', 'pass1', null, '2017-10-07 23:29:36.005000', null, null, null, null, null, null);
INSERT INTO public.subj (id, name, created, address, type, login, pass, email, updated, description, contacts, auto_bonus_active, partner_id, partner_code, picture) VALUES (5, 'newname', '2017-10-07 23:32:47.563000', null, 0, 'login11', 'pass1', null, '2017-10-07 23:32:47.563000', null, null, null, null, null, null);
INSERT INTO public.subj (id, name, created, address, type, login, pass, email, updated, description, contacts, auto_bonus_active, partner_id, partner_code, picture) VALUES (6, 'newname', '2017-10-07 23:33:29.827000', null, 0, 'login111', 'pass1', null, '2017-10-07 23:33:29.827000', null, null, null, null, null, null);
INSERT INTO public.subj (id, name, created, address, type, login, pass, email, updated, description, contacts, auto_bonus_active, partner_id, partner_code, picture) VALUES (7, 'newname', '2017-10-07 23:36:53.834000', null, 0, 'login1111', 'pass1', null, '2017-10-07 23:36:53.834000', null, null, null, null, null, null);
INSERT INTO public.subj (id, name, created, address, type, login, pass, email, updated, description, contacts, auto_bonus_active, partner_id, partner_code, picture) VALUES (8, 'newname', '2017-10-07 23:41:38.365000', null, 0, 'login12111', 'pass1', null, '2017-10-07 23:41:38.365000', null, null, null, null, null, null);
INSERT INTO public.subj (id, name, created, address, type, login, pass, email, updated, description, contacts, auto_bonus_active, partner_id, partner_code, picture) VALUES (1, 'name1', '2017-09-22 22:47:42.146000', 'adr!', 0, 'login1!', 'pass1', null, '2017-09-22 22:47:58.016000', 'descr!', 'conta!', true, null, null, 'logo_ir.gif');

INSERT INTO public.subj_group (id, subj_id, group_name) VALUES (1, 1, 'aaa');
INSERT INTO public.subj_group (id, subj_id, group_name) VALUES (2, 2, 'bbb');

INSERT INTO public.message (id, msg, type, img, event_date, event_time, bonus_read, bonus_tell, tag_id) VALUES (1, 'msg1', 1, null, null, null, 1, 1, 1);
INSERT INTO public.message (id, msg, type, img, event_date, event_time, bonus_read, bonus_tell, tag_id) VALUES (2, 'msg2', 0, null, null, null, 2, 1, 1);
INSERT INTO public.message (id, msg, type, img, event_date, event_time, bonus_read, bonus_tell, tag_id) VALUES (3, 'new_msg', 0, null, null, null, 3, 1, 1);

INSERT INTO public.message_ref (subj_id, msg_id, likes, created, id, to_id) VALUES (2, 2, 0, '2017-09-23 20:44:12.093000', 2, null);
INSERT INTO public.message_ref (subj_id, msg_id, likes, created, id, to_id) VALUES (1, 2, 0, '2017-09-23 20:43:51.099000', 1, null);
INSERT INTO public.message_ref (subj_id, msg_id, likes, created, id, to_id) VALUES (2, 3, 0, '2017-09-24 13:13:12.337000', 5, null);

INSERT INTO public.msg_book (id, msg_id, subj_id) VALUES (2, 2, 2);
INSERT INTO public.msg_book (id, msg_id, subj_id) VALUES (1, 2, 1);

INSERT INTO public.subj_tag (subj_id, tag_id, id) VALUES (1, 1, 1);
INSERT INTO public.subj_tag (subj_id, tag_id, id) VALUES (2, 1, 2);

INSERT INTO public.subj_subscribe (subscriber, subscribe_on, id, group_id) VALUES (1, 2, 1, 1);

INSERT INTO public.subj_sees_msg (id, subj_id, msg_id) VALUES (1, 1, 1);

INSERT INTO public.bonus (subj_id, bonus_amount, created, from_subj, id) VALUES (1, 1, '2017-09-25 21:18:02.297000', 2, 1);


