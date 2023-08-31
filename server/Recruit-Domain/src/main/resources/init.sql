INSERT INTO columns (columns_id, created_at, updated_at, navigation_id, next_columns_id, title) VALUES (1,NOW(),NOW(), 1, 2,"개발자");
INSERT INTO columns (columns_id, created_at, updated_at, navigation_id, next_columns_id, title) VALUES (2,NOW(),NOW(), 1, 3,"디자이너");
INSERT INTO columns (columns_id, created_at, updated_at, navigation_id, next_columns_id, title) VALUES (3,NOW(),NOW(), 1, null, "기획자");
INSERT INTO navigation (navigation_id, created_at, updated_at, title) VALUES (1,NOW(),NOW(),"공통");
INSERT INTO navigation (navigation_id, created_at, updated_at, title) VALUES (2,NOW(),NOW(),"회장단");
INSERT INTO navigation (navigation_id, created_at, updated_at, title) VALUES (3,NOW(),NOW(),"운영팀");
INSERT INTO navigation (navigation_id, created_at, updated_at, title) VALUES (4,NOW(),NOW(),"홍보 및 디자인팀");
INSERT INTO navigation (navigation_id, created_at, updated_at, title) VALUES (5,NOW(),NOW(),"지원자 대응팀");
INSERT INTO navigation (navigation_id, created_at, updated_at, title) VALUES (6,NOW(),NOW(),"OT 담당팀");
INSERT INTO navigation (navigation_id, created_at, updated_at, title) VALUES (7,NOW(),NOW(),"기타 참고");
INSERT INTO interviewer (idp_id, name, role, year) VALUES (0,"이서현_관리자","ROLE_TF", 21);
INSERT INTO interviewer (idp_id, name, role, year) VALUES (1,"임채승","ROLE_TF", 22);
INSERT INTO interviewer (idp_id, name, role, year) VALUES (2,"강바다","ROLE_TF", 24);
INSERT INTO interviewer (idp_id, name, role, year) VALUES (3,"이도연","ROLE_PRESIDENT", 23);