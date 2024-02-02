--h) 4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 
--   2 of the latter will also have a subquery in the HAVING clause; use the 
--   aggregation operators: COUNT, SUM, AVG, MIN, MAX

-- 1. Print costumers and the number of orders for each of them
--used COUNT
SELECT O.id_costumer, COUNT(*) AS nr_orders
FROM order_ O
GROUP BY O.id_costumer

-- 2. Print the total quantity from the recipe of the ingredients that have ids 1 or 2
SELECT SUM(recipe.quantity) AS quantity, ingredient.id_ingredient
FROM ( ingredient INNER JOIN recipe on ingredient.id_ingredient = recipe.id_ingredient )
GROUP BY ingredient.id_ingredient
having ingredient.id_ingredient=1 or ingredient.id_ingredient=2


--3. Print the contracts ids and salaries of the contracts that have assigned two or more employees
SELECT * FROM employees
SELECT * FROM contract_em
SELECT C.id_contract, C.salary as Salary
FROM contract_em C INNER JOIN employees E
	ON E.id_contract_fk = C.id_contract
GROUP BY C.id_contract,C.salary
HAVING 1 < (SELECT count(*)
			FROM employees E2
			WHERE C.id_contract = E2.id_contract_fk)

--4. print the avg of the bills values for each costumer with at least two orders
SELECT * FROM order_
SELECT * FROM costumer
SELECT O.id_costumer, AVG(O.bill) AS average_bills
FROM order_ O
GROUP BY O.id_costumer
HAVING 1 < (SELECT COUNT(O2.id_costumer)
			FROM order_ O2
			WHERE O.id_costumer = O2.id_costumer )
