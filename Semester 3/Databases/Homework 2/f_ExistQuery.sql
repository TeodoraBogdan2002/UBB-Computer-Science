use japanese_restaurant

-- f. 2 queries using the EXISTS operator to introduce a subquery in the WHERE clause;

-- 1. find the products that are made with the ingredients with id 2
SELECT P.name_pr, P.description_pr ,P.price
FROM product P
WHERE EXISTS ( SELECT *
			   FROM recipe R
			   WHERE (R.id_ingredient = 2 or R.id_ingredient=4) AND R.id_product = P.id_product
			   )
ORDER BY P.price 

-- 2.Get the average salary of the employees that work in cooking and delivery

SELECT AVG(C.salary) AS [Average Salary]  --(4000+4000+3400+3900)/4
FROM employees e
INNER JOIN contract_em C ON C.id_contract=e.id_contract_fk
WHERE EXISTS(
	SELECT E.id_employees
	FROM employees E
	WHERE E.id_contract_fk = C.id_contract AND (e.ocupation = 'cook' or e.ocupation='delivery')
)