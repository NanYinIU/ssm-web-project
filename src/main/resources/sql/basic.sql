INSERT INTO `s_operate_type` (`id`, `name`, `comment`, `ord`, `is_can_deleted`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '', NULL, 1, NULL, NULL, NULL, NULL);

INSERT INTO `s_project_level` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '一级', NULL, 1, 0, NULL, NULL);
INSERT INTO `s_project_status` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '未开始', NULL, 1, 0, NULL, NULL);
INSERT INTO `s_project_type` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '研发类', NULL, 1, 0, NULL, NULL);
INSERT INTO `s_project_user_duty` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '项目中的职责1', NULL, NULL, 0, NULL, NULL);
INSERT INTO `s_resource_icon` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '项目管理', 'fas fa-project-diagram', 1, 0, NULL, NULL),
	(2, '主页', 'fas fa-tachometer-alt', 2, 0, NULL, NULL);
INSERT INTO `s_resource_type` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '侧边栏', NULL, 1, 0, NULL, NULL);
INSERT INTO `s_unit_category` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '分类1', NULL, NULL, 0, NULL, NULL);
INSERT INTO `s_user_sex` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '男', NULL, NULL, 0, NULL, NULL);

INSERT INTO `s_user_status` (`id`, `name`, `comment`, `ord`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '正常', NULL, 1, 1, NULL, NULL);


INSERT INTO `project` (`id`, `name`, `parent_id`, `ord`, `status_id`, `type_id`, `level_id`, `code`, `plan_start`, `plan_finish`, `actual_start`, `actual_finish`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, '测试项目', NULL, 1, 1, 1, 1, '01', '2019-10-10 10:00:00', '2020-10-10 10:00:00', NULL, NULL, 0, NULL, NULL);

INSERT INTO `resource` (`id`, `name`, `comment`, `ord`, `type_id`, `is_deleted`, `gmt_create`, `gmt_modify`, `url`, `icon_id`)
VALUES
	(1, '项目管理', NULL, 2, 1, 0, NULL, NULL, '/projects', 1),
	(2, '主页', NULL, 1, 1, 0, NULL, NULL, '/index', 2);



INSERT INTO `unit` (`id`, `name`, `parent_id`, `code`, `category_id`, `ord`, `is_deleted`, `gmt_modify`, `gmt_create`)
VALUES
	(1, '总部', NULL, 1, 1, NULL, NULL, NULL, NULL);

INSERT INTO `user` (`id`, `name`, `password`, `email`, `salt`, `age`, `sex_id`, `unit_id`, `status_id`, `is_deleted`, `gmt_create`, `gmt_modify`)
VALUES
	(1, 'admin', 'a38b872c85e9096dd2d97b5dbe482861', NULL, '1', 24, 1, 1, 1, 0, '2019-10-10 10:00:00', '2019-10-10 10:00:00');

INSERT INTO `auth` (`id`, `comment`, `gmt_create`, `gmt_modify`, `is_deleted`, `name`, `ord`)
VALUES
	(1, NULL, NULL, NULL, 0, 'dd', NULL);

INSERT INTO `r_project_user` ( `users_id`, `project_id`, `duty_id`)
VALUES
	( 1, 1, 1);

INSERT INTO `r_resource_auth` ( `resources_id`, `auth_id`)
VALUES
	( 1, 1),
	( 2, 1);

INSERT INTO `r_user_auth` ( `users_id`, `auth_id`)
VALUES
	( 1, 1);

