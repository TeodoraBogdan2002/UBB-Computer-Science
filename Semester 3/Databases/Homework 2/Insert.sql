use japanese_r


INSERT INTO contract_em(id_contract, final_date, salary)
VALUES (1, '2027-03-20', 3400),
        (2, '2023-11-25', 4500),
		(3, '2025-10-13', 3900),
		(4, '2028-05-16', 4000 )


INSERT INTO costumer(id_costumer, name_c, address_c, phone_nr)
VALUES (1, 'Popescu', 'Str. X, nr. 10, Cluj-Napoca', '0712345678'),
       (2, 'Pop', 'Str. Y, nr. 5, Cluj-Napoca', '0787654321'),
	   (3, 'Y', 'Str. M, nr. 36, Floresti', '0723141567'),
	   (4, 'Marinescu', 'Str. K, nr. 24, Gilau', '0728769287')
--	   (5, 'Bogdan', 'Str. T, nr 10, Apahida', '0756565656'),
--	   (6, 'Aaaa', 'Str. N, nr. 7, Feleacu', '0711111111')


INSERT INTO employees(id_employees, id_contract, employee_name, e_firstName, ocupation, phone_nr_e)
values (1, 2, 'Pop', 'A', 'management', '0711111117'),
       (2, 2, 'Ttt', 'B', 'management', '0798989898'),
	   (3, 4, 'Popescu', 'Ana', 'cook', '0712121212'),
	   (4, 4, 'Cerna', 'Y', 'cook', '0722222222'),
	   (5, 3, 'Xlescu' , 'Bbb' , 'delivery', '0711221122'),
       (6, 1, 'F', 'Ana', 'delivery', '0761616161')

SELECT *
FROM contract_em

SELECT *
FROM costumer

SELECT *
FROM employees

INSERT INTO order_(id_order, id_costumer, order_date, order_hour, bill,informations)
values (1,2,'2022-10-11',16, 45,''),
       (2,2,'2022-09-27',10,100,''),
	   (3,1,'2022-08-20',19,120,''),
	   (4,3, '2022-10-25',20,30,''),
	   (5, 1, '2022-10-3', 12, 200,''),
       (6, 2, '2022-09-14', 17, 250,'')

SELECT *
FROM order_

INSERT INTO product(id_product,name_pr,price,description_pr)
VALUES (1,'classic sushi rolls',30,'avocado roll'),
		(2,'classic sushi rolls',28,'salmon roll'),
		(3,'maki',25,'fish maki'),
		(4,'maki',33,'vegetable maki'),
		(5,'salad',29,'vegetables salad'),
		(6,'vegan roll sushi',27,'veggie rolls')
SELECT *
FROM product

INSERT INTO product_distribution(id_distribution,id_order,id_product)
VALUES(1,1,1),
		(2,1,4),
		(3,2,2),
		(4,3,2),
		(5,3,3),
		(6,3,4),
		(7,4,1),
		(8,5,4),
		(9,6,2),
		(10,6,4)
SELECT *
FROM product_distribution

INSERT INTO ingredient(id_ingredient,description_i)
VALUES(1,'rice'),
		(2,'salmon'),
		(3,'wasabi'),
		(4,'avocado'),
		(5,'alga')

SELECT *
FROM ingredient

INSERT INTO recipe(id_recipe, id_product, id_ingredient,quantity)
VALUES (1,1,1,20),
		(2,1,4,25),
		(3,2,1,30),
		(4,2,2,15),
		(5,3,3,8),
		(6,3,1,30),
		(7,4,1,20)
SELECT *
FROM recipe

--------------------UPDATE-------

SELECT * FROM employees
UPDATE employees SET employee_name='Marc' WHERE id_employees=3 AND id_employees=5
SELECT* FROM employees

SELECT * FROM contract_em
UPDATE contract_em SET final_date='2025-08-17' WHERE salary<=4000
SELECT * FROM contract_em

SELECT * FROM product
UPDATE product SET price=27 WHERE description_pr IN ('avocado roll','vegetable maki')
SELECT * FROM product

-------------------DELETE-------------

DELETE 
FROM recipe
WHERE id_ingredient=1


