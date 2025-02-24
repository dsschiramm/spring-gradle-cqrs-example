CREATE USER 'application-command'@'%' IDENTIFIED BY '1234';
CREATE USER 'application-query'@'%' IDENTIFIED BY '1234';

GRANT ALL PRIVILEGES ON company.* TO 'application-command'@'%';
GRANT ALL PRIVILEGES ON company.* TO 'application-query'@'%';
--GRANT SELECT ON company.* TO 'application-query'@'%';

FLUSH PRIVILEGES;
