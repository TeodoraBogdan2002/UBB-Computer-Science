use japanese_restaurant

--e. 2 queries with the IN operator and a subquery in the WHERE clause; 
--in at least one case, the subquery must include a subquery in its own WHERE clause;

-- 1. Get the distinct orders that have the product 'fish maki' and 'salmon roll'
--USED DISTINCT
SELECT DISTINCT O.id_order,O.order_date, P.id_product,P.description_pr
FROM order_ O
INNER JOIN product_distribution PD ON O.id_order=PD.id_order
INNER JOIN product P on PD.id_product=P.id_product
WHERE P.id_product IN(
                       SELECT P1.id_product 
					   FROM product P1
					   WHERE P1.description_pr ='fish maki' OR P1.description_pr='salmon roll')

-- 2. Get the products that were ordered until now
SELECT * 
FROM product
WHERE product.id_product 
IN ( SELECT DISTINCT product_distribution.id_product
	 FROM  product_distribution);
