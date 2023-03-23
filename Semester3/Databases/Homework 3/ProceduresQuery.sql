use japanese_r


--------- modify the type of a column
go
CREATE OR alter PROCEDURE setSalaryFromIntoToDec as
      ALTER TABLE contract_em ALTER COLUMN salary float
--select * from contract_em
go
 
CREATE OR ALTER PROCEDURE setSalaryFromDecToInt as
      ALTER TABLE contract_em ALTER COLUMN salary INT
go
--select* from contract_em


-------------add/remove a column

create or alter procedure addAddressColumn as
    alter table employees add address_emp varchar(max)
go


create or alter procedure removeAddrColumn as
    alter table employees drop column address_emp
go

-- add/remove a DEFAULT constraint


CREATE OR ALTER PROCEDURE addDefaultToBillFromOrder_
AS
	ALTER TABLE order_ ADD CONSTRAINT default_bill DEFAULT(0) FOR bill
go

CREATE OR ALTER PROCEDURE removeDefaultFromBillFromOrder_
AS
	ALTER TABLE order_ DROP CONSTRAINT default_bill
go

-- create/drop a table

CREATE OR ALTER PROCEDURE addPromotionalMenu 
AS
	CREATE TABLE PromotionalMenu (
		pm_id INT not null,
		id_order INT NOT NULL,
		description_pm VARCHAR(50) NOT NULL,
		CONSTRAINT PromotionalMenu_PRIMARY_KEY PRIMARY KEY(pm_id)
	)
go
 
CREATE OR ALTER PROCEDURE dropPromotionalMenu
AS
	DROP TABLE PromotionalMenu
go
-- add/remove a primary key

CREATE OR ALTER PROCEDURE addPrimaryKeyPromotionalMenu
AS
	ALTER TABLE PromotionalMenu
		DROP CONSTRAINT PromotionalMenu_PRIMARY_KEY
	ALTER TABLE PromotionalMenu
		ADD CONSTRAINT PromotionalMenu_PRIMARY_KEY PRIMARY KEY(pm_id, description_pm)
go
 
CREATE OR ALTER PROCEDURE removePrimaryKeyPromotionalMenu
AS
	ALTER TABLE PromotionalMenu
		DROP CONSTRAINT PromotionalMenu_PRIMARY_KEY
	ALTER TABLE PromotionalMenu
		ADD CONSTRAINT PromotionalMenu_PRIMARY_KEY PRIMARY KEY(pm_id)
go

-- add/remove a candidate key

CREATE OR ALTER PROCEDURE newCandidateKey 
AS	
	ALTER TABLE employees
		ADD CONSTRAINT employee_CANDIDATE_KEY UNIQUE(employee_name,e_firstName)
go


CREATE OR ALTER PROCEDURE removeCandidateKey
AS
	ALTER TABLE employees
		DROP CONSTRAINT employee_CANDIDATE_KEY
go

-- add / remove a foreign key

CREATE OR ALTER PROCEDURE newForeignKeyPM
AS
	ALTER TABLE PromotionalMenu
		ADD CONSTRAINT PromotionalMenu_FOREIGN_KEY FOREIGN KEY(id_order) REFERENCES order_(id_order)
go

CREATE OR ALTER PROCEDURE removeForeignKeyPM
AS
	ALTER TABLE PromotionalMenu
		DROP CONSTRAINT PromotionalMenu_FOREIGN_KEY
go

-- a table that contains the current version of the database schema

drop table versionTable
create table versionTable (
    version int
)

insert into versionTable values (1) -- initial version
drop table proceduresTable
create table proceduresTable (
    fromVersion int,
    toVersion int,
    primary key (fromVersion, toVersion),
    nameProc varchar(max)
)

INSERT INTO proceduresTable
VALUES
		(1,2,'setSalaryFromIntoToDec'),
		(2,1,'setSalaryFromDecToInt'),
		(2,3,'addAddressColumn'),
        (3,2,'removeAddrColumn'),
		(3,4,'addDefaultToBillFromOrder_'),
		(4,3,'removeDefaultFromBillFromOrder_'),
		(4,5,'addPromotionalMenu'),
	    (5,4,'dropPromotionalMenu'),
		(5,6,'addPrimaryKeyPromotionalMenu'),
		(6,5,'removePrimaryKeyPromotionalMenu'),
		(6,7,'newCandidateKey'),
		(7,6,'removeCandidateKey'),
		(7,8,'newForeignKeyPM'),
		(8,7,'removeForeignKeyPM')
go
CREATE or alter PROCEDURE goToVersion(@newVersion INT) AS
	DECLARE @curr int
	DECLARE @procedureName varchar(255)
	SELECT @curr=version FROM versionTable
	IF @newVersion > (SELECT max(toVersion) FROM proceduresTable)
		RAISERROR ('Version does not exist', 10, 1)
	
	IF @newVersion < (SELECT min(fromVersion) FROM proceduresTable)
		RAISERROR ('Version does not exist', 10, 1)
	
	WHILE @curr < @newVersion BEGIN
		SELECT @procedureName=nameProc FROM proceduresTable WHERE @curr=fromVersion AND @curr+1=toVersion
	    print @procedureName
		EXEC (@procedureName)
		SET @curr=@curr+1
		UPDATE versionTable SET version=@curr
	END
	
	WHILE @curr > @newVersion BEGIN
		SELECT @procedureName=nameProc FROM proceduresTable WHERE @curr=fromVersion AND @curr-1=toVersion
		    print @procedureName
	
		EXEC (@procedureName)
		SET @curr=@curr-1
		UPDATE versionTable SET version=@curr
	END;
GO

execute goToVersion 1

SELECT *
FROM versionTable

SELECT *
FROM proceduresTable

select * 
from employees
