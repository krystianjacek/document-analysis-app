DROP TABLE IF EXISTS tab_logs;
CREATE TABLE tab_logs
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    office_name  VARCHAR(255),
    user_name  VARCHAR(255),
    day_of_month INTEGER,
    hour INTEGER,
	document_id INTEGER,
	scan_time BIGINT,
	save_time BIGINT,
	show_time BIGINT
);
