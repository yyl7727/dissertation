DELETE FROM parameter WHERE para_code='0001' AND para_type='0001';
INSERT INTO parameter(para_type,para_name,para_code,para_value) VALUES('0001','职称','0001','学生');
DELETE FROM parameter WHERE para_code='0002' AND para_type='0001';
INSERT INTO parameter(para_type,para_name,para_code,para_value) VALUES('0001','职称','0002','教师');
DELETE FROM parameter WHERE para_code='0003' AND para_type='0001';
INSERT INTO parameter(para_type,para_name,para_code,para_value) VALUES('0001','职称','0003','管理员');
