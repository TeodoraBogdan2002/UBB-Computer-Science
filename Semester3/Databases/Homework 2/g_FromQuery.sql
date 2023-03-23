use japanese_restaurant
--g. 2 queries with a subquery in the FROM clause;       

-- 1.Increase the salary with 300 to the employees whose contracts expire in less than 3 years
--we print the contract id, contract's final date, the salary, the increased salary and the difference years
--used DISTINCT
SELECT C.id_contract,C.final_date, C.salary, C.salary +300 AS increased_salary, DATEDIFF(YEAR,'2022-11-10',C.final_date ) AS years_until_final_date
FROM (
	SELECT *
	FROM contract_em C
	WHERE DATEDIFF(YEAR,'2022-11-10',C.final_date ) <=3
)C WHERE c.id_contract IN (
	SELECT DISTINCT employees.id_contract_fk
	FROM employees
)
ORDER BY increased_salary DESC

-- 2.Print the costumers that had paid bills that cost more than 100 
--USED DISTINCT
SELECT distinct C.id_costumer, C.name_c
FROM (
	SELECT C.id_costumer, C.name_c
	FROM costumer C  INNER JOIN order_ O on O.id_costumer = C.id_costumer
	WHERE O.bill >= 100
)C WHERE C.id_costumer IN (
	SELECT C.id_costumer
	FROM costumer C
	INNER JOIN order_ O on O.id_costumer = C.id_costumer
)