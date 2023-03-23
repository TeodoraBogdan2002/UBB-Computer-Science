use japanese_restaurant
--UNION
--a. 2 queries with the union operation; use UNION [ALL] and OR;

--select the employees that have the ocupation delivary or management
SELECT id_employees , employee_name
FROM employees as E
where E.ocupation like 'delivery'
UNION ALL
SELECT id_employees , employee_name
FROM employees as E
where E.ocupation like 'management'

--select the employees that have the salary smaller than 3500 OR bigger than 4000

SELECT id_employees , employee_name ,salary
FROM employees as E, contract_em as C
where (C.salary <=3500 or C.salary>=4000) and C.id_contract=E.id_contract_fk

