use japanese_restaurant

--i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); 
--   rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN.

-- 1. -----ANY---- print top 3 orders that have bills values greater than the minimum bill from the last month
SELECT * FROM order_ o
WHERE DATEDIFF(MONTH,'2022-11-11',O.order_date ) >=-1

--SELECT * FROM order_
SELECT TOP 3 O.*
FROM order_ O
WHERE O.bill > ANY (
	SELECT O2.bill
	FROM order_ O2
	WHERE DATEDIFF(MONTH,'2022-11-11',O2.order_date ) >=-1)
ORDER BY o.bill DESC

--rewritten with an aggregation operator
--use MIN instead of ANY
SELECT TOP 3 O.*
FROM order_ O
WHERE O.bill > (
	SELECT MIN(O2.bill)
	FROM order_ O2
	WHERE DATEDIFF(MONTH,'2022-11-11',O2.order_date ) >=-1)
ORDER BY o.bill DESC


-- 2.---ALL---- print the employees with the salary greater than the ones that are working in delivery

--SELECT * FROM contract_em
--SELECT * from employees
SELECT C.*
FROM contract_em C
where c.salary>ALL(Select C2.salary
                   from contract_em C2, employees E
				   where C2.id_contract=E.id_contract_fk and E.ocupation='delivery')

-- rewritten with aggregation operator -> from ANY to MAX

SELECT C.*
FROM contract_em C
where c.salary>(Select MAX(C2.salary)
                   from contract_em C2, employees E
				   where C2.id_contract=E.id_contract_fk and E.ocupation='delivery')


-- 3. ---ANY--- Print the employees whose contracts expire in the next three years
SELECT E.employee_name,E.e_firstName
FROM employees E
WHERE E.id_employees = ANY(SELECT E2.id_employees
                           FROM employees E2, contract_em C
						   WHERE E2.id_contract_fk=C.id_contract and DATEDIFF(YEAR,'2022-11-11',C.final_date )<=3)

-- rewritten using IN
SELECT E.employee_name,E.e_firstName
FROM employees E
WHERE E.id_employees IN(SELECT E2.id_employees
                           FROM employees E2, contract_em C
						   WHERE E2.id_contract_fk=C.id_contract and DATEDIFF(YEAR,'2022-11-11',C.final_date )<=3)

-- 4. ---ALL--- Print all the products that are not containing salmon
--select * from product
--select * from recipe
--select * from ingredient

SELECT P.*
FROM product P
where P.id_product <> ALL(Select R.id_product
                          from recipe R,ingredient I
						  where R.id_ingredient=I.id_ingredient and I.description_i like '%salmon%')
--rewritten using NOT IN
SELECT P.*
FROM product P
where P.id_product NOT IN(Select R.id_product
                          from recipe R,ingredient I
						  where R.id_ingredient=I.id_ingredient and I.description_i like '%salmon%')
