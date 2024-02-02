use japanese_restaurant


IF OBJECT_ID(N'dbo.ingredient_distribuiton', N'U') IS NOT NULL  
   DROP TABLE [dbo].[ingredient_distribuiton];  
GO

IF OBJECT_ID(N'dbo.recipe', N'U') IS NOT NULL  
   DROP TABLE [dbo].[recipe];  
GO

IF OBJECT_ID(N'dbo.product_distribution', N'U') IS NOT NULL  
   DROP TABLE [dbo].[product_distribution];  
GO

drop table ingredient

drop table producer

IF OBJECT_ID(N'dbo.product', N'U') IS NOT NULL  
   DROP TABLE [dbo].[product];  
GO
IF OBJECT_ID(N'dbo.employees', N'U') IS NOT NULL  
   DROP TABLE [dbo].[employees];  
GO
IF OBJECT_ID(N'dbo.order_', N'U') IS NOT NULL  
   DROP TABLE [dbo].[order_];  
GO
IF OBJECT_ID(N'dbo.costumer', N'U') IS NOT NULL  
   DROP TABLE [dbo].[costumer];  
GO

IF OBJECT_ID(N'dbo.contract_em', N'U') IS NOT NULL  
   DROP TABLE [dbo].[contract_em];  
GO

CREATE TABLE contract_em(id_contract INT PRIMARY KEY NOT NULL,
                        final_date DATE NOT NULL,
						salary INT NOT NULL);

--1(contract_e):n(employees)

CREATE TABLE employees(id_employees int PRIMARY KEY NOT NULL, 
                       id_contract_fk INT FOREIGN KEY REFERENCES contract_em(id_contract) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
                       employee_name char(20) NOT NULL,
					   e_firstName char(20) NOT NULL,
					   ocupation char(30) NOT NULL,
					   phone_nr_e int);



CREATE TABLE costumer(id_costumer INT PRIMARY KEY NOT NULL,
                      name_c VARCHAR(50) not null,
					  address_c varchar(100) not null,
					  phone_nr varchar(11) not null);

CREATE TABLE order_(id_order INT PRIMARY KEY NOT NULL,
                    id_costumer INT FOREIGN KEY REFERENCES costumer(id_costumer) ON DELETE CASCADE ON UPDATE CASCADE,
					order_date DATE NOT NULL,
					order_hour int NOT NULL,
					bill int NOT NULL,
					informations VARCHAR(200));



CREATE TABLE product(id_product INT PRIMARY KEY NOT NULL,
                     name_pr VARCHAR(50) NOT NULL,
					 price INT NOT NULL,
					 description_pr VARCHAR(100));

CREATE TABLE product_distribution(id_distribution INT PRIMARY KEY NOT NULL,
                                  id_order INT FOREIGN KEY REFERENCES order_(id_order) ON DELETE CASCADE ON UPDATE CASCADE,
								  id_product INT FOREIGN KEY REFERENCES product(id_product) ON DELETE CASCADE ON UPDATE CASCADE);


CREATE TABLE ingredient(id_ingredient INT PRIMARY KEY NOT NULL,
                        description_i VARCHAR(50) NOT NULL);

create table producer(id_producer INT PRIMARY KEY NOT NULL,
                       name_producer varchar(50),
					   address_producer varchar(100),
					   phone_nr_pr varchar(11));

CREATE TABLE recipe(id_recipe INT PRIMARY KEY NOT NULL,
                    id_product INT FOREIGN KEY REFERENCES product(id_product) ON DELETE CASCADE ON UPDATE CASCADE,
					id_ingredient INT FOREIGN KEY REFERENCES ingredient(id_ingredient) ON DELETE CASCADE ON UPDATE CASCADE,
					quantity int);

CREATE TABLE ingredient_distribuiton(id_producer INT FOREIGN KEY REFERENCES producer(id_producer) ON DELETE CASCADE ON UPDATE CASCADE,
					id_ingredient INT FOREIGN KEY REFERENCES ingredient(id_ingredient) ON DELETE CASCADE ON UPDATE CASCADE,
					price int not null,
					quantity int not null);