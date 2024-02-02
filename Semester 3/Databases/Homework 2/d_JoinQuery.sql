use japanese_restaurant

--d. 4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); 
--one query will join at least 3 tables, while another one will join at least two many-to-many relationships;

--1. INNER JOIN -> get the ingredients that were used in the products that appear in the orders

SELECT I.id_ingredient,I.description_i, R.id_recipe, P.name_pr, O.id_order, O.order_date
FROM ingredient I
INNER JOIN recipe R ON I.id_ingredient=R.id_ingredient
INNER JOIN product P ON R.id_product=P.id_product
INNER JOIN product_distribution PD ON P.id_product=PD.id_product
INNER join order_ O ON PD.id_order=O.id_order
ORDER BY R.id_recipe

--2. LEFT JOIN -> get the name of the product's ingredients, also the ingredients that are not used
SELECT I.id_ingredient,I.description_i, R.id_recipe, P.name_pr
FROM ingredient I
LEFT JOIN recipe R ON I.id_ingredient=R.id_ingredient
LEFT JOIN product P ON R.id_product=P.id_product
ORDER BY R.id_recipe

--3. RIGHT JOIN ->get the name of the products and the ingredients that were used and also the products 
                --that have not ingredients distributed yet
SELECT I.id_ingredient,I.description_i, R.id_recipe, P.name_pr
FROM ingredient I
RIGHT JOIN recipe R ON I.id_ingredient=R.id_ingredient
RIGHT JOIN product P ON R.id_product=P.id_product
ORDER BY R.id_recipe

--4. FULL OUTER JOIN ->get the products and the ingredients that were used in them, and also the ingredients that were not used yet and the products that have not ingredients

SELECT I.id_ingredient,I.description_i, R.id_recipe, P.name_pr
FROM ingredient I
FULL OUTER JOIN recipe R ON I.id_ingredient=R.id_ingredient
FULL OUTER JOIN product P ON R.id_product=P.id_product
ORDER BY R.id_recipe

