use hw5
drop table Recipe
drop table Ingredient
drop table Product
--DROP TABLE Ta
CREATE TABLE Product(
	aid INT NOT NULL IDENTITY (1,1), 
	CONSTRAINT PK_Product PRIMARY KEY (aid),
	a2 INT UNIQUE,
	additionalField VARCHAR(100)
);
	
--DROP TABLE Ingredient
CREATE TABLE Ingredient(
	bid INT NOT NULL IDENTITY (1,1), 
	CONSTRAINT PK_Ingredient PRIMARY KEY (bid),
	b2 INT 
);

--DROP TABLE Recipe
CREATE TABLE Recipe(
	cid INT NOT NULL IDENTITY (1,1),
	CONSTRAINT PK_Recipe PRIMARY KEY (cid), 
	aid INT REFERENCES Product(aid), 
	bid INT REFERENCES Ingredient(bid)
	);


INSERT INTO Product VALUES (4,'a'),(22,'b'), (-17,'c'), (148,'d'), (0,'e')
INSERT INTO Ingredient VALUES (40), (2), (2), (190), (-132)
INSERT INTO Recipe VALUES (1,1),(2,2),(3,3),(4,4),(5,5), (5,1), (1,2), (2,3)


SELECT * FROM Recipe

--a)
--clustered index scan
SELECT *
FROM  Product 
WHERE a2 < 0 
ORDER BY aid DESC
--clustered index seek
SELECT * 
FROM Product 
WHERE aid>0

--nonclustered index scan + key lookup
SELECT *
FROM Product 
ORDER BY a2

--nonclustered index seek
SELECT aid 
FROM Product 
WHERE a2 = 4


--b)

GO 
DROP INDEX Idx_NC_b2 ON Ingredient
GO
--without creating said index, the execution plan is a clustered index scan
--Select estimated subtree cost: 0.0032875
SELECT *
FROM Ingredient 
WHERE b2 = 2


CREATE NONCLUSTERED INDEX Idx_NC_b2 ON Ingredient(b2)
--now, the execution plan will show a nonclustered index seek, which would be more efficient
--Select estimated subtree cost: 0.0032842


--c)
--this will use the previously created index and split the cost...
GO
CREATE or alter VIEW cView
AS 
	SELECT *
	FROM Product a
	INNER JOIN Ingredient b  ON a.a2=b.b2
	--INNER JOIN Recipe c ON a.aid = c.aid

GO
SELECT * FROM cView