use japanese_restaurant

--INTERSECT
--b. 2 queries with the intersection operation; use INTERSECT and IN;

--select the costumers that ordered at least once

SELECT id_costumer, costumer.name_c
from costumer
intersect 
select O.id_costumer, costumer.name_c
from order_ O,costumer

--get only the employees which belong to the contracts with id 1, 3 or 4
SELECT id_employees, employee_name, id_contract_fk
FROM employees
WHERE id_contract_fk IN (1,3,4);