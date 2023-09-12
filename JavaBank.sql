DROP DATABASE IF EXISTS JavaBank;
CREATE DATABASE JavaBank;
USE JavaBank;

DROP TABLE accounts;
CREATE TABLE accounts
(
	accnumber int primary key auto_increment,
	accname varchar(150) NOT NULL,
	accbalance decimal(10,2) NOT NULL
);

drop table accounts;



DELIMITER // 
CREATE PROCEDURE createAccount(IN p_account_name VARCHAR(150), IN p_account_balance DECIMAL(10, 2))
BEGIN
  INSERT INTO accounts (accname, accbalance) VALUES
  (p_account_name, p_account_balance);
  
  SELECT accnumber FROM accounts WHERE accname = p_account_name;
END //
DELIMITER ;


DELIMITER // 
  CREATE PROCEDURE detailsAccount(IN p_account_number int)
  BEGIN
  SELECT * FROM accounts WHERE accnumber = p_account_number;
  END //
DELIMITER ;


SELECT * FROM accounts;

select count(accnumber) from accounts where accname = "duda";

CREATE USER 'test'@'localhost' IDENTIFIED BY 'senha';

--skip-grant-tables;