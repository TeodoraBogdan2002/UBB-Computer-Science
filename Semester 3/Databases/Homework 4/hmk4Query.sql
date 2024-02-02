use [tryyy]
DROP TABLE Makes
DROP TABLE Manufacturer
DROP TABLE ContainsPart
DROP TABLE Part
DROP TABLE Driver
DROP TABLE Has
DROP TABLE Car
DROP TABLE Sponsor
DROP TABLE Team
DROP TABLE Competes
DROP TABLE Category
DROP TABLE Edition

DROP VIEW viewSelect
DROP VIEW viewSelJoin
DROP VIEW viewSelGrJoin

DROP PROCEDURE UspAddHas
DROP PROCEDURE UspAddCar

DROP PROCEDURE UspTestRunViews
DROP PROCEDURE UspTestRunTables

DROP PROCEDURE RemoveFromMakes
DROP PROCEDURE AddInMakes
DROP PROCEDURE RemoveFromTeam
DROP PROCEDURE AddInTeam
DROP PROCEDURE RemoveFromEdition
DROP PROCEDURE AddInEdition

CREATE TABLE Edition
(
	EID INT PRIMARY KEY,
	Year INT
)

CREATE TABLE Category
(
	CID INT PRIMARY KEY,
	Name VARCHAR(50)
)

INSERT INTO Category
Values
	(1, 'Cat_1')

CREATE TABLE Competes
(
	COMID INT PRIMARY KEY,
	EID INT REFERENCES Edition,
	CID INT REFERENCES Category
)

CREATE TABLE Team
(
	TID INT PRIMARY KEY,
	Name VARCHAR(50),
	CID INT REFERENCES Category
)

CREATE TABLE Sponsor
(
	SID INT PRIMARY KEY,
	Name VARCHAR(50),
	TID INT REFERENCES Team
)

CREATE TABLE Car
(
	CID INT PRIMARY KEY,
	Number INT,
)

CREATE TABLE Has
(
	RelID INT PRIMARY KEY,
	TID INT REFERENCES Team,
	CID INT REFERENCES Car
)

CREATE TABLE Driver
(
	DID INT PRIMARY KEY,
	Name VARCHAR(50),
	DOB DATE,
	CID INT REFERENCES Car
)

CREATE TABLE Part
(
	PID INT PRIMARY KEY
)

INSERT INTO Part
Values
	(1)

CREATE TABLE ContainsPart
(
	RelID INT PRIMARY KEY,
	CID INT REFERENCES Car,
	PID INT REFERENCES Part
)

CREATE TABLE Manufacturer
(
	MID INT PRIMARY KEY,
	Name VARCHAR(50)
)

INSERT INTO Manufacturer
Values
	(1, 'Manufacturer_1')

CREATE TABLE Makes
(
	RelID INT NOT NULL,
	PID INT REFERENCES Part,
	MID INT REFERENCES Manufacturer,
	Pcount INT,
	CONSTRAINT MKEY PRIMARY KEY (RelID, Pcount)
)
GO

-- a table with a single column primary key and no foreign keys - Edition
-- a table with a single column primary key and at least one foreign key - Team
-- a table with a multicolumn primary key - Makes

-- a view with a SELECT statement operation on one table

CREATE VIEW viewSelect AS
SELECT *
FROM Team

-- a view with a SELECT statement that operates on at least 2 different tables and contains at least one JOIN operator
GO
CREATE VIEW viewSelJoin AS
SELECT T.Name
FROM Team T
INNER JOIN Has H ON H.TID = T.TID
GO 
-- a view with a SELECT statement that has a GROUP BY clause, operates on at least 2 different tables and contains at least one JOIN operator

CREATE VIEW viewSelGrJoin AS
SELECT C.CID
FROM Car C
INNER JOIN Has H ON H.CID = C.CID
GROUP BY C.CID
GO
-- Add and remove for Edition

CREATE PROCEDURE AddInEdition -- aici sa stii ca am scos alter
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			INSERT INTO Edition
			Values(@i, 2022)
			SET @i = @i + 1 
		END
GO
--GO
--EXEC AddInEdition
--SELECT *
--FROM Edition

CREATE PROCEDURE RemoveFromEdition
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			DELETE FROM Edition WHERE EID = @i
			SET @i = @i + 1 
		END
GO

--GO 
--EXEC RemoveFromEdition
--SELECT *
--FROM Edition

-- Add and remove for Team
CREATE PROCEDURE AddInTeam
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			INSERT INTO Team
			Values(@i, 'Team_test', 1)
			SET @i = @i + 1 
		END
GO

--GO
--EXEC AddInTeam
--SELECT *
--FROM Team

