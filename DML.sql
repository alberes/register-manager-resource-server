SELECT * FROM client;
SELECT * FROM user_account;
SELECT * FROM user_account_role;
SELECT * FROM user_account_scope;

delete from client;


INSERT INTO  client(ID, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, SCOPE, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES (gen_random_uuid(), 'my-client-id', '$2a$12$UKQvBMFvDSOwRdPbPCiY0eXSt9rSpWgiFG2Wi5GrpQV6yqAEya0Be', 'http://127.0.0.1:8080/login/oauth2/code/register-manager-frontend-client-oidc', 'USER', NOW(), NOW());

INSERT INTO  client(ID, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, SCOPE, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES (gen_random_uuid(), 'admin-client-id', '$2a$12$40ulvRdrXkyEszgVHD/kEOdZTEzJpnrM7JN0WqGzoFkNJ3eSLRrm.', 'http://127.0.0.1:8080/login/oauth2/code/register-manager-frontend-client-oidc', 'ADMIN', NOW(), NOW());

INSERT INTO  client(ID, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, SCOPE, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES (gen_random_uuid(), 'my-client-id-docker', '$2a$12$tQSPG8P30ACYvdZfQ35.1esuQEvf9mPW9edWHR0Sia1RjwMI8dyvS', 'http://localhost/login/oauth2/code/register-manager-frontend-client-oidc', 'USER', NOW(), NOW());

INSERT INTO  client(ID, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, SCOPE, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES (gen_random_uuid(), 'admin-client-id-docker', '$2a$12$QRaR3xbxb3KYhtZKrjgbl.NV6OoUuExXRlaszUa0SMBoF750aoAqq', 'http://localhost/login/oauth2/code/register-manager-frontend-client-oidc', 'ADMIN', NOW(), NOW());

INSERT INTO user_account(ID, NAME, EMAIL, PASSWORD, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES (gen_random_uuid(), 'Admin Manager Data', 'admin@admin.com', '$2a$12$jyoZ3UQBbkYll/oDXIcYh.KJFn6xiVcVs./28e/IWpi5i4SyVSQne', NOW(), NOW());

INSERT INTO user_account_role(USER_ACCOUNT_ID, ROLES) VALUES(
  (SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'ADMIN');

INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'openid');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'profile');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'email');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'address');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'phone');

INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'client.write');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'client.read');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'client.update');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'client.delete');

INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'user.write');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'user.read');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'user.update');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin.com'), 'user.delete');



INSERT INTO user_account(ID, NAME, EMAIL, PASSWORD, CREATED_DATE, LAST_MODIFIED_DATE)
VALUES (gen_random_uuid(), 'Admin Manager Data Docker', 'admin@admin-docker.com', '$2a$12$3jCf9EdrqBUrpYJnH0LCp.xuRBGxA2ol1F1/OuSN74OPT5yuUy2qK', NOW(), NOW());

INSERT INTO user_account_role(USER_ACCOUNT_ID, ROLES) VALUES(
  (SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'ADMIN');

INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'openid');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'profile');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'email');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'address');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'phone');

INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'client.write');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'client.read');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'client.update');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'client.delete');

INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'user.write');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'user.read');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'user.update');
INSERT INTO user_account_scope(USER_ACCOUNT_ID, SCOPES) VALUES(
(SELECT id FROM user_account WHERE email = 'admin@admin-docker.com'), 'user.delete');