CREATE PROCEDURE RemoveFromTeam
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			DELETE FROM Team WHERE TID = @i
			SET @i = @i + 1 
		END
GO -- efectiv asa am stat si la mine ca sa vad unde era problema, asta e debugging
--GO 
--EXEC RemoveFromTeam
--SELECT *
--FROM Team

-- Add and remove for Makes
CREATE PROCEDURE AddInMakes
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			INSERT INTO Makes
			Values(@i, 1, 1, @i) --nu ziceam de go, ziceam de rulatul asta in stilul asta
			SET @i = @i + 1 
		END
GO
--GO
--EXEC AddInMakes
--SELECT *
--FROM Makes

--GO
CREATE PROCEDURE RemoveFromMakes -- tre sa fac asa pana jos ca sa le testam
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			DELETE FROM Makes WHERE RelID = @i
			SET @i = @i + 1 
		END

--GO 
--EXEC RemoveFromMakes
--SELECT *
--FROM Makes

GO
---- script
IF EXISTS(
	SELECT * 
	FROM dbo.sysobjects 
	WHERE id = object_id(N'[FK_TestRunTables_Tables]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestRunTables] DROP CONSTRAINT FK_TestRunTables_Tables

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects
	WHERE id = object_id(N'[FK_TestTables_Tables]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestTables] DROP CONSTRAINT FK_TestTables_Tables

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects
	WHERE id = object_id(N'[FK_TestRunTables_TestRuns]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestRunTables] DROP CONSTRAINT FK_TestRunTables_TestRuns

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects
	WHERE id = object_id(N'[FK_TestRunViews_TestRuns]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestRunViews] DROP CONSTRAINT FK_TestRunViews_TestRuns

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects
	WHERE id = object_id(N'[FK_TestTables_Tests]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestTables] DROP CONSTRAINT FK_TestTables_Tests

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects
	WHERE id = object_id(N'[FK_TestViews_Tests]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestViews] DROP CONSTRAINT FK_TestViews_Tests

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects
	WHERE id = object_id(N'[FK_TestRunViews_Views]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestRunViews] DROP CONSTRAINT FK_TestRunViews_Views

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects
	WHERE id = object_id(N'[FK_TestViews_Views]') AND OBJECTPROPERTY(id, N'IsForeignKey') = 1)
	ALTER TABLE [TestViews] DROP CONSTRAINT FK_TestViews_Views

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[Tables]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [Tables]

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[TestRunTables]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [TestRunTables]

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[TestRunViews]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [TestRunViews]

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[TestRuns]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [TestRuns]

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[TestTables]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [TestTables]

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[TestViews]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [TestViews]

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[Tests]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [Tests]

GO

IF EXISTS(
	SELECT *
	FROM dbo.sysobjects	
	WHERE id = object_id(N'[Views]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1 )
	DROP TABLE [Views]

GO

CREATE TABLE [Tables] (
	[TableID] [INT] IDENTITY (1, 1) NOT NULL,
	[Name] [NVARCHAR](50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL
) ON [PRIMARY]

GO

CREATE TABLE [TestRunTables] (
	[TestRunID] [int] NOT NULL ,
	[TableID] [int] NOT NULL ,
	[StartAt] [datetime] NOT NULL ,
	[EndAt] [datetime] NOT NULL 
) ON [PRIMARY]

GO


CREATE TABLE [TestRunViews] (
	[TestRunID] [int] NOT NULL ,
	[ViewID] [int] NOT NULL ,
	[StartAt] [datetime] NOT NULL ,
	[EndAt] [datetime] NOT NULL 
) ON [PRIMARY]

GO

CREATE TABLE [TestRuns] (
	[TestRunID] [int] IDENTITY (1, 1) NOT NULL ,
	[Description] [nvarchar] (2000) COLLATE SQL_Latin1_General_CP1_CI_AS NULL ,
	[StartAt] [datetime] NULL ,
	[EndAt] [datetime] NULL 
) ON [PRIMARY]

GO

CREATE TABLE [TestTables] (
	[TestID] [int] NOT NULL ,
	[TableID] [int] NOT NULL ,
	[NoOfRows] [int] NOT NULL ,
	[Position] [int] NOT NULL 
) ON [PRIMARY]

GO

CREATE TABLE [TestViews] (
	[TestID] [int] NOT NULL ,
	[ViewID] [int] NOT NULL 
) ON [PRIMARY]

GO

CREATE TABLE [Tests] (
	[TestID] [int] IDENTITY (1, 1) NOT NULL ,
	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]

GO

CREATE TABLE [Views] (
	[ViewID] [int] IDENTITY (1, 1) NOT NULL ,
	[Name] [nvarchar] (50) COLLATE SQL_Latin1_General_CP1_CI_AS NOT NULL 
) ON [PRIMARY]

GO

ALTER TABLE [Tables] WITH NOCHECK ADD 
	CONSTRAINT [PK_Tables] PRIMARY KEY  CLUSTERED 
	(
		[TableID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [TestRunTables] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestRunTables] PRIMARY KEY  CLUSTERED 
	(
		[TestRunID],
		[TableID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [TestRunViews] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestRunViews] PRIMARY KEY  CLUSTERED 
	(
		[TestRunID],
		[ViewID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [TestRuns] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestRuns] PRIMARY KEY  CLUSTERED 
	(
		[TestRunID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [TestTables] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestTables] PRIMARY KEY  CLUSTERED 
	(
		[TestID],
		[TableID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [TestViews] WITH NOCHECK ADD 
	CONSTRAINT [PK_TestViews] PRIMARY KEY  CLUSTERED 
	(
		[TestID],
		[ViewID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [Tests] WITH NOCHECK ADD 
	CONSTRAINT [PK_Tests] PRIMARY KEY  CLUSTERED 
	(
		[TestID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [Views] WITH NOCHECK ADD 
	CONSTRAINT [PK_Views] PRIMARY KEY  CLUSTERED 
	(
		[ViewID]
	)  ON [PRIMARY] 

GO

ALTER TABLE [TestRunTables] ADD 
	CONSTRAINT [FK_TestRunTables_Tables] FOREIGN KEY 
	(
		[TableID]
	) REFERENCES [Tables] (
		[TableID]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [FK_TestRunTables_TestRuns] FOREIGN KEY 
	(
		[TestRunID]
	) REFERENCES [TestRuns] (
		[TestRunID]
	) ON DELETE CASCADE  ON UPDATE CASCADE 

GO

ALTER TABLE [TestRunViews] ADD 
	CONSTRAINT [FK_TestRunViews_TestRuns] FOREIGN KEY 
	(
		[TestRunID]
	) REFERENCES [TestRuns] (
		[TestRunID]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [FK_TestRunViews_Views] FOREIGN KEY 
	(
		[ViewID]
	) REFERENCES [Views] (
		[ViewID]
	) ON DELETE CASCADE  ON UPDATE CASCADE 

GO

ALTER TABLE [TestTables] ADD 
	CONSTRAINT [FK_TestTables_Tables] FOREIGN KEY 
	(
		[TableID]
	) REFERENCES [Tables] (
		[TableID]
	) ON DELETE CASCADE  ON UPDATE CASCADE ,
	CONSTRAINT [FK_TestTables_Tests] FOREIGN KEY 
	(
		[TestID]
	) REFERENCES [Tests] (
		[TestID]
	) ON DELETE CASCADE  ON UPDATE CASCADE 

GO

ALTER TABLE [TestViews] ADD 
	CONSTRAINT [FK_TestViews_Tests] FOREIGN KEY 
	(
		[TestID]
	) REFERENCES [Tests] (
		[TestID]
	),
	CONSTRAINT [FK_TestViews_Views] FOREIGN KEY 
	(
		[ViewID]
	) REFERENCES [Views] (
		[ViewID]
	)

GO
---- script


DELETE FROM Tables
INSERT INTO Tables VALUES ('Edition'), ('Team'), ('Makes')

DELETE FROM Views
INSERT INTO Views VALUES ('viewSelect'), ('viewSelJoin'), ('viewSelGrJoin')

DELETE FROM Tests
INSERT INTO Tests VALUES ('AddInEdition'), ('RemoveFromEdition'), ('AddInTeam'), ('RemoveFromTeam'), ('AddInMakes'), ('RemoveFromMakes')

SELECT * FROM Tests
SELECT * FROM Tables
SELECT * FROM Views

DELETE FROM TestViews
INSERT INTO TestViews VALUES (1,1)
INSERT INTO TestViews VALUES (1,2)
INSERT INTO TestViews VALUES (1,3)

SELECT * FROM TestViews

-- ce format ai aici??? VALUES(test id,table id,no rows,position)
DELETE FROM TestTables
INSERT INTO TestTables VALUES (2, 1, 100, 1) -- o sa vedem daca pusca
INSERT INTO TestTables VALUES (4, 2, 100, 2) -- de aia sugeram sa iei de la 0 ori o baza noua, ori sa stergi ce ai in asta
INSERT INTO TestTables VALUES (6, 3, 100, 3)

SELECT * FROM TestTables

GO

CREATE PROC UspTestRunViews
AS
	DECLARE @start1 DATETIME;
	DECLARE @start2 DATETIME;
	DECLARE @start3 DATETIME;
	DECLARE @end1 DATETIME;
	DECLARE @end2 DATETIME;
	DECLARE @end3 DATETIME;

	SET @start1 = GETDATE();
	EXEC ('SELECT * FROM viewSelect');
	SET @end1 = GETDATE();
	INSERT INTO TestRuns VALUES ('TestView1', @start1, @end1)
	INSERT INTO TestRunViews VALUES (@@IDENTITY, 1, @start1, @end1);

	SET @start2 = GETDATE();
	EXEC ('SELECT * FROM viewSelJoin');
	SET @end2 = GETDATE();
	INSERT INTO TestRuns VALUES ('TestView2', @start2, @end2)
	INSERT INTO TestRunViews VALUES (@@IDENTITY, 2, @start2, @end2);

	SET @start3 = GETDATE();
	EXEC ('SELECT * FROM viewSelGrJoin');
	SET @end3 = GETDATE();
	INSERT INTO TestRuns VALUES ('TestView3', @start3, @end3)
	INSERT INTO TestRunViews VALUES (@@IDENTITY, 3, @start3, @end3);

GO

CREATE PROC UspTestRunTables
AS
	DECLARE @start1 DATETIME;
	DECLARE @start2 DATETIME;
	DECLARE @start3 DATETIME;
	DECLARE @start4 DATETIME;
	DECLARE @start5 DATETIME;
	DECLARE @start6 DATETIME;
	DECLARE @end1 DATETIME;
	DECLARE @end2 DATETIME;
	DECLARE @end3 DATETIME;
	DECLARE @end4 DATETIME;
	DECLARE @end5 DATETIME;
	DECLARE @end6 DATETIME;

	SET @start2 = GETDATE();
	EXEC RemoveFromEdition;
	SET @end2 = GETDATE();
	INSERT INTO TestRuns VALUES ('RemoveFromEdition', @start2, @end2);
	INSERT INTO TestRunTables VALUES (@@IDENTITY, 1, @start2, @end2);

	SET @start1 = GETDATE();
	EXEC AddInEdition;
	SET @end1 = GETDATE();
	INSERT INTO TestRuns VALUES ('AddInEdition', @start2, @end2);
	INSERT INTO TestRunTables VALUES (@@IDENTITY, 1, @start2, @end2);

	SET @start4 = GETDATE();
	EXEC RemoveFromTeam;
	SET @end4 = GETDATE();
	INSERT INTO TestRuns VALUES ('RemoveFromTeam', @start4, @end4);
	INSERT INTO TestRunTables VALUES (@@IDENTITY, 2, @start4, @end4);

	SET @start3 = GETDATE();
	EXEC AddInTeam;
	SET @end3 = GETDATE();
	INSERT INTO TestRuns VALUES ('AddInTeam', @start3, @end3);
	INSERT INTO TestRunTables VALUES (@@IDENTITY, 2, @start3, @end3);

	SET @start6 = GETDATE();
	EXEC RemoveFromMakes;
	SET @end6 = GETDATE();
	INSERT INTO TestRuns VALUES ('RemoveFromMakes', @start6, @end6);
	INSERT INTO TestRunTables VALUES (@@IDENTITY, 3, @start6, @end6);

	SET @start5 = GETDATE();
	EXEC AddInMakes;
	SET @end5 = GETDATE();
	INSERT INTO TestRuns VALUES ('AddInMakes', @start5, @end5);
	INSERT INTO TestRunTables VALUES (@@IDENTITY, 3, @start5, @end5);

GO

EXEC UspTestRunTables;

-- provide values for the views for Has and Car
GO

CREATE PROC UspAddCar
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			INSERT INTO Car
			Values(@i, @i)
			SET @i = @i + 1 
		END

GO
EXEC UspAddCar

GO
CREATE PROC UspAddHas
AS
	DECLARE @i INT
	SET @i = 1
	WHILE @i < 100
		BEGIN
			INSERT INTO Has
			Values(@i, @i, @i)
			SET @i = @i + 1 
		END

GO

EXEC UspAddHas

GO
EXEC UspTestRunViews;

SELECT * FROM TestRuns
SELECT * FROM TestRunViews
SELECT * FROM TestRunTables

-- de verificat ordinea
DELETE FROM Has
DELETE FROM Car
DELETE FROM Edition
DELETE FROM Team
DELETE FROM Makes

DELETE FROM TestTables
INSERT INTO TestTables VALUES (2, 1, 100, 1)
INSERT INTO TestTables VALUES (4, 2, 100, 2)
INSERT INTO TestTables VALUES (6, 3, 100, 3)

SELECT * FROM TestTables

DELETE FROM TestRunViews
DELETE FROM TestRunTables
DELETE FROM TestRuns

SELECT * FROM Tables
SELECT * FROM Tests
SELECT * FROM Views